package org.jesperancinha.parser.projectsigner.service;

import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.api.MergeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MergeServiceImplIT {

    @Autowired
    private MergeService<Paragraphs> mergeService;

    @Test
    public void mergeDocumentWithFooterTemplate() {
        final String testReadme = "Readme text!";
        final Paragraphs.ParagraphsBuilder paragraphsBuilder = new Paragraphs.ParagraphsBuilder();
        paragraphsBuilder.withTagParagraph("## tag", "This is a test paragraph");
        paragraphsBuilder.withTagParagraph("## ta", "This is a test2 paragraph");
        final Paragraphs testParagraphs = paragraphsBuilder.build();

        final String result = mergeService.mergeDocumentWithFooterTemplate(testReadme, testParagraphs);

        assertThat(result).isEqualTo("Readme text!\n\n## tag\nThis is a test paragraph\n\n## ta\nThis is a test2 paragraph\n");
    }

    @Test
    public void mergeDocumentWithFooterTemplateDifferentOrder() {
        final String testReadme = "Readme text!";
        final Paragraphs.ParagraphsBuilder paragraphsBuilder = new Paragraphs.ParagraphsBuilder();
        paragraphsBuilder.withTagParagraph("## ta", "This is a test2 paragraph");
        paragraphsBuilder.withTagParagraph("## tag", "This is a test paragraph");
        final Paragraphs testParagraphs = paragraphsBuilder.build();

        final String result = mergeService.mergeDocumentWithFooterTemplate(testReadme, testParagraphs);

        assertThat(result).isEqualTo("Readme text!\n\n## ta\nThis is a test2 paragraph\n\n## tag\nThis is a test paragraph\n");
    }
}