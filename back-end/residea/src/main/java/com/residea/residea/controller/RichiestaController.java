package com.residea.residea.controller;

import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Richiesta;
import com.residea.residea.entities.Richiesta.Stato;
import com.residea.residea.entities.Utente;
import com.residea.residea.services.RichiestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/richieste")
public class RichiestaController {

    @Autowired
    private RichiestaService richiestaService;

    // ---------- CREATE ----------
    @PostMapping
    public Richiesta createRichiesta(@RequestBody Richiesta richiesta) {
        return richiestaService.createRichiesta(richiesta);
    }

    // ---------- READ ----------
    @GetMapping
    public List<Richiesta> getAllRichieste() {
        return richiestaService.getAllRichieste();
    }

    @GetMapping("/{id}")
    public Richiesta getRichiestaById(@PathVariable Integer id) {
        return richiestaService.getRichiestaById(id).orElse(null);
    }

    // ---------- UPDATE ----------
    @PutMapping("/{id}")
    public Richiesta updateRichiesta(
            @PathVariable Integer id,
            @RequestBody Richiesta richiestaAggiornata
    ) {
        return richiestaService.updateRichiesta(id, richiestaAggiornata);
    }

    // ---------- DELETE ----------
    @DeleteMapping("/{id}")
    public void deleteRichiesta(@PathVariable Integer id) {
        richiestaService.deleteRichiesta(id);
    }

    // ---------- QUERY: BY UTENTE ----------
    @GetMapping("/utente/{idUtente}")
    public List<Richiesta> getRichiesteByUtente(@PathVariable Integer idUtente) {
        Utente u = new Utente();
        u.setIdUtente(idUtente);
        return richiestaService.findByUtente(u);
    }

    // ---------- QUERY: BY IMMOBILE ----------
    @GetMapping("/immobile/{idImmobile}")
    public List<Richiesta> getRichiesteByImmobile(@PathVariable Integer idImmobile) {
        Immobile i = new Immobile();
        i.setIdImmobile(idImmobile);
        return richiestaService.findByImmobile(i);
    }

    // ---------- QUERY: BY STATO ----------
    @GetMapping("/stato/{stato}")
    public List<Richiesta> getRichiesteByStato(@PathVariable String stato) {
        try {
            Stato statoEnum = stringToStato(stato);
            return richiestaService.findByStato(statoEnum);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Stato non valido: " + stato);
        }
    }

    // ---------- HELPER: converte String -> Enum Stato ----------
    private Stato stringToStato(String statoStr) {
        return switch (statoStr.toLowerCase()) {
            case "in attesa", "attesa", "in_attesa" -> Stato.IN_ATTESA;
            case "in elaborazione", "elaborazione", "in_elaborazione" -> Stato.IN_ELABORAZIONE;
            case "completata" -> Stato.COMPLETATA;
            case "annullata" -> Stato.ANNULLATA;
            default -> throw new IllegalArgumentException("Stato non valido: " + statoStr);
        };
    }
}
