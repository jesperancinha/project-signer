package org.jesperancinha.parser.projectsigner.model

import lombok.Builder
import org.jesperancinha.parser.markdowner.badges.model.BadgeGroup
import org.jesperancinha.parser.markdowner.badges.model.BadgeType

@Builder
class ProjectData(val title: String, val badgeGroupMap: Map<BadgeType, BadgeGroup>)