package com.residea.residea.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.residea.residea.entities.Citta;
import com.residea.residea.entities.PrezzoPerCap;
import com.residea.residea.services.PrezzoPerCapService;

@RestController
@RequestMapping("api/prezzo")
public class PrezzoPerCapController {

    @Autowired
    private PrezzoPerCapService prezzoService;

    // --- CREATE ---
    @PostMapping
    public ResponseEntity<PrezzoPerCap> createPrezzo(@RequestBody PrezzoPerCap prezzo) {
        PrezzoPerCap saved = prezzoService.createPrezzo(prezzo);
        return ResponseEntity.ok(saved);
    }

    // --- GET ALL ---
    @GetMapping
    public ResponseEntity<List<PrezzoPerCap>> getAllPrezzi() {
        return ResponseEntity.ok(prezzoService.getAllPrezzi());
    }

    // --- GET BY CAP ---
    @GetMapping("/{cap}")
    public ResponseEntity<PrezzoPerCap> getPrezzoByCap(@PathVariable String cap) {
        return prezzoService.getPrezzoByCap(cap)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- UPDATE ---
    @PutMapping("/{cap}")
    public ResponseEntity<PrezzoPerCap> updatePrezzo(
            @PathVariable String cap,
            @RequestBody PrezzoPerCap updatedPrezzo) {

        PrezzoPerCap updated = prezzoService.updatePrezzo(cap, updatedPrezzo);
        return ResponseEntity.ok(updated);
    }

    // --- DELETE ---
    @DeleteMapping("/{cap}")
    public ResponseEntity<Void> deletePrezzo(@PathVariable String cap) {
        prezzoService.deletePrezzo(cap);
        return ResponseEntity.noContent().build();
    }

    // --- FIND BY CITTA ---
    @GetMapping("/citta/{idCitta}")
    public ResponseEntity<List<PrezzoPerCap>> findByCitta(@PathVariable Integer idCitta) {
        Citta citta = new Citta();
        citta.setIdCitta(idCitta); // basta solo l'id per la query
        return ResponseEntity.ok(prezzoService.findByCitta(citta));
    }

    // --- FIND BY VALID FROM AFTER (>=) ---
    @GetMapping("/validFromAfter/{date}")
    public ResponseEntity<List<PrezzoPerCap>> findByValidFromAfter(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(prezzoService.findByValidFromAfter(date));
    }

    // --- FIND BY VALID TO BEFORE (<=) ---
    @GetMapping("/validToBefore/{date}")
    public ResponseEntity<List<PrezzoPerCap>> findByValidToBefore(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(prezzoService.findByValidToBefore(date));
    }
}
