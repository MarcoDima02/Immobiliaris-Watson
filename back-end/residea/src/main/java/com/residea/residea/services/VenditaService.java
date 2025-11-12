package com.residea.residea.services;

import java.util.List;
import java.util.Optional;

import com.residea.residea.entities.Vendita;
import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Utente;
import com.residea.residea.entities.Contratto;

public interface VenditaService {

    Vendita createVendita(Vendita vendita);

    List<Vendita> getAllVendite();

    Optional<Vendita> getVenditaById(Integer idVendita);

    Vendita updateVendita(Integer idVendita, Vendita updatedVendita);

    void deleteVendita(Integer idVendita);

    List<Vendita> findByUtente(Utente utente);

    List<Vendita> findByImmobile(Immobile immobile);

    List<Vendita> findByContratto(Contratto contratto);
}
