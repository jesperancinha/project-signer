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
    private val finderService: FinderService? = null

    @Mock
    private val readmeNamingService: ReadmeNamingService? = null

    @Mock
    private val mergeService: MergeService? = null

    @Mock
    private val templateService: TemplateService? = null

    @Mock
    private val readmeService: ReadmeService? = null

    @Mock
    lateinit var optionsService: OptionsServiceMock

    @Mock
    private val fileWriterService: FileWriterService? = null

    @Mock
    private val generatorService: GeneratorSevice? = null

    @BeforeEach
    fun setUp() {
        System.getProperty("file.encoding") shouldBe "UTF-8"
    }

    @Test
    @Throws(IOException::class)
    fun testIterateThroughFilesAndFolders() {
        val mockParagraphs: Paragraphs = mock(Paragraphs::class.java)
        `when`(templateService!!.findAllParagraphs()).thenReturn(mockParagraphs)
        finderService!!.iterateThroughFilesAndFolders(tempDirectory!!)
        verify(templateService).findAllParagraphs()
        verify(generatorService)?.processReadmeFile(tempDirectory!!, mockParagraphs)
        verifyNoInteractions(readmeNamingService)
        verifyNoInteractions(optionsService)
        verifyNoInteractions(fileWriterService)
        verifyNoInteractions(readmeService)
        verifyNoInteractions(mergeService)
    }

    companion object {
        @TempDir
        var tempDirectory: Path? = null
    }
}