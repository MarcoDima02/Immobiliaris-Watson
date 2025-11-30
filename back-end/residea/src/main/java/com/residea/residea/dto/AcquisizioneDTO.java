package com.residea.residea.dto;

import java.time.LocalDate;

/**
 * DTO per le acquisizioni (contratti completati) nella dashboard agente
 */
public class AcquisizioneDTO {
    
    // Dati contratto
    private Integer idContratto;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String stato;
    private Double commissione;
    private String terminiCondizioni;
    
    // Dati cliente
    private Integer idCliente;
    private String nomeCliente;
    private String cognomeCliente;
    private String emailCliente;
    private String telefonoCliente;
    
    // Dati immobile
    private Integer idImmobile;
    private String tipologiaImmobile;
    private String indirizzoImmobile;
    private String cittaImmobile;
    private String provinciaImmobile;
    
    // Dati valutazione
    private Double valutazioneFinale;
    private Double superficieTotale;
    
    // Dati proprietario
    private String nomeProprietario;
    private String cognomeProprietario;

    // Getters e Setters
    
    public Integer getIdContratto() {
        return idContratto;
    }

    public void setIdContratto(Integer idContratto) {
        this.idContratto = idContratto;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Double getCommissione() {
        return commissione;
    }

    public void setCommissione(Double commissione) {
        this.commissione = commissione;
    }

    public String getTerminiCondizioni() {
        return terminiCondizioni;
    }

    public void setTerminiCondizioni(String terminiCondizioni) {
        this.terminiCondizioni = terminiCondizioni;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCognomeCliente() {
        return cognomeCliente;
    }

    public void setCognomeCliente(String cognomeCliente) {
        this.cognomeCliente = cognomeCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public Integer getIdImmobile() {
        return idImmobile;
    }

    public void setIdImmobile(Integer idImmobile) {
        this.idImmobile = idImmobile;
    }

    public String getTipologiaImmobile() {
        return tipologiaImmobile;
    }

    public void setTipologiaImmobile(String tipologiaImmobile) {
        this.tipologiaImmobile = tipologiaImmobile;
    }

    public String getIndirizzoImmobile() {
        return indirizzoImmobile;
    }

    public void setIndirizzoImmobile(String indirizzoImmobile) {
        this.indirizzoImmobile = indirizzoImmobile;
    }

    public String getCittaImmobile() {
        return cittaImmobile;
    }

    public void setCittaImmobile(String cittaImmobile) {
        this.cittaImmobile = cittaImmobile;
    }

    public String getProvinciaImmobile() {
        return provinciaImmobile;
    }

    public void setProvinciaImmobile(String provinciaImmobile) {
        this.provinciaImmobile = provinciaImmobile;
    }

    public Double getValutazioneFinale() {
        return valutazioneFinale;
    }

    public void setValutazioneFinale(Double valutazioneFinale) {
        this.valutazioneFinale = valutazioneFinale;
    }

    public Double getSuperficieTotale() {
        return superficieTotale;
    }

    public void setSuperficieTotale(Double superficieTotale) {
        this.superficieTotale = superficieTotale;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    public String getCognomeProprietario() {
        return cognomeProprietario;
    }

    public void setCognomeProprietario(String cognomeProprietario) {
        this.cognomeProprietario = cognomeProprietario;
    }
}
