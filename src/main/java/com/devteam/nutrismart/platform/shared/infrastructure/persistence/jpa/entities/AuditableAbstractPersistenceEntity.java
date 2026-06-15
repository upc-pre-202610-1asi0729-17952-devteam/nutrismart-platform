package com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * Clase base abstracta para todas las entidades JPA de la plataforma.
 * <p>
 * Proporciona los campos de auditoría comunes {@code id}, {@code createdAt} y
 * {@code updatedAt}, gestionados automáticamente por el mecanismo de auditoría
 * de Spring Data JPA.
 * </p>
 * <p>
 * Toda entidad JPA debe extender esta clase en lugar de declarar estos campos
 * individualmente.
 * </p>
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class AuditableAbstractPersistenceEntity {

    /** Identificador único generado automáticamente por la base de datos. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Marca de tiempo del momento en que se creó el registro. No se actualiza después de la inserción. */
    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    /** Marca de tiempo de la última modificación del registro. Se actualiza automáticamente. */
    @LastModifiedDate
    private Instant updatedAt;
}
