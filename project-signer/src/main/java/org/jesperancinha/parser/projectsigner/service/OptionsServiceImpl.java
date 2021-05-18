package org.jesperancinha.parser.projectsigner.service;


import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser;
import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser.ReadmeNamingParserBuilder;
import org.jesperancinha.parser.projectsigner.api.OptionsService;
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

@Service
@Profile({"dev", "prod"})
public class OptionsServiceImpl implements OptionsService<ProjectSignerOptions, ReadmeNamingParserBuilder> {

    private ProjectSignerOptions projectSignerOptions;

    private ReadmeNamingParserBuilder commonBuilder;

    @Override
    public ProjectSignerOptions processOptions(final String[] args) {
        final ProjectSignerOptions projectSignerOptions = new ProjectSignerOptions();
        new CommandLine(projectSignerOptions).parseArgs(args);
        this.projectSignerOptions = projectSignerOptions;
        commonBuilder = ReadmeNamingParser.builder()
                .templateLocation(this.projectSignerOptions.getTemplateLocation())
                .isNoEmpty(this.projectSignerOptions.isNoEmpty());
        return projectSignerOptions;
    }

    @Override
    public ProjectSignerOptions getProjectSignerOptions() {
        return projectSignerOptions;
    }

    @Override
    public ReadmeNamingParserBuilder getCommonNamingParser() {
        return commonBuilder;
    }

}
