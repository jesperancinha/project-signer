package org.jesperancinha.parser.projectsigner.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.io.IOUtils
import org.jesperancinha.parser.markdowner.badges.parser.BadgeParser
import org.jesperancinha.parser.markdowner.helper.ReadmeParserHelper
import org.jesperancinha.parser.markdowner.model.Paragraphs
import org.jesperancinha.parser.projectsigner.model.SignerMatch
import org.jesperancinha.parser.projectsigner.model.SignerPattern
import org.jesperancinha.parser.projectsigner.model.ProjectData
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.*
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.util.*
import java.util.function.Consumer
import java.util.regex.Pattern
import java.util.stream.Collectors
import kotlin.io.path.name
import kotlin.system.exitProcess

/**
 * A Readme service to read and manipulate markdown files
 */
@Service
open class ReadmeService(
    private val mergeService: MergeService,
    private val optionsService: OptionsService,
    private val techStackService: TechStackService,
) {
    val allProjectData: MutableList<ProjectData> = ArrayList()

    /**
     * Reads an input marked down string and returns the exact same text without the specified cardinal tags and their content.
     *
     *
     * This means any content of # tags
     *
     * @param templateInputStream An input stream of a Markdown text
     * @param tags              All tags of the paragraphs to be removed
     * @return The filtered String
     * @throws IOException Any IO Exception thrown
     */
    @Throws(IOException::class)
    open fun readDataStrippedOfTags(templateInputStream: InputStream, vararg tags: String): String? {
        return if (tags.isEmpty())
            IOUtils.toString(templateInputStream, Charset.defaultCharset())
        else ReadmeParserHelper.readDataSprippedOfTags(templateInputStream, *tags)
    }

    @Throws(IOException::class)
    open fun exportNewReadme(readmePath: Path, inputStream: InputStream, allParagraphs: Paragraphs) {
        logger.info("Visiting path {}", readmePath)
        val readme =
            readDataStrippedOfTags(inputStream, *optionsService.projectSignerOptions?.tagNames ?: emptyArray()) ?: ""
        val newText = mergeService.mergeDocumentWithFooterTemplate(readme, allParagraphs)
        val lintedText = createLintedText(newText)
        val nonRefText = removeNonRefs(lintedText)
        if (!optionsService.projectSignerOptions?.rootDirectory?.relativize(readmePath).toString().contains("/")) {
            readme.split("\n".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
                .firstOrNull()
                ?.replace(
                    "#",
                    ""
                )
                ?.trim()?.let {
                    ProjectData(
                        title = it,
                        directory = runCatching {
                            val toPath =
                                readmePath.toFile().absoluteFile.relativeTo(File(".").absoluteFile.parentFile.parentFile)
                                    .toPath()
                            if (toPath.name == "")
                                readmePath.subpath(2, 3).name
                            else
                                toPath.subpath(1, 2).name
                        }.getOrElse {
                            logger.error("ERROR getting title!", it)
                            null
                        },
                        badgeGroupMap = BadgeParser.parse(readme)
                    )
                }
                ?.let {
                    allProjectData.add(
                        it
                    )
                }
        }
        val techStackTextFiltered =
            techStackService.mutateTechnologiesUsedParagraph(nonRefText.split("\n")[0], readmePath, nonRefText)
        mergeService.writeMergedResult(readmePath, techStackTextFiltered)
    }

    private fun createLintedText(newText: String): String {
        val ref = object : Any() {
            var readme = newText
        }
        lintMatches!!.forEach(Consumer { signerPattern: SignerPattern ->
            val m = signerPattern.find.matcher(ref.readme)
            if (m.find()) {
                ref.readme = m.replaceAll(signerPattern.replace)
            }
        })
        return ref.readme
    }

    private fun removeNonRefs(newText: String): String {
        return if (Objects.isNull(newText) || refsToRemove!!.size == 1 && refsToRemove!![0].isEmpty()) {
            newText
        } else Arrays.stream(newText.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            .filter { text: String -> !textIsRef(text) }
            .collect(Collectors.joining("\n")) + "\n".replace("[\r\n]{2}".toRegex(), "\n\n")
            .replace("[\r\n]{3}".toRegex(), "\n")
    }

    private fun textIsRef(text: String): Boolean {
        return textHasWord(text)
    }

    private fun textHasWord(text: String): Boolean {
        return refsToRemove!!.stream().anyMatch { ref: String ->
            text.lowercase(Locale.getDefault()).contains(ref.lowercase(Locale.getDefault()))
        }
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ReadmeService::class.java)
        private var refsToRemove: List<String>? = null
        private var lintMatches: List<SignerPattern>? = null
        private val objectMapper = ObjectMapper()

        init {
            try {
                refsToRemove = Arrays.stream(
                    IOUtils.toString(
                        ReadmeService::class.java.getResourceAsStream("/references.txt"), StandardCharsets.UTF_8.name()
                    )
                        .split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                ).collect(Collectors.toList())
                val jsonLint = IOUtils.toString(
                    ReadmeService::class.java.getResourceAsStream("/jeorg-lint.json"),
                    StandardCharsets.UTF_8.name()
                )
                lintMatches = objectMapper.readValue(jsonLint, Array<SignerMatch>::class.java)
                    .map { lintMatch: SignerMatch ->
                        SignerPattern(
                            find = Pattern.compile(lintMatch.find),
                            replace = lintMatch.replace

                        )
                    }
            } catch (e: IOException) {
                e.printStackTrace()
                exitProcess(1)
            }
        }
    }

}