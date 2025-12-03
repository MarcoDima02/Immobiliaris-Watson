package com.residea.residea.events;

import java.math.BigDecimal;

public class ValutazioneCreatedEvent {
    private final Integer idValutazione;
    private final String userEmail;
    private final String userName;
    private final Integer valoreMin;
    private final Integer valoreMax;
    
    // Dati immobile per riepilogo
    private final String tipologia;
    private final String indirizzo;
    private final String citta;
    private final String provincia;
    private final BigDecimal superficie;
    private final Integer nStanze;
    private final Integer nBagni;

    public ValutazioneCreatedEvent(Integer idValutazione, String userEmail, String userName, 
                                   Integer valoreMin, Integer valoreMax,
                                   String tipologia, String indirizzo, String citta, String provincia,
                                   BigDecimal superficie, Integer nStanze, Integer nBagni) {
        this.idValutazione = idValutazione;
        this.userEmail = userEmail;
        this.userName = userName;
        this.valoreMin = valoreMin;
        this.valoreMax = valoreMax;
        this.tipologia = tipologia;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.provincia = provincia;
        this.superficie = superficie;
        this.nStanze = nStanze;
        this.nBagni = nBagni;
    }

    public Integer getIdValutazione() {
        return idValutazione;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getValoreMin() {
        return valoreMin;
    }

    public Integer getValoreMassimo() {
        return valoreMax;
    }
    
    public String getTipologia() {
        return tipologia;
    }
    
    public String getIndirizzo() {
        return indirizzo;
    }
    
    public String getCitta() {
        return citta;
    }
    
    public String getProvincia() {
        return provincia;
    }
    
    public BigDecimal getSuperficie() {
        return superficie;
    }
    
    public Integer getNStanze() {
        return nStanze;
    }
    
    public Integer getNBagni() {
        return nBagni;
    }
}
