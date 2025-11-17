package com.residea.residea.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@ToString(exclude = "idImmobile") // Evita lazy loading issues
public class Contratto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idContratto")
    private Integer idContratto;

    @ManyToOne
    @JoinColumn(name = "idImmobile", nullable = false)
    private Immobile idImmobile;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TipoContratto tipoContratto;

    private LocalDate dataContratto;
    private LocalDate dataScadenzaContratto;

    @Column(length = 255)
    private String pathContrattoPDF;

    // --- ENUM per tipoContratto ---
    public enum TipoContratto {
        ESCLUSIVO,
        ALTRO
    }
}