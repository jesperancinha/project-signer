package org.jesperancinha.parser.projectsigner.filter

import org.jesperancinha.parser.markdowner.model.Paragraphs
import org.jesperancinha.parser.projectsigner.service.GeneratorSevice
import org.jesperancinha.parser.projectsigner.service.findReadmeFileName
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.ObjectUtils
import java.io.File
import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.*

open class ProjectSignerVisitor(
    private val generatorService: GeneratorSevice? = null,
    private val allParagraphs: Paragraphs? = null,
    private val allLicenseText: List<String>? = null
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
                        Files.move(toPath, toPath.resolveSibling("Readme.md"))
                    }
                }
                generatorService!!.processReadmeFile(dir, allParagraphs)
                if (Objects.nonNull(allLicenseText)) {
                    try {
                        generatorService.processLicenseFile(dir, allLicenseText)
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