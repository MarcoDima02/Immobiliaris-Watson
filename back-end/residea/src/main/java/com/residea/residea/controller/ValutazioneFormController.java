package com.residea.residea.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.residea.residea.dto.FormValutazioneRequest;
import com.residea.residea.dto.FormValutazioneResponse;
import com.residea.residea.entities.DettagliImmobile;
import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Superficie;
import com.residea.residea.entities.Utente;
import com.residea.residea.repos.DettagliImmobileRepo;
import com.residea.residea.repos.ImmobileRepo;
import com.residea.residea.repos.SuperficiRepo;
import com.residea.residea.repos.UtenteRepo;

@RestController
@RequestMapping("/api/valutazioni/form")
public class ValutazioneFormController {

    private final ImmobileRepo immobileRepo;
    private final DettagliImmobileRepo dettagliRepo;
    private final SuperficiRepo superficiRepo;
    private final UtenteRepo utenteRepo;

    public ValutazioneFormController(ImmobileRepo immobileRepo,
                                     DettagliImmobileRepo dettagliRepo,
                                     SuperficiRepo superficiRepo,
                                     UtenteRepo utenteRepo) {
        this.immobileRepo = immobileRepo;
        this.dettagliRepo = dettagliRepo;
        this.superficiRepo = superficiRepo;
        this.utenteRepo = utenteRepo;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<FormValutazioneResponse> submit(@RequestBody FormValutazioneRequest request) {
        // Log temporaneo per debug
        System.out.println("=== DEBUG REQUEST ===");
        System.out.println("nStanze: " + request.getNStanze());
        System.out.println("nBagni: " + request.getNBagni());
        System.out.println("piano: " + request.getPiano());
        System.out.println("pianiTotali: " + request.getPianiTotali());
        System.out.println("====================");
        
        // Validazioni minime lato backend (ulteriori possono essere aggiunte)
        if (request.getSuperficie() == null || request.getSuperficie().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().build();
        }
        if (request.getCap() == null || !request.getCap().matches("\\d{5}")) {
            return ResponseEntity.badRequest().build();
        }

        Utente proprietario = null;
        if (request.getIdUtente() != null) {
            // Se l'utente esiste già, lo recuperiamo
            proprietario = utenteRepo.findById(request.getIdUtente()).orElse(null);
        } else if (request.getEmailUtente() != null && !request.getEmailUtente().isEmpty()) {
            // Creiamo un nuovo utente se sono forniti i dati
            proprietario = new Utente();
            proprietario.setNome(request.getNomeUtente() != null ? request.getNomeUtente() : "");
            proprietario.setCognome(request.getCognomeUtente() != null ? request.getCognomeUtente() : "");
            proprietario.setEmail(request.getEmailUtente());
            proprietario.setTelefono(request.getTelefonoUtente() != null ? request.getTelefonoUtente() : "");
            // Non impostiamo alcuna password per i proprietari creati dal form;
            // le credenziali sono necessarie solo per ruoli con accesso (AGENTE/AMMINISTRATORE).
            proprietario.setPasswordHash(null);
            proprietario.setRuolo(Utente.Ruolo.PROPRIETARIO);
            proprietario.setVerificaEmail(false);
            proprietario.setConsensoPrivacy(true);
            proprietario = utenteRepo.save(proprietario);
        }

        // Mappatura Tipologia
        Immobile.Tipologia tipologia = Immobile.Tipologia.APPARTAMENTO; // default
        try {
            if (request.getTipologia() != null) {
                tipologia = Immobile.Tipologia.valueOf(request.getTipologia().toUpperCase().replace(' ', '_'));
            }
        } catch (IllegalArgumentException ignored) {}

        Immobile immobile = new Immobile();
    immobile.setProprietario(proprietario); // può essere null in fase di pre-valutazione
        immobile.setTipologia(tipologia);
        immobile.setIndirizzo(request.getIndirizzo());
        immobile.setCitta(request.getCitta());
        immobile.setProvincia(request.getProvincia());
        immobile.setCap(request.getCap());
        immobile.setStato(Immobile.Stato.DISPONIBILE);
    immobile = immobileRepo.save(immobile);

        DettagliImmobile dettagli = new DettagliImmobile();
        dettagli.setImmobile(immobile);  // @MapsId gestisce automaticamente l'ID
        dettagli.setNStanze(request.getNStanze());
        dettagli.setNBagni(request.getNBagni());
        dettagli.setNPiano(request.getPiano());
        dettagli.setNPianiImmobile(request.getPianiTotali());
        dettagli.setAscensore(Boolean.TRUE.equals(request.getAscensore()));
        dettagli.setGarage(Boolean.TRUE.equals(request.getGarage()));
        dettagli.setBalconeTerrazzo(request.getSuperficieBalconeTerrazzo() != null && request.getSuperficieBalconeTerrazzo().compareTo(BigDecimal.ZERO) > 0);
        dettagli.setGiardino(Boolean.TRUE.equals(request.getGiardino()));
        dettagli.setCantina(Boolean.TRUE.equals(request.getCantina()));
        dettagli.setAnnoCostruzione(request.getAnnoCostruzione());
        // Condizione
        try {
            if (request.getCondizione() != null) {
                dettagli.setCondizioneImmobile(DettagliImmobile.CondizioneImmobile.valueOf(request.getCondizione().toUpperCase().replace(' ', '_')));
            }
        } catch (IllegalArgumentException ignored) {}
        // Classe energetica
        try {
            if (request.getClasseEnergetica() != null) {
                dettagli.setClasseEnergetica(DettagliImmobile.ClasseEnergetica.valueOf(request.getClasseEnergetica().replace("+","_PLUS"))); // mapping A+ -> A_PLUS
            }
        } catch (IllegalArgumentException ignored) {}

    dettagliRepo.save(dettagli);

        Superficie superfici = new Superficie();
        superfici.setImmobile(immobile);  // @MapsId gestisce automaticamente l'ID
        superfici.setSuperficieMq(request.getSuperficie());
        superfici.setSuperficieBalconeTerrazzo(request.getSuperficieBalconeTerrazzo());
        superfici.setSuperficieGarage(Boolean.TRUE.equals(request.getGarage()) ? request.getSuperficieGarage() : null);
        superfici.setSuperficieGiardino(Boolean.TRUE.equals(request.getGiardino()) ? request.getSuperficieGiardino() : null);
        superfici.setSuperficieCantina(Boolean.TRUE.equals(request.getCantina()) ? request.getSuperficieCantina() : null);
    superficiRepo.save(superfici);

        FormValutazioneResponse response = new FormValutazioneResponse(
                immobile.getIdImmobile(),
                dettagli.getIdImmobile(),
                superfici.getIdImmobile(),
                proprietario != null ? proprietario.getIdUtente() : null,
                "CREATO"
        );

        return ResponseEntity.status(201).body(response);
    }
}
