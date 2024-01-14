package org.jesperancinha.parser.projectsigner.service

import io.kotest.matchers.shouldBe
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.jesperancinha.parser.markdowner.model.Paragraphs
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import java.io.IOException
import java.nio.file.Path

@ExtendWith(MockKExtension::class)
internal class FinderServiceTest {
    @InjectMockKs
    lateinit var finderService: FinderService

    @MockK
    lateinit var readmeNamingService: ReadmeNamingService

    @MockK
    lateinit var mergeService: MergeService

    @MockK(relaxed = true)
    lateinit var templateService: TemplateService

    @MockK
    lateinit var readmeService: ReadmeService

    @MockK
    lateinit var optionsService: OptionsServiceMock

    @MockK
    lateinit var fileWriterService: FileWriterService

    @MockK(relaxed = true)
    lateinit var generatorService: GeneratorSevice

    @BeforeEach
    fun setUp() {
        System.getProperty("file.encoding") shouldBe "UTF-8"
    }

    @Test
    @Throws(IOException::class)
    fun testIterateThroughFilesAndFolders() {
        val mockParagraphs: Paragraphs = mockk()
        every { mockParagraphs.getParagraphCount() } returns 0
        every { templateService.findAllParagraphs() } returns mockParagraphs
        every { templateService.findAllRedirectParagraphs() } returns mockParagraphs
        finderService.iterateThroughFilesAndFolders(tempDirectory)
        verify { templateService.findAllParagraphs() }
        verify { generatorService.processReadmeFile(tempDirectory, mockParagraphs) }
        confirmVerified(readmeNamingService, optionsService, fileWriterService, readmeService, mergeService)
    }

    companion object {
        @TempDir
        lateinit var tempDirectory: Path
    }
}