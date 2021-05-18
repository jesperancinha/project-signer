package org.jesperancinha.parser.projectsigner.api;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface GeneratorService<T> {
    void processReadmeFile(Path readmePath, T allParagraphs) throws IOException;

    void processLicenseFile(Path licencePath, List<String> license) throws Throwable;
}
