package com.residea.residea.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residea.residea.entities.Superficie;
import com.residea.residea.repos.SuperficieRepo;
import com.residea.residea.services.SuperficieService;

@Service
public class SuperficieServiceImpl implements SuperficieService {

    @Autowired
    private SuperficieRepo superficieRepo;

    // --- CREATE ---
    @Override
    public Superficie createSuperficie(Superficie superficie) {
        return superficieRepo.save(superficie);
    }

    // --- GET ALL ---
    @Override
    public List<Superficie> getAllSuperfici() {
        return superficieRepo.findAll();
    }

    // --- GET BY ID ---
    @Override
    public Optional<Superficie> getSuperficieById(Integer idImmobile) {
        return superficieRepo.findById(idImmobile);
    }

    // --- UPDATE ---
    @Override
    public Superficie updateSuperficie(Integer idImmobile, Superficie superficieAggiornata) {
        return superficieRepo.findById(idImmobile)
                .map(existing -> {
                    existing.setSuperficieMq(superficieAggiornata.getSuperficieMq());
                    existing.setSuperficieBalconeTerrazzo(superficieAggiornata.getSuperficieBalconeTerrazzo());
                    existing.setSuperficieGiardino(superficieAggiornata.getSuperficieGiardino());
                    existing.setSuperficieGarage(superficieAggiornata.getSuperficieGarage());
                    existing.setSuperficieCantina(superficieAggiornata.getSuperficieCantina());
                    return superficieRepo.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Superficie non trovata con idImmobile: " + idImmobile));
    }

    // --- DELETE ---
    @Override
    public void deleteSuperficie(Integer idImmobile) {
        if (!superficieRepo.existsById(idImmobile)) {
            throw new RuntimeException("Superficie non trovata con idImmobile: " + idImmobile);
        }
        superficieRepo.deleteById(idImmobile);
    }
}
