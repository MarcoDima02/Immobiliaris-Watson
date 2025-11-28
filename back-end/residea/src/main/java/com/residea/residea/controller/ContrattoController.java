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

import com.residea.residea.dto.ContrattoDTO;
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
    public List<ContrattoDTO> getAll() {
        return contrattoService.getAllContratti().stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ContrattoDTO getById(@PathVariable Integer id) {
        Contratto contratto = contrattoService.getContrattoById(id);
        return toDTO(contratto);
    }

    @GetMapping("/tipo/{tipo}")
    public List<ContrattoDTO> getByTipo(@PathVariable String tipo) {
        Contratto.TipoContratto tipoEnum = Contratto.TipoContratto.fromString(tipo);
        if (tipoEnum == null) return List.of();
        return contrattoService.getContrattiByTipo(tipoEnum).stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/immobile/{idImmobile}")
    public List<ContrattoDTO> getByImmobile(@PathVariable Integer idImmobile) {
        return contrattoService.getContrattiByImmobileId(idImmobile).stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/scaduti/{data}")
    public List<ContrattoDTO> getScaduti(@PathVariable String data) {
        LocalDate d = LocalDate.parse(data);
        return contrattoService.getContrattiScaduti(d).stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/in-scadenza/{data}")
    public List<ContrattoDTO> getInScadenza(@PathVariable String data) {
        LocalDate d = LocalDate.parse(data);
        return contrattoService.getContrattiInScadenza(d).stream()
                .map(this::toDTO)
                .toList();
    }

    // --- CREATE ---
    @PostMapping
    public Contratto creaContratto(@RequestBody Contratto contratto) {
        if (contratto.getTipoContratto() != null) {
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

    // --- helper per convertire Contratto -> DTO ---
    private ContrattoDTO toDTO(Contratto c) {
        ContrattoDTO dto = new ContrattoDTO();
        dto.setIdContratto(c.getIdContratto());
        dto.setIdImmobile(c.getIdImmobile() != null ? c.getIdImmobile().getIdImmobile() : null);
        dto.setIdAgente(c.getAgente() != null ? c.getAgente().getIdUtente() : null);
        dto.setTipoContratto(c.getTipoContratto() != null ? c.getTipoContratto().name() : null);
        dto.setDataContratto(c.getDataContratto());
        dto.setDataScadenzaContratto(c.getDataScadenzaContratto());
        dto.setPathContrattoPDF(c.getPathContrattoPDF());
        return dto;
    }
}
