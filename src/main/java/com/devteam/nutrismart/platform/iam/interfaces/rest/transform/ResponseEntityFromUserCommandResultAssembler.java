package com.devteam.nutrismart.platform.iam.interfaces.rest.transform;

import com.devteam.nutrismart.platform.iam.application.commandservices.UserCommandFailure;
import com.devteam.nutrismart.platform.iam.domain.model.aggregates.User;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Ensamblador estático que traduce los {@link Result} de comandos de usuario
 * en respuestas HTTP {@link ResponseEntity} adecuadas para la API REST.
 */
public final class ResponseEntityFromUserCommandResultAssembler {

    private ResponseEntityFromUserCommandResultAssembler() {}

    /**
     * Convierte el resultado de un comando de creación de usuario en una {@link ResponseEntity}.
     * Retorna 201 en caso de éxito, o el código de error correspondiente al fallo.
     *
     * @param result resultado del comando de registro
     * @return respuesta HTTP con el recurso del usuario creado o el mensaje de error
     */
    public static ResponseEntity<?> toResponseEntityFromCreateResult(Result<User, UserCommandFailure> result) {
        return result.fold(
                user -> ResponseEntity.status(201).body(UserResourceFromEntityAssembler.toResourceFromEntity(user)),
                failure -> switch (failure) {
                    case UserCommandFailure.EmailAlreadyExists f ->
                            ResponseEntity.status(409).body(Map.of("message", "Email already exists: " + f.email()));
                    case UserCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                    case UserCommandFailure.UserNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "User not found"));
                }
        );
    }

    /**
     * Convierte el resultado de un comando de actualización de usuario en una {@link ResponseEntity}.
     * Retorna 200 en caso de éxito, o el código de error correspondiente al fallo.
     *
     * @param result resultado del comando de actualización
     * @return respuesta HTTP con el recurso del usuario actualizado o el mensaje de error
     */
    public static ResponseEntity<?> toResponseEntityFromUpdateResult(Result<User, UserCommandFailure> result) {
        return result.fold(
                user -> ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFromEntity(user)),
                failure -> switch (failure) {
                    case UserCommandFailure.UserNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "User not found"));
                    case UserCommandFailure.EmailAlreadyExists f ->
                            ResponseEntity.status(409).body(Map.of("message", "Email already exists: " + f.email()));
                    case UserCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }

    /**
     * Convierte el resultado de un comando de eliminación de usuario en una {@link ResponseEntity}.
     * Retorna 204 No Content en caso de éxito, o el código de error correspondiente al fallo.
     *
     * @param result resultado del comando de eliminación
     * @return respuesta HTTP 204 o el mensaje de error
     */
    public static ResponseEntity<?> toResponseEntityFromDeleteResult(Result<Void, UserCommandFailure> result) {
        return result.fold(
                v -> ResponseEntity.noContent().build(),
                failure -> switch (failure) {
                    case UserCommandFailure.UserNotFound f ->
                            ResponseEntity.status(404).body(Map.of("message", "User not found"));
                    case UserCommandFailure.EmailAlreadyExists f ->
                            ResponseEntity.status(409).body(Map.of("message", "Email already exists: " + f.email()));
                    case UserCommandFailure.InvalidData f ->
                            ResponseEntity.status(400).body(Map.of("message", f.reason()));
                }
        );
    }
}
