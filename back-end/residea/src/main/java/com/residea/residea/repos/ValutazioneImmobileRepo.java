package com.residea.residea.repos;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.ValutazioneImmobile;

@Repository
public interface ValutazioneImmobileRepo extends JpaRepository<ValutazioneImmobile, Integer> {
    
    Optional<ValutazioneImmobile> findByImmobile(Immobile immobile);
    
    List<ValutazioneImmobile> findByConfidenceGreaterThan(BigDecimal minConfidence);
    
    List<ValutazioneImmobile> findByValoreMedioBetween(Integer valoreMin, Integer valoreMax);
    
    List<ValutazioneImmobile> findByFattoreAggiustamentoGreaterThan(BigDecimal soglia);
    
    @Query("SELECT v FROM ValutazioneImmobile v WHERE v.immobile.idImmobile = :idImmobile")
    Optional<ValutazioneImmobile> findByIdImmobile(Integer idImmobile);
    
    void deleteByImmobile(Immobile immobile);
}
