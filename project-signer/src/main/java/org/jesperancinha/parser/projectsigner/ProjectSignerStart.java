package org.jesperancinha.parser.projectsigner;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser;
import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser.ReadmeNamingParserBuilder;
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions;
import org.jesperancinha.parser.projectsigner.inteface.OptionsService;
import org.jesperancinha.parser.projectsigner.service.FinderServiceImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ProjectSignerStart implements ApplicationRunner {


    private final FinderServiceImpl finderServiceImpl;
    private final OptionsService<ProjectSignerOptions, ReadmeNamingParserBuilder> optionsService;

    public static void main(String[] args) {
        SpringApplication.run(ProjectSignerStart.class, args);
    }

    public ProjectSignerStart(final FinderServiceImpl finderServiceImpl, final OptionsService<ProjectSignerOptions, ReadmeNamingParserBuilder> optionsService) {
        this.finderServiceImpl = finderServiceImpl;
        this.optionsService = optionsService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final ProjectSignerOptions projectSignerOptions = optionsService.processOptions(args.getSourceArgs());
        finderServiceImpl.iterateThroughFilesAndFolders(projectSignerOptions.getRootDirectory());
    }
}
