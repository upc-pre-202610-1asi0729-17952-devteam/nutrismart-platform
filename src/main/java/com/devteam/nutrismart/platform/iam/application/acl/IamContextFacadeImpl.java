package com.devteam.nutrismart.platform.iam.application.acl;

import com.devteam.nutrismart.platform.iam.application.queries.GetUserByIdQuery;
import com.devteam.nutrismart.platform.iam.application.queryservices.UserQueryService;
import com.devteam.nutrismart.platform.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementación del Anti-Corruption Layer (ACL) del contexto IAM.
 * Expone un resumen del usuario a otros contextos acotados sin revelar los detalles internos del dominio.
 */
@Service
public class IamContextFacadeImpl implements IamContextFacade {

    private final UserQueryService userQueryService;

    public IamContextFacadeImpl(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public Optional<UserSummaryData> getUserSummary(Long userId) {
        return userQueryService.handle(new GetUserByIdQuery(userId))
                .map(u -> new UserSummaryData(
                        u.getId(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getProteinTarget(),
                        u.getCarbsTarget(),
                        u.getFatTarget()
                ));
    }
}
