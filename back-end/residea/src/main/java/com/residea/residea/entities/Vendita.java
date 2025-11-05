package com.residea.residea.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vendite")
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

    // --- COSTRUTTORI ---
    public Vendita() {}

    public Vendita(Contratto contratto, Immobile immobile, Utente utente, BigDecimal commissionePercentuale) {
        this.contratto = contratto;
        this.immobile = immobile;
        this.utente = utente;
        this.commissionePercentuale = commissionePercentuale;
    }

    // --- GETTER & SETTER ---
    public Integer getIdVendita() {
        return idVendita;
    }

    public void setIdVendita(Integer idVendita) {
        this.idVendita = idVendita;
    }

    public Contratto getContratto() {
        return contratto;
    }

    public void setContratto(Contratto contratto) {
        this.contratto = contratto;
    }

    public Immobile getImmobile() {
        return immobile;
    }

    public void setImmobile(Immobile immobile) {
        this.immobile = immobile;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public BigDecimal getCommissionePercentuale() {
        return commissionePercentuale;
    }

    public void setCommissionePercentuale(BigDecimal commissionePercentuale) {
        this.commissionePercentuale = commissionePercentuale;
    }

    @Override
    public String toString() {
        return "Vendita{" +
                "idVendita=" + idVendita +
                ", contratto=" + (contratto != null ? contratto.getIdContratto() : null) +
                ", immobile=" + (immobile != null ? immobile.getIdImmobile() : null) +
                ", utente=" + (utente != null ? utente.getIdUtente() : null) +
                ", commissionePercentuale=" + commissionePercentuale +
                '}';
    }
}