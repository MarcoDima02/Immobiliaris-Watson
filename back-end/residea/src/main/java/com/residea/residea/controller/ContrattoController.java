package com.residea.residea.controller;

import java.time.LocalDate;
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

import com.residea.residea.entities.Contratto;
import com.residea.residea.entities.Contratto.TipoContratto;
import com.residea.residea.services.ContrattoService;

@RestController
@RequestMapping("/api/contratti")
public class ContrattoController {
@Autowired
private ContrattoService contrattoService;

// --- READ ---
@GetMapping
public List<Contratto> getAll() {
    return contrattoService.getAllContratti();
}

@GetMapping("/{id}")
public Contratto getById(@PathVariable Integer id) {
    return contrattoService.getContrattoById(id);
}

@GetMapping("/tipo/{tipo}")
public List<Contratto> getByTipo(@PathVariable String tipo) {
    Contratto.TipoContratto tipoEnum = Contratto.TipoContratto.fromString(tipo);
    if (tipoEnum == null) return List.of();
    return contrattoService.getContrattiByTipo(tipoEnum);
}


@GetMapping("/immobile/{idImmobile}")
public List<Contratto> getByImmobile(@PathVariable Integer idImmobile) {
    return contrattoService.getContrattiByImmobileId(idImmobile);
}

@GetMapping("/scaduti/{data}")
public List<Contratto> getScaduti(@PathVariable String data) {
    LocalDate d = LocalDate.parse(data);
    return contrattoService.getContrattiScaduti(d);
}

@GetMapping("/in-scadenza/{data}")
public List<Contratto> getInScadenza(@PathVariable String data) {
    LocalDate d = LocalDate.parse(data);
    return contrattoService.getContrattiInScadenza(d);
}

// --- CREATE ---
@PostMapping
public Contratto creaContratto(@RequestBody Contratto contratto) {
    if (contratto.getTipoContratto() != null) {
        // usa il metodo fromString invece di valueOf
        contratto.setTipoContratto(
            Contratto.TipoContratto.fromString(contratto.getTipoContratto().name())
        );
    }
    return contrattoService.salvaContratto(contratto);
}


// --- UPDATE ---
@PutMapping("/{id}")
public Contratto aggiornaContratto(@PathVariable Integer id, @RequestBody Contratto contratto) {
    contratto.setIdContratto(id);
    return contrattoService.aggiornaContratto(contratto);
}

@PutMapping("/{id}/pdf")
public Contratto aggiornaPDF(@PathVariable Integer id, @RequestBody String nuovoPath) {
    return contrattoService.aggiornaPathContrattoPDF(id, nuovoPath);
}

// --- DELETE ---
@DeleteMapping("/{id}")
public void eliminaContratto(@PathVariable Integer id) {
    contrattoService.eliminaContratto(id);
}


}
