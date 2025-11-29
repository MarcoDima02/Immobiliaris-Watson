package com.residea.residea.dto;

import java.math.BigDecimal;

public class VenditaDto {
    private Integer idVendita;
    private Integer idContratto;
    private Integer idImmobile;
    private Integer idUtente;
    private BigDecimal commissionePercentuale;

    public Integer getIdVendita() {
        return idVendita;
    }

    public void setIdVendita(Integer idVendita) {
        this.idVendita = idVendita;
    }

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

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public BigDecimal getCommissionePercentuale() {
        return commissionePercentuale;
    }

    public void setCommissionePercentuale(BigDecimal commissionePercentuale) {
        this.commissionePercentuale = commissionePercentuale;
    }
}
