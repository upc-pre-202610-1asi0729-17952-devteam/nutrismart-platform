package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.persistence.jpa.entities;

import com.devteam.nutrismart.platform.smartrecommendation.domain.model.valueobjects.WeatherType;
import com.devteam.nutrismart.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "weather_snapshots")
@Getter
@Setter
@NoArgsConstructor
public class WeatherSnapshotJpaEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private String city;

    private String country;

    private Double temperatureCelsius;

    @Column(name = "weather_condition")
    private String condition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WeatherType weatherType;
}
