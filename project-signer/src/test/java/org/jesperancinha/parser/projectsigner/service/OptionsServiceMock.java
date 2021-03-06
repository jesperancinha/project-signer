package org.jesperancinha.parser.projectsigner.service;

import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser;
import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser.ReadmeNamingParserBuilder;
import org.jesperancinha.parser.projectsigner.api.OptionsService;
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions;
import org.mockito.Mockito;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Service
@Profile({"test", "localtest", "default"})
public class OptionsServiceMock implements OptionsService<ProjectSignerOptions, ReadmeNamingParserBuilder> {
    private ProjectSignerOptions projectSignerOptions;
    private ReadmeNamingParserBuilder commonBuilder;

    public void setNoEmptyUp() {
        Mockito.clearInvocations(projectSignerOptions);
        when(projectSignerOptions.isNoEmpty()).thenReturn(true);
        commonBuilder = ReadmeNamingParser.builder()
                .templateLocation(this.projectSignerOptions.getTemplateLocation())
                .isNoEmpty(this.projectSignerOptions.isNoEmpty());
    }

    @Override
    public ProjectSignerOptions processOptions(final String[] args) {
        final String rootPath = getClass().getResource("/dummyDirectory").getPath();
        final String template = getClass().getResource("/Readme.md").getPath();
        final ProjectSignerOptions projectSignerOptions = mock(ProjectSignerOptions.class);
        when(projectSignerOptions.getRootDirectory()).thenReturn(Path.of(rootPath));
        when(projectSignerOptions.getTemplateLocation()).thenReturn(Path.of(template));
        when(projectSignerOptions.getTagNames()).thenReturn(new String[]{"License", "About me"});
        this.projectSignerOptions = projectSignerOptions;
        this.commonBuilder = ReadmeNamingParser.builder()
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

    public void setNoEmptyDown() {
        Mockito.clearInvocations(projectSignerOptions);
        when(projectSignerOptions.isNoEmpty()).thenReturn(false);
        commonBuilder = ReadmeNamingParser.builder()
                .templateLocation(this.projectSignerOptions.getTemplateLocation())
                .isNoEmpty(this.projectSignerOptions.isNoEmpty());
    }
}
