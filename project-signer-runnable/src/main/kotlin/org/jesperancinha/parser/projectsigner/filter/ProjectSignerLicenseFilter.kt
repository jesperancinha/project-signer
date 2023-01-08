package org.jesperancinha.parser.projectsigner.filter

import java.util.*
import java.util.function.Supplier

object ProjectSignerLicenseFilter {
    const val ISC_LICENSE_MARK = "ISC License"
    const val APACHE_LICENSE_MARK = "Apache License"
    const val MIT_LICENSE_MARK = "The MIT License"
    @Throws(Throwable::class)
    fun getLicense(rawLicenses: List<String>?, license: String?): String {
        if (Objects.isNull(license) || license!!.contains(APACHE_LICENSE_MARK)) {
            return rawLicenses!!.stream().filter { lic: String -> lic.contains(APACHE_LICENSE_MARK) }
                .findFirst().orElseThrow(Supplier<Throwable> { RuntimeException("No license found!") })
        } else if (license.contains(ISC_LICENSE_MARK)) {
            return rawLicenses!!.stream().filter { lic: String -> lic.contains(ISC_LICENSE_MARK) }
                .findFirst().orElseThrow(Supplier<Throwable> { RuntimeException("No license found!") })
        } else if (license.contains(MIT_LICENSE_MARK)) {
            return rawLicenses!!.stream().filter { lic: String -> lic.contains(MIT_LICENSE_MARK) }
                .findFirst().orElseThrow(Supplier<Throwable> { RuntimeException("No license found!") })
        }
        throw RuntimeException("No license found!")
    }
}