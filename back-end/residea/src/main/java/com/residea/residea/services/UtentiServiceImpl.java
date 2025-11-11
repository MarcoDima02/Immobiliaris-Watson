package com.residea.residea.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.residea.residea.entities.Utente;
import com.residea.residea.entities.Utente.Ruolo;
import com.residea.residea.repos.UtenteRepo;

@Service
public class UtentiServiceImpl implements UtentiService {

    @Autowired
    private UtenteRepo utenteRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- READ ---
    @Override
    public List<Utente> getAllUtente() {
    return utenteRepo.findAll();
    }

    @Override
    public List<Utente> getUtenteByTelefono(String telefono) {
    return utenteRepo.findByTelefono(telefono);
    }

    @Override
    public Utente getUtenteById(Integer idUtente) {
    return utenteRepo.findById(idUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }

    // --- CREATE ---
    @Override
    public Utente salvaUtente(Utente utente) {
        // Hash della password prima di salvare
        utente.setPasswordHash(passwordEncoder.encode(utente.getPasswordHash()));
    return utenteRepo.save(utente);
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
    return utenteRepo.save(utenteAggiornato);
    }

    @Override
    public Utente cambiaRuoloUtente(Integer idUtente, Ruolo ruoloNuovo) {
    Utente utente = utenteRepo.findById(idUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        utente.setRuolo(ruoloNuovo);
    return utenteRepo.save(utente);
    }

    // --- PASSWORD VERIFICA ---
    @Override
    public boolean verificaPassword(Integer idUtente, String passwordInChiaro) {
    Utente utente = utenteRepo.findById(idUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    return passwordEncoder.matches(passwordInChiaro, utente.getPasswordHash());
    }

    // --- DELETE ---
    // @Override
    // public void eliminaUtente(Integer idUtente) {
    //     utentiRepo.deleteById(idUtente);
    // }
}
