package org.jesperancinha.parser.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jesperancinha.parser.markdowner.helper.TemplateParserHelper;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A markdown template service to handle markdown texts
 */
@Service
@Slf4j
public class TemplateService {

    private final OptionsService optionsService;

    public TemplateService(OptionsService optionsService) {
        this.optionsService = optionsService;
    }

    /**
     * Receives an input Markdown text stream nd parses its content to a Paragraphs object see {@link Paragraphs}
     *
     * @return A {@link Paragraphs} parsed object
     * @throws IOException Any kind of IO Exception
     */
    public Paragraphs findAllParagraphs() throws IOException {
        val fileTemplate = optionsService.getProjectSignerOptions().getTemplateLocation().toFile();
        val templateInputStream = new FileInputStream(fileTemplate);
        return TemplateParserHelper.findAllParagraphs(templateInputStream);
    }

    public List<String> readAllLicenses() {
        val licenseLocations = optionsService.getProjectSignerOptions().getLicenseLocations();
        if (licenseLocations == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(licenseLocations)
                .map(path -> {
                    try (final FileInputStream templateInputStream = new FileInputStream(path.toFile())) {
                        return new String(templateInputStream.readAllBytes(), StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        log.error("Failing to read template file {}. Error {}", path.getFileName().toString(), e.getMessage());
                        return null;
                    }
                }).collect(Collectors.toList());
    }
}
