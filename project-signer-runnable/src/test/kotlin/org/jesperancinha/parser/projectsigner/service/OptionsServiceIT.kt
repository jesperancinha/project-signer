package org.jesperancinha.parser.projectsigner.service

import org.assertj.core.api.Assertions.assertThat
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OptionsServiceIT {
    private val optionsService = OptionsService()

    @BeforeEach
    fun setUp() {
        assertThat(System.getProperty("file.encoding")).isEqualTo("UTF-8")
    }

    @Test
    fun testProcessOptions() {
        val args = arrayOf<String>(
            "-t",
            TEMPLATE_LOCATION,
            LICENSE,
            ABOUT_ME,
            "-d",
            ROOT_LOCATION
        )
        val projectSignerOptions: ProjectSignerOptions = optionsService.processOptions(args)
        assertThat(projectSignerOptions.templateLocation.toString()).isEqualTo(TEMPLATE_LOCATION)
        assertThat(projectSignerOptions.rootDirectory.toString()).isEqualTo(ROOT_LOCATION)
        assertThat(projectSignerOptions.tagNames).contains(LICENSE, ABOUT_ME)
        assertThat(projectSignerOptions).isNotNull
    }

    @Test
    fun testProcessOptionsNE() {
        val args = arrayOf<String>(
            "-t",
            TEMPLATE_LOCATION,
            LICENSE,
            ABOUT_ME,
            "-d",
            ROOT_LOCATION,
            "-ne"
        )
        val projectSignerOptions: ProjectSignerOptions = optionsService.processOptions(args)
        assertThat(projectSignerOptions.templateLocation.toString()).isEqualTo(TEMPLATE_LOCATION)
        assertThat(projectSignerOptions.rootDirectory.toString()).isEqualTo(ROOT_LOCATION)
        assertThat(projectSignerOptions.tagNames).contains(LICENSE, ABOUT_ME)
        assertThat(projectSignerOptions).isNotNull
    }

    @Test
    fun testProcessLongOptions() {
        val args = arrayOf<String>(
            "--template-location",
            TEMPLATE_LOCATION,
            LICENSE,
            ABOUT_ME,
            "--root-directory",
            ROOT_LOCATION
        )
        val projectSignerOptions: ProjectSignerOptions = optionsService.processOptions(args)
        assertThat(projectSignerOptions.templateLocation.toString()).isEqualTo(TEMPLATE_LOCATION)
        assertThat(projectSignerOptions.rootDirectory.toString()).isEqualTo(ROOT_LOCATION)
        assertThat(projectSignerOptions.tagNames).contains(LICENSE, ABOUT_ME)
        assertThat(projectSignerOptions).isNotNull
    }

    @Test
    fun testProcessLongOptionsNE() {
        val args = arrayOf<String>(
            "--template-location",
            TEMPLATE_LOCATION,
            LICENSE,
            ABOUT_ME,
            "--root-directory",
            ROOT_LOCATION,
            "--no-empty"
        )
        val projectSignerOptions: ProjectSignerOptions = optionsService.processOptions(args)
        assertThat(projectSignerOptions.templateLocation.toString()).isEqualTo(TEMPLATE_LOCATION)
        assertThat(projectSignerOptions.rootDirectory.toString()).isEqualTo(ROOT_LOCATION)
        assertThat(projectSignerOptions.tagNames).contains(LICENSE, ABOUT_ME)
        assertThat(projectSignerOptions).isNotNull
    }

    companion object {
        private const val TEMPLATE_LOCATION = "../project-signer-templates/Readme.md"
        private const val ROOT_LOCATION = "../.."
        private const val LICENSE = "License"
        private const val ABOUT_ME = "About me"
    }
}