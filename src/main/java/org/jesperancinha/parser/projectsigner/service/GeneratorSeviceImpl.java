package org.jesperancinha.parser.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.inteface.GeneratorService;
import org.jesperancinha.parser.projectsigner.inteface.ReadmeNamingService;
import org.jesperancinha.parser.projectsigner.inteface.ReadmeService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;

/**
 * This is a generator service which is responsible for the generation of the new Readme.md file
 * It includes extra functions:
 * <p>
 * - Denies creation of Readme.md files where no project exists
 * - Creates matching Readme.me files where they don't exists in spite of existing package managing system files
 */
@Slf4j
@Service
public class GeneratorSeviceImpl implements GeneratorService {

    private ReadmeNamingService readmeNamingService;
    private ReadmeService readmeService;

    public GeneratorSeviceImpl(ReadmeNamingService readmeNamingService,
                               ReadmeService readmeService) {
        this.readmeNamingService = readmeNamingService;
        this.readmeService = readmeService;
    }

    @Override
    public void processReadmeFile(final Path readmePath, final Paragraphs allParagraphs) throws IOException {
        final InputStream inputStream = readmeNamingService.buildReadmeStream(readmePath);
        if (Objects.nonNull(inputStream)) {
            readmeService.exportNewReadme(readmePath, inputStream, allParagraphs);
        }
    }
}
