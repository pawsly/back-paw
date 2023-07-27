package com.example.pawsly;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://3.39.25.7:8080/user/signup")
                .allowedMethods("POST","GET","PUT")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3000);
    }
}