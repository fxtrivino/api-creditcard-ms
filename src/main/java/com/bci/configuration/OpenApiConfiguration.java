package com.bci.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfiguration {
	
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(apiInfo());
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
    
    
}
