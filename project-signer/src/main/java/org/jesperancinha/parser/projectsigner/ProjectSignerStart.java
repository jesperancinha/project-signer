package org.jesperancinha.parser.projectsigner;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser.ReadmeNamingParserBuilder;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions;
import org.jesperancinha.parser.projectsigner.inteface.FileWriterService;
import org.jesperancinha.parser.projectsigner.inteface.OptionsService;
import org.jesperancinha.parser.projectsigner.inteface.ReadmeService;
import org.jesperancinha.parser.projectsigner.model.ProjectData;
import org.jesperancinha.parser.projectsigner.service.FinderServiceImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ProjectSignerStart implements ApplicationRunner {


    private final FinderServiceImpl finderServiceImpl;
    private final FileWriterService<ProjectData> fileWriterService;
    private final OptionsService<ProjectSignerOptions, ReadmeNamingParserBuilder> optionsService;
    private final ReadmeService<Paragraphs, ProjectData> readmeService;

    public static void main(String[] args) {
        SpringApplication.run(ProjectSignerStart.class, args);
    }

    public ProjectSignerStart(final FinderServiceImpl finderServiceImpl,
                              final FileWriterService<ProjectData> fileWriterService,
                              final OptionsService<ProjectSignerOptions, ReadmeNamingParserBuilder> optionsService,
                              final ReadmeService<Paragraphs, ProjectData> readmeService) {
        this.finderServiceImpl = finderServiceImpl;
        this.fileWriterService = fileWriterService;
        this.optionsService = optionsService;
        this.readmeService = readmeService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final ProjectSignerOptions projectSignerOptions = optionsService.processOptions(args.getSourceArgs());
        finderServiceImpl.iterateThroughFilesAndFolders(projectSignerOptions.getRootDirectory());
        fileWriterService.exportReportFile(optionsService.getProjectSignerOptions().getReportLocation()
                , readmeService.getAllProjectData());

    }
}
