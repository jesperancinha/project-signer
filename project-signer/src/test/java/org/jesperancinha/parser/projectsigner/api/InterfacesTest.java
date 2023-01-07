package org.jesperancinha.parser.projectsigner.api;

import org.jesperancinha.parser.projectsigner.model.ProjectData;
import org.jesperancinha.parser.projectsigner.service.FileWriterService;
import org.jesperancinha.parser.projectsigner.service.MergeService;
import org.jesperancinha.parser.projectsigner.service.OptionsService;
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
            public void exportReadmeFile(Path path, String text) throws IOException {

            }

            public void exportReportFile(Path path, List<ProjectData> projectDataList) {

            }
        };

        final var finderService = new FinderService() {
            @Override
            public void iterateThroughFilesAndFolders(Path rootPath) throws IOException {

            }
        };

        final var generatorService = new GeneratorService<>() {
            @Override
            public void processReadmeFile(Path readmePath, Object allParagraphs) throws IOException {

            }

            @Override
            public void processLicenseFile(Path licencePath, List<String> license) throws Throwable {

            }
        };
        final var mergeService = new MergeService(fileWriterService) {
            public String mergeDocumentWithFooterTemplate(String readmeMd, Object footer) {
                return null;
            }

            public void writeMergedResult(Path readmePath, String newText) throws IOException {

            }
        };

        final var optionsService = mock(OptionsService.class);

        final var readmeNamingService = new ReadmeNamingService() {
            @Override
            public InputStream buildReadmeStream(Path path) throws IOException {
                return null;
            }
        };

        final var readmeService = new ReadmeService<String, String>() {

            @Override
            public String readDataSprippedOfTags(InputStream templateInputStream, String... tags) throws IOException {
                return null;
            }

            @Override
            public void exportNewReadme(Path readmePath, InputStream inputStream, String allParagraphs) throws IOException {

            }

            @Override
            public List<String> getAllProjectData() {
                return null;
            }
        };

        final var templateService = new TemplateService<>() {
            @Override
            public Object findAllParagraphs() throws IOException {
                return null;
            }

            @Override
            public List<String> readAllLicenses() throws IOException {
                return null;
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
