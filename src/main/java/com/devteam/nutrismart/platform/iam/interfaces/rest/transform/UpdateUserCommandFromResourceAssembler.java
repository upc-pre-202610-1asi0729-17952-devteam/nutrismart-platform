package com.devteam.nutrismart.platform.iam.interfaces.rest.transform;

import com.devteam.nutrismart.platform.iam.application.commands.UpdateUserCommand;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.EmailAddress;
import com.devteam.nutrismart.platform.iam.interfaces.rest.resources.UpdateUserResource;

/**
 * Ensamblador estático que convierte el recurso REST {@link UpdateUserResource}
 * en el comando de aplicación {@link UpdateUserCommand}.
 */
public final class UpdateUserCommandFromResourceAssembler {

    private UpdateUserCommandFromResourceAssembler() {}

    /**
     * Construye un {@link UpdateUserCommand} combinando el identificador de ruta con
     * los datos del cuerpo de la solicitud.
     *
     * @param id       identificador del usuario a actualizar (extraído del path variable)
     * @param resource datos actualizados del recurso REST
     * @return comando listo para ser despachado al servicio de aplicación
     */
    public static UpdateUserCommand toCommandFromResource(Long id, UpdateUserResource resource) {
        return new UpdateUserCommand(
                id,
                resource.firstName(),
                resource.lastName(),
                new EmailAddress(resource.email()),
                resource.goal(),
                resource.weight(),
                resource.height(),
                resource.activityLevel(),
                resource.restrictions(),
                resource.medicalConditions(),
                resource.homeCity(),
                resource.birthday(),
                resource.biologicalSex(),
                resource.planExpiresAt()
        );
    }
}
