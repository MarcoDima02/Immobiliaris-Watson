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
        // Se il ruolo richiede credenziali (AGENTE o AMMINISTRATORE), la password in chiaro
        // deve essere fornita e verrà hashata. Altrimenti (es. PROPRIETARIO) può rimanere null.
        if (utente.getRuolo() == Ruolo.AGENTE || utente.getRuolo() == Ruolo.AMMINISTRATORE) {
            if (utente.getPasswordHash() == null || utente.getPasswordHash().isEmpty()) {
                throw new IllegalArgumentException("Password richiesta per ruolo AGENTE o AMMINISTRATORE");
            }
            utente.setPasswordHash(passwordEncoder.encode(utente.getPasswordHash()));
        } else {
            // Per ruoli senza accesso, permettiamo password nulla
            if (utente.getPasswordHash() == null || utente.getPasswordHash().isEmpty()) {
                utente.setPasswordHash(null);
            } else {
                // Se viene fornita comunque una password, la hashamo
                utente.setPasswordHash(passwordEncoder.encode(utente.getPasswordHash()));
            }
        }
        return utenteRepo.save(utente);
    }

    // --- UPDATE ---
    @Override
    public Utente aggiornaUtente(Utente utenteAggiornato) {
        // Se viene fornita una nuova password, la hashamo
        if (utenteAggiornato.getPasswordHash() != null && !utenteAggiornato.getPasswordHash().isEmpty()) {
            utenteAggiornato.setPasswordHash(
                passwordEncoder.encode(utenteAggiornato.getPasswordHash())
            );
        }
        // Se il ruolo aggiornato richiede credenziali, assicurarsi che una password sia presente
        if (utenteAggiornato.getRuolo() == Ruolo.AGENTE || utenteAggiornato.getRuolo() == Ruolo.AMMINISTRATORE) {
            if (utenteAggiornato.getPasswordHash() == null || utenteAggiornato.getPasswordHash().isEmpty()) {
                throw new IllegalArgumentException("Password richiesta per ruolo AGENTE o AMMINISTRATORE");
            }
        }
        return utenteRepo.save(utenteAggiornato);
    }

    @Override
    public Utente cambiaRuoloUtente(Integer idUtente, Ruolo ruoloNuovo) {
    Utente utente = utenteRepo.findById(idUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        // Se si sta promuovendo a ruolo con accesso, verificare che ci sia una password
        if ((ruoloNuovo == Ruolo.AGENTE || ruoloNuovo == Ruolo.AMMINISTRATORE)
            && (utente.getPasswordHash() == null || utente.getPasswordHash().isEmpty())) {
            throw new IllegalArgumentException("Impossibile cambiare ruolo: l'utente non ha una password impostata");
        }
        utente.setRuolo(ruoloNuovo);
        return utenteRepo.save(utente);
    }

    // --- PASSWORD VERIFICA ---
    @Override
    public boolean verificaPassword(Integer idUtente, String passwordInChiaro) {
    Utente utente = utenteRepo.findById(idUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    if (utente.getPasswordHash() == null || utente.getPasswordHash().isEmpty()) {
        return false;
    }
    return passwordEncoder.matches(passwordInChiaro, utente.getPasswordHash());
    }

    // --- DELETE ---
    // @Override
    // public void eliminaUtente(Integer idUtente) {
    //     utentiRepo.deleteById(idUtente);
    // }
}
