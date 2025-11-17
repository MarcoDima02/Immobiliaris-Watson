package com.residea.residea.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PrezzoPerCAP")

public class PrezzoPerCap {

    @Id
    @Column(name = "cap", length = 10)
    private String cap;

    @ManyToOne
    @JoinColumn(name = "idCitta")
    private Citta citta;

    @Column(name = "prezzoMq", precision = 10, scale = 2, nullable = false)
    private BigDecimal prezzoMq;

    @Column(name = "fonte", length = 150)
    private String fonte;

    @Column(name = "validFrom") 
    private LocalDate validFrom;

    @Column(name = "validTo")
    private LocalDate validTo;

    @Column(name = "qualityScore", precision = 3, scale = 2)
    private BigDecimal qualityScore;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    // --- COSTRUTTORI ---
    public PrezzoPerCap() {}
    public PrezzoPerCap(String cap, Citta citta, BigDecimal prezzoMq, String fonte, LocalDate validFrom,
            LocalDate validTo, BigDecimal qualityScore, LocalDateTime updatedAt) {
        this.cap = cap;
        this.citta = citta;
        this.prezzoMq = prezzoMq;
        this.fonte = fonte;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.qualityScore = qualityScore;
        this.updatedAt = updatedAt;
    }

     // --- GETTERS & SETTERS ---

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public Citta getCitta() {
        return citta;
    }

    public void setCitta(Citta citta) {
        this.citta = citta;
    }

    public BigDecimal getPrezzoMq() {
        return prezzoMq;
    }

    public void setPrezzoMq(BigDecimal prezzoMq) {
        this.prezzoMq = prezzoMq;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public BigDecimal getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(BigDecimal qualityScore) {
        this.qualityScore = qualityScore;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "PrezzoPerCap [cap=" + cap + 
                ", citta=" + (citta != null ? citta.getIdCitta() : null) + 
                ", prezzoMq=" + prezzoMq + 
                ", fonte=" + fonte + 
                ", validFrom=" + validFrom + 
                ", validTo=" + validTo + 
                ", qualityScore=" + qualityScore + 
                ", updatedAt=" + updatedAt + "]";
    }
    
}
