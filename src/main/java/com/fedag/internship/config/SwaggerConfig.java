package com.fedag.internship.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * class SwaggerConfig for configuration swagger.
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "Internship", version = "1.0"))
@SecurityScheme(name = "bearer-token-auth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class SwaggerConfig {
}
