package com.residea.residea.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DettagliImmobile")
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoRiscaldamento tipoRiscaldamento = TipoRiscaldamento.NO;

    private Integer annoCostruzione;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private CondizioneImmobile condizioneImmobile;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private ClasseEnergetica classeEnergetica;

    @Column(precision = 10, scale = 2)
    private BigDecimal prezzo;

    // --- COSTRUTTORI ---
    public DettagliImmobile() {}

    public DettagliImmobile(Immobile immobile, Integer nStanze, Integer nBagni, Integer nPiano, Integer nPianiImmobile,
                            boolean balconeTerrazzo, boolean giardino, boolean garage, boolean ascensore, boolean cantina,
                            TipoRiscaldamento tipoRiscaldamento, Integer annoCostruzione,
                            CondizioneImmobile condizioneImmobile, ClasseEnergetica classeEnergetica, BigDecimal prezzo) {
        this.immobile = immobile;
        this.nStanze = nStanze;
        this.nBagni = nBagni;
        this.nPiano = nPiano;
        this.nPianiImmobile = nPianiImmobile;
        this.balconeTerrazzo = balconeTerrazzo;
        this.giardino = giardino;
        this.garage = garage;
        this.ascensore = ascensore;
        this.cantina = cantina;
        this.tipoRiscaldamento = tipoRiscaldamento;
        this.annoCostruzione = annoCostruzione;
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

    public Integer getNStanze() {
        return nStanze;
    }

    public void setNStanze(Integer nStanze) {
        this.nStanze = nStanze;
    }

    public Integer getNBagni() {
        return nBagni;
    }

    public void setNBagni(Integer nBagni) {
        this.nBagni = nBagni;
    }

    public Integer getNPiano() {
        return nPiano;
    }

    public void setNPiano(Integer nPiano) {
        this.nPiano = nPiano;
    }

    public Integer getNPianiImmobile() {
        return nPianiImmobile;
    }

    public void setNPianiImmobile(Integer nPianiImmobile) {
        this.nPianiImmobile = nPianiImmobile;
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

    public CondizioneImmobile getCondizioneImmobile() {
        return condizioneImmobile;
    }

    public void setCondizioneImmobile(CondizioneImmobile condizioneImmobile) {
        this.condizioneImmobile = condizioneImmobile;
    }

    public ClasseEnergetica getClasseEnergetica(ClasseEnergetica classeEnergetica) {
        return classeEnergetica;
    }

    public void setClasseEnergetica(ClasseEnergetica classeEnergetica) {
        this.classeEnergetica = classeEnergetica;
    }

    public ClasseEnergetica getClasseEnergetica() {
        return classeEnergetica;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "DettagliImmobile{" +
                "idImmobile=" + idImmobile +
                ", nStanze=" + nStanze +
                ", nBagni=" + nBagni +
                ", nPiano=" + nPiano +
                ", nPianiImmobile=" + nPianiImmobile +
                ", balconeTerrazzo=" + balconeTerrazzo +
                ", giardino=" + giardino +
                ", garage=" + garage +
                ", ascensore=" + ascensore +
                ", cantina=" + cantina +
                ", tipoRiscaldamento=" + tipoRiscaldamento +
                ", annoCostruzione=" + annoCostruzione +
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

    // --- ENUM per classe energetica ---
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