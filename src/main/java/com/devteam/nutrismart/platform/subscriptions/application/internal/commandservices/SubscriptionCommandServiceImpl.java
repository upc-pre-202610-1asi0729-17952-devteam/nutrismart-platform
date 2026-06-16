package com.devteam.nutrismart.platform.subscriptions.application.internal.commandservices;

import com.devteam.nutrismart.platform.iam.application.commandservices.UserCommandService;
import com.devteam.nutrismart.platform.iam.application.commands.UpdateUserPlanCommand;
import com.devteam.nutrismart.platform.iam.domain.model.valueobjects.UserPlan;
import com.devteam.nutrismart.platform.shared.application.result.Result;
import com.devteam.nutrismart.platform.subscriptions.application.commandservices.SubscriptionCommandFailure;
import com.devteam.nutrismart.platform.subscriptions.application.commandservices.SubscriptionCommandService;
import com.devteam.nutrismart.platform.subscriptions.application.commands.CreateSubscriptionCommand;
import com.devteam.nutrismart.platform.subscriptions.application.commands.UpdateSubscriptionCommand;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.Subscription;
import com.devteam.nutrismart.platform.subscriptions.domain.model.repositories.SubscriptionRepository;
import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de comandos de suscripciones.
 * Gestiona la creación y actualización de suscripciones, asegurando que
 * no existan suscripciones activas duplicadas para el mismo usuario.
 */
@Service
@Transactional
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserCommandService userCommandService;

    public SubscriptionCommandServiceImpl(SubscriptionRepository subscriptionRepository,
                                          UserCommandService userCommandService) {
        this.subscriptionRepository = subscriptionRepository;
        this.userCommandService = userCommandService;
    }

    @Override
    public Result<Subscription, SubscriptionCommandFailure> handle(CreateSubscriptionCommand command) {
        try {
            if (subscriptionRepository.findByUserIdAndStatus(command.userId(), SubscriptionStatus.ACTIVE).isPresent()) {
                return Result.failure(new SubscriptionCommandFailure.AlreadyActive(command.userId()));
            }
            Subscription subscription = Subscription.create(command.userId(), command.plan());
            Subscription saved = subscriptionRepository.save(subscription);
            UserPlan userPlan = UserPlan.valueOf(command.plan().name());
            userCommandService.handle(new UpdateUserPlanCommand(command.userId(), userPlan));
            return Result.success(saved);
        } catch (Exception ex) {
            return Result.failure(new SubscriptionCommandFailure.InvalidData(ex.getMessage()));
        }
    }

    @Override
    public Result<Subscription, SubscriptionCommandFailure> handle(UpdateSubscriptionCommand command) {
        try {
            return subscriptionRepository.findById(command.id())
                    .map(subscription -> {
                        subscription.update(command.plan(), command.status(),
                                command.billingCycleStart(), command.billingCycleEnd());
                        Subscription saved = subscriptionRepository.save(subscription);
                        return Result.<Subscription, SubscriptionCommandFailure>success(saved);
                    })
                    .orElse(Result.failure(new SubscriptionCommandFailure.NotFound(command.id())));
        } catch (Exception ex) {
            return Result.failure(new SubscriptionCommandFailure.InvalidData(ex.getMessage()));
        }
    }
}
