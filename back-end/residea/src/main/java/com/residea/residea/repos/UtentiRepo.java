package com.residea.residea.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.residea.residea.entities.Utente;

@Repository
public interface UtentiRepo extends JpaRepository<Utente, Integer> {
    List<Utente> findByTelefono(String telefono);
}