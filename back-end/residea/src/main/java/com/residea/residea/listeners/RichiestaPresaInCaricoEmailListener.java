package com.residea.residea.listeners;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.residea.residea.events.RichiestaPresaInCaricoEvent;
import com.residea.residea.services.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Listener che invia email al proprietario quando un agente prende in carico la sua richiesta.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RichiestaPresaInCaricoEmailListener {

    private final EmailService emailService;

    @EventListener
    public void onRichiestaPresaInCarico(RichiestaPresaInCaricoEvent event) {
        try {
            // Verifica che l'email del proprietario sia presente
            if (event.getProprietarioEmail() == null || event.getProprietarioEmail().isBlank()) {
                log.debug("No proprietario email available for richiesta {} - skipping email", 
                    event.getIdRichiesta());
                return;
            }

            // Prepara le variabili per il template
            Map<String, Object> vars = new HashMap<>();
            
            // Dati proprietario
            vars.put("proprietarioNome", event.getProprietarioNome() != null ? event.getProprietarioNome() : "");
            vars.put("proprietarioCognome", event.getProprietarioCognome() != null ? event.getProprietarioCognome() : "");
            
            // Dati agente
            vars.put("agenteNome", event.getAgenteNome() != null ? event.getAgenteNome() : "");
            vars.put("agenteCognome", event.getAgenteCognome() != null ? event.getAgenteCognome() : "");
            vars.put("agenteEmail", event.getAgenteEmail() != null ? event.getAgenteEmail() : "");
            vars.put("agenteTelefono", event.getAgenteTelefono() != null ? event.getAgenteTelefono() : "");
            
            // Dati immobile (opzionali)
            vars.put("immobileIndirizzo", event.getImmobileIndirizzo() != null ? event.getImmobileIndirizzo() : "");
            vars.put("immobileCitta", event.getImmobileCitta() != null ? event.getImmobileCitta() : "");
            
            // Dati richiesta
            vars.put("idRichiesta", event.getIdRichiesta());

            // Invia l'email
            String subject = "Un agente ha preso in carico la tua richiesta";
            emailService.sendHtmlEmail(
                event.getProprietarioEmail(), 
                subject, 
                "emails/richiesta-presa-in-carico", 
                vars
            );
            
            log.info("Email presa in carico sent to {} for richiesta {}", 
                event.getProprietarioEmail(), event.getIdRichiesta());
                
        } catch (Exception ex) {
            log.error("Error while sending richiesta presa in carico email for richiesta {}", 
                event.getIdRichiesta(), ex);
        }
    }
}
