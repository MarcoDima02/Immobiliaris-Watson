package com.residea.residea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/utenti")  // URL che apri nel browser
    public String utentiPage() {
        return "utenti";     // Thymeleaf cercherà templates/utenti.html
    }

    @GetMapping({"/immobili"})
    public String immobili() {
        return "immobili"; // renderizza src/main/resources/templates/immobili.html
    }

    @GetMapping("/leads")
    public String leads() {
        return "lead"; // renderizza src/main/resources/templates/lead.html
    }

    @GetMapping("/contratti")
    public String contratti() {
        return "contratti"; // Thymeleaf cerca templates/contratti.html
    }

    @GetMapping("/amministratore")
    public String dashboardAmministratore() {
        return "AmministratoreDashboard"; // Thymeleaf cerca templates/AmministratoreDashboard.html
    }

    @GetMapping("/dashboard-utenti")
    public String dashboardUtenti() {
        return "dashboard-utenti"; // Thymeleaf cerca templates/AmministratoreDashboard.html
    }

    @GetMapping("/dashboard-immobili")
    public String dashboardImmobili() {
        return "dashboard-immobili"; // Thymeleaf cerca templates/AmministratoreDashboard.html
    }

    @GetMapping("/dashboard-contratti")
    public String dashboardContratti() {
        return "dashboard-contratti"; // Thymeleaf cerca templates/AmministratoreDashboard.html
    }

        @GetMapping("/dashboard-leads")
    public String dashboardLeads() {
        return "dashboard-leads"; // Thymeleaf cerca templates/AmministratoreDashboard.html
    }

    @GetMapping("/dashboard-richieste")
    public String dashboardRichieste() {
        return "dashboard-richieste"; // Thymeleaf cerca templates/AmministratoreDashboard.html
    }

    @GetMapping("/dashboard-vendite")
    public String dashboardVendite() {
        return "dashboard-vendite"; // Thymeleaf cerca templates/AmministratoreDashboard.html
    }
}