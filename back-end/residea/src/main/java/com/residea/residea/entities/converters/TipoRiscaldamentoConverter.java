package com.residea.residea.entities.converters;

import com.residea.residea.entities.DettagliImmobile;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoRiscaldamentoConverter implements AttributeConverter<DettagliImmobile.TipoRiscaldamento, String> {
    @Override
    public String convertToDatabaseColumn(DettagliImmobile.TipoRiscaldamento attribute) {
        if (attribute == null) return null;
        return switch (attribute) {
            case NO -> "No";
            case AUTONOMO -> "Autonomo";
            case CONDOMINIALE -> "Condominiale";
            case POMPE_DI_CALORE -> "Pompe di calore";
            case PAVIMENTO -> "Pavimento";
        };
    }

    @Override
    public DettagliImmobile.TipoRiscaldamento convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return switch (dbData) {
            case "No" -> DettagliImmobile.TipoRiscaldamento.NO;
            case "Autonomo" -> DettagliImmobile.TipoRiscaldamento.AUTONOMO;
            case "Condominiale" -> DettagliImmobile.TipoRiscaldamento.CONDOMINIALE;
            case "Pompe di calore" -> DettagliImmobile.TipoRiscaldamento.POMPE_DI_CALORE;
            case "Pavimento" -> DettagliImmobile.TipoRiscaldamento.PAVIMENTO;
            default -> DettagliImmobile.TipoRiscaldamento.NO;
        };
    }
}
