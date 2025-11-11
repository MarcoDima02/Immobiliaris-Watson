package com.residea.residea.services;

import java.math.BigDecimal;
import java.util.List;

import com.residea.residea.entities.DettagliImmobile;
import com.residea.residea.entities.DettagliImmobile.ClasseEnergetica;
import com.residea.residea.entities.DettagliImmobile.CondizioneImmobile;
import com.residea.residea.entities.DettagliImmobile.TipoRiscaldamento;

public interface DettagliImmobileService {

    // --- READ ---
    List<DettagliImmobile> getAllDettagli();
    DettagliImmobile getDettagliByIdImmobile(Integer idImmobile);
    List<DettagliImmobile> getDettagliByPrezzoMinoreDi(BigDecimal prezzoMax);
    List<DettagliImmobile> getDettagliByClasseEnergetica(ClasseEnergetica classeEnergetica);
    List<DettagliImmobile> getDettagliByCondizione(CondizioneImmobile condizioneImmobile);
    List<DettagliImmobile> getDettagliByTipoRiscaldamento(TipoRiscaldamento tipoRiscaldamento);
    List<DettagliImmobile> getDettagliConGarage(Boolean garage);
    List<DettagliImmobile> getDettagliConGiardino(Boolean giardino);

    // --- CREATE ---
    DettagliImmobile salvaDettagli(DettagliImmobile dettagli);

    // --- UPDATE ---
    DettagliImmobile aggiornaDettagli(DettagliImmobile dettagliAggiornati);
    DettagliImmobile aggiornaPrezzo(Integer idImmobile, BigDecimal nuovoPrezzo);

    // --- DELETE ---
    void eliminaDettagli(Integer idImmobile);
}
