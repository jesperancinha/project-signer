package org.jesperancinha.parser.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.helper.MergeParserHelper;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.inteface.FileWriterService;
import org.jesperancinha.parser.projectsigner.inteface.MergeService;
import org.jesperancinha.parser.projectsigner.model.ProjectData;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

/**
 * A merge service destined to merge operation between markdown files and objects
 */
@Slf4j
@Service
public class MergeServiceImpl implements MergeService<Paragraphs> {

    private final FileWriterService<ProjectData> fileWriterService;

    public MergeServiceImpl(FileWriterService<ProjectData> fileWriterService) {
        this.fileWriterService = fileWriterService;
    }

    /**
     * Receives a complete markdown text and a {@link Paragraphs} instance and adds all paragraphs in the stipulated order to the end of the text
     *
     * @param readmeMd A complete String representation of a markdown text
     * @param footer   A {@link Paragraphs} instance which will add all paragraphs to the end of the markdown text
     * @return The complete String merge between a {@link String} text and a {@link Paragraphs} instances
     */
    @Override
    public String mergeDocumentWithFooterTemplate(String readmeMd, Paragraphs footer) {
        return MergeParserHelper.mergeDocumentWithFooterTemplate(readmeMd, footer);
    }

    @Override
    public void writeMergedResult(Path readmePath, String newText) throws IOException {
        log.trace("New readme:\n {}", newText);
        fileWriterService.exportReadmeFile(readmePath, newText);
    }
}
