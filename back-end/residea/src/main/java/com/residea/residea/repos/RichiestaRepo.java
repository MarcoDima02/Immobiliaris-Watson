package com.residea.residea.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.residea.residea.entities.Richiesta;
import com.residea.residea.entities.Utente;
import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Richiesta.Stato;

@Repository
public interface RichiestaRepo extends JpaRepository<Richiesta, Integer> {

    List<Richiesta> findByUtente(Utente utente);

    List<Richiesta> findByImmobile(Immobile immobile);

    List<Richiesta> findByStato(Stato stato);

}
