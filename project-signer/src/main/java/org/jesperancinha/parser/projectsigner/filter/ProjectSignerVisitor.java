package org.jesperancinha.parser.projectsigner.filter;

import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.inteface.GeneratorService;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;
import static org.jesperancinha.parser.projectsigner.filter.ProjectSignerFilterHelper.isIgnorableFolder;

@Slf4j
@Builder
public class ProjectSignerVisitor extends SimpleFileVisitor<Path> {

    private final GeneratorService<Paragraphs> generatorService;
    private final Paragraphs allParagraphs;
    private final List<String> allLicenseText;

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        log.trace("Visiting file {}", file);
        return CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        if (isIgnorableFolder(dir)) {
            return SKIP_SUBTREE;
        }
        return CONTINUE;
    }

    @SneakyThrows
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException e) {
        if (isIgnorableFolder(dir)) {
            return SKIP_SUBTREE;
        }
        if (ObjectUtils.isEmpty(e)) {
            generatorService.processReadmeFile(dir, allParagraphs);
            if (Objects.nonNull(allLicenseText)) {
                generatorService.processLicenseFile(dir, allLicenseText);
            }
        } else {
            log.error("Failed on file {}", dir, e);
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file,
                                           IOException exc) {
        if (exc instanceof AccessDeniedException) {
            log.warn("Access denied on file {}", file);
        } else {
            log.error("Error on file {}", file, exc);
        }
        return CONTINUE;
    }

}
