package org.jesperancinha.parser.projectsigner.model

import com.fasterxml.jackson.annotation.JsonProperty

data class SignerMatch(
    @param:JsonProperty("find")
    val find: String,
    @param:JsonProperty("replace")
    val replace: String
)