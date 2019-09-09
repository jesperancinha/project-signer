package org.jesperancinha.parser.projectsigner.inteface;

import org.jesperancinha.parser.markdowner.ReadmeNamingParser;

public interface OptionsService<T> {

    T processOptions(String[] args);

    T getProjectSignerOptions();

    ReadmeNamingParser.ReadmeNamingParserBuilder getCommonNamingParser();
}
