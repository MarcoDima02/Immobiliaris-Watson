package com.residea.residea.dto;

import com.residea.residea.entities.Utente;

public class LoginResponse {
    private Utente user;
    private String redirectTo;

    public LoginResponse() {}

    public LoginResponse(Utente user, String redirectTo) {
        this.user = user;
        this.redirectTo = redirectTo;
    }

    public Utente getUser() {
        return user;
    }

    public void setUser(Utente user) {
        this.user = user;
    }

    public String getRedirectTo() {
        return redirectTo;
    }

    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
    }
}
