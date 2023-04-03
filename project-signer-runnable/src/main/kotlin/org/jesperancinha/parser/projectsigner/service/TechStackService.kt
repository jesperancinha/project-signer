package org.jesperancinha.parser.projectsigner.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.nio.file.Path
import java.util.regex.Pattern

val nameAndLinkPattern = Pattern.compile(".*\"([a-z A-Z0-9.]*)\".*(http.*)\\)")

private const val TECHNOLOGIES_USED = "## Technologies used\n"

private const val PLEASE_CHECK_THE_TECH_STACK_MD_TECK_STACK_MD_FILE_FOR_DETAILS =
    "\nPlease check the [TechStack.md](TechStack.md) file for details."

@Service
class TechStackService(
    val fileWriterService: FileWriterService,
) {
    fun mutateTechnologiesUsedParagraph(projectName: String, readmePath: Path, nonRefText: String): String {
        val headSplit = nonRefText.split(TECHNOLOGIES_USED)
        if (headSplit.size <= 1) {
            return nonRefText
        }
        val secondSplit = headSplit[1].split("## ")
        val techOriginalParagraph = secondSplit[0]
        if (techOriginalParagraph.isEmpty() || techOriginalParagraph.contains(PLEASE_CHECK_THE_TECH_STACK_MD_TECK_STACK_MD_FILE_FOR_DETAILS)) {
            return nonRefText
        }
        val techStackList = techOriginalParagraph.split("\n").let { lines ->
            lines.filter { it.isNotEmpty() && it != "---" }.mapNotNull {
                val matcher = nameAndLinkPattern.matcher(it)
                matcher.matches()
                runCatching {
                    matcher.run { "[${group(1)}](${group(2)})" }
                }.onFailure { ex ->
                    logger.error("Error found interpreting \"{}\" to tech stack! {}", it, ex.stackTraceToString())
                }.getOrNull()
            }
        }
        val techStackText = "$projectName TechStack\n\n- ${techStackList.joinToString("\n\n- ")}"
        fileWriterService.exportTechStack(readmePath, techStackText)
        return "${headSplit[0]}$TECHNOLOGIES_USED$PLEASE_CHECK_THE_TECH_STACK_MD_TECK_STACK_MD_FILE_FOR_DETAILS\n\n## ${
            secondSplit.subList(
                1,
                secondSplit.size
            ).joinToString("## ")
        }"
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TechStackService::class.java)
    }
}