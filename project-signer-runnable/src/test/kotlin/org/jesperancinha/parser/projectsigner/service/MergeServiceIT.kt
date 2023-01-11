package org.jesperancinha.parser.projectsigner.service

import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions.assertThat
import org.jesperancinha.parser.markdowner.model.Paragraphs
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptionsTest.Companion.ROOT_DIRECTORY
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptionsTest.Companion.TEMPLATE_LOCATION_README_MD
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(args = [TEMPLATE_LOCATION_README_MD, ROOT_DIRECTORY])
@ActiveProfiles("test")
private class MergeServiceIT {
    @Autowired
    private val mergeService: MergeService? = null

    @BeforeEach
    fun setUp() {
        System.getProperty("file.encoding") shouldBe "UTF-8"
    }

    @Test
    fun mergeDocumentWithFooterTemplate() {
        val testReadme = "Readme text!"
        val paragraphsBuilder = Paragraphs()
        paragraphsBuilder.withTagParagraph("## tag", "This is a test paragraph")
        paragraphsBuilder.withTagParagraph("## ta", "This is a test2 paragraph")
        val testParagraphs: Paragraphs = paragraphsBuilder
        val result = mergeService!!.mergeDocumentWithFooterTemplate(testReadme, testParagraphs)
        assertThat(result).isEqualTo("Readme text!\n\n## tag\nThis is a test paragraph\n\n## ta\nThis is a test2 paragraph\n")
    }

    @Test
    fun mergeDocumentWithFooterTemplateDifferentOrder() {
        val testReadme = "Readme text!"
        val paragraphsBuilder = Paragraphs()
        paragraphsBuilder.withTagParagraph("## ta", "This is a test2 paragraph")
        paragraphsBuilder.withTagParagraph("## tag", "This is a test paragraph")
        val testParagraphs: Paragraphs = paragraphsBuilder
        val result = mergeService!!.mergeDocumentWithFooterTemplate(testReadme, testParagraphs)
        assertThat(result).isEqualTo("Readme text!\n\n## ta\nThis is a test2 paragraph\n\n## tag\nThis is a test paragraph\n")
    }
}