package org.jesperancinha.parser.projectsigner.inteface;

import java.io.IOException;
import java.nio.file.Path;

public interface GeneratorService<T> {
    void processReadmeFile(Path readmePath, T allParagraphs) throws IOException;
}
