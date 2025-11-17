package com.residea.residea.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.residea.residea.dto.FormValutazioneRequest;
import com.residea.residea.dto.ValutazioneResultResponse;
import com.residea.residea.entities.DettagliImmobile;
import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.PrezzoPerCap;
import com.residea.residea.entities.Superficie;
import com.residea.residea.entities.ValutazioneImmobile;
import com.residea.residea.repos.CittaRepo;
import com.residea.residea.repos.DettagliImmobileRepo;
import com.residea.residea.repos.ImmobileRepo;
import com.residea.residea.repos.PrezzoPerCapRepo;
import com.residea.residea.repos.SuperficiRepo;
import com.residea.residea.repos.ValutazioneImmobileRepo;
import com.residea.residea.services.ValutazioneImmobileService;

@Service
public class ValutazioneServiceImpl implements ValutazioneImmobileService {

    private final PrezzoPerCapRepo prezzoRepo;
    private final CittaRepo cittaRepo;
    private final ImmobileRepo immobileRepo;
    private final DettagliImmobileRepo dettagliRepo;
    private final SuperficiRepo superficiRepo;
    private final ValutazioneImmobileRepo valutazioneRepo;

    public ValutazioneServiceImpl(PrezzoPerCapRepo prezzoRepo, CittaRepo cittaRepo, ImmobileRepo immobileRepo,
                                  DettagliImmobileRepo dettagliRepo, SuperficiRepo superficiRepo,
                                  ValutazioneImmobileRepo valutazioneRepo) {
        this.prezzoRepo = prezzoRepo;
        this.cittaRepo = cittaRepo;
        this.immobileRepo = immobileRepo;
        this.dettagliRepo = dettagliRepo;
        this.superficiRepo = superficiRepo;
        this.valutazioneRepo = valutazioneRepo;
    }

    @Override
    public ValutazioneResultResponse calculateFromRequest(FormValutazioneRequest req) {
        // 1) Prezzo per CAP
        PrezzoPerCap prezzoPerCapEntity = prezzoRepo.findById(req.getCap()).orElse(null);
        BigDecimal prezzoPerMq;
        boolean hasPrezzoCAP = false;
        
        if (prezzoPerCapEntity != null) {
            prezzoPerMq = prezzoPerCapEntity.getPrezzoMq();
            hasPrezzoCAP = true;
        } else {
            prezzoPerMq = findAveragePrezzoByProvincia(req.getProvincia());
            if (prezzoPerMq == null || prezzoPerMq.compareTo(BigDecimal.ZERO) == 0) {
                prezzoPerMq = BigDecimal.valueOf(1500); // fallback
            }
        }

        // 2) Superfici - FORMULA CORRETTA come da doc
        BigDecimal superficie = safe(req.getSuperficie());
        BigDecimal balcone = safe(req.getSuperficieBalconeTerrazzo());
        BigDecimal giardino = safe(req.getSuperficieGiardino());
        BigDecimal garage = safe(req.getSuperficieGarage());
        BigDecimal cantina = safe(req.getSuperficieCantina());

        // effective_area come da FormulaValutazione.md
        BigDecimal superficieCommerciale = superficie
                .add(balcone.multiply(BigDecimal.valueOf(0.3)))    // 30% balcone
                .add(giardino.multiply(BigDecimal.valueOf(0.5)))   // 50% giardino
                .add(garage.multiply(BigDecimal.valueOf(0.6)))     // 60% garage
                .add(cantina.multiply(BigDecimal.valueOf(0.4)));   // 40% cantina

        // 3) Valore base
        BigDecimal valoreBase = prezzoPerMq.multiply(superficieCommerciale)
                .setScale(0, RoundingMode.HALF_UP);

        // 4) Coefficienti - TUTTI come da FormulaValutazione.md
        Map<String, BigDecimal> coeff = new LinkedHashMap<>();
        
        // A. Type factor (tipologia immobile)
        coeff.put("tipologia", BigDecimal.valueOf(tipologiaCoeff(req.getTipologia())));
        
        // B. Size factor (effetto dimensionale) - comportamento dipende dalla tipologia
        coeff.put("dimensione", BigDecimal.valueOf(dimensioneCoeff(req.getSuperficie(), req.getTipologia())));
        
        // C. Rooms factor (numero stanze, baseline 2)
        if (req.getNStanze() != null) {
            coeff.put("stanze", BigDecimal.valueOf(stanzeCoeff(req.getNStanze())));
        }
        
        // D. Bathrooms factor (numero bagni, baseline 1)
        if (req.getNBagni() != null) {
            coeff.put("bagni", BigDecimal.valueOf(bagniCoeff(req.getNBagni())));
        }
        
        // E. Floor + elevator (piano e ascensore combinati)
        double pianoCoeff = pianoCoeff(req.getPiano(), req.getAscensore(), req.getPianiTotali());
        if (pianoCoeff != 1.0) {
            coeff.put("piano", BigDecimal.valueOf(pianoCoeff));
        }
        
        // F. Garage (presenza)
        if (Boolean.TRUE.equals(req.getGarage())) {
            coeff.put("garage", BigDecimal.valueOf(1.05)); // +5%
        }
        
        // G. Giardino (presenza)
        if (Boolean.TRUE.equals(req.getGiardino())) {
            coeff.put("giardino", BigDecimal.valueOf(1.04)); // +4%
        }
        
        // H. Tipo riscaldamento
        if (req.getTipoRiscaldamento() != null) {
            double heatingCoeff = riscaldamentoCoeff(req.getTipoRiscaldamento());
            if (heatingCoeff != 1.0) {
                coeff.put("riscaldamento", BigDecimal.valueOf(heatingCoeff));
            }
        }
        
        // I. Classe energetica
        if (req.getClasseEnergetica() != null) {
            double energyCoeff = classeCoeff(req.getClasseEnergetica());
            if (energyCoeff != 1.0) {
                coeff.put("classeEnergetica", BigDecimal.valueOf(energyCoeff));
            }
        }
        
        // J. Anno costruzione / età
        if (req.getAnnoCostruzione() != null) {
            double ageCoeff = etaCoeff(req.getAnnoCostruzione());
            if (ageCoeff != 1.0) {
                coeff.put("eta", BigDecimal.valueOf(ageCoeff));
            }
        }
        
        // K. Cantina (presenza)
        if (Boolean.TRUE.equals(req.getCantina())) {
            coeff.put("cantina", BigDecimal.valueOf(1.02)); // +2%
        }
        
        // L. Condizione immobile
        if (req.getCondizione() != null) {
            double condCoeff = condizioneCoeff(req.getCondizione());
            if (condCoeff != 1.0) {
                coeff.put("condizione", BigDecimal.valueOf(condCoeff));
            }
        }

        // Calcola fattore aggiustamento totale
        BigDecimal fattoreAggiustamento = coeff.values().stream()
                .reduce(BigDecimal.ONE, BigDecimal::multiply);

        // 5) Valore medio
        BigDecimal valoreMedioBD = valoreBase.multiply(fattoreAggiustamento)
                .setScale(0, RoundingMode.HALF_UP);
        Integer valoreMedio = valoreMedioBD.intValue();

        // 6) Confidence - come da FormulaValutazione.md
        BigDecimal confidence = calculateConfidence(req, hasPrezzoCAP);
        
        // 7) Uncertainty e range - come da FormulaValutazione.md
        BigDecimal uncertainty = calculateUncertainty(req, hasPrezzoCAP);
        
        Integer valoreMin = valoreMedioBD.multiply(BigDecimal.ONE.subtract(uncertainty))
                .setScale(0, RoundingMode.HALF_UP).intValue();
        Integer valoreMax = valoreMedioBD.multiply(BigDecimal.ONE.add(uncertainty))
                .setScale(0, RoundingMode.HALF_UP).intValue();

        ValutazioneResultResponse response = new ValutazioneResultResponse();
        response.setPrezzoBasePerMq(prezzoPerMq);
        response.setSuperficieCommerciale(superficieCommerciale);
        response.setCoefficientiApplicati(coeff);
        response.setValoreLordo(valoreBase);
        response.setValoreMedio(valoreMedio);
        response.setValoreMin(valoreMin);
        response.setValoreMax(valoreMax);
        response.setConfidence(confidence);
        response.setNote("Valutazione calcolata secondo formula standard");
        
        return response;
    }
    
    // ========== COEFFICIENTI - come da FormulaValutazione.md ==========
    
    /**
     * A. Type factor - tipologia immobile
     */
    private double tipologiaCoeff(String tipo) {
        if (tipo == null) return 1.0;
        return switch (tipo.toUpperCase()) {
            case "APPARTAMENTO", "APARTMENT" -> 1.00;
            case "CASA_INDIPENDENTE", "HOUSE" -> 1.05;
            case "VILLA" -> 1.15;
            case "MONOLOCALE", "STUDIO" -> 0.95;
            default -> 1.0;
        };
    }
    
    /**
     * B. Size factor - effetto dimensionale
     */
    private double dimensioneCoeff(BigDecimal area, String tipologia) {
        if (area == null) return 1.0;
        double areaMq = area.doubleValue();

        // Comportamento diverso per tipologie "house-like" (VILLA / CASA_INDIPENDENTE)
        if (tipologia != null) {
            String t = tipologia.toUpperCase();
            if (t.contains("VILLA") || t.contains("CASA") || t.contains("HOUSE")) {
                // Per le ville/case indipendenti tagli grandi possono avere un premio
                if (areaMq < 80) return 1.00;   // baseline per tagli piccoli/medi
                if (areaMq < 150) return 1.03;  // +3% per taglio ampio
                return 1.08;                   // +8% per metrature molto grandi
            }
        }

        // Default per appartamenti: piccoli premium, grandi discount
        if (areaMq < 40) return 1.05;      // +5% (più costoso per m²)
        if (areaMq < 80) return 1.00;      // baseline
        if (areaMq < 150) return 0.98;     // -2%
        return 0.95;                        // -5%
    }
    
    /**
     * C. Rooms factor - numero stanze (baseline 2)
     */
    private double stanzeCoeff(Integer stanze) {
        if (stanze == null) return 1.0;
        // +3% per stanza oltre 2, -2% per stanza mancante
        double factor = 1.0 + (stanze - 2) * 0.03;
        // Clamp tra 0.9 e 1.3
        return Math.max(0.9, Math.min(1.3, factor));
    }
    
    /**
     * D. Bathrooms factor - numero bagni (baseline 1)
     */
    private double bagniCoeff(Integer bagni) {
        if (bagni == null) return 1.0;
        if (bagni == 0) return 0.95; // -5%
        // +2% per bagno aggiuntivo oltre 1
        return 1.0 + (bagni - 1) * 0.02;
    }
    
    /**
     * E. Piano + ascensore combinati
     */
    private double pianoCoeff(Integer piano, Boolean ascensore, Integer pianiTotali) {
        if (piano == null) return 1.0;
        
        boolean hasElevator = Boolean.TRUE.equals(ascensore);
        
        // Piano terra
        if (piano == 0) return 0.97; // -3%
        
        // Primo piano (baseline)
        if (piano == 1) return 1.00;
        
        // Attico/top floor (bonus vista)
        if (pianiTotali != null && piano.equals(pianiTotali) && piano >= 3) {
            return 1.02; // +2% attico
        }
        
        // Piani intermedi (2-4)
        if (piano >= 2 && piano <= 4) {
            double factor = 1.00 + 0.01 * (piano - 1); // +1% per piano sopra il 1°
            // Se ascensore presente e piano > 1: aggiungi +3%
            if (hasElevator && piano > 1) {
                factor *= 1.03;
            }
            return factor;
        }
        
        // Piani alti (>4)
        if (hasElevator) {
            return 1.05; // +5% con ascensore
        } else {
            return 0.95; // -5% senza ascensore (penalità)
        }
    }
    
    /**
     * H. Tipo riscaldamento
     */
    private double riscaldamentoCoeff(String tipo) {
        if (tipo == null) return 1.0;
        return switch (tipo.toUpperCase()) {
            case "POMPE_DI_CALORE", "POMPE DI CALORE", "PAVIMENTO" -> 1.03; // +3%
            case "AUTONOMO" -> 1.02;                                         // +2%
            case "CONDOMINIALE" -> 1.01;                                     // +1%
            case "NO", "NESSUNO", "OBSOLETO" -> 0.95;                       // -5%
            default -> 1.0;
        };
    }
    
    /**
     * I. Classe energetica - scala relativa a G baseline
     */
    private double classeCoeff(String classe) {
        if (classe == null) return 1.0;
        return switch (classe.toUpperCase()) {
            case "A_PLUS", "A+" -> 1.08;  // +8%
            case "A" -> 1.06;              // +6%
            case "B" -> 1.04;              // +4%
            case "C" -> 1.02;              // +2%
            case "D" -> 1.00;              // baseline
            case "E" -> 0.98;              // -2%
            case "F" -> 0.96;              // -4%
            case "G" -> 0.96;              // -4%
            default -> 1.0;
        };
    }
    
    /**
     * J. Anno costruzione / età edificio
     */
    private double etaCoeff(Integer annoCostruzione) {
        if (annoCostruzione == null) return 1.0;
        
        int currentYear = java.time.Year.now().getValue();
        int age = currentYear - annoCostruzione;
        
        if (age <= 10) return 1.03;       // +3% (nuovo)
        if (age <= 30) return 1.01;       // +1% (buono)
        if (age <= 50) return 1.00;       // baseline
        return 0.95;                       // -5% (vecchio, se non ristrutturato)
    }
    
    /**
     * L. Condizione immobile (può compensare età)
     */
    private double condizioneCoeff(String cond) {
        if (cond == null) return 1.0;
        return switch (cond.toUpperCase()) {
            case "NUOVO" -> 1.05;                         // +5%
            case "RISTRUTTURATO" -> 1.05;                 // +5% (equivale a ristrutturazione recente)
            case "PARZIALMENTE_RISTRUTTURATO" -> 1.02;    // +2%
            case "NON_RISTRUTTURATO" -> 1.00;             // baseline
            default -> 1.0;
        };
    }
    
    /**
     * Calcola confidence - come da FormulaValutazione.md sezione 6
     */
    private BigDecimal calculateConfidence(FormValutazioneRequest req, boolean hasPrezzoCAP) {
        double confidence = 0.30; // baseline CAP-only
        
        // +0.10 se PrezzoPerCAP disponibile (non fallback)
        if (hasPrezzoCAP) {
            confidence += 0.10;
        }
        
        // +0.05 se anno costruzione fornito
        if (req.getAnnoCostruzione() != null) {
            confidence += 0.05;
        }
        
        // Bonus aggiuntivi per completezza dati (non specificati nel doc, ma ragionevoli)
        if (req.getClasseEnergetica() != null && !req.getClasseEnergetica().isEmpty()) {
            confidence += 0.05;
        }
        
        if (req.getCondizione() != null && !req.getCondizione().isEmpty()) {
            confidence += 0.05;
        }
        
        // Clamp tra 0.05 e 0.95
        confidence = Math.max(0.05, Math.min(0.95, confidence));
        
        return BigDecimal.valueOf(confidence).setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Calcola uncertainty - come da FormulaValutazione.md sezione 4
     */
    private BigDecimal calculateUncertainty(FormValutazioneRequest req, boolean hasPrezzoCAP) {
        double uncertainty = 0.08; // default base con prezzo recente e dati completi
        
        // 0.12 se prezzo > 12 mesi (qui assumiamo sempre recente se disponibile)
        // 0.20 se mancano molti campi
        int campiMancanti = 0;
        
        if (req.getClasseEnergetica() == null || req.getClasseEnergetica().isEmpty()) campiMancanti++;
        if (req.getAnnoCostruzione() == null) campiMancanti++;
        if (req.getGarage() == null && req.getGiardino() == null) campiMancanti++;
        if (req.getCondizione() == null || req.getCondizione().isEmpty()) campiMancanti++;
        if (!hasPrezzoCAP) campiMancanti += 2; // peso maggiore se manca prezzo CAP
        
        if (campiMancanti >= 4) {
            uncertainty = 0.20;
        } else if (campiMancanti >= 2) {
            uncertainty = 0.12;
        }
        
        return BigDecimal.valueOf(uncertainty);
    }

    @Override
    public ValutazioneResultResponse calculateFromImmobileId(Integer idImmobile) {
        Immobile imm = immobileRepo.findById(idImmobile).orElseThrow(() -> new IllegalArgumentException("Immobile non trovato"));
        DettagliImmobile det = dettagliRepo.findById(idImmobile).orElse(null);
        Superficie sup = superficiRepo.findById(idImmobile).orElse(null);

        FormValutazioneRequest req = new FormValutazioneRequest();
        req.setCap(imm.getCap());
        req.setProvincia(imm.getProvincia());
        req.setTipologia(imm.getTipologia() != null ? imm.getTipologia().name() : null);
        
        if (sup != null) {
            req.setSuperficie(sup.getSuperficieMq());
            req.setSuperficieBalconeTerrazzo(sup.getSuperficieBalconeTerrazzo());
            req.setSuperficieGarage(sup.getSuperficieGarage());
            req.setSuperficieCantina(sup.getSuperficieCantina());
            req.setSuperficieGiardino(sup.getSuperficieGiardino());
        }
        
        if (det != null) {
            req.setCondizione(det.getCondizioneImmobile() != null ? det.getCondizioneImmobile().name() : null);
            req.setClasseEnergetica(det.getClasseEnergetica() != null ? det.getClasseEnergetica().name() : null);
            req.setPiano(det.getNPiano());
            req.setPianiTotali(det.getNPianiImmobile());
            req.setGarage(det.isGarage());
            req.setAscensore(det.isAscensore());
            req.setGiardino(det.isGiardino());
            req.setCantina(det.isCantina());
            req.setAnnoCostruzione(det.getAnnoCostruzione());
            req.setNStanze(det.getNStanze());
            req.setNBagni(det.getNBagni());
            req.setTipoRiscaldamento(det.getTipoRiscaldamento() != null ? det.getTipoRiscaldamento().name() : null);
        }

        ValutazioneResultResponse result = calculateFromRequest(req);
        result.setIdImmobile(idImmobile);
        return result;
    }

    // --- helper ---
    private BigDecimal safe(BigDecimal v) { return v == null ? BigDecimal.ZERO : v; }

    private BigDecimal findAveragePrezzoByProvincia(String provincia) {
        if (provincia == null) return BigDecimal.ZERO;
        List<com.residea.residea.entities.Citta> cities = cittaRepo.findByProvinciaIgnoreCase(provincia);
        BigDecimal sum = BigDecimal.ZERO;
        int count = 0;
        for (var c : cities) {
            List<PrezzoPerCap> prezzi = prezzoRepo.findByCitta(c);
            for (var p : prezzi) {
                if (p.getPrezzoMq() != null) {
                    sum = sum.add(p.getPrezzoMq());
                    count++;
                }
            }
        }
        return count == 0 ? BigDecimal.ZERO : sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
    }

    // --- CALCOLO E SALVATAGGIO ---
    @Override
    @Transactional
    public ValutazioneResultResponse calculateAndSave(FormValutazioneRequest req, Integer idImmobile) {
        ValutazioneResultResponse result = calculateFromRequest(req);
        
        Immobile immobile = immobileRepo.findById(idImmobile)
                .orElseThrow(() -> new IllegalArgumentException("Immobile non trovato con id: " + idImmobile));
        
        // Calcola fattore aggiustamento (prodotto di tutti i coefficienti)
        BigDecimal fattoreAggiustamento = result.getCoefficientiApplicati().values().stream()
                .reduce(BigDecimal.ONE, BigDecimal::multiply);
        
        // Crea entity
        ValutazioneImmobile valutazione = new ValutazioneImmobile();
        valutazione.setImmobile(immobile);
        valutazione.setValoreBase(result.getValoreLordo().intValue());
        valutazione.setFattoreAggiustamento(fattoreAggiustamento);
        valutazione.setValoreMedio(result.getValoreMedio());
        valutazione.setValoreMin(result.getValoreMin());
        valutazione.setValoreMax(result.getValoreMax());
        valutazione.setConfidence(result.getConfidence());
        
        valutazioneRepo.save(valutazione);
        
        // Aggiungi idImmobile alla response
        result.setIdImmobile(idImmobile);
        
        return result;
    }

    // --- CRUD VALUTAZIONI PERSISTITE ---
    @Override
    public List<ValutazioneImmobile> getAllValutazioni() {
        return valutazioneRepo.findAll();
    }

    @Override
    public ValutazioneImmobile getValutazioneById(Integer idValutazione) {
        return valutazioneRepo.findById(idValutazione)
                .orElseThrow(() -> new IllegalArgumentException("Valutazione non trovata con id: " + idValutazione));
    }

    @Override
    public ValutazioneImmobile getValutazioneByIdImmobile(Integer idImmobile) {
        return valutazioneRepo.findByIdImmobile(idImmobile)
                .orElseThrow(() -> new IllegalArgumentException("Nessuna valutazione per immobile: " + idImmobile));
    }

    @Override
    public List<ValutazioneImmobile> getValutazioniByConfidenceMaggioreDi(BigDecimal minConfidence) {
        return valutazioneRepo.findByConfidenceGreaterThan(minConfidence);
    }

    @Override
    public List<ValutazioneImmobile> getValutazioniByValoreMedioCompreso(Integer valoreMin, Integer valoreMax) {
        return valutazioneRepo.findByValoreMedioBetween(valoreMin, valoreMax);
    }

    @Override
    public List<ValutazioneImmobile> getValutazioniByFattoreAggiustamentoMaggioreDi(BigDecimal soglia) {
        return valutazioneRepo.findByFattoreAggiustamentoGreaterThan(soglia);
    }

    @Override
    @Transactional
    public ValutazioneImmobile salvaValutazione(ValutazioneImmobile valutazione) {
        return valutazioneRepo.save(valutazione);
    }

    @Override
    @Transactional
    public ValutazioneImmobile aggiornaValutazione(ValutazioneImmobile valutazioneAggiornata) {
        if (valutazioneAggiornata.getIdValutazione() == null || 
            !valutazioneRepo.existsById(valutazioneAggiornata.getIdValutazione())) {
            throw new IllegalArgumentException("Valutazione non esistente");
        }
        return valutazioneRepo.save(valutazioneAggiornata);
    }

    @Override
    @Transactional
    public ValutazioneImmobile aggiornaValoreMedio(Integer idValutazione, Integer nuovoValoreMedio) {
        ValutazioneImmobile valutazione = getValutazioneById(idValutazione);
        valutazione.setValoreMedio(nuovoValoreMedio);
        return valutazioneRepo.save(valutazione);
    }

    @Override
    @Transactional
    public ValutazioneImmobile aggiornaConfidence(Integer idValutazione, BigDecimal nuovaConfidence) {
        ValutazioneImmobile valutazione = getValutazioneById(idValutazione);
        valutazione.setConfidence(nuovaConfidence);
        return valutazioneRepo.save(valutazione);
    }

    @Override
    @Transactional
    public void eliminaValutazione(Integer idValutazione) {
        if (!valutazioneRepo.existsById(idValutazione)) {
            throw new IllegalArgumentException("Valutazione non trovata con id: " + idValutazione);
        }
        valutazioneRepo.deleteById(idValutazione);
    }

    @Override
    @Transactional
    public void eliminaValutazioniByImmobile(Integer idImmobile) {
        Immobile immobile = immobileRepo.findById(idImmobile)
                .orElseThrow(() -> new IllegalArgumentException("Immobile non trovato"));
        valutazioneRepo.deleteByImmobile(immobile);
    }
}
