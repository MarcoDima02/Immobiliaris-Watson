package com.residea.residea.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.residea.residea.entities.Immagine;
import com.residea.residea.services.ImmagineService;

@RestController
@RequestMapping("/api/immagini")
public class ImmagineController {

    private final ImmagineService immagineService;

    public ImmagineController(ImmagineService immagineService) {
        this.immagineService = immagineService;
    }

    // --- GET: tutte le immagini ---
    @GetMapping
    public ResponseEntity<List<Immagine>> getAllImmagini() {
        List<Immagine> immagini = immagineService.getAllImmagini();
        return ResponseEntity.ok(immagini);
    }

    // --- GET: immagine per ID ---
    @GetMapping("/{id}")
    public ResponseEntity<Immagine> getImmagineById(@PathVariable Integer id) {
        Immagine immagine = immagineService.getImmagineById(id);
        if (immagine == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(immagine);
    }

    // --- GET: immagini per ID immobile ---
    @GetMapping("/immobile/{idImmobile}")
    public ResponseEntity<List<Immagine>> getImmaginiByImmobile(@PathVariable Integer idImmobile) {
        List<Immagine> immagini = immagineService.getImmaginiByImmobileId(idImmobile);
        return ResponseEntity.ok(immagini);
    }

    // --- POST: salva nuova immagine ---
    @PostMapping
    public ResponseEntity<Immagine> creaImmagine(@RequestBody Immagine immagine) {
        Immagine nuovaImmagine = immagineService.salvaImmagine(immagine);
        return ResponseEntity.ok(nuovaImmagine);
    }

    // --- PUT: aggiorna immagine esistente ---
    @PutMapping("/{id}")
    public ResponseEntity<Immagine> aggiornaImmagine(@PathVariable Integer id, @RequestBody Immagine immagineAggiornata) {
        immagineAggiornata.setIdImmagine(id);
        Immagine aggiornata = immagineService.aggiornaImmagine(immagineAggiornata);
        if (aggiornata == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(aggiornata);
    }

    // --- DELETE: elimina immagine ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaImmagine(@PathVariable Integer id) {
        immagineService.eliminaImmagine(id);
        return ResponseEntity.noContent().build();
    }
}
