package com.residea.residea.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Superfici")
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

    // --- COSTRUTTORI ---
    public Superficie() {}

    public Superficie(Immobile immobile, BigDecimal superficieMq, BigDecimal superficieBalconeTerrazzo,
                     BigDecimal superficieGiardino, BigDecimal superficieGarage, BigDecimal superficieCantina) {
        this.immobile = immobile;
        this.superficieMq = superficieMq;
        this.superficieBalconeTerrazzo = superficieBalconeTerrazzo;
        this.superficieGiardino = superficieGiardino;
        this.superficieGarage = superficieGarage;
        this.superficieCantina = superficieCantina;
    }

    // --- GETTER & SETTER ---
    public Integer getIdImmobile() {
        return idImmobile;
    }

    public void setIdImmobile(Integer idImmobile) {
        this.idImmobile = idImmobile;
    }

    public Immobile getImmobile() {
        return immobile;
    }

    public void setImmobile(Immobile immobile) {
        this.immobile = immobile;
    }

    public BigDecimal getSuperficieMq() {
        return superficieMq;
    }

    public void setSuperficieMq(BigDecimal superficieMq) {
        this.superficieMq = superficieMq;
    }

    public BigDecimal getSuperficieBalconeTerrazzo() {
        return superficieBalconeTerrazzo;
    }

    public void setSuperficieBalconeTerrazzo(BigDecimal superficieBalconeTerrazzo) {
        this.superficieBalconeTerrazzo = superficieBalconeTerrazzo;
    }

    public BigDecimal getSuperficieGiardino() {
        return superficieGiardino;
    }

    public void setSuperficieGiardino(BigDecimal superficieGiardino) {
        this.superficieGiardino = superficieGiardino;
    }

    public BigDecimal getSuperficieGarage() {
        return superficieGarage;
    }

    public void setSuperficieGarage(BigDecimal superficieGarage) {
        this.superficieGarage = superficieGarage;
    }

    public BigDecimal getSuperficieCantina() {
        return superficieCantina;
    }

    public void setSuperficieCantina(BigDecimal superficieCantina) {
        this.superficieCantina = superficieCantina;
    }

    @Override
    public String toString() {
        return "Superfici{" +
                "idImmobile=" + idImmobile +
                ", superficieMq=" + superficieMq +
                ", superficieBalconeTerrazzo=" + superficieBalconeTerrazzo +
                ", superficieGiardino=" + superficieGiardino +
                ", superficieGarage=" + superficieGarage +
                ", superficieCantina=" + superficieCantina +
                '}';
    }
}