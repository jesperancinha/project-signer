package org.jesperancinha.parser.projectsigner.inteface;

import org.jesperancinha.parser.markdowner.model.Paragraphs;

import java.io.IOException;
import java.nio.file.Path;

public interface MergeService {
    String mergeDocumentWithFooterTemplate(String readmeMd, Paragraphs footer);

    void writeMergedResult(Path readmePath, String newText) throws IOException;
}
