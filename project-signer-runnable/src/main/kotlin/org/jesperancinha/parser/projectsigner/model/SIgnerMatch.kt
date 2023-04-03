package org.jesperancinha.parser.projectsigner.model

import com.fasterxml.jackson.annotation.JsonProperty

data class SIgnerMatch(
    @JsonProperty("find")
    val find: String,
    @JsonProperty("replace")
    val replace: String
)