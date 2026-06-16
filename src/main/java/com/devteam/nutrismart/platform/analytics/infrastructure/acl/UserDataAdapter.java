package com.devteam.nutrismart.platform.analytics.infrastructure.acl;

import com.devteam.nutrismart.platform.analytics.application.ports.UserDataPort;
import com.devteam.nutrismart.platform.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Adaptador ACL que implementa {@link UserDataPort} delegando en la fachada
 * del contexto de identidad y acceso ({@code IamContextFacade}).
 * Traduce los datos del usuario al contrato interno del módulo de analíticas.
 */
@Component
public class UserDataAdapter implements UserDataPort {

    private final IamContextFacade iamContextFacade;

    public UserDataAdapter(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    @Override
    public Optional<UserSummaryData> getUserSummary(Long userId) {
        return iamContextFacade.getUserSummary(userId)
                .map(u -> new UserSummaryData(
                        u.id(),
                        u.firstName(),
                        u.lastName(),
                        u.proteinTarget(),
                        u.carbsTarget(),
                        u.fatTarget()
                ));
    }
}
