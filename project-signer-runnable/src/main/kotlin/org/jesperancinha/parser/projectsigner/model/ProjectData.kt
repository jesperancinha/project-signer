package org.jesperancinha.parser.projectsigner.model

import org.jesperancinha.parser.markdowner.badges.model.BadgeGroup
import org.jesperancinha.parser.markdowner.badges.model.BadgeType

class ProjectData(val title: String, val badgeGroupMap: Map<BadgeType, BadgeGroup>)