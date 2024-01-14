package org.jesperancinha.parser.projectsigner.service

import org.jesperancinha.parser.projectsigner.filter.ProjectSignerVisitor
import org.springframework.stereotype.Service
import java.io.IOException
import java.nio.file.*
import java.util.*

@Service
open class FinderService(
    private val generatorService: GeneratorSevice,
    private val templateService: TemplateService
) {
    @Throws(IOException::class)
    open fun iterateThroughFilesAndFolders(rootPath: Path): Path? {
        val allParagraphs = templateService.findAllParagraphs()
        val allRedirectParagraphs = templateService.findAllRedirectParagraphs()
        val readAllLicenses = templateService.readAllLicenses()
       return Files.walkFileTree(
            rootPath,
            EnumSet.of(FileVisitOption.FOLLOW_LINKS), Int.MAX_VALUE,
            ProjectSignerVisitor(
                generatorService = generatorService,
                allParagraphs = allParagraphs,
                allRedirectParagraphs = allRedirectParagraphs,
                allLicenseText = readAllLicenses
            )
        )
    }
}