package org.jesperancinha.parser.projectsigner.configuration

import org.jesperancinha.parser.markdowner.filter.FileFilterChain
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class ProjectSignerConfiguration {
    @Bean
    open fun fileFilterChain(): FileFilterChain = FileFilterChain.createDefaultChain()
}