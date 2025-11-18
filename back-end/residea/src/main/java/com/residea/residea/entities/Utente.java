package com.residea.residea.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Utente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "passwordHash") // Non loggiamo mai la password
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUtente")
    private Integer idUtente;

    @Column(nullable = false, length = 20)
    private String nome;

    @Column(nullable = false, length = 20)
    private String cognome;

    @Column(length = 10)
    private String telefono;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "passwordHash", nullable = true, length = 255)
    private String passwordHash;

    @Column(name = "ruolo", nullable = false, length = 20)
    @lombok.Builder.Default
    private Ruolo ruolo = Ruolo.PROPRIETARIO;

    @Column(name = "verifica_email", nullable = false)
    @lombok.Builder.Default
    private boolean verificaEmail = false;

    @Column(name = "consenso_privacy", nullable = false)
    @lombok.Builder.Default
    private boolean consensoPrivacy = false;

    // Custom setter per normalizzare ruolo (override Lombok)
    public void setRuolo(Ruolo ruolo) {
        if (ruolo != null) {
            this.ruolo = Ruolo.valueOf(ruolo.name().toUpperCase());
        }
    }

    // Setter da stringa JSON (utile per deserializzazione)
    public void setRuolo(String ruolo) {
        if (ruolo != null && !ruolo.isBlank()) {
            this.ruolo = Ruolo.valueOf(ruolo.trim().toUpperCase());
        }
    }

    // Normalizza ruolo prima di salvare o aggiornare
    @PrePersist
    @PreUpdate
    private void normalizeRuolo() {
        if (this.ruolo != null) {
            this.ruolo = Ruolo.valueOf(this.ruolo.name().toUpperCase());
        }
    }

    @PostLoad
    private void normalizeRuoloAfterLoad() {
        if (this.ruolo != null) {
            this.ruolo = Ruolo.valueOf(this.ruolo.name().toUpperCase());
        }
    }

    // --- ENUM interno ---
public enum Ruolo {
    PROPRIETARIO, AGENTE, AMMINISTRATORE;

    @com.fasterxml.jackson.annotation.JsonCreator
    public static Ruolo fromString(String key) {
        if (key == null) return null;
        try {
            return Ruolo.valueOf(key.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null; // oppure lanciare IllegalArgumentException personalizzata
        }
    }

    @com.fasterxml.jackson.annotation.JsonValue
    public String toValue() {
        return this.name();
    }
}
}
