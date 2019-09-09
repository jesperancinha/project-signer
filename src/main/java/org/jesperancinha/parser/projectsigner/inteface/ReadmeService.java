package org.jesperancinha.parser.projectsigner.inteface;

import org.jesperancinha.parser.markdowner.model.Paragraphs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface ReadmeService {
    String readDataSprippedOfTags(InputStream templateInputStream, String... tags) throws IOException;

    void exportNewReadme(Path readmePath, InputStream inputStream, Paragraphs allParagraphs) throws IOException;
}
