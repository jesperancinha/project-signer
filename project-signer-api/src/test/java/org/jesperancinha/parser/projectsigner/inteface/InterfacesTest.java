package org.jesperancinha.parser.projectsigner.inteface;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InterfacesTest {

    @Test
    public void testAllInterfacesWhenCreatingImplementationThenAllow() {
        final var fileWriterService = new FileWriterService<String>() {
            @Override
            public void exportReadmeFile(Path path, String text) throws IOException {

            }

            @Override
            public void exportReportFile(Path path, List<String> projectDataList) throws IOException {

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
        final var mergeService = new MergeService<>() {
            @Override
            public String mergeDocumentWithFooterTemplate(String readmeMd, Object footer) {
                return null;
            }

            @Override
            public void writeMergedResult(Path readmePath, String newText) throws IOException {

            }
        };

        final var optionsService = new OptionsService<>() {
            @Override
            public Object processOptions(String[] args) {
                return null;
            }

            @Override
            public Object getProjectSignerOptions() {
                return null;
            }

            @Override
            public Object getCommonNamingParser() {
                return null;
            }
        };

        final var readmeNamingService = new ReadmeNamingService() {
            @Override
            public InputStream buildReadmeStream(Path path) throws IOException {
                return null;
            }
        };

        final var readmeService = new ReadmeService<String,String>() {

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
