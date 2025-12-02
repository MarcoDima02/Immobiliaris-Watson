package com.residea.residea.dto;

import java.time.LocalDateTime;

/**
 * DTO che combina Richiesta + Immobile + DettagliImmobile
 * Utilizzato per visualizzare una richiesta con tutti i dettagli dell'immobile associato
 */
public class RichiestaDettagliImmobileDto {

    // ===== RICHIESTA =====
    private Integer idRichiesta;
    private Integer idUtente;
    private String nomeUtente;
    private String cognomeUtente;
    private String emailUtente;
    private String telefonoUtente;
    private LocalDateTime dataRichiesta;
    private LocalDateTime dataAppuntamento;
    private String stato;
    private String noteUtente;
    private String motivoAnnullamento;

    // ===== IMMOBILE =====
    private Integer idImmobile;
    private String tipologia;
    private String indirizzo;
    private String citta;
    private String provincia;
    private String cap;
    private String statoImmobile;
    private Double latitudine;
    private Double longitudine;

    // ===== DETTAGLI IMMOBILE =====
    private Integer nStanze;
    private Integer nBagni;
    private Integer nPiano;
    private Integer nPianiImmobile;
    private Boolean balconeTerrazzo;
    private Boolean giardino;
    private Boolean garage;
    private Boolean ascensore;
    private Boolean cantina;
    private String tipoRiscaldamento;
    private Integer annoCostruzione;
    private String condizioneImmobile;
    private String classeEnergetica;
    private String esposizione;
    private Double prezzo;

    // ===== COSTRUTTORI =====
    public RichiestaDettagliImmobileDto() {}

    // ===== GETTER & SETTER =====
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

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getCognomeUtente() {
        return cognomeUtente;
    }

    public void setCognomeUtente(String cognomeUtente) {
        this.cognomeUtente = cognomeUtente;
    }

    public String getEmailUtente() {
        return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }

    public String getTelefonoUtente() {
        return telefonoUtente;
    }

    public void setTelefonoUtente(String telefonoUtente) {
        this.telefonoUtente = telefonoUtente;
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

    public Integer getIdImmobile() {
        return idImmobile;
    }

    public void setIdImmobile(Integer idImmobile) {
        this.idImmobile = idImmobile;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
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

    public String getStatoImmobile() {
        return statoImmobile;
    }

    public void setStatoImmobile(String statoImmobile) {
        this.statoImmobile = statoImmobile;
    }

    public Double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(Double latitudine) {
        this.latitudine = latitudine;
    }

    public Double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(Double longitudine) {
        this.longitudine = longitudine;
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

    public Boolean getBalconeTerrazzo() {
        return balconeTerrazzo;
    }

    public void setBalconeTerrazzo(Boolean balconeTerrazzo) {
        this.balconeTerrazzo = balconeTerrazzo;
    }

    public Boolean getGiardino() {
        return giardino;
    }

    public void setGiardino(Boolean giardino) {
        this.giardino = giardino;
    }

    public Boolean getGarage() {
        return garage;
    }

    public void setGarage(Boolean garage) {
        this.garage = garage;
    }

    public Boolean getAscensore() {
        return ascensore;
    }

    public void setAscensore(Boolean ascensore) {
        this.ascensore = ascensore;
    }

    public Boolean getCantina() {
        return cantina;
    }

    public void setCantina(Boolean cantina) {
        this.cantina = cantina;
    }

    public String getTipoRiscaldamento() {
        return tipoRiscaldamento;
    }

    public void setTipoRiscaldamento(String tipoRiscaldamento) {
        this.tipoRiscaldamento = tipoRiscaldamento;
    }

    public Integer getAnnoCostruzione() {
        return annoCostruzione;
    }

    public void setAnnoCostruzione(Integer annoCostruzione) {
        this.annoCostruzione = annoCostruzione;
    }

    public String getCondizioneImmobile() {
        return condizioneImmobile;
    }

    public void setCondizioneImmobile(String condizioneImmobile) {
        this.condizioneImmobile = condizioneImmobile;
    }

    public String getClasseEnergetica() {
        return classeEnergetica;
    }

    public void setClasseEnergetica(String classeEnergetica) {
        this.classeEnergetica = classeEnergetica;
    }

    public String getEsposizione() {
        return esposizione;
    }

    public void setEsposizione(String esposizione) {
        this.esposizione = esposizione;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "RichiestaDettagliImmobileDto{" +
                "idRichiesta=" + idRichiesta +
                ", citta='" + citta + '\'' +
                ", tipologia='" + tipologia + '\'' +
                ", nomeUtente='" + nomeUtente + '\'' +
                ", nStanze=" + nStanze +
                '}';
    }
}
