package org.jesperancinha.parser.projectsigner.inteface;

import org.jesperancinha.parser.markdowner.model.Paragraphs;

import java.io.IOException;

public interface TemplateService {
    Paragraphs findAllParagraphs() throws IOException;
}
