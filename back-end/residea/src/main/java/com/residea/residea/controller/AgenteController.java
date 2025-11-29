package com.residea.residea.controller;

import com.residea.residea.dto.AgenteRichiestaDTO;
import com.residea.residea.services.AgenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller per gestire i dati della dashboard dell'agente immobiliare.
 * Espone endpoint per visualizzare contratti, immobili, richieste, valutazioni, ecc.
 */
@RestController
@RequestMapping("/api/agente")
public class AgenteController {

    @Autowired
    private AgenteService agenteService;
    
    private static final Logger logger = LoggerFactory.getLogger(AgenteController.class);

    /**
     * GET /api/agente/dashboard/{idAgente}
     * 
     * Restituisce i dati aggregati per la dashboard dell'agente:
     * - Contratti gestiti dall'agente
     * - Immobili associati ai contratti
     * - Dettagli immobili
     * - Superfici
     * - Richieste associate agli immobili
     * - Dati utenti che hanno fatto richiesta
     * - Valutazioni immobili
     * 
     * @param idAgente ID dell'agente
     * @return Lista di AgenteRichiestaDTO con dati aggregati
     */
    @GetMapping("/dashboard/{idAgente}")
    public ResponseEntity<List<AgenteRichiestaDTO>> getDashboard(@PathVariable Integer idAgente) {
        try {
            List<AgenteRichiestaDTO> dashboardData = agenteService.getDashboardData(idAgente);
            return ResponseEntity.ok(dashboardData);
        } catch (Exception e) {
            logger.error("Errore nel fetching dashboard per agente: " + idAgente, e);
            return ResponseEntity.status(500).body(null);
        }

    }

    /**
     * GET /api/agente/contratti/{idAgente}
     * 
     * Restituisce solo i contratti gestiti dall'agente.
     * (Optional: se vuoi avere endpoint specifico)
     * 
     * @param idAgente ID dell'agente
     * @return Lista di AgenteRichiestaDTO con dati dei soli contratti
     */
    @GetMapping("/contratti/{idAgente}")
    public ResponseEntity<List<AgenteRichiestaDTO>> getContrattiByAgente(@PathVariable Integer idAgente) {
        try {
            List<AgenteRichiestaDTO> contratti = agenteService.getDashboardData(idAgente);
            return ResponseEntity.ok(contratti);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * GET /api/agente/richieste-prese-in-carico/{idAgente}
     * 
     * Restituisce tutte le richieste prese in carico dall'agente con dettagli completi.
     * Include: contratti, immobili, dettagli immobili, superfici, richieste, valutazioni, utenti.
     * 
     * @param idAgente ID dell'agente
     * @return Lista di AgenteRichiestaDTO con dati aggregati delle richieste
     */
    @GetMapping("/richieste-prese-in-carico/{idAgente}")
    public ResponseEntity<List<AgenteRichiestaDTO>> getRichiestePrese(@PathVariable Integer idAgente) {
        try {
            List<AgenteRichiestaDTO> richieste = agenteService.getDashboardData(idAgente);
            return ResponseEntity.ok(richieste);
        } catch (Exception e) {
            logger.error("Errore nel fetching richieste prese in carico per agente: " + idAgente, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * GET /api/agente/richiesta-dettagli/{idContratto}
     * 
     * Restituisce i dettagli completi di una singola richiesta/contratto.
     * 
     * @param idContratto ID del contratto
     * @return AgenteRichiestaDTO con dettagli della richiesta
     */
    @GetMapping("/richiesta-dettagli/{idContratto}")
    public ResponseEntity<AgenteRichiestaDTO> getRichiestaDettagli(@PathVariable Integer idContratto) {
        try {
            AgenteRichiestaDTO richiestaDettagli = agenteService.getRichiestaDettagli(idContratto);
            if (richiestaDettagli != null) {
                return ResponseEntity.ok(richiestaDettagli);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            logger.error("Errore nel fetching dettagli richiesta per contratto: " + idContratto, e);
            return ResponseEntity.status(500).body(null);
        }
    }
}
