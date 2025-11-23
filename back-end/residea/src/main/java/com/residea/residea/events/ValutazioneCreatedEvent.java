package com.residea.residea.events;

import java.math.BigDecimal;

public class ValutazioneCreatedEvent {
    private final Integer idValutazione;
    private final String userEmail;
    private final String userName;
    private final Integer valoreMedio;

    public ValutazioneCreatedEvent(Integer idValutazione, String userEmail, String userName, Integer valoreMedio) {
        this.idValutazione = idValutazione;
        this.userEmail = userEmail;
        this.userName = userName;
        this.valoreMedio = valoreMedio;
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

    public Integer getValoreMedio() {
        return valoreMedio;
    }
}
