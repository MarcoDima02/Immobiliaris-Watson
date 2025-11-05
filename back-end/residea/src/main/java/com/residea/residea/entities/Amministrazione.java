package com.residea.residea.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "amministrazione")
public class Amministrazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String nome;

    @Column(length = 100)
    private String cognome;

    @Column(length = 20)
    private String telefono;

    @Column(length = 150, nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", length = 255, nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Ruolo ruolo = Ruolo.AGENTE;

    // --- COSTRUTTORI ---
    public Amministrazione() {}

    public Amministrazione(String nome, String cognome, String telefono, String email, String passwordHash, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
        this.passwordHash = passwordHash;
        this.ruolo = ruolo;
    }

    // --- GETTER & SETTER ---
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    @Override
    public String toString() {
        return "Amministrazione{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", ruolo=" + ruolo +
                '}';
    }

    // --- ENUM per il ruolo ---
    public enum Ruolo {
        AGENTE,
        AMMINISTRATORE
    }
}