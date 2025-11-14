package com.residea.residea.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Utente;
import com.residea.residea.repos.UtenteRepo;
import com.residea.residea.services.ImmobileService;

@RestController
@RequestMapping("/api/immobili")
public class ImmobileController {

    @Autowired
    private ImmobileService immobiliService;

    @Autowired
    private UtenteRepo utenteRepo;

    // GET /api/immobili → restituisce lista di immobili
    @GetMapping
    public List<Immobile> getAllImmobili() {
        return immobiliService.getAllImmobili();
    }

    // POST /api/immobili → crea nuovo immobile
    @PostMapping
    public Immobile salvaImmobile(@RequestBody Immobile immobile) {
        // se viene passato proprietario con solo id, risolvi l'entità reale
        if (immobile.getProprietario() != null && immobile.getProprietario().getIdUtente() != null) {
            Integer pid = immobile.getProprietario().getIdUtente();
            Utente p = utenteRepo.findById(pid).orElse(null);
            immobile.setProprietario(p);
        } else {
            immobile.setProprietario(null);
        }
        return immobiliService.salvaImmobile(immobile);
    }

    // PUT /api/immobili → aggiorna immobile
    @PutMapping
    public Immobile aggiornaImmobile(@RequestBody Immobile immobile) {
        if (immobile.getProprietario() != null && immobile.getProprietario().getIdUtente() != null) {
            Integer pid = immobile.getProprietario().getIdUtente();
            Utente p = utenteRepo.findById(pid).orElse(null);
            immobile.setProprietario(p);
        } else {
            immobile.setProprietario(null);
        }
        return immobiliService.aggiornaImmobile(immobile);
    }

    // GET /api/immobili/{id}
    @GetMapping(path = "/{id}")
    public Immobile getImmobileById(@org.springframework.web.bind.annotation.PathVariable("id") Integer id) {
        return immobiliService.getImmobileById(id);
    }

    // GET /api/immobili/proprietario/{idUtente}
    @GetMapping(path = "/proprietario/{idUtente}")
    public java.util.List<Immobile> getImmobiliByProprietario(@org.springframework.web.bind.annotation.PathVariable("idUtente") String idUtenteStr) {
        if (idUtenteStr == null || idUtenteStr.isBlank()) return java.util.Collections.emptyList();
        Integer idUtente = null;
        try {
            idUtente = Integer.valueOf(idUtenteStr.trim());
        } catch (NumberFormatException ex) {
            return java.util.Collections.emptyList();
        }
        Utente p = utenteRepo.findById(idUtente).orElse(null);
        if (p == null) return java.util.Collections.emptyList();
        return immobiliService.getImmobiliByProprietario(p);
    }

    // GET /api/immobili/tipologia/{tipologia}
    @GetMapping(path = "/tipologia/{tipologia}")
    public java.util.List<Immobile> getImmobiliByTipologia(@org.springframework.web.bind.annotation.PathVariable("tipologia") String tipologia) {
        if (tipologia == null || tipologia.isBlank()) return java.util.Collections.emptyList();
        try {
            Immobile.Tipologia t = Immobile.Tipologia.valueOf(tipologia.trim().toUpperCase());
            return immobiliService.getImmobiliByTipologia(t);
        } catch (IllegalArgumentException ex) {
            return java.util.Collections.emptyList();
        }
    }
}
