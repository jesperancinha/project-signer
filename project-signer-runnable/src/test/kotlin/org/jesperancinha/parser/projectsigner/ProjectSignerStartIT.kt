package org.jesperancinha.parser.projectsigner

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.jesperancinha.parser.projectsigner.service.FinderService
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.util.CollectionUtils
import java.nio.file.Path

@SpringBootTest(args = ["--template-location=Readme.md", "--root-directory=/"])
@ActiveProfiles("test")
class ProjectSignerStartIT {
    @Captor
    private val pathArgumentCaptor: ArgumentCaptor<Path>? = null

    @Test
    fun run() {
        if (!CollectionUtils.isEmpty(pathArgumentCaptor?.allValues)) {
            assertThat(pathArgumentCaptor?.value).isNotNull
        }
    }
}