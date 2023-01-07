package org.jesperancinha.parser.projectsigner.service;

import org.jesperancinha.parser.markdowner.model.Paragraph;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.api.TemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptionsTest.ROOT_DIRECTORY;
import static org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptionsTest.TEMPLATE_LOCATION_README_MD;

@SpringBootTest(args = {TEMPLATE_LOCATION_README_MD, ROOT_DIRECTORY})
@ActiveProfiles("test")
public class TemplateServiceImplIT {

    @Autowired
    private TemplateService<Paragraphs> templateService;

    @Test
    public void testFindAllParagraphs() throws IOException {
        final Paragraphs allParagraphs = templateService.findAllParagraphs();

        assertThat(allParagraphs.getParagraphCount()).isEqualTo(2);
        final Paragraph license = allParagraphs.getParagraphByTag("License");
        final Paragraph aboutMe = allParagraphs.getParagraphByTag("About me");
        assertThat(license).isNotNull();
        assertThat(aboutMe).isNotNull();
        assertThat(license.getText()).isEqualTo("\nThis is one One");
        assertThat(aboutMe.getText()).isEqualTo("\nThis is two Two");
        assertThat(allParagraphs.getParagraphCount()).isEqualTo(2);
    }
}