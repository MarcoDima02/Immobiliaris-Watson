package com.residea.residea.entities.converters;

import com.residea.residea.entities.Richiesta;
import com.residea.residea.entities.Richiesta.Stato;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RichiestaStatoConverter implements AttributeConverter<Stato, String> {

    @Override
    public String convertToDatabaseColumn(Stato attribute) {
        if (attribute == null) return null;
        // Salva nel DB la stringa user-friendly
        return attribute.getDisplayValue();
    }

    @Override
    public Stato convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        // Confronto case-insensitive
        return switch (dbData.toLowerCase()) {
            case "in attesa" -> Stato.IN_ATTESA;
            case "in elaborazione" -> Stato.IN_ELABORAZIONE;
            case "completata" -> Stato.COMPLETATA;
            case "annullata" -> Stato.ANNULLATA;
            default -> throw new IllegalArgumentException("Valore non valido per Stato Richiesta: " + dbData);
        };
    }
}
