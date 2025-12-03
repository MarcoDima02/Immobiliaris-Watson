package com.residea.residea.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.residea.residea.entities.Utente;
import com.residea.residea.services.UtentiService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/utenti")
public class UtenteRestController {

    @Autowired
    private UtentiService utentiService;

    // GET /api/utenti → restituisce lista di utenti
    @GetMapping
    public ResponseEntity<List<Utente>> getAllUtenti() {
        List<Utente> utenti = utentiService.getAllUtenti();
        // Nascondi password hash prima di restituire
        utenti.forEach(u -> u.setPasswordHash(null));
        return ResponseEntity.ok(utenti);
    }

    // GET /api/utenti/{id} → restituisce utente per ID
    @GetMapping("/{id}")
    public ResponseEntity<Utente> getUtenteById(@PathVariable Integer id) {
        try {
            Utente utente = utentiService.getUtenteById(id);
            if (utente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            utente.setPasswordHash(null); // Nascondi password
            return ResponseEntity.ok(utente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // POST /api/utenti → crea nuovo utente
    @PostMapping
    public ResponseEntity<Utente> creaUtente(@RequestBody Utente utente) {
        try {
            if (utente.getRuolo() != null) {
                utente.setRuolo(Utente.Ruolo.valueOf(utente.getRuolo().name().toUpperCase()));
            }
            Utente nuovoUtente = utentiService.salvaUtente(utente);
            nuovoUtente.setPasswordHash(null); // Nascondi password
            return ResponseEntity.status(HttpStatus.CREATED).body(nuovoUtente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // PUT /api/utenti/{id} → aggiorna utente esistente
    @PutMapping("/{id}")
    public ResponseEntity<Utente> aggiornaUtente(@PathVariable Integer id, @RequestBody Utente utente) {
        try {
            // Verifica che l'utente esista
            Utente esistente = utentiService.getUtenteById(id);
            if (esistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            
            // Imposta l'ID per sicurezza
            utente.setIdUtente(id);
            
            // Se il ruolo è presente, normalizzalo
            if (utente.getRuolo() != null) {
                utente.setRuolo(Utente.Ruolo.valueOf(utente.getRuolo().name().toUpperCase()));
            }
            
            Utente aggiornato = utentiService.aggiornaUtente(utente);
            aggiornato.setPasswordHash(null); // Nascondi password
            return ResponseEntity.ok(aggiornato);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // PUT /api/utenti/{id}/ruolo → cambia solo il ruolo dell'utente
    @PutMapping("/{id}/ruolo")
    public ResponseEntity<Utente> cambiaRuolo(@PathVariable Integer id, @RequestBody java.util.Map<String, String> body) {
        try {
            String nuovoRuolo = body.get("ruolo");
            if (nuovoRuolo == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            
            Utente.Ruolo ruolo = Utente.Ruolo.valueOf(nuovoRuolo.toUpperCase());
            Utente aggiornato = utentiService.cambiaRuoloUtente(id, ruolo);
            aggiornato.setPasswordHash(null); // Nascondi password
            return ResponseEntity.ok(aggiornato);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // GET /api/utenti/telefono/{telefono} → cerca utente per telefono
    @GetMapping("/telefono/{telefono}")
    public List<Utente> getUtenteByTelefono(@PathVariable String telefono) {
        return utentiService.getUtenteByTelefono(telefono);
    }

    // POST /api/utenti/login → login by email + password
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody java.util.Map<String, String> body, HttpSession session) {
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
            
            // Salva dati utente nella sessione
            session.setAttribute("userId", u.getIdUtente());
            session.setAttribute("userRuolo", u.getRuolo().name());
            session.setAttribute("userEmail", u.getEmail());
            
            // hide passwordHash before returning
            u.setPasswordHash(null);

            // return only the authenticated user; frontend decides navigation
            return ResponseEntity.ok(u);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}