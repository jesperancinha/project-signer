package org.jesperancinha.parser.projectsigner.service

import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import picocli.CommandLine

@Service
@Profile("dev", "prod", "test")
open class OptionsService {
    var projectSignerOptions: ProjectSignerOptions? = null
    var commonNamingParser: ReadmeNamingParser? = null

    fun processOptions(args: Array<String>): ProjectSignerOptions = ProjectSignerOptions().apply {
        CommandLine(this).parseArgs(*args)
        projectSignerOptions = this
        commonNamingParser = projectSignerOptions?.templateLocation?.let {
            ReadmeNamingParser(
                templateLocation = it,
                isNoEmpty = true
            )
        }
    }
}