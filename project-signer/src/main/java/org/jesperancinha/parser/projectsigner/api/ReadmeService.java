package org.jesperancinha.parser.projectsigner.api;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public interface ReadmeService<T, P> {
    String readDataSprippedOfTags(InputStream templateInputStream, String... tags) throws IOException;

    void exportNewReadme(Path readmePath, InputStream inputStream, T allParagraphs) throws IOException;

    List<P> getAllProjectData();
}
