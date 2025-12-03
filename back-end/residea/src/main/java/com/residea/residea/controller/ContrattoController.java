package com.residea.residea.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.residea.residea.entities.Contratto;
import com.residea.residea.entities.Contratto.TipoContratto;
import com.residea.residea.events.ContrattoAllegatoEvent;
import com.residea.residea.services.ContrattoService;

@RestController
@RequestMapping("/api/contratti")
public class ContrattoController {

    @Autowired
    private ContrattoService contrattoService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    // --- READ ---
    @GetMapping
    public List<Contratto> getAll() {
        return contrattoService.getAllContratti();
    }

    @GetMapping("/{id}")
    public Contratto getById(@PathVariable Integer id) {
        return contrattoService.getContrattoById(id);
    }

    @GetMapping("/tipo/{tipo}")
    public List<Contratto> getByTipo(@PathVariable String tipo) {
        Contratto.TipoContratto tipoEnum = Contratto.TipoContratto.fromString(tipo);
        if (tipoEnum == null) return List.of();
        return contrattoService.getContrattiByTipo(tipoEnum);
    }

    @GetMapping("/immobile/{idImmobile}")
    public List<Contratto> getByImmobile(@PathVariable Integer idImmobile) {
        return contrattoService.getContrattiByImmobileId(idImmobile);
    }

    @GetMapping("/scaduti/{data}")
    public List<Contratto> getScaduti(@PathVariable String data) {
        LocalDate d = LocalDate.parse(data);
        return contrattoService.getContrattiScaduti(d);
    }

    @GetMapping("/in-scadenza/{data}")
    public List<Contratto> getInScadenza(@PathVariable String data) {
        LocalDate d = LocalDate.parse(data);
        return contrattoService.getContrattiInScadenza(d);
    }

    // --- CREATE ---
    @PostMapping
    public Contratto creaContratto(@RequestBody Contratto contratto) {
        if (contratto.getTipoContratto() != null) {
            contratto.setTipoContratto(
                Contratto.TipoContratto.fromString(contratto.getTipoContratto().name())
            );
        }
        return contrattoService.salvaContratto(contratto);
    }

    // --- UPDATE ---
    @PutMapping("/{id}")
    public Contratto aggiornaContratto(@PathVariable Integer id, @RequestBody Contratto contratto) {
        contratto.setIdContratto(id);
        return contrattoService.aggiornaContratto(contratto);
    }

    @DeleteMapping("/{id}")
    public void eliminaContratto(@PathVariable Integer id) {
        contrattoService.eliminaContratto(id);
    }

    /**
     * Allega un contratto PDF e invia email al proprietario
     */
    @PatchMapping("/{id}/allega-pdf")
    public ResponseEntity<?> allegaContrattoPDF(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        try {
            String pathPDF = body.get("pathPDF");
            
            if (pathPDF == null || pathPDF.isBlank()) {
                return ResponseEntity.badRequest().body("Path PDF non fornito");
            }
            
            // Aggiorna il contratto con il path del PDF
            Contratto contratto = contrattoService.aggiornaPathContrattoPDF(id, pathPDF);
            
            if (contratto == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Pubblica evento per inviare email al proprietario
            try {
                com.residea.residea.entities.Immobile immobile = contratto.getIdImmobile();
                com.residea.residea.entities.Utente proprietario = immobile.getProprietario();
                com.residea.residea.entities.Utente agente = contratto.getAgente();
                
                if (proprietario != null && proprietario.getEmail() != null) {
                    ContrattoAllegatoEvent event = new ContrattoAllegatoEvent(
                        contratto.getIdContratto(),
                        immobile.getIdImmobile(),
                        proprietario.getIdUtente(),
                        proprietario.getEmail(),
                        proprietario.getNome(),
                        proprietario.getCognome(),
                        agente != null ? agente.getNome() : "",
                        agente != null ? agente.getCognome() : "",
                        contratto.getTipoContratto() != null ? contratto.getTipoContratto().name() : "",
                        contratto.getDataContratto() != null ? contratto.getDataContratto().toString() : "",
                        contratto.getPathContrattoPDF(),
                        immobile.getIndirizzo(),
                        immobile.getCitta()
                    );
                    
                    eventPublisher.publishEvent(event);
                }
            } catch (Exception e) {
                // Log ma non bloccare il processo se l'email fallisce
                System.err.println("Errore nell'invio email contratto allegato: " + e.getMessage());
            }
            
            return ResponseEntity.ok(contratto);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Errore durante l'allegato del contratto: " + e.getMessage());
        }
    }
}
    