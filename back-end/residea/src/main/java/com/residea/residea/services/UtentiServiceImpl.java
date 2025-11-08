package com.residea.residea.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.residea.residea.entities.Utente;
import com.residea.residea.entities.Utente.Ruolo;
import com.residea.residea.repos.UtentiRepo;

@Service
public class UtentiServiceImpl implements UtentiService {

    @Autowired
    private UtentiRepo utentiRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- READ ---
    @Override
    public List<Utente> getAllUtente() {
        return utentiRepo.findAll();
    }

    @Override
    public List<Utente> getUtenteByTelefono(String telefono) {
        return utentiRepo.findByTelefono(telefono);
    }

    @Override
    public Utente getUtenteById(Integer idUtente) {
        return utentiRepo.findById(idUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }

    // --- CREATE ---
    @Override
    public Utente salvaUtente(Utente utente) {
        // Hash della password prima di salvare
        utente.setPasswordHash(passwordEncoder.encode(utente.getPasswordHash()));
        return utentiRepo.save(utente);
    }

    // --- UPDATE ---
    @Override
    public Utente aggiornaUtente(Utente utenteAggiornato) {
        // Se vuoi aggiornare anche la password, ricordati di hasharla
        if (utenteAggiornato.getPasswordHash() != null) {
            utenteAggiornato.setPasswordHash(
                passwordEncoder.encode(utenteAggiornato.getPasswordHash())
            );
        }
        return utentiRepo.save(utenteAggiornato);
    }

    @Override
    public Utente cambiaRuoloUtente(Integer idUtente, Ruolo ruoloNuovo) {
        Utente utente = utentiRepo.findById(idUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        utente.setRuolo(ruoloNuovo);
        return utentiRepo.save(utente);
    }

    // --- PASSWORD VERIFICA ---
    @Override
    public boolean verificaPassword(Integer idUtente, String passwordInChiaro) {
        Utente utente = utentiRepo.findById(idUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        return passwordEncoder.matches(passwordInChiaro, utente.getPasswordHash());
    }

    // --- DELETE ---
    // @Override
    // public void eliminaUtente(Integer idUtente) {
    //     utentiRepo.deleteById(idUtente);
    // }
}
