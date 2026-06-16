package com.devteam.nutrismart.platform.smartrecommendation.application.internal.commandservices;

import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.WeatherSnapshotCommandFailure;
import com.devteam.nutrismart.platform.smartrecommendation.application.commandservices.WeatherSnapshotCommandService;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.CreateWeatherSnapshotCommand;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.SyncWeatherSnapshotCommand;
import com.devteam.nutrismart.platform.smartrecommendation.application.commands.UpdateWeatherSnapshotCommand;
import com.devteam.nutrismart.platform.smartrecommendation.application.ports.ExternalWeatherPort;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.aggregates.WeatherSnapshot;
import com.devteam.nutrismart.platform.smartrecommendation.domain.model.repositories.WeatherSnapshotRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de comandos para instantáneas meteorológicas.
 * <p>
 * Gestiona las operaciones de escritura del agregado {@code WeatherSnapshot}:
 * <ul>
 *   <li>Creación de un nuevo snapshot con ciudad, país, temperatura en Celsius y condición climática.</li>
 *   <li>Actualización de los datos de un snapshot existente por identificador.</li>
 *   <li>Sincronización del snapshot con la fuente de datos meteorológica externa (OpenWeatherMap)
 *       a través del puerto {@code ExternalWeatherPort}: actualiza el snapshot si ya existe
 *       para la ciudad/país indicados o crea uno nuevo en caso contrario.</li>
 * </ul>
 * </p>
 * <p>
 * Forma parte de la capa de aplicación interna del módulo {@code smartrecommendation},
 * siguiendo la arquitectura hexagonal y el patrón CQRS.
 * </p>
 */
@Service
@Transactional
public class WeatherSnapshotCommandServiceImpl implements WeatherSnapshotCommandService {

    private static final Logger log = LoggerFactory.getLogger(WeatherSnapshotCommandServiceImpl.class);

    private final WeatherSnapshotRepository snapshotRepository;
    private final ExternalWeatherPort externalWeatherPort;

    public WeatherSnapshotCommandServiceImpl(WeatherSnapshotRepository snapshotRepository,
                                             ExternalWeatherPort externalWeatherPort) {
        this.snapshotRepository = snapshotRepository;
        this.externalWeatherPort = externalWeatherPort;
    }

    @Override
    public Result<WeatherSnapshot, WeatherSnapshotCommandFailure> handle(CreateWeatherSnapshotCommand command) {
        try {
            WeatherSnapshot snapshot = WeatherSnapshot.create(
                    command.city(),
                    command.country(),
                    command.temperatureCelsius(),
                    command.condition()
            );
            return Result.success(snapshotRepository.save(snapshot));
        } catch (Exception ex) {
            return Result.failure(new WeatherSnapshotCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<WeatherSnapshot, WeatherSnapshotCommandFailure> handle(UpdateWeatherSnapshotCommand command) {
        try {
            return snapshotRepository.findById(command.id())
                    .map(snapshot -> {
                        snapshot.update(command.city(), command.country(), command.temperatureCelsius(), command.condition());
                        return Result.<WeatherSnapshot, WeatherSnapshotCommandFailure>success(snapshotRepository.save(snapshot));
                    })
                    .orElse(Result.failure(new WeatherSnapshotCommandFailure.SnapshotNotFound(command.id())));
        } catch (Exception ex) {
            return Result.failure(new WeatherSnapshotCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<WeatherSnapshot, WeatherSnapshotCommandFailure> handle(SyncWeatherSnapshotCommand command) {
        try {
            log.info("[WEATHER SYNC] Requesting OWM for city='{}' country='{}'", command.city(), command.country());
            var data = externalWeatherPort.fetchCurrentWeather(command.city(), command.country());
            log.info("[WEATHER SYNC] OWM responded: temp={}°C condition='{}'", data.temperatureCelsius(), data.condition());
            return snapshotRepository.findByCityAndCountry(command.city(), command.country())
                    .map(snapshot -> {
                        log.info("[WEATHER SYNC] Existing snapshot found (id={}), calling update()", snapshot.getId());
                        snapshot.update(command.city(), command.country(), data.temperatureCelsius(), data.condition());
                        WeatherSnapshot saved = snapshotRepository.save(snapshot);
                        log.info("[WEATHER SYNC] Snapshot updated — updatedAt persisted: {}", saved.getUpdatedAt());
                        return Result.<WeatherSnapshot, WeatherSnapshotCommandFailure>success(saved);
                    })
                    .orElseGet(() -> {
                        log.info("[WEATHER SYNC] No existing snapshot, calling create()");
                        var created = WeatherSnapshot.create(command.city(), command.country(), data.temperatureCelsius(), data.condition());
                        WeatherSnapshot saved = snapshotRepository.save(created);
                        log.info("[WEATHER SYNC] Snapshot created — updatedAt persisted: {}", saved.getUpdatedAt());
                        return Result.success(saved);
                    });
        } catch (Exception ex) {
            log.error("[WEATHER SYNC] Exception during sync for city='{}' country='{}': {}", command.city(), command.country(), ex.getMessage(), ex);
            return Result.failure(new WeatherSnapshotCommandFailure.InvalidData(ex.getMessage()));
        }
    }
}
