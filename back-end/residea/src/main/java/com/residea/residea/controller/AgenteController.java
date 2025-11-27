package com.residea.residea.controller;

import com.residea.residea.dto.AgenteRichiestaDTO;
import com.residea.residea.services.AgenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller per gestire i dati della dashboard dell'agente immobiliare.
 * Espone endpoint per visualizzare contratti, immobili, richieste, valutazioni, ecc.
 */
@RestController
@RequestMapping("/api/agente")
public class AgenteController {

    @Autowired
    private AgenteService agenteService;

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
}
