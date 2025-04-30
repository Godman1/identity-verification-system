package com.identity.verification.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Identity Verification API")
                        .description("APIs for managing users and admin approval in the identity verification system")
                        .version("1.0.0"));
    }
}
