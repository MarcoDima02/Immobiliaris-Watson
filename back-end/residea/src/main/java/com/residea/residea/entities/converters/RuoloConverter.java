package com.residea.residea.entities.converters;

import com.residea.residea.entities.Utente;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RuoloConverter implements AttributeConverter<Utente.Ruolo, String> {
    @Override
    public String convertToDatabaseColumn(Utente.Ruolo attribute) {
        if (attribute == null) return "Proprietario";
        return switch (attribute) {
            case PROPRIETARIO -> "Proprietario";
            case AGENTE -> "Agente";
            case AMMINISTRATORE -> "Amministratore";
        };
    }

    @Override
    public Utente.Ruolo convertToEntityAttribute(String dbData) {
        if (dbData == null) return Utente.Ruolo.PROPRIETARIO;
        String norm = dbData.trim().toLowerCase();
        return switch (norm) {
            case "proprietario" -> Utente.Ruolo.PROPRIETARIO;
            case "agente" -> Utente.Ruolo.AGENTE;
            case "amministratore" -> Utente.Ruolo.AMMINISTRATORE;
            default -> Utente.Ruolo.PROPRIETARIO;
        };
    }

    
}
