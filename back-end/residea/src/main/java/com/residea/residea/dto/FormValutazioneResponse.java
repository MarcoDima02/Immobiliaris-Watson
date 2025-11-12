package com.residea.residea.dto;

public class FormValutazioneResponse {
    private Integer idImmobile;
    private Integer idDettagli;   // uguale a idImmobile
    private Integer idSuperfici;  // uguale a idImmobile
    private Integer idUtente;     // ID dell'utente proprietario
    private String stato;

    public FormValutazioneResponse() {}

    public FormValutazioneResponse(Integer idImmobile, Integer idDettagli, Integer idSuperfici, Integer idUtente, String stato) {
        this.idImmobile = idImmobile;
        this.idDettagli = idDettagli;
        this.idSuperfici = idSuperfici;
        this.idUtente = idUtente;
        this.stato = stato;
    }

    public Integer getIdImmobile() { return idImmobile; }
    public void setIdImmobile(Integer idImmobile) { this.idImmobile = idImmobile; }

    public Integer getIdDettagli() { return idDettagli; }
    public void setIdDettagli(Integer idDettagli) { this.idDettagli = idDettagli; }

    public Integer getIdSuperfici() { return idSuperfici; }
    public void setIdSuperfici(Integer idSuperfici) { this.idSuperfici = idSuperfici; }

    public Integer getIdUtente() { return idUtente; }
    public void setIdUtente(Integer idUtente) { this.idUtente = idUtente; }

    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }
}
