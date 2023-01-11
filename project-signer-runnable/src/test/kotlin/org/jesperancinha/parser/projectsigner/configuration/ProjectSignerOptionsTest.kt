package org.jesperancinha.parser.projectsigner.configuration

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import picocli.CommandLine

class ProjectSignerOptionsTest {

    @BeforeEach
    fun setUp() {
        System.getProperty("file.encoding") shouldBe "UTF-8"
    }

    @Test
    fun `should parse generic options`() {
        val args = arrayOf("-t", TEST_LOCATION, TEST_LABEL_1, TEST_LABEL_2, "-d", TEST_ROOT)
        val projectSignerOptions = ProjectSignerOptions()
        CommandLine(projectSignerOptions).parseArgs(*args)
        projectSignerOptions.shouldNotBeNull()
        projectSignerOptions.templateLocation.toString() shouldBe TEST_LOCATION
        projectSignerOptions.tagNames.shouldContainExactly(TEST_LABEL_1, TEST_LABEL_2)
        projectSignerOptions.rootDirectory.toString() shouldBe TEST_ROOT
    }

    @Test
    fun `should parse options with no empty activated`() {
        val args = arrayOf("-t", TEST_LOCATION, TEST_LABEL_1, TEST_LABEL_2, "-d", TEST_ROOT, "-ne")
        val projectSignerOptions = ProjectSignerOptions()
        CommandLine(projectSignerOptions).parseArgs(*args)
        projectSignerOptions.shouldNotBeNull()
        projectSignerOptions.templateLocation.toString() shouldBe TEST_LOCATION
        projectSignerOptions.tagNames.shouldContainExactly(TEST_LABEL_1, TEST_LABEL_2)
        projectSignerOptions.rootDirectory.toString() shouldBe TEST_ROOT
    }

    companion object {
        private const val TEST_LOCATION = "location"
        private const val TEST_LABEL_1 = "label1"
        private const val TEST_LABEL_2 = "label2"
        private const val TEST_ROOT = "root"
        const val README_MD = "target/test-classes/Readme.md"
        const val TEMPLATE_LOCATION_README_MD = "--template-location=" + README_MD
        const val ROOT_DIRECTORY = "--root-directory=/target/test-classes/"
    }
}


