package com.residea.residea.dto;

import java.math.BigDecimal;
import java.util.Map;

public class ValutazioneResultResponse {

    private Integer idImmobile;
    private BigDecimal prezzoBasePerMq;
    private BigDecimal superficieCommerciale;
    private Map<String, BigDecimal> coefficientiApplicati;
    private BigDecimal valoreLordo;
    
    // Range di valutazione
    private Integer valoreMin;
    private Integer valoreMedio;
    private Integer valoreMax;
    
    private BigDecimal confidence;
    private String note;

    public ValutazioneResultResponse() {}

    public ValutazioneResultResponse(BigDecimal prezzoBasePerMq, BigDecimal superficieCommerciale,
                                     Map<String, BigDecimal> coefficientiApplicati, BigDecimal valoreLordo,
                                     BigDecimal valoreFinale, String note) {
        this.prezzoBasePerMq = prezzoBasePerMq;
        this.superficieCommerciale = superficieCommerciale;
        this.coefficientiApplicati = coefficientiApplicati;
        this.valoreLordo = valoreLordo;
        this.valoreMedio = valoreFinale.intValue();
        this.note = note;
    }

    // Getters & Setters
    public Integer getIdImmobile() { return idImmobile; }
    public void setIdImmobile(Integer idImmobile) { this.idImmobile = idImmobile; }
    
    public BigDecimal getPrezzoBasePerMq() { return prezzoBasePerMq; }
    public void setPrezzoBasePerMq(BigDecimal prezzoBasePerMq) { this.prezzoBasePerMq = prezzoBasePerMq; }
    
    public BigDecimal getSuperficieCommerciale() { return superficieCommerciale; }
    public void setSuperficieCommerciale(BigDecimal superficieCommerciale) { this.superficieCommerciale = superficieCommerciale; }
    
    public Map<String, BigDecimal> getCoefficientiApplicati() { return coefficientiApplicati; }
    public void setCoefficientiApplicati(Map<String, BigDecimal> coefficientiApplicati) { this.coefficientiApplicati = coefficientiApplicati; }
    
    public BigDecimal getValoreLordo() { return valoreLordo; }
    public void setValoreLordo(BigDecimal valoreLordo) { this.valoreLordo = valoreLordo; }
    
    public Integer getValoreMin() { return valoreMin; }
    public void setValoreMin(Integer valoreMin) { this.valoreMin = valoreMin; }
    
    public Integer getValoreMedio() { return valoreMedio; }
    public void setValoreMedio(Integer valoreMedio) { this.valoreMedio = valoreMedio; }
    
    public Integer getValoreMax() { return valoreMax; }
    public void setValoreMax(Integer valoreMax) { this.valoreMax = valoreMax; }
    
    public BigDecimal getConfidence() { return confidence; }
    public void setConfidence(BigDecimal confidence) { this.confidence = confidence; }
    
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    
    // Backward compatibility
    public BigDecimal getValoreFinale() { 
        return valoreMedio != null ? BigDecimal.valueOf(valoreMedio) : null; 
    }
    public void setValoreFinale(BigDecimal valoreFinale) { 
        this.valoreMedio = valoreFinale != null ? valoreFinale.intValue() : null; 
    }
}
