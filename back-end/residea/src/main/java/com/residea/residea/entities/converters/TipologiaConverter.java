package com.residea.residea.entities.converters;

import com.residea.residea.entities.Immobile;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipologiaConverter implements AttributeConverter<Immobile.Tipologia, String> {

    @Override
    public String convertToDatabaseColumn(Immobile.Tipologia attribute) {
        if (attribute == null) return null;
        return switch (attribute) {
            case APPARTAMENTO -> "Appartamento";
            case VILLA -> "Villa";
            case CASA_INDIPENDENTE -> "Casa indipendente";
            case MONOLOCALE -> "Monolocale";
        };
    }

    @Override
    public Immobile.Tipologia convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return switch (dbData) {
            case "Appartamento" -> Immobile.Tipologia.APPARTAMENTO;
            case "Villa" -> Immobile.Tipologia.VILLA;
            case "Casa indipendente" -> Immobile.Tipologia.CASA_INDIPENDENTE;
            case "Monolocale" -> Immobile.Tipologia.MONOLOCALE;
            default -> Immobile.Tipologia.APPARTAMENTO;
        };
    }
}
