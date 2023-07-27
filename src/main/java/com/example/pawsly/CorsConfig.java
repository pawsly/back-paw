package com.example.pawsly;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://192.168.0.3:8080/user/signup","https://192.168.0.3:8080/user/signup",
                        "http://192.168.0.3:8080/user/login","https://192.168.0.3:8080/user/login" ,
                        "http://192.168.0.3:8080/board/post","https://192.168.0.3:8080/board/post",
                        "http://192.168.0.3:8080/board/post/{id}","https://192.168.0.3:8080/board/post/{id}"
                        ,"http://192.168.0.3:8080/upload","https://192.168.0.3:8080/upload")
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowCredentials(true)
                .maxAge(3600);

    }
}