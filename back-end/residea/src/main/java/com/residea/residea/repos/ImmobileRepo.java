package com.residea.residea.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Utente;

@Repository
public interface ImmobileRepo extends JpaRepository<Immobile, Integer> {
    List<Immobile> findByProprietario(Utente proprietario);
    List<Immobile> findByTipologia(Immobile.Tipologia tipologia);
}
