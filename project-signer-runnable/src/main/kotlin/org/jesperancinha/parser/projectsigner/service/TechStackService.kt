package org.jesperancinha.parser.projectsigner.service

import org.jesperancinha.parser.markdowner.model.Paragraphs
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.nio.file.Path
import java.util.regex.Pattern

val nameAndLinkPattern = Pattern.compile(".*\"([a-z A-Z]*)\".*(http.*)\\)")

private const val TECHNOLOGIES_USED = "Technologies used"

private const val PLEASE_CHECK_THE_TECH_STACK_MD_TECK_STACK_MD_FILE_FOR_DETAILS = "\nPlease check the [TechStack.md](TeckStack.md) file for details."

@Service
class TechStackService(
    val fileWriterService: FileWriterService,
) {
    fun mutateTechnologiesUsedParagraph(readmePath: Path, globalParagraphs: Paragraphs): Paragraphs {
        val techOriginalParagraph = globalParagraphs.getParagraphByTag(TECHNOLOGIES_USED)?.text
        if(techOriginalParagraph == PLEASE_CHECK_THE_TECH_STACK_MD_TECK_STACK_MD_FILE_FOR_DETAILS ){
            return globalParagraphs
        }
        val techStackList = techOriginalParagraph?.split("\n")?.let { lines ->
            lines.filter { it.isNotEmpty() && it != "---" }
                .map {
                    val matcher = nameAndLinkPattern.matcher(it)
                    matcher.matches()
                    runCatching {
                        matcher.run { "[${group(1)}](${group(2)})" }
                    }.onFailure {ex->
                        logger.error("Error found interpreting \"{}\" to tech stack! {}", it, ex.stackTraceToString())
                    }
                }
        } ?: emptyList()
        val techStackText = techStackList.joinToString("\n")
        fileWriterService.exportTechStack(readmePath, techStackText)
        globalParagraphs.withTagParagraph(
            "## $TECHNOLOGIES_USED",
            PLEASE_CHECK_THE_TECH_STACK_MD_TECK_STACK_MD_FILE_FOR_DETAILS
        )
        globalParagraphs.tags.removeLast()
        return globalParagraphs
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TechStackService::class.java)
    }
}