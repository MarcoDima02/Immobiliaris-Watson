package com.residea.residea.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.residea.residea.entities.Lead;
import com.residea.residea.entities.Utente;
import com.residea.residea.repos.UtenteRepo;
import com.residea.residea.services.LeadService;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    @Autowired
    private LeadService leadService;

    @Autowired
    private UtenteRepo utenteRepo;

    @GetMapping
    public List<Lead> getAll() {
        return leadService.getAllLeads();
    }

    @GetMapping(path = "/{id}")
    public Lead getById(@PathVariable("id") Integer id) {
        return leadService.getLeadById(id).orElse(null);
    }

    @PostMapping
    public Lead create(@RequestBody Lead lead) {
        // resolve utente if id provided
        if (lead.getUtente() != null && lead.getUtente().getIdUtente() != null) {
            Integer uid = lead.getUtente().getIdUtente();
            Utente u = utenteRepo.findById(uid).orElse(null);
            lead.setUtente(u);
        } else {
            lead.setUtente(null);
        }
        return leadService.createLead(lead);
    }

    @PutMapping(path = "/{id}")
    public Lead update(@PathVariable("id") Integer id, @RequestBody Lead updated) {
        // resolve utente if provided
        if (updated.getUtente() != null && updated.getUtente().getIdUtente() != null) {
            Integer uid = updated.getUtente().getIdUtente();
            Utente u = utenteRepo.findById(uid).orElse(null);
            updated.setUtente(u);
        } else {
            updated.setUtente(null);
        }
        return leadService.updateLead(id, updated);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") Integer id) {
        leadService.deleteLead(id);
    }

    @GetMapping(path = "/email/{email}")
    public List<Lead> findByEmail(@PathVariable("email") String email) {
        return leadService.findByEmail(email);
    }

    @GetMapping(path = "/citta/{citta}")
    public List<Lead> findByCitta(@PathVariable("citta") String citta) {
        return leadService.findByCitta(citta);
    }

    @GetMapping(path = "/utente/{idUtente}")
    public List<Lead> findByUtente(@PathVariable("idUtente") Integer idUtente) {
        Utente u = utenteRepo.findById(idUtente).orElse(null);
        if (u == null) return java.util.Collections.emptyList();
        return leadService.findByUtente(u);
    }
}

