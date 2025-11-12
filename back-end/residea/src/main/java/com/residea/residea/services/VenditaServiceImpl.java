package com.residea.residea.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.residea.residea.entities.Vendita;
import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Utente;
import com.residea.residea.entities.Contratto;
import com.residea.residea.repos.VenditaRepo;

@Service
@Transactional
public class VenditaServiceImpl implements VenditaService {

    private final VenditaRepo venditaRepo;

    public VenditaServiceImpl(VenditaRepo venditaRepo) {
        this.venditaRepo = venditaRepo;
    }

    @Override
    public Vendita createVendita(Vendita vendita) {
        return venditaRepo.save(vendita);
    }

    @Override
    public List<Vendita> getAllVendite() {
        return venditaRepo.findAll();
    }

    @Override
    public Optional<Vendita> getVenditaById(Integer idVendita) {
        return venditaRepo.findById(idVendita);
    }

    @Override
    public Vendita updateVendita(Integer idVendita, Vendita updatedVendita) {
        return venditaRepo.findById(idVendita)
                .map(existing -> {
                    existing.setContratto(updatedVendita.getContratto());
                    existing.setImmobile(updatedVendita.getImmobile());
                    existing.setUtente(updatedVendita.getUtente());
                    existing.setCommissionePercentuale(updatedVendita.getCommissionePercentuale());
                    return venditaRepo.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Vendita non trovata con id: " + idVendita));
    }

    @Override
    public void deleteVendita(Integer idVendita) {
        if (!venditaRepo.existsById(idVendita)) {
            throw new RuntimeException("Vendita non trovata con id: " + idVendita);
        }
        venditaRepo.deleteById(idVendita);
    }

    @Override
    public List<Vendita> findByUtente(Utente utente) {
        return venditaRepo.findByUtente(utente);
    }

    @Override
    public List<Vendita> findByImmobile(Immobile immobile) {
        return venditaRepo.findByImmobile(immobile);
    }

    @Override
    public List<Vendita> findByContratto(Contratto contratto) {
        return venditaRepo.findByContratto(contratto);
    }
}
