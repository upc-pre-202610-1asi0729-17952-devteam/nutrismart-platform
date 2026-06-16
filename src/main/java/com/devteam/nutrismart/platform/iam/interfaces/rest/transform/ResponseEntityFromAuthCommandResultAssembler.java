package com.devteam.nutrismart.platform.iam.interfaces.rest.transform;

import com.devteam.nutrismart.platform.iam.application.commandservices.AuthFailure;
import com.devteam.nutrismart.platform.iam.application.commandservices.AuthTokenData;
import com.devteam.nutrismart.platform.iam.interfaces.rest.resources.AuthResponse;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import com.devteam.nutrismart.platform.shared.interfaces.rest.resources.ErrorResource;
import org.springframework.http.ResponseEntity;

/**
 * Ensamblador estático que traduce el {@link Result} del comando de autenticación
 * en una respuesta HTTP {@link ResponseEntity} adecuada para la API REST.
 */
public final class ResponseEntityFromAuthCommandResultAssembler {

    private ResponseEntityFromAuthCommandResultAssembler() {}

    /**
     * Convierte el resultado del comando de autenticación en una {@link ResponseEntity}.
     * Retorna 200 con el {@link AuthResponse} en caso de éxito,
     * o 401/400 según el tipo de fallo de autenticación.
     *
     * @param result resultado del comando de autenticación
     * @return respuesta HTTP con los datos del token o el mensaje de error
     */
    public static ResponseEntity<?> toResponseEntity(Result<AuthTokenData, AuthFailure> result) {
        return result.fold(
                data -> ResponseEntity.ok(new AuthResponse(
                        data.token(),
                        data.userId(),
                        data.email(),
                        data.firstName(),
                        data.lastName(),
                        data.goal(),
                        data.plan()
                )),
                failure -> switch (failure) {
                    case AuthFailure.InvalidCredentials f ->
                            ResponseEntity.status(401).body(new ErrorResource("INVALID_CREDENTIALS", "Invalid credentials"));
                    case AuthFailure.InvalidData f ->
                            ResponseEntity.status(400).body(new ErrorResource("INVALID_DATA", f.reason()));
                }
        );
    }
}
