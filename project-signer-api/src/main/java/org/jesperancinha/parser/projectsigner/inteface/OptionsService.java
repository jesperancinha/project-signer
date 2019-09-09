package org.jesperancinha.parser.projectsigner.inteface;

import org.jesperancinha.parser.markdowner.ReadmeNamingParser;
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions;

public interface OptionsService {

    ProjectSignerOptions processOptions(String[] args);

    ProjectSignerOptions getProjectSignerOptions();

    ReadmeNamingParser.ReadmeNamingParserBuilder getCommonNamingParser();
}
