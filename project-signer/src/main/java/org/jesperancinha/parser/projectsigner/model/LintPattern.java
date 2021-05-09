package org.jesperancinha.parser.projectsigner.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.regex.Pattern;

@Value
@Builder
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class LintPattern {
    Pattern find;
    String replace;
}
