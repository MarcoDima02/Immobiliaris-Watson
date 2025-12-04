package com.residea.residea.dto;

public class UtenteDto {
    private Integer idUtente;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private String ruolo;
    private Boolean verificaEmail;
    private Boolean consensoPrivacy;

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
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

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public Boolean getVerificaEmail() {
        return verificaEmail;
    }

    public void setVerificaEmail(Boolean verificaEmail) {
        this.verificaEmail = verificaEmail;
    }

    public Boolean getConsensoPrivacy() {
        return consensoPrivacy;
    }

    public void setConsensoPrivacy(Boolean consensoPrivacy) {
        this.consensoPrivacy = consensoPrivacy;
    }
}
