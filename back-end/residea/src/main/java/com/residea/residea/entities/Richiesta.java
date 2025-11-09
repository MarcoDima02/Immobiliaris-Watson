package com.residea.residea.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Richiesta")
public class Richiesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRichiesta")
    private Integer idRichiesta;

    @ManyToOne
    @JoinColumn(name = "idUtente", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "idImmobile", nullable = false)
    private Immobile immobile;

    @Column(name = "dataRichiesta", nullable = false)
    private LocalDateTime dataRichiesta;

    @Column(name = "dataAppuntamento")
    private LocalDateTime dataAppuntamento;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Stato stato;

    @Column(columnDefinition = "TEXT")
    private String noteUtente;

    @Column(columnDefinition = "TEXT")
    private String motivoAnnullamento;

    // --- COSTRUTTORI ---
    public Richiesta() {}

    public Richiesta(Utente utente, Immobile immobile, LocalDateTime dataRichiesta, LocalDateTime dataAppuntamento, Stato stato, String noteUtente, String motivoAnnullamento) {
        this.utente = utente;
        this.immobile = immobile;
        this.dataRichiesta = dataRichiesta;
        this.dataAppuntamento = dataAppuntamento;
        this.stato = stato;
        this.noteUtente = noteUtente;
        this.motivoAnnullamento = motivoAnnullamento;
    }

    // --- GETTER & SETTER ---
    public Integer getIdRichiesta() {
        return idRichiesta;
    }

    public void setIdRichiesta(Integer idRichiesta) {
        this.idRichiesta = idRichiesta;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Immobile getImmobile() {
        return immobile;
    }

    public void setImmobile(Immobile immobile) {
        this.immobile = immobile;
    }

    public LocalDateTime getDataRichiesta() {
        return dataRichiesta;
    }

    public void setDataRichiesta(LocalDateTime dataRichiesta) {
        this.dataRichiesta = dataRichiesta;
    }

    public LocalDateTime getDataAppuntamento() {
        return dataAppuntamento;
    }

    public void setDataAppuntamento(LocalDateTime dataAppuntamento) {
        this.dataAppuntamento = dataAppuntamento;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    public String getNoteUtente() {
        return noteUtente;
    }

    public void setNoteUtente(String noteUtente) {
        this.noteUtente = noteUtente;
    }

    public String getMotivoAnnullamento() {
        return motivoAnnullamento;
    }

    public void setMotivoAnnullamento(String motivoAnnullamento) {
        this.motivoAnnullamento = motivoAnnullamento;
    }

    @Override
    public String toString() {
        return "Prenotazione{" +
                "idRichiesta=" + idRichiesta +
                ", utente=" + (utente != null ? utente.getIdUtente() : null) +
                ", immobile=" + (immobile != null ? immobile.getIdImmobile() : null) +
                ", dataRichiesta=" + dataRichiesta +
                ", dataAppuntamento=" + dataAppuntamento +
                ", stato='" + stato + '\'' +
                ", noteUtente='" + noteUtente + '\'' +
                ", motivoAnnullamento='" + motivoAnnullamento + '\'' +
                '}';
    }

    // --- ENUM per stato ---
    public enum Stato {
        IN_ATTESA("In attesa"),
        IN_ELABORAZIONE("In elaborazione"),
        COMPLETATA("Completata"),
        ANNULLATA("Annullata");

        private final String displayValue;

        Stato(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }
}