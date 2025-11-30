package com.residea.residea.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO per le card delle richieste nella dashboard agente.
 * Include dati cliente, immobile, valutazione e posizione.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RichiestaCardDTO {
    
    // --- RICHIESTA ---
    private Integer idRichiesta;
    private LocalDateTime dataRichiesta;
    private LocalDateTime dataAppuntamento;
    private String stato;
    private String noteUtente;
    
    // --- CLIENTE ---
    private Integer idCliente;
    private String nomeCliente;
    private String cognomeCliente;
    private String telefonoCliente;
    private String emailCliente;
    
    // --- IMMOBILE ---
    private Integer idImmobile;
    private String indirizzo;
    private String citta;
    private String provincia;
    private String cap;
    private String tipologia;
    private String statoImmobile;
    
    // --- DETTAGLI IMMOBILE ---
    private Integer nLocali;  // nStanze
    private Integer nBagni;
    private BigDecimal superficieMq;
    private Boolean balconeTerrazzo;
    
    // --- COORDINATE PER MAPPA ---
    private Double latitudine;
    private Double longitudine;
    
    // --- VALUTAZIONE ---
    private Integer idValutazione;
    private Long valoreMin;
    private Long valoreMax;
    private Long valoreMedio;
    private BigDecimal confidence;
    
    // --- AGENTE ASSEGNATO (se presente) ---
    private Integer idAgente;
    private String nomeAgente;
    private String cognomeAgente;
}
