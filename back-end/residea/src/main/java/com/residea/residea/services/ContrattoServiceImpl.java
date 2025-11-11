package com.residea.residea.services;

import com.residea.residea.entities.Contratto;
import com.residea.residea.entities.Contratto.TipoContratto;
import com.residea.residea.repos.ContrattoRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class ContrattoServiceImpl implements ContrattoService {

    @Autowired
    private ContrattoRepo contrattoRepository;

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
        return contrattoRepository.save(contratto);
    }

    // --- UPDATE ---
    @Override
    public Contratto aggiornaContratto(Contratto contrattoAggiornato) {
        Optional<Contratto> contrattoEsistente = contrattoRepository.findById(contrattoAggiornato.getIdContratto());
        if (contrattoEsistente.isPresent()) {
            return contrattoRepository.save(contrattoAggiornato);
        }
        return null;
    }

    @Override
    public Contratto aggiornaPathContrattoPDF(Integer idContratto, String nuovoPath) {
        Optional<Contratto> contrattoOpt = contrattoRepository.findById(idContratto);
        if (contrattoOpt.isPresent()) {
            Contratto contratto = contrattoOpt.get();
            contratto.setPathContrattoPDF(nuovoPath);
            return contrattoRepository.save(contratto);
        }
        return null;
    }

    // --- DELETE ---
    @Override
    public void eliminaContratto(Integer idContratto) {
        contrattoRepository.deleteById(idContratto);
    }
}
