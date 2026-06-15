package com.devteam.nutrismart.platform.shared.infrastructure.i18n.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * Configuración de internacionalización (i18n) de la plataforma.
 * <p>
 * Configura el {@link LocaleResolver} para resolver el idioma a partir del encabezado
 * {@code Accept-Language} de cada petición HTTP. Soporta inglés ({@code en}) y
 * español ({@code es}), con inglés como idioma por defecto.
 * </p>
 */
@Configuration
public class LocaleConfiguration {

    @Bean
    public LocaleResolver localeResolver() {
        var resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        resolver.setSupportedLocales(List.of(Locale.ENGLISH, Locale.forLanguageTag("es")));
        return resolver;
    }
}
