package com.fedag.internship.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * class AuditConfig for configuration auditing entities.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
@Configuration
@EnableJpaAuditing
public class AuditConfig {
}
