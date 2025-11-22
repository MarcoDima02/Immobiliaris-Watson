package com.residea.residea.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residea.residea.entities.Contratto;
import com.residea.residea.entities.Contratto.TipoContratto;
import com.residea.residea.repos.ContrattoRepo;
import com.residea.residea.repos.ImmobileRepo;


@Service
public class ContrattoServiceImpl implements ContrattoService {

    @Autowired
    private ContrattoRepo contrattoRepository;

    @Autowired
    private ImmobileRepo immobileRepo;
    
    // --- READ ---
    @Override
    public List<Contratto> getAllContratti() {
        return contrattoRepository.findAll();
    }

    @Override
    public Contratto getContrattoById(Integer idContratto) {
        return contrattoRepository.findById(idContratto).orElse(null);
    }

    @Override
    public List<Contratto> getContrattiByTipo(TipoContratto tipoContratto) {
        return contrattoRepository.findByTipoContratto(tipoContratto);
    }

    @Override
    public List<Contratto> getContrattiByImmobileId(Integer idImmobile) {
        return contrattoRepository.findByIdImmobile_IdImmobile(idImmobile);
    }

    @Override
    public List<Contratto> getContrattiScaduti(LocalDate dataRiferimento) {
        return contrattoRepository.findByDataScadenzaContrattoBefore(dataRiferimento);
    }

    @Override
    public List<Contratto> getContrattiInScadenza(LocalDate entroData) {
        return contrattoRepository.findByDataScadenzaContrattoBetween(LocalDate.now(), entroData);
    }

    // --- CREATE ---
    @Override
    public Contratto salvaContratto(Contratto contratto) {

        // Leggo id dell'immobile dal JSON
        Integer idImm = contratto.getIdImmobile().getIdImmobile();

        // Carico l'immobile dal DB → OBBLIGATORIO
        var immobile = immobileRepo.findById(idImm)
                .orElseThrow(() -> new RuntimeException("Immobile non trovato: " + idImm));

        // Aggancio immobile "managed"
        contratto.setIdImmobile(immobile);

        return contrattoRepository.save(contratto);
    }


    // --- UPDATE ---
    @Override
    public Contratto aggiornaContratto(Contratto contrattoAggiornato) {

        Integer idImm = contrattoAggiornato.getIdImmobile().getIdImmobile();

        var immobile = immobileRepo.findById(idImm)
                .orElseThrow(() -> new RuntimeException("Immobile non trovato: " + idImm));

        contrattoAggiornato.setIdImmobile(immobile);

        return contrattoRepository.save(contrattoAggiornato);
    }
    
    @Override
    public Contratto aggiornaPathContrattoPDF(Integer idContratto, String nuovoPath) {
        return contrattoRepository.findById(idContratto)
                .map(contratto -> {
                    contratto.setPathContrattoPDF(nuovoPath);
                    return contrattoRepository.save(contratto);
                })
                .orElse(null); // oppure lancia eccezione se preferisci
    }


    // --- DELETE ---
    @Override
    public void eliminaContratto(Integer idContratto) {
        contrattoRepository.deleteById(idContratto);
    }
}
