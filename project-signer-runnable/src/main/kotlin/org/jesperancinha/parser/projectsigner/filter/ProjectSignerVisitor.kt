package org.jesperancinha.parser.projectsigner.filter

import org.jesperancinha.parser.markdowner.model.Paragraphs
import org.jesperancinha.parser.projectsigner.service.GeneratorSevice
import org.jesperancinha.parser.projectsigner.service.findReadmeFileName
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.ObjectUtils
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.*
import kotlin.io.path.absolutePathString
import kotlin.io.path.deleteIfExists

open class ProjectSignerVisitor(
    private val generatorService: GeneratorSevice? = null,
    private val allParagraphs: Paragraphs,
    private val allRedirectParagraphs: Paragraphs?,
    private val allLicenseText: List<String>? = null,
    private val rootPath: Path
) : SimpleFileVisitor<Path>() {

    override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
        logger.trace("Visiting file {}", file)
        return FileVisitResult.CONTINUE
    }

    override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
        return if (ProjectSignerFilterHelper.isIgnorableFolder(dir)) {
            FileVisitResult.SKIP_SUBTREE
        } else FileVisitResult.CONTINUE
    }

    @Throws(IOException::class)
    override fun postVisitDirectory(dir: Path?, e: IOException?): FileVisitResult {
        dir?.let {
            if (ProjectSignerFilterHelper.isIgnorableFolder(dir)) {
                return FileVisitResult.SKIP_SUBTREE
            }
            if (ObjectUtils.isEmpty(e)) {
                dir.findReadmeFileName()?.let {
                    if (it != "Readme.md") {
                        val toPath = File(dir.toFile(), it).toPath()
                        val toPathCheck = File(dir.toFile(), "Readme.md").exists()
                        if (toPathCheck) toPath.deleteIfExists() else Files.move(
                            toPath,
                            toPath.resolveSibling("Readme.md")
                        )
                    }
                }

                if (allRedirectParagraphs == null ||
                    allRedirectParagraphs.getParagraphCount() == 0 ||
                    dir.isUserProject(rootPath)
                ) {
                    generatorService?.processReadmeFile(dir, allParagraphs)
                } else {
                    generatorService?.processReadmeFile(dir, requireNotNull(allRedirectParagraphs))
                }
                if (Objects.nonNull(allLicenseText)) {
                    try {
                        generatorService?.processLicenseFile(dir, allLicenseText)
                    } catch (ex: Throwable) {
                        throw RuntimeException(ex)
                    }
                }
            } else {
                logger.error("Failed on file {}", dir, e)
            }
        }
        return FileVisitResult.CONTINUE
    }

    override fun visitFileFailed(
        file: Path,
        exc: IOException
    ): FileVisitResult {
        if (exc is AccessDeniedException) {
            logger.warn("Access denied on file {}", file)
        } else {
            logger.error("Error on file {}", file, exc)
        }
        return FileVisitResult.CONTINUE
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ProjectSignerVisitor::class.java)
    }
}

private fun Path.isUserProject(rootPath: Path): Boolean {
    val process = ProcessBuilder("git", "config", "--get", "user.name")
        .start()

    val reader = BufferedReader(InputStreamReader(process.inputStream))
    val username = reader.readLine()

    process.waitFor()
    return this.absolutePathString().replace(rootPath.absolutePathString(), "").contains("/$username")
}
