package com.residea.residea.services;

import com.residea.residea.entities.DettagliImmobile;
import com.residea.residea.entities.DettagliImmobile.ClasseEnergetica;
import com.residea.residea.entities.DettagliImmobile.CondizioneImmobile;
import com.residea.residea.entities.DettagliImmobile.TipoRiscaldamento;
import com.residea.residea.repos.DettagliImmobileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DettagliImmobileServiceImpl implements DettagliImmobileService {

    @Autowired
    private DettagliImmobileRepo dettagliImmobileRepo;

    // --- READ ---
    @Override
    public List<DettagliImmobile> getAllDettagli() {
        return dettagliImmobileRepo.findAll();
    }

    @Override
    public DettagliImmobile getDettagliByIdImmobile(Integer idImmobile) {
        return dettagliImmobileRepo.findById(idImmobile).orElse(null);
    }

    @Override
    public List<DettagliImmobile> getDettagliByPrezzoMinoreDi(BigDecimal prezzoMax) {
        return dettagliImmobileRepo.findByPrezzoLessThanEqual(prezzoMax);
    }

    @Override
    public List<DettagliImmobile> getDettagliByClasseEnergetica(ClasseEnergetica classeEnergetica) {
        return dettagliImmobileRepo.findByClasseEnergetica(classeEnergetica);
    }

    @Override
    public List<DettagliImmobile> getDettagliByCondizione(CondizioneImmobile condizioneImmobile) {
        return dettagliImmobileRepo.findByCondizioneImmobile(condizioneImmobile);
    }

    @Override
    public List<DettagliImmobile> getDettagliByTipoRiscaldamento(TipoRiscaldamento tipoRiscaldamento) {
        return dettagliImmobileRepo.findByTipoRiscaldamento(tipoRiscaldamento);
    }

    @Override
    public List<DettagliImmobile> getDettagliConGarage(Boolean garage) {
        return dettagliImmobileRepo.findByGarage(garage);
    }

    @Override
    public List<DettagliImmobile> getDettagliConGiardino(Boolean giardino) {
        return dettagliImmobileRepo.findByGiardino(giardino);
    }

    // --- CREATE ---
    @Override
    public DettagliImmobile salvaDettagli(DettagliImmobile dettagli) {
        return dettagliImmobileRepo.save(dettagli);
    }

    // --- UPDATE ---
    @Override
    public DettagliImmobile aggiornaDettagli(DettagliImmobile dettagliAggiornati) {
        Optional<DettagliImmobile> esistente = dettagliImmobileRepo.findById(dettagliAggiornati.getIdImmobile());
        if (esistente.isPresent()) {
            return dettagliImmobileRepo.save(dettagliAggiornati);
        }
        return null;
    }

    @Override
    public DettagliImmobile aggiornaPrezzo(Integer idImmobile, BigDecimal nuovoPrezzo) {
        Optional<DettagliImmobile> dettagliOpt = dettagliImmobileRepo.findById(idImmobile);
        if (dettagliOpt.isPresent()) {
            DettagliImmobile dettagli = dettagliOpt.get();
            dettagli.setPrezzo(nuovoPrezzo);
            return dettagliImmobileRepo.save(dettagli);
        }
        return null;
    }

    // --- DELETE ---
    @Override
    public void eliminaDettagli(Integer idImmobile) {
        dettagliImmobileRepo.deleteById(idImmobile);
    }
}
