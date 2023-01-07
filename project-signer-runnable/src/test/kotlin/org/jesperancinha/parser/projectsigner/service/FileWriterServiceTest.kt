package org.jesperancinha.parser.projectsigner.service

import org.apache.commons.io.IOUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.file.Path

@ExtendWith(MockitoExtension::class)
internal class FileWriterServiceTest {
    @InjectMocks
    private val fileWriterService: FileWriterService? = null

    @Test
    @Throws(IOException::class)
    fun testExportReadmeFile() {
        fileWriterService!!.exportReadmeFile(tempDirectory!!, README_SIGNED_FILE)
        val resultPath = tempDirectory!!.resolve("Readme.md")
        val resultFile: File = resultPath.toFile()
        val fileReader = InputStreamReader(FileInputStream(resultFile))
        val result = IOUtils.toString(fileReader)
        assertThat(result).isEqualTo(README_SIGNED_FILE)
    }

    companion object {
        private const val README_SIGNED_FILE = "Readme signed file"

        @TempDir
        var tempDirectory: Path? = null
    }
}