package com.devteam.nutrismart.platform.subscriptions.application.internal.commandservices;

import com.devteam.nutrismart.platform.shared.application.result.Result;
import com.devteam.nutrismart.platform.subscriptions.application.commandservices.BillingRecordCommandFailure;
import com.devteam.nutrismart.platform.subscriptions.application.commandservices.BillingRecordCommandService;
import com.devteam.nutrismart.platform.subscriptions.application.commands.CreateBillingRecordCommand;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.BillingRecord;
import com.devteam.nutrismart.platform.subscriptions.domain.model.repositories.BillingRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de comandos de registros de pago.
 * Crea nuevos registros en el historial de facturación delegando la persistencia
 * en el repositorio de dominio {@code BillingRecordRepository}.
 */
@Service
@Transactional
public class BillingRecordCommandServiceImpl implements BillingRecordCommandService {

    private final BillingRecordRepository billingRecordRepository;

    public BillingRecordCommandServiceImpl(BillingRecordRepository billingRecordRepository) {
        this.billingRecordRepository = billingRecordRepository;
    }

    @Override
    public Result<BillingRecord, BillingRecordCommandFailure> handle(CreateBillingRecordCommand command) {
        try {
            BillingRecord billingRecord = BillingRecord.create(
                    command.userId(), command.plan(), command.amount(),
                    command.currency(), command.status()
            );
            BillingRecord saved = billingRecordRepository.save(billingRecord);
            return Result.success(saved);
        } catch (Exception ex) {
            return Result.failure(new BillingRecordCommandFailure.InvalidData(ex.getMessage()));
        }
    }
}
