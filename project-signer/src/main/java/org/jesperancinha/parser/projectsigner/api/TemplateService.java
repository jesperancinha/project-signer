package org.jesperancinha.parser.projectsigner.api;

import java.io.IOException;
import java.util.List;

public interface TemplateService<T> {
    T findAllParagraphs() throws IOException;

    List<String> readAllLicenses() throws IOException;
}
