package com.residea.residea.entities.converters;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.residea.residea.entities.Contratto;

@Converter(autoApply = true)
public class TipoContrattoConverter implements AttributeConverter<Contratto.TipoContratto, String> {

    @Override
    public String convertToDatabaseColumn(Contratto.TipoContratto attribute) {
        return attribute == null ? null : attribute.name().toUpperCase();
    }

    @Override
    public Contratto.TipoContratto convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            return Contratto.TipoContratto.valueOf(dbData.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
