package org.jesperancinha.parser.projectsigner.inteface;

import java.io.IOException;

public interface TemplateService<T> {
    T findAllParagraphs() throws IOException;
}
