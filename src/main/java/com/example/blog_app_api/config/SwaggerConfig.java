package com.example.blog_app_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {
    public  static final String AUTHORIZATION_HEADER="Authorization";
    private ApiKey apiKey()
    {
        return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
    }
    private List<SecurityContext> securityContexts()
    {
        return Arrays.asList(SecurityContext.builder().securityReferences(defaultAuth()).build());
    }
    private List<SecurityReference> securityReferences()
    {
        AuthorizationScope scopes = new AuthorizationScope("global","accessEverything");
        return Arrays.asList(new SecurityReference("scope",new AuthorizationScope[]{scopes}));
    }
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo())
                .securityContexts(securityContexts())
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo getInfo()
    {
        return new ApiInfo("Blogging Application : Backend Course","This project is developed by abishek",
                "1.0","Terms of service",new Contact("abishek","google.com","abc@gmail.com")
                ,"License API","license url", Collections.emptyList());
    }
}
