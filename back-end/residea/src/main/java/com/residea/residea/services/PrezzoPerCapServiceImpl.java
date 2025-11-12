package com.residea.residea.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.residea.residea.entities.PrezzoPerCap;
import com.residea.residea.entities.Citta;
import com.residea.residea.repos.PrezzoPerCapRepo;

@Service
@Transactional
public class PrezzoPerCapServiceImpl implements PrezzoPerCapService {

    private final PrezzoPerCapRepo prezzoRepo;

    public PrezzoPerCapServiceImpl(PrezzoPerCapRepo prezzoRepo) {
        this.prezzoRepo = prezzoRepo;
    }

    @Override
    public PrezzoPerCap createPrezzo(PrezzoPerCap prezzo) {
        return prezzoRepo.save(prezzo);
    }

    @Override
    public List<PrezzoPerCap> getAllPrezzi() {
        return prezzoRepo.findAll();
    }

    @Override
    public Optional<PrezzoPerCap> getPrezzoByCap(String cap) {
        return prezzoRepo.findById(cap);
    }

    @Override
    public PrezzoPerCap updatePrezzo(String cap, PrezzoPerCap updatedPrezzo) {
        return prezzoRepo.findById(cap)
                .map(existing -> {
                    existing.setCitta(updatedPrezzo.getCitta());
                    existing.setPrezzoMq(updatedPrezzo.getPrezzoMq());
                    existing.setFonte(updatedPrezzo.getFonte());
                    existing.setValidFrom(updatedPrezzo.getValidFrom());
                    existing.setValidTo(updatedPrezzo.getValidTo());
                    existing.setQualityScore(updatedPrezzo.getQualityScore());
                    existing.setUpdatedAt(updatedPrezzo.getUpdatedAt());
                    return prezzoRepo.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Prezzo per CAP non trovato: " + cap));
    }

    @Override
    public void deletePrezzo(String cap) {
        if (!prezzoRepo.existsById(cap)) {
            throw new RuntimeException("Prezzo per CAP non trovato: " + cap);
        }
        prezzoRepo.deleteById(cap);
    }

    @Override
    public List<PrezzoPerCap> findByCitta(Citta citta) {
        return prezzoRepo.findByCitta(citta);
    }

    @Override
    public List<PrezzoPerCap> findByValidFromAfter(LocalDate date) {
        return prezzoRepo.findByValidFromAfter(date);
    }

    @Override
    public List<PrezzoPerCap> findByValidToBefore(LocalDate date) {
        return prezzoRepo.findByValidToBefore(date);
    }
}
