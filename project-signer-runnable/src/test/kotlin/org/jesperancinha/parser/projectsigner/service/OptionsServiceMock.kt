package org.jesperancinha.parser.projectsigner.service

import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptionsTest
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.nio.file.Path
import java.util.*

@Service
@Profile("test", "localtest", "default")
open class OptionsServiceMock (
    var projectSignerOptions: ProjectSignerOptions?= null,
    private var commonBuilder: ReadmeNamingParser?= null
) {

    fun setNoEmptyUp() {
        projectSignerOptions?.noEmpty = true
        commonBuilder = projectSignerOptions?.templateLocation?.let {
            ReadmeNamingParser(
                templateLocation = it,
                isNoEmpty = (projectSignerOptions?.noEmpty == true)
            )
        }

    }

    fun processOptions(): ProjectSignerOptions {
        val rootPath = Objects.requireNonNull(javaClass.getResource("/dummyDirectory")).path
        val projectSignerOptions = ProjectSignerOptions()
        projectSignerOptions.rootDirectory= Path.of(rootPath)
        projectSignerOptions.templateLocation=Path.of(ProjectSignerOptionsTest.README_MD)
        projectSignerOptions.tagNames=arrayOf("License", "About me")
        this.projectSignerOptions = projectSignerOptions
        commonBuilder = this.projectSignerOptions?.noEmpty?.let {
            this.projectSignerOptions!!.templateLocation?.let { it1 ->
                ReadmeNamingParser(
                    templateLocation = it1,
                    isNoEmpty = it
                )
            }
        }
        return projectSignerOptions
    }

    fun setNoEmptyDown() {
        commonBuilder = projectSignerOptions?.noEmpty?.let {
            projectSignerOptions?.templateLocation?.let { it1 ->
                ReadmeNamingParser(
                    templateLocation = it1,
                    isNoEmpty = it
                )
            }
        }
    }
}