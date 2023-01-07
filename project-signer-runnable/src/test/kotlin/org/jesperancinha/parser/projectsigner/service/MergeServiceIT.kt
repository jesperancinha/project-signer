package org.jesperancinha.parser.projectsigner.service

import org.assertj.core.api.Assertions.assertThat
import org.jesperancinha.parser.markdowner.model.Paragraphs
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptionsTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(args = [ProjectSignerOptionsTest.Companion.TEMPLATE_LOCATION_README_MD, ProjectSignerOptionsTest.Companion.ROOT_DIRECTORY])
@ActiveProfiles("test")
class MergeServiceIT {
    @Autowired
    private val mergeService: MergeService? = null
    @Test
    fun mergeDocumentWithFooterTemplate() {
        val testReadme = "Readme text!"
        val paragraphsBuilder = Paragraphs.ParagraphsBuilder()
        paragraphsBuilder.withTagParagraph("## tag", "This is a test paragraph")
        paragraphsBuilder.withTagParagraph("## ta", "This is a test2 paragraph")
        val testParagraphs: Paragraphs = paragraphsBuilder.build()
        val result = mergeService!!.mergeDocumentWithFooterTemplate(testReadme, testParagraphs)
        assertThat(result).isEqualTo("Readme text!\n\n## tag\nThis is a test paragraph\n\n## ta\nThis is a test2 paragraph\n")
    }

    @Test
    fun mergeDocumentWithFooterTemplateDifferentOrder() {
        val testReadme = "Readme text!"
        val paragraphsBuilder = Paragraphs.ParagraphsBuilder()
        paragraphsBuilder.withTagParagraph("## ta", "This is a test2 paragraph")
        paragraphsBuilder.withTagParagraph("## tag", "This is a test paragraph")
        val testParagraphs: Paragraphs = paragraphsBuilder.build()
        val result = mergeService!!.mergeDocumentWithFooterTemplate(testReadme, testParagraphs)
        assertThat(result).isEqualTo("Readme text!\n\n## ta\nThis is a test2 paragraph\n\n## tag\nThis is a test paragraph\n")
    }
}