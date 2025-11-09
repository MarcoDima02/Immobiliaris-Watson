package com.residea.residea.dto;

public class FormValutazioneResponse {
    private Integer idImmobile;
    private Integer idDettagli;   // uguale a idImmobile
    private Integer idSuperfici;  // uguale a idImmobile
    private String stato;

    public FormValutazioneResponse() {}

    public FormValutazioneResponse(Integer idImmobile, Integer idDettagli, Integer idSuperfici, String stato) {
        this.idImmobile = idImmobile;
        this.idDettagli = idDettagli;
        this.idSuperfici = idSuperfici;
        this.stato = stato;
    }

    public Integer getIdImmobile() { return idImmobile; }
    public void setIdImmobile(Integer idImmobile) { this.idImmobile = idImmobile; }

    public Integer getIdDettagli() { return idDettagli; }
    public void setIdDettagli(Integer idDettagli) { this.idDettagli = idDettagli; }

    public Integer getIdSuperfici() { return idSuperfici; }
    public void setIdSuperfici(Integer idSuperfici) { this.idSuperfici = idSuperfici; }

    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }
}
