package com.residea.residea.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.residea.residea.entities.Vendita;
import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Utente;
import com.residea.residea.entities.Contratto;

@Repository
public interface VenditaRepo extends JpaRepository<Vendita, Integer> {

    List<Vendita> findByUtente(Utente utente);

    List<Vendita> findByImmobile(Immobile immobile);

    List<Vendita> findByContratto(Contratto contratto);

}
