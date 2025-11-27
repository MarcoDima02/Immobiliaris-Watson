package com.residea.residea.services;

import com.residea.residea.dto.AgenteRichiestaDTO;
import com.residea.residea.entities.Contratto;
import com.residea.residea.entities.DettagliImmobile;
import com.residea.residea.entities.Richiesta;
import com.residea.residea.entities.Superficie;
import com.residea.residea.entities.ValutazioneImmobile;
import com.residea.residea.repos.ContrattoRepo;
import com.residea.residea.repos.DettagliImmobileRepo;
import com.residea.residea.repos.RichiestaRepo;
import com.residea.residea.repos.SuperficiRepo;
import com.residea.residea.repos.ValutazioneImmobileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service per la dashboard dell'agente immobiliare.
 * Aggrega dati di Contratti, Immobili, Richieste, Valutazioni, ecc.
 */
@Service
public class AgenteService {

    @Autowired
    private ContrattoRepo contrattoRepo;

    @Autowired
    private RichiestaRepo richiestaRepo;

    @Autowired
    private DettagliImmobileRepo dettagliRepo;

    @Autowired
    private SuperficiRepo superficieRepo;

    @Autowired
    private ValutazioneImmobileRepo valutatzioneRepo;

    /**
     * Restituisce i dati aggregati per la dashboard dell'agente.
     * Include: Contratti, Immobili, Richieste, Superfici, Valutazioni
     */
    public List<AgenteRichiestaDTO> getDashboardData(Integer idAgente) {
        List<AgenteRichiestaDTO> result = new ArrayList<>();

        // 1. Trovare tutti i contratti dell'agente
        List<Contratto> contratti = contrattoRepo.findByAgente_IdUtente(idAgente);

        // 2. Per ogni contratto, aggregare i dati
        for (Contratto contratto : contratti) {
            Integer idImmobile = contratto.getIdImmobile().getIdImmobile();

            // Trovare tutte le richieste per questo immobile
            List<Richiesta> richieste = richiestaRepo.findByImmobile_IdImmobile(idImmobile);

            // Se non ci sono richieste, aggiungere il contratto da solo
            if (richieste.isEmpty()) {
                AgenteRichiestaDTO dto = mapContratoToDTO(contratto);
                result.add(dto);
            } else {
                // Per ogni richiesta, creare un DTO
                for (Richiesta richiesta : richieste) {
                    AgenteRichiestaDTO dto = new AgenteRichiestaDTO();

                    // Dati Contratto
                    dto.setIdContratto(contratto.getIdContratto());
                    dto.setTipoContratto(contratto.getTipoContratto() != null ? contratto.getTipoContratto().name() : null);
                    dto.setDataContratto(contratto.getDataContratto());
                    dto.setDataScadenzaContratto(contratto.getDataScadenzaContratto());
                    dto.setPathContrattoPDF(contratto.getPathContrattoPDF());

                    // Dati Immobile
                    dto.setIdImmobile(contratto.getIdImmobile().getIdImmobile());
                    dto.setTipologia(contratto.getIdImmobile().getTipologia() != null ? contratto.getIdImmobile().getTipologia().name() : null);
                    dto.setIndirizzo(contratto.getIdImmobile().getIndirizzo());
                    dto.setCitta(contratto.getIdImmobile().getCitta());
                    dto.setProvincia(contratto.getIdImmobile().getProvincia());
                    dto.setCap(contratto.getIdImmobile().getCap());
                    dto.setStato(contratto.getIdImmobile().getStato() != null ? contratto.getIdImmobile().getStato().name() : null);

                    // Dati DettagliImmobile
                    DettagliImmobile dettagli = dettagliRepo.findById(idImmobile).orElse(null);
                    if (dettagli != null) {
                        dto.setNStanze(dettagli.getNStanze());
                        dto.setNBagni(dettagli.getNBagni());
                        dto.setNPiano(dettagli.getNPiano());
                        dto.setNPianiImmobile(dettagli.getNPianiImmobile());
                        dto.setBalconeTerrazzo(dettagli.isBalconeTerrazzo());
                        dto.setGiardino(dettagli.isGiardino());
                        dto.setGarage(dettagli.isGarage());
                        dto.setAscensore(dettagli.isAscensore());
                        dto.setCantina(dettagli.isCantina());
                        dto.setTipoRiscaldamento(dettagli.getTipoRiscaldamento() != null ? dettagli.getTipoRiscaldamento().name() : null);
                        dto.setAnnoCostruzione(dettagli.getAnnoCostruzione());
                        dto.setCondizioneImmobile(dettagli.getCondizioneImmobile() != null ? dettagli.getCondizioneImmobile().name() : null);
                        dto.setClasseEnergetica(dettagli.getClasseEnergetica() != null ? dettagli.getClasseEnergetica().name() : null);
                    }

                    // Dati Superfici
                    Superficie superficie = superficieRepo.findById(idImmobile).orElse(null);
                    if (superficie != null) {
                        dto.setSuperficieMq(superficie.getSuperficieMq());
                        dto.setSuperficieBalconeTerrazzo(superficie.getSuperficieBalconeTerrazzo());
                        dto.setSuperficieGiardino(superficie.getSuperficieGiardino());
                        dto.setSuperficieGarage(superficie.getSuperficieGarage());
                        dto.setSuperficieCantina(superficie.getSuperficieCantina());
                    }

                    // Dati Richiesta
                    dto.setIdRichiesta(richiesta.getIdRichiesta());
                    dto.setDataRichiesta(richiesta.getDataRichiesta());
                    dto.setDataAppuntamento(richiesta.getDataAppuntamento());
                    dto.setStatoRichiesta(richiesta.getStato() != null ? richiesta.getStato().name() : null);
                    dto.setNoteUtente(richiesta.getNoteUtente());
                    dto.setMotivoAnnullamento(richiesta.getMotivoAnnullamento());

                    // Dati Utente (chi ha fatto richiesta)
                    if (richiesta.getUtente() != null) {
                        dto.setIdUtente(richiesta.getUtente().getIdUtente());
                        dto.setNomeUtente(richiesta.getUtente().getNome());
                        dto.setCognomeUtente(richiesta.getUtente().getCognome());
                        dto.setTelefonoUtente(richiesta.getUtente().getTelefono());
                        dto.setEmailUtente(richiesta.getUtente().getEmail());
                    }

                    // Dati ValutazioneImmobile
                    ValutazioneImmobile valutazione = valutatzioneRepo.findByIdImmobile(idImmobile).orElse(null);
                    if (valutazione != null) {
                        dto.setIdValutazione(valutazione.getIdValutazione());
                        dto.setValoreBase(valutazione.getValoreBase() != null ? valutazione.getValoreBase().longValue() : null);
                        dto.setFattoreAggiustamento(valutazione.getFattoreAggiustamento());
                        dto.setValoreMedio(valutazione.getValoreMedio() != null ? valutazione.getValoreMedio().longValue() : null);
                        dto.setValoreMin(valutazione.getValoreMin() != null ? valutazione.getValoreMin().longValue() : null);
                        dto.setValoreMax(valutazione.getValoreMax() != null ? valutazione.getValoreMax().longValue() : null);
                        dto.setConfidence(valutazione.getConfidence());
                    }

                    result.add(dto);
                }
            }
        }

        return result;
    }

    /**
     * Mappa un Contratto a AgenteRichiestaDTO senza richieste associate
     */
    private AgenteRichiestaDTO mapContratoToDTO(Contratto contratto) {
        AgenteRichiestaDTO dto = new AgenteRichiestaDTO();

        // Dati Contratto
        dto.setIdContratto(contratto.getIdContratto());
        dto.setTipoContratto(contratto.getTipoContratto() != null ? contratto.getTipoContratto().name() : null);
        dto.setDataContratto(contratto.getDataContratto());
        dto.setDataScadenzaContratto(contratto.getDataScadenzaContratto());
        dto.setPathContrattoPDF(contratto.getPathContrattoPDF());

        // Dati Immobile
        Integer idImmobile = contratto.getIdImmobile().getIdImmobile();
        dto.setIdImmobile(idImmobile);
        dto.setTipologia(contratto.getIdImmobile().getTipologia() != null ? contratto.getIdImmobile().getTipologia().name() : null);
        dto.setIndirizzo(contratto.getIdImmobile().getIndirizzo());
        dto.setCitta(contratto.getIdImmobile().getCitta());
        dto.setProvincia(contratto.getIdImmobile().getProvincia());
        dto.setCap(contratto.getIdImmobile().getCap());
        dto.setStato(contratto.getIdImmobile().getStato() != null ? contratto.getIdImmobile().getStato().name() : null);

        // Dati DettagliImmobile
        DettagliImmobile dettagli = dettagliRepo.findById(idImmobile).orElse(null);
        if (dettagli != null) {
            dto.setNStanze(dettagli.getNStanze());
            dto.setNBagni(dettagli.getNBagni());
            dto.setNPiano(dettagli.getNPiano());
            dto.setNPianiImmobile(dettagli.getNPianiImmobile());
            dto.setBalconeTerrazzo(dettagli.isBalconeTerrazzo());
            dto.setGiardino(dettagli.isGiardino());
            dto.setGarage(dettagli.isGarage());
            dto.setAscensore(dettagli.isAscensore());
            dto.setCantina(dettagli.isCantina());
            dto.setTipoRiscaldamento(dettagli.getTipoRiscaldamento() != null ? dettagli.getTipoRiscaldamento().name() : null);
            dto.setAnnoCostruzione(dettagli.getAnnoCostruzione());
            dto.setCondizioneImmobile(dettagli.getCondizioneImmobile() != null ? dettagli.getCondizioneImmobile().name() : null);
            dto.setClasseEnergetica(dettagli.getClasseEnergetica() != null ? dettagli.getClasseEnergetica().name() : null);
        }

        // Dati Superfici
        Superficie superficie = superficieRepo.findById(idImmobile).orElse(null);
        if (superficie != null) {
            dto.setSuperficieMq(superficie.getSuperficieMq());
            dto.setSuperficieBalconeTerrazzo(superficie.getSuperficieBalconeTerrazzo());
            dto.setSuperficieGiardino(superficie.getSuperficieGiardino());
            dto.setSuperficieGarage(superficie.getSuperficieGarage());
            dto.setSuperficieCantina(superficie.getSuperficieCantina());
        }

        return dto;
    }
}
