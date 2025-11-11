package com.residea.residea.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ValutazioneImmobile")
public class ValutazioneImmobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idValutazione")
    private Integer idValutazione;

    @ManyToOne
    @JoinColumn(name = "idImmobile", nullable = false)
    private Immobile immobile;

    @Column(name ="valoreBase")
    private Integer valoreBase;

    @Column(name ="fattoreAggiustamento")
    private BigDecimal fattoreAggiustamento;

    @Column(name = "valoreMedio")
    private Integer valoreMedio;

    @Column(name = "valoreMin")
    private Integer valoreMin;

    @Column(name = "valoreMax")
    private Integer valoreMax;

    @Column(name = "confidence", precision = 3, scale = 2)
    private BigDecimal confidence;

    // --- COSTRUTTORI ---
    public ValutazioneImmobile() {}

    public ValutazioneImmobile(Immobile immobile, Integer valoreBase, BigDecimal fattoreAggiustamento, Integer valoreMedio, Integer valoreMin, Integer valoreMax, BigDecimal confidence) {
        this.immobile = immobile;
        this.valoreBase = valoreBase;
        this.fattoreAggiustamento = fattoreAggiustamento;
        this.valoreMedio = valoreMedio;
        this.valoreMin = valoreMin;
        this.valoreMax = valoreMax;
        this.confidence = confidence;
    }

    // --- GETTER & SETTER ---
    public Integer getIdValutazione() {
        return idValutazione;
    }

    public void setIdValutazione(Integer idValutazione) {
        this.idValutazione = idValutazione;
    }

    public Immobile getImmobile() {
        return immobile;
    }

    public void setImmobile(Immobile immobile) {
        this.immobile = immobile;
    }

    public Integer getValoreBase() {
        return valoreBase;
    }

    public void setValoreBase(Integer valoreBase) {
        this.valoreBase = valoreBase;
    }

    public BigDecimal getFattoreAggiustamento() {
        return fattoreAggiustamento;
    }

    public void setFattoreAggiustamento(BigDecimal fattoreAggiustamento) {
        this.fattoreAggiustamento = fattoreAggiustamento;
    }

    public Integer getValoreMedio() {
        return valoreMedio;
    }

    public void setValoreMedio(Integer valoreMedio) {
        this.valoreMedio = valoreMedio;
    }

    public Integer getValoreMin() {
        return valoreMin;
    }

    public void setValoreMin(Integer valoreMin) {
        this.valoreMin = valoreMin;
    }

    public Integer getValoreMax() {
        return valoreMax;
    }

    public void setValoreMax(Integer valoreMax) {
        this.valoreMax = valoreMax;
    }

    public BigDecimal getConfidence() {
        return confidence;
    }

    public void setConfidence(BigDecimal confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "ValutazioneImmobile [idValutazione=" + idValutazione + 
                ", immobile=" + (immobile != null ? immobile.getIdImmobile() : null) + 
                ", valoreBase=" + valoreBase + 
                ", fattoreAggiustamento="+ fattoreAggiustamento + 
                ", valoreMedio=" + valoreMedio + 
                ", valoreMin=" + valoreMin + 
                ", valoreMax=" + valoreMax + 
                ", confidence=" + confidence + "]";
    }

}
