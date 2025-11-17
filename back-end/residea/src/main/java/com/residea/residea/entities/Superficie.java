package com.residea.residea.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Superfici")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "immobile") // Evita lazy loading issues
public class Superficie {

    @Id
    @Column(name = "idImmobile")
    private Integer idImmobile;

    @OneToOne
    @MapsId
    @JoinColumn(name = "idImmobile")
    private Immobile immobile;

    @Column(precision = 10, scale = 2)
    private BigDecimal superficieMq;

    @Column(precision = 10, scale = 2)
    private BigDecimal superficieBalconeTerrazzo;

    @Column(precision = 10, scale = 2)
    private BigDecimal superficieGiardino;

    @Column(precision = 10, scale = 2)
    private BigDecimal superficieGarage;

    @Column(precision = 10, scale = 2)
    private BigDecimal superficieCantina;
}