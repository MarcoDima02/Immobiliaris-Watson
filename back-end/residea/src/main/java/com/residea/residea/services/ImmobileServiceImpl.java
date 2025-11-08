package com.residea.residea.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Utente;
import com.residea.residea.repos.ImmobileRepo;

@Service
public class ImmobileServiceImpl implements ImmobileService {

    @Autowired
    private ImmobileRepo immobileRepo;

    // --- READ ---
    @Override
    public List<Immobile> getAllImmobili() {
        return immobileRepo.findAll();
    }

    @Override
    public Immobile getImmobileById(Integer idImmobile) {
        return immobileRepo.findById(idImmobile)
                .orElseThrow(() -> new RuntimeException("Immobile non trovato"));
    }

    @Override
    public List<Immobile> getImmobiliByProprietario(Utente proprietario) {
        return immobileRepo.findByProprietario(proprietario);
    }

    @Override
    public List<Immobile> getImmobiliByTipologia(Immobile.Tipologia tipologia) {
        return immobileRepo.findByTipologia(tipologia);
    }

    // --- CREATE ---
    @Override
    public Immobile salvaImmobile(Immobile immobile) {
        return immobileRepo.save(immobile);
    }

    // --- UPDATE ---
    @Override
    public Immobile aggiornaImmobile(Immobile immobileAggiornato) {
        // L'update funziona direttamente tramite save(), purché idImmobile sia valorizzato
        return immobileRepo.save(immobileAggiornato);
    }

    // --- DELETE ---
    // @Override
    // public void eliminaImmobile(Integer idImmobile) {
    //     immobileRepo.deleteById(idImmobile);
    // }
}
