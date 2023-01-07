package org.jesperancinha.parser.projectsigner;

import org.jesperancinha.parser.projectsigner.service.FinderService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(args = {"--template-location=Readme.md","--root-directory=/"})
@ActiveProfiles("test")
public class ProjectSignerStartIT {

    @MockBean
    private FinderService finderService;

    @Captor
    private ArgumentCaptor<Path> pathArgumentCaptor;

    @Test
    public void run() throws IOException {
        verify(finderService, atLeast(0)).iterateThroughFilesAndFolders(pathArgumentCaptor.capture());
        verifyNoMoreInteractions(finderService);
        if (!CollectionUtils.isEmpty(pathArgumentCaptor.getAllValues())) {
            assertThat(pathArgumentCaptor.getValue()).isNotNull();
        }
    }
}