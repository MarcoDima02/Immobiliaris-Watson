package com.residea.residea.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
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
    private Integer idUtente;         // proprietario (se già registrato)
    
    // Dati utente (per utenti non ancora registrati)
    private String nomeUtente;
    private String cognomeUtente;
    private String emailUtente;
    private String telefonoUtente;
}
