package com.residea.residea.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.residea.residea.entities.converters.TipoContrattoConverter;


import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Contratti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "idImmobile")

public class Contratto {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "idContratto")
private Integer idContratto;

@ManyToOne
@JoinColumn(name = "idImmobile", nullable = false)
@JsonIgnoreProperties("contratti")
private Immobile idImmobile;

@Convert(converter = TipoContrattoConverter.class)
@Column(length = 50)
private TipoContratto tipoContratto;


private LocalDate dataContratto;
private LocalDate dataScadenzaContratto;

@Column(length = 255)
private String pathContrattoPDF;

// Normalizza tipoContratto prima di salvare o aggiornare
@PrePersist
@PreUpdate
private void normalizeTipoContratto() {
    if (this.tipoContratto != null) {
        this.tipoContratto = TipoContratto.valueOf(this.tipoContratto.name().toUpperCase());
    }
}

@PostLoad
private void normalizeTipoContrattoAfterLoad() {
    if (this.tipoContratto != null) {
        this.tipoContratto = TipoContratto.valueOf(this.tipoContratto.name().toUpperCase());
    }
}

// --- ENUM TipoContratto con gestione JSON ---
public enum TipoContratto {
    AFFITTO,
    VENDITA,
    COMODATO,
    ESCLUSIVO;

    @com.fasterxml.jackson.annotation.JsonCreator
    public static TipoContratto fromString(String key) {
        if (key == null) return null;
        try {
            return TipoContratto.valueOf(key.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    @com.fasterxml.jackson.annotation.JsonValue
    public String toValue() {
        return this.name();
    }
}

}
