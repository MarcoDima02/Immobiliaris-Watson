package com.residea.residea.services;

import java.util.List;

import com.residea.residea.entities.Citta;

public interface CittaService {

    // --- READ ---
    List<Citta> getAllCitta();
    Citta getCittaById(Integer idCitta);
    List<Citta> getCittaByNome(String nome);
    List<Citta> getCittaByProvincia(String provincia);
    List<Citta> getCittaByRegione(String regione);
    Citta getCittaByCodiceIstat(String codiceIstat);

    // --- CREATE ---
    Citta salvaCitta(Citta citta);

    // --- UPDATE ---
    Citta aggiornaCitta(Citta cittaAggiornata);

    // --- DELETE ---
    void eliminaCitta(Integer idCitta);
}
