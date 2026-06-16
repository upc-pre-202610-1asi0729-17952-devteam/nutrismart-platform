package com.devteam.nutrismart.platform.subscriptions.application.internal.queryservices;

import com.devteam.nutrismart.platform.subscriptions.application.queries.GetAllBillingRecordsQuery;
import com.devteam.nutrismart.platform.subscriptions.application.queries.GetBillingRecordByIdQuery;
import com.devteam.nutrismart.platform.subscriptions.application.queryservices.BillingRecordQueryService;
import com.devteam.nutrismart.platform.subscriptions.domain.model.aggregates.BillingRecord;
import com.devteam.nutrismart.platform.subscriptions.domain.model.repositories.BillingRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de consultas de registros de pago.
 * Recupera registros de facturación desde el repositorio de dominio sin
 * aplicar lógica de negocio adicional.
 */
@Service
@Transactional(readOnly = true)
public class BillingRecordQueryServiceImpl implements BillingRecordQueryService {

    private final BillingRecordRepository billingRecordRepository;

    public BillingRecordQueryServiceImpl(BillingRecordRepository billingRecordRepository) {
        this.billingRecordRepository = billingRecordRepository;
    }

    @Override
    public Optional<BillingRecord> handle(GetBillingRecordByIdQuery query) {
        return billingRecordRepository.findById(query.id());
    }

    @Override
    public List<BillingRecord> handle(GetAllBillingRecordsQuery query) {
        return billingRecordRepository.findAll();
    }

    @Override
    public List<BillingRecord> findByUserId(Long userId) {
        return billingRecordRepository.findByUserId(userId);
    }
}
