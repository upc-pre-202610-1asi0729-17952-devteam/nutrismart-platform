package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.external.googlehealth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuración del {@link RestTemplate} dedicado a las llamadas OAuth y de datos
 * contra la API de Google Health.
 */
@Configuration
public class GoogleHealthConfig {

    @Bean
    @Qualifier("googleHealthRestTemplate")
    public RestTemplate googleHealthRestTemplate() {
        return new RestTemplate();
    }
}
