package com.residea.residea.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.residea.residea.entities.Vendita;
import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Utente;
import com.residea.residea.services.VenditaService;

@RestController
@RequestMapping("/api/vendite")
public class VenditaController {

    @Autowired
    private VenditaService venditaService;

    // --- CREATE ---
    @PostMapping
    public ResponseEntity<Vendita> createVendita(@RequestBody Vendita vendita) {
        return ResponseEntity.ok(venditaService.createVendita(vendita));
    }

    // --- GET ALL ---
    @GetMapping
    public ResponseEntity<List<Vendita>> getAllVendite() {
        return ResponseEntity.ok(venditaService.getAllVendite());
    }

    // --- GET BY ID ---
    @GetMapping("/{id}")
    public ResponseEntity<Vendita> getVenditaById(@PathVariable Integer id) {
        return venditaService.getVenditaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- UPDATE ---
    @PutMapping("/{id}")
    public ResponseEntity<Vendita> updateVendita(
            @PathVariable Integer id,
            @RequestBody Vendita updatedVendita) {

        try {
            Vendita updated = venditaService.updateVendita(id, updatedVendita);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- DELETE ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendita(@PathVariable Integer id) {
        try {
            venditaService.deleteVendita(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- FIND BY UTENTE ---
    @GetMapping("/utente/{idUtente}")
    public ResponseEntity<List<Vendita>> findByUtente(@PathVariable Integer idUtente) {
        Utente u = new Utente();
        u.setIdUtente(idUtente);
        return ResponseEntity.ok(venditaService.findByUtente(u));
    }

    // --- FIND BY IMMOBILE ---
    @GetMapping("/immobile/{idImmobile}")
    public ResponseEntity<List<Vendita>> findByImmobile(@PathVariable Integer idImmobile) {
        Immobile i = new Immobile();
        i.setIdImmobile(idImmobile);
        return ResponseEntity.ok(venditaService.findByImmobile(i));
    }

}
