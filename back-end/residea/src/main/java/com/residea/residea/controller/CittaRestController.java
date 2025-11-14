package com.residea.residea.controller;

import com.residea.residea.entities.Citta;
import com.residea.residea.services.CittaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citta")
public class CittaRestController {

    @Autowired
    private CittaService cittaService;

    // --- READ ---

    // GET /api/citta → restituisce tutte le città
    @GetMapping
    public List<Citta> getAllCitta() {
        return cittaService.getAllCitta();
    }

    // GET /api/citta/{id} → restituisce una città per ID
    @GetMapping("/{id}")
    public Citta getCittaById(@PathVariable Integer id) {
        return cittaService.getCittaById(id);
    }

    // GET /api/citta/nome/{nome} → cerca per nome
    @GetMapping("/nome/{nome}")
    public List<Citta> getCittaByNome(@PathVariable String nome) {
        return cittaService.getCittaByNome(nome);
    }

    // GET /api/citta/provincia/{provincia} → cerca per provincia
    @GetMapping("/provincia/{provincia}")
    public List<Citta> getCittaByProvincia(@PathVariable String provincia) {
        return cittaService.getCittaByProvincia(provincia);
    }

    // GET /api/citta/regione/{regione} → cerca per regione
    @GetMapping("/regione/{regione}")
    public List<Citta> getCittaByRegione(@PathVariable String regione) {
        return cittaService.getCittaByRegione(regione);
    }

    // GET /api/citta/codiceIstat/{codice} → cerca per codice ISTAT
    @GetMapping("/codiceIstat/{codice}")
    public Citta getCittaByCodiceIstat(@PathVariable String codice) {
        return cittaService.getCittaByCodiceIstat(codice);
    }

    // --- CREATE ---

    // POST /api/citta → crea nuova città
    @PostMapping
    public Citta creaCitta(@RequestBody Citta citta) {
        return cittaService.salvaCitta(citta);
    }

    // --- UPDATE ---

    // PUT /api/citta/{id} → aggiorna una città esistente
    @PutMapping("/{id}")
    public Citta aggiornaCitta(@PathVariable Integer id, @RequestBody Citta nuovaCitta) {
        nuovaCitta.setIdCitta(id);
        return cittaService.aggiornaCitta(nuovaCitta);
    }

    // --- DELETE ---

    // DELETE /api/citta/{id} → elimina una città
    @DeleteMapping("/{id}")
    public void eliminaCitta(@PathVariable Integer id) {
        cittaService.eliminaCitta(id);
    }
}
