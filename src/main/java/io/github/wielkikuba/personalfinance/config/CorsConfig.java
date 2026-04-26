package io.github.wielkikuba.personalfinance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Mówi Springowi, że to klasa konfiguracyjna
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Odblokuj wszystkie adresy w API (np. /api/user, /api/category)
                .allowedOrigins("http://127.0.0.1:5500", "http://localhost:5500", "http://localhost:8080") // Pozwól na dostęp z Twojego Live Servera
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH") // Pozwól na te metody HTTP
                .allowedHeaders("*") // Pozwól na wysyłanie dowolnych nagłówków (np. JSON)
                .allowCredentials(true);
    }
}