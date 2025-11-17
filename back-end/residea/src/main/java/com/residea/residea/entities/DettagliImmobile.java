package com.residea.residea.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
@Table(name = "DettagliImmobile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "immobile") // Evita lazy loading issues
public class DettagliImmobile {

    @Id
    @Column(name = "idImmobile")
    private Integer idImmobile;

    @OneToOne
    @MapsId
    @JoinColumn(name = "idImmobile")
    private Immobile immobile;

    @Column(name = "nStanze")
    private Integer nStanze;

    @Column(name = "nBagni")
    private Integer nBagni;

    @Column(name = "nPiano")
    private Integer nPiano;

    @Column(name = "nPianiImmobile")
    private Integer nPianiImmobile;

    private boolean balconeTerrazzo;
    private boolean giardino;
    private boolean garage;
    private boolean ascensore;
    private boolean cantina;

    @Convert(converter = com.residea.residea.entities.converters.TipoRiscaldamentoConverter.class)
    @Column(nullable = false, length = 20)
    @lombok.Builder.Default
    private TipoRiscaldamento tipoRiscaldamento = TipoRiscaldamento.NO;

    private Integer annoCostruzione;

    @Convert(converter = com.residea.residea.entities.converters.CondizioneImmobileConverter.class)
    @Column(length = 30)
    private CondizioneImmobile condizioneImmobile;

    @Convert(converter = com.residea.residea.entities.converters.ClasseEnergeticaConverter.class)
    @Column(length = 2)
    private ClasseEnergetica classeEnergetica;

    @Column(precision = 10, scale = 2)
    private BigDecimal prezzo;

    // --- ENUM per tipo riscaldamento ---
    public enum TipoRiscaldamento {
        NO,
        AUTONOMO,
        CONDOMINIALE,
        POMPE_DI_CALORE,
        PAVIMENTO
    }

    // --- ENUM per condizione immobile ---
    public enum CondizioneImmobile {
        NUOVO,
        RISTRUTTURATO,
        PARZIALMENTE_RISTRUTTURATO,
        NON_RISTRUTTURATO
    }

    // --- ENUM per classe energetica ---
    public enum ClasseEnergetica {
        A_PLUS("A+"),
        A("A"),
        B("B"),
        C("C"),
        D("D"),
        E("E"),
        F("F"),
        G("G");

        private final String displayValue;

        ClasseEnergetica(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }
}