package com.devteam.nutrismart.platform.metabolicadaptation.application.internal.commandservices;

import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.WearableConnectionCommandFailure;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commandservices.WearableConnectionCommandService;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.ConnectWearableCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.DeleteWearableConnectionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.application.commands.UpdateWearableConnectionCommand;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.aggregates.WearableConnection;
import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.repositories.WearableConnectionRepository;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación transaccional del servicio de comandos para el agregado {@code WearableConnection}.
 * Gestiona la conexión, actualización y eliminación de dispositivos wearable en el repositorio de dominio.
 */
@Service
@Transactional
public class WearableConnectionCommandServiceImpl implements WearableConnectionCommandService {

    private final WearableConnectionRepository repository;

    public WearableConnectionCommandServiceImpl(WearableConnectionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<WearableConnection, WearableConnectionCommandFailure> handle(ConnectWearableCommand command) {
        try {
            WearableConnection wc = WearableConnection.create(command.userId(), command.provider(),
                    command.status(), command.lastSyncedAt(), command.authorizedAt());
            return Result.success(repository.save(wc));
        } catch (Exception ex) {
            return Result.failure(new WearableConnectionCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<WearableConnection, WearableConnectionCommandFailure> handle(UpdateWearableConnectionCommand command) {
        try {
            return repository.findById(command.id())
                    .map(wc -> {
                        wc.update(command.status(), command.lastSyncedAt());
                        return Result.<WearableConnection, WearableConnectionCommandFailure>success(repository.save(wc));
                    })
                    .orElse(Result.failure(new WearableConnectionCommandFailure.WearableConnectionNotFound(command.id())));
        } catch (Exception ex) {
            return Result.failure(new WearableConnectionCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<Void, WearableConnectionCommandFailure> handle(DeleteWearableConnectionCommand command) {
        if (!repository.existsById(command.id())) {
            return Result.failure(new WearableConnectionCommandFailure.WearableConnectionNotFound(command.id()));
        }
        repository.deleteById(command.id());
        return Result.success(null);
    }
}
