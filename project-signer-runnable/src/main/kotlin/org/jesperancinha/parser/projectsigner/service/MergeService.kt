package org.jesperancinha.parser.projectsigner.service

import lombok.extern.slf4j.Slf4j
import org.jesperancinha.parser.markdowner.helper.MergeParserHelper
import org.jesperancinha.parser.markdowner.model.Paragraphs
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.IOException
import java.nio.file.Path

/**
 * A merge service destined to merge operation between markdown files and objects
 */
@Slf4j
@Service
open class MergeService(private val fileWriterService: FileWriterService) {
    /**
     * Receives a complete Markdown text and a [Paragraphs] instance and adds all paragraphs in the stipulated order to the end of the text
     *
     * @param readmeMd A complete String representation of a Markdown text
     * @param footer   A [Paragraphs] instance which will add all paragraphs to the end of the markdown text
     * @return The complete String merge between a [String] text and a [Paragraphs] instances
     */
    fun mergeDocumentWithFooterTemplate(readmeMd: String?, footer: Paragraphs?): String {
        return MergeParserHelper.mergeDocumentWithFooterTemplate(readmeMd, footer)
    }

    @Throws(IOException::class)
    open fun writeMergedResult(readmePath: Path, newText: String) {
        logger.trace("New readme:\n {}", newText)
        fileWriterService.exportReadmeFile(readmePath, newText)
    }
    
    companion object {
        val logger: Logger = LoggerFactory.getLogger(MergeService::class.java)
    }
}