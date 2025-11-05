package com.residea.residea.services;

import java.util.List;

import com.residea.residea.entities.Utente;
import com.residea.residea.entities.Amministrazione.Ruolo;

public interface UtentiService {

    //Read
    List<Utente> getUtente();

    List<Utente> getUtenteByIdUtente();

    List<Utente> getUtenteByTelefono(); //visto che li voleva chiamare

    //Create
    Utente salvaUtente(Utente utente);

    //Update
    Utente aggiornaUtente(Utente utenteAggiornato);

    Utente cambiaRuoloUtente(String idUtente, Ruolo ruoloNuovo);

    //Delete 
     boolean eliminaUtente(Integer idUtente);
}
