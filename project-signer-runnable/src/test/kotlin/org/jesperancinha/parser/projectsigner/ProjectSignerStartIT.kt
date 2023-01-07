package org.jesperancinha.parser.projectsigner
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.assertj.core.api.AssertionsForClassTypes.assertThat

import org.jesperancinha.parser.projectsigner.service.FinderService
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.atLeast
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.util.CollectionUtils
import java.nio.file.Path

@SpringBootTest(args = ["--template-location=Readme.md", "--root-directory=/"])
@ActiveProfiles("test")
class ProjectSignerStartIT {
    @MockBean
    private val finderService: FinderService? = null

    @Captor
    private val pathArgumentCaptor: ArgumentCaptor<Path>? = null
    @Test
    fun run() {
        pathArgumentCaptor?.capture()?.let { verify(finderService, atLeast(0))?.iterateThroughFilesAndFolders(it) }
        verifyNoMoreInteractions(finderService)
        if (!CollectionUtils.isEmpty(pathArgumentCaptor?.allValues)) {
            assertThat(pathArgumentCaptor?.value).isNotNull
        }
    }
}