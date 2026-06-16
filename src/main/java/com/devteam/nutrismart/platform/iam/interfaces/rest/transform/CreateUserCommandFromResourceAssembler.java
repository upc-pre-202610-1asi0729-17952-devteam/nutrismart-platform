package com.devteam.nutrismart.platform.iam.interfaces.rest.transform;

import com.devteam.nutrismart.platform.iam.application.commands.RegisterAccountCommand;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.EmailAddress;
import com.devteam.nutrismart.platform.iam.interfaces.rest.resources.CreateUserResource;

/**
 * Ensamblador estático que convierte el recurso REST {@link CreateUserResource}
 * en el comando de aplicación {@link RegisterAccountCommand}.
 */
public final class CreateUserCommandFromResourceAssembler {

    private CreateUserCommandFromResourceAssembler() {}

    /**
     * Construye un {@link RegisterAccountCommand} a partir del recurso de creación de usuario.
     * El correo electrónico se envuelve en el objeto de valor {@link com.devteam.nutrismart.platform.iam.domain.model.valueobjects.EmailAddress}.
     *
     * @param resource datos del recurso REST de creación de usuario
     * @return comando listo para ser despachado al servicio de aplicación
     */
    public static RegisterAccountCommand toCommandFromResource(CreateUserResource resource) {
        return new RegisterAccountCommand(
                resource.firstName(),
                resource.lastName(),
                new EmailAddress(resource.email()),
                resource.password(),
                resource.goal(),
                resource.weight(),
                resource.height(),
                resource.activityLevel(),
                null,
                resource.restrictions(),
                resource.medicalConditions(),
                resource.birthday(),
                resource.biologicalSex(),
                resource.homeCity()
        );
    }
}
