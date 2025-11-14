package com.residea.residea.repos;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.residea.residea.entities.ValutazioneImmobile;
import com.residea.residea.entities.Immobile;

@Repository
public interface ValutazioneImmobileRepo extends JpaRepository<ValutazioneImmobile, Integer> {

    // Trova una valutazione tramite l'immobile associato
    Optional<ValutazioneImmobile> findByImmobile(Immobile immobile);

    // Trova una valutazione tramite l'ID dell'immobile
    Optional<ValutazioneImmobile> findByImmobile_IdImmobile(Integer idImmobile);

    // Filtra per confidence superiore a una soglia
    List<ValutazioneImmobile> findByConfidenceGreaterThan(BigDecimal minConfidence);

    // Filtra per valore medio compreso in un intervallo
    List<ValutazioneImmobile> findByValoreMedioBetween(Integer valoreMin, Integer valoreMax);

    // Filtra per fattore di aggiustamento superiore a una certa soglia
    List<ValutazioneImmobile> findByFattoreAggiustamentoGreaterThan(BigDecimal soglia);

    // Restituisce tutte le valutazioni associate a un immobile
    List<ValutazioneImmobile> findAllByImmobile_IdImmobile(Integer idImmobile);

    public void deleteAll(Optional<ValutazioneImmobile> valutazioni);

}
