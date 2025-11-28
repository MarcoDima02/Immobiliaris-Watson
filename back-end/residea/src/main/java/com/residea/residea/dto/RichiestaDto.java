package com.residea.residea.dto;

import java.time.LocalDateTime;

public class RichiestaDto {
    private Integer idRichiesta;
    private Integer idUtente;
    private Integer idImmobile;
    private LocalDateTime dataRichiesta;
    private LocalDateTime dataAppuntamento;
    private String stato;
    private String noteUtente;
    private String motivoAnnullamento;

    public Integer getIdRichiesta() {
        return idRichiesta;
    }

    public void setIdRichiesta(Integer idRichiesta) {
        this.idRichiesta = idRichiesta;
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public Integer getIdImmobile() {
        return idImmobile;
    }

    public void setIdImmobile(Integer idImmobile) {
        this.idImmobile = idImmobile;
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
}
