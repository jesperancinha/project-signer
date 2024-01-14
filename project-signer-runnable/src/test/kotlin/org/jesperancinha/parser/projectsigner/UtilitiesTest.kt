package org.jesperancinha.parser.projectsigner

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.InputStreamReader

class UtilitiesTest {

    @Test
    fun `should get github username`(){
        val process = ProcessBuilder("git", "config", "--get", "user.name")
            .start()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val username = reader.readLine()

        process.waitFor()

        username shouldBe "jesperancinha"
    }
}