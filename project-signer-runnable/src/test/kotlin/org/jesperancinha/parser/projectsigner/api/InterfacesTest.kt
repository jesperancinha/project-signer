package org.jesperancinha.parser.projectsigner.api

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.jesperancinha.parser.markdowner.filter.FileFilterChain
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
    fun testAllInterfacesWhenCreatingImplementationThenAllow() {
        val fileWriterService: FileWriterService = object : FileWriterService() {
            override fun exportReadmeFile(path: Path, text: String?) {}
            override fun exportReportFiles(path: Path?, projectDataList: List<ProjectData>) {}
        }
        val mergeService: MergeService = object : MergeService(fileWriterService) {
            override fun writeMergedResult(readmePath: Path, newText: String?) {}
        }
        val optionsService: OptionsService = mock(OptionsService::class.java)
        val readmeService: ReadmeService = object : ReadmeService(mergeService, optionsService) {
            override fun readDataSprippedOfTags(templateInputStream: InputStream?, vararg tags: String): String? {
                return null
            }

            @Throws(IOException::class)
            override fun exportNewReadme(readmePath: Path, inputStream: InputStream?, allParagraphs: Paragraphs?) {
            }
        }
        val readmeNamingService: ReadmeNamingService =
            object : ReadmeNamingService(FileFilterChain.builder().build(), optionsService) {
                @Throws(IOException::class)
                override fun buildReadmeStream(path: Path?): InputStream? {
                    return null
                }
            }
        val generatorService: GeneratorSevice = object : GeneratorSevice(readmeNamingService, readmeService) {
            override fun processReadmeFile(readmePath: Path, allParagraphs: Paragraphs?) {}
            override fun processLicenseFile(licencePath: Path, license: List<String>?) {}
        }
        val templateService: TemplateService = object : TemplateService(optionsService) {
            override fun findAllParagraphs(): Paragraphs? {
                return null
            }

            override fun readAllLicenses(): List<String>? {
                return null
            }
        }
        val finderService: FinderService = object : FinderService(generatorService, templateService) {
            override fun iterateThroughFilesAndFolders(rootPath: Path?): Path? {
                return null
            }
        }
        assertThat(fileWriterService).isNotNull
        assertThat(finderService).isNotNull
        assertThat(generatorService).isNotNull
        assertThat(mergeService).isNotNull
        assertThat(optionsService).isNotNull
        assertThat(readmeNamingService).isNotNull
        assertThat(readmeService).isNotNull
        assertThat(templateService).isNotNull
    }
}