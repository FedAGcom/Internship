package com.fedag.internship.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * class SwaggerConfig for configuration swagger.
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@OpenAPIDefinition(info = @Info(title = "Internship", version = "1.0"))
@Configuration
public class SwaggerConfig {
}
