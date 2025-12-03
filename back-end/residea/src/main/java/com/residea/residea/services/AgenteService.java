package com.residea.residea.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.residea.residea.dto.AgenteRichiestaDTO;
import com.residea.residea.entities.Contratto;
import com.residea.residea.entities.DettagliImmobile;
import com.residea.residea.entities.Richiesta;
import com.residea.residea.entities.Superficie;
import com.residea.residea.entities.ValutazioneImmobile;
import com.residea.residea.events.RichiestaPresaInCaricoEvent;
import com.residea.residea.repos.ContrattoRepo;
import com.residea.residea.repos.DettagliImmobileRepo;
import com.residea.residea.repos.RichiestaRepo;
import com.residea.residea.repos.SuperficiRepo;
import com.residea.residea.repos.UtenteRepo;
import com.residea.residea.repos.ValutazioneImmobileRepo;

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
    private ValutazioneImmobileRepo valutazioneRepo;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UtenteRepo utenteRepo;

    /**
     * Restituisce i dati aggregati per la dashboard dell'agente.
     * Include: Richieste IN_ATTESA (disponibili per tutti), Contratti dell'agente, Immobili, Richieste, Superfici, Valutazioni
     */
    public List<AgenteRichiestaDTO> getDashboardData(Integer idAgente) {
        List<AgenteRichiestaDTO> result = new ArrayList<>();

        // 0. PRIMA: Aggiungere tutte le richieste IN_ATTESA che NON hanno un contratto con questo agente
        List<Richiesta> richiesteInAttesa = richiestaRepo.findByStato(Richiesta.Stato.IN_ATTESA);
        List<Contratto> contrattiAgente = contrattoRepo.findByAgente_IdUtente(idAgente);
        List<Integer> immobiliConContratto = contrattiAgente.stream()
            .map(c -> c.getIdImmobile().getIdImmobile())
            .toList();
        
        for (Richiesta richiesta : richiesteInAttesa) {
            // Salta questa richiesta se l'immobile ha già un contratto con questo agente
            if (immobiliConContratto.contains(richiesta.getImmobile().getIdImmobile())) {
                continue;
            }
            
            AgenteRichiestaDTO dto = new AgenteRichiestaDTO();
            
            // Dati Richiesta
            dto.setIdRichiesta(richiesta.getIdRichiesta());
            dto.setStatoRichiesta(richiesta.getStato() != null ? richiesta.getStato().name() : null);
            dto.setDataRichiesta(richiesta.getDataRichiesta());
            dto.setDataAppuntamento(richiesta.getDataAppuntamento());
            dto.setNoteUtente(richiesta.getNoteUtente());
            dto.setMotivoAnnullamento(richiesta.getMotivoAnnullamento());
            
            // Dati Immobile
            com.residea.residea.entities.Immobile immobile = richiesta.getImmobile();
            dto.setIdImmobile(immobile.getIdImmobile());
            dto.setTipologia(immobile.getTipologia() != null ? immobile.getTipologia().name() : null);
            dto.setIndirizzo(immobile.getIndirizzo());
            dto.setCitta(immobile.getCitta());
            dto.setProvincia(immobile.getProvincia());
            dto.setCap(immobile.getCap());
            dto.setStato(immobile.getStato() != null ? immobile.getStato().name() : null);
            
            // Dati DettagliImmobile
            DettagliImmobile dettagli = dettagliRepo.findById(immobile.getIdImmobile()).orElse(null);
            if (dettagli != null) {
                dto.setNStanze(dettagli.getNStanze());
                dto.setNBagni(dettagli.getNBagni());
                dto.setNPiano(dettagli.getNPiano());
                dto.setNPianiImmobile(dettagli.getNPianiImmobile());
                dto.setAscensore(dettagli.isAscensore());
                dto.setGarage(dettagli.isGarage());
                dto.setBalconeTerrazzo(dettagli.isBalconeTerrazzo());
                dto.setGiardino(dettagli.isGiardino());
                dto.setCantina(dettagli.isCantina());
                dto.setAnnoCostruzione(dettagli.getAnnoCostruzione());
                dto.setCondizioneImmobile(dettagli.getCondizioneImmobile() != null ? dettagli.getCondizioneImmobile().name() : null);
                dto.setTipoRiscaldamento(dettagli.getTipoRiscaldamento() != null ? dettagli.getTipoRiscaldamento().name() : null);
                dto.setClasseEnergetica(dettagli.getClasseEnergetica() != null ? dettagli.getClasseEnergetica().name() : null);
            }
            
            // Dati Superficie
            Superficie superficie = superficieRepo.findById(immobile.getIdImmobile()).orElse(null);
            if (superficie != null) {
                dto.setSuperficieMq(superficie.getSuperficieMq());
                dto.setSuperficieBalconeTerrazzo(superficie.getSuperficieBalconeTerrazzo());
                dto.setSuperficieGarage(superficie.getSuperficieGarage());
                dto.setSuperficieGiardino(superficie.getSuperficieGiardino());
                dto.setSuperficieCantina(superficie.getSuperficieCantina());
            }
            
            // Dati Utente (proprietario)
            if (richiesta.getUtente() != null) {
                dto.setIdUtente(richiesta.getUtente().getIdUtente());
                dto.setNomeUtente(richiesta.getUtente().getNome());
                dto.setCognomeUtente(richiesta.getUtente().getCognome());
                dto.setEmailUtente(richiesta.getUtente().getEmail());
                dto.setTelefonoUtente(richiesta.getUtente().getTelefono());
            }
            
            // Dati Valutazione (se presente)
            java.util.Optional<ValutazioneImmobile> valutazioneOpt = valutazioneRepo.findByIdImmobile(immobile.getIdImmobile());
            if (valutazioneOpt.isPresent()) {
                ValutazioneImmobile valutazione = valutazioneOpt.get();
                dto.setIdValutazione(valutazione.getIdValutazione());
                dto.setValoreBase(valutazione.getValoreBase() != null ? valutazione.getValoreBase().longValue() : null);
                dto.setFattoreAggiustamento(valutazione.getFattoreAggiustamento());
                dto.setValoreMedio(valutazione.getValoreMedio() != null ? valutazione.getValoreMedio().longValue() : null);
                dto.setValoreMin(valutazione.getValoreMin() != null ? valutazione.getValoreMin().longValue() : null);
                dto.setValoreMax(valutazione.getValoreMax() != null ? valutazione.getValoreMax().longValue() : null);
                dto.setConfidence(valutazione.getConfidence());
            }
            
            // NO contratto per richieste in attesa
            dto.setIdContratto(null);
            dto.setTipoContratto(null);
            
            result.add(dto);
        }

        // 1. POI: Per ogni contratto dell'agente, aggregare i dati
        for (Contratto contratto : contrattiAgente) {
            Integer idImmobile = contratto.getIdImmobile().getIdImmobile();

            // Trovare tutte le richieste per questo immobile (ESCLUSE quelle IN_ATTESA già caricate)
            List<Richiesta> richieste = richiestaRepo.findByImmobile_IdImmobile(idImmobile).stream()
                .filter(r -> r.getStato() != Richiesta.Stato.IN_ATTESA)
                .toList();

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
                    ValutazioneImmobile valutazione = valutazioneRepo.findByIdImmobile(idImmobile).orElse(null);
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

        // Dati ValutazioneImmobile
        ValutazioneImmobile valutazione = valutazioneRepo.findByIdImmobile(idImmobile).orElse(null);
        if (valutazione != null) {
            dto.setIdValutazione(valutazione.getIdValutazione());
            dto.setValoreBase(valutazione.getValoreBase() != null ? valutazione.getValoreBase().longValue() : null);
            dto.setFattoreAggiustamento(valutazione.getFattoreAggiustamento());
            dto.setValoreMedio(valutazione.getValoreMedio() != null ? valutazione.getValoreMedio().longValue() : null);
            dto.setValoreMin(valutazione.getValoreMin() != null ? valutazione.getValoreMin().longValue() : null);
            dto.setValoreMax(valutazione.getValoreMax() != null ? valutazione.getValoreMax().longValue() : null);
            dto.setConfidence(valutazione.getConfidence());
        }

        return dto;
    }

    /**
     * Restituisce i dettagli completi di una singola richiesta/contratto per ID contratto.
     * 
     * @param idContratto ID del contratto
     * @return AgenteRichiestaDTO con tutti i dettagli della richiesta
     */
    public AgenteRichiestaDTO getRichiestaDettagli(Integer idContratto) {
        // Trovare il contratto
        Contratto contratto = contrattoRepo.findById(idContratto).orElse(null);
        if (contratto == null) {
            return null;
        }

        Integer idImmobile = contratto.getIdImmobile().getIdImmobile();

        // Trovare le richieste per questo immobile
        List<Richiesta> richieste = richiestaRepo.findByImmobile_IdImmobile(idImmobile);

        // Se non ci sono richieste, restituire il contratto con dettagli
        if (richieste.isEmpty()) {
            return mapContratoToDTO(contratto);
        }

        // Se ci sono richieste, restituire la prima (o implementare logica diversa)
        Richiesta richiesta = richieste.get(0);

        AgenteRichiestaDTO dto = new AgenteRichiestaDTO();

        // Dati Contratto
        dto.setIdContratto(contratto.getIdContratto());
        dto.setTipoContratto(contratto.getTipoContratto() != null ? contratto.getTipoContratto().name() : null);
        dto.setDataContratto(contratto.getDataContratto());
        dto.setDataScadenzaContratto(contratto.getDataScadenzaContratto());
        dto.setPathContrattoPDF(contratto.getPathContrattoPDF());

        // Dati Immobile
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
        ValutazioneImmobile valutazione = valutazioneRepo.findByIdImmobile(idImmobile).orElse(null);
        if (valutazione != null) {
            dto.setIdValutazione(valutazione.getIdValutazione());
            dto.setValoreBase(valutazione.getValoreBase() != null ? valutazione.getValoreBase().longValue() : null);
            dto.setFattoreAggiustamento(valutazione.getFattoreAggiustamento());
            dto.setValoreMedio(valutazione.getValoreMedio() != null ? valutazione.getValoreMedio().longValue() : null);
            dto.setValoreMin(valutazione.getValoreMin() != null ? valutazione.getValoreMin().longValue() : null);
            dto.setValoreMax(valutazione.getValoreMax() != null ? valutazione.getValoreMax().longValue() : null);
            dto.setConfidence(valutazione.getConfidence());
        }

        return dto;
    }

    /**
     * Calcola le statistiche per la dashboard dell'agente.
     * Conta le richieste per stato.
     */
    public com.residea.residea.dto.DashboardStatsDTO getDashboardStats(Integer idAgente) {
        // Trova tutti i contratti dell'agente
        List<Contratto> contratti = contrattoRepo.findByAgente_IdUtente(idAgente);
        
        int richiesteInCarico = 0;
        int richiesteCompletate = 0;
        int richiesteArchiviate = 0;
        
        // Per ogni contratto, conta le richieste per immobile
        for (Contratto contratto : contratti) {
            List<Richiesta> richieste = richiestaRepo.findByImmobile_IdImmobile(
                contratto.getIdImmobile().getIdImmobile()
            );
            
            for (Richiesta richiesta : richieste) {
                if (richiesta.getStato() == Richiesta.Stato.IN_ATTESA || 
                    richiesta.getStato() == Richiesta.Stato.IN_ELABORAZIONE) {
                    richiesteInCarico++;
                } else if (richiesta.getStato() == Richiesta.Stato.COMPLETATA) {
                    richiesteCompletate++;
                } else if (richiesta.getStato() == Richiesta.Stato.ANNULLATA) {
                    richiesteArchiviate++;
                }
            }
        }
        
        return new com.residea.residea.dto.DashboardStatsDTO(
            richiesteInCarico, 
            richiesteCompletate, 
            richiesteArchiviate
        );
    }

    /**
     * Restituisce le richieste in attesa (non ancora prese in carico).
     */
    public List<com.residea.residea.dto.RichiestaCardDTO> getRichiesteInAttesa() {
        List<Richiesta> richieste = richiestaRepo.findByStato(Richiesta.Stato.IN_ATTESA);
        return richieste.stream()
            .map(this::mapRichiestaToCardDTO)
            .toList();
    }

    /**
     * Restituisce le richieste prese in carico dall'agente.
     */
    public List<com.residea.residea.dto.RichiestaCardDTO> getRichiesteInCarico(Integer idAgente) {
        List<com.residea.residea.dto.RichiestaCardDTO> result = new ArrayList<>();
        
        // Trova tutti i contratti dell'agente
        List<Contratto> contratti = contrattoRepo.findByAgente_IdUtente(idAgente);
        
        // Per ogni contratto, trova le richieste attive
        for (Contratto contratto : contratti) {
            List<Richiesta> richieste = richiestaRepo.findByImmobile_IdImmobile(
                contratto.getIdImmobile().getIdImmobile()
            );
            
            for (Richiesta richiesta : richieste) {
                if (richiesta.getStato() == Richiesta.Stato.IN_ATTESA || 
                    richiesta.getStato() == Richiesta.Stato.IN_ELABORAZIONE) {
                    com.residea.residea.dto.RichiestaCardDTO dto = mapRichiestaToCardDTO(richiesta);
                    // Aggiungi info agente
                    dto.setIdAgente(idAgente);
                    dto.setNomeAgente(contratto.getAgente().getNome());
                    dto.setCognomeAgente(contratto.getAgente().getCognome());
                    result.add(dto);
                }
            }
        }
        
        return result;
    }

    /**
     * Mappa una Richiesta a RichiestaCardDTO.
     */
    private com.residea.residea.dto.RichiestaCardDTO mapRichiestaToCardDTO(Richiesta richiesta) {
        com.residea.residea.dto.RichiestaCardDTO dto = new com.residea.residea.dto.RichiestaCardDTO();
        
        // Dati Richiesta
        dto.setIdRichiesta(richiesta.getIdRichiesta());
        dto.setDataRichiesta(richiesta.getDataRichiesta());
        dto.setDataAppuntamento(richiesta.getDataAppuntamento());
        dto.setStato(richiesta.getStato() != null ? richiesta.getStato().name() : null);
        dto.setNoteUtente(richiesta.getNoteUtente());
        
        // Dati Cliente
        if (richiesta.getUtente() != null) {
            dto.setIdCliente(richiesta.getUtente().getIdUtente());
            dto.setNomeCliente(richiesta.getUtente().getNome());
            dto.setCognomeCliente(richiesta.getUtente().getCognome());
            dto.setTelefonoCliente(richiesta.getUtente().getTelefono());
            dto.setEmailCliente(richiesta.getUtente().getEmail());
        }
        
        // Dati Immobile
        if (richiesta.getImmobile() != null) {
            dto.setIdImmobile(richiesta.getImmobile().getIdImmobile());
            dto.setIndirizzo(richiesta.getImmobile().getIndirizzo());
            dto.setCitta(richiesta.getImmobile().getCitta());
            dto.setProvincia(richiesta.getImmobile().getProvincia());
            dto.setCap(richiesta.getImmobile().getCap());
            dto.setTipologia(richiesta.getImmobile().getTipologia() != null ? 
                richiesta.getImmobile().getTipologia().name() : null);
            dto.setStatoImmobile(richiesta.getImmobile().getStato() != null ? 
                richiesta.getImmobile().getStato().name() : null);
            dto.setLatitudine(richiesta.getImmobile().getLatitudine() != null ? 
                richiesta.getImmobile().getLatitudine().doubleValue() : null);
            dto.setLongitudine(richiesta.getImmobile().getLongitudine() != null ? 
                richiesta.getImmobile().getLongitudine().doubleValue() : null);
            
            Integer idImmobile = richiesta.getImmobile().getIdImmobile();
            
            // Dati Dettagli Immobile
            DettagliImmobile dettagli = dettagliRepo.findById(idImmobile).orElse(null);
            if (dettagli != null) {
                dto.setNLocali(dettagli.getNStanze());
                dto.setNBagni(dettagli.getNBagni());
                dto.setBalconeTerrazzo(dettagli.isBalconeTerrazzo());
            }
            
            // Dati Superfici
            Superficie superficie = superficieRepo.findById(idImmobile).orElse(null);
            if (superficie != null) {
                dto.setSuperficieMq(superficie.getSuperficieMq());
            }
            
            // Dati Valutazione
            ValutazioneImmobile valutazione = valutazioneRepo.findByIdImmobile(idImmobile).orElse(null);
            if (valutazione != null) {
                dto.setIdValutazione(valutazione.getIdValutazione());
                dto.setValoreMin(valutazione.getValoreMin() != null ? 
                    valutazione.getValoreMin().longValue() : null);
                dto.setValoreMax(valutazione.getValoreMax() != null ? 
                    valutazione.getValoreMax().longValue() : null);
                dto.setValoreMedio(valutazione.getValoreMedio() != null ? 
                    valutazione.getValoreMedio().longValue() : null);
                dto.setConfidence(valutazione.getConfidence());
            }
        }
        
        return dto;
    }

    /**
     * Prende in carico una richiesta assegnandola all'agente tramite un contratto.
     */
    public void prendiInCaricoRichiesta(Integer idAgente, Integer idRichiesta) {
        // Trova la richiesta
        Richiesta richiesta = richiestaRepo.findById(idRichiesta)
            .orElseThrow(() -> new RuntimeException("Richiesta non trovata"));
        
        // Verifica che la richiesta sia in attesa
        if (richiesta.getStato() != Richiesta.Stato.IN_ATTESA) {
            throw new RuntimeException("La richiesta non è in stato IN_ATTESA");
        }
        
        // Recupera l'agente completo dal database
        com.residea.residea.entities.Utente agenteCompleto = utenteRepo.findById(idAgente)
            .orElseThrow(() -> new RuntimeException("Agente non trovato"));
        
        // Verifica se esiste già un contratto per questo immobile con questo agente
        List<Contratto> contrattiEsistenti = contrattoRepo.findByAgente_IdUtente(idAgente);
        boolean contrattoEsiste = contrattiEsistenti.stream()
            .anyMatch(c -> c.getIdImmobile().getIdImmobile().equals(richiesta.getImmobile().getIdImmobile()));
        
        if (!contrattoEsiste) {
            // Crea un nuovo contratto
            Contratto contratto = new Contratto();
            contratto.setIdImmobile(richiesta.getImmobile());
            contratto.setAgente(agenteCompleto);
            contratto.setTipoContratto(Contratto.TipoContratto.ESCLUSIVO);
            contratto.setDataContratto(java.time.LocalDate.now());
            contratto.setDataScadenzaContratto(java.time.LocalDate.now().plusYears(1));
            
            contrattoRepo.save(contratto);
        }
        
        // Aggiorna lo stato della richiesta
        richiesta.setStato(Richiesta.Stato.IN_ELABORAZIONE);
        richiestaRepo.save(richiesta);
        
        // Pubblica evento per l'invio email al proprietario
        try {
            com.residea.residea.entities.Utente proprietario = richiesta.getUtente();
            com.residea.residea.entities.Immobile immobile = richiesta.getImmobile();
            
            RichiestaPresaInCaricoEvent event = new RichiestaPresaInCaricoEvent(
                richiesta.getIdRichiesta(),
                idAgente,
                proprietario.getIdUtente(),
                proprietario.getEmail(),
                proprietario.getNome(),
                proprietario.getCognome(),
                agenteCompleto.getNome(),
                agenteCompleto.getCognome(),
                agenteCompleto.getEmail(),
                agenteCompleto.getTelefono(),
                immobile.getIndirizzo(),
                immobile.getCitta()
            );
            
            eventPublisher.publishEvent(event);
        } catch (Exception e) {
            // Log ma non bloccare il processo se l'email fallisce
            System.err.println("Errore nell'invio email presa in carico: " + e.getMessage());
        }
    }
    
    /**
     * Recupera tutte le acquisizioni (contratti completati) di un agente
     */
    public List<com.residea.residea.dto.AcquisizioneDTO> getAcquisizioni(Integer idAgente) {
        // Trova tutti i contratti dell'agente - per ora prendiamo tutti i contratti
        // poiché Contratto non ha campo stato
        List<Contratto> contratti = contrattoRepo.findByAgente_IdUtente(idAgente);
        
        return contratti.stream()
            .map(this::mapContrattoToAcquisizioneDTO)
            .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Mappa un Contratto in AcquisizioneDTO con tutti i dettagli
     */
    private com.residea.residea.dto.AcquisizioneDTO mapContrattoToAcquisizioneDTO(Contratto contratto) {
        com.residea.residea.dto.AcquisizioneDTO dto = new com.residea.residea.dto.AcquisizioneDTO();
        
        // Dati contratto
        dto.setIdContratto(contratto.getIdContratto());
        dto.setDataInizio(contratto.getDataContratto());
        dto.setDataFine(contratto.getDataScadenzaContratto());
        dto.setStato("COMPLETATA"); // Default poiché Contratto non ha campo stato
        dto.setCommissione(null); // Contratto non ha campo commissione
        dto.setTerminiCondizioni(null); // Contratto non ha campo termini
        
        // Dati immobile
        com.residea.residea.entities.Immobile immobile = contratto.getIdImmobile();
        if (immobile != null) {
            dto.setIdImmobile(immobile.getIdImmobile());
            dto.setTipologiaImmobile(immobile.getTipologia() != null ? immobile.getTipologia().name() : null);
            dto.setIndirizzoImmobile(immobile.getIndirizzo());
            dto.setCittaImmobile(immobile.getCitta());
            dto.setProvinciaImmobile(immobile.getProvincia());
            
            // Dati proprietario
            com.residea.residea.entities.Utente proprietario = immobile.getProprietario();
            if (proprietario != null) {
                dto.setNomeProprietario(proprietario.getNome());
                dto.setCognomeProprietario(proprietario.getCognome());
            }
            
            // Trova la richiesta associata tramite il contratto
            java.util.Optional<Richiesta> richiestaOpt = richiestaRepo.findByImmobile_IdImmobile(immobile.getIdImmobile())
                .stream()
                .findFirst();
            
            if (richiestaOpt.isPresent()) {
                Richiesta richiesta = richiestaOpt.get();
                
                // Dati cliente (utente che ha fatto la richiesta)
                com.residea.residea.entities.Utente cliente = richiesta.getUtente();
                if (cliente != null) {
                    dto.setIdCliente(cliente.getIdUtente());
                    dto.setNomeCliente(cliente.getNome());
                    dto.setCognomeCliente(cliente.getCognome());
                    dto.setEmailCliente(cliente.getEmail());
                    dto.setTelefonoCliente(cliente.getTelefono());
                }
            }
            
            // Superficie totale
            java.util.Optional<com.residea.residea.entities.Superficie> superficieOpt = superficieRepo.findById(immobile.getIdImmobile());
            if (superficieOpt.isPresent()) {
                com.residea.residea.entities.Superficie superficie = superficieOpt.get();
                Double totale = 0.0;
                if (superficie.getSuperficieMq() != null) totale += superficie.getSuperficieMq().doubleValue();
                dto.setSuperficieTotale(totale);
            }
            
            // Valutazione finale
            java.util.Optional<ValutazioneImmobile> valutazioneOpt = valutazioneRepo.findByImmobile(immobile);
            if (valutazioneOpt.isPresent()) {
                ValutazioneImmobile valutazione = valutazioneOpt.get();
                // Usa valoreMedio invece di valutazioneStimata
                if (valutazione.getValoreMedio() != null) {
                    dto.setValutazioneFinale(valutazione.getValoreMedio().doubleValue());
                }
            }
        }
        return dto;
    }
    
    /**
     * Cambia lo stato di una richiesta con validazione delle transizioni
     * Transizioni valide:
     * - IN_ATTESA -> IN_ELABORAZIONE (tramite prendi in carico)
     * - IN_ELABORAZIONE -> COMPLETATA
     * - Qualsiasi -> ANNULLATA
     */
    public void cambiaStatoRichiesta(Integer idAgente, Integer idRichiesta, String nuovoStatoStr) {
        // Trova la richiesta
        Richiesta richiesta = richiestaRepo.findById(idRichiesta)
            .orElseThrow(() -> new RuntimeException("Richiesta non trovata con id: " + idRichiesta));
        
        // Converti il nuovo stato da stringa a enum
        Richiesta.Stato nuovoStato;
        try {
            nuovoStato = Richiesta.Stato.valueOf(nuovoStatoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Stato non valido: " + nuovoStatoStr + 
                ". Valori ammessi: IN_ATTESA, IN_ELABORAZIONE, COMPLETATA, ANNULLATA");
        }
        
        // Valida la transizione
        Richiesta.Stato statoCorrente = richiesta.getStato();
        if (!isTransizioneValida(statoCorrente, nuovoStato)) {
            throw new IllegalArgumentException(
                "Transizione non valida da " + statoCorrente + " a " + nuovoStato
            );
        }
        
        // Se si passa a COMPLETATA, verifica che esista un contratto
        if (nuovoStato == Richiesta.Stato.COMPLETATA) {
            // Verifica che esista un contratto per questa richiesta
            // ContrattoRepo non ha findByRichiesta_IdRichiesta, usa findByIdImmobile_IdImmobile
            java.util.Optional<Contratto> contrattoOpt = contrattoRepo.findByIdImmobile_IdImmobile(richiesta.getImmobile().getIdImmobile())
                .stream()
                .filter(c -> c.getAgente().getIdUtente().equals(idAgente))
                .findFirst();
            
            if (contrattoOpt.isEmpty()) {
                throw new RuntimeException("Impossibile completare una richiesta senza contratto associato");
            }
            
            // Verifica che il contratto appartenga all'agente
            Contratto contratto = contrattoOpt.get();
            if (!contratto.getAgente().getIdUtente().equals(idAgente)) {
                throw new RuntimeException("Il contratto non appartiene all'agente specificato");
            }
            
            // Nota: Contratto non ha campo stato, quindi non possiamo aggiornarlo
        }
        
        // Aggiorna lo stato della richiesta
        richiesta.setStato(nuovoStato);
        richiestaRepo.save(richiesta);
    }
    
    /**
     * Valida se una transizione di stato è ammessa
     */
    private boolean isTransizioneValida(Richiesta.Stato statoCorrente, Richiesta.Stato nuovoStato) {
        // Si può sempre annullare
        if (nuovoStato == Richiesta.Stato.ANNULLATA) {
            return true;
        }
        
        // Transizioni valide
        switch (statoCorrente) {
            case IN_ATTESA:
                return nuovoStato == Richiesta.Stato.IN_ELABORAZIONE;
            case IN_ELABORAZIONE:
                return nuovoStato == Richiesta.Stato.COMPLETATA;
            case COMPLETATA:
                return false; // Non si può cambiare stato da COMPLETATA
            case ANNULLATA:
                return false; // Non si può cambiare stato da ANNULLATA
            default:
                return false;
        }
    }
}
