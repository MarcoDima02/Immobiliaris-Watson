package com.residea.residea.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO UNIFICATO che combina Contratto + Richiesta + Immobile + DettagliImmobile + Superfici + Valutazione
 * Utilizzato sia per dashboard Agente che Admin
 * 
 * NAMING STANDARD:
 * - statoRichiesta: stato della richiesta (IN_ATTESA, IN_ELABORAZIONE, ecc.)
 * - statoImmobile: stato dell'immobile (DISPONIBILE, VENDUTO, ecc.)
 */
public class RichiestaDettagliImmobileDto {

    // ===== CONTRATTO (solo se presente) =====
    private Integer idContratto;
    private String tipoContratto;
    private LocalDate dataContratto;
    private LocalDate dataScadenzaContratto;
    private String pathContrattoPDF;

    // ===== RICHIESTA =====
    private Integer idRichiesta;
    private LocalDateTime dataRichiesta;
    private LocalDateTime dataAppuntamento;
    private String statoRichiesta;  // STANDARDIZZATO: era "stato" in Admin
    private String noteUtente;
    private String motivoAnnullamento;
    
    // ===== UTENTE (richiedente) =====
    private Integer idUtente;
    private String nomeUtente;
    private String cognomeUtente;
    private String emailUtente;
    private String telefonoUtente;

    // ===== IMMOBILE =====
    private Integer idImmobile;
    private String tipologia;
    private String indirizzo;
    private String citta;
    private String provincia;
    private String cap;
    private String statoImmobile;  // STANDARDIZZATO: era "stato" in Agente
    private Double latitudine;     // Solo Admin
    private Double longitudine;    // Solo Admin

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
    private String esposizione;    // Solo Admin
    private Double prezzo;         // Solo Admin
    
    // ===== SUPERFICI (nuovo da Agente) =====
    private BigDecimal superficieMq;
    private BigDecimal superficieBalconeTerrazzo;
    private BigDecimal superficieGiardino;
    private BigDecimal superficieGarage;
    private BigDecimal superficieCantina;
    
    // ===== VALUTAZIONE (nuovo da Agente) =====
    private Integer idValutazione;
    private Long valoreBase;
    private BigDecimal fattoreAggiustamento;
    private Long valoreMedio;
    private Long valoreMin;
    private Long valoreMax;
    private BigDecimal confidence;

    // ===== COSTRUTTORI =====
    public RichiestaDettagliImmobileDto() {}

    // ===== GETTER & SETTER =====
    
    // CONTRATTO
    public Integer getIdContratto() {
        return idContratto;
    }

    public void setIdContratto(Integer idContratto) {
        this.idContratto = idContratto;
    }

    public String getTipoContratto() {
        return tipoContratto;
    }

    public void setTipoContratto(String tipoContratto) {
        this.tipoContratto = tipoContratto;
    }

    public LocalDate getDataContratto() {
        return dataContratto;
    }

    public void setDataContratto(LocalDate dataContratto) {
        this.dataContratto = dataContratto;
    }

    public LocalDate getDataScadenzaContratto() {
        return dataScadenzaContratto;
    }

    public void setDataScadenzaContratto(LocalDate dataScadenzaContratto) {
        this.dataScadenzaContratto = dataScadenzaContratto;
    }

    public String getPathContrattoPDF() {
        return pathContrattoPDF;
    }

    public void setPathContrattoPDF(String pathContrattoPDF) {
        this.pathContrattoPDF = pathContrattoPDF;
    }
    
    // RICHIESTA
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
        return statoRichiesta;  // COMPATIBILITÀ: getter per vecchio nome
    }

    public void setStato(String stato) {
        this.statoRichiesta = stato;  // COMPATIBILITÀ: setter per vecchio nome
    }

    public String getStatoRichiesta() {
        return statoRichiesta;
    }

    public void setStatoRichiesta(String statoRichiesta) {
        this.statoRichiesta = statoRichiesta;
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

    // SUPERFICI
    public BigDecimal getSuperficieMq() {
        return superficieMq;
    }

    public void setSuperficieMq(BigDecimal superficieMq) {
        this.superficieMq = superficieMq;
    }

    public BigDecimal getSuperficieBalconeTerrazzo() {
        return superficieBalconeTerrazzo;
    }

    public void setSuperficieBalconeTerrazzo(BigDecimal superficieBalconeTerrazzo) {
        this.superficieBalconeTerrazzo = superficieBalconeTerrazzo;
    }

    public BigDecimal getSuperficieGiardino() {
        return superficieGiardino;
    }

    public void setSuperficieGiardino(BigDecimal superficieGiardino) {
        this.superficieGiardino = superficieGiardino;
    }

    public BigDecimal getSuperficieGarage() {
        return superficieGarage;
    }

    public void setSuperficieGarage(BigDecimal superficieGarage) {
        this.superficieGarage = superficieGarage;
    }

    public BigDecimal getSuperficieCantina() {
        return superficieCantina;
    }

    public void setSuperficieCantina(BigDecimal superficieCantina) {
        this.superficieCantina = superficieCantina;
    }

    // VALUTAZIONE
    public Integer getIdValutazione() {
        return idValutazione;
    }

    public void setIdValutazione(Integer idValutazione) {
        this.idValutazione = idValutazione;
    }

    public Long getValoreBase() {
        return valoreBase;
    }

    public void setValoreBase(Long valoreBase) {
        this.valoreBase = valoreBase;
    }

    public BigDecimal getFattoreAggiustamento() {
        return fattoreAggiustamento;
    }

    public void setFattoreAggiustamento(BigDecimal fattoreAggiustamento) {
        this.fattoreAggiustamento = fattoreAggiustamento;
    }

    public Long getValoreMedio() {
        return valoreMedio;
    }

    public void setValoreMedio(Long valoreMedio) {
        this.valoreMedio = valoreMedio;
    }

    public Long getValoreMin() {
        return valoreMin;
    }

    public void setValoreMin(Long valoreMin) {
        this.valoreMin = valoreMin;
    }

    public Long getValoreMax() {
        return valoreMax;
    }

    public void setValoreMax(Long valoreMax) {
        this.valoreMax = valoreMax;
    }

    public BigDecimal getConfidence() {
        return confidence;
    }

    public void setConfidence(BigDecimal confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "RichiestaDettagliImmobileDto{" +
                "idRichiesta=" + idRichiesta +
                ", citta='" + citta + '\'' +
                ", tipologia='" + tipologia + '\'' +
                ", nomeUtente='" + nomeUtente + '\'' +
                ", nStanze=" + nStanze +
                ", statoRichiesta='" + statoRichiesta + '\'' +
                ", idContratto=" + idContratto +
                '}';
    }
}
