package com.residea.residea.dto;

import com.residea.residea.entities.Utente;

public class LoginResponse {
    private Utente user;
    // no redirectTo: backend only returns the authenticated user, frontend decides navigation

    public LoginResponse() {}

    public LoginResponse(Utente user) {
        this.user = user;
    }

    public Utente getUser() {
        return user;
    }

    public void setUser(Utente user) {
        this.user = user;
    }

    // redirectTo removed
}
