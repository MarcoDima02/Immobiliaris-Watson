package com.residea.residea.services;

import java.util.List;

import com.residea.residea.entities.Utente;
import com.residea.residea.entities.Utente.Ruolo;

public interface UtentiService {

    //Read
    List<Utente> getAllUtenti();
    List<Utente> getUtenteByTelefono(String telefono);
    Utente getUtenteById(Integer idUtente);
    Utente getUtenteByEmail(String email);

    boolean verificaPassword(Integer idutente, String passwordCriptata); // Verifica password

    //Create
    Utente salvaUtente(Utente utente);

    //Update
    Utente aggiornaUtente(Utente utenteAggiornato);
    Utente cambiaRuoloUtente(Integer idUtente, Ruolo ruoloNuovo);


}
