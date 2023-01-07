package org.jesperancinha.parser.projectsigner.service;


import lombok.val;
import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser;
import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser.ReadmeNamingParserBuilder;
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

@Service
@Profile({"dev", "prod", "test"})
public class OptionsService {

    private ProjectSignerOptions projectSignerOptions;

    private ReadmeNamingParserBuilder commonBuilder;

    public ProjectSignerOptions processOptions(final String[] args) {
        val projectSignerOptions = new ProjectSignerOptions();
        new CommandLine(projectSignerOptions).parseArgs(args);
        this.projectSignerOptions = projectSignerOptions;
        commonBuilder = ReadmeNamingParser.builder()
                .templateLocation(this.projectSignerOptions.getTemplateLocation())
                .isNoEmpty(this.projectSignerOptions.isNoEmpty());
        return projectSignerOptions;
    }

    public ProjectSignerOptions getProjectSignerOptions() {
        return projectSignerOptions;
    }

    public ReadmeNamingParserBuilder getCommonNamingParser() {
        return commonBuilder;
    }

}
