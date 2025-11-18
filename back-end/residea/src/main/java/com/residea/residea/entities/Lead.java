package com.residea.residea.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Leads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "utente") // Evita lazy loading issues
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLead")
    private Integer idLead;

    @ManyToOne
    @JoinColumn(name = "idUtente", referencedColumnName = "idUtente")
    private Utente utente;

    @Column(length = 100)
    private String nome;

    @Column(length = 150)
    private String email;

    @Column(length = 20)
    private String telefono;

    @Column(length = 100)
    private String citta;

    @Column(length = 100)
    private String fonte;

    @Column(length = 100)
    private String campagna;

    @Column(length = 100)
    private String utmSource;

    @Column(length = 100)
    private String utmMedium;

    @Column(length = 100)
    private String utmCampaign;

    @Column(nullable = false)
    private boolean convertitoInRichiesta = false;

    private Integer idRichiesta;
    private Integer assegnatoA;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // --- CALLBACK AUTOMATICO PER UPDATE ---
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}