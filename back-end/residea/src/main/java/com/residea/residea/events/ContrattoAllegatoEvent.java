package com.residea.residea.events;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Evento emesso quando un agente allega un contratto PDF.
 */
@Data
@AllArgsConstructor
public class ContrattoAllegatoEvent {
    
    private Integer idContratto;
    private Integer idImmobile;
    private Integer idProprietario;
    
    // Dati del proprietario per l'email
    private String proprietarioEmail;
    private String proprietarioNome;
    private String proprietarioCognome;
    
    // Dati dell'agente
    private String agenteNome;
    private String agenteCognome;
    
    // Dati contratto
    private String tipoContratto;
    private String dataContratto;
    private String pathContrattoPDF;
    
    // Dati immobile
    private String immobileIndirizzo;
    private String immobileCitta;
}
