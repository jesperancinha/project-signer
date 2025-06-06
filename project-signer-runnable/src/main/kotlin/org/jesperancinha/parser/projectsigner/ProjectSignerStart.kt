package org.jesperancinha.parser.projectsigner

import org.jesperancinha.parser.projectsigner.service.FileWriterService
import org.jesperancinha.parser.projectsigner.service.FinderService
import org.jesperancinha.parser.projectsigner.service.OptionsService
import org.jesperancinha.parser.projectsigner.service.ReadmeService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.core.env.Environment
import java.nio.file.Path
import kotlin.Throws

@SpringBootApplication
open class ProjectSignerStart(
    private val finderService: FinderService,
    private val fileWriterService: FileWriterService,
    private val optionsService: OptionsService,
    private val readmeService: ReadmeService,
    private val environment: Environment
) : ApplicationRunner {
    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        System.setProperty("file.encoding","UTF-8")
        if (environment.activeProfiles.isNotEmpty()) {
            val projectSignerOptions = optionsService.processOptions(args.sourceArgs)
            finderService.iterateThroughFilesAndFolders(projectSignerOptions.rootDirectory ?: throw RuntimeException("Root directory needs to be configured!"))
            val profiles = listOf(*environment.activeProfiles)
            if (profiles.contains("prod") || profiles.contains("dev")) {
                optionsService.projectSignerOptions?.reportLocation?.let { Path.of(it) ?: throw RuntimeException("Report location needs to be configured") }
                    ?.let {
                        fileWriterService.exportReportFiles(
                            it, readmeService.allProjectData
                        )
                    }
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ProjectSignerStart::class.java, *args)
        }
    }
}