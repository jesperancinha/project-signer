package org.jesperancinha.parser.projectsigner.model;

import lombok.Builder;

import java.util.regex.Pattern;

@Builder
public record LintPattern(Pattern find, String replace) {
}
