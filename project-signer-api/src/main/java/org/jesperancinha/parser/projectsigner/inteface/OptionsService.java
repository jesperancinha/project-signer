package org.jesperancinha.parser.projectsigner.inteface;

public interface OptionsService<T, P> {

    T processOptions(String[] args);

    T getProjectSignerOptions();

    P getCommonNamingParser();
}
