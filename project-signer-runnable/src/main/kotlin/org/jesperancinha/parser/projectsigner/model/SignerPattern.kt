package org.jesperancinha.parser.projectsigner.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.regex.Pattern

data class SignerPattern(
    @JsonProperty("find")
    val find: Pattern,
    @JsonProperty("replace")
    val replace: String
)