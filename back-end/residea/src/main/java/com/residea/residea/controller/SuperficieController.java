package com.residea.residea.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.residea.residea.entities.Superficie;
import com.residea.residea.services.SuperficieService;

@RestController
@RequestMapping("api/superfici")
public class SuperficieController {

    @Autowired
    private SuperficieService superficieService;

    // --- CREATE ---
    @PostMapping
    public ResponseEntity<Superficie> createSuperficie(@RequestBody Superficie superficie) {
        Superficie saved = superficieService.createSuperficie(superficie);
        return ResponseEntity.ok(saved);
    }

    // --- GET ALL ---
    @GetMapping
    public ResponseEntity<List<Superficie>> getAllSuperfici() {
        return ResponseEntity.ok(superficieService.getAllSuperfici());
    }

    // --- GET BY ID IMMOBILE ---
    @GetMapping("api/superficie/{idImmobile}")
    public ResponseEntity<Superficie> getSuperficieById(@PathVariable Integer idImmobile) {
        return superficieService.getSuperficieById(idImmobile)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- UPDATE ---
    @PutMapping("api/superficie/{idImmobile}")
    public ResponseEntity<Superficie> updateSuperficie(
            @PathVariable Integer idImmobile,
            @RequestBody Superficie updatedSuperficie) {

        Superficie updated = superficieService.updateSuperficie(idImmobile, updatedSuperficie);
        return ResponseEntity.ok(updated);
    }

    // --- DELETE ---
    @DeleteMapping("api/superficie/{idImmobile}")
    public ResponseEntity<Void> deleteSuperficie(@PathVariable Integer idImmobile) {
        superficieService.deleteSuperficie(idImmobile);
        return ResponseEntity.noContent().build();
    }
}
