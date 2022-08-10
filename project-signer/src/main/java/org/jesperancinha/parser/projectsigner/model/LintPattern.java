package org.jesperancinha.parser.projectsigner.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.regex.Pattern;

@Builder
public record LintPattern(Pattern find, String replace) {
}
