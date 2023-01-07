package org.jesperancinha.parser.projectsigner.filter

import java.nio.file.Path
import java.util.*

object ProjectSignerFilterHelper {
    /**
     * Checks if the current path matches folders where no processing should happen.
     *
     * @param projectPath Project [Path] to be analysed for the existing of a package managing system
     * @return If the folder is ignorable
     */
    fun isIgnorableFolder(projectPath: Path): Boolean {
        val fileName = projectPath.fileName
        if (Objects.isNull(fileName)) {
            return true
        }
        val directoryName = fileName.toString()
        return (directoryName.equals("generated", ignoreCase = true)
                ||
                directoryName.equals("src", ignoreCase = true)
                ||
                directoryName.equals("java", ignoreCase = true)
                ||
                directoryName.equals("build", ignoreCase = true)
                ||
                directoryName.equals("classes", ignoreCase = true)
                ||
                directoryName.equals("sources", ignoreCase = true)
                ||
                directoryName.equals("dist", ignoreCase = true)
                ||
                directoryName.equals("target", ignoreCase = true)
                ||
                directoryName.equals("node_modules", ignoreCase = true)
                ||
                directoryName.equals("resources", ignoreCase = true)
                ||
                directoryName.equals("project-signer-templates", ignoreCase = true)
                ||
                directoryName.equals("test-classes", ignoreCase = true))
    }
}