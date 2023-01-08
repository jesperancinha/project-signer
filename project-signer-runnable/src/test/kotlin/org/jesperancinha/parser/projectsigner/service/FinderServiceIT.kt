package org.jesperancinha.parser.projectsigner.service

import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.assertj.core.api.Assertions.assertThat
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptionsTest
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptionsTest.Companion.TEMPLATE_LOCATION_README_MD
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

@SpringBootTest(args = [TEMPLATE_LOCATION_README_MD, ProjectSignerOptionsTest.Companion.ROOT_DIRECTORY])
@ActiveProfiles("test")
internal class FinderServiceIT {
    @Autowired
    private val finderService: FinderService? = null
    @BeforeEach
    @Throws(IOException::class)
    fun setUp() {
        assertThat(System.getProperty("file.encoding")).isEqualTo("UTF-8")
        val resource = javaClass.getResource("/.")
        copyFolder(Path.of(resource.path), tempDirectory)
    }

    @Test
    @Throws(IOException::class)
    fun testIterateThroughFilesAndFolders() {
        assertThat(tempDirectory).isNotNull
        finderService!!.iterateThroughFilesAndFolders(tempDirectory!!)
        val subDirectory1 = getFileContent("directory1/subDirectory1/Readme.md")
        assertThat(subDirectory1).isEqualTo("# label1\n\n# label2\n\n# label3\n\n## License\n\nThis is one One\n\n## About me\n\nThis is two Two\n")
        val directory1 = getFileContent("directory1/Readme.md")
        assertThat(directory1).isEqualTo("## label1\n\n### label2\n\n# label3\n\n## License\n\nThis is one One\n\n## About me\n\nThis is two Two\n")
        val noProject2 = getFileContent("directory2NoReadme/noProject2/Readme.md")
        assertThat(noProject2).isNull()
        val project1Maven = getFileContent("directory2NoReadme/project1Maven/Readme.md")
        assertThat(project1Maven).isEqualTo("# This is a test project\n\n## License\n\nThis is one One\n\n## About me\n\nThis is two Two\n")
        val project1MavenNoName = getFileContent("directory2NoReadme/project1MavenNoName/Readme.md")
        assertThat(project1MavenNoName).isEqualTo("# ProjectMavenArtifact\n\n## License\n\nThis is one One\n\n## About me\n\nThis is two Two\n")
        val project2NPM = getFileContent("directory2NoReadme/project2NPM/Readme.md")
        assertThat(project2NPM).isEqualTo("# npm-project\n\n## License\n\nThis is one One\n\n## About me\n\nThis is two Two\n")
        val project3MavenAndNPM = getFileContent("directory2NoReadme/project3MavenAndNPM/Readme.md")
        assertThat(project3MavenAndNPM).isEqualTo("# This is a test project\n\n## License\n\nThis is one One\n\n## About me\n\nThis is two Two\n")
        val project4Gradle = getFileContent("directory2NoReadme/project4Gradle/Readme.md")
        assertThat(project4Gradle).isEqualTo("# project4Gradle\n\n## License\n\nThis is one One\n\n## About me\n\nThis is two Two\n")
        val project5Sbt = getFileContent("directory2NoReadme/project5Sbt/Readme.md")
        assertThat(project5Sbt).isEqualTo("# sbt-project\n\n## License\n\nThis is one One\n\n## About me\n\nThis is two Two\n")
    }

    @Throws(IOException::class)
    private fun getFileContent(readmeLocation: String): String? {
        val file: File = tempDirectory!!.resolve(readmeLocation).toFile()
        return if (file.exists()) {
            IOUtils.toString(FileInputStream(file), Charset.defaultCharset())
        } else null
    }

    @Throws(IOException::class)
    private fun copyFolder(src: Path, dest: Path?) {
        Files.walk(src)
            .forEach { source: Path -> copy(source, dest!!.resolve(src.relativize(source))) }
    }

    private fun copy(src: Path, dest: Path) {
        try {
            FileUtils.deleteDirectory(dest.toFile())
            Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING)
        } catch (e: Exception) {
            logger.error("Error found copying {} to {}", src, dest, e)
        }
    }

    companion object {
        @TempDir
        var tempDirectory: Path? = null
        val logger: Logger = LoggerFactory.getLogger(FinderServiceIT::class.java)
    }
}