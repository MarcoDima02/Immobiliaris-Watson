package com.residea.residea.services;

import java.util.List;
import java.util.Optional;

import com.residea.residea.entities.Superficie;

public interface SuperficieService {

    // CREATE
    Superficie createSuperficie(Superficie superficie);

    // READ
    List<Superficie> getAllSuperfici();
    Optional<Superficie> getSuperficieById(Integer idImmobile);

    // UPDATE
    Superficie updateSuperficie(Integer idImmobile, Superficie superficieAggiornata);

    // DELETE
    void deleteSuperficie(Integer idImmobile);
}
