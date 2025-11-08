package com.residea.residea.services;

import java.util.List;
import com.residea.residea.entities.Immagine;

public interface ImmagineService {

    // --- READ ---
    List<Immagine> getAllImmagini();
    Immagine getImmagineById(Integer idImmagine);
    List<Immagine> getImmaginiByImmobileId(Integer idImmobile);

    // --- CREATE ---
    Immagine salvaImmagine(Immagine immagine);

    // --- UPDATE ---
    Immagine aggiornaImmagine(Immagine immagineAggiornata);

    // --- DELETE ---
    void eliminaImmagine(Integer idImmagine);
}