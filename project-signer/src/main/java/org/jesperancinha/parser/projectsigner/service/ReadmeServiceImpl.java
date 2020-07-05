package org.jesperancinha.parser.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jesperancinha.parser.markdowner.filter.ReadmeNamingParser.ReadmeNamingParserBuilder;
import org.jesperancinha.parser.markdowner.helper.ReadmeParserHelper;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions;
import org.jesperancinha.parser.projectsigner.inteface.MergeService;
import org.jesperancinha.parser.projectsigner.inteface.OptionsService;
import org.jesperancinha.parser.projectsigner.inteface.ReadmeService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A Readme service to read and manipulate markdown files
 */
@Service
@Slf4j
public class ReadmeServiceImpl implements ReadmeService<Paragraphs> {

    private static List<String> refsToRemove;

    static {
        try {
            refsToRemove = Arrays.stream(IOUtils.toString(ReadmeServiceImpl.class.getResourceAsStream("/noref.txt"), StandardCharsets.UTF_8.name())
                    .split("\n")).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final MergeService<Paragraphs> mergeService;
    private final OptionsService<ProjectSignerOptions, ReadmeNamingParserBuilder> optionsService;

    public ReadmeServiceImpl(MergeService<Paragraphs> mergeService, OptionsService<ProjectSignerOptions, ReadmeNamingParserBuilder> optionsService) {
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

        mergeService.writeMergedResult(readmePath, removeNonRefs(newText));
    }

    private String removeNonRefs(String newText) {
        return Arrays.stream(newText.split("\n"))
                .filter(text -> !textIsRef(text))
                .collect(Collectors.joining("\n"))
                .concat("\n")
                .replaceAll("[\r\n]{2}", "\n\n")
                .replaceAll("[\r\n]{3}", "\n");
    }

    private boolean textIsRef(String text) {
        return textHasWord(text);
    }

    private boolean textHasWord(String text) {
        return refsToRemove.stream().anyMatch(ref -> text.toLowerCase().contains(ref.toLowerCase()));
    }
}
