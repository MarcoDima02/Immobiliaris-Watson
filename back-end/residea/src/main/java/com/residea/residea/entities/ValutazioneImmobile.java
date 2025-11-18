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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ValutazioneImmobile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "immobile") // Evita lazy loading issues
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
}
