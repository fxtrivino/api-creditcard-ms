package com.bci.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfiguration {
	
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
        		.addSecurityItem(new SecurityRequirement().
        	            addList("Bearer Authentication"))
        	        .components(new Components().addSecuritySchemes
        	            ("Bearer Authentication", createAPIKeyScheme()))
        		.info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("API Credit Card")
                .description("API Credit Card")
                .version("v1.0.0")
                .contact(apiContact())
                .license(apiLicence());
    }

    private License apiLicence() {
        return new License()
                .name("BCI S.A. Licence")
                .url("https://www.bci.com");
    }

    private Contact apiContact() {
        return new Contact()
                .name("Xavier Trivino")
                .email("fxtrivino@gmail.com")
                .url("https://www.bci.com");
    }
    
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("bearer");
    }
    
    
}
