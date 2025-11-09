package com.residea.residea.entities.converters;

import com.residea.residea.entities.DettagliImmobile;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class CondizioneImmobileConverter implements AttributeConverter<DettagliImmobile.CondizioneImmobile, String> {
    @Override
    public String convertToDatabaseColumn(DettagliImmobile.CondizioneImmobile attribute) {
        if (attribute == null) return null;
        return switch (attribute) {
            case NUOVO -> "Nuovo";
            case RISTRUTTURATO -> "Ristrutturato";
            case PARZIALMENTE_RISTRUTTURATO -> "Parzialmente ristrutturato";
            case NON_RISTRUTTURATO -> "Non ristrutturato";
        };
    }

    @Override
    public DettagliImmobile.CondizioneImmobile convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return switch (dbData) {
            case "Nuovo" -> DettagliImmobile.CondizioneImmobile.NUOVO;
            case "Ristrutturato" -> DettagliImmobile.CondizioneImmobile.RISTRUTTURATO;
            case "Parzialmente ristrutturato" -> DettagliImmobile.CondizioneImmobile.PARZIALMENTE_RISTRUTTURATO;
            case "Non ristrutturato" -> DettagliImmobile.CondizioneImmobile.NON_RISTRUTTURATO;
            default -> null;
        };
    }
}
