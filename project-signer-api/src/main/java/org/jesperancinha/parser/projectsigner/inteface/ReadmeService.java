package org.jesperancinha.parser.projectsigner.inteface;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface ReadmeService<T> {
    String readDataSprippedOfTags(InputStream templateInputStream, String... tags) throws IOException;

    void exportNewReadme(Path readmePath, InputStream inputStream, T allParagraphs) throws IOException;
}
