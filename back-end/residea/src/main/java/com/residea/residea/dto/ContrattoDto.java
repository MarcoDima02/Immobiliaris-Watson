package com.residea.residea.dto;

import java.time.LocalDate;

public class ContrattoDto {
    private Integer idContratto;
    private Integer idImmobile;
    private Integer idAgente;
    private String tipoContratto;  // String invece di enum per semplic ità nel frontend
    private LocalDate dataContratto;
    private LocalDate dataScadenzaContratto;
    private String pathContrattoPDF;

    public Integer getIdContratto() {
        return idContratto;
    }

    public void setIdContratto(Integer idContratto) {
        this.idContratto = idContratto;
    }

    public Integer getIdImmobile() {
        return idImmobile;
    }

    public void setIdImmobile(Integer idImmobile) {
        this.idImmobile = idImmobile;
    }

    public Integer getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(Integer idAgente) {
        this.idAgente = idAgente;
    }

    public String getTipoContratto() {
        return tipoContratto;
    }

    public void setTipoContratto(String tipoContratto) {
        this.tipoContratto = tipoContratto;
    }

    public LocalDate getDataContratto() {
        return dataContratto;
    }

    public void setDataContratto(LocalDate dataContratto) {
        this.dataContratto = dataContratto;
    }

    public LocalDate getDataScadenzaContratto() {
        return dataScadenzaContratto;
    }

    public void setDataScadenzaContratto(LocalDate dataScadenzaContratto) {
        this.dataScadenzaContratto = dataScadenzaContratto;
    }

    public String getPathContrattoPDF() {
        return pathContrattoPDF;
    }

    public void setPathContrattoPDF(String pathContrattoPDF) {
        this.pathContrattoPDF = pathContrattoPDF;
    }
}
