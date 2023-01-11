package org.jesperancinha.parser.projectsigner.api

import io.kotest.matchers.nulls.shouldNotBeNull
import org.jesperancinha.parser.markdowner.filter.FileFilterChain
import org.jesperancinha.parser.markdowner.filter.MavenFilter
import org.jesperancinha.parser.markdowner.model.Paragraphs
import org.jesperancinha.parser.projectsigner.model.ProjectData
import org.jesperancinha.parser.projectsigner.service.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import java.io.IOException
import java.io.InputStream
import java.nio.file.Path

class InterfacesTest {
    @Test
    fun `should create and functionally run anonymous class instances`() {
        val fileWriterService: FileWriterService = object : FileWriterService() {
            override fun exportReadmeFile(path: Path, text: String) {}
            override fun exportReportFiles(path: Path, projectDataList: List<ProjectData>) {}
        }
        val mergeService: MergeService = object : MergeService(fileWriterService) {
            override fun writeMergedResult(readmePath: Path, newText: String) {}
        }
        val optionsService: OptionsService = mock(OptionsService::class.java)
        val readmeService: ReadmeService = object : ReadmeService(mergeService, optionsService) {
            override fun readDataSprippedOfTags(templateInputStream: InputStream, vararg tags: String): String? {
                return null
            }

            @Throws(IOException::class)
            override fun exportNewReadme(readmePath: Path, inputStream: InputStream, allParagraphs: Paragraphs) {
            }
        }
        val readmeNamingService: ReadmeNamingService =
            object : ReadmeNamingService(
                FileFilterChain(nextFileFilterChain = null, projectFilter = MavenFilter()),
                optionsService
            ) {
                @Throws(IOException::class)
                override fun buildReadmeStream(path: Path): InputStream? {
                    return null
                }
            }
        val generatorService: GeneratorSevice = object : GeneratorSevice(readmeNamingService, readmeService) {
            override fun processReadmeFile(readmePath: Path, allParagraphs: Paragraphs) {}
            override fun processLicenseFile(licencePath: Path, licenses: List<String>?) {}
        }
        val templateService: TemplateService = object : TemplateService(optionsService) {
            override fun findAllParagraphs(): Paragraphs {
                return Paragraphs()
            }

            override fun readAllLicenses(): List<String>? {
                return null
            }
        }
        val finderService: FinderService = object : FinderService(generatorService, templateService) {
            override fun iterateThroughFilesAndFolders(rootPath: Path): Path? {
                return null
            }
        }
        fileWriterService.shouldNotBeNull()
        finderService.shouldNotBeNull()
        generatorService.shouldNotBeNull()
        mergeService.shouldNotBeNull()
        optionsService.shouldNotBeNull()
        readmeNamingService.shouldNotBeNull()
        readmeService.shouldNotBeNull()
        templateService.shouldNotBeNull()
    }
}