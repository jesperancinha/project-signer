package org.jesperancinha.parser.projectsigner

import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class UtilitiesTest {

    @Test
    fun `should get github username`() {
        runCatching {
            val process = ProcessBuilder("git", "config", "--get", "user.name")
                .start()

            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val username = reader.readLine()

            process.waitFor()

            username shouldBeIn arrayOf("jesperancinha", null, "Joao Esperancinha")
        }.onFailure {
            it.shouldBeInstanceOf<IOException>()
            it.message.shouldContain("No such file or directory")
        }
    }
}