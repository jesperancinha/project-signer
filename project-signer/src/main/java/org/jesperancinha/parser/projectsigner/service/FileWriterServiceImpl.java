package org.jesperancinha.parser.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.badges.model.Badge;
import org.jesperancinha.parser.markdowner.badges.model.BadgeSettingGroup;
import org.jesperancinha.parser.markdowner.badges.model.BadgeType;
import org.jesperancinha.parser.markdowner.badges.parser.BadgeParser;
import org.jesperancinha.parser.projectsigner.api.FileWriterService;
import org.jesperancinha.parser.projectsigner.model.ProjectData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileWriterServiceImpl implements FileWriterService<ProjectData> {
    @Override
    public void exportReadmeFile(Path path, String text) throws IOException {
        final File f = new File(path.toFile(), "Readme.md");
        final FileWriter fileWriter = new FileWriter(f);
        fileWriter.write(text);
        fileWriter.flush();
        fileWriter.close();
    }

    @Override
    public void exportReportFile(Path path, final List<ProjectData> projectDataList) {
        BadgeParser.badgeTypes.values().forEach(badgeType -> {
            final var f = new File(path.toFile(), badgeType.getDestinationFile());
            try {
                final var fileWriter = new FileWriter(f);
                fileWriter.write(String.format("# %s Report\n\n", getTitle(badgeType)));
                writeHyperLinks(fileWriter);
                writeTitles(badgeType, fileWriter);
                writeTopTable(badgeType, fileWriter);
                projectDataList.sort((o1, o2) -> {
                    final List<Badge> badgeList1 = o1.getBadgeGroupMap().get(badgeType).getBadgeHashMap().values().stream().filter(Objects::nonNull).toList();
                    final List<Badge> badgeList2 = o2.getBadgeGroupMap().get(badgeType).getBadgeHashMap().values().stream().filter(Objects::nonNull).toList();
                    final var nBadges1 = badgeList1.size();
                    final var nBadges2 = badgeList2.size();
                    if (nBadges1 == nBadges2) {
                        if(badgeList1.size()>0) {
                            final String badgeText1 = badgeList1.get(0).getBadgeText();
                            final String badgeText2 = badgeList2.get(0).getBadgeText();
                            if (badgeText1.contains("message=") && badgeText2.contains("message=")) {
                                final var message1 = badgeText1.split("message=")[1].substring(0,3);
                                final var message2 = badgeText2.split("message=")[1].substring(0,3);
                                return message1.compareTo(message2);
                            }
                        }
                        return o1.getTitle().compareTo(o2.getTitle());
                    }
                    return nBadges2 - nBadges1;
                });
                projectDataList.forEach(projectData -> writeProjectData(badgeType, fileWriter, projectData));
                writeHyperLinks(fileWriter);
                fileWriter.close();
            } catch (IOException e) {
                log.error("Error!", e);
                System.exit(1);
            }
        });

    }

    private void writeHyperLinks(FileWriter fileWriter) throws IOException {
        final var links = BadgeParser.badgeTypes.values().stream()
                .map(badgeType -> {
                    final String title = getTitle(badgeType);
                    return String.format("[%s](./%s)",
                            title,
                            badgeType.getDestinationFile());
                })
                .collect(Collectors.joining(" - "));
        fileWriter.write(String.format("## %s \n\n", links));
    }

    private String getTitle(BadgeType badgeType) {
        if (Objects.isNull(badgeType.getTitle())) {
            return badgeType.getDestinationFile().replace(".md", "");
        }
        return badgeType.getTitle();
    }

    private void writeTitles(BadgeType badgeType, FileWriter fileWriter) throws IOException {
        final var badgeSettingGroup = BadgeParser.badgeSettingGroups.get(badgeType);
        fileWriter.write("|Project");
        badgeSettingGroup.getBadgeSettingList().forEach(badgePattern -> {
            try {
                if (!badgePattern.getTitle().equals("Project")) {
                    fileWriter.write("|");
                    fileWriter.write(badgePattern.getTitle());
                }
            } catch (IOException e) {
                log.error("Error!", e);
                System.exit(1);
            }
        });
        fileWriter.write("|\n");
        fileWriter.flush();
    }

    private void writeProjectData(BadgeType badgeType, FileWriter fileWriter, ProjectData projectData) {
        try {
            final var badgeGroup = projectData.getBadgeGroupMap().get(badgeType);

            BadgeParser.badgeSettingGroups.get(badgeType).getBadgeSettingList()
                    .forEach(badgePattern -> {
                        final Badge badge = badgeGroup.getBadgeHashMap().get(badgePattern.getPattern());
                        if (badgePattern.getTitle().equals("Project") && Objects.isNull(badge)) {
                            try {
                                fileWriter.write("|");
                                fileWriter.write(projectData.getTitle());
                            } catch (IOException e) {
                                log.error("Error!", e);
                                System.exit(1);
                            }
                        } else {
                            writeBadgeElement(fileWriter, badge);
                        }
                    });
            fileWriter.write("|\n");
            fileWriter.flush();
        } catch (IOException e) {
            log.error("Error!", e);
            System.exit(1);
        }
    }

    private void writeBadgeElement(FileWriter fileWriter, org.jesperancinha.parser.markdowner.badges.model.Badge badge) {
        try {
            fileWriter.write("|");
            if (Objects.isNull(badge)) {
                fileWriter.write("---");
            } else {
                fileWriter.write(badge.getBadgeText());
            }
        } catch (IOException e) {
            log.error("Error!", e);
            System.exit(1);
        }
    }

    private void writeTopTable(BadgeType badgeType, FileWriter fileWriter) throws IOException {
        final BadgeSettingGroup badgeSettingGroup = BadgeParser.badgeSettingGroups.get(badgeType);
        badgeSettingGroup.getBadgeSettingList().forEach(badgePattern -> {
            try {
                fileWriter.write("|---");
            } catch (IOException e) {
                log.error("Error!", e);
                System.exit(1);
            }
        });
        fileWriter.write("|\n");
    }
}
