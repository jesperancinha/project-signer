package org.jesperancinha.parser.projectsigner.service

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.jesperancinha.parser.markdowner.model.Paragraph
import org.jesperancinha.parser.markdowner.model.Paragraphs
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptionsTest.Companion.ROOT_DIRECTORY
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptionsTest.Companion.TEMPLATE_LOCATION_README_MD
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.io.IOException

@SpringBootTest(args = [TEMPLATE_LOCATION_README_MD, ROOT_DIRECTORY])
@ActiveProfiles("test")
class TemplateServiceIT @Autowired constructor(
    private val templateService: TemplateService
) {


    @BeforeEach
    fun setUp() {
        System.getProperty("file.encoding") shouldBe "UTF-8"
    }

    @Test
    @Throws(IOException::class)
    fun testFindAllParagraphs() {
        val allParagraphs: Paragraphs = templateService.findAllParagraphs()
        allParagraphs.getParagraphCount() shouldBe 2
        val license: Paragraph = allParagraphs.getParagraphByTag("License").shouldNotBeNull()
        val aboutMe: Paragraph = allParagraphs.getParagraphByTag("About me").shouldNotBeNull()
        license.text shouldBe "\nThis is one One"
        aboutMe.text shouldBe "\nThis is two Two"
    }
}