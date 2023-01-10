package org.jesperancinha.parser.projectsigner.service

import org.jesperancinha.parser.markdowner.badges.model.Badge
import org.jesperancinha.parser.markdowner.badges.model.BadgePattern
import org.jesperancinha.parser.markdowner.badges.model.BadgeType
import org.jesperancinha.parser.markdowner.badges.parser.BadgeParser
import org.jesperancinha.parser.projectsigner.model.ProjectData
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Path
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors
import kotlin.system.exitProcess

@Service
open class FileWriterService {
    @Throws(IOException::class)
    open fun exportReadmeFile(path: Path, text: String) {
        val fileName = path.findReadmeFileNameWithDefault()
        val readmeFile = File(path.toFile(), fileName)
        val fileWriter = FileWriter(readmeFile)
        fileWriter.write(text)
        fileWriter.flush()
        fileWriter.close()
    }

    open fun exportReportFiles(path: Path, projectDataList: List<ProjectData>) {
        BadgeParser.badgeTypes.values.forEach(Consumer { badgeType: BadgeType ->
            val file = File(path.toFile(), badgeType.destinationFile)
            try {
                val fileWriter = FileWriter(file)
                fileWriter.write(String.format("# %s Report\n\n", getTitle(badgeType)))
                writeHyperLinks(fileWriter)
                writeTitles(badgeType, fileWriter)
                writeTopTable(badgeType, fileWriter)


                projectDataList.sortedWith { o1: ProjectData, o2: ProjectData ->
                    val badgeList1 = o1.badgeGroupMap[badgeType]?.badgeHashMap?.values?.filterNotNull()?.toList()
                    val badgeList2 = o2.badgeGroupMap[badgeType]?.badgeHashMap?.values?.filterNotNull()?.toList()
                    val nBadges1 = badgeList1?.size ?: 0
                    val nBadges2 = badgeList2?.size ?: 0
                    if (nBadges1 == nBadges2) {
                        if (badgeList1?.isNotEmpty() == true && badgeList2?.isNotEmpty() == true) {
                            val badgeText1 = badgeList1[0].badgeText
                            val badgeText2 = badgeList2[0].badgeText
                            if (badgeText1.contains("message=") && badgeText2.contains(
                                    "message="
                                )
                            ) {
                                val message1 = badgeText1.split("message=".toRegex()).dropLastWhile { it.isEmpty() }
                                    .toTypedArray()[1].substring(0, 3)
                                val message2 = badgeText2.split("message=".toRegex()).dropLastWhile { it.isEmpty() }
                                    .toTypedArray()[1].substring(0, 3)
                                return@sortedWith message1.compareTo(message2)
                            }
                        }
                        return@sortedWith o1.title.compareTo(o2.title)
                    }
                    nBadges2 - nBadges1
                }
                projectDataList.forEach(Consumer { projectData: ProjectData? ->
                    writeProjectData(
                        badgeType,
                        fileWriter,
                        projectData
                    )
                })
                writeHyperLinks(fileWriter)
                fileWriter.close()
            } catch (e: IOException) {
                logger.error("Error!", e)
                exitProcess(1)
            }
        })
    }

    @Throws(IOException::class)
    private fun writeHyperLinks(fileWriter: FileWriter) {
        val links = BadgeParser.badgeTypes.values.stream()
            .map { badgeType: BadgeType ->
                val title = getTitle(badgeType)
                String.format(
                    "[%s](./%s)",
                    title,
                    badgeType.destinationFile
                )
            }
            .collect(Collectors.joining(" - "))
        fileWriter.write(String.format("## %s \n\n", links))
    }

    private fun getTitle(badgeType: BadgeType): String {
        return (if (Objects.isNull(badgeType.title)) {
            badgeType.destinationFile.replace(".md", "")
        } else badgeType.title) ?: ""
    }

    @Throws(IOException::class)
    private fun writeTitles(badgeType: BadgeType, fileWriter: FileWriter) {
        val badgeSettingGroup = BadgeParser.badgeSettingGroups[badgeType]
        fileWriter.write("|Project")
        badgeSettingGroup!!.badgeSettingList.forEach(Consumer { badgePattern: BadgePattern ->
            try {
                if (badgePattern.title != "Project") {
                    fileWriter.write("|")
                    fileWriter.write(badgePattern.title)
                }
            } catch (e: IOException) {
                logger.error("Error!", e)
                exitProcess(1)
            }
        })
        fileWriter.write("|\n")
        fileWriter.flush()
    }

    private fun writeProjectData(badgeType: BadgeType, fileWriter: FileWriter, projectData: ProjectData?) {
        try {
            val badgeGroup = projectData!!.badgeGroupMap[badgeType]
            BadgeParser.badgeSettingGroups[badgeType]!!.badgeSettingList
                .forEach(Consumer { badgePattern: BadgePattern ->
                    val badge = badgeGroup?.badgeHashMap?.get(badgePattern.pattern)
                    if (badgePattern.title == "Project" && Objects.isNull(badge)) {
                        try {
                            fileWriter.write("|")
                            fileWriter.write(projectData.title)
                        } catch (e: IOException) {
                            logger.error("Error!", e)
                            exitProcess(1)
                        }
                    } else {
                        badge?.let { writeBadgeElement(fileWriter, it) }
                    }
                })
            fileWriter.write("|\n")
            fileWriter.flush()
        } catch (e: IOException) {
            logger.error("Error!", e)
            exitProcess(1)
        }
    }

    private fun writeBadgeElement(fileWriter: FileWriter, badge: Badge) {
        try {
            fileWriter.write("|")
            if (Objects.isNull(badge)) {
                fileWriter.write("---")
            } else {
                fileWriter.write(badge.badgeText)
            }
        } catch (e: IOException) {
            logger.error("Error!", e)
            exitProcess(1)
        }
    }

    @Throws(IOException::class)
    private fun writeTopTable(badgeType: BadgeType, fileWriter: FileWriter) {
        val badgeSettingGroup = BadgeParser.badgeSettingGroups[badgeType]
        badgeSettingGroup?.badgeSettingList?.forEach(Consumer {
            try {
                fileWriter.write("|---")
            } catch (e: IOException) {
                logger.error("Error!", e)
                exitProcess(1)
            }
        })
        fileWriter.write("|\n")
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(FileWriterService::class.java)
    }

}

fun Path.findReadmeFileNameWithDefault() = findReadmeFileName() ?: "Readme.md"

fun Path.findReadmeFileName() = toFile()
    .list { _, name -> name.lowercase().endsWith("readme.md") }
    .takeIf { it?.isNotEmpty() ?: false }
    ?.let { it[0] }
