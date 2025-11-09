package com.residea.residea.entities.converters;

import com.residea.residea.entities.DettagliImmobile;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ClasseEnergeticaConverter implements AttributeConverter<DettagliImmobile.ClasseEnergetica, String> {
    @Override
    public String convertToDatabaseColumn(DettagliImmobile.ClasseEnergetica attribute) {
        if (attribute == null) return null;
        return switch (attribute) {
            case A_PLUS -> "A+";
            case A -> "A";
            case B -> "B";
            case C -> "C";
            case D -> "D";
            case E -> "E";
            case F -> "F";
            case G -> "G";
        };
    }

    @Override
    public DettagliImmobile.ClasseEnergetica convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return switch (dbData) {
            case "A+" -> DettagliImmobile.ClasseEnergetica.A_PLUS;
            case "A" -> DettagliImmobile.ClasseEnergetica.A;
            case "B" -> DettagliImmobile.ClasseEnergetica.B;
            case "C" -> DettagliImmobile.ClasseEnergetica.C;
            case "D" -> DettagliImmobile.ClasseEnergetica.D;
            case "E" -> DettagliImmobile.ClasseEnergetica.E;
            case "F" -> DettagliImmobile.ClasseEnergetica.F;
            case "G" -> DettagliImmobile.ClasseEnergetica.G;
            default -> null;
        };
    }
}
