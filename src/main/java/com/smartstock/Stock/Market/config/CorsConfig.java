package com.smartstock.Stock.Market.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allows CORS for all endpoints
                .allowedOrigins("*") // Allows requests from any origin
                .allowedMethods("*") // Allows all HTTP methods
                .allowedHeaders("*"); // Allows all headers
    }
}