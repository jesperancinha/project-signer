package org.jesperancinha.parser.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.filter.ProjectSignerVisitor;
import org.jesperancinha.parser.projectsigner.inteface.FinderService;
import org.jesperancinha.parser.projectsigner.inteface.GeneratorService;
import org.jesperancinha.parser.projectsigner.inteface.TemplateService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;
import static java.nio.file.FileVisitOption.FOLLOW_LINKS;
import static java.util.EnumSet.of;

@Slf4j
@Service
public class FinderServiceImpl implements FinderService {

    private final GeneratorService<Paragraphs> generatorService;
    private final TemplateService<Paragraphs> templateService;

    public FinderServiceImpl(
            final GeneratorService<Paragraphs> generatorService, final TemplateService<Paragraphs> templateService) {
        this.generatorService = generatorService;
        this.templateService = templateService;
    }

    @Override
    public void iterateThroughFilesAndFolders(Path rootPath) throws IOException {
        final Paragraphs allParagraphs = templateService.findAllParagraphs();
       final List<String> readAllLicenses = templateService.readAllLicenses();
        Files.walkFileTree(rootPath, of(FOLLOW_LINKS), MAX_VALUE,
                ProjectSignerVisitor.builder()
                        .generatorService(generatorService)
                        .allParagraphs(allParagraphs)
                        .allLicenseText(readAllLicenses)
                        .build());
    }

}
