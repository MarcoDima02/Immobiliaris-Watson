package com.residea.residea.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.residea.residea.dto.FormValutazioneRequest;
import com.residea.residea.dto.ValutazioneResultResponse;
import com.residea.residea.entities.DettagliImmobile;
import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Superficie;
import com.residea.residea.entities.Utente;
import com.residea.residea.repos.DettagliImmobileRepo;
import com.residea.residea.repos.ImmobileRepo;
import com.residea.residea.repos.SuperficiRepo;
import com.residea.residea.repos.UtenteRepo;
import com.residea.residea.services.ValutazioneImmobileService;
import com.residea.residea.utils.EnumMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/valutazioni/form")
@RequiredArgsConstructor
public class ValutazioneFormController {

    private final ImmobileRepo immobileRepo;
    private final DettagliImmobileRepo dettagliRepo;
    private final SuperficiRepo superficiRepo;
    private final UtenteRepo utenteRepo;
    private final ValutazioneImmobileService valutazioneService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<ValutazioneResultResponse> submit(@RequestBody FormValutazioneRequest request) {
        
        
        // Validazioni minime lato backend 
        if (request.getSuperficie() == null || request.getSuperficie().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().build();
        }
        if (request.getCap() == null || !request.getCap().matches("\\d{5}")) {
            return ResponseEntity.badRequest().build();
        }
        // Validazione campi obbligatori
        if (request.getNStanze() == null || request.getNBagni() == null || request.getAnnoCostruzione() == null) {
            return ResponseEntity.badRequest().build();
        }

        Utente proprietario = null;
        if (request.getIdUtente() != null) {
            proprietario = utenteRepo.findById(request.getIdUtente()).orElse(null);
        } else if (request.getEmailUtente() != null && !request.getEmailUtente().isEmpty()) {
            // Cerca utente esistente per email o telefono
            proprietario = utenteRepo.findByEmail(request.getEmailUtente()).orElse(null);
            
            if (proprietario == null && request.getTelefonoUtente() != null) {
                var utenti = utenteRepo.findByTelefono(request.getTelefonoUtente());
                if (!utenti.isEmpty()) {
                    proprietario = utenti.get(0);
                }
            }

            if (proprietario == null) {
                proprietario = Utente.builder()
                    .nome(request.getNomeUtente() != null ? request.getNomeUtente() : "")
                    .cognome(request.getCognomeUtente() != null ? request.getCognomeUtente() : "")
                    .email(request.getEmailUtente())
                    .telefono(request.getTelefonoUtente() != null ? request.getTelefonoUtente() : "")
                    .passwordHash(null)
                    .ruolo(Utente.Ruolo.PROPRIETARIO)
                    .verificaEmail(false)
                    .consensoPrivacy(true)
                    .build();
                proprietario = utenteRepo.save(proprietario);
            }
        }

        // Creazione Immobile con builder pattern
        Immobile immobile = Immobile.builder()
            .proprietario(proprietario)
            .tipologia(EnumMapper.mapTipologia(request.getTipologia()))
            .indirizzo(request.getIndirizzo())
            .citta(request.getCitta())
            .provincia(request.getProvincia())
            .cap(request.getCap())
            .stato(Immobile.Stato.DISPONIBILE)
            .build();
        immobile = immobileRepo.save(immobile);

        // Creazione DettagliImmobile con builder pattern
        DettagliImmobile dettagli = DettagliImmobile.builder()
            .immobile(immobile)
            .nStanze(request.getNStanze())
            .nBagni(request.getNBagni())
            .nPiano(request.getPiano())
            .nPianiImmobile(request.getPianiTotali())
            .ascensore(Boolean.TRUE.equals(request.getAscensore()))
            .garage(Boolean.TRUE.equals(request.getGarage()))
            .balconeTerrazzo(request.getSuperficieBalconeTerrazzo() != null && 
                           request.getSuperficieBalconeTerrazzo().compareTo(BigDecimal.ZERO) > 0)
            .giardino(Boolean.TRUE.equals(request.getGiardino()))
            .cantina(Boolean.TRUE.equals(request.getCantina()))
            .annoCostruzione(EnumMapper.normalizeAnnoCostruzione(request.getAnnoCostruzione()))
            .condizioneImmobile(EnumMapper.mapCondizione(request.getCondizione()))
            .tipoRiscaldamento(EnumMapper.mapTipoRiscaldamento(request.getTipoRiscaldamento()))
            .classeEnergetica(EnumMapper.mapClasseEnergetica(request.getClasseEnergetica()))
            .esposizione(request.getEsposizione())
            .build();
        dettagliRepo.save(dettagli);

        // Creazione Superficie con builder pattern
        Superficie superfici = Superficie.builder()
            .immobile(immobile)
            .superficieMq(request.getSuperficie())
            .superficieBalconeTerrazzo(request.getSuperficieBalconeTerrazzo())
            .superficieGarage(Boolean.TRUE.equals(request.getGarage()) ? request.getSuperficieGarage() : null)
            .superficieGiardino(Boolean.TRUE.equals(request.getGiardino()) ? request.getSuperficieGiardino() : null)
            .superficieCantina(Boolean.TRUE.equals(request.getCantina()) ? request.getSuperficieCantina() : null)
            .build();
        superficiRepo.save(superfici);

        // ========================================
        // CALCOLO VALUTAZIONE DOPO SALVATAGGIO
        // ========================================
        ValutazioneResultResponse valutazione = valutazioneService.calculateAndSave(request, immobile.getIdImmobile());
        
        return ResponseEntity.status(201).body(valutazione);
    }
}
