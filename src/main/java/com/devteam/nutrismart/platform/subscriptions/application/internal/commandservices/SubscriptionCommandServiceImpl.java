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
import com.devteam.nutrismart.platform.subscriptions.domain.model.repositories.PaymentMethodRepository;
import com.devteam.nutrismart.platform.subscriptions.domain.model.events.SubscriptionActivatedEvent;
import com.devteam.nutrismart.platform.subscriptions.domain.model.events.SubscriptionCancelledEvent;
import com.devteam.nutrismart.platform.subscriptions.domain.model.repositories.SubscriptionRepository;
import com.devteam.nutrismart.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Implementación del servicio de comandos de suscripciones.
 * Gestiona la creación y actualización de suscripciones, asegurando que
 * no existan suscripciones activas duplicadas para el mismo usuario.
 */
@Service
@Transactional
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final UserCommandService userCommandService;
    private final ApplicationEventPublisher eventPublisher;

    public SubscriptionCommandServiceImpl(SubscriptionRepository subscriptionRepository,
                                          PaymentMethodRepository paymentMethodRepository,
                                          UserCommandService userCommandService,
                                          ApplicationEventPublisher eventPublisher) {
        this.subscriptionRepository = subscriptionRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.userCommandService = userCommandService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<Subscription, SubscriptionCommandFailure> handle(CreateSubscriptionCommand command) {
        try {
            // Upsert: si el usuario ya tiene una suscripción activa, un "create" se interpreta
            // como un cambio de plan sobre esa misma suscripción (renovando el ciclo de
            // facturación) en lugar de rechazarlo. Esto evita el 409 al cambiar de plan y
            // hace la operación idempotente ante reintentos del cliente.
            Subscription subscription = subscriptionRepository
                    .findByUserIdAndStatus(command.userId(), SubscriptionStatus.ACTIVE)
                    .map(active -> {
                        active.update(command.plan(), SubscriptionStatus.ACTIVE,
                                LocalDate.now(), LocalDate.now().plusMonths(1));
                        return active;
                    })
                    .orElseGet(() -> Subscription.create(command.userId(), command.plan()));

            // Sellar el Customer de Stripe del usuario (resuelto desde su método de pago) para
            // dejarlo disponible en renovaciones/cambios de plan futuros, igual que NutriSense.
            paymentMethodRepository.findAnyStripeCustomerIdByUserId(command.userId())
                    .ifPresent(subscription::applyStripeCustomer);
            Subscription saved = subscriptionRepository.save(subscription);
            UserPlan userPlan = UserPlan.valueOf(command.plan().name());
            userCommandService.handle(new UpdateUserPlanCommand(command.userId(), userPlan));
            eventPublisher.publishEvent(SubscriptionActivatedEvent.of(
                    saved.getUserId(), saved.getId(), saved.getPlan().name()));
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
                        // Mantener sincronizado el plan del usuario en IAM cuando la suscripción
                        // queda activa (cambio de plan vía PUT). En cancelaciones/expiraciones no
                        // se toca el plan aquí; ese flujo lo gestiona la expiración del beneficio.
                        if (saved.getStatus() == SubscriptionStatus.ACTIVE) {
                            userCommandService.handle(new UpdateUserPlanCommand(
                                    saved.getUserId(), UserPlan.valueOf(saved.getPlan().name())));
                            eventPublisher.publishEvent(SubscriptionActivatedEvent.of(
                                    saved.getUserId(), saved.getId(), saved.getPlan().name()));
                        } else {
                            eventPublisher.publishEvent(SubscriptionCancelledEvent.of(
                                    saved.getUserId(), saved.getId(), saved.getPlan().name()));
                        }
                        return Result.<Subscription, SubscriptionCommandFailure>success(saved);
                    })
                    .orElse(Result.failure(new SubscriptionCommandFailure.NotFound(command.id())));
        } catch (Exception ex) {
            return Result.failure(new SubscriptionCommandFailure.InvalidData(ex.getMessage()));
        }
    }
}
