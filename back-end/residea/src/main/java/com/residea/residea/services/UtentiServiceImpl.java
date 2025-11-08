package com.residea.residea.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.residea.residea.entities.Amministrazione.Ruolo;
import com.residea.residea.entities.Utente;

@Service
public class UtentiServiceImpl implements UtentiService{

    @Override
    public List<Utente> getUtente() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUtente'");
    }

    @Override
    public List<Utente> getUtenteByIdUtente() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUtenteByIdUtente'");
    }

    @Override
    public List<Utente> getUtenteByTelefono() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUtenteByTelefono'");
    }

    @Override
    public Utente salvaUtente(Utente utente) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'salvaUtente'");
    }

    @Override
    public Utente aggiornaUtente(Utente utenteAggiornato) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'aggiornaUtente'");
    }

    @Override
    public Utente cambiaRuoloUtente(String idUtente, Ruolo ruoloNuovo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cambiaRuoloUtente'");
    }

   

}
