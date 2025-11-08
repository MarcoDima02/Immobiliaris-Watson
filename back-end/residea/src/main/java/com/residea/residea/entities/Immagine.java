package com.residea.residea.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "immagini")
public class Immagine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idImmagine")
    private Integer idImmagine;

    @ManyToOne
    @JoinColumn(name = "idImmobile", nullable = false)
    private Immobile immobile; // rinominato da idImmobile

    @Column(length = 255)
    private String url;

    @Column(length = 150)
    private String nomeFile;

    @Column(columnDefinition = "TEXT")
    private String descrizione;

    @Column(nullable = false)
    private boolean copertina = false;

    @Column(nullable = false)
    private Integer ordinamento = 0;

    private Integer dimensioneKb;

    // --- COSTRUTTORI ---
        public Immagine(Immobile idImmobile, String url, String nomeFile, String descrizione,boolean copertina, Integer ordinamento, Integer dimensioneKb) {
        this.immobile = idImmobile;
        this.url = url;
        this.nomeFile = nomeFile;
        this.descrizione = descrizione;
        this.copertina = copertina;
        this.ordinamento = ordinamento;
        this.dimensioneKb = dimensioneKb;
    }

    // --- GETTER & SETTER ---
    public Integer getIdImmagine() {
        return idImmagine;
    }

    public void setIdImmagine(Integer idImmagine) {
        this.idImmagine = idImmagine;
    }

    public Immobile getImmobile() {
        return immobile;
    }

    public void setImmobile(Immobile Immobile) {
        this.immobile = Immobile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public boolean isCopertina() {
        return copertina;
    }

    public void setCopertina(boolean copertina) {
        this.copertina = copertina;
    }

    public Integer getOrdinamento() {
        return ordinamento;
    }

    public void setOrdinamento(Integer ordinamento) {
        this.ordinamento = ordinamento;
    }

    public Integer getDimensioneKb() {
        return dimensioneKb;
    }

    public void setDimensioneKb(Integer dimensioneKb) {
        this.dimensioneKb = dimensioneKb;
    }

    @Override
    public String toString() {
        return "Immagine{" +
                "idImmagine=" + idImmagine +
                ", immobile=" + (immobile != null ? immobile.getIdImmobile() : null) +
                ", url='" + url + '\'' +
                ", nomeFile='" + nomeFile + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", copertina=" + copertina +
                ", ordinamento=" + ordinamento +
                ", dimensioneKb=" + dimensioneKb +
                '}';
    }
}