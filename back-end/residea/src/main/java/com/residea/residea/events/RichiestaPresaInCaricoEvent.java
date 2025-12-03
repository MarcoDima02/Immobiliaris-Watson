package com.residea.residea.events;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Evento emesso quando un agente prende in carico una richiesta di valutazione.
 */
@Data
@AllArgsConstructor
public class RichiestaPresaInCaricoEvent {
    
    private Integer idRichiesta;
    private Integer idAgente;
    private Integer idProprietario;
    
    // Dati del proprietario per l'email
    private String proprietarioEmail;
    private String proprietarioNome;
    private String proprietarioCognome;
    
    // Dati dell'agente per l'email
    private String agenteNome;
    private String agenteCognome;
    private String agenteEmail;
    private String agenteTelefono;
    
    // Dati dell'immobile (opzionali)
    private String immobileIndirizzo;
    private String immobileCitta;
}
