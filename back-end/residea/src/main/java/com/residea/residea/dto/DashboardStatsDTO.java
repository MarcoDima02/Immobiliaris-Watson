package com.residea.residea.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO per le statistiche della dashboard agente.
 * Contiene i contatori delle richieste per stato.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    
    /**
     * Numero di richieste prese in carico dall'agente (stato: IN_ATTESA, IN_CORSO)
     */
    private Integer richiesteInCarico;
    
    /**
     * Numero di richieste completate con successo (stato: COMPLETATA)
     */
    private Integer richiesteCompletate;
    
    /**
     * Numero di richieste archiviate o annullate (stato: ARCHIVIATA, ANNULLATA)
     */
    private Integer richiesteArchiviate;
}
