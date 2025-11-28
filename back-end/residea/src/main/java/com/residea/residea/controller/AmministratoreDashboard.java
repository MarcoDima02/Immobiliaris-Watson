package com.residea.residea.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.residea.residea.entities.Contratto;
import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Immobile.Stato;
import com.residea.residea.entities.Immobile.Tipologia;
import com.residea.residea.entities.Utente;
import com.residea.residea.services.UtentiService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import com.residea.residea.services.ImmobileService;
import com.residea.residea.services.ContrattoService;


import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboardAmministratore")
public class AmministratoreDashboard {

    @Autowired
    private UtentiService utentiService;

        @Autowired
        private ImmobileService immobiliService;

        @Autowired
        private ContrattoService contrattiService;


    private boolean isAmministratore(HttpSession session) {
        Object ruolo = session.getAttribute("userRuolo");
        return ruolo != null && ruolo.toString().equalsIgnoreCase("AMMINISTRATORE");
    }

    // Pagina utenti
    @GetMapping("/utenti")
    public String getUtenti(Model model, HttpSession session,
                            @RequestParam(value = "nome", required = false) String nome,
                            @RequestParam(value = "cognome", required = false) String cognome,
                            @RequestParam(value = "email", required = false) String email,
                            @RequestParam(value = "telefono", required = false) String telefono) {

        if (!isAmministratore(session)) {
            return "redirect:/";
        }

        // Ottieni tutti gli utenti
        List<Utente> utenti = utentiService.getAllUtenti();

        // Debug: stampa numero utenti
        System.out.println("Utenti trovati: " + utenti.size());

        // Filtri semplici (opzionali)
        if (nome != null && !nome.isEmpty()) {
            utenti.removeIf(u -> !u.getNome().toLowerCase().contains(nome.toLowerCase()));
        }
        if (cognome != null && !cognome.isEmpty()) {
            utenti.removeIf(u -> !u.getCognome().toLowerCase().contains(cognome.toLowerCase()));
        }
        if (email != null && !email.isEmpty()) {
            utenti.removeIf(u -> !u.getEmail().toLowerCase().contains(email.toLowerCase()));
        }
        if (telefono != null && !telefono.isEmpty()) {
            utenti.removeIf(u -> !u.getTelefono().contains(telefono));
        }

        // Passa la lista al template
        model.addAttribute("listaUtenti", utenti);

        return "dashboard-utenti"; // Thymeleaf cercherà templates/dashboard-utenti.html
    }

    @GetMapping("/immobili")
    public String getImmobili(Model model, HttpSession session,
                              @RequestParam(value = "citta", required = false) String citta,
                              @RequestParam(value = "provincia", required = false) String provincia,
                              @RequestParam(value = "tipologia", required = false) Tipologia tipologia,
                              @RequestParam(value = "stato", required = false) Stato stato,
                              @RequestParam(value = "proprietario", required = false) Integer idProprietario) {

        // --- Controllo accesso ---
        if (!isAmministratore(session)) {
            return "redirect:/";
        }

        // --- Carica immobili ---
        List<Immobile> immobili = immobiliService.getAllImmobili();

        System.out.println("Immobili trovati: " + immobili.size());

        // --- Filtri opzionali ---
        if (citta != null && !citta.isEmpty()) {
            immobili.removeIf(i -> i.getCitta() == null ||
                    !i.getCitta().toLowerCase().contains(citta.toLowerCase()));
        }

        if (provincia != null && !provincia.isEmpty()) {
            immobili.removeIf(i -> i.getProvincia() == null ||
                    !i.getProvincia().equalsIgnoreCase(provincia));
        }

        if (tipologia != null) {
            immobili.removeIf(i -> i.getTipologia() != tipologia);
        }

        if (stato != null) {
            immobili.removeIf(i -> i.getStato() != stato);
        }

        if (idProprietario != null) {
            immobili.removeIf(i -> i.getProprietario() == null ||
                    !i.getProprietario().getIdUtente().equals(idProprietario));
        }

        // Aggiungo lista al template
        model.addAttribute("listaImmobili", immobili);

        // Opzionale: per select nel form
        model.addAttribute("tipologie", Tipologia.values());
        model.addAttribute("stati", Stato.values());
        model.addAttribute("utenti", utentiService.getAllUtenti());

        return "dashboard-immobili"; // → templates/dashboard-immobili.html
    }



    @GetMapping("/contratti")
        public String getContratti(Model model,
                                   HttpSession session,
                                   @RequestParam(value = "tipo", required = false) Contratto.TipoContratto tipo,
                                   @RequestParam(value = "immobile", required = false) Integer idImmobile,
                                   @RequestParam(value = "agente", required = false) Integer idAgente) {

            // --- Controllo accesso ---
            if (!isAmministratore(session)) {
                return "redirect:/";
            }

            // --- Carica contratti ---
            List<Contratto> contratti = contrattiService.getAllContratti();
            System.out.println("Contratti trovati: " + contratti.size());

            // --- Filtri opzionali ---
            if (tipo != null) {
                contratti.removeIf(c -> c.getTipoContratto() != tipo);
            }

            if (idImmobile != null) {
                contratti.removeIf(c -> c.getIdImmobile() == null ||
                        !c.getIdImmobile().getIdImmobile().equals(idImmobile));
            }

            if (idAgente != null) {
                contratti.removeIf(c -> c.getAgente() == null ||
                        !c.getAgente().getIdUtente().equals(idAgente));
            }

            // --- Aggiungo dati al model ---
            model.addAttribute("listaContratti", contratti);

            // Per i filtri nel form
            model.addAttribute("tipiContratto", Contratto.TipoContratto.values());
            model.addAttribute("immobili", immobiliService.getAllImmobili());
            model.addAttribute("agenti", utentiService.getAllUtenti());

            return "dashboard-contratti"; 
        }

        @GetMapping("/api/contratti")
        @ResponseBody
        public List<Contratto> apiContratti(HttpSession session) {

            if (!isAmministratore(session)) {
                return Collections.emptyList(); // blocco sicurezza
            }

            return contrattiService.getAllContratti();
        }
    @PostMapping("/api/contratti")
@ResponseBody
public Contratto creaContratto(@RequestBody Contratto c, HttpSession session) {
    if (!isAmministratore(session)) throw new RuntimeException("Accesso negato");
    return contrattiService.salvaContratto(c);
}


}