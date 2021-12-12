package org.jesperancinha.parser.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.api.FileWriterService;
import org.jesperancinha.parser.projectsigner.api.GeneratorService;
import org.jesperancinha.parser.projectsigner.api.MergeService;
import org.jesperancinha.parser.projectsigner.api.OptionsService;
import org.jesperancinha.parser.projectsigner.api.ReadmeNamingService;
import org.jesperancinha.parser.projectsigner.api.ReadmeService;
import org.jesperancinha.parser.projectsigner.api.TemplateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Path;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class FinderServiceImplTest {

    @InjectMocks
    private FinderServiceImpl finderService;

    @Mock
    private ReadmeNamingService readmeNamingService;

    @Mock
    private MergeService mergeService;

    @Mock
    private TemplateService templateService;

    @Mock
    private ReadmeService readmeService;

    @Mock
    private OptionsService optionsService;

    @Mock
    private FileWriterService fileWriterService;

    @Mock
    private GeneratorService generatorService;

    @TempDir
    public static Path tempDirectory;

    @Test
    public void testIterateThroughFilesAndFolders() throws IOException {
        final Paragraphs mockParagraphs = mock(Paragraphs.class);
        when(templateService.findAllParagraphs()).thenReturn(mockParagraphs);

        finderService.iterateThroughFilesAndFolders(tempDirectory);

        verify(templateService).findAllParagraphs();
        verify(generatorService).processReadmeFile(tempDirectory, mockParagraphs);
        verifyNoInteractions(readmeNamingService);
        verifyNoInteractions(optionsService);
        verifyNoInteractions(fileWriterService);
        verifyNoInteractions(readmeService);
        verifyNoInteractions(mergeService);
    }
}