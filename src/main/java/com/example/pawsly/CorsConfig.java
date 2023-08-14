package com.example.pawsly;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081","http://3.39.25.7:8080")
                .allowedMethods("POST","GET","DELETE","PUT","PATCH")
                .allowCredentials(true)
                .maxAge(3600);
    }
}