package com.residea.residea.services;

import java.time.LocalDate;
import java.util.List;

import com.residea.residea.entities.Contratto;
import com.residea.residea.entities.Contratto.TipoContratto;

public interface ContrattoService {

    // --- READ ---
    List<Contratto> getAllContratti();
    Contratto getContrattoById(Integer idContratto);
    List<Contratto> getContrattiByTipo(TipoContratto tipoContratto);
    List<Contratto> getContrattiByImmobileId(Integer idImmobile);
    List<Contratto> getContrattiScaduti(LocalDate dataRiferimento);
    List<Contratto> getContrattiInScadenza(LocalDate entroData);

    // --- CREATE ---
    Contratto salvaContratto(Contratto contratto);

    // --- UPDATE ---
    Contratto aggiornaContratto(Contratto contrattoAggiornato);
    Contratto aggiornaPathContrattoPDF(Integer idContratto, String nuovoPath);

    // --- DELETE ---
    void eliminaContratto(Integer idContratto);
}
