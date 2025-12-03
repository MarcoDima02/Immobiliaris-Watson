package com.residea.residea.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve i PDF dei contratti
        registry.addResourceHandler("/uploads/contratti/**")
                .addResourceLocations("file:uploads/contratti/");
        
        // Serve le immagini degli immobili
        registry.addResourceHandler("/uploads/immagini/**")
                .addResourceLocations("file:uploads/immagini/");
    }
}
