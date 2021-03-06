package fr.graynaud.eu4saveconverter.config;

import fr.graynaud.eu4saveconverter.config.properties.WebProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
@OpenAPIDefinition
@SecurityScheme(type = SecuritySchemeType.APIKEY, name = HttpHeaders.AUTHORIZATION, in = SecuritySchemeIn.HEADER, bearerFormat = "bearer")
public class OpenApiConfig {

    private final WebProperties webProperties;

    public OpenApiConfig(WebProperties webProperties) {this.webProperties = webProperties;}

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(webProperties.getTitle())
                                .description(webProperties.getDescription())
                                .version(webProperties.getVersion())
                                .license(new License().name("Apache License 2.0")
                                                      .url("http://www.apache.org/licenses/")));
    }
}
