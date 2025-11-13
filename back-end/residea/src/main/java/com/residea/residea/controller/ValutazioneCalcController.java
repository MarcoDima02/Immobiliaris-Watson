package com.residea.residea.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.residea.residea.dto.FormValutazioneRequest;
import com.residea.residea.dto.ValutazioneResultResponse;
import com.residea.residea.services.ValutazioneImmobileService;

@RestController
@RequestMapping("/api/valutazioni")
public class ValutazioneCalcController {

    private final ValutazioneImmobileService valutazioneService;

    public ValutazioneCalcController(ValutazioneImmobileService valutazioneService) {
        this.valutazioneService = valutazioneService;
    }

    @PostMapping(path = "/calculate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ValutazioneResultResponse> calculateFromForm(@RequestBody FormValutazioneRequest request) {
        ValutazioneResultResponse res = valutazioneService.calculateFromRequest(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping(path = "/{idImmobile}/calculate-and-save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ValutazioneResultResponse> calculateAndSave(
            @PathVariable Integer idImmobile,
            @RequestBody FormValutazioneRequest request) {
        ValutazioneResultResponse res = valutazioneService.calculateAndSave(request, idImmobile);
        return ResponseEntity.ok(res);
    }

    @GetMapping(path = "/{idImmobile}/calcolo", produces = "application/json")
    public ResponseEntity<ValutazioneResultResponse> calculateFromSaved(@PathVariable Integer idImmobile) {
        ValutazioneResultResponse res = valutazioneService.calculateFromImmobileId(idImmobile);
        return ResponseEntity.ok(res);
    }
}
