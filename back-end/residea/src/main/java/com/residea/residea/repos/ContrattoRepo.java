package com.residea.residea.repos;

import com.residea.residea.entities.Contratto;
import com.residea.residea.entities.Contratto.TipoContratto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContrattoRepo extends JpaRepository<Contratto, Integer> {

    List<Contratto> findByTipoContratto(TipoContratto tipoContratto);
    List<Contratto> findByIdImmobile_IdImmobile(Integer idImmobile);
    List<Contratto> findByDataScadenzaContrattoBefore(LocalDate data);
    List<Contratto> findByDataScadenzaContrattoBetween(LocalDate inizio, LocalDate fine);
}
