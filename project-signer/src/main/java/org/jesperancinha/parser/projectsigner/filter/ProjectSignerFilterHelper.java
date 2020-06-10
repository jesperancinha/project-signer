package org.jesperancinha.parser.projectsigner.filter;

import java.nio.file.Path;

public class ProjectSignerFilterHelper {

    /**
     * Checks if the current path matches folders where no processing should happen.
     *
     * @param projectPath Project {@link Path} to be analysed for the existing of a package managing system
     * @return If the folder is ignorable
     */
    public static boolean isIgnorableFolder(Path projectPath) {
        final String directoryName = projectPath.getFileName().toString();
        return directoryName.equalsIgnoreCase("generated")
                ||
                directoryName.equalsIgnoreCase("src")
                ||
                directoryName.equalsIgnoreCase("java")
                ||
                directoryName.equalsIgnoreCase("build")
                ||
                directoryName.equalsIgnoreCase("classes")
                ||
                directoryName.equalsIgnoreCase("sources")
                ||
                directoryName.equalsIgnoreCase("dist")
                ||
                directoryName.equalsIgnoreCase("target")
                ||
                directoryName.equalsIgnoreCase("node_modules")
                ||
                directoryName.equalsIgnoreCase("resources")
                ||
                directoryName.equalsIgnoreCase("project-signer-templates")
                ||
                directoryName.equalsIgnoreCase("test-classes");
    }
}
