package org.jesperancinha.parser.projectsigner.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.jesperancinha.parser.markdowner.badges.model.BadgeGroup;
import org.jesperancinha.parser.markdowner.badges.model.BadgeType;

import java.util.Map;

@Value
@Builder
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class ProjectData {
    String title;
    Map<BadgeType, BadgeGroup> badgeGroupMap;
}
