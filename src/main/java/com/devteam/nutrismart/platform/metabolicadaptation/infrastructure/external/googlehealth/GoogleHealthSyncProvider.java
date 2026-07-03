package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.external.googlehealth;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.services.ImportedActivity;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.services.WearableAuthorization;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.services.WearableSyncException;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.services.WearableSyncProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de {@link WearableSyncProvider} respaldada por la API de Google Health
 * ({@code health.googleapis.com/v4}).
 * <p>
 * Realiza los intercambios OAuth2 (authorization-code y refresh-token) contra el endpoint de
 * tokens de Google, luego lee los data points de tipo {@code exercise} del usuario y los mapea a
 * {@link ImportedActivity}. Ante un error de transporte/HTTP lanza {@link WearableSyncException}
 * (nunca fabrica datos): el servicio de comando lo traduce a un fallo de sincronización visible.
 * Una respuesta exitosa pero vacía se devuelve tal cual (lista vacía = "sin sesiones").
 * </p>
 */
@Component
public class GoogleHealthSyncProvider implements WearableSyncProvider {

    private static final Logger log = LoggerFactory.getLogger(GoogleHealthSyncProvider.class);
    private static final String DEFAULT_ACTIVITY_TYPE = "walking";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String tokenEndpoint;
    private final String baseUrl;
    private final String activityDataType;

    public GoogleHealthSyncProvider(
            @Qualifier("googleHealthRestTemplate") RestTemplate restTemplate,
            @Value("${google.health.client-id:}") String clientId,
            @Value("${google.health.client-secret:}") String clientSecret,
            @Value("${google.health.redirect-uri:}") String redirectUri,
            @Value("${google.health.token-endpoint:https://oauth2.googleapis.com/token}") String tokenEndpoint,
            @Value("${google.health.base-url:https://health.googleapis.com}") String baseUrl,
            @Value("${google.health.activity-data-type:exercise}") String activityDataType) {
        this.restTemplate = restTemplate;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.tokenEndpoint = tokenEndpoint;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.activityDataType = activityDataType;
    }

    @Override
    public WearableAuthorization authorize(String oauthCode) {
        if (clientId.isBlank() || clientSecret.isBlank())
            return WearableAuthorization.failure("Google Health OAuth client is not configured.");

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("code", oauthCode);
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("redirect_uri", redirectUri);
        form.add("grant_type", "authorization_code");
        return exchange(form);
    }

    @Override
    public WearableAuthorization refresh(String refreshToken) {
        if (clientId.isBlank() || clientSecret.isBlank())
            return WearableAuthorization.failure("Google Health OAuth client is not configured.");

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("refresh_token", refreshToken);
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("grant_type", "refresh_token");
        return exchange(form);
    }

    @Override
    public List<ImportedActivity> fetchActivities(String accessToken, LocalDate from, LocalDate to) {
        try {
            // Google Health usa un filtro AIP-160 (no startTime/endTime). El rango civil_start_time
            // es semiabierto [from, to+1día) para incluir el día `to` completo. El valor es un
            // literal de fecha ISO 8601 entre comillas dobles (p. ej. "2026-06-03").
            LocalDate toExclusive = to.plusDays(1);
            String filter = "%s.interval.civil_start_time >= \"%s\" AND %s.interval.civil_start_time < \"%s\""
                    .formatted(activityDataType, from, activityDataType, toExclusive);

            // Codificamos una sola vez y pasamos un URI: la sobrecarga exchange(String) vuelve a
            // codificar la cadena ya codificada (el "%22" de las comillas pasa a "%2522"), así que a
            // Google le llegan comillas literales y el ':' de la hora rompe la sintaxis AIP-160.
            // Con un URI ya codificado no hay re-encoding.
            URI uri = UriComponentsBuilder
                    .fromUriString(baseUrl + "/v4/users/me/dataTypes/" + activityDataType + "/dataPoints")
                    .queryParam("pageSize", 25)
                    .queryParam("filter", filter)
                    .encode()
                    .build()
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            ResponseEntity<String> response = restTemplate.exchange(
                    uri, HttpMethod.GET, new HttpEntity<>(headers), String.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                log.warn("Google Health dataPoints call returned {}.", response.getStatusCode());
                throw new WearableSyncException(
                        "Google Health returned " + response.getStatusCode());
            }
            // Una respuesta exitosa es autoritativa: una lista vacía significa "sin sesiones".
            return parseExercises(response.getBody());
        } catch (WearableSyncException ex) {
            throw ex;
        } catch (Exception ex) {
            log.warn("Google Health activity fetch failed.", ex);
            throw new WearableSyncException("Google Health activity fetch failed", ex);
        }
    }

    /** Envía el formulario OAuth al endpoint de tokens de Google y mapea la respuesta. */
    private WearableAuthorization exchange(MultiValueMap<String, String> form) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                    tokenEndpoint, new HttpEntity<>(form, headers), TokenResponse.class);

            TokenResponse token = response.getBody();
            if (!response.getStatusCode().is2xxSuccessful() || token == null || token.accessToken() == null)
                return WearableAuthorization.failure("Token endpoint returned no access token.");

            Instant expiresAt = token.expiresIn() > 0 ? Instant.now().plusSeconds(token.expiresIn()) : null;
            return new WearableAuthorization(true, token.accessToken(), token.refreshToken(), expiresAt, null);
        } catch (Exception ex) {
            log.warn("Google token exchange failed.", ex);
            return WearableAuthorization.failure("Token exchange failed: " + ex.getMessage());
        }
    }

    /** Mapea las sesiones de ejercicio del {@code ListDataPointsResponse} a {@link ImportedActivity}. */
    private List<ImportedActivity> parseExercises(String body) throws Exception {
        List<ImportedActivity> activities = new ArrayList<>();
        JsonNode root = objectMapper.readTree(body);
        JsonNode points = root.get("dataPoints");
        if (points == null || !points.isArray()) return activities;

        for (JsonNode point : points) {
            JsonNode ex = point.get("exercise");
            if (ex == null || !ex.isObject()) continue;

            JsonNode interval = ex.get("interval");
            LocalDate date = readIntervalDate(interval);
            int durationMinutes = readDurationMinutes(ex, interval);
            if (durationMinutes <= 0) continue;

            String activityType = mapExerciseType(text(ex, "exerciseType"));
            double calories = readCalories(ex);
            activities.add(new ImportedActivity(activityType, durationMinutes, calories, date));
        }
        return activities;
    }

    /** Lee el día de la sesión, prefiriendo la fecha civil (local), cayendo a hoy (UTC). */
    private LocalDate readIntervalDate(JsonNode interval) {
        if (interval != null && interval.has("civilStartTime")) {
            JsonNode d = interval.get("civilStartTime").get("date");
            if (d != null && d.has("year") && d.has("month") && d.has("day")) {
                try {
                    return LocalDate.of(d.get("year").asInt(), d.get("month").asInt(), d.get("day").asInt());
                } catch (Exception ignored) { /* fall through */ }
            }
        }
        return LocalDate.now();
    }

    /** Lee la duración activa en minutos enteros, cayendo al span del intervalo. */
    private int readDurationMinutes(JsonNode exercise, JsonNode interval) {
        double seconds = parseDurationSeconds(text(exercise, "activeDuration"));
        if (seconds <= 0 && interval != null && interval.has("startTime") && interval.has("endTime")) {
            try {
                Instant start = Instant.parse(interval.get("startTime").asText());
                Instant end = Instant.parse(interval.get("endTime").asText());
                seconds = (end.toEpochMilli() - start.toEpochMilli()) / 1000.0;
            } catch (Exception ignored) { /* keep 0 */ }
        }
        return (int) Math.round(seconds / 60.0);
    }

    /** Lee {@code metricsSummary.caloriesKcal}, redondeado a kilocalorías enteras. */
    private double readCalories(JsonNode exercise) {
        JsonNode ms = exercise.get("metricsSummary");
        if (ms != null && ms.has("caloriesKcal")) return Math.round(ms.get("caloriesKcal").asDouble());
        return 0d;
    }

    /**
     * Mapea un {@code exerciseType} de Google Health a uno de los tipos de actividad canónicos de
     * NutriSmart (las claves de {@code MET_VALUES} / i18n {@code activity_types}).
     */
    private String mapExerciseType(String type) {
        String t = type == null ? "" : type.toUpperCase();
        if (t.isEmpty()) return DEFAULT_ACTIVITY_TYPE;
        if (has(t, "HIK", "RUCK")) return "hiking";
        if (has(t, "WALK")) return "walking";
        if (has(t, "RUN", "JOG", "TREADMILL", "SPRINT")) return "running";
        if (has(t, "SWIM")) return "swimming";
        if (has(t, "BIK", "CYCL", "SPIN")) return "cycling";
        if (has(t, "ROW")) return "rowing";
        if (has(t, "ELLIPTIC")) return "elliptical";
        if (has(t, "STAIR", "STEP")) return "stair_climbing";
        if (has(t, "CROSSFIT", "CIRCUIT", "BOOTCAMP")) return "crossfit";
        if (has(t, "HIIT", "INTERVAL", "TABATA")) return "hiit";
        if (has(t, "JUMP_ROPE", "SKIP")) return "jump_rope";
        if (has(t, "STRENGTH", "WEIGHT", "LIFT", "CALISTHEN", "BODY_WEIGHT", "CORE")) return "weight_training";
        if (has(t, "STRETCH", "MOBILITY", "FLEXIBILITY")) return "stretching";
        if (has(t, "YOGA")) return "yoga";
        if (has(t, "PILATES", "BARRE")) return "pilates";
        if (has(t, "BOX", "KICK")) return "boxing";
        if (has(t, "MUAY", "MARTIAL", "KARATE", "TAEKWON", "JIU", "WRESTL", "JUDO")) return "martial_arts";
        if (has(t, "DANC", "ZUMBA", "BALLET", "BALLROOM", "HIP_HOP", "JAZZ", "TANGO", "SALSA")) return "dancing";
        if (has(t, "BASKETBALL")) return "basketball";
        if (has(t, "SOCCER", "FOOTBALL")) return "football";
        if (has(t, "TENNIS")) return "tennis";
        if (has(t, "VOLLEYBALL")) return "volleyball";
        return DEFAULT_ACTIVITY_TYPE;
    }

    private boolean has(String t, String... keys) {
        for (String k : keys) if (t.contains(k)) return true;
        return false;
    }

    /** Parsea una duración google (p. ej. {@code "1830s"}) a segundos. */
    private double parseDurationSeconds(String duration) {
        if (duration == null || duration.isBlank()) return 0;
        String trimmed = duration.replace("s", "").replace("S", "");
        try { return Double.parseDouble(trimmed); } catch (NumberFormatException e) { return 0; }
    }

    private String text(JsonNode node, String field) {
        JsonNode v = node.get(field);
        return v != null && v.isTextual() ? v.asText() : null;
    }

    /** Subconjunto de la respuesta del endpoint de tokens OAuth2 de Google. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    private record TokenResponse(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("refresh_token") String refreshToken,
            @JsonProperty("expires_in") long expiresIn
    ) {}
}
