package com.residea.residea.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;


@Entity
@Table(name = "Citta")
public class Citta {

    @Id
    @Column(name = "idCitta")
    private Integer idCitta;

    @Column(name = "nome")
    private String nome;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "regione")
    private String regione;

    @Column(name = "codiceIstat")
    private String codiceIstat;

    // --- COSTRUTTORI ---

    public Citta(String codiceIstat, Integer idCitta, String nome, String provincia, String regione) {
        this.codiceIstat = codiceIstat;
        this.idCitta = idCitta;
        this.nome = nome;
        this.provincia = provincia;
        this.regione = regione;
    }
    
    // --- GETTER & SETTER ---

    public Integer getIdCitta() {
        return idCitta;
    }

    public void setIdCitta(Integer idCitta) {
        this.idCitta = idCitta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public String getCodiceIstat() {
        return codiceIstat;
    }

    public void setCodiceIstat(String codiceIstat) {
        this.codiceIstat = codiceIstat;
    }

    @Override
    public String toString() {
        return "Citta{" +
                "idCitta=" + idCitta +
                ", nome='" + nome + '\'' +
                ", provincia='" + provincia + '\'' +
                ", regione='" + regione + '\'' +
                ", codiceIstat='" + codiceIstat + '\'' +
                '}';
    }
}

