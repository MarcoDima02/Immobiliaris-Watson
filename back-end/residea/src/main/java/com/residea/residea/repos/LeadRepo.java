package com.residea.residea.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.residea.residea.entities.Lead;
import com.residea.residea.entities.Utente;

@Repository
public interface LeadRepo extends JpaRepository<Lead, Integer> {

    List<Lead> findByUtente(Utente utente);

    List<Lead> findByEmail(String email);

    List<Lead> findByCitta(String citta);

    List<Lead> findByConvertitoInRichiesta(boolean convertitoInRichiesta);

    List<Lead> findByNomeContainingIgnoreCase(String nome);

    List<Lead> findAllByOrderByCreatedAtDesc();
}
