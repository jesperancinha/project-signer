package org.jesperancinha.parser.projectsigner.service;

import org.jesperancinha.parser.markdowner.model.Paragraphs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Path;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinderServiceTest {

    @InjectMocks
    private FinderService finderService;

    @Mock
    private ReadmeNamingService readmeNamingService;

    @Mock
    private MergeService mergeService;

    @Mock
    private TemplateService templateService;

    @Mock
    private ReadmeService readmeService;

    @Mock
    private OptionsServiceMock optionsService;

    @Mock
    private FileWriterService fileWriterService;

    @Mock
    private GeneratorSevice generatorService;

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