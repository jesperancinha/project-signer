package org.jesperancinha.parser.projectsigner.model

import org.jesperancinha.parser.markdowner.badges.model.BadgeGroup
import org.jesperancinha.parser.markdowner.badges.model.BadgeType

data class ProjectData(
    val title: String,
    val directory: String?,
    val badgeGroupMap: Map<BadgeType, BadgeGroup>
) {
    fun getNormalizedTitle() = if(title.startsWith("[![")) title else (directory ?: title)
}