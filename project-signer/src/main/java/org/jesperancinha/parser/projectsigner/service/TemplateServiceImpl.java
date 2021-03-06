package org.jesperancinha.parser.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser.ReadmeNamingParserBuilder;
import org.jesperancinha.parser.markdowner.helper.TemplateParserHelper;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.api.OptionsService;
import org.jesperancinha.parser.projectsigner.api.TemplateService;
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A markdown template service to handle markdown texts
 */
@Service
@Slf4j
public class TemplateServiceImpl implements TemplateService<Paragraphs> {

    private final OptionsService<ProjectSignerOptions, ReadmeNamingParserBuilder> optionsService;

    public TemplateServiceImpl(OptionsService<ProjectSignerOptions, ReadmeNamingParserBuilder> optionsService) {
        this.optionsService = optionsService;
    }

    /**
     * Receives an input markdown text stream nd parses its content to a Paragraphs object see {@link Paragraphs}
     *
     * @return A {@link Paragraphs} parsed object
     * @throws IOException Any kind of IO Exception
     */
    @Override
    public Paragraphs findAllParagraphs() throws IOException {
        final File fileTemplate = optionsService.getProjectSignerOptions().getTemplateLocation().toFile();
        final FileInputStream templateInputStream = new FileInputStream(fileTemplate);
        return TemplateParserHelper.findAllParagraphs(templateInputStream);
    }

    @Override
    public List<String> readAllLicenses() throws IOException {
        return Arrays.stream(optionsService.getProjectSignerOptions().getLicenseLocations())
                .map(path -> {
                    try {
                        final FileInputStream templateInputStream = new FileInputStream(path.toFile());
                        return new String(templateInputStream.readAllBytes(), StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        log.error("Failing to read template file {}. Error {}", path.getFileName().toString(), e.getMessage());
                        return null;
                    }
                }).collect(Collectors.toList());
    }
}
