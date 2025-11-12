package com.residea.residea.controller;

import com.residea.residea.entities.Utente;
import com.residea.residea.entities.Utente.Ruolo;
import com.residea.residea.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utenti")
public class UtenteRestController {

    @Autowired
    private UtentiService utentiService;

    // GET /api/utenti → restituisce lista di utenti
    @GetMapping
    public List<Utente> getAllUtenti() {
        return utentiService.getAllUtente();
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
