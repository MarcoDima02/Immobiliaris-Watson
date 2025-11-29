package com.residea.residea.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.residea.residea.dto.ImmagineDto;
import com.residea.residea.dto.ImmobileDto;
import com.residea.residea.dto.RichiestaDto;
import com.residea.residea.dto.UtenteDto;
import com.residea.residea.dto.VenditaDto;
import com.residea.residea.entities.Contratto;
import com.residea.residea.entities.Immagine;
import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Immobile.Stato;
import com.residea.residea.entities.Immobile.Tipologia;
import com.residea.residea.entities.Richiesta;
import com.residea.residea.entities.Utente;
import com.residea.residea.entities.Vendita;
import com.residea.residea.entities.Lead;

import com.residea.residea.services.ContrattoService;
import com.residea.residea.services.ImmagineService;
import com.residea.residea.services.ImmobileService;
import com.residea.residea.services.LeadService;
import com.residea.residea.services.RichiestaService;
import com.residea.residea.services.UtentiService;
import com.residea.residea.services.VenditaService;
import com.residea.residea.services.LeadService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AmministratoreDashboard {

    @Autowired
    private UtentiService utentiService;

        @Autowired
        private ImmobileService immobiliService;

        @Autowired
        private ContrattoService contrattiService;

        @Autowired
        private VenditaService venditaService;

        @Autowired
        private RichiestaService richiestaService;

        @Autowired
        private ImmagineService immagineService;

        @Autowired
        private LeadService leadService;

        private static final Logger log = LoggerFactory.getLogger(AmministratoreDashboard.class);


    // Security is performed with @PreAuthorize on the class; keep session check as fallback
    private boolean isAmministratore(HttpSession session) {
        Object ruolo = session.getAttribute("userRuolo");
        log.info("Session ID: {}, userRuolo: {}", session.getId(), ruolo);
        return ruolo != null && ruolo.toString().equalsIgnoreCase("AMMINISTRATORE");
    }

    // Pagina utenti
    @GetMapping("/utenti")
    public ResponseEntity<List<UtenteDto>> getUtenti(HttpSession session,
                            @RequestParam(value = "nome", required = false) String nome,
                            @RequestParam(value = "cognome", required = false) String cognome,
                            @RequestParam(value = "email", required = false) String email,
                            @RequestParam(value = "telefono", required = false) String telefono) {
        // TODO: Riattivare dopo che tutti hanno rifatto login
        // if (!isAmministratore(session)) {
        //     log.warn("Accesso negato: utente non amministratore");
        //     return ResponseEntity.status(403).build();
        // }

        // Ottieni tutti gli utenti e mappali in DTO
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

        List<UtenteDto> dtos = utenti.stream().map(this::toUtenteDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/immobili")
    public ResponseEntity<List<ImmobileDto>> getImmobili(HttpSession session,
                              @RequestParam(value = "citta", required = false) String citta,
                              @RequestParam(value = "provincia", required = false) String provincia,
                              @RequestParam(value = "tipologia", required = false) Tipologia tipologia,
                              @RequestParam(value = "stato", required = false) Stato stato,
                              @RequestParam(value = "proprietario", required = false) Integer idProprietario) {
        // TODO: Riattivare dopo login
        // if (!isAmministratore(session)) {
        //     return ResponseEntity.status(403).build();
        // }

        // --- Carica immobili e mappa in DTO ---
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
        List<ImmobileDto> dtos = immobili.stream().map(this::toImmobileDto).toList();
        return ResponseEntity.ok(dtos);
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

// --- Pagina dashboard Richieste ---
@GetMapping("/richieste")
public String getRichieste(Model model,
                           HttpSession session,
                           @RequestParam(value = "stato", required = false) Richiesta.Stato stato,
                           @RequestParam(value = "utente", required = false) Integer idUtente,
                           @RequestParam(value = "immobile", required = false) Integer idImmobile) {

    if (!isAmministratore(session)) {
        return "redirect:/";
    }

    // --- Carica tutte le richieste ---
    List<Richiesta> richieste = richiestaService.getAllRichieste();
    System.out.println("Richieste trovate: " + richieste.size());

    // --- Filtri opzionali ---
    if (stato != null) {
        richieste.removeIf(r -> r.getStato() != stato);
    }
    if (idUtente != null) {
        richieste.removeIf(r -> r.getUtente() == null || !r.getUtente().getIdUtente().equals(idUtente));
    }
    if (idImmobile != null) {
        richieste.removeIf(r -> r.getImmobile() == null || !r.getImmobile().getIdImmobile().equals(idImmobile));
    }

    // --- Aggiungo dati al model ---
    model.addAttribute("listaRichieste", richieste);
    model.addAttribute("statiRichieste", Richiesta.Stato.values());
    model.addAttribute("utenti", utentiService.getAllUtenti());
    model.addAttribute("immobili", immobiliService.getAllImmobili());

    return "dashboard-richieste"; // Thymeleaf template
}

// --- API per richieste (JSON) ---
@GetMapping("/api/richieste")
@ResponseBody
public List<Richiesta> apiRichieste(HttpSession session) {
    if (!isAmministratore(session)) return Collections.emptyList();
    return richiestaService.getAllRichieste();
}

// --- Crea nuova richiesta ---
@PostMapping("/api/richieste")
@ResponseBody
public Richiesta creaRichiesta(@RequestBody Richiesta r, HttpSession session) {
    if (!isAmministratore(session)) throw new RuntimeException("Accesso negato");

    if (r.getUtente() != null && r.getUtente().getIdUtente() != null) {
        r.setUtente(utentiService.getUtenteById(r.getUtente().getIdUtente()));
    }
    if (r.getImmobile() != null && r.getImmobile().getIdImmobile() != null) {
        r.setImmobile(immobiliService.getImmobileById(r.getImmobile().getIdImmobile()));
    }

    return richiestaService.createRichiesta(r);
}

// --- Aggiorna richiesta ---
@PutMapping("/api/richieste/{id}")
@ResponseBody
public Richiesta aggiornaRichiesta(@PathVariable Integer id, @RequestBody Richiesta r, HttpSession session) {
    if (!isAmministratore(session)) throw new RuntimeException("Accesso negato");

    if (r.getUtente() != null && r.getUtente().getIdUtente() != null) {
        r.setUtente(utentiService.getUtenteById(r.getUtente().getIdUtente()));
    }
    if (r.getImmobile() != null && r.getImmobile().getIdImmobile() != null) {
        r.setImmobile(immobiliService.getImmobileById(r.getImmobile().getIdImmobile()));
    }

    return richiestaService.updateRichiesta(id, r);
}

// --- Elimina richiesta ---
@DeleteMapping("/api/richieste/{id}")
@ResponseBody
public void eliminaRichiesta(@PathVariable Integer id, HttpSession session) {
    if (!isAmministratore(session)) throw new RuntimeException("Accesso negato");
    richiestaService.deleteRichiesta(id);
}


// --- Pagina dashboard Vendite ---
@GetMapping("/vendite")
public String getVendite(Model model,
                         HttpSession session,
                         @RequestParam(value = "contratto", required = false) Integer idContratto,
                         @RequestParam(value = "immobile", required = false) Integer idImmobile,
                         @RequestParam(value = "utente", required = false) Integer idUtente) {

    if (!isAmministratore(session)) {
        return "redirect:/";
    }

    // --- Carica tutte le vendite ---
    List<Vendita> vendite = venditaService.getAllVendite();
    System.out.println("Vendite trovate: " + vendite.size());

    // --- Filtri opzionali ---
    if (idContratto != null) {
        vendite.removeIf(v -> v.getContratto() == null || !v.getContratto().getIdContratto().equals(idContratto));
    }
    if (idImmobile != null) {
        vendite.removeIf(v -> v.getImmobile() == null || !v.getImmobile().getIdImmobile().equals(idImmobile));
    }
    if (idUtente != null) {
        vendite.removeIf(v -> v.getUtente() == null || !v.getUtente().getIdUtente().equals(idUtente));
    }

    // --- Aggiungo dati al model ---
    model.addAttribute("listaVendite", vendite);
    model.addAttribute("utenti", utentiService.getAllUtenti());
    model.addAttribute("immobili", immobiliService.getAllImmobili());
    model.addAttribute("contratti", contrattiService.getAllContratti());

    return "dashboard-vendite"; // Thymeleaf template
}

// --- API per vendite (JSON) ---
@GetMapping("/api/vendite")
@ResponseBody
public List<VenditaDto> apiVendite(HttpSession session) {
    if (!isAmministratore(session)) return Collections.emptyList();

    List<Vendita> vendite = venditaService.getAllVendite();
    return vendite.stream().map(this::toVenditaDto).toList();
}

// --- Crea nuova vendita ---
@PostMapping("/api/vendite")
@ResponseBody
public Vendita creaVendita(@RequestBody Vendita v, HttpSession session) {
    if (!isAmministratore(session)) throw new RuntimeException("Accesso negato");

    if (v.getContratto() != null && v.getContratto().getIdContratto() != null)
        v.setContratto(contrattiService.getContrattoById(v.getContratto().getIdContratto()));
    if (v.getUtente() != null && v.getUtente().getIdUtente() != null)
        v.setUtente(utentiService.getUtenteById(v.getUtente().getIdUtente()));
    if (v.getImmobile() != null && v.getImmobile().getIdImmobile() != null)
        v.setImmobile(immobiliService.getImmobileById(v.getImmobile().getIdImmobile()));

    return venditaService.createVendita(v);
}

// --- Aggiorna vendita ---
@PutMapping("/api/vendite/{id}")
@ResponseBody
public Vendita aggiornaVendita(@PathVariable Integer id, @RequestBody Vendita v, HttpSession session) {
    if (!isAmministratore(session)) throw new RuntimeException("Accesso negato");

    if (v.getContratto() != null && v.getContratto().getIdContratto() != null)
        v.setContratto(contrattiService.getContrattoById(v.getContratto().getIdContratto()));
    if (v.getUtente() != null && v.getUtente().getIdUtente() != null)
        v.setUtente(utentiService.getUtenteById(v.getUtente().getIdUtente()));
    if (v.getImmobile() != null && v.getImmobile().getIdImmobile() != null)
        v.setImmobile(immobiliService.getImmobileById(v.getImmobile().getIdImmobile()));

    return venditaService.updateVendita(id, v);
}

// --- Delete vendita (facoltativo) ---
@DeleteMapping("/api/vendite/{id}")
@ResponseBody
public void eliminaVendita(@PathVariable Integer id, HttpSession session) {
    if (!isAmministratore(session)) throw new RuntimeException("Accesso negato");
    venditaService.deleteVendita(id);
}

// --- Pagina dashboard Leads ---
// Pagina HTML
@GetMapping("/leads")
public String leadsPage(Model model, HttpSession session) {
    if (!isAmministratore(session)) return "redirect:/";
    model.addAttribute("utenti", utentiService.getAllUtenti());
    return "dashboard-leads"; // Thymeleaf
}

// API JSON
@GetMapping("/api/leads")
@ResponseBody
public List<Lead> apiLeads(HttpSession session) {
    if (!isAmministratore(session)) return Collections.emptyList();
    return leadService.getAllLeads();
}

@PostMapping("/api/leads")
@ResponseBody
public Lead creaLead(@RequestBody Lead l, HttpSession session) {
    if (!isAmministratore(session)) throw new RuntimeException("Accesso negato");
    if (l.getUtente() != null && l.getUtente().getIdUtente() != null) {
        l.setUtente(utentiService.getUtenteById(l.getUtente().getIdUtente()));
    }
    return leadService.createLead(l);
}

@PutMapping("/api/leads/{id}")
@ResponseBody
public Lead aggiornaLead(@PathVariable Integer id, @RequestBody Lead l, HttpSession session) {
    if (!isAmministratore(session)) throw new RuntimeException("Accesso negato");
    if (l.getUtente() != null && l.getUtente().getIdUtente() != null) {
        l.setUtente(utentiService.getUtenteById(l.getUtente().getIdUtente()));
    }
    return leadService.updateLead(id, l);
}

@DeleteMapping("/api/leads/{id}")
@ResponseBody
public void eliminaLead(@PathVariable Integer id, HttpSession session) {
    if (!isAmministratore(session)) throw new RuntimeException("Accesso negato");
    leadService.deleteLead(id);
}



    // --- Mappers ---
    private UtenteDto toUtenteDto(Utente u) {
        if (u == null) return null;
        UtenteDto d = new UtenteDto();
        d.setIdUtente(u.getIdUtente());
        d.setNome(u.getNome());
        d.setCognome(u.getCognome());
        d.setEmail(u.getEmail());
        d.setTelefono(u.getTelefono());
        d.setRuolo(u.getRuolo() == null ? null : u.getRuolo().name());
        d.setVerificaEmail(u.isVerificaEmail());
        d.setConsensoPrivacy(u.isConsensoPrivacy());
        return d;
    }

    private ImmobileDto toImmobileDto(Immobile i) {
        if (i == null) return null;
        ImmobileDto d = new ImmobileDto();
        d.setIdImmobile(i.getIdImmobile());
        d.setIdProprietario(i.getProprietario() == null ? null : i.getProprietario().getIdUtente());
        d.setTipologia(i.getTipologia() == null ? null : i.getTipologia().name());
        d.setIndirizzo(i.getIndirizzo());
        d.setCitta(i.getCitta());
        d.setProvincia(i.getProvincia());
        d.setCap(i.getCap());
        d.setLatitudine(i.getLatitudine() == null ? null : i.getLatitudine().doubleValue());
        d.setLongitudine(i.getLongitudine() == null ? null : i.getLongitudine().doubleValue());
        d.setStato(i.getStato() == null ? null : i.getStato().name());
        return d;
    }

    // private ContrattoDTO toContrattoDTO(Contratto c) {
    //     if (c == null) return null;
    //     ContrattoDTO d = new ContrattoDTO();
    //     d.setIdContratto(c.getIdContratto());
    //     d.setIdImmobile(c.getIdImmobile() == null ? null : c.getIdImmobile().getIdImmobile());
    //     d.setIdAgente(c.getAgente() == null ? null : c.getAgente().getIdUtente());
    //     d.setTipoContratto(c.getTipoContratto() == null ? null : c.getTipoContratto().name());
    //     d.setDataContratto(c.getDataContratto());
    //     d.setDataScadenzaContratto(c.getDataScadenzaContratto());
    //     d.setPathContrattoPDF(c.getPathContrattoPDF());
    //     return d;
    // }

    private VenditaDto toVenditaDto(Vendita v) {
        if (v == null) return null;
        VenditaDto d = new VenditaDto();
        d.setIdVendita(v.getIdVendita());
        d.setIdContratto(v.getContratto() == null ? null : v.getContratto().getIdContratto());
        d.setIdImmobile(v.getImmobile() == null ? null : v.getImmobile().getIdImmobile());
        d.setIdUtente(v.getUtente() == null ? null : v.getUtente().getIdUtente());
        d.setCommissionePercentuale(v.getCommissionePercentuale());
        return d;
    }

    private RichiestaDto toRichiestaDto(Richiesta r) {
        if (r == null) return null;
        RichiestaDto d = new RichiestaDto();
        d.setIdRichiesta(r.getIdRichiesta());
        d.setIdUtente(r.getUtente() == null ? null : r.getUtente().getIdUtente());
        d.setIdImmobile(r.getImmobile() == null ? null : r.getImmobile().getIdImmobile());
        d.setDataRichiesta(r.getDataRichiesta());
        d.setDataAppuntamento(r.getDataAppuntamento());
        d.setStato(r.getStato() == null ? null : r.getStato().name());
        d.setNoteUtente(r.getNoteUtente());
        d.setMotivoAnnullamento(r.getMotivoAnnullamento());
        return d;
    }

    private ImmagineDto toImmagineDto(Immagine img) {
        if (img == null) return null;
        ImmagineDto d = new ImmagineDto();
        d.setIdImmagine(img.getIdImmagine());
        d.setIdImmobile(img.getImmobile() == null ? null : img.getImmobile().getIdImmobile());
        d.setUrl(img.getUrl());
        d.setNomeFile(img.getNomeFile());
        d.setDescrizione(img.getDescrizione());
        d.setCopertina(img.isCopertina());
        d.setOrdinamento(img.getOrdinamento());
        d.setDimensioneKb(img.getDimensioneKb());
        return d;
    }

    // --- Endpoints per Vendite ---
    

    

    // --- Endpoints per Immagini ---
    @GetMapping("/immagini")
    public ResponseEntity<List<ImmagineDto>> getImmagini(HttpSession session,
                                @RequestParam(value = "immobile", required = false) Integer idImmobile,
                                @RequestParam(value = "copertina", required = false) Boolean copertina) {
        // TODO: Riattivare dopo login
        // if (!isAmministratore(session)) {
        //     return ResponseEntity.status(403).build();
        // }

        List<Immagine> immagini = immagineService.getAllImmagini();
        System.out.println("Immagini trovate: " + immagini.size());

        // Filtri opzionali
        if (idImmobile != null) {
            immagini.removeIf(img -> img.getImmobile() == null || !img.getImmobile().getIdImmobile().equals(idImmobile));
        }
        if (copertina != null) {
            immagini.removeIf(img -> img.isCopertina() != copertina);
        }

        List<ImmagineDto> dtos = immagini.stream().map(this::toImmagineDto).toList();
        return ResponseEntity.ok(dtos);
    }

}