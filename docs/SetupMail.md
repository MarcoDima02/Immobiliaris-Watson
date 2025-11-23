# Configurare invio email reali (production / staging)

Questa guida spiega come passare dall'ambiente di sviluppo (MailHog) ad un provider SMTP reale (es. SendGrid / Mailgun / AWS SES / SMTP relay) in modo sicuro.

Passaggi principali (sintesi):

1) Scegliere un provider SMTP (SendGrid, Mailgun, Mailjet, Amazon SES, ecc.)
2) Ottenere le credenziali (username/password o API key - in alcuni provider `username=apikey`)
3) Configurare le variabili d'ambiente nel deployment (mai committare chiavi nel repo)
4) Verificare il dominio (SPF/DKIM) per miglior deliverability (consigliato)

---

## Variabili d'ambiente supportate

Le proprietà Spring già supportate dal progetto (puoi impostarle come env vars):

- MAIL_HOST (es. smtp.sendgrid.net)
- MAIL_PORT (es. 587)
- MAIL_USER (es. api user)
- MAIL_PASS (es. API key)
- MAIL_SMTP_AUTH (true/false)
- MAIL_STARTTLS (true/false)
- MAIL_DEFAULT_FROM (opzionale, esempio: noreply@tuodominio.com)

Questi valori sono letti da `application.properties` tramite ${...}.

### Esempio di `docker-compose` per testare con SendGrid (NON inserire chiavi nel repo — usare .env o secrets)

```yaml
services:
  backend:
    environment:
      - MAIL_HOST=smtp.sendgrid.net
      - MAIL_PORT=587
      - MAIL_USER=apikey
      - MAIL_PASS=${SENDGRID_API_KEY}
      - MAIL_SMTP_AUTH=true
      - MAIL_STARTTLS=true
      - MAIL_DEFAULT_FROM=noreply@tuodominio.com
```

Usa un file `.env` per iniettare `SENDGRID_API_KEY` in modo sicuro e non committere mai `.env` con segreti.

## Test di consegna (manuale)

1. Imposta le env vars come sopra e avvia il backend (local o container) con i nuovi valori.
2. Invia una richiesta POST a `POST /api/valutazioni/form` coi campi richiesti incluse `emailUtente`.
3. Osserva i log del backend e verifica l'assenza di errori di SMTP.
4. Se ci sono email non consegnate (bounce), il provider dovrebbe fornire feedback nella console (provider dashboard).

### Usare Docker Compose (flussi raccomandati)

Di seguito sono descritti due flussi comunemente usati e sicuri per eseguire l'app con docker-compose.

- Test locale sicuro (MailHog)

  * Mantieni MailHog disponibile e usalo come sink di default in sviluppo. Imposta le variabili nel file `.env` o nelle env del servizio in questo modo:

    MAIL_HOST=mailhog
    MAIL_PORT=1025
    MAIL_REAL_ENABLED=false

  * Avvia l'ambiente di sviluppo standard:

    docker compose -f docker-compose.dev.yml up -d --build

  * In questa modalità l'app creerà i messaggi ma li inoltrerà a MailHog (http://localhost:8025) in modo da poterli ispezionare senza inviare mail reali.

- SMTP reale (opt‑in esplicito)

  * Quando desideri inviare email reali, configura i valori SMTP reali in un file `.env` locale (non committare mai questo file) e imposta `MAIL_REAL_ENABLED=true`:

    MAIL_HOST=smtp.example.com
    MAIL_PORT=587
    MAIL_USER=your_user
    MAIL_PASS=your_password_or_api_key
    MAIL_STARTTLS=true
    MAIL_SMTP_AUTH=true
    MAIL_DEFAULT_FROM=noreply@yourdomain.com
    MAIL_REAL_ENABLED=true

  * Riavvia il backend in modo che legga le nuove variabili:

    docker compose -f docker-compose.dev.yml up -d --build backend

  * L'app tenterà la consegna via SMTP reale; verifica i log del backend e la tabella `EmailLog` per controllare l'esito.

## Opzione più semplice: usare un account Gmail (veloce, senza modifiche DNS)

Se vuoi evitare la fase di verifica del dominio (SPF/DKIM) e inviare solo qualche email reale per test a basso volume, puoi usare il server SMTP di Gmail. È la soluzione più rapida per test veloci, ma tieni presente che Gmail ha limiti di invio e non è adatto per volumi elevati in produzione.

Passaggi per usare Gmail (consigliato per test rapidi):

1. Assicurati che l'account Gmail abbia attivata l'autenticazione a due fattori (2FA).
2. Crea una **App Password** nelle impostazioni di sicurezza dell'account Google. Usa la password generata (16 caratteri) come `MAIL_PASS`/`GMAIL_APP_PASSWORD`.
3. Add the following env vars (example `.env` in project root):

```env
GMAIL_ADDRESS=residea.noreply@gmail.com
GMAIL_APP_PASSWORD=<your-16-char-app-password>
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USER=${GMAIL_ADDRESS}
MAIL_PASS=${GMAIL_APP_PASSWORD}
MAIL_SMTP_AUTH=true
MAIL_STARTTLS=true
MAIL_DEFAULT_FROM=${GMAIL_ADDRESS}
MAIL_REAL_ENABLED=true
```

4. Riavvia il backend con il file `.env` locale in posizione (esempio):

  docker compose -f docker-compose.dev.yml up -d --build backend

- Limitazioni e note:
- I limiti giornalieri di invio di Gmail sono bassi (decine/centinaia di messaggi al giorno a seconda dell'account), quindi è adatto solo per test a basso volume.
- La deliverability potrebbe essere peggiore rispetto a un dominio verificato; le email potrebbero finire nella cartella spam se non verifichi il dominio mittente (SPF/DKIM).
- Per la produzione è raccomandato usare un provider transazionale (SendGrid/SES/Postmark) con verifica del dominio.

> Nota: un endpoint interno di test che esisteva in precedenza (es. POST /api/internal/email/send-test) è stato rimosso per evitare invii reali accidentali. Usa il flusso pubblico di invio (`POST /api/valutazioni/form`) per inviare email reali in sviluppo quando `MAIL_REAL_ENABLED=true`, oppure usa MailHog per ispezionare le email in sicurezza.

## Nota su deliverability
- Verifica il dominio mittente tramite SPF/DKIM con il provider per evitare che le email finiscano in SPAM.
- Per un testing sicuro, Mailtrap / Mailhog / Mailcatcher sono ottime alternative e permettono debug senza invio reale.

---

Se vuoi, posso:
- aggiungere un flag di runtime `RESIDEA_MAIL_REAL=true/false` per controllare se inviare mail reali o usare MailHog;
- aggiungere un esempio di configurazione per AWS SES / SendGrid e le istruzioni per verificare il dominio;
- aggiungere un test di integrazione che invia una mail mock e verifica che JavaMailSender sia invocato.
