package org.jesperancinha.parser.projectsigner.configuration

import picocli.CommandLine.Option
import picocli.CommandLine.Parameters
import java.nio.file.Path

open class ProjectSignerOptions {
    @Option(
        names = ["-t", "--raw-location"],
        paramLabel = "Template location",
        description = ["Location of the signing raw"],
        required = true
    )
    var templateLocation: Path? = null

    @Option(
        names = ["-l", "--license-location"],
        paramLabel = "License location",
        description = ["License location"]
    )
    var licenseLocations: String? = null

    @Option(
        names = ["-r", "--report-location"],
        paramLabel = "Report location",
        description = ["Destination of report files"],
        defaultValue = "../project-signer-quality"
    )
    var reportLocation: String? = null

    @Parameters(
        paramLabel = "Start tags",
        description = ["Start of paragraph replace. This will remove all paragraphs with these names. It only applies to rules with '#' title markdown notation"]
    )
    var tagNames: Array<String> =  emptyArray()

    @Option(
        names = ["-d", "--root-directory"],
        paramLabel = "Root directory",
        description = ["Where to start searching for sub Readme.md files and/or empty projects"]
    )
    var rootDirectory: Path? = null

    @Option(
        names = ["-ne", "--no-empty"],
        paramLabel = "No Empty",
        description = ["If set, it does not create empty signed Readme.md files"],
        defaultValue = "false"
    )
    var noEmpty:Boolean = false


    @Option(
        names = ["-tr", "--raw-redirect-location"],
        paramLabel = "Redirect Template location",
        description = ["Location of the redirect template raw"],
        required = false
    )
    var redirectTemplateLocation: Path? = null

    fun getLicenseLocations(): Array<Path>? = licenseLocations?.split(",")?.mapNotNull { Path.of(it) }?.toTypedArray()
}