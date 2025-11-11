package com.residea.residea.repos;

import com.residea.residea.entities.Citta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CittaRepo extends JpaRepository<Citta, Integer> {

    List<Citta> findByNomeContainingIgnoreCase(String nome);
    List<Citta> findByProvinciaIgnoreCase(String provincia);
    List<Citta> findByRegioneIgnoreCase(String regione);
    Optional<Citta> findByCodiceIstat(String codiceIstat);
}
