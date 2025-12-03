package com.residea.residea.listeners;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.residea.residea.events.ValutazioneCreatedEvent;
import com.residea.residea.services.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ValutazioneEmailListener {

    private final EmailService emailService;

    @EventListener
    public void onValutazioneCreated(ValutazioneCreatedEvent ev) {
        try {
            if (ev.getUserEmail() == null || ev.getUserEmail().isBlank()) {
                log.debug("No user email available for valutazione {} - skipping email", ev.getIdValutazione());
                return;
            }

            Map<String,Object> vars = new HashMap<>();
            vars.put("id", ev.getIdValutazione());
            vars.put("nome", ev.getUserName() == null ? "utente" : ev.getUserName());
            vars.put("valoreMin", ev.getValoreMin());
            vars.put("valoreMax", ev.getValoreMassimo());
            
            // Dati immobile per riepilogo
            vars.put("tipologia", ev.getTipologia());
            vars.put("indirizzo", ev.getIndirizzo());
            vars.put("citta", ev.getCitta());
            vars.put("provincia", ev.getProvincia());
            vars.put("superficie", ev.getSuperficie());
            vars.put("nStanze", ev.getNStanze());
            vars.put("nBagni", ev.getNBagni());

            emailService.sendHtmlEmail(ev.getUserEmail(), "La tua valutazione è pronta", "emails/valutazione-created", vars);
        } catch (Exception ex) {
            log.error("Error while sending valutazione email for id {}", ev.getIdValutazione(), ex);
        }
    }
}
