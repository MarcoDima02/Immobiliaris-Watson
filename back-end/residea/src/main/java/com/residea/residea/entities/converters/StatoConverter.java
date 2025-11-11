package com.residea.residea.entities.converters;

import com.residea.residea.entities.Immobile;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StatoConverter implements AttributeConverter<Immobile.Stato, String> {
    @Override
    public String convertToDatabaseColumn(Immobile.Stato attribute) {
        if (attribute == null) return null;
        return switch (attribute) {
            case DISPONIBILE -> "Disponibile";
            case VENDUTO -> "Venduto";
        };
    }

    @Override
    public Immobile.Stato convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return switch (dbData) {
            case "Disponibile" -> Immobile.Stato.DISPONIBILE;
            case "Venduto" -> Immobile.Stato.VENDUTO;
            default -> Immobile.Stato.DISPONIBILE;
        };
    }
}
