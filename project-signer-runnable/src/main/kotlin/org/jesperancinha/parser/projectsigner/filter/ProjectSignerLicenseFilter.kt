package org.jesperancinha.parser.projectsigner.filter

object ProjectSignerLicenseFilter {
    const val ISC_LICENSE_MARK = "ISC License"
    const val APACHE_LICENSE_MARK = "Apache License"
    const val MIT_LICENSE_MARK = "The MIT License"
    @Throws(Throwable::class)
    fun getLicense(rawLicenses: List<String>?, license: String?): String {
        val licenses = rawLicenses.orEmpty()
        if (license == null || license.contains(APACHE_LICENSE_MARK)) {
            return licenses.firstOrNull { lic: String -> lic.contains(APACHE_LICENSE_MARK) }
                ?: throw RuntimeException("No license found!")
        } else if (license.contains(ISC_LICENSE_MARK)) {
            return licenses.firstOrNull { lic: String -> lic.contains(ISC_LICENSE_MARK) }
                ?: throw RuntimeException("No license found!")
        } else if (license.contains(MIT_LICENSE_MARK)) {
            return licenses.firstOrNull { lic: String -> lic.contains(MIT_LICENSE_MARK) }
                ?: throw RuntimeException("No license found!")
        }
        throw RuntimeException("No license found!")
    }
}