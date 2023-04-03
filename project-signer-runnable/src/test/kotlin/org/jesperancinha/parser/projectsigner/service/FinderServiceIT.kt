package org.jesperancinha.parser.projectsigner.service

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptionsTest.Companion.ROOT_DIRECTORY
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

@SpringBootTest(args = [TEMPLATE_LOCATION_README_MD, ROOT_DIRECTORY])
@ActiveProfiles("test")
internal class FinderServiceIT @Autowired constructor(
    private val finderService: FinderService
) {

    @BeforeEach
    @Throws(IOException::class)
    fun setUp() {
        System.getProperty("file.encoding") shouldBe "UTF-8"
        val resource = javaClass.getResource("/.")
        resource.shouldNotBeNull()
        copyFolder(Path.of(resource.path), tempDirectory)
    }

    @Test
    @Throws(IOException::class)
    fun testIterateThroughFilesAndFolders() {
        val techUsedCommonText =
            "\n## Technologies used\n\nPlease check the [TechStack.md](TechStack.md) file for details.\n"
        tempDirectory.shouldNotBeNull()
        finderService.iterateThroughFilesAndFolders(tempDirectory!!)

        getFileContent("directory1/subDirectory1/Readme.md") shouldBe "# label1\n\n# label2\n\n# label3\n\n## License\n\nThis is one One\n\n## About me\n\nThis is two Two\n"
        getFileContent("directory1/subDirectory1/TechStack.md").shouldBeNull()

        getFileContent("directory1/Readme.md") shouldBe "# Spot On Project - The Emotional\n$techUsedCommonText\n## label1\n\n### label2\n\n# label3\n\n## License\n\nThis is one One\n\n## About me\n\nThis is two Two\n"
        getFileContent("directory1/TechStack.md").shouldNotBeNull() shouldBe """
            # Spot On Project - The Emotional TechStack

            - [Kotlin](https://kotlinlang.org/)

            - [Spring Framework](https://spring.io/projects/spring-framework)

            - [Spring Boot](https://spring.io/projects/spring-boot)

            - [Mockk](https://mockk.io/)

            - [Kotest](https://kotest.io/)

            - [Maven](https://maven.apache.org/)
        """.trimIndent()

        getFileContent("directory1/specialCaseEmoji/TechStack.md").shouldBeNull()

        val noProject2 = getFileContent("directory2NoReadme/noProject2/Readme.md")
        noProject2.shouldBeNull()
        val project1Maven = getFileContent("directory2NoReadme/project1Maven/Readme.md")
        project1Maven shouldBe "# This is a test project\n\n## License\n\nThis is one One\n\n## About me\n\nThis is two Two\n"
        val project1MavenNoName = getFileContent("directory2NoReadme/project1MavenNoName/Readme.md")
        project1MavenNoName shouldBe "# ProjectMavenArtifact\n\n## License\n\nThis is one One\n\n## About me\n\nThis is two Two\n"
        val project2NPM = getFileContent("directory2NoReadme/project2NPM/Readme.md")
        project2NPM shouldBe "# npm-project\n\n## License\n\nThis is one One\n\n## About me\n\nThis is two Two\n"
        val project3MavenAndNPM = getFileContent("directory2NoReadme/project3MavenAndNPM/Readme.md")
        project3MavenAndNPM shouldBe "# This is a test project\n\n## License\n\nThis is one One\n\n## About me\n\nThis is two Two\n"
        val project4Gradle = getFileContent("directory2NoReadme/project4Gradle/Readme.md")
        project4Gradle shouldBe "# project4Gradle\n\n## License\n\nThis is one One\n\n## About me\n\nThis is two Two\n"
        val project5Sbt = getFileContent("directory2NoReadme/project5Sbt/Readme.md")
        project5Sbt shouldBe "# sbt-project\n\n## License\n\nThis is one One\n\n## About me\n\nThis is two Two\n"
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