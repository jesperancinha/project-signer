package org.jesperancinha.parser.projectsigner.inteface;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileWriterService<T> {

    void exportReadmeFile(Path path, String text) throws IOException;

    void exportReportFile(Path path, List<T> projectDataList) throws IOException;
}
