package com.residea.residea.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residea.residea.dto.ImmobileListDTO;
import com.residea.residea.entities.Contratto;
import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Richiesta;
import com.residea.residea.entities.Superficie;
import com.residea.residea.entities.Utente;
import com.residea.residea.entities.ValutazioneImmobile;
import com.residea.residea.repos.ContrattoRepo;
import com.residea.residea.repos.DettagliImmobileRepo;
import com.residea.residea.repos.ImmobileRepo;
import com.residea.residea.repos.RichiestaRepo;
import com.residea.residea.repos.SuperficiRepo;
import com.residea.residea.repos.ValutazioneImmobileRepo;

@Service
public class ImmobileServiceImpl implements ImmobileService {

    @Autowired
    private ImmobileRepo immobileRepo;
    
    @Autowired
    private RichiestaRepo richiestaRepo;

    @Autowired
    private ContrattoRepo contrattoRepo;

    @Autowired
    private DettagliImmobileRepo dettagliImmobileRepo;

    @Autowired
    private SuperficiRepo superficiRepo;

    @Autowired
    private ValutazioneImmobileRepo valutazioneRepo;

    // --- READ ---
    @Override
    public List<Immobile> getAllImmobili() {
        return immobileRepo.findAll();
    }

    @Override
    public Immobile getImmobileById(Integer idImmobile) {
        return immobileRepo.findById(idImmobile)
                .orElseThrow(() -> new RuntimeException("Immobile non trovato"));
    }

    @Override
    public List<Immobile> getImmobiliByProprietario(Utente proprietario) {
        return immobileRepo.findByProprietario(proprietario);
    }

    @Override
    public List<Immobile> getImmobiliByTipologia(Immobile.Tipologia tipologia) {
        return immobileRepo.findByTipologia(tipologia);
    }

    // --- CREATE ---
    @Override
    public Immobile salvaImmobile(Immobile immobile) {
        return immobileRepo.save(immobile);
    }

    // --- UPDATE ---
    @Override
    public Immobile aggiornaImmobile(Immobile immobileAggiornato) {
        // L'update funziona direttamente tramite save(), purché idImmobile sia valorizzato
        return immobileRepo.save(immobileAggiornato);
    }

    // --- DELETE ---
    // @Override
    // public void eliminaImmobile(Integer idImmobile) {
    //     immobileRepo.deleteById(idImmobile);
    // }
    
    // --- READ WITH DETAILS (for dashboard) ---
    @Override
    public List<ImmobileListDTO> getAllImmobiliWithDetails() {
        List<Immobile> immobili = immobileRepo.findAll();
        return immobili.stream()
                .map(this::mapToListDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ImmobileListDTO getImmobileDetailsById(Integer idImmobile) {
        Immobile immobile = immobileRepo.findById(idImmobile)
                .orElseThrow(() -> new RuntimeException("Immobile non trovato con id: " + idImmobile));
        return mapToListDTO(immobile);
    }
    
    /**
     * Mappa un'entità Immobile in ImmobileListDTO con tutti i dati correlati
     */
    private ImmobileListDTO mapToListDTO(Immobile immobile) {
        ImmobileListDTO dto = new ImmobileListDTO();

        // Dati immobile base
        dto.setIdImmobile(immobile.getIdImmobile());
        dto.setTipologia(immobile.getTipologia() != null ? immobile.getTipologia().name() : null);
        dto.setIndirizzo(immobile.getIndirizzo());
        dto.setCitta(immobile.getCitta());
        dto.setProvincia(immobile.getProvincia());
        dto.setCap(immobile.getCap());
        dto.setStato(immobile.getStato() != null ? immobile.getStato().name() : null);
        
        // Coordinate
        if (immobile.getLatitudine() != null) {
            dto.setLatitudine(immobile.getLatitudine().doubleValue());
        }
        if (immobile.getLongitudine() != null) {
            dto.setLongitudine(immobile.getLongitudine().doubleValue());
        }

        // Dati proprietario
        Utente proprietario = immobile.getProprietario();
        if (proprietario != null) {
            dto.setIdProprietario(proprietario.getIdUtente());
            dto.setNomeProprietario(proprietario.getNome());
            dto.setCognomeProprietario(proprietario.getCognome());
            dto.setEmailProprietario(proprietario.getEmail());
            dto.setTelefonoProprietario(proprietario.getTelefono());
        }

        // Cerca richiesta associata (se esiste)
        Optional<Richiesta> richiestaOpt = richiestaRepo.findByImmobile_IdImmobile(immobile.getIdImmobile())
                .stream()
                .filter(r -> !r.getStato().equals(Richiesta.Stato.ANNULLATA))
                .findFirst();

        if (richiestaOpt.isPresent()) {
            Richiesta richiesta = richiestaOpt.get();
            dto.setIdRichiesta(richiesta.getIdRichiesta());
            dto.setStatoRichiesta(richiesta.getStato().name());

            // Cerca contratto associato (se esiste)
            // ContrattoRepo non ha findByRichiesta_IdRichiesta, usa findByIdImmobile_IdImmobile
            Optional<Contratto> contrattoOpt = contrattoRepo.findByIdImmobile_IdImmobile(immobile.getIdImmobile())
                .stream()
                .findFirst();
            if (contrattoOpt.isPresent()) {
                Contratto contratto = contrattoOpt.get();
                dto.setIdContratto(contratto.getIdContratto());
                // Contratto non ha campo stato
                dto.setStatoContratto("ATTIVO");

                // Dati agente dal contratto
                Utente agente = contratto.getAgente();
                if (agente != null) {
                    dto.setIdAgente(agente.getIdUtente());
                    dto.setNomeAgente(agente.getNome());
                    dto.setCognomeAgente(agente.getCognome());
                }
            }
        }

        // Superficie totale
        Optional<Superficie> superficieOpt = superficiRepo.findById(immobile.getIdImmobile());
        if (superficieOpt.isPresent()) {
            Superficie superficie = superficieOpt.get();
            Double totale = 0.0;
            if (superficie.getSuperficieMq() != null) totale += superficie.getSuperficieMq().doubleValue();
            dto.setSuperficieTotale(totale);
        }

        // Valutazione stimata
        Optional<ValutazioneImmobile> valutazioneOpt = valutazioneRepo.findByImmobile(immobile);
        if (valutazioneOpt.isPresent()) {
            ValutazioneImmobile valutazione = valutazioneOpt.get();
            // Usa valoreMedio invece di valutazioneStimata
            if (valutazione.getValoreMedio() != null) {
                dto.setValutazioneStimata(valutazione.getValoreMedio().doubleValue());
            }
        }

        return dto;
    }
}
