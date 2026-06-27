package it.uniroma3.siw.config; 

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Permette tutte le richieste verso URL che iniziano con /rest/
        registry.addMapping("/rest/**")
            .allowedOrigins("http://localhost:5173") // La porta del tuo server React/Vite
            .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}