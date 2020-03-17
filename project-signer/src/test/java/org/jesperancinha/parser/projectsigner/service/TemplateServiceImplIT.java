package org.jesperancinha.parser.projectsigner.service;

import org.jesperancinha.parser.markdowner.model.Paragraph;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.inteface.TemplateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
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
        assertThat(license.getText()).isEqualTo("This is one\nOne");
        assertThat(aboutMe.getText()).isEqualTo("This is two\nTwo");
        assertThat(allParagraphs.getParagraphCount()).isEqualTo(2);
    }
}