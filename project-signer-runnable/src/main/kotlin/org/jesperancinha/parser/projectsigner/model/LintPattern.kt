package org.jesperancinha.parser.projectsigner.model

import java.util.regex.Pattern

data class LintPattern(val find: Pattern, val replace: String)