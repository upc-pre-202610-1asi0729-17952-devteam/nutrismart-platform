package com.devteam.nutrismart.platform.metabolicadaptation.application.internal.commandservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.WearableConnectionCommandFailure;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.WearableConnectionCommandService;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.ConnectWearableCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.DeleteWearableConnectionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.SetWearableAutoSyncCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.SyncWearableConnectionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.ActivityLog;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.WearableConnection;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.ActivityLogRepository;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.WearableConnectionRepository;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.services.ImportedActivity;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.services.WearableAuthorization;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.services.WearableSyncException;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.services.WearableSyncProvider;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.WearableStatus;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementación transaccional del servicio de comandos para {@code WearableConnection}.
 * Gestiona el ciclo de conectar (con OAuth), sincronizar, auto-sync y desvincular, y orquesta
 * la importación de actividad desde el proveedor.
 */
@Service
@Transactional
public class WearableConnectionCommandServiceImpl implements WearableConnectionCommandService {

    private static final Logger log = LoggerFactory.getLogger(WearableConnectionCommandServiceImpl.class);
    private static final double DEFAULT_KCAL_PER_MINUTE = 6.0;

    private final WearableConnectionRepository repository;
    private final ActivityLogRepository activityLogRepository;
    private final WearableSyncProvider syncProvider;

    public WearableConnectionCommandServiceImpl(WearableConnectionRepository repository,
                                                ActivityLogRepository activityLogRepository,
                                                WearableSyncProvider syncProvider) {
        this.repository = repository;
        this.activityLogRepository = activityLogRepository;
        this.syncProvider = syncProvider;
    }

    @Override
    public Result<WearableConnection, WearableConnectionCommandFailure> handle(ConnectWearableCommand command) {
        try {
            var existing = repository.findByUserIdAndProvider(command.userId(), command.provider());
            if (existing.isPresent() && existing.get().getStatus() == WearableStatus.CONNECTED)
                return Result.failure(new WearableConnectionCommandFailure.AlreadyConnected(command.userId(), command.provider()));

            WearableConnection connection;
            try {
                connection = WearableConnection.create(command.userId(), command.provider(),
                        WearableStatus.DISCONNECTED, null, Instant.now(), false);
            } catch (IllegalArgumentException ex) {
                return Result.failure(new WearableConnectionCommandFailure.InvalidData(ex.getMessage()));
            }

            WearableAuthorization auth = syncProvider.authorize(command.oauthCode());
            if (!auth.success())
                return Result.failure(new WearableConnectionCommandFailure.AuthorizationFailed(auth.failureReason()));

            connection.applyAuthorization(auth.accessToken(), auth.refreshToken(), auth.expiresAt());
            connection.applySync(Instant.now());
            var saved = repository.save(connection);

            // Primer lote de actividad del día (best-effort): la autorización ya tuvo éxito, así que
            // un fallo del proveedor al importar no debe invalidar la conexión recién creada.
            try {
                importActivities(saved, LocalDate.now(ZoneOffset.UTC), LocalDate.now(ZoneOffset.UTC));
            } catch (WearableSyncException ex) {
                log.warn("Initial activity import failed for user {} right after connecting; " +
                        "connection kept, user can retry a manual sync.", command.userId(), ex);
            }

            return Result.success(saved);
        } catch (Exception ex) {
            log.error("Error connecting wearable for user {}", command.userId(), ex);
            return Result.failure(new WearableConnectionCommandFailure.SyncFailed(ex.getMessage()));
        }
    }

    @Override
    public Result<WearableConnection, WearableConnectionCommandFailure> handle(SyncWearableConnectionCommand command) {
        try {
            var found = repository.findById(command.id());
            if (found.isEmpty())
                return Result.failure(new WearableConnectionCommandFailure.WearableConnectionNotFound(command.id()));

            WearableConnection connection = found.get();
            LocalDate today = LocalDate.now(ZoneOffset.UTC);
            importActivities(connection, today.minusDays(7), today);

            connection.applySync(Instant.now());
            return Result.success(repository.save(connection));
        } catch (Exception ex) {
            log.error("Error syncing wearable connection {}", command.id(), ex);
            return Result.failure(new WearableConnectionCommandFailure.SyncFailed(ex.getMessage()));
        }
    }

    @Override
    public Result<WearableConnection, WearableConnectionCommandFailure> handle(SetWearableAutoSyncCommand command) {
        var found = repository.findById(command.id());
        if (found.isEmpty())
            return Result.failure(new WearableConnectionCommandFailure.WearableConnectionNotFound(command.id()));

        WearableConnection connection = found.get();
        connection.setAutoSync(command.enabled());
        return Result.success(repository.save(connection));
    }

    @Override
    public Result<Void, WearableConnectionCommandFailure> handle(DeleteWearableConnectionCommand command) {
        if (!repository.existsById(command.id()))
            return Result.failure(new WearableConnectionCommandFailure.WearableConnectionNotFound(command.id()));
        repository.deleteById(command.id());
        return Result.success(null);
    }

    /**
     * Obtiene la actividad del proveedor para el rango y persiste las entradas nuevas (deduplicadas
     * por tipo, duración y día contra los registros existentes del usuario).
     */
    private void importActivities(WearableConnection connection, LocalDate from, LocalDate to) {
        String accessToken = ensureFreshAccessToken(connection);
        List<ImportedActivity> imported = syncProvider.fetchActivities(accessToken, from, to);

        Set<String> existingKeys = new HashSet<>();
        for (ActivityLog logItem : activityLogRepository.findByUserId(connection.getUserId())) {
            existingKeys.add(dedupKey(logItem.getActivityType(), logItem.getDurationMinutes(),
                    LocalDate.ofInstant(logItem.getTimestamp(), ZoneOffset.UTC)));
        }

        for (ImportedActivity activity : imported) {
            String key = dedupKey(activity.activityType(), activity.durationMinutes(), activity.date());
            if (!existingKeys.add(key)) continue; // ya existe

            double calories = activity.caloriesBurned() > 0
                    ? activity.caloriesBurned()
                    : activity.durationMinutes() * DEFAULT_KCAL_PER_MINUTE;
            Instant timestamp = activity.date().atStartOfDay(ZoneOffset.UTC).toInstant();
            activityLogRepository.save(ActivityLog.create(connection.getUserId(),
                    activity.activityType(), activity.durationMinutes(), calories, timestamp));
        }
    }

    /** Devuelve un access token no expirado, refrescándolo con el proveedor cuando es posible. */
    private String ensureFreshAccessToken(WearableConnection connection) {
        boolean stillValid = connection.getTokenExpiresAt() != null
                && connection.getTokenExpiresAt().isAfter(Instant.now().plusSeconds(60));
        if (stillValid || connection.getRefreshToken() == null || connection.getRefreshToken().isBlank())
            return connection.getAccessToken() != null ? connection.getAccessToken() : "";

        WearableAuthorization refreshed = syncProvider.refresh(connection.getRefreshToken());
        if (refreshed.success())
            connection.applyAuthorization(refreshed.accessToken(), refreshed.refreshToken(), refreshed.expiresAt());

        return connection.getAccessToken() != null ? connection.getAccessToken() : "";
    }

    private String dedupKey(String type, Integer duration, LocalDate date) {
        return type + "|" + duration + "|" + date;
    }
}
