package com.residea.residea.listeners;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.residea.residea.events.ContrattoAllegatoEvent;
import com.residea.residea.services.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Listener che invia email al proprietario quando un agente allega un contratto.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ContrattoAllegatoEmailListener {

    private final EmailService emailService;
    
    @Value("${contratti.upload-dir}")
    private String uploadDir;

    @EventListener
    public void onContrattoAllegato(ContrattoAllegatoEvent event) {
        try {
            // Verifica che l'email del proprietario sia presente
            if (event.getProprietarioEmail() == null || event.getProprietarioEmail().isBlank()) {
                log.debug("No proprietario email available for contratto {} - skipping email", 
                    event.getIdContratto());
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
            
            // Dati contratto
            vars.put("tipoContratto", event.getTipoContratto() != null ? event.getTipoContratto() : "");
            vars.put("dataContratto", event.getDataContratto() != null ? event.getDataContratto() : "");
            
            // Dati immobile
            vars.put("immobileIndirizzo", event.getImmobileIndirizzo() != null ? event.getImmobileIndirizzo() : "");
            vars.put("immobileCitta", event.getImmobileCitta() != null ? event.getImmobileCitta() : "");
            
            // ID contratto
            vars.put("idContratto", event.getIdContratto());

            // Prepara l'allegato PDF
            File attachmentFile = null;
            String attachmentName = "contratto.pdf";
            
            if (event.getPathContrattoPDF() != null && !event.getPathContrattoPDF().isBlank()) {
                // Il path è tipo "/uploads/contratti/123456_contratto.pdf"
                // Dobbiamo costruire il percorso completo del file
                String filename = event.getPathContrattoPDF().substring(event.getPathContrattoPDF().lastIndexOf("/") + 1);
                File file = new File(uploadDir, filename);
                
                if (file.exists() && file.canRead()) {
                    attachmentFile = file;
                    attachmentName = "Contratto_" + event.getTipoContratto() + ".pdf";
                    log.debug("Contratto PDF found: {}", file.getAbsolutePath());
                } else {
                    log.warn("Contratto PDF not found or not readable: {}", file.getAbsolutePath());
                }
            }

            // Invia l'email con allegato
            String subject = "Il tuo contratto è pronto";
            emailService.sendHtmlEmailWithAttachment(
                event.getProprietarioEmail(), 
                subject, 
                "emails/contratto-allegato", 
                vars,
                attachmentFile,
                attachmentName
            );
            
            log.info("Email contratto allegato sent to {} for contratto {}", 
                event.getProprietarioEmail(), event.getIdContratto());
                
        } catch (Exception ex) {
            log.error("Error while sending contratto allegato email for contratto {}", 
                event.getIdContratto(), ex);
        }
    }
}
