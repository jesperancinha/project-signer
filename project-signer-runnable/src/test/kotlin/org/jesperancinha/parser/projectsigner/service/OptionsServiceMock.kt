package org.jesperancinha.parser.projectsigner.service

import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser
import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser.ReadmeNamingParserBuilder
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptionsTest
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.nio.file.Path
import java.util.*

@Service
@Profile("test", "localtest", "default")
open class OptionsServiceMock (
    var projectSignerOptions: ProjectSignerOptions?= null,
    private var commonBuilder: ReadmeNamingParserBuilder?= null
) {

    fun setNoEmptyUp() {
        projectSignerOptions?.noEmpty = true
        commonBuilder = ReadmeNamingParser.builder()
            .templateLocation(projectSignerOptions?.templateLocation)
            .isNoEmpty(projectSignerOptions?.noEmpty == true)
    }

    fun processOptions(): ProjectSignerOptions {
        val rootPath = Objects.requireNonNull(javaClass.getResource("/dummyDirectory")).path
        val projectSignerOptions = ProjectSignerOptions()
        projectSignerOptions.rootDirectory= Path.of(rootPath)
        projectSignerOptions.templateLocation=Path.of(ProjectSignerOptionsTest.README_MD)
        projectSignerOptions.tagNames=arrayOf("License", "About me")
        this.projectSignerOptions = projectSignerOptions
        commonBuilder = this.projectSignerOptions?.noEmpty?.let {
            ReadmeNamingParser.builder()
                .templateLocation(this.projectSignerOptions!!.templateLocation)
                .isNoEmpty(it)
        }
        return projectSignerOptions
    }
    val commonNamingParser: ReadmeNamingParserBuilder?
        get() = commonBuilder

    fun setNoEmptyDown() {
        commonBuilder = projectSignerOptions?.noEmpty?.let {
            ReadmeNamingParser.builder()
                .templateLocation(projectSignerOptions?.templateLocation)
                .isNoEmpty(it)
        }
    }
}