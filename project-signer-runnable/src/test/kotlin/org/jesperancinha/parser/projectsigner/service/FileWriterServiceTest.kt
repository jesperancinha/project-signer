package org.jesperancinha.parser.projectsigner.service

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.apache.commons.io.IOUtils.toString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.file.Path

@ExtendWith(MockKExtension::class)
internal class FileWriterServiceTest {
    @InjectMockKs
    private lateinit var fileWriterService: FileWriterService

    @BeforeEach
    fun setUp() {
        System.getProperty("file.encoding") shouldBe "UTF-8"
    }

    @Test
    @Throws(IOException::class)
    fun testExportReadmeFile() {
        tempDirectory.shouldNotBeNull()
        fileWriterService.exportReadmeFile(tempDirectory, README_SIGNED_FILE)
        val resultPath = tempDirectory.resolve("Readme.md")
        val resultFile: File = resultPath.toFile()
        val fileReader = InputStreamReader(FileInputStream(resultFile))
        val result = toString(fileReader)
        result shouldBe README_SIGNED_FILE
    }

    companion object {
        private const val README_SIGNED_FILE = "Readme signed file"

        @TempDir
        lateinit var tempDirectory: Path
    }
}