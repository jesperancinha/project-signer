package org.jesperancinha.parser.projectsigner;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.api.ReadmeService;
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions;
import org.jesperancinha.parser.projectsigner.model.ProjectData;
import org.jesperancinha.parser.projectsigner.service.FileWriterService;
import org.jesperancinha.parser.projectsigner.service.FinderServiceImpl;
import org.jesperancinha.parser.projectsigner.service.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class ProjectSignerStart implements ApplicationRunner {

    private final Environment environment;
    private final FinderServiceImpl finderService;
    private final FileWriterService fileWriterService;
    private final OptionsService optionsService;
    private final ReadmeService<Paragraphs, ProjectData> readmeService;

    public static void main(String[] args) {
        SpringApplication.run(ProjectSignerStart.class, args);
    }

    public ProjectSignerStart(final FinderServiceImpl finderService,
                              final FileWriterService fileWriterService,
                              final OptionsService optionsService,
                              final ReadmeService<Paragraphs, ProjectData> readmeService, Environment environment) {
        this.finderService = finderService;
        this.fileWriterService = fileWriterService;
        this.optionsService = optionsService;
        this.readmeService = readmeService;
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(environment.getActiveProfiles().length > 0) {
            final ProjectSignerOptions projectSignerOptions = optionsService.processOptions(args.getSourceArgs());
            finderService.iterateThroughFilesAndFolders(projectSignerOptions.getRootDirectory());
            fileWriterService.exportReportFile(
                    optionsService.getProjectSignerOptions().getReportLocation(), readmeService.getAllProjectData());
        }
    }
}
