package org.jesperancinha.parser.projectsigner.filter;

import java.util.List;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

public class ProjectSignerLicenseFilter {

    public static final String ISC_LICENSE_MARK = "ISC License";
    public static final String APACHE_LICENSE_MARK = "Apache License";
    public static final String MIT_LICENSE_MARK = "The MIT License";

    public static String getLicense(List<String> rawLicenses, String license) throws Throwable {
        if (isNull(license) || license.contains(APACHE_LICENSE_MARK)) {
            return rawLicenses.stream().filter(lic -> lic.contains(APACHE_LICENSE_MARK))
                    .findFirst().orElseThrow((Supplier<Throwable>) () -> new RuntimeException("No license found!"));
        } else if (license.contains(ISC_LICENSE_MARK)) {
            return rawLicenses.stream().filter(lic -> lic.contains(ISC_LICENSE_MARK))
                    .findFirst().orElseThrow((Supplier<Throwable>) () -> new RuntimeException("No license found!"));
        } else if (license.contains(MIT_LICENSE_MARK)) {
            return rawLicenses.stream().filter(lic -> lic.contains(MIT_LICENSE_MARK))
                    .findFirst().orElseThrow((Supplier<Throwable>) () -> new RuntimeException("No license found!"));
        }
        throw new RuntimeException("No license found!");
    }
}
