package org.jesperancinha.parser.projectsigner.service

import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser
import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser.ReadmeNamingParserBuilder
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import picocli.CommandLine

@Service
@Profile("dev", "prod", "test")
open class OptionsService {
    var projectSignerOptions: ProjectSignerOptions? = null
    var commonNamingParser: ReadmeNamingParserBuilder? = null

    fun processOptions(args: Array<String?>): ProjectSignerOptions {
        val projectSignerOptions = ProjectSignerOptions()
        CommandLine(projectSignerOptions).parseArgs(*args)
        this.projectSignerOptions = projectSignerOptions
        commonNamingParser = ReadmeNamingParser.builder()
            .templateLocation(this.projectSignerOptions!!.templateLocation)
            .isNoEmpty(this.projectSignerOptions != null)
        return projectSignerOptions
    }
}