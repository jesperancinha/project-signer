package org.jesperancinha.parser.projectsigner.service;

import org.jesperancinha.parser.markdowner.ReadmeNamingParser.ReadmeNamingParserBuilder;
import org.jesperancinha.parser.markdowner.TemplateParserHelper;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions;
import org.jesperancinha.parser.projectsigner.inteface.OptionsService;
import org.jesperancinha.parser.projectsigner.inteface.TemplateService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * A markdown template service to handle markdown texts
 */
@Service
public class TemplateServiceImpl implements TemplateService {

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
}
