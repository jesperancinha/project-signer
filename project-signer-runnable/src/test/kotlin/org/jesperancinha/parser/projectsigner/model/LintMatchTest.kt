package org.jesperancinha.parser.projectsigner.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.jesperancinha.parser.projectsigner.service.ReadmeService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class LintMatchTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testMatchAndReplaceWhenPatterReplacedThenGetDocumentWithStandards() throws IOException {
        assertThat(formatText("\n- test")).isEqualTo("\n-   test");
        assertThat(formatText("\n- [")).isEqualTo("\n-   [");
        assertThat(formatText("\n1. A")).isEqualTo("\n1.  A");
        assertThat(formatText("\n12.  A")).isEqualTo("\n12. A");
        assertThat(formatText("\n   1. A")).isEqualTo("\n   1.  A");
        assertThat(formatText("\n   12.  A")).isEqualTo("\n   12. A");
    }

    private String formatText(final String test) throws IOException {
        final var jsonLint =
                IOUtils.toString(Objects.requireNonNull(ReadmeService.class.getResourceAsStream("/jeorg-lint.json")),
                        StandardCharsets.UTF_8);
        List<LintPattern> lintMatches = Arrays.stream(objectMapper.readValue(jsonLint, LintMatch[].class))
                .map(lintMatch -> LintPattern.builder()
                        .find(Pattern.compile(lintMatch.find()))
                        .replace(lintMatch.replace())
                        .build()).toList();
        var ref = new Object() {
            String readme = test;
        };

        lintMatches.forEach(lintMatch -> {
            Matcher m = lintMatch.find().matcher(ref.readme);
            if (m.find()) {
                ref.readme = m.replaceAll(lintMatch.replace());
            }
        });
        return ref.readme;
    }
}
