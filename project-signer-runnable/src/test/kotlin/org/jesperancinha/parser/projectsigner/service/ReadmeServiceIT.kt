package org.jesperancinha.parser.projectsigner.service

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.io.IOException

@SpringBootTest(args = ["--raw-location=Readme.md", "--root-directory=/"])
@ActiveProfiles("test")
internal class ReadmeServiceIT @Autowired constructor(
    @Autowired
    private val readmeService: ReadmeService
){

    @BeforeEach
    fun setUp() {
        System.getProperty("file.encoding") shouldBe "UTF-8"
    }

    @Test
    @Throws(IOException::class)
    fun testReadDataSprippedOfTagsFile0Label1() {
        val resourceAsStream = javaClass.getResourceAsStream(DIRECTORY_0_README_MD)
        resourceAsStream.shouldNotBeNull()
        val label1 = readmeService.readDataStrippedOfTags(resourceAsStream, "label1")
        label1 shouldBe "# label3"
    }

    @Test
    @Throws(IOException::class)
    fun testReadDataSprippedOfTagsFile0Label2() {
        val resourceAsStream = javaClass.getResourceAsStream(DIRECTORY_0_README_MD)
        resourceAsStream.shouldNotBeNull()
        val label2 = readmeService.readDataStrippedOfTags(resourceAsStream, "label2")
        label2 shouldBe "## label1\n\n# label3"
    }

    @Test
    @Throws(IOException::class)
    fun testReadDataSprippedOfTagsFile0Label3() {
        val resourceAsStream = javaClass.getResourceAsStream(DIRECTORY_0_README_MD)
        resourceAsStream.shouldNotBeNull()
        val label3 = readmeService.readDataStrippedOfTags(resourceAsStream, "label3")
        label3 shouldBe ("## label1\n\n### label2")
    }

    @Test
    @Throws(IOException::class)
    fun testReadDataSprippedOfTagsFile1Label1() {
        val resourceAsStream = javaClass.getResourceAsStream(DIRECTORY_1_README_MD)
        resourceAsStream.shouldNotBeNull()
        val label1 = readmeService.readDataStrippedOfTags(resourceAsStream, "label1")
        label1 shouldBe "# label2\n\n# label3"
    }

    @Test
    @Throws(IOException::class)
    fun testReadDataSprippedOfTagsFile1Label2() {
        val resourceAsStream = javaClass.getResourceAsStream(DIRECTORY_1_README_MD)
        resourceAsStream.shouldNotBeNull()
        val label2 = readmeService.readDataStrippedOfTags(resourceAsStream, "label2")
        label2 shouldBe "# label1\n\n# label3"
    }

    @Test
    @Throws(IOException::class)
    fun testReadDataSprippedOfTagsFile1Label3() {
        val resourceAsStream = javaClass.getResourceAsStream(DIRECTORY_1_README_MD)
        resourceAsStream.shouldNotBeNull()
        val label3 = readmeService.readDataStrippedOfTags(resourceAsStream, "label3")
        label3 shouldBe "# label1\n\n# label2"
    }

    @Test
    @Throws(IOException::class)
    fun `should read special case 1 with tags stripped off`() {
        val resourceAsStream = javaClass.getResourceAsStream(DIRECTORY_1_SPECIAL_CASE_1)
        resourceAsStream.shouldNotBeNull()
        val label = readmeService.readDataStrippedOfTags(resourceAsStream, "License", "About me")
        label shouldBe "# Mancala JE"
    }

    @Test
    @Throws(IOException::class)
    fun `should read emoji data`() {
        val resourceAsStream = javaClass.getResourceAsStream(DIRECTORY_1_SPECIAL_CASE_EMOJI)
        resourceAsStream.shouldNotBeNull()
        val label = readmeService.readDataStrippedOfTags(resourceAsStream, "License", "About me")
        label shouldBe "# Note manager WebApp \uD83D\uDCBB"
    }

    companion object {
        private const val DIRECTORY_0_README_MD = "/raw/Readme.md"
        private const val DIRECTORY_1_README_MD = "/directory1/subDirectory1/Readme.md"
        private const val DIRECTORY_1_SPECIAL_CASE_1 = "/directory1/specialCase1/Readme.md"
        private const val DIRECTORY_1_SPECIAL_CASE_EMOJI = "/directory1/specialCaseEmoji/Readme.md"
    }
}