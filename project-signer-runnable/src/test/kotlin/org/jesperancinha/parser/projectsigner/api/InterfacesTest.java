package org.jesperancinha.parser.projectsigner.api;

import org.jesperancinha.parser.markdowner.filter.FileFilterChain;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.model.ProjectData;
import org.jesperancinha.parser.projectsigner.service.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class InterfacesTest {

    @Test
    public void testAllInterfacesWhenCreatingImplementationThenAllow() {
        final var fileWriterService = new FileWriterService() {
            public void exportReadmeFile(Path path, String text) {

            }

            public void exportReportFiles(Path path, List<ProjectData> projectDataList) {

            }
        };

        final var mergeService = new MergeService(fileWriterService) {

            public void writeMergedResult(Path readmePath, String newText) {

            }
        };

        final var optionsService = mock(OptionsService.class);

        final var readmeService = new ReadmeService(mergeService, optionsService) {

            public String readDataSprippedOfTags(InputStream templateInputStream, String... tags) {
                return null;
            }

            public void exportNewReadme(Path readmePath, InputStream inputStream, Paragraphs allParagraphs) throws IOException {

            }

            public List<ProjectData> getAllProjectData() {
                return null;
            }
        };

        final var readmeNamingService = new ReadmeNamingService(FileFilterChain.builder().build(), optionsService) {
            public InputStream buildReadmeStream(Path path) throws IOException {
                return null;
            }
        };

        final var generatorService = new GeneratorSevice(readmeNamingService, readmeService) {
            public void processReadmeFile(Path readmePath, Paragraphs allParagraphs) {

            }

            public void processLicenseFile(Path licencePath, List<String> license) {

            }

        };

        final var templateService = new TemplateService(optionsService) {
            public Paragraphs findAllParagraphs() {
                return null;
            }

            public List<String> readAllLicenses() {
                return null;
            }
        };

        final var finderService = new FinderService(generatorService, templateService) {
            public void iterateThroughFilesAndFolders(Path rootPath) {

            }
        };

        assertThat(fileWriterService).isNotNull();
        assertThat(finderService).isNotNull();
        assertThat(generatorService).isNotNull();
        assertThat(mergeService).isNotNull();
        assertThat(optionsService).isNotNull();
        assertThat(readmeNamingService).isNotNull();
        assertThat(readmeService).isNotNull();
        assertThat(templateService).isNotNull();

    }
}
