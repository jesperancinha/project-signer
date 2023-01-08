package org.jesperancinha.parser.projectsigner.service

import org.assertj.core.api.Assertions
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.jesperancinha.parser.markdowner.model.Paragraph
import org.jesperancinha.parser.markdowner.model.Paragraphs
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptionsTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.io.IOException

@SpringBootTest(args = [ProjectSignerOptionsTest.Companion.TEMPLATE_LOCATION_README_MD, ProjectSignerOptionsTest.Companion.ROOT_DIRECTORY])
@ActiveProfiles("test")
class TemplateServiceIT {
    @Autowired
    private val templateService: TemplateService? = null

    @BeforeEach
    fun setUp() {
        Assertions.assertThat(System.getProperty("file.encoding")).isEqualTo("UTF-8")
    }

    @Test
    @Throws(IOException::class)
    fun testFindAllParagraphs() {
        val allParagraphs: Paragraphs? = templateService!!.findAllParagraphs()
        assertThat(allParagraphs?.paragraphCount).isEqualTo(2)
        val license: Paragraph ?= allParagraphs?.getParagraphByTag("License")
        val aboutMe: Paragraph ?= allParagraphs?.getParagraphByTag("About me")
        assertThat(license).isNotNull
        assertThat(aboutMe).isNotNull
        assertThat(license?.text).isEqualTo("\nThis is one One")
        assertThat(aboutMe?.text).isEqualTo("\nThis is two Two")
        assertThat(allParagraphs?.paragraphCount).isEqualTo(2)
    }
}