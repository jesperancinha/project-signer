package org.jesperancinha.parser.projectsigner.service

import io.kotest.matchers.shouldBe
import org.jesperancinha.parser.markdowner.model.Paragraphs
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.io.IOException
import java.nio.file.Path

@ExtendWith(MockitoExtension::class)
internal class FinderServiceTest {
    @InjectMocks
    lateinit var finderService: FinderService

    @Mock
    lateinit var readmeNamingService: ReadmeNamingService

    @Mock
    lateinit var mergeService: MergeService

    @Mock
    lateinit var templateService: TemplateService

    @Mock
    lateinit var readmeService: ReadmeService

    @Mock
    lateinit var optionsService: OptionsServiceMock

    @Mock
    lateinit var fileWriterService: FileWriterService

    @Mock
    lateinit var generatorService: GeneratorSevice

    @BeforeEach
    fun setUp() {
        System.getProperty("file.encoding") shouldBe "UTF-8"
    }

    @Test
    @Throws(IOException::class)
    fun testIterateThroughFilesAndFolders() {
        val mockParagraphs: Paragraphs = mock(Paragraphs::class.java)
        `when`(templateService.findAllParagraphs()).thenReturn(mockParagraphs)
        finderService.iterateThroughFilesAndFolders(tempDirectory)
        verify(templateService).findAllParagraphs()
        verify(generatorService)?.processReadmeFile(tempDirectory, mockParagraphs)
        verifyNoInteractions(readmeNamingService)
        verifyNoInteractions(optionsService)
        verifyNoInteractions(fileWriterService)
        verifyNoInteractions(readmeService)
        verifyNoInteractions(mergeService)
    }

    companion object {
        @TempDir
        lateinit var tempDirectory: Path
    }
}