package com.residea.residea.services;

import java.util.List;

import com.residea.residea.dto.ImmobileListDTO;
import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Utente;

public interface ImmobileService {

    // --- READ ---
    List<Immobile> getAllImmobili();
    Immobile getImmobileById(Integer idImmobile);
    List<Immobile> getImmobiliByProprietario(Utente proprietario);
    List<Immobile> getImmobiliByTipologia(Immobile.Tipologia tipologia);
    
    // --- READ WITH DETAILS (for dashboard) ---
    List<ImmobileListDTO> getAllImmobiliWithDetails();
    ImmobileListDTO getImmobileDetailsById(Integer idImmobile);

    // --- CREATE ---
    Immobile salvaImmobile(Immobile immobile);

    // --- UPDATE ---
    Immobile aggiornaImmobile(Immobile immobileAggiornato);

    // --- DELETE ---
    //void eliminaImmobile(Integer idImmobile);
}