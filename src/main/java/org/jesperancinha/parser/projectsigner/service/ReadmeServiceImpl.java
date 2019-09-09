package org.jesperancinha.parser.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.ReadmeParserHelper;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.inteface.MergeService;
import org.jesperancinha.parser.projectsigner.inteface.OptionsService;
import org.jesperancinha.parser.projectsigner.inteface.ReadmeService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * A Readme service to read and manipulate markdown files
 */
@Service
@Slf4j
public class ReadmeServiceImpl implements ReadmeService {

    private MergeService mergeService;
    private OptionsService optionsService;

    public  ReadmeServiceImpl(MergeService mergeService, OptionsService optionsService) {
        this.mergeService = mergeService;
        this.optionsService = optionsService;
    }

    /**
     * Reads an input marked down string and returns the exact same text without the specified cardinal tags and their content.
     * <p>
     * This means any content of # tags
     *
     * @param readmeInputStream An input stream of a markdown text
     * @param tags              All tags of the paragraphs to be removed
     * @return The filtered String
     * @throws IOException Any IO Exception thrown
     */
    @Override
    public String readDataSprippedOfTags(final InputStream readmeInputStream, String... tags) throws IOException {
        return ReadmeParserHelper.readDataSprippedOfTags(readmeInputStream, tags);
    }

    @Override
    public void exportNewReadme(Path readmePath, InputStream inputStream, Paragraphs allParagraphs) throws IOException {
        log.trace("Visiting path {}", readmePath);
        final String readme = readDataSprippedOfTags(inputStream, optionsService.getProjectSignerOptions().getTagNames());
        final String newText = mergeService.mergeDocumentWithFooterTemplate(readme, allParagraphs);
        mergeService.writeMergedResult(readmePath, newText);
    }
}
