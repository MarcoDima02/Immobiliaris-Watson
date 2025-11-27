package com.residea.residea.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
/**
 * DTO che aggrega i dati di una richiesta/contratto per la dashboard dell'agente.
 * Include: Contratto, Immobile, DettagliImmobile, Superfici, Richiesta, ValutazioneImmobile
 */
public class AgenteRichiestaDTO {

    // --- CONTRATTO ---
    private Integer idContratto;
    private String tipoContratto;
    private LocalDate dataContratto;
    private LocalDate dataScadenzaContratto;
    private String pathContrattoPDF;

    // --- IMMOBILE ---
    private Integer idImmobile;
    private String tipologia;
    private String indirizzo;
    private String citta;
    private String provincia;
    private String cap;
    private String stato;

    // --- DETTAGLI IMMOBILE ---
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

    // --- SUPERFICI ---
    private BigDecimal superficieMq;
    private BigDecimal superficieBalconeTerrazzo;
    private BigDecimal superficieGiardino;
    private BigDecimal superficieGarage;
    private BigDecimal superficieCantina;

    // --- RICHIESTA ---
    private Integer idRichiesta;
    private LocalDateTime dataRichiesta;
    private LocalDateTime dataAppuntamento;
    private String statoRichiesta;
    private String noteUtente;
    private String motivoAnnullamento;

    // --- UTENTE (che ha fatto la richiesta) ---
    private Integer idUtente;
    private String nomeUtente;
    private String cognomeUtente;
    private String telefonoUtente;
    private String emailUtente;

    // --- VALUTAZIONE IMMOBILE ---
    private Integer idValutazione;
    private Long valoreBase;
    private BigDecimal fattoreAggiustamento;
    private Long valoreMedio;
    private Long valoreMin;
    private Long valoreMax;
    private BigDecimal confidence;

    // --- COSTRUTTORI ---
    public AgenteRichiestaDTO() {}

    public AgenteRichiestaDTO(
        Integer idContratto, String tipoContratto, LocalDate dataContratto, LocalDate dataScadenzaContratto,
        Integer idImmobile, String tipologia, String indirizzo, String citta, String provincia, String cap,
        Integer nStanze, Integer nBagni, Integer nPiano, Integer nPianiImmobile,
        BigDecimal superficieMq,
        Integer idRichiesta, LocalDateTime dataRichiesta, String statoRichiesta,
        Integer idUtente, String nomeUtente, String cognomeUtente, String emailUtente
    ) {
        this.idContratto = idContratto;
        this.tipoContratto = tipoContratto;
        this.dataContratto = dataContratto;
        this.dataScadenzaContratto = dataScadenzaContratto;
        this.idImmobile = idImmobile;
        this.tipologia = tipologia;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.provincia = provincia;
        this.cap = cap;
        this.nStanze = nStanze;
        this.nBagni = nBagni;
        this.nPiano = nPiano;
        this.nPianiImmobile = nPianiImmobile;
        this.superficieMq = superficieMq;
        this.idRichiesta = idRichiesta;
        this.dataRichiesta = dataRichiesta;
        this.statoRichiesta = statoRichiesta;
        this.idUtente = idUtente;
        this.nomeUtente = nomeUtente;
        this.cognomeUtente = cognomeUtente;
        this.emailUtente = emailUtente;
    }

    
}
