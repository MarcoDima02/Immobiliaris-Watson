package com.residea.residea.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FormValutazioneRequest {

    // Obbligatori
    // Appartamento, Villa, Casa_indipendente, Monolocale
    private String tipologia;

    // superficie in m² > 0 e < 20000
    private BigDecimal superficie;

    private String indirizzo;

    private String provincia; // sigla

    private String citta;

    private String cap; // 5 cifre

    @JsonProperty("nStanze")
    private Integer nStanze; // >=0

    @JsonProperty("nBagni")
    private Integer nBagni; // >=0

    private String finalitaRichiesta; // STIMA | DOCUMENTO

    // Raccomandati
    private Integer piano;            // nPiano
    private Integer pianiTotali;      // nPianiImmobile
    private Boolean ascensore;        // ascensore
    private Boolean garage;           // garage
    private BigDecimal superficieGarage;   // superficieGarage
    private BigDecimal superficieBalconeTerrazzo; // superficieBalconeTerrazzo
    private Boolean giardino;         // giardino
    private BigDecimal superficieGiardino;  // superficieGiardino
    private Boolean cantina;          // cantina
    private BigDecimal superficieCantina;   // superficieCantina

    private String tipoRiscaldamento; // No, Autonomo, Condominiale, Pompe_di_calore, Pavimento
    private String classeEnergetica;  // A_PLUS, A, B, ..., G
    private Integer annoCostruzione;  // 1800..currentYear
    private String condizione;        // NUOVO, RISTRUTTURATO, PARZIALMENTE_RISTRUTTURATO, NON_RISTRUTTURATO
    private String esposizione;       // opzionale

    // Metadata opzionali
    private Integer idUtente;         // proprietario

    // Getters & Setters
    public String getTipologia() { return tipologia; }
    public void setTipologia(String tipologia) { this.tipologia = tipologia; }

    public BigDecimal getSuperficie() { return superficie; }
    public void setSuperficie(BigDecimal superficie) { this.superficie = superficie; }

    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getCitta() { return citta; }
    public void setCitta(String citta) { this.citta = citta; }

    public String getCap() { return cap; }
    public void setCap(String cap) { this.cap = cap; }

    public Integer getNStanze() { return nStanze; }
    public void setNStanze(Integer nStanze) { this.nStanze = nStanze; }

    public Integer getNBagni() { return nBagni; }
    public void setNBagni(Integer nBagni) { this.nBagni = nBagni; }

    public String getFinalitaRichiesta() { return finalitaRichiesta; }
    public void setFinalitaRichiesta(String finalitaRichiesta) { this.finalitaRichiesta = finalitaRichiesta; }

    public Integer getPiano() { return piano; }
    public void setPiano(Integer piano) { this.piano = piano; }

    public Integer getPianiTotali() { return pianiTotali; }
    public void setPianiTotali(Integer pianiTotali) { this.pianiTotali = pianiTotali; }

    public Boolean getAscensore() { return ascensore; }
    public void setAscensore(Boolean ascensore) { this.ascensore = ascensore; }

    public Boolean getGarage() { return garage; }
    public void setGarage(Boolean garage) { this.garage = garage; }

    public BigDecimal getSuperficieGarage() { return superficieGarage; }
    public void setSuperficieGarage(BigDecimal superficieGarage) { this.superficieGarage = superficieGarage; }

    public BigDecimal getSuperficieBalconeTerrazzo() { return superficieBalconeTerrazzo; }
    public void setSuperficieBalconeTerrazzo(BigDecimal superficieBalconeTerrazzo) { this.superficieBalconeTerrazzo = superficieBalconeTerrazzo; }

    public Boolean getGiardino() { return giardino; }
    public void setGiardino(Boolean giardino) { this.giardino = giardino; }

    public BigDecimal getSuperficieGiardino() { return superficieGiardino; }
    public void setSuperficieGiardino(BigDecimal superficieGiardino) { this.superficieGiardino = superficieGiardino; }

    public Boolean getCantina() { return cantina; }
    public void setCantina(Boolean cantina) { this.cantina = cantina; }

    public BigDecimal getSuperficieCantina() { return superficieCantina; }
    public void setSuperficieCantina(BigDecimal superficieCantina) { this.superficieCantina = superficieCantina; }

    public String getTipoRiscaldamento() { return tipoRiscaldamento; }
    public void setTipoRiscaldamento(String tipoRiscaldamento) { this.tipoRiscaldamento = tipoRiscaldamento; }

    public String getClasseEnergetica() { return classeEnergetica; }
    public void setClasseEnergetica(String classeEnergetica) { this.classeEnergetica = classeEnergetica; }

    public Integer getAnnoCostruzione() { return annoCostruzione; }
    public void setAnnoCostruzione(Integer annoCostruzione) { this.annoCostruzione = annoCostruzione; }

    public String getCondizione() { return condizione; }
    public void setCondizione(String condizione) { this.condizione = condizione; }

    public String getEsposizione() { return esposizione; }
    public void setEsposizione(String esposizione) { this.esposizione = esposizione; }

    public Integer getIdUtente() { return idUtente; }
    public void setIdUtente(Integer idUtente) { this.idUtente = idUtente; }
}
