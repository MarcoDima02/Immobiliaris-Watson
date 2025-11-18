package com.residea.residea.utils;

import com.residea.residea.entities.DettagliImmobile;
import com.residea.residea.entities.Immobile;

/**
 * Utility class per mappare valori stringa da frontend agli enum del backend.
 * Centralizza la logica di conversione per evitare duplicazione nei controller.
 */
public class EnumMapper {

    private EnumMapper() {
        // Utility class, non istanziabile
    }

    /**
     * Mappa la stringa della tipologia immobile all'enum corrispondente.
     * @param tipologia stringa da frontend (es. "APPARTAMENTO", "VILLA")
     * @return enum Tipologia, default APPARTAMENTO se non riconosciuto
     */
    public static Immobile.Tipologia mapTipologia(String tipologia) {
        if (tipologia == null || tipologia.isEmpty()) {
            return Immobile.Tipologia.APPARTAMENTO;
        }
        
        try {
            return Immobile.Tipologia.valueOf(
                tipologia.toUpperCase().replace(' ', '_')
            );
        } catch (IllegalArgumentException e) {
            return Immobile.Tipologia.APPARTAMENTO;
        }
    }

    /**
     * Mappa la stringa della condizione immobile all'enum corrispondente.
     * Supporta sia valori inglesi che italiani.
     * @param condizione stringa da frontend
     * @return enum CondizioneImmobile, default NUOVO se non riconosciuto
     */
    public static DettagliImmobile.CondizioneImmobile mapCondizione(String condizione) {
        if (condizione == null || condizione.isEmpty()) {
            return DettagliImmobile.CondizioneImmobile.NUOVO;
        }

        String normalized = condizione.toLowerCase().trim();
        
        return switch (normalized) {
            case "new", "nuovo" -> DettagliImmobile.CondizioneImmobile.NUOVO;
            case "renovated", "ristrutturato" -> DettagliImmobile.CondizioneImmobile.RISTRUTTURATO;
            case "partially_renovated", "parzialmente ristrutturato", "parzialmente_ristrutturato" -> 
                DettagliImmobile.CondizioneImmobile.PARZIALMENTE_RISTRUTTURATO;
            case "to_renovate", "da ristrutturare", "non ristrutturato", "non_ristrutturato" -> 
                DettagliImmobile.CondizioneImmobile.NON_RISTRUTTURATO;
            default -> {
                // Try valueOf as fallback
                try {
                    yield DettagliImmobile.CondizioneImmobile.valueOf(
                        normalized.toUpperCase().replace(' ', '_')
                    );
                } catch (IllegalArgumentException e) {
                    yield DettagliImmobile.CondizioneImmobile.NUOVO;
                }
            }
        };
    }

    /**
     * Mappa la stringa del tipo riscaldamento all'enum corrispondente.
     * Supporta sia valori inglesi che italiani.
     * @param tipoRiscaldamento stringa da frontend
     * @return enum TipoRiscaldamento, default NO se non riconosciuto
     */
    public static DettagliImmobile.TipoRiscaldamento mapTipoRiscaldamento(String tipoRiscaldamento) {
        if (tipoRiscaldamento == null || tipoRiscaldamento.isEmpty()) {
            return DettagliImmobile.TipoRiscaldamento.NO;
        }

        String normalized = tipoRiscaldamento.toLowerCase().trim();
        
        return switch (normalized) {
            case "autonomous", "autonomo" -> DettagliImmobile.TipoRiscaldamento.AUTONOMO;
            case "centralized", "condominiale" -> DettagliImmobile.TipoRiscaldamento.CONDOMINIALE;
            case "heat_pump", "pompe di calore", "pompe_di_calore" -> 
                DettagliImmobile.TipoRiscaldamento.POMPE_DI_CALORE;
            case "floor", "pavimento" -> DettagliImmobile.TipoRiscaldamento.PAVIMENTO;
            case "no", "none" -> DettagliImmobile.TipoRiscaldamento.NO;
            default -> {
                // Try valueOf as fallback
                try {
                    yield DettagliImmobile.TipoRiscaldamento.valueOf(
                        normalized.toUpperCase().replace(' ', '_')
                    );
                } catch (IllegalArgumentException e) {
                    yield DettagliImmobile.TipoRiscaldamento.NO;
                }
            }
        };
    }

    /**
     * Mappa la stringa della classe energetica all'enum corrispondente.
     * @param classeEnergetica stringa da frontend (es. "A+", "B")
     * @return enum ClasseEnergetica, null se non riconosciuto
     */
    public static DettagliImmobile.ClasseEnergetica mapClasseEnergetica(String classeEnergetica) {
        if (classeEnergetica == null || classeEnergetica.isEmpty()) {
            return null;
        }

        try {
            // Gestisce il caso speciale A+ -> A_PLUS
            String normalized = classeEnergetica.replace("+", "_PLUS");
            return DettagliImmobile.ClasseEnergetica.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Valida e normalizza l'anno di costruzione per il tipo YEAR di MySQL (1901-2155).
     * @param anno anno di costruzione da validare
     * @return anno normalizzato, null se input è null
     */
    public static Integer normalizeAnnoCostruzione(Integer anno) {
        if (anno == null) {
            return null;
        }
        
        if (anno < 1901) return 1901;
        if (anno > 2155) return 2155;
        
        return anno;
    }
}
