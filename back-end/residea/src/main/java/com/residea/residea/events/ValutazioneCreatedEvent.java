package com.residea.residea.events;

public class ValutazioneCreatedEvent {
    private final Integer idValutazione;
    private final String userEmail;
    private final String userName;
    private final Integer valoreMin;
    private final Integer valoreMax;

    public ValutazioneCreatedEvent(Integer idValutazione, String userEmail, String userName, Integer valoreMin, Integer valoreMax) {
        this.idValutazione = idValutazione;
        this.userEmail = userEmail;
        this.userName = userName;
        this.valoreMin = valoreMin;
        this.valoreMax = valoreMax;
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
}
