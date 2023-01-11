package org.jesperancinha.parser.projectsigner.service

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.apache.commons.io.IOUtils
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptionsTest.Companion.ROOT_DIRECTORY
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptionsTest.Companion.TEMPLATE_LOCATION_README_MD
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.io.IOException
import java.net.URISyntaxException
import java.nio.charset.Charset.*
import java.nio.file.Path
import java.util.*

@SpringBootTest(args = [TEMPLATE_LOCATION_README_MD, ROOT_DIRECTORY])
@ActiveProfiles("test")
internal class ReadmeNamingServiceIT @Autowired constructor(
    private val namingService: ReadmeNamingService,
    private val optionsService: OptionsServiceMock
) {
    @BeforeEach
    fun setUp() {
        optionsService.processOptions()
        optionsService.setNoEmptyDown()

        logger.info(optionsService.projectSignerOptions?.rootDirectory.toString())
    }

    @Test
    @Throws(IOException::class)
    fun testBuildReadmeSamePath() {
        val path = Path.of(optionsService.projectSignerOptions?.templateLocation.toString())
        val inputStream = namingService.buildReadmeStream(path)
        inputStream.shouldBeNull()
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamNothing() {
        val path = Path.of(Objects.requireNonNull(javaClass.getResource("/directory2NoReadme/noProject2")).toURI())
        val inputStream = namingService.buildReadmeStream(path)
        inputStream.shouldBeNull()
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamMixMavenAndNPMFlagOn() {
        optionsService.setNoEmptyUp()
        val path =
            Path.of(Objects.requireNonNull(javaClass.getResource("/directory2NoReadme/project3MavenAndNPM")).toURI())
        val inputStream = namingService.buildReadmeStream(path)
        inputStream.shouldNotBeNull()
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamMixMavenAndNPM() {
        val path =
            Path.of(Objects.requireNonNull(javaClass.getResource("/directory2NoReadme/project3MavenAndNPM")).toURI())
        val inputStream = namingService.buildReadmeStream(path)
        inputStream.shouldNotBeNull()
        val result: String = IOUtils.toString(inputStream, defaultCharset())
        result shouldBe "# This is a test project"
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamMavenName() {
        val path = Path.of(Objects.requireNonNull(javaClass.getResource("/directory2NoReadme/project1Maven")).toURI())
        val inputStream = namingService.buildReadmeStream(path)
        inputStream.shouldNotBeNull()
        val result: String = IOUtils.toString(inputStream, defaultCharset())
        result shouldBe "# This is a test project"
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamMavenNoName() {
        val path =
            Path.of(Objects.requireNonNull(javaClass.getResource("/directory2NoReadme/project1MavenNoName")).toURI())
        val inputStream = namingService.buildReadmeStream(path)
        inputStream.shouldNotBeNull()
        val result: String = IOUtils.toString(inputStream, defaultCharset())
        result shouldBe "# ProjectMavenArtifact"
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamNPM() {
        val path = Path.of(Objects.requireNonNull(javaClass.getResource("/directory2NoReadme/project2NPM")).toURI())
        val inputStream = namingService.buildReadmeStream(path)

        inputStream.shouldNotBeNull()
        val result: String = IOUtils.toString(inputStream, defaultCharset())
        result shouldBe "# npm-project"
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamGradle() {
        val path = Path.of(Objects.requireNonNull(javaClass.getResource("/directory2NoReadme/project4Gradle")).toURI())
        val inputStream = namingService.buildReadmeStream(path)
        inputStream.shouldNotBeNull()
        val result: String = IOUtils.toString(inputStream, defaultCharset())
        result shouldBe "# project4Gradle"
    }

    @Test
    @Throws(URISyntaxException::class, IOException::class)
    fun testBuildReadmeStreamSBT() {
        val path = Path.of(Objects.requireNonNull(javaClass.getResource("/directory2NoReadme/project5Sbt")).toURI())
        val inputStream = namingService.buildReadmeStream(path)
        inputStream.shouldNotBeNull()
        val result: String = IOUtils.toString(inputStream, defaultCharset())
        result shouldBe "# sbt-project"
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ReadmeNamingServiceIT::class.java)
    }
}