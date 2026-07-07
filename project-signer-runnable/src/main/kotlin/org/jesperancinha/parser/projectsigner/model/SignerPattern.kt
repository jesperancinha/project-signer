package org.jesperancinha.parser.projectsigner.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.regex.Pattern

data class SignerPattern(
    @param:JsonProperty("find")
    val find: Pattern,
    @param:JsonProperty("replace")
    val replace: String
)