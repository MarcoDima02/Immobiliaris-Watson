package com.residea.residea.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.hibernate.boot.model.relational.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.residea.residea.dto.ContrattoDto;
import com.residea.residea.dto.ImmagineDto;
import com.residea.residea.dto.ImmobileDto;
import com.residea.residea.dto.RichiestaDettagliImmobileDto;
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
import com.residea.residea.entities.Utente.Ruolo;
import com.residea.residea.entities.Vendita;
import com.residea.residea.services.ContrattoService;
import com.residea.residea.services.DettagliImmobileService;
import com.residea.residea.services.ImmagineService;
import com.residea.residea.services.ImmobileService;
import com.residea.residea.services.RichiestaService;
import com.residea.residea.services.UtentiService;
import com.residea.residea.services.VenditaService;

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
        private DettagliImmobileService dettagliImmobileService;

        @Autowired
        private ImmagineService immagineService;

        // Controllo amministratore
    private boolean isAmministratore(HttpSession session) {
        Object ruolo = session.getAttribute("userRuolo");
        return ruolo != null && ruolo.toString().equalsIgnoreCase("AMMINISTRATORE");
    }

    // Removed old dashboard() method - use REST endpoints instead (getUtenti, getImmobili, etc.)
    
    // Note: Frontend handles navigation, backend only returns data/status codes
    // Dashboard data accessed via: /api/admin/dashboard/utenti, /immobili, /contratti, etc.

    // Pagina utenti
    @GetMapping("/utenti")
    public ResponseEntity<List<UtenteDto>> getUtenti(HttpSession session,
                            @RequestParam(value = "nome", required = false) String nome,
                            @RequestParam(value = "cognome", required = false) String cognome,
                            @RequestParam(value = "email", required = false) String email,
                            @RequestParam(value = "ruolo", required = false) Ruolo ruolo,
                            @RequestParam(value = "telefono", required = false) String telefono){
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

    // --- Endpoints per Contratti ---
    @GetMapping("/contratti")
    public ResponseEntity<List<ContrattoDto>> getContratti(HttpSession session,
                                   @RequestParam(value = "tipo", required = false) Contratto.TipoContratto tipo,
                                   @RequestParam(value = "immobile", required = false) Integer idImmobile,
                                   @RequestParam(value = "agente", required = false) Integer idAgente) {
        // TODO: Riattivare dopo login
        // if (!isAmministratore(session)) {
        //     return ResponseEntity.status(403).build();
        // }

        List<Contratto> contratti = contrattiService.getAllContratti();
        System.out.println("Contratti trovati: " + contratti.size());

        // Filtri opzionali
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

        List<ContrattoDto> dtos = contratti.stream().map(this::toContrattoDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/contratti")
    public ResponseEntity<Contratto> createContratto(@RequestBody Contratto c, HttpSession session) {
        // TODO: Riattivare dopo login
        // if (!isAmministratore(session)) return ResponseEntity.status(403).build();
        Contratto saved = contrattiService.salvaContratto(c);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/contratti/{id}")
    public ResponseEntity<Contratto> updateContratto(@PathVariable Integer id, @RequestBody Contratto c, HttpSession session) {
        // TODO: Riattivare dopo login
        // if (!isAmministratore(session)) return ResponseEntity.status(403).build();
        c.setIdContratto(id);
        Contratto updated = contrattiService.salvaContratto(c);
        return ResponseEntity.ok(updated);
    }

    // --- Endpoints per Richieste ---
@GetMapping("/richieste")
public ResponseEntity<List<RichiestaDto>> getRichieste(HttpSession session,
                           @RequestParam(value = "stato", required = false) Richiesta.Stato stato,
                           @RequestParam(value = "utente", required = false) Integer idUtente,
                           @RequestParam(value = "immobile", required = false) Integer idImmobile) {
    // TODO: Riattivare dopo login
    // if (!isAmministratore(session)) {
    //     return ResponseEntity.status(403).build();
    // }

    List<Richiesta> richieste = richiestaService.getAllRichieste();
    System.out.println("Richieste trovate: " + richieste.size());

    // Filtri opzionali
    if (stato != null) {
        richieste.removeIf(r -> r.getStato() != stato);
    }
    if (idUtente != null) {
        richieste.removeIf(r -> r.getUtente() == null || !r.getUtente().getIdUtente().equals(idUtente));
    }
    if (idImmobile != null) {
        richieste.removeIf(r -> r.getImmobile() == null || !r.getImmobile().getIdImmobile().equals(idImmobile));
    }

    List<RichiestaDto> dtos = richieste.stream().map(this::toRichiestaDto).toList();
    return ResponseEntity.ok(dtos);
}



@PostMapping("/richieste")
public ResponseEntity<Richiesta> createRichiesta(@RequestBody Richiesta r, HttpSession session) {
    // TODO: Riattivare dopo login
    // if (!isAmministratore(session)) return ResponseEntity.status(403).build();
    
    if (r.getUtente() != null && r.getUtente().getIdUtente() != null) {
        r.setUtente(utentiService.getUtenteById(r.getUtente().getIdUtente()));
    }
    if (r.getImmobile() != null && r.getImmobile().getIdImmobile() != null) {
        r.setImmobile(immobiliService.getImmobileById(r.getImmobile().getIdImmobile()));
    }
    
    Richiesta saved = richiestaService.createRichiesta(r);
    return ResponseEntity.ok(saved);
}

@PutMapping("/richieste/{id}")
public ResponseEntity<Richiesta> updateRichiesta(@PathVariable Integer id, @RequestBody Richiesta r, HttpSession session) {
    // TODO: Riattivare dopo login
    // if (!isAmministratore(session)) return ResponseEntity.status(403).build();
    
    if (r.getUtente() != null && r.getUtente().getIdUtente() != null) {
        r.setUtente(utentiService.getUtenteById(r.getUtente().getIdUtente()));
    }
    if (r.getImmobile() != null && r.getImmobile().getIdImmobile() != null) {
        r.setImmobile(immobiliService.getImmobileById(r.getImmobile().getIdImmobile()));
    }
    
    Richiesta updated = richiestaService.updateRichiesta(id, r);
    return ResponseEntity.ok(updated);
}

// --- Endpoints per Vendite ---
@GetMapping("/vendite")
public ResponseEntity<List<VenditaDto>> getVendite(HttpSession session,
                         @RequestParam(value = "contratto", required = false) Integer idContratto,
                         @RequestParam(value = "immobile", required = false) Integer idImmobile,
                         @RequestParam(value = "utente", required = false) Integer idUtente) {
    // TODO: Riattivare dopo login
    // if (!isAmministratore(session)) {
    //     return ResponseEntity.status(403).build();
    // }

    List<Vendita> vendite = venditaService.getAllVendite();
    System.out.println("Vendite trovate: " + vendite.size());

    // Filtri opzionali
    if (idContratto != null) {
        vendite.removeIf(v -> v.getContratto() == null || !v.getContratto().getIdContratto().equals(idContratto));
    }
    if (idImmobile != null) {
        vendite.removeIf(v -> v.getImmobile() == null || !v.getImmobile().getIdImmobile().equals(idImmobile));
    }
    if (idUtente != null) {
        vendite.removeIf(v -> v.getUtente() == null || !v.getUtente().getIdUtente().equals(idUtente));
    }

    List<VenditaDto> dtos = vendite.stream().map(this::toVenditaDto).toList();
    return ResponseEntity.ok(dtos);
}

@PostMapping("/vendite")
public ResponseEntity<Vendita> createVendita(@RequestBody Vendita v, HttpSession session) {
    // TODO: Riattivare dopo login
    // if (!isAmministratore(session)) return ResponseEntity.status(403).build();
    
    if (v.getContratto() != null && v.getContratto().getIdContratto() != null)
        v.setContratto(contrattiService.getContrattoById(v.getContratto().getIdContratto()));
    if (v.getUtente() != null && v.getUtente().getIdUtente() != null)
        v.setUtente(utentiService.getUtenteById(v.getUtente().getIdUtente()));
    if (v.getImmobile() != null && v.getImmobile().getIdImmobile() != null)
        v.setImmobile(immobiliService.getImmobileById(v.getImmobile().getIdImmobile()));
    
    Vendita saved = venditaService.createVendita(v);
    return ResponseEntity.ok(saved);
}

@PutMapping("/vendite/{id}")
public ResponseEntity<Vendita> updateVendita(@PathVariable Integer id, @RequestBody Vendita v, HttpSession session) {
    // TODO: Riattivare dopo login
    // if (!isAmministratore(session)) return ResponseEntity.status(403).build();
    
    if (v.getContratto() != null && v.getContratto().getIdContratto() != null)
        v.setContratto(contrattiService.getContrattoById(v.getContratto().getIdContratto()));
    if (v.getUtente() != null && v.getUtente().getIdUtente() != null)
        v.setUtente(utentiService.getUtenteById(v.getUtente().getIdUtente()));
    if (v.getImmobile() != null && v.getImmobile().getIdImmobile() != null)
        v.setImmobile(immobiliService.getImmobileById(v.getImmobile().getIdImmobile()));
    
    Vendita updated = venditaService.updateVendita(id, v);
    return ResponseEntity.ok(updated);
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
        d.setRuolo(u.getRuolo() == null ? null : u.getRuolo());
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

    private ContrattoDto toContrattoDto(Contratto c) {
        if (c == null) return null;
        ContrattoDto d = new ContrattoDto();
        d.setIdContratto(c.getIdContratto());
        d.setIdImmobile(c.getIdImmobile() == null ? null : c.getIdImmobile().getIdImmobile());
        d.setIdAgente(c.getAgente() == null ? null : c.getAgente().getIdUtente());
        d.setTipoContratto(c.getTipoContratto() == null ? null : c.getTipoContratto().name());
        d.setDataContratto(c.getDataContratto());
        d.setDataScadenzaContratto(c.getDataScadenzaContratto());
        d.setPathContrattoPDF(c.getPathContrattoPDF());
        return d;
    }

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

    /**
     * Mapper per Richiesta con Dettagli Immobile
     * Combina: Richiesta + Immobile + DettagliImmobile + Utente
     */
    private RichiestaDettagliImmobileDto toRichiestaDettagliImmobileDto(Richiesta r) {
        if (r == null) return null;
        
        RichiestaDettagliImmobileDto d = new RichiestaDettagliImmobileDto();
        
        // Dati Richiesta
        d.setIdRichiesta(r.getIdRichiesta());
        d.setDataRichiesta(r.getDataRichiesta());
        d.setDataAppuntamento(r.getDataAppuntamento());
        d.setStato(r.getStato() == null ? null : r.getStato().name());
        d.setNoteUtente(r.getNoteUtente());
        d.setMotivoAnnullamento(r.getMotivoAnnullamento());
        
        // Dati Utente (richiedente)
        if (r.getUtente() != null) {
            d.setIdUtente(r.getUtente().getIdUtente());
            d.setNomeUtente(r.getUtente().getNome());
            d.setCognomeUtente(r.getUtente().getCognome());
            d.setEmailUtente(r.getUtente().getEmail());
            d.setTelefonoUtente(r.getUtente().getTelefono());
        }
        
        // Dati Immobile
        if (r.getImmobile() != null) {
            Immobile immobile = r.getImmobile();
            d.setIdImmobile(immobile.getIdImmobile());
            d.setTipologia(immobile.getTipologia() == null ? null : immobile.getTipologia().name());
            d.setIndirizzo(immobile.getIndirizzo());
            d.setCitta(immobile.getCitta());
            d.setProvincia(immobile.getProvincia());
            d.setCap(immobile.getCap());
            d.setStatoImmobile(immobile.getStato() == null ? null : immobile.getStato().name());
            d.setLatitudine(immobile.getLatitudine() == null ? null : immobile.getLatitudine().doubleValue());
            d.setLongitudine(immobile.getLongitudine() == null ? null : immobile.getLongitudine().doubleValue());
            
            // Dati DettagliImmobile - JOIN con Immobile
            com.residea.residea.entities.DettagliImmobile dettagli = dettagliImmobileService.getDettagliByIdImmobile(immobile.getIdImmobile());
            if (dettagli != null) {
                d.setNStanze(dettagli.getNStanze());
                d.setNBagni(dettagli.getNBagni());
                d.setNPiano(dettagli.getNPiano());
                d.setNPianiImmobile(dettagli.getNPianiImmobile());
                d.setBalconeTerrazzo(dettagli.isBalconeTerrazzo());
                d.setGiardino(dettagli.isGiardino());
                d.setGarage(dettagli.isGarage());
                d.setAscensore(dettagli.isAscensore());
                d.setCantina(dettagli.isCantina());
                d.setTipoRiscaldamento(dettagli.getTipoRiscaldamento() == null ? null : dettagli.getTipoRiscaldamento().name());
                d.setAnnoCostruzione(dettagli.getAnnoCostruzione());
                d.setCondizioneImmobile(dettagli.getCondizioneImmobile() == null ? null : dettagli.getCondizioneImmobile().name());
                d.setClasseEnergetica(dettagli.getClasseEnergetica() == null ? null : dettagli.getClasseEnergetica().getDisplayValue());
                d.setEsposizione(dettagli.getEsposizione());
                d.setPrezzo(dettagli.getPrezzo() == null ? null : dettagli.getPrezzo().doubleValue());
            }
        }
        
        return d;
    }

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

    /**
     * GET /api/admin/dashboard/richieste/con-dettagli
     * 
     * Ritorna tutte le richieste con i dettagli completi dell'immobile associato.
     * Combina dati da: Richiesta, Immobile, DettagliImmobile, Utente
     * 
     * @param session Session HTTP
     * @param stato Filtro opzionale per stato richiesta
     * @param idUtente Filtro opzionale per ID utente
     * @param idImmobile Filtro opzionale per ID immobile
     * @return Lista di RichiestaDettagliImmobileDto con dati aggregati
     */
    @GetMapping("/richieste/dettagli")
    public ResponseEntity<List<RichiestaDettagliImmobileDto>> getRichiesteConDettagli(
            HttpSession session,
            @RequestParam(value = "stato", required = false) Richiesta.Stato stato,
            @RequestParam(value = "utente", required = false) Integer idUtente,
            @RequestParam(value = "immobile", required = false) Integer idImmobile) {
        
        List<Richiesta> richieste = richiestaService.getAllRichieste();
        System.out.println("Richieste trovate per con i dettagli: " + richieste.size());

        // Filtri opzionali
        if (stato != null) {
            richieste.removeIf(r -> r.getStato() != stato);
        }
        if (idUtente != null) {
            richieste.removeIf(r -> r.getUtente() == null || !r.getUtente().getIdUtente().equals(idUtente));
        }
        if (idImmobile != null) {
            richieste.removeIf(r -> r.getImmobile() == null || !r.getImmobile().getIdImmobile().equals(idImmobile));
        }

        // Mappa ogni richiesta con i dettagli dell'immobile
        List<RichiestaDettagliImmobileDto> dtos = richieste.stream()
                .map(this::toRichiestaDettagliImmobileDto)
                .toList();
        
        return ResponseEntity.ok(dtos);
    }

}