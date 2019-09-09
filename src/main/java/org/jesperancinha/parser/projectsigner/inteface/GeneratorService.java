package org.jesperancinha.parser.projectsigner.inteface;

import org.jesperancinha.parser.markdowner.model.Paragraphs;

import java.io.IOException;
import java.nio.file.Path;

public interface GeneratorService {
    void processReadmeFile(Path readmePath, Paragraphs allParagraphs) throws IOException;
}
