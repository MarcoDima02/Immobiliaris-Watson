package com.residea.residea.dto;

import java.math.BigDecimal;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    // Custom constructor per backward compatibility
    public ValutazioneResultResponse(BigDecimal prezzoBasePerMq, BigDecimal superficieCommerciale,
                                     Map<String, BigDecimal> coefficientiApplicati, BigDecimal valoreLordo,
                                     BigDecimal valoreFinale, String note) {
        this.prezzoBasePerMq = prezzoBasePerMq;
        this.superficieCommerciale = superficieCommerciale;
        this.coefficientiApplicati = coefficientiApplicati;
        this.valoreLordo = valoreLordo;
        this.valoreMedio = valoreFinale != null ? valoreFinale.intValue() : null;
        this.note = note;
    }

    // Backward compatibility methods
    public BigDecimal getValoreFinale() { 
        return valoreMedio != null ? BigDecimal.valueOf(valoreMedio) : null; 
    }
    public void setValoreFinale(BigDecimal valoreFinale) { 
        this.valoreMedio = valoreFinale != null ? valoreFinale.intValue() : null; 
    }
}
