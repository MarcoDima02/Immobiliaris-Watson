package com.residea.residea.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.residea.residea.entities.Immagine;
import com.residea.residea.repos.ImmagineRepo;

@Service
public class ImmagineServiceImpl implements ImmagineService {

    @Autowired
    private ImmagineRepo immagineRepo;

    // --- READ ---
    @Override
    public List<Immagine> getAllImmagini() {
        return immagineRepo.findAll();
    }

    @Override
    public Immagine getImmagineById(Integer idImmagine) {
        return immagineRepo.findById(idImmagine)
                .orElseThrow(() -> new RuntimeException("Immagine non trovata"));
    }

    @Override
    public List<Immagine> getImmaginiByImmobileId(Integer idImmobile) {
        return immagineRepo.findByImmobile_IdImmobile(idImmobile);
    }

    // --- CREATE ---
    @Override
    public Immagine salvaImmagine(Immagine immagine) {
        return immagineRepo.save(immagine);
    }

    // --- UPDATE ---
    @Override
    public Immagine aggiornaImmagine(Immagine immagineAggiornata) {
        return immagineRepo.save(immagineAggiornata);
    }

    // --- DELETE ---
    @Override
    public void eliminaImmagine(Integer idImmagine) {
        immagineRepo.deleteById(idImmagine);
    }
}
