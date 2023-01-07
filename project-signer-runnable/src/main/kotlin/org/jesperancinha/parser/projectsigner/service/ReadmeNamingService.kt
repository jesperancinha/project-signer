package org.jesperancinha.parser.projectsigner.service

import org.jesperancinha.parser.markdowner.filter.FileFilterChain
import org.springframework.stereotype.Service
import java.io.*
import java.nio.file.Path

/**
 * A service responsible for assuring the existence of Readme files in all recognizable projects: Maven, Node package manager, Gradle and Simple build tool.
 */
@Service
open class ReadmeNamingService(
    private val fileFilterChain: FileFilterChain,
    private val optionsService: OptionsService
) {
    /**
     * Builds a stream of a Readme marked down texts taking a [Path] as a reference.
     * If a Readme.md file already exists, it will return a Stream with its content.
     * If a Readme.md File does not exist then it will create a new stream with the project title as the first header.
     *
     *
     * Special algorithms will determine the name of the project automatically
     *
     * @param path The path where to search and determine the right text for the markdown Readme file
     * @return The calculated Input text stream
     * @throws IOException Any IO Exception that may occur
     */
    @Throws(IOException::class)
    open fun buildReadmeStream(path: Path?): InputStream? {
       return optionsService.commonNamingParser?.fileFilterChain(fileFilterChain)?.build()
            ?.buildReadmeStream(path)
    }
}