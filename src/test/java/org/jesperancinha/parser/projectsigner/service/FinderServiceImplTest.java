package org.jesperancinha.parser.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.jesperancinha.parser.projectsigner.inteface.FileWriterService;
import org.jesperancinha.parser.projectsigner.inteface.GeneratorService;
import org.jesperancinha.parser.projectsigner.inteface.MergeService;
import org.jesperancinha.parser.projectsigner.inteface.OptionsService;
import org.jesperancinha.parser.projectsigner.inteface.ReadmeNamingService;
import org.jesperancinha.parser.projectsigner.inteface.ReadmeService;
import org.jesperancinha.parser.projectsigner.inteface.TemplateService;
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
import static org.mockito.Mockito.verifyZeroInteractions;
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
        verifyZeroInteractions(readmeNamingService);
        verifyZeroInteractions(optionsService);
        verifyZeroInteractions(fileWriterService);
        verifyZeroInteractions(readmeService);
        verifyZeroInteractions(mergeService);
    }
}