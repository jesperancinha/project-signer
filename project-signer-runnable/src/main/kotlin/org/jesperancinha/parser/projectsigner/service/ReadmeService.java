package org.jesperancinha.parser.projectsigner.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jesperancinha.parser.markdowner.badges.parser.BadgeParser;
import org.jesperancinha.parser.markdowner.helper.ReadmeParserHelper;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.model.LintMatch;
import org.jesperancinha.parser.projectsigner.model.LintPattern;
import org.jesperancinha.parser.projectsigner.model.ProjectData;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A Readme service to read and manipulate markdown files
 */
@Service
@Slf4j
public class ReadmeService {

    private static List<String> refsToRemove;
    private static List<LintPattern> lintMatches;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        try {
            refsToRemove = Arrays.stream(IOUtils.toString(ReadmeService.class.getResourceAsStream("/references.txt"), StandardCharsets.UTF_8.name())
                    .split("\n")).collect(Collectors.toList());
            final var jsonLint = IOUtils.toString(ReadmeService.class.getResourceAsStream("/jeorg-lint.json"), StandardCharsets.UTF_8.name());
            lintMatches = Arrays.stream(objectMapper.readValue(jsonLint, LintMatch[].class))
                    .map(lintMatch -> LintPattern.builder()
                            .find(Pattern.compile(lintMatch.find()))
                            .replace(lintMatch.replace())
                            .build())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private final MergeService mergeService;
    private final OptionsService optionsService;
    private final List<ProjectData> allProjectData = new ArrayList<>();

    public ReadmeService(MergeService mergeService, OptionsService optionsService) {
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
    public String readDataSprippedOfTags(final InputStream readmeInputStream, String... tags) throws IOException {
        if (tags == null) {
            return IOUtils.toString(readmeInputStream, Charset.defaultCharset());
        }
        return ReadmeParserHelper.readDataSprippedOfTags(readmeInputStream, tags);
    }

    public void exportNewReadme(Path readmePath, InputStream inputStream, Paragraphs allParagraphs) throws IOException {
        log.info("Visiting path {}", readmePath);
        final var readme = readDataSprippedOfTags(inputStream, optionsService.getProjectSignerOptions().getTagNames());
        final var newText = mergeService.mergeDocumentWithFooterTemplate(readme, allParagraphs);
        final var lintedText = createLintedText(newText);
        final var nonRefText = removeNonRefs(lintedText);
        if (!optionsService.getProjectSignerOptions().getRootDirectory().relativize(readmePath).toString().contains("/")) {
            allProjectData.add(
                    ProjectData
                            .builder()
                            .title(readme.split("\n")[0].replace("#", "").strip())
                            .badgeGroupMap(BadgeParser.parse(readme))
                            .build());
        }
        mergeService.writeMergedResult(readmePath, nonRefText);
    }

    private String createLintedText(String newText) {
        var ref = new Object() {
            String readme = newText;
        };
        lintMatches.forEach(lintMatch -> {
            Matcher m = lintMatch.find().matcher(ref.readme);
            if (m.find()) {
                ref.readme = m.replaceAll(lintMatch.replace());
            }
        });
        return ref.readme;
    }

    private String removeNonRefs(String newText) {
        if (Objects.isNull(newText) || refsToRemove.size() == 1 && refsToRemove.get(0).isEmpty()) {
            return newText;
        }
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

    public List<ProjectData> getAllProjectData() {
        return allProjectData;
    }
}
