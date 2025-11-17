package com.residea.residea.controller;

import com.residea.residea.entities.DettagliImmobile;
import com.residea.residea.entities.DettagliImmobile.ClasseEnergetica;
import com.residea.residea.entities.DettagliImmobile.CondizioneImmobile;
import com.residea.residea.entities.DettagliImmobile.TipoRiscaldamento;
import com.residea.residea.services.DettagliImmobileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/dettagli")
public class DettagliImmobileController {

    @Autowired
    private DettagliImmobileService dettagliService;

    // ---------------- READ ----------------

    // GET /api/dettagli → tutte le schede dettagli
    @GetMapping
    public List<DettagliImmobile> getAllDettagli() {
        return dettagliService.getAllDettagli();
    }

    // GET /api/dettagli/{id} → dettagli per id immobile
    @GetMapping("/{id}")
    public DettagliImmobile getDettagliById(@PathVariable Integer id) {
        return dettagliService.getDettagliByIdImmobile(id);
    }

    // GET /api/dettagli/prezzo/{prezzo} → dettagli con prezzo minore di
    @GetMapping("/prezzo/{prezzo}")
    public List<DettagliImmobile> getDettagliByPrezzo(@PathVariable BigDecimal prezzo) {
        return dettagliService.getDettagliByPrezzoMinoreDi(prezzo);
    }

    // GET /api/dettagli/classeEnergetica/{classe} → per classe energetica
    @GetMapping("/classeEnergetica/{classe}")
    public List<DettagliImmobile> getDettagliByClasseEnergetica(@PathVariable ClasseEnergetica classe) {
        return dettagliService.getDettagliByClasseEnergetica(classe);
    }

    // GET /api/dettagli/condizione/{condizione} → per condizione immobile
    @GetMapping("/condizione/{condizione}")
    public List<DettagliImmobile> getDettagliByCondizione(@PathVariable CondizioneImmobile condizione) {
        return dettagliService.getDettagliByCondizione(condizione);
    }

    // GET /api/dettagli/tipoRiscaldamento/{tipo} → per tipo riscaldamento
    @GetMapping("/tipoRiscaldamento/{tipo}")
    public List<DettagliImmobile> getDettagliByTipoRiscaldamento(@PathVariable TipoRiscaldamento tipo) {
        return dettagliService.getDettagliByTipoRiscaldamento(tipo);
    }

    // GET /api/dettagli/garage/{garage} → con o senza garage
    @GetMapping("/garage/{garage}")
    public List<DettagliImmobile> getDettagliConGarage(@PathVariable Boolean garage) {
        return dettagliService.getDettagliConGarage(garage);
    }

    // GET /api/dettagli/giardino/{giardino} → con o senza giardino
    @GetMapping("/giardino/{giardino}")
    public List<DettagliImmobile> getDettagliConGiardino(@PathVariable Boolean giardino) {
        return dettagliService.getDettagliConGiardino(giardino);
    }

    // ---------------- CREATE ----------------

    // POST /api/dettagli → crea nuovi dettagli immobile
    @PostMapping
    public DettagliImmobile creaDettagli(@RequestBody DettagliImmobile dettagli) {
        return dettagliService.salvaDettagli(dettagli);
    }

    // ---------------- UPDATE ----------------

    // PUT /api/dettagli/{id} → aggiorna dettagli immobile completo
    @PutMapping("/{id}")
    public DettagliImmobile aggiornaDettagli(@PathVariable Integer id, @RequestBody DettagliImmobile dettagliAggiornati) {
        dettagliAggiornati.setIdImmobile(id);
        return dettagliService.aggiornaDettagli(dettagliAggiornati);
    }

    // PATCH /api/dettagli/{id}/prezzo → aggiorna solo il prezzo
    @PatchMapping("/{id}/prezzo")
    public DettagliImmobile aggiornaPrezzo(@PathVariable Integer id, @RequestBody BigDecimal nuovoPrezzo) {
        return dettagliService.aggiornaPrezzo(id, nuovoPrezzo);
    }

    // ---------------- DELETE ----------------

    // DELETE /api/dettagli/{id} → elimina dettagli immobile
    @DeleteMapping("/{id}")
    public void eliminaDettagli(@PathVariable Integer id) {
        dettagliService.eliminaDettagli(id);
    }
}
