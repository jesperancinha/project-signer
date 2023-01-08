package org.jesperancinha.parser.projectsigner.service

import lombok.extern.slf4j.Slf4j
import org.jesperancinha.parser.projectsigner.filter.ProjectSignerVisitor
import org.springframework.stereotype.Service
import java.io.IOException
import java.nio.file.*
import java.util.*

@Slf4j
@Service
open class FinderService(
    private val generatorService: GeneratorSevice,
    private val templateService: TemplateService
) {
    @Throws(IOException::class)
    open fun iterateThroughFilesAndFolders(rootPath: Path?): Path? {
        val allParagraphs = templateService.findAllParagraphs()
        val readAllLicenses = templateService.readAllLicenses()
       return Files.walkFileTree(
            rootPath,
            EnumSet.of(FileVisitOption.FOLLOW_LINKS), Int.MAX_VALUE,
            ProjectSignerVisitor(
                generatorService = generatorService,
                allParagraphs = allParagraphs,
                allLicenseText = readAllLicenses
            )
        )
    }
}