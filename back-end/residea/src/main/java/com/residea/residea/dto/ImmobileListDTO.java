package com.residea.residea.dto;

/**
 * DTO per la lista immobili nella dashboard agente
 * Contiene dati essenziali per la visualizzazione in griglia
 */
public class ImmobileListDTO {
    
    // Dati immobile
    private Integer idImmobile;
    private String tipologia;
    private String indirizzo;
    private String citta;
    private String provincia;
    private String cap;
    private String stato;
    
    // Dati proprietario
    private Integer idProprietario;
    private String nomeProprietario;
    private String cognomeProprietario;
    private String emailProprietario;
    private String telefonoProprietario;
    
    // Dati agente assegnato (se presente)
    private Integer idAgente;
    private String nomeAgente;
    private String cognomeAgente;
    
    // Dati richiesta/contratto (se presente)
    private Integer idRichiesta;
    private String statoRichiesta;
    private Integer idContratto;
    private String statoContratto;
    
    // Valutazione
    private Double valutazioneStimata;
    
    // Superficie totale
    private Double superficieTotale;
    
    // Coordinate
    private Double latitudine;
    private Double longitudine;

    // Getters e Setters
    
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

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Integer getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(Integer idProprietario) {
        this.idProprietario = idProprietario;
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

    public String getEmailProprietario() {
        return emailProprietario;
    }

    public void setEmailProprietario(String emailProprietario) {
        this.emailProprietario = emailProprietario;
    }

    public String getTelefonoProprietario() {
        return telefonoProprietario;
    }

    public void setTelefonoProprietario(String telefonoProprietario) {
        this.telefonoProprietario = telefonoProprietario;
    }

    public Integer getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(Integer idAgente) {
        this.idAgente = idAgente;
    }

    public String getNomeAgente() {
        return nomeAgente;
    }

    public void setNomeAgente(String nomeAgente) {
        this.nomeAgente = nomeAgente;
    }

    public String getCognomeAgente() {
        return cognomeAgente;
    }

    public void setCognomeAgente(String cognomeAgente) {
        this.cognomeAgente = cognomeAgente;
    }

    public Integer getIdRichiesta() {
        return idRichiesta;
    }

    public void setIdRichiesta(Integer idRichiesta) {
        this.idRichiesta = idRichiesta;
    }

    public String getStatoRichiesta() {
        return statoRichiesta;
    }

    public void setStatoRichiesta(String statoRichiesta) {
        this.statoRichiesta = statoRichiesta;
    }

    public Integer getIdContratto() {
        return idContratto;
    }

    public void setIdContratto(Integer idContratto) {
        this.idContratto = idContratto;
    }

    public String getStatoContratto() {
        return statoContratto;
    }

    public void setStatoContratto(String statoContratto) {
        this.statoContratto = statoContratto;
    }

    public Double getValutazioneStimata() {
        return valutazioneStimata;
    }

    public void setValutazioneStimata(Double valutazioneStimata) {
        this.valutazioneStimata = valutazioneStimata;
    }

    public Double getSuperficieTotale() {
        return superficieTotale;
    }

    public void setSuperficieTotale(Double superficieTotale) {
        this.superficieTotale = superficieTotale;
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
}
