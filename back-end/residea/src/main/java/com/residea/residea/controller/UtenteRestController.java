package com.residea.residea.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.residea.residea.entities.Utente;
import com.residea.residea.services.UtentiService;

@RestController
@RequestMapping("/api/utenti")
public class UtenteRestController {

    @Autowired
    private UtentiService utentiService;

    // GET /api/utenti → restituisce lista di utenti
    @GetMapping
    public List<Utente> getAllUtenti() {
        return utentiService.getAllUtenti();
    }

    // POST /api/utenti → crea nuovo utente
@PostMapping
public Utente creaUtente(@RequestBody Utente utente) {
    if (utente.getRuolo() != null) {
        utente.setRuolo(Utente.Ruolo.valueOf(utente.getRuolo().name().toUpperCase()));
    }
    return utentiService.salvaUtente(utente);
}

    // GET /api/utenti/telefono/{telefono} → cerca utente per telefono
    @GetMapping("/telefono/{telefono}")
    public List<Utente> getUtenteByTelefono(@PathVariable String telefono) {
        return utentiService.getUtenteByTelefono(telefono);
    }
}