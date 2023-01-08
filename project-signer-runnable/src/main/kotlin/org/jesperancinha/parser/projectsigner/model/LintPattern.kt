package org.jesperancinha.parser.projectsigner.model

import lombok.Builder
import java.util.regex.Pattern

data class LintPattern(val find: Pattern, val replace: String)