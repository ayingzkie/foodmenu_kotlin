package com.example.foodmenukotlin.config


import com.example.foodmenukotlin.security.UserPrincipal
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

import java.util.Optional
import java.util.UUID

@Configuration
@EnableJpaAuditing
class AuditingConfig {

    @Bean
    fun auditorProvider(): AuditorAware<UUID> {
        return SpringSecurityAuditAwareImpl()
    }
}

internal class SpringSecurityAuditAwareImpl : AuditorAware<UUID> {

    override fun getCurrentAuditor(): Optional<UUID> {
        val authentication = SecurityContextHolder.getContext().authentication

        if (authentication == null ||
                !authentication.isAuthenticated ||
                authentication is AnonymousAuthenticationToken) {
            return Optional.empty()
        }

        val userPrincipal = authentication.principal as UserPrincipal

        return Optional.ofNullable(userPrincipal.id)
    }
}
