package com.residea.residea.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "dettagli_immobile")
public class DettagliImmobile {

    @Id
    @Column(name = "idImmobile")
    private Integer idImmobile;

    @OneToOne
    @MapsId
    @JoinColumn(name = "idImmobile")
    private Immobile immobile;

    private Integer nLocali;
    private Integer nCamere;
    private Integer nBagni;
    private Integer nPiano;

    private boolean balconeTerrazzo;
    private boolean giardino;
    private boolean garage;
    private boolean ascensore;
    private boolean cantina;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoRiscaldamento tipoRiscaldamento = TipoRiscaldamento.NO;

    private Integer annoCostruzione;
    private boolean esposizioneSolare;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private CondizioneImmobile condizioneImmobile;

    @Column(length = 50)
    private String classeEnergetica;

    @Column(precision = 15, scale = 2)
    private BigDecimal prezzo;

    // --- COSTRUTTORI ---
    public DettagliImmobile(Immobile immobile, Integer nLocali, Integer nCamere, Integer nBagni, Integer nPiano,boolean balconeTerrazzo, boolean giardino, boolean garage, boolean ascensore, boolean cantina,TipoRiscaldamento tipoRiscaldamento, Integer annoCostruzione, boolean esposizioneSolare,CondizioneImmobile condizioneImmobile, String classeEnergetica, BigDecimal prezzo) {
        this.immobile = immobile;
        this.nLocali = nLocali;
        this.nCamere = nCamere;
        this.nBagni = nBagni;
        this.nPiano = nPiano;
        this.balconeTerrazzo = balconeTerrazzo;
        this.giardino = giardino;
        this.garage = garage;
        this.ascensore = ascensore;
        this.cantina = cantina;
        this.tipoRiscaldamento = tipoRiscaldamento;
        this.annoCostruzione = annoCostruzione;
        this.esposizioneSolare = esposizioneSolare;
        this.condizioneImmobile = condizioneImmobile;
        this.classeEnergetica = classeEnergetica;
        this.prezzo = prezzo;
    }

    // --- GETTER & SETTER ---
    public Integer getIdImmobile() {
        return idImmobile;
    }

    public void setIdImmobile(Integer idImmobile) {
        this.idImmobile = idImmobile;
    }

    public Immobile getImmobile() {
        return immobile;
    }

    public void setImmobile(Immobile immobile) {
        this.immobile = immobile;
    }

    public Integer getnLocali() {
        return nLocali;
    }

    public void setnLocali(Integer nLocali) {
        this.nLocali = nLocali;
    }

    public Integer getnCamere() {
        return nCamere;
    }

    public void setnCamere(Integer nCamere) {
        this.nCamere = nCamere;
    }

    public Integer getnBagni() {
        return nBagni;
    }

    public void setnBagni(Integer nBagni) {
        this.nBagni = nBagni;
    }

    public Integer getnPiano() {
        return nPiano;
    }

    public void setnPiano(Integer nPiano) {
        this.nPiano = nPiano;
    }

    public boolean isBalconeTerrazzo() {
        return balconeTerrazzo;
    }

    public void setBalconeTerrazzo(boolean balconeTerrazzo) {
        this.balconeTerrazzo = balconeTerrazzo;
    }

    public boolean isGiardino() {
        return giardino;
    }

    public void setGiardino(boolean giardino) {
        this.giardino = giardino;
    }

    public boolean isGarage() {
        return garage;
    }

    public void setGarage(boolean garage) {
        this.garage = garage;
    }

    public boolean isAscensore() {
        return ascensore;
    }

    public void setAscensore(boolean ascensore) {
        this.ascensore = ascensore;
    }

    public boolean isCantina() {
        return cantina;
    }

    public void setCantina(boolean cantina) {
        this.cantina = cantina;
    }

    public TipoRiscaldamento getTipoRiscaldamento() {
        return tipoRiscaldamento;
    }

    public void setTipoRiscaldamento(TipoRiscaldamento tipoRiscaldamento) {
        this.tipoRiscaldamento = tipoRiscaldamento;
    }

    public Integer getAnnoCostruzione() {
        return annoCostruzione;
    }

    public void setAnnoCostruzione(Integer annoCostruzione) {
        this.annoCostruzione = annoCostruzione;
    }

    public boolean isEsposizioneSolare() {
        return esposizioneSolare;
    }

    public void setEsposizioneSolare(boolean esposizioneSolare) {
        this.esposizioneSolare = esposizioneSolare;
    }

    public CondizioneImmobile getCondizioneImmobile() {
        return condizioneImmobile;
    }

    public void setCondizioneImmobile(CondizioneImmobile condizioneImmobile) {
        this.condizioneImmobile = condizioneImmobile;
    }

    public String getClasseEnergetica() {
        return classeEnergetica;
    }

    public void setClasseEnergetica(String classeEnergetica) {
        this.classeEnergetica = classeEnergetica;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "DettagliImmobile{" +
                "idImmobile=" + idImmobile +
                ", nLocali=" + nLocali +
                ", nCamere=" + nCamere +
                ", nBagni=" + nBagni +
                ", nPiano=" + nPiano +
                ", balconeTerrazzo=" + balconeTerrazzo +
                ", giardino=" + giardino +
                ", garage=" + garage +
                ", ascensore=" + ascensore +
                ", cantina=" + cantina +
                ", tipoRiscaldamento=" + tipoRiscaldamento +
                ", annoCostruzione=" + annoCostruzione +
                ", esposizioneSolare=" + esposizioneSolare +
                ", condizioneImmobile=" + condizioneImmobile +
                ", classeEnergetica='" + classeEnergetica + '\'' +
                ", prezzo=" + prezzo +
                '}';
    }

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
}