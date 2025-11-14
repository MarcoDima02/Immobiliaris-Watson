package com.residea.residea.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.ValutazioneImmobile;
import com.residea.residea.repos.ValutazioneImmobileRepo;

@Service
@Transactional
public class ValutazioneImmobileServiceImpl implements ValutazioneImmobileService {

    private final ValutazioneImmobileRepo valutazioneRepo;

    public ValutazioneImmobileServiceImpl(ValutazioneImmobileRepo valutazioneRepo) {
        this.valutazioneRepo = valutazioneRepo;
    }

    // --- READ ---
    @Override
    public List<ValutazioneImmobile> getAllValutazioni() {
        return valutazioneRepo.findAll();
    }

    @Override
    public ValutazioneImmobile getValutazioneById(Integer idValutazione) {
        return valutazioneRepo.findById(idValutazione)
                .orElseThrow(() -> new RuntimeException("Valutazione non trovata con id: " + idValutazione));
    }

    @Override
    public ValutazioneImmobile getValutazioneByIdImmobile(Integer idImmobile) {
        return valutazioneRepo.findByImmobile_IdImmobile(idImmobile)
                .orElseThrow(() -> new RuntimeException("Nessuna valutazione trovata per immobile con id: " + idImmobile));
    }

    @Override
    public List<ValutazioneImmobile> getValutazioniByConfidenceMaggioreDi(BigDecimal minConfidence) {
        return valutazioneRepo.findByConfidenceGreaterThan(minConfidence);
    }

    @Override
    public List<ValutazioneImmobile> getValutazioniByValoreMedioCompreso(Integer valoreMin, Integer valoreMax) {
        return valutazioneRepo.findByValoreMedioBetween(valoreMin, valoreMax);
    }

    @Override
    public List<ValutazioneImmobile> getValutazioniByFattoreAggiustamentoMaggioreDi(BigDecimal soglia) {
        return valutazioneRepo.findByFattoreAggiustamentoGreaterThan(soglia);
    }

    // --- CREATE ---
    @Override
    public ValutazioneImmobile salvaValutazione(ValutazioneImmobile valutazione) {
        return valutazioneRepo.save(valutazione);
    }

    // --- UPDATE ---
    @Override
    public ValutazioneImmobile aggiornaValutazione(ValutazioneImmobile valutazioneAggiornata) {
        ValutazioneImmobile esistente = getValutazioneById(valutazioneAggiornata.getIdValutazione());

        esistente.setImmobile(valutazioneAggiornata.getImmobile());
        esistente.setValoreBase(valutazioneAggiornata.getValoreBase());
        esistente.setFattoreAggiustamento(valutazioneAggiornata.getFattoreAggiustamento());
        esistente.setValoreMedio(valutazioneAggiornata.getValoreMedio());
        esistente.setValoreMin(valutazioneAggiornata.getValoreMin());
        esistente.setValoreMax(valutazioneAggiornata.getValoreMax());
        esistente.setConfidence(valutazioneAggiornata.getConfidence());

        return valutazioneRepo.save(esistente);
    }

    @Override
    public ValutazioneImmobile aggiornaValoreMedio(Integer idValutazione, Integer nuovoValoreMedio) {
        ValutazioneImmobile valutazione = getValutazioneById(idValutazione);
        valutazione.setValoreMedio(nuovoValoreMedio);
        return valutazioneRepo.save(valutazione);
    }

    @Override
    public ValutazioneImmobile aggiornaConfidence(Integer idValutazione, BigDecimal nuovaConfidence) {
        ValutazioneImmobile valutazione = getValutazioneById(idValutazione);
        valutazione.setConfidence(nuovaConfidence);
        return valutazioneRepo.save(valutazione);
    }

    // --- DELETE ---
    @Override
    public void eliminaValutazione(Integer idValutazione) {
        if (!valutazioneRepo.existsById(idValutazione)) {
            throw new RuntimeException("Valutazione non trovata con id: " + idValutazione);
        }
        valutazioneRepo.deleteById(idValutazione);
    }

    @Override
    public void eliminaValutazioniByImmobile(Integer idImmobile) {
        Optional<ValutazioneImmobile> valutazioni = valutazioneRepo.findByImmobile_IdImmobile(idImmobile);
        if (valutazioni.isEmpty()) {
            throw new RuntimeException("Nessuna valutazione trovata per immobile con id: " + idImmobile);
        }
        valutazioneRepo.deleteAll(valutazioni);
    }
}
