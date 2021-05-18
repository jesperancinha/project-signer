package org.jesperancinha.parser.projectsigner.api;

import java.io.IOException;
import java.nio.file.Path;

public interface MergeService<T> {
    String mergeDocumentWithFooterTemplate(String readmeMd, T footer);

    void writeMergedResult(Path readmePath, String newText) throws IOException;
}
