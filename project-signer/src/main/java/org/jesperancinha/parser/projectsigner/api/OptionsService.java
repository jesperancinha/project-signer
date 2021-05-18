package org.jesperancinha.parser.projectsigner.api;

public interface OptionsService<T, P> {

    T processOptions(String[] args);

    T getProjectSignerOptions();

    P getCommonNamingParser();
}
