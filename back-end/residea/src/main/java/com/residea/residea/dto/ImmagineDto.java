package com.residea.residea.dto;

public class ImmagineDto {
    private Integer idImmagine;
    private Integer idImmobile;
    private String url;
    private String nomeFile;
    private String descrizione;
    private Boolean copertina;
    private Integer ordinamento;
    private Integer dimensioneKb;

    public Integer getIdImmagine() {
        return idImmagine;
    }

    public void setIdImmagine(Integer idImmagine) {
        this.idImmagine = idImmagine;
    }

    public Integer getIdImmobile() {
        return idImmobile;
    }

    public void setIdImmobile(Integer idImmobile) {
        this.idImmobile = idImmobile;
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

    public Boolean getCopertina() {
        return copertina;
    }

    public void setCopertina(Boolean copertina) {
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
}
