package org.jesperancinha.parser.projectsigner

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
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
    lateinit var pathArgumentCaptor: ArgumentCaptor<Path>

    @BeforeEach
    fun setUp() {
        System.getProperty("file.encoding") shouldBe "UTF-8"
    }

    @Test
    fun run() {
        if (!CollectionUtils.isEmpty(pathArgumentCaptor.allValues)) {
            pathArgumentCaptor.value.shouldNotBeNull()
        }
    }
}