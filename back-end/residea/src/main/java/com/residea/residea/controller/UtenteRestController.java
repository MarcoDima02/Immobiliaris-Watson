package com.residea.residea.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // POST /api/utenti/login → login by email + password
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody java.util.Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        if (email == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email and password are required");
        }
        try {
            Utente u = utentiService.getUtenteByEmail(email);
            boolean ok = utentiService.verificaPassword(u.getIdUtente(), password);
            if (!ok) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
            // hide passwordHash before returning
            u.setPasswordHash(null);
            return ResponseEntity.ok(u);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}