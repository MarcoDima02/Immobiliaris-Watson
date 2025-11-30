package com.residea.residea.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.residea.residea.dto.AgenteRichiestaDTO;
import com.residea.residea.dto.DashboardStatsDTO;
import com.residea.residea.dto.RichiestaCardDTO;
import com.residea.residea.services.AgenteService;

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

    /**
     * GET /api/agente/{idAgente}/dashboard/stats
     * 
     * Restituisce le statistiche per la dashboard dell'agente:
     * - Numero richieste in carico
     * - Numero richieste completate
     * - Numero richieste archiviate
     * 
     * @param idAgente ID dell'agente
     * @return DashboardStatsDTO con contatori
     */
    @GetMapping("/{idAgente}/dashboard/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats(@PathVariable Integer idAgente) {
        try {
            DashboardStatsDTO stats = agenteService.getDashboardStats(idAgente);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            logger.error("Errore nel fetching stats dashboard per agente: " + idAgente, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * GET /api/agente/richieste/attesa
     * 
     * Restituisce tutte le richieste in attesa (non ancora prese in carico da nessun agente).
     * 
     * @return Lista di RichiestaCardDTO
     */
    @GetMapping("/richieste/attesa")
    public ResponseEntity<List<RichiestaCardDTO>> getRichiesteInAttesa() {
        try {
            List<RichiestaCardDTO> richieste = agenteService.getRichiesteInAttesa();
            return ResponseEntity.ok(richieste);
        } catch (Exception e) {
            logger.error("Errore nel fetching richieste in attesa", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * GET /api/agente/{idAgente}/richieste/carico
     * 
     * Restituisce le richieste prese in carico dall'agente specifico.
     * 
     * @param idAgente ID dell'agente
     * @return Lista di RichiestaCardDTO
     */
    @GetMapping("/{idAgente}/richieste/carico")
    public ResponseEntity<List<RichiestaCardDTO>> getRichiesteInCarico(@PathVariable Integer idAgente) {
        try {
            List<RichiestaCardDTO> richieste = agenteService.getRichiesteInCarico(idAgente);
            return ResponseEntity.ok(richieste);
        } catch (Exception e) {
            logger.error("Errore nel fetching richieste in carico per agente: " + idAgente, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * POST /api/agente/{idAgente}/richieste/{idRichiesta}/prendi-in-carico
     * 
     * Assegna una richiesta all'agente creando un contratto.
     * 
     * @param idAgente ID dell'agente
     * @param idRichiesta ID della richiesta da prendere in carico
     * @return Messaggio di conferma
     */
    @PostMapping("/{idAgente}/richieste/{idRichiesta}/prendi-in-carico")
    public ResponseEntity<String> prendiInCarico(
        @PathVariable Integer idAgente, 
        @PathVariable Integer idRichiesta
    ) {
        try {
            agenteService.prendiInCaricoRichiesta(idAgente, idRichiesta);
            return ResponseEntity.ok("Richiesta presa in carico con successo");
        } catch (Exception e) {
            logger.error("Errore prendendo in carico richiesta: " + idRichiesta + " per agente: " + idAgente, e);
            return ResponseEntity.status(500).body("Errore: " + e.getMessage());
        }
    }
    
    /**
     * GET /api/agente/{idAgente}/acquisizioni
     * Recupera tutte le acquisizioni (contratti completati) di un agente.
     * 
     * @param idAgente ID dell'agente
     * @return Lista di AcquisizioneDTO
     */
    @GetMapping("/{idAgente}/acquisizioni")
    public ResponseEntity<List<com.residea.residea.dto.AcquisizioneDTO>> getAcquisizioni(
        @PathVariable Integer idAgente
    ) {
        try {
            List<com.residea.residea.dto.AcquisizioneDTO> acquisizioni = agenteService.getAcquisizioni(idAgente);
            return ResponseEntity.ok(acquisizioni);
        } catch (Exception e) {
            logger.error("Errore recuperando acquisizioni per agente: " + idAgente, e);
            return ResponseEntity.status(500).build();
        }
    }
    
    /**
     * PUT /api/agente/{idAgente}/richieste/{idRichiesta}/stato
     * Modifica lo stato di una richiesta.
     * Transizioni valide:
     * - IN_ATTESA -> IN_ELABORAZIONE (tramite prendi in carico)
     * - IN_ELABORAZIONE -> COMPLETATA
     * - Qualsiasi -> ANNULLATA
     * 
     * @param idAgente ID dell'agente
     * @param idRichiesta ID della richiesta
     * @param nuovoStato Nuovo stato da impostare (IN_ELABORAZIONE, COMPLETATA, ANNULLATA)
     * @return Messaggio di conferma
     */
    @PutMapping("/{idAgente}/richieste/{idRichiesta}/stato")
    public ResponseEntity<String> cambiaStatoRichiesta(
        @PathVariable Integer idAgente,
        @PathVariable Integer idRichiesta,
        @org.springframework.web.bind.annotation.RequestParam String nuovoStato
    ) {
        try {
            agenteService.cambiaStatoRichiesta(idAgente, idRichiesta, nuovoStato);
            return ResponseEntity.ok("Stato richiesta aggiornato con successo");
        } catch (IllegalArgumentException e) {
            logger.error("Stato non valido: " + nuovoStato, e);
            return ResponseEntity.badRequest().body("Errore: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Errore cambiando stato richiesta: " + idRichiesta, e);
            return ResponseEntity.status(500).body("Errore: " + e.getMessage());
        }
    }
}
