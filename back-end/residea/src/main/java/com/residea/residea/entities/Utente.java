package com.residea.residea.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "utenti")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUtente")
    private Integer idUtente;

    @Column(nullable = false, length = 20)
    private String nome;

    @Column(nullable = false, length = 20)
    private String cognome;

    @Column(length = 9)
    private String telefono;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "passwordHash", nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "ruolo", nullable = false, length = 20)
    private Ruolo ruolo = Ruolo.PROPRIETARIO;

    @Column(name = "verifica_email", nullable = false)
    private boolean verificaEmail = false;

    @Column(name = "consenso_privacy", nullable = false)
    private boolean consensoPrivacy = false;


    public Utente(String nome, String cognome, String telefono, String email, String passwordHash, Ruolo ruolo, boolean verificaEmail, boolean consensoPrivacy) {
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
        this.passwordHash = passwordHash;
        this.ruolo = ruolo;
        this.verificaEmail = verificaEmail;
        this.consensoPrivacy = consensoPrivacy;
    }

    // --- GETTER & SETTER ---
    public Integer getIdUtente() { return idUtente; }
    public void setIdUtente(Integer idUtente) { this.idUtente = idUtente; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public Ruolo getRuolo() { return ruolo; }
    public void setRuolo(Ruolo ruolo) { this.ruolo = ruolo; }

    public boolean isVerificaEmail() { return verificaEmail; }
    public void setVerificaEmail(boolean verificaEmail) { this.verificaEmail = verificaEmail; }

    public boolean isConsensoPrivacy() { return consensoPrivacy; }
    public void setConsensoPrivacy(boolean consensoPrivacy) { this.consensoPrivacy = consensoPrivacy; }

    @Override
    public String toString() {
        return "Utente{" +
                "idUtente=" + idUtente +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", ruolo=" + ruolo +
                ", verificaEmail=" + verificaEmail +
                ", consensoPrivacy=" + consensoPrivacy +
                '}';
    }

    // --- ENUM interno ---
    public enum Ruolo {
        PROPRIETARIO,
        AGENTE,
        AMMINISTRATORE, 
    }
}