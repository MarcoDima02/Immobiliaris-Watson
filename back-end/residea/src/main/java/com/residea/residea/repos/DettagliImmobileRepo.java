package com.residea.residea.repos;

import com.residea.residea.entities.DettagliImmobile;
import com.residea.residea.entities.DettagliImmobile.ClasseEnergetica;
import com.residea.residea.entities.DettagliImmobile.CondizioneImmobile;
import com.residea.residea.entities.DettagliImmobile.TipoRiscaldamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DettagliImmobileRepo extends JpaRepository<DettagliImmobile, Integer> {

    List<DettagliImmobile> findByPrezzoLessThanEqual(BigDecimal prezzo);
    List<DettagliImmobile> findByClasseEnergetica(ClasseEnergetica classeEnergetica);
    List<DettagliImmobile> findByCondizioneImmobile(CondizioneImmobile condizioneImmobile);
    List<DettagliImmobile> findByTipoRiscaldamento(TipoRiscaldamento tipoRiscaldamento);
    List<DettagliImmobile> findByGarage(Boolean garage);
    List<DettagliImmobile> findByGiardino(Boolean giardino);
}
