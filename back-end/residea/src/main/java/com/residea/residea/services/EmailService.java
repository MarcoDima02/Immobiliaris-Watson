package com.residea.residea.services;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.residea.residea.entities.EmailLog;
import com.residea.residea.repos.EmailLogRepo;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final EmailLogRepo emailLogRepo;
    private final ObjectMapper objectMapper;
    @Value("${mail.default.from:noreply@residea.local}")
    private String defaultFrom;

    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine,
                        EmailLogRepo emailLogRepo,
                        ObjectMapper objectMapper) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.emailLogRepo = emailLogRepo;
        this.objectMapper = objectMapper;
    }

    @Value("${mail.real.enabled:false}")
    private boolean mailRealEnabled;

    @Value("${spring.mail.host:localhost}")
    private String mailHost;

    @Async
    public void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> variables) {

        // serialize variables for logging/persistence
        String varsJson = null;
        try {
            varsJson = variables == null ? null : objectMapper.writeValueAsString(variables);
        } catch (Exception e) {
            varsJson = "{}";
        }

        // persist initial EmailLog row
        EmailLog emailLog = new EmailLog();
        emailLog.setDestinatario(to);
        emailLog.setSubject(subject);
        emailLog.setTemplate(templateName);
        emailLog.setVariablesJson(varsJson);
        emailLog.setStatus("PENDING");
        emailLog.setAttempts(0);
        emailLog.setCreatedAt(LocalDateTime.now());
        emailLog.setUpdatedAt(LocalDateTime.now());
        try {
            emailLog = emailLogRepo.save(emailLog);
        } catch (Exception e) {
            log.warn("Unable to persist EmailLog, continuing send without log persistence", e);
        }

        try {
            Context ctx = new Context();
            if (variables != null) {
                ctx.setVariables(variables);
            }

            String html = templateEngine.process(templateName, ctx);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            helper.setFrom(defaultFrom);
            
            // Aggiungi logo come immagine inline (CID) se presente nel template
            try {
                org.springframework.core.io.ClassPathResource logoResource = 
                    new org.springframework.core.io.ClassPathResource("static/images/residea-logo.png");
                if (logoResource.exists()) {
                    helper.addInline("logo", logoResource);
                    log.debug("Logo aggiunto come immagine inline");
                }
            } catch (Exception e) {
                log.warn("Failed to add logo as inline image: {}", e.getMessage());
            }

            // Safety: only perform real SMTP send if mail.real.enabled=true OR the host isn't a common dev sink
            boolean hostIsDevSink = mailHost == null || mailHost.isBlank() || mailHost.contains("localhost") || mailHost.contains("mailhog");
            if (mailRealEnabled || !hostIsDevSink) {
                mailSender.send(message);
                log.info("Email sent to {} with subject={}", to, subject);
            } else {
                // Dev mode - don't attempt to deliver to external SMTP; if using MailHog, mailSender will still deliver to it
                mailSender.send(message); // allow sending to MailHog or local SMTP when running dev
                log.info("Email created and routed to dev sink (mailHost={}) for {}", mailHost, to);
            }

            // update EmailLog to SENT
            try {
                if (emailLog != null) {
                    emailLog.setStatus("SENT");
                    emailLog.setAttempts(emailLog.getAttempts() + 1);
                    emailLog.setProviderResponse("SENT via SMTP host=" + mailHost);
                    emailLog.setUpdatedAt(LocalDateTime.now());
                    emailLogRepo.save(emailLog);
                }
            } catch (Exception e) {
                log.warn("Failed to update EmailLog to SENT for {}", to, e);
            }

        } catch (Exception ex) {
            log.error("Failed to send email to {}", to, ex);

            // update EmailLog to FAILED
            try {
                if (emailLog != null) {
                    emailLog.setStatus("FAILED");
                    emailLog.setAttempts(emailLog.getAttempts() + 1);
                    emailLog.setProviderResponse(ex.getMessage());
                    emailLog.setUpdatedAt(LocalDateTime.now());
                    emailLogRepo.save(emailLog);
                }
            } catch (Exception e2) {
                log.warn("Failed to update EmailLog to FAILED for {}", to, e2);
            }
        }
    }

    /**
     * Invia email HTML con allegato PDF
     */
    @Async
    public void sendHtmlEmailWithAttachment(String to, String subject, String templateName, 
                                           Map<String, Object> variables, File attachment, String attachmentName) {
        
        // serialize variables for logging/persistence
        String varsJson = null;
        try {
            varsJson = variables == null ? null : objectMapper.writeValueAsString(variables);
        } catch (Exception e) {
            varsJson = "{}";
        }

        // persist initial EmailLog row
        EmailLog emailLog = new EmailLog();
        emailLog.setDestinatario(to);
        emailLog.setSubject(subject);
        emailLog.setTemplate(templateName);
        emailLog.setVariablesJson(varsJson);
        emailLog.setStatus("PENDING");
        emailLog.setAttempts(0);
        emailLog.setCreatedAt(LocalDateTime.now());
        emailLog.setUpdatedAt(LocalDateTime.now());
        try {
            emailLog = emailLogRepo.save(emailLog);
        } catch (Exception e) {
            log.warn("Unable to persist EmailLog, continuing send without log persistence", e);
        }

        try {
            Context ctx = new Context();
            if (variables != null) {
                ctx.setVariables(variables);
            }

            String html = templateEngine.process(templateName, ctx);

            MimeMessage message = mailSender.createMimeMessage();
            // true = multipart per supportare allegati
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            helper.setFrom(defaultFrom);
            
            // Aggiungi logo come immagine inline (CID)
            try {
                org.springframework.core.io.ClassPathResource logoResource = 
                    new org.springframework.core.io.ClassPathResource("static/images/residea-logo.png");
                if (logoResource.exists()) {
                    helper.addInline("logo", logoResource);
                    log.debug("Logo aggiunto come immagine inline");
                }
            } catch (Exception e) {
                log.warn("Failed to add logo as inline image: {}", e.getMessage());
            }
            
            // Aggiungi allegato se presente
            if (attachment != null && attachment.exists()) {
                helper.addAttachment(attachmentName != null ? attachmentName : attachment.getName(), attachment);
                log.debug("Allegato aggiunto: {}", attachmentName);
            }

            // Safety: only perform real SMTP send if mail.real.enabled=true OR the host isn't a common dev sink
            boolean hostIsDevSink = mailHost == null || mailHost.isBlank() || mailHost.contains("localhost") || mailHost.contains("mailhog");
            if (mailRealEnabled || !hostIsDevSink) {
                mailSender.send(message);
                log.info("Email with attachment sent to {} with subject={}", to, subject);
            } else {
                // Dev mode - don't attempt to deliver to external SMTP; if using MailHog, mailSender will still deliver to it
                mailSender.send(message);
                log.info("Email with attachment created and routed to dev sink (mailHost={}) for {}", mailHost, to);
            }

            // update EmailLog to SENT
            try {
                if (emailLog != null) {
                    emailLog.setStatus("SENT");
                    emailLog.setAttempts(emailLog.getAttempts() + 1);
                    emailLog.setProviderResponse("SENT via SMTP host=" + mailHost + " (with attachment)");
                    emailLog.setUpdatedAt(LocalDateTime.now());
                    emailLogRepo.save(emailLog);
                }
            } catch (Exception e) {
                log.warn("Failed to update EmailLog to SENT for {}", to, e);
            }

        } catch (Exception ex) {
            log.error("Failed to send email with attachment to {}", to, ex);

            // update EmailLog to FAILED
            try {
                if (emailLog != null) {
                    emailLog.setStatus("FAILED");
                    emailLog.setAttempts(emailLog.getAttempts() + 1);
                    emailLog.setProviderResponse(ex.getMessage());
                    emailLog.setUpdatedAt(LocalDateTime.now());
                    emailLogRepo.save(emailLog);
                }
            } catch (Exception e2) {
                log.warn("Failed to update EmailLog to FAILED for {}", to, e2);
            }
        }
    }
}
