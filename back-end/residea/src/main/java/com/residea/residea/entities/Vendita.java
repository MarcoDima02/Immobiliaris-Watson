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
@Table(name = "Vendite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"contratto", "immobile", "utente"}) // Evita lazy loading issues
public class Vendita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVendita")
    private Integer idVendita;

    @ManyToOne
    @JoinColumn(name = "idContratto", nullable = false)
    private Contratto contratto;

    @ManyToOne
    @JoinColumn(name = "idImmobile", nullable = false)
    private Immobile immobile;

    @ManyToOne
    @JoinColumn(name = "idUtente", nullable = false)
    private Utente utente;

    @Column(precision = 5, scale = 2)
    private BigDecimal commissionePercentuale;
}