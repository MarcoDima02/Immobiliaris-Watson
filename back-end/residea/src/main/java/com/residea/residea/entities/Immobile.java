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

@Entity
@Table(name = "Immobile")
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

    // --- COSTRUTTORI ---
    public Immobile() {}

    public Immobile(Utente proprietario, Tipologia tipologia, String indirizzo, String citta, String provincia, String cap,
                    BigDecimal latitudine, BigDecimal longitudine, Stato stato) {
        this.proprietario = proprietario;
        this.tipologia = tipologia;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.provincia = provincia;
        this.cap = cap;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.stato = stato;
    }

    // --- GETTER & SETTER ---
    public Integer getIdImmobile() {
        return idImmobile;
    }

    public void setIdImmobile(Integer idImmobile) {
        this.idImmobile = idImmobile;
    }

    public Utente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }

    public Tipologia getTipologia() {
        return tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public BigDecimal getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(BigDecimal latitudine) {
        this.latitudine = latitudine;
    }

    public BigDecimal getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(BigDecimal longitudine) {
        this.longitudine = longitudine;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        return "Immobile{" +
                "idImmobile=" + idImmobile +
                ", proprietario=" + (proprietario != null ? proprietario.getIdUtente() : null) +
                ", tipologia=" + tipologia +
                ", indirizzo='" + indirizzo + '\'' +
                ", citta='" + citta + '\'' +
                ", provincia='" + provincia + '\'' +
                ", cap='" + cap + '\'' +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", stato='" + stato + '\'' +
                '}';
    }

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