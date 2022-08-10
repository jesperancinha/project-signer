package org.jesperancinha.parser.projectsigner.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

public record LintMatch(String find, String replace) {
}
