package com.residea.residea.entities.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.residea.residea.entities.Utente;

@Converter(autoApply = true)
public class RuoloConverter implements AttributeConverter<Utente.Ruolo, String> {

    @Override
    public String convertToDatabaseColumn(Utente.Ruolo ruolo) {
        return ruolo == null ? null : ruolo.name();
    }

    @Override
    public Utente.Ruolo convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            return Utente.Ruolo.valueOf(dbData.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null; // o lanciare PersistenceException se preferisci
        }
    }
}