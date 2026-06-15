package com.devteam.nutrismart.platform.shared.interfaces.rest.transform;

import com.devteam.nutrismart.platform.shared.application.result.ApplicationError;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

/**
 * Ensamblador genérico que convierte un {@link Result} de aplicación en
 * una {@link ResponseEntity} HTTP apropiada.
 * <p>
 * En caso de éxito aplica la función {@code successResourceAssembler} sobre el valor
 * y retorna el {@code successStatus} indicado. En caso de fallo delega en
 * {@link ErrorResponseAssembler} para construir la respuesta de error.
 * </p>
 */
public final class ResponseEntityAssembler {

    private ResponseEntityAssembler() {}

    public static <T, R> ResponseEntity<?> toResponseEntityFromResult(
            Result<T, ApplicationError> result,
            Function<T, R> successResourceAssembler,
            HttpStatusCode successStatus
    ) {
        return switch (result) {
            case Result.Success<T, ApplicationError> success ->
                    new ResponseEntity<>(successResourceAssembler.apply(success.value()), successStatus);
            case Result.Failure<T, ApplicationError> failure ->
                    ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }
}
