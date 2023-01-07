package org.jesperancinha.parser.projectsigner.service

import lombok.extern.slf4j.Slf4j
import org.jesperancinha.parser.markdowner.model.Paragraphs
import org.jesperancinha.parser.projectsigner.filter.ProjectSignerLicenseFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.util.*
import kotlin.system.exitProcess

/**
 * This is a generator service which is responsible for the generation of the new Readme.md file
 * It includes extra functions:
 *
 *
 * - Denies creation of Readme.md files where no project exists
 * - Creates matching Readme.me files where they don't exist in spite of existing package managing system files
 */
@Slf4j
@Service
open class GeneratorSevice(
    private val readmeNamingService: ReadmeNamingService,
    private val readmeService: ReadmeService
) {
    @Throws(IOException::class)
    open fun processReadmeFile(readmePath: Path, allParagraphs: Paragraphs?) {
        val inputStream = readmeNamingService.buildReadmeStream(readmePath)
        if (Objects.nonNull(inputStream)) {
            readmeService.exportNewReadme(readmePath, inputStream, allParagraphs)
        }
    }

    @Throws(Throwable::class)
    open fun processLicenseFile(licencePath: Path, licenses: List<String>?) {
        val f = File(licencePath.toFile(), "Readme.md")
        val licenseLegacyFile = File(licencePath.toFile(), "License.txt")
        var licenseText: String? = null
        if (licenseLegacyFile.exists()) {
            val delete = licenseLegacyFile.delete()
            if (!delete) {
                logger.error("Could not delete existing legacy file License.txt. Exiting...")
                exitProcess(1)
            }
        }
        val expectedLegactyFile = File(licencePath.toFile(), "License")
        if (expectedLegactyFile.exists()) {
            val templateInputStream = FileInputStream(expectedLegactyFile)
            licenseText = String(templateInputStream.readAllBytes(), StandardCharsets.UTF_8)
        }
        val licenseFile = File(licencePath.toFile(), "LICENSE")
        if (licenseFile.exists()) {
            val delete = licenseFile.delete()
            if (!delete) {
                logger.error("Could not delete existing licence file LICENSE. Exiting...")
                exitProcess(1)
            }
        }
        if (f.exists()) {
            try {
                val newLicense = ProjectSignerLicenseFilter.getLicense(licenses, licenseText)
                val fileWriter = FileWriter(licenseFile)
                fileWriter.write(newLicense)
                fileWriter.flush()
                fileWriter.close()
            } catch (e: RuntimeException) {
                logger.error(
                    "Failed to find license for path {} and text {}",
                    licencePath.toString(),
                    licenseText
                )
            }
        }
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(GeneratorSevice::class.java)
    }
}