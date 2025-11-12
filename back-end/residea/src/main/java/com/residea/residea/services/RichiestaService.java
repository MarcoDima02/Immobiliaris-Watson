package com.residea.residea.services;

import java.util.List;
import java.util.Optional;

import com.residea.residea.entities.Richiesta;
import com.residea.residea.entities.Utente;
import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Richiesta.Stato;

public interface RichiestaService {

    Richiesta createRichiesta(Richiesta richiesta);

    List<Richiesta> getAllRichieste();

    Optional<Richiesta> getRichiestaById(Integer idRichiesta);

    Richiesta updateRichiesta(Integer idRichiesta, Richiesta updatedRichiesta);

    void deleteRichiesta(Integer idRichiesta);

    List<Richiesta> findByUtente(Utente utente);

    List<Richiesta> findByImmobile(Immobile immobile);

    List<Richiesta> findByStato(Stato stato);

}
