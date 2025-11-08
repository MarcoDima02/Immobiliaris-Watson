package com.residea.residea.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PrezzoPerCap")

public class PrezzoPerCap {

    @Id
    private String cap;

    @OneToOne
    @MapsId
    @JoinColumn(name = "idCitta")
    private Citta idCitta;

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

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public Citta getIdCitta() {
        return idCitta;
    }

    public void setIdCitta(Citta idCitta) {
        this.idCitta = idCitta;
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
                ", idCitta=" + idCitta + 
                ", prezzoMq=" + prezzoMq + 
                ", fonte=" + fonte + 
                ", validFrom=" + validFrom + 
                ", validTo=" + validTo + 
                ", qualityScore=" + qualityScore + 
                ", updatedAt=" + updatedAt + "]";
    }
    
}
