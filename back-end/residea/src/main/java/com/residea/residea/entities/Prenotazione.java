package com.residea.residea.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prenotazioni")
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPrenotazione")
    private Integer idPrenotazione;

    @ManyToOne
    @JoinColumn(name = "idUtente", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "idImmobile", nullable = false)
    private Immobile immobile;

    @Column(name = "dataPrenotazione", nullable = false)
    private LocalDateTime dataPrenotazione;

    @Column(name = "dataAppuntamento")
    private LocalDateTime dataAppuntamento;

    @Column(length = 50)
    private String stato;

    @Column(columnDefinition = "TEXT")
    private String noteUtente;

    @Column(columnDefinition = "TEXT")
    private String motivoAnnullamento;

    // --- COSTRUTTORI ---
    public Prenotazione(Utente utente, Immobile immobile, LocalDateTime dataPrenotazione, LocalDateTime dataAppuntamento, String stato, String noteUtente, String motivoAnnullamento) {
        this.utente = utente;
        this.immobile = immobile;
        this.dataPrenotazione = dataPrenotazione;
        this.dataAppuntamento = dataAppuntamento;
        this.stato = stato;
        this.noteUtente = noteUtente;
        this.motivoAnnullamento = motivoAnnullamento;
    }

    // --- GETTER & SETTER ---
    public Integer getIdPrenotazione() {
        return idPrenotazione;
    }

    public void setIdPrenotazione(Integer idPrenotazione) {
        this.idPrenotazione = idPrenotazione;
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

    public LocalDateTime getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(LocalDateTime dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public LocalDateTime getDataAppuntamento() {
        return dataAppuntamento;
    }

    public void setDataAppuntamento(LocalDateTime dataAppuntamento) {
        this.dataAppuntamento = dataAppuntamento;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
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
                "idPrenotazione=" + idPrenotazione +
                ", utente=" + (utente != null ? utente.getIdUtente() : null) +
                ", immobile=" + (immobile != null ? immobile.getIdImmobile() : null) +
                ", dataPrenotazione=" + dataPrenotazione +
                ", dataAppuntamento=" + dataAppuntamento +
                ", stato='" + stato + '\'' +
                ", noteUtente='" + noteUtente + '\'' +
                ", motivoAnnullamento='" + motivoAnnullamento + '\'' +
                '}';
    }
}