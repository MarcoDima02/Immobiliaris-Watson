package com.residea.residea.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Immobile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "proprietario") // Evita lazy loading issues
public class Immobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idImmobile")
    private Integer idImmobile;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idProprietario", nullable = true)
    private Utente proprietario;

    @Convert(converter = com.residea.residea.entities.converters.TipologiaConverter.class)
    @Column(nullable = false, length = 30)
    private Tipologia tipologia;

    @Column(length = 200)
    private String indirizzo;

    @Column(length = 100)
    private String citta;

    @Column(length = 3)
    private String provincia;

    @Column(length = 5)
    private String cap;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitudine;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitudine;

    @Convert(converter = com.residea.residea.entities.converters.StatoConverter.class)
    @Column(length = 50)
    private Stato stato;

    // --- ENUM interno ---
    public enum Tipologia {
        APPARTAMENTO,
        VILLA,
        CASA_INDIPENDENTE,
        MONOLOCALE
    }

    public enum Stato {
        DISPONIBILE,
        VENDUTO
    }
}