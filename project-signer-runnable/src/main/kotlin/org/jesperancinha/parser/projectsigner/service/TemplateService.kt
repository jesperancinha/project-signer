package org.jesperancinha.parser.projectsigner.service

import org.jesperancinha.parser.markdowner.helper.TemplateParserHelper
import org.jesperancinha.parser.markdowner.model.Paragraphs
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.FileInputStream
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.util.*

/**
 * A markdown raw service to handle markdown texts
 */
@Service
open class TemplateService(private val optionsService: OptionsService) {
    /**
     * Receives an input Markdown text stream nd parses its content to a Paragraphs object see [Paragraphs]
     *
     * @return A [Paragraphs] parsed object
     * @throws IOException Any kind of IO Exception
     */
    open fun  findAllParagraphs(): Paragraphs {
        val fileTemplate = optionsService.projectSignerOptions?.templateLocation?.toFile()
        val templateInputStream = fileTemplate?.let { FileInputStream(it) }
        return templateInputStream?.let { TemplateParserHelper.findAllParagraphs(templateInputStream) } ?: Paragraphs()
    }
    open fun findAllRedirectParagraphs(): Paragraphs? {
        val fileTemplate = optionsService.projectSignerOptions?.redirectTemplateLocation?.toFile()
        val templateInputStream = fileTemplate?.let { FileInputStream(it) }
        return templateInputStream?.let { TemplateParserHelper.findAllParagraphs(templateInputStream) } ?: Paragraphs()
    }

    open fun readAllLicenses(): List<String>? {
        val licenseLocations = optionsService.projectSignerOptions?.getLicenseLocations()
            ?: return emptyList()
        return licenseLocations
            .mapNotNull { path: Path ->
                try {
                    FileInputStream(path.toFile()).use { templateInputStream ->
                        return@mapNotNull String(
                            templateInputStream.readAllBytes(),
                            StandardCharsets.UTF_8
                        )
                    }
                } catch (e: IOException) {
                    logger.error(
                        "Failing to read raw file {}. Error {}",
                        path.fileName.toString(),
                        e.message
                    )
                    return@mapNotNull null
                }
            }
    }

    companion object {
        val logger: org.slf4j.Logger = LoggerFactory.getLogger(TemplateService::class.java)
    }
}