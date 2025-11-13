package com.residea.residea.services;

import java.math.BigDecimal;
import java.util.List;

import com.residea.residea.entities.ValutazioneImmobile;

public interface ValutazioneImmobileService {

    // --- READ ---
    List<ValutazioneImmobile> getAllValutazioni();
    ValutazioneImmobile getValutazioneById(Integer idValutazione);
    ValutazioneImmobile getValutazioneByIdImmobile(Integer idImmobile);
    List<ValutazioneImmobile> getValutazioniByConfidenceMaggioreDi(BigDecimal minConfidence);
    List<ValutazioneImmobile> getValutazioniByValoreMedioCompreso(Integer valoreMin, Integer valoreMax);
    List<ValutazioneImmobile> getValutazioniByFattoreAggiustamentoMaggioreDi(BigDecimal soglia);
    
    // --- CREATE ---
    ValutazioneImmobile salvaValutazione(ValutazioneImmobile valutazione);

    // --- UPDATE ---
    ValutazioneImmobile aggiornaValutazione(ValutazioneImmobile valutazioneAggiornata);
    ValutazioneImmobile aggiornaValoreMedio(Integer idValutazione, Integer nuovoValoreMedio);
    ValutazioneImmobile aggiornaConfidence(Integer idValutazione, BigDecimal nuovaConfidence);

    // --- DELETE ---
    void eliminaValutazione(Integer idValutazione);
    void eliminaValutazioniByImmobile(Integer idImmobile);
}
