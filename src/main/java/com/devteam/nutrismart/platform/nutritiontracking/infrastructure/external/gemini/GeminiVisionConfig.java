package com.devteam.nutrismart.platform.nutritiontracking.infrastructure.external.gemini;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuración de Spring para el adaptador externo de Gemini Vision.
 * <p>
 * Registra un bean {@link RestTemplate} dedicado para las llamadas a la API
 * de Gemini Vision, identificado con el calificador {@code geminiRestTemplate}.
 * </p>
 */
@Configuration
public class GeminiVisionConfig {

    /**
     * Crea el {@link RestTemplate} utilizado exclusivamente por {@code GeminiVisionClient}
     * para comunicarse con la API de Gemini Vision.
     *
     * @return instancia de {@link RestTemplate} con configuración por defecto
     */
    @Bean
    @Qualifier("geminiRestTemplate")
    public RestTemplate geminiRestTemplate() {
        return new RestTemplate();
    }
}
