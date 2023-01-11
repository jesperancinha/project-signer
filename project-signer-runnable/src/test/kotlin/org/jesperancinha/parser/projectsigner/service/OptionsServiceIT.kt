package org.jesperancinha.parser.projectsigner.service

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OptionsServiceIT {
    private val optionsService = OptionsService()

    @BeforeEach
    fun setUp() {
        System.getProperty("file.encoding") shouldBe "UTF-8"
    }

    @Test
    fun testProcessOptions() {
        val args = arrayOf(
            "-t",
            TEMPLATE_LOCATION,
            LICENSE,
            ABOUT_ME,
            "-d",
            ROOT_LOCATION
        )
        val projectSignerOptions: ProjectSignerOptions = optionsService.processOptions(args)
        projectSignerOptions.templateLocation.toString() shouldBe TEMPLATE_LOCATION
        projectSignerOptions.rootDirectory.toString() shouldBe ROOT_LOCATION
        projectSignerOptions.tagNames.shouldContainExactly(LICENSE, ABOUT_ME)
        projectSignerOptions.shouldNotBeNull()
    }

    @Test
    fun testProcessOptionsNE() {
        val args = arrayOf(
            "-t",
            TEMPLATE_LOCATION,
            LICENSE,
            ABOUT_ME,
            "-d",
            ROOT_LOCATION,
            "-ne"
        )
        val projectSignerOptions: ProjectSignerOptions = optionsService.processOptions(args)
        projectSignerOptions.templateLocation.toString() shouldBe TEMPLATE_LOCATION
        projectSignerOptions.rootDirectory.toString() shouldBe ROOT_LOCATION
        projectSignerOptions.tagNames.shouldContainExactly(LICENSE, ABOUT_ME)
        projectSignerOptions.shouldNotBeNull()
    }

    @Test
    fun testProcessLongOptions() {
        val args = arrayOf(
            "--template-location",
            TEMPLATE_LOCATION,
            LICENSE,
            ABOUT_ME,
            "--root-directory",
            ROOT_LOCATION
        )
        val projectSignerOptions: ProjectSignerOptions = optionsService.processOptions(args)
        projectSignerOptions.templateLocation.toString() shouldBe TEMPLATE_LOCATION
        projectSignerOptions.rootDirectory.toString() shouldBe ROOT_LOCATION
        projectSignerOptions.tagNames.shouldContainExactly(LICENSE, ABOUT_ME)
        projectSignerOptions.shouldNotBeNull()
    }

    @Test
    fun testProcessLongOptionsNE() {
        val args = arrayOf(
            "--template-location",
            TEMPLATE_LOCATION,
            LICENSE,
            ABOUT_ME,
            "--root-directory",
            ROOT_LOCATION,
            "--no-empty"
        )
        val projectSignerOptions: ProjectSignerOptions = optionsService.processOptions(args)
        projectSignerOptions.templateLocation.toString() shouldBe TEMPLATE_LOCATION
        projectSignerOptions.rootDirectory.toString() shouldBe ROOT_LOCATION
        projectSignerOptions.tagNames.shouldContainExactly(LICENSE, ABOUT_ME)
        projectSignerOptions.shouldNotBeNull()
    }

    companion object {
        private const val TEMPLATE_LOCATION = "../project-signer-templates/Readme.md"
        private const val ROOT_LOCATION = "../.."
        private const val LICENSE = "License"
        private const val ABOUT_ME = "About me"
    }
}