package org.jesperancinha.parser.projectsigner.model;

import lombok.Builder;
import org.jesperancinha.parser.markdowner.badges.model.BadgeGroup;
import org.jesperancinha.parser.markdowner.badges.model.BadgeType;

import java.util.Map;

@Builder
public record ProjectData(String title, Map<BadgeType, BadgeGroup> badgeGroupMap) {
}
