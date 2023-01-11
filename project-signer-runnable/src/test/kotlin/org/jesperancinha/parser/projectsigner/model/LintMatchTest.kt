package org.jesperancinha.parser.projectsigner.model

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.matchers.shouldBe
import org.apache.commons.io.IOUtils
import org.jesperancinha.parser.projectsigner.service.ReadmeService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.function.Consumer
import java.util.regex.Pattern

internal class LintMatchTest {

    @BeforeEach
    fun setUp() {
        System.getProperty("file.encoding") shouldBe "UTF-8"
    }

    @Test
    @Throws(IOException::class)
    fun testMatchAndReplaceWhenPatterReplacedThenGetDocumentWithStandards() {
        formatText("\n- test") shouldBe "\n-   test"
        formatText("\n- [") shouldBe "\n-   ["
        formatText("\n1. A") shouldBe "\n1.  A"
        formatText("\n12.  A") shouldBe "\n12. A"
        formatText("\n   1. A") shouldBe "\n   1.  A"
        formatText("\n   12.  A") shouldBe "\n   12. A"
    }

    @Throws(IOException::class)
    private fun formatText(test: String): String {
        val jsonLint = IOUtils.toString(
            Objects.requireNonNull<InputStream>(
                ReadmeService::class.java.getResourceAsStream("/jeorg-lint.json")
            ),
            StandardCharsets.UTF_8
        )
        val lintMatches: List<LintPattern> =
            objectMapper.readValue(jsonLint, Array<LintMatch>::class.java)
                .map { (find, replace): LintMatch ->
                    LintPattern(
                        find = Pattern.compile(find),
                        replace = replace
                    )
                }.toList()
        val ref = object : Any() {
            var readme = test
        }
        lintMatches.forEach(Consumer { (find, replace): LintPattern ->
            val m = find.matcher(ref.readme)
            if (m.find()) {
                ref.readme = m.replaceAll(replace)
            }
        })
        return ref.readme
    }

    companion object {
        private val objectMapper: ObjectMapper = ObjectMapper()
    }
}