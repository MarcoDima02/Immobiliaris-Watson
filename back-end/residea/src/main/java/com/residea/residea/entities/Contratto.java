package com.residea.residea.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "contratti")
public class Contratto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idContratto")
    private Integer idContratto;

    @ManyToOne
    @JoinColumn(name = "idImmobile", nullable = false)
    private Immobile immobile;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TipoContratto tipoContratto;

    private LocalDate dataContratto;
    private LocalDate dataScadenzaContratto;

    @Column(length = 255)
    private String pathContrattoPDF;

    // --- COSTRUTTORI ---
    public Contratto() {}

    public Contratto(Immobile immobile, TipoContratto tipoContratto, LocalDate dataContratto, LocalDate dataScadenzaContratto, String pathContrattoPDF) {
        this.immobile = immobile;
        this.tipoContratto = tipoContratto;
        this.dataContratto = dataContratto;
        this.dataScadenzaContratto = dataScadenzaContratto;
        this.pathContrattoPDF = pathContrattoPDF;
    }

    // --- GETTER & SETTER ---
    public Integer getIdContratto() {
        return idContratto;
    }

    public void setIdContratto(Integer idContratto) {
        this.idContratto = idContratto;
    }

    public Immobile getImmobile() {
        return immobile;
    }

    public void setImmobile(Immobile immobile) {
        this.immobile = immobile;
    }

    public TipoContratto getTipoContratto() {
        return tipoContratto;
    }

    public void setTipoContratto(TipoContratto tipoContratto) {
        this.tipoContratto = tipoContratto;
    }

    public LocalDate getDataContratto() {
        return dataContratto;
    }

    public void setDataContratto(LocalDate dataContratto) {
        this.dataContratto = dataContratto;
    }

    public LocalDate getDataScadenzaContratto() {
        return dataScadenzaContratto;
    }

    public void setDataScadenzaContratto(LocalDate dataScadenzaContratto) {
        this.dataScadenzaContratto = dataScadenzaContratto;
    }

    public String getPathContrattoPDF() {
        return pathContrattoPDF;
    }

    public void setPathContrattoPDF(String pathContrattoPDF) {
        this.pathContrattoPDF = pathContrattoPDF;
    }

    @Override
    public String toString() {
        return "Contratto{" +
                "idContratto=" + idContratto +
                ", immobile=" + (immobile != null ? immobile.getIdImmobile() : null) +
                ", tipoContratto=" + tipoContratto +
                ", dataContratto=" + dataContratto +
                ", dataScadenzaContratto=" + dataScadenzaContratto +
                ", pathContrattoPDF='" + pathContrattoPDF + '\'' +
                '}';
    }

    // --- ENUM per tipoContratto ---
    public enum TipoContratto {
        ESCLUSIVO,
        ALTRO
    }
}