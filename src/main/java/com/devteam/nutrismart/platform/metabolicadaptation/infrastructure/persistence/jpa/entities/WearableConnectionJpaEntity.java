package com.devteam.nutrismart.platform.metabolicadaptation.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.metabolicadaptation.domain.model.valueobjects.WearableStatus;
import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.Instant;

/**
 * Entidad JPA que mapea el agregado {@code WearableConnection} a la tabla {@code wearable_connections}.
 * Almacena la conexión de un usuario con un dispositivo wearable externo y su estado de sincronización.
 */
@Entity
@Table(name = "wearable_connections")
@Getter
@Setter
@NoArgsConstructor
public class WearableConnectionJpaEntity extends AuditableAbstractPersistenceEntity {

    @Comment("Identificador del usuario propietario de esta conexión wearable")
    @Column(nullable = false)
    private Long userId;

    @Comment("Nombre del proveedor del dispositivo wearable (ej. Fitbit, Garmin, Apple Health)")
    @Column(nullable = false)
    private String provider;

    @Comment("Estado actual de la conexión con el dispositivo wearable: CONNECTED o DISCONNECTED")
    @Enumerated(EnumType.STRING)
    private WearableStatus status;

    @Comment("Fecha y hora de la última sincronización exitosa con el dispositivo wearable")
    private Instant lastSyncedAt;

    @Comment("Fecha y hora en que el usuario autorizó el acceso al dispositivo wearable")
    private Instant authorizedAt;
}
