package org.jesperancinha.parser.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.filter.ProjectSignerLicenseFilter;
import org.jesperancinha.parser.projectsigner.inteface.GeneratorService;
import org.jesperancinha.parser.projectsigner.inteface.ReadmeNamingService;
import org.jesperancinha.parser.projectsigner.inteface.ReadmeService;
import org.jesperancinha.parser.projectsigner.model.ProjectData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
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
public class GeneratorSeviceImpl implements GeneratorService<Paragraphs> {

    private final ReadmeNamingService readmeNamingService;
    private final ReadmeService<Paragraphs, ProjectData> readmeService;

    public GeneratorSeviceImpl(ReadmeNamingService readmeNamingService,
                               ReadmeService<Paragraphs, ProjectData> readmeService) {
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

    @Override
    public void processLicenseFile(Path licencePath, List<String> licenses) throws Throwable {
        final File f = new File(licencePath.toFile(), "Readme.md");
        final File licenseLegacyFile = new File(licencePath.toFile(), "License.txt");
        String licenseText = null;
        if (licenseLegacyFile.exists()) {
            boolean delete = licenseLegacyFile.delete();
            if (!delete) {
                log.error("Could not delete existing legacy file License.txt. Exiting...");
                System.exit(1);
            }
        }
        final File expectedLegactyFile = new File(licencePath.toFile(), "License");
        if (expectedLegactyFile.exists()) {
            final FileInputStream templateInputStream = new FileInputStream(expectedLegactyFile);
            licenseText = new String(templateInputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
        final File licenseFile = new File(licencePath.toFile(), "LICENSE");
        if (licenseFile.exists()) {
            boolean delete = licenseFile.delete();
            if (!delete) {
                log.error("Could not delete existing licence file LICENSE. Exiting...");
                System.exit(1);
            }
        }
        if (f.exists()) {
            try {
                final String newLicense = ProjectSignerLicenseFilter.getLicense(licenses, licenseText);
                final FileWriter fileWriter = new FileWriter(licenseFile);
                fileWriter.write(newLicense);
                fileWriter.flush();
                fileWriter.close();
            } catch (RuntimeException e) {
                log.error("Failed to find license for path {} and text {}", licencePath.toString(), licenseText);
            }
        }
    }
}
