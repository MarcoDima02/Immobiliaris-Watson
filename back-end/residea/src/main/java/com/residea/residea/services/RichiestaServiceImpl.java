package com.residea.residea.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.residea.residea.entities.Richiesta;
import com.residea.residea.entities.Utente;
import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Richiesta.Stato;
import com.residea.residea.repos.RichiestaRepo;

@Service
@Transactional
public class RichiestaServiceImpl implements RichiestaService {

    private final RichiestaRepo richiestaRepo;

    public RichiestaServiceImpl(RichiestaRepo richiestaRepo) {
        this.richiestaRepo = richiestaRepo;
    }

    @Override
    public Richiesta createRichiesta(Richiesta richiesta) {
        return richiestaRepo.save(richiesta);
    }

    @Override
    public List<Richiesta> getAllRichieste() {
        return richiestaRepo.findAll();
    }

    @Override
    public Optional<Richiesta> getRichiestaById(Integer idRichiesta) {
        return richiestaRepo.findById(idRichiesta);
    }

    @Override
    public Richiesta updateRichiesta(Integer idRichiesta, Richiesta updatedRichiesta) {
        return richiestaRepo.findById(idRichiesta)
                .map(existing -> {
                    existing.setUtente(updatedRichiesta.getUtente());
                    existing.setImmobile(updatedRichiesta.getImmobile());
                    existing.setDataRichiesta(updatedRichiesta.getDataRichiesta());
                    existing.setDataAppuntamento(updatedRichiesta.getDataAppuntamento());
                    existing.setStato(updatedRichiesta.getStato());
                    existing.setNoteUtente(updatedRichiesta.getNoteUtente());
                    existing.setMotivoAnnullamento(updatedRichiesta.getMotivoAnnullamento());
                    return richiestaRepo.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Richiesta non trovata con id: " + idRichiesta));
    }

    @Override
    public void deleteRichiesta(Integer idRichiesta) {
        if (!richiestaRepo.existsById(idRichiesta)) {
            throw new RuntimeException("Richiesta non trovata con id: " + idRichiesta);
        }
        richiestaRepo.deleteById(idRichiesta);
    }

    @Override
    public List<Richiesta> findByUtente(Utente utente) {
        return richiestaRepo.findByUtente(utente);
    }

    @Override
    public List<Richiesta> findByImmobile(Immobile immobile) {
        return richiestaRepo.findByImmobile(immobile);
    }

    @Override
    public List<Richiesta> findByStato(Stato stato) {
        return richiestaRepo.findByStato(stato);
    }
}
