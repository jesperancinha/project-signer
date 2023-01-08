package org.jesperancinha.parser.projectsigner.configuration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import picocli.CommandLine

class ProjectSignerOptionsTest {

    @BeforeEach
    fun setUp() {
        assertThat(System.getProperty("file.encoding")).isEqualTo("UTF-8")
    }

    @Test
    fun parseOptions() {
        val args = arrayOf("-t", TEST_LOCATION, TEST_LABEL_1, TEST_LABEL_2, "-d", TEST_ROOT)
        val projectSignerOptions = ProjectSignerOptions()
        CommandLine(projectSignerOptions).parseArgs(*args)
        assertThat(projectSignerOptions.templateLocation.toString()).isEqualTo(TEST_LOCATION)
        assertThat(projectSignerOptions.tagNames).containsOnly(TEST_LABEL_1, TEST_LABEL_2)
        assertThat(projectSignerOptions.rootDirectory.toString()).isEqualTo(TEST_ROOT)
        assertThat(projectSignerOptions).isNotNull
    }

    @Test
    fun parseOptionsWithNoEmpty() {
        val args = arrayOf("-t", TEST_LOCATION, TEST_LABEL_1, TEST_LABEL_2, "-d", TEST_ROOT, "-ne")
        val projectSignerOptions = ProjectSignerOptions()
        CommandLine(projectSignerOptions).parseArgs(*args)
        assertThat(projectSignerOptions.templateLocation.toString()).isEqualTo(TEST_LOCATION)
        assertThat(projectSignerOptions.tagNames).containsOnly(TEST_LABEL_1, TEST_LABEL_2)
        assertThat(projectSignerOptions.rootDirectory.toString()).isEqualTo(TEST_ROOT)
        assertThat(projectSignerOptions).isNotNull
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


