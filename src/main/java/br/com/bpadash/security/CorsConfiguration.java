package br.com.bpadash.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("Access-Control-Allow-Origin",
                        "https://bpadash.com/",
                        "http://localhost:9999/",
                        "http://localhost:3000/"
                )
                .allowedMethods("*");
    }
}