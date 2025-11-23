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

### Using Docker Compose (recommended flows)

Here are two common, safe flows to run the app with docker-compose.

- Safe local testing (MailHog)

  * Keep MailHog available and make it the default dev sink. Set the `.env` or service envs like:

    MAIL_HOST=mailhog
    MAIL_PORT=1025
    MAIL_REAL_ENABLED=false

  * Start the standard dev compose:

    docker compose -f docker-compose.dev.yml up -d --build

  The backend will create messages but route them to MailHog (http://localhost:8025) so you can inspect content without sending to real users.

- Real SMTP (explicit opt-in)

  * When you want to send real emails, set your real SMTP values in a local `.env` (never commit this file) and set `MAIL_REAL_ENABLED=true`:

    MAIL_HOST=smtp.example.com
    MAIL_PORT=587
    MAIL_USER=your_user
    MAIL_PASS=your_password_or_api_key
    MAIL_STARTTLS=true
    MAIL_SMTP_AUTH=true
    MAIL_DEFAULT_FROM=noreply@yourdomain.com
    MAIL_REAL_ENABLED=true

  * Restart the backend so it picks these values up:

    docker compose -f docker-compose.dev.yml up -d --build backend

  * The app will attempt real SMTP delivery; check logs and `EmailLog` to verify outcome.

## Simpler option: use a Gmail account (quick, no DNS changes)

If you want to avoid the DNS/domain verification step and just send a few real emails (low volume/testing) you can use a Gmail account's SMTP server. This is the easiest route but note Gmail **has sending limits** and is not ideal for high-volume production.

Steps for Gmail (recommended for quick tests):

1. Make sure the Gmail account has **2‑step verification (2FA)** enabled.
2. Create an **App Password** in the Google account security settings. Use the generated 16‑character app password as your SMTP password.
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

4. Restart the backend with the `.env` in place (example):

  docker compose -f docker-compose.dev.yml up -d --build backend

Limitations & notes:
- Gmail daily sending limits are small (dozens/hundreds per day depending on account), so it's suitable only for low-volume testing.
- Deliverability may be worse than a verified sending domain; emails may land in spam if you don't verify a custom domain.
- For production, a transactional provider (SendGrid/SES/Postmark) with domain verification is recommended.

> Note: an internal test endpoint that previously existed (e.g., POST /api/internal/email/send-test) was removed to avoid accidental real sends. Use the public submission flow (`POST /api/valutazioni/form`) for sending real emails in dev when `MAIL_REAL_ENABLED=true`, or use MailHog for safe, local inspection.

## Nota su deliverability
- Verifica il dominio mittente tramite SPF/DKIM con il provider per evitare che le email finiscano in SPAM.
- Per un testing sicuro, Mailtrap / Mailhog / Mailcatcher sono ottime alternative e permettono debug senza invio reale.

---

Se vuoi, posso:
- aggiungere un flag di runtime `RESIDEA_MAIL_REAL=true/false` per controllare se inviare mail reali o usare MailHog;
- aggiungere un esempio di configurazione per AWS SES / SendGrid e le istruzioni per verificare il dominio;
- aggiungere un test di integrazione che invia una mail mock e verifica che JavaMailSender sia invocato.
