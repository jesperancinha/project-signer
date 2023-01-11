package org.jesperancinha.parser.projectsigner

import io.kotest.matchers.shouldBe
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.util.CollectionUtils
import java.nio.file.Path

@SpringBootTest(args = ["--template-location=Readme.md", "--root-directory=/"])
@ActiveProfiles("test")
class ProjectSignerStartIT {
    @Captor
    private val pathArgumentCaptor: ArgumentCaptor<Path>? = null

    @BeforeEach
    fun setUp() {
        System.getProperty("file.encoding") shouldBe "UTF-8"
    }

    @Test
    fun run() {
        if (!CollectionUtils.isEmpty(pathArgumentCaptor?.allValues)) {
            assertThat(pathArgumentCaptor?.value).isNotNull
        }
    }
}