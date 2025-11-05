package com.residea.residea.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "leads")
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

    // --- COSTRUTTORI ---
    public Lead() {}

    public Lead(Utente utente, String nome, String email, String telefono, String citta, String fonte,
                String campagna, String utmSource, String utmMedium, String utmCampaign,
                boolean convertitoInRichiesta, Integer idRichiesta, Integer assegnatoA, String note) {
        this.utente = utente;
        this.nome = nome;
        this.email = email;
        this.telefono = telefono;
        this.citta = citta;
        this.fonte = fonte;
        this.campagna = campagna;
        this.utmSource = utmSource;
        this.utmMedium = utmMedium;
        this.utmCampaign = utmCampaign;
        this.convertitoInRichiesta = convertitoInRichiesta;
        this.idRichiesta = idRichiesta;
        this.assegnatoA = assegnatoA;
        this.note = note;
    }

    // --- GETTER & SETTER ---
    public Integer getIdLead() {
        return idLead;
    }

    public void setIdLead(Integer idLead) {
        this.idLead = idLead;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getCampagna() {
        return campagna;
    }

    public void setCampagna(String campagna) {
        this.campagna = campagna;
    }

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public String getUtmMedium() {
        return utmMedium;
    }

    public void setUtmMedium(String utmMedium) {
        this.utmMedium = utmMedium;
    }

    public String getUtmCampaign() {
        return utmCampaign;
    }

    public void setUtmCampaign(String utmCampaign) {
        this.utmCampaign = utmCampaign;
    }

    public boolean isConvertitoInRichiesta() {
        return convertitoInRichiesta;
    }

    public void setConvertitoInRichiesta(boolean convertitoInRichiesta) {
        this.convertitoInRichiesta = convertitoInRichiesta;
    }

    public Integer getIdRichiesta() {
        return idRichiesta;
    }

    public void setIdRichiesta(Integer idRichiesta) {
        this.idRichiesta = idRichiesta;
    }

    public Integer getAssegnatoA() {
        return assegnatoA;
    }

    public void setAssegnatoA(Integer assegnatoA) {
        this.assegnatoA = assegnatoA;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // --- CALLBACK AUTOMATICO PER UPDATE ---
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Lead{" +
                "idLead=" + idLead +
                ", utente=" + (utente != null ? utente.getIdUtente() : null) +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", citta='" + citta + '\'' +
                ", fonte='" + fonte + '\'' +
                ", campagna='" + campagna + '\'' +
                ", utmSource='" + utmSource + '\'' +
                ", utmMedium='" + utmMedium + '\'' +
                ", utmCampaign='" + utmCampaign + '\'' +
                ", convertitoInRichiesta=" + convertitoInRichiesta +
                ", idRichiesta=" + idRichiesta +
                ", assegnatoA=" + assegnatoA +
                ", note='" + note + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}