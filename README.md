# Immobiliaris - Portale Immobiliare

Progetto realizzato nell'ambito del Laboratorio Integrato – Digital Strategist, Web e Software Developer.
Un portale innovativo per l'acquisizione di immobili in esclusiva, pensato per modernizzare l'approccio tradizionale dell'agenzia immobiliare Immobiliaris.

**Calendario e Scadenze:** [Calendario 25-26.pdf](./Calendario%2025-26.pdf)

---

## Indice

1. [Descrizione](#descrizione)
2. [Struttura del Progetto](#struttura-del-progetto)
3. [Stack Tecnologico](#stack-tecnologico)
4. [Funzionalità Principali](#funzionalità-principali)
5. [Team](#team)
6. [Target e Area Geografica](#target-e-area-geografica)
7. [Budget e Timeline](#budget-e-timeline)
8. [Media e Canali di Comunicazione](#media-e-canali-di-comunicazione)
9. [SEO Strategy](#seo-strategy)
10. [KPI e Metriche di Successo](#kpi-e-metriche-di-successo)
11. [Stato del Progetto](#stato-del-progetto)
12. [Come Iniziare](#come-iniziare)
13. [Documentazione](#documentazione)
14. [Sicurezza e Privacy](#sicurezza-e-privacy)
15. [Contribuire al Progetto](#contribuire-al-progetto)
16. [Licenza](#licenza)
17. [Note di Sviluppo](#note-di-sviluppo)

---

## Descrizione

Immobiliaris è un'agenzia immobiliare attiva nel territorio piemontese, parte del gruppo Indomus, specializzata nella compravendita di immobili con particolare attenzione all'acquisizione di nuove proprietà da vendere.

Il portale web permetterà di:

- Onboarding proprietari attraverso un form multi-step intuitivo per la raccolta dati sull'immobile
- Valutazione automatica dell'immobile entro 72 ore dalla richiesta
- Gestione contratti di vendita in esclusiva digitali e ottimizzati
- Dashboard amministrativa per la gestione delle richieste e delle lead
- Integrazione marketing con strumenti di automation per massimizzare le conversioni

### Obiettivi del Progetto

Il progetto nasce dall'esigenza di modernizzare l'approccio commerciale, attualmente basato su canali tradizionali (passaparola, volantini, eventi locali), per attrarre un target più giovane (35–55 anni) attraverso:

- Creazione di un portale digitale ispirato a modelli innovativi come Gromia.com
- Strategia di comunicazione integrata multi-canale
- Campagne paid per generare traffico qualificato e conversioni
- Definizione di una USP (Unique Selling Proposition) forte e differenziante
- Piano di lead generation strutturato e misurabile

---

## Struttura del Progetto

```plaintext
/backend          → API REST e logica server (Java + Spring Boot)
/frontend         → Interfaccia utente e UX (HTML/CSS/SCSS/JS/TypeScript)
/database         → Script SQL, schema e migrazioni (MySQL)
/docs             → Documentazione tecnica (Swagger, JSDoc)
/assets           → Immagini, loghi, risorse statiche
/marketing        → Materiali campagne, visual identity, PED
/.github          → Configurazione GitHub Projects e Actions
```

---

## Stack Tecnologico

```plaintext
Backend:          Java 21 + Spring Boot
Frontend:         React, TypeScript, HTML, CSS, SCSS
Database:         MySQL
Marketing:        Facebook Ads, Google Ads, Performance Max
Automation:       CRM, DEM/Newsletter, Marketing Automation
Versionamento:    Git + GitHub
Project Mgmt:     GitHub Projects
Documentazione:   README.md, Swagger, JSDoc
Testing:          JUnit, Jest
Performance:      Lighthouse, PageSpeed Insights
```

Note tecniche:

- Non è consentito utilizzare Bootstrap o WordPress
- Altri framework e librerie possono essere utilizzati solo dopo conferma del mentor
- Responsive design obbligatorio
- Ottimizzazione SEO (on-page, technical, off-page)

---

## Funzionalità Principali

### Onboarding Proprietari

Form multi-step intuitivo e user-friendly per la raccolta dati immobile:

- Informazioni generali (tipologia, ubicazione, metratura)
- Caratteristiche specifiche (numero stanze, stato, anno costruzione)
- Dati proprietario necessari per il form

### Valutazione Automatica

Sistema di valutazione basato su:

- Analisi dati mercato locale
- Algoritmo di pricing dinamico
- Risposta immediata per la valutazione automatica, alla fine del form
- Risposta entro 72 da parte di un agente immobiliare che effettuerà una valutazione specifica

### Gestione Contratti

- Generazione automatica proposte contrattuali
- Firma digitale integrata
- Tracking stato pratiche
- Storico documenti e comunicazioni

### Dashboard Amministrativa (Backoffice)

- Gestione richieste e lead in tempo reale
- CRM integrato per follow-up clienti
- Analytics e reportistica avanzata
- Gestione utenti e permessi
- Monitoraggio campagne marketing

### Marketing Automation

- Integrazione con Meta Ads e Google Ads
- Email automation per nurturing lead
- Retargeting automatico
- A/B testing integrato
- Tracking conversioni e ROI

---

## Team

**Gruppo:** Immobiliaris Watson  
**Referente Aziendale:** Paolo Ghirlinzoni – Immobiliaris / Gruppo Indomus  
**Studio di Sviluppo:** Prisma Studio

### Membri del Team e Ruoli

#### Software Developers

**Marco Dima** – Team Leader & Software Developer – [@MarcoDima02](https://github.com/MarcoDima02)
- Coordinamento generale del progetto
- Architettura backend e database
- Sviluppo API REST con Spring Boot
- Gestione repository e CI/CD
- Integrazione servizi e deployment

**Alessandro Grotta** – Software Developer – [@Grottino](https://github.com/Grottino)
- Sviluppo backend Java + Spring Boot
- Implementazione business logic
- Gestione autenticazione e sicurezza
- Testing e debugging backend
- Ottimizzazione query database

**Andrea Bellissimo** – Software Developer – [@AndreaBellissimo](https://github.com/AndreaBellissimo)
- Sviluppo backend e API REST
- Configurazione e gestione database MySQL
- Implementazione servizi email e automazioni
- Testing e validazione dati
- Documentazione tecnica

#### Web Developers

**Massimo Musso** – Web Developer – [@Max-HKW](https://github.com/Max-HKW)
- Sviluppo frontend React + TypeScript
- Implementazione UI/UX da mockup
- Integrazione API REST
- Form validation e gestione stato
- Responsive design e accessibility

**Davide Martinelli** – Web Developer – [@davide-its](https://github.com/davide-its)
- Sviluppo frontend e componenti React
- Ottimizzazione performance frontend
- SEO on-page e technical (meta tag, structured data)
- Testing cross-browser e responsive
- Implementazione design system

#### Digital Strategists

**Alessia Valendino** – Digital Strategist – [@alessiavalendino](https://github.com/alessiavalendino)
- Analisi buyer persona e competitor
- Definizione strategia di comunicazione
- Gestione campagne Meta Ads
- Content strategy e copywriting
- Analytics e reportistica

**Irene Dabusti** – Digital Strategist – [@irenedabusti](https://github.com/irenedabusti)
- Definizione TOV e linee editoriali
- Proposta visual identity e branding
- Copywriting sito e ottimizzazione SEO
- Social media strategy
- Lead generation e funnel

**Asmaa Tantaoui** – Digital Strategist – [@AsmaaTantaoui](https://github.com/AsmaaTantaoui)
- Gestione campagne Google Ads e Performance Max
- Strategia di conversione lead
- PED (Piano Editoriale Digitale)
- Marketing automation
- A/B testing e ottimizzazione conversioni

---

### Digital Strategist

Responsabilità:

- Analisi buyer persona e competitor
- Definizione TOV (Tone of Voice), linee editoriali, PED
- Proposta logo e visual identity
- Copywriting sito e ottimizzazione SEO
- Gestione campagne paid (Meta, Google Ads, Performance Max)
- Strategia di conversione lead

### Web Developer

Responsabilità:

- Conversione mockup in codice
- UX/UI design e accessibilità (WCAG)
- Validazione dati e form handling
- Integrazione API REST
- SEO on-page e technical (meta tag, structured data, sitemap.xml, robots.txt)
- Performance optimization (Lighthouse score >90)

### Software Developer

Responsabilità:

- Configurazione e gestione database MySQL
- Sviluppo backend con Java + Spring Boot
- Creazione API REST
- Implementazione business logic
- Integrazione CRM e marketing automation
- Testing e deployment

---

## Target e Area Geografica

### Target Principale

- Età: 35–55 anni
- Profilo: Professionisti, famiglie, investitori
- Comportamento: Digitalizzati, ricercano soluzioni rapide e trasparenti
- Esigenza: Vendere immobile velocemente, con servizio affidabile e moderno

### Area Geografica

Piemonte - Città medio-grandi:

- Torino
- Cuneo
- Alessandria
- Asti
- Novara
- Vercelli
- Biella
- Verbania

---

## Budget e Timeline

### Budget Stimato

- Lead Generation (Paid Ads): €1.500/mese per 12 mesi
- Sviluppo Portale: Da definire (architettura, design, SEO, CRM)
- Marketing Automation: Da definire
- Infrastruttura e Hosting: Da definire

### Timeline Progetto

1. Fase 1 - Analisi e Design: 4 Novembre
2. Fase 2 - Sviluppo MVP: [Da definire]
3. Fase 3 - Testing e Ottimizzazione: [Da definire]
4. Fase 4 - Launch e Campagne Paid: [Da definire]
5. Fase 5 - Monitoraggio e Iterazione: [Da definire]

Note: Alla fine del progetto il cliente valuterà il rinnovo e il budget per gli anni successivi.

---

## Media e Canali di Comunicazione

### Social Media

- Facebook – Campagne lead generation, remarketing
- Instagram – Visual storytelling, community building
- WhatsApp Business – Customer care, assistenza immediata
- TikTok (opzionale) – Content marketing per target giovane
- YouTube (opzionale) – Video tour immobili, testimonial

### Strumenti Marketing

- Facebook Ads Manager
- Google Ads (Search, Display, Performance Max)
- CRM integrato
- DEM / Newsletter automation
- Marketing Automation platform
- Analytics e tracking (Google Analytics 4, Meta Pixel)

---

## SEO Strategy

### On-Page SEO

- Ottimizzazione meta title, description, headings
- Structured data (Schema.org per immobili)
- URL SEO-friendly
- Ottimizzazione immagini (alt text, compressione, lazy loading)
- Internal linking strategico

### Technical SEO

- Sitemap.xml dinamica
- Robots.txt ottimizzato
- Performance e Core Web Vitals
- Mobile-first design
- HTTPS e sicurezza
- Canonical tags

### Off-Page SEO

- Link building locale
- Partnership strategiche
- Directory immobiliari
- Guest posting e PR digitale
- (In collaborazione con Digital Strategist)

---

## KPI e Metriche di Successo

### Performance Tecnica

- Lighthouse Score: **>90** (Performance, Accessibility, Best Practices, SEO)
- Page Load Time: **<3 secondi**
- Mobile Usability: **100%**

### Marketing e Business

- **Lead Generation:** Numero richieste valutazione/mese
- **Conversion Rate:** % form compilati → contratti firmati
- **CAC (Customer Acquisition Cost):** Costo per lead qualificato
- **ROI Campagne:** Ritorno investimento paid advertising
- **Engagement Social:** Reach, interazioni, crescita follower

### UX e Qualità

- **Bounce Rate:** <40%
- **Tempo Medio Sessione:** >3 minuti
- **Pagine per Sessione:** >2,5
- **Form Completion Rate:** >60%

---

## Stato del Progetto

In sviluppo - sviluppo frontend e backend, strategia in definizione

### Milestone Completate

1. Briefing iniziale ricevuto
2. Team assemblato
3. Repository GitHub creato
4. Documentazione README iniziale
5. Discussione e individuazione stile e logo per il sito
6. Proposta di rebranding accettata da parte del cliente
7. Inizializzazione e definizione database
8. Sviluppo in fase iniziale/intermedia lato backend e frontend
9. Testing primo endpoint

### Prossimi Step

1. Finalizzazione backend
2. Finalizzazione frontend
3. definizione strategia e lead

---

## Come Iniziare

Questa sezione contiene istruzioni complete per installare e avviare il progetto su una macchina pulita (fisica o virtuale). Le istruzioni sono state testate e garantiscono la replicabilità della release.

### Prerequisiti

Prima di iniziare, assicurati di avere installato:

- **Java Development Kit (JDK) 21** - [Download](https://www.oracle.com/java/technologies/downloads/#java21)
- **Node.js 18+** e **npm** - [Download](https://nodejs.org/)
- **MySQL 8.0+** - [Download](https://dev.mysql.com/downloads/mysql/)
- **Git** - [Download](https://git-scm.com/downloads)
- **Docker** (opzionale, per MailHog) - [Download](https://www.docker.com/products/docker-desktop/)

Verifica le versioni installate:

```bash
java -version        # Dovrebbe mostrare Java 21
node -v              # Dovrebbe mostrare v18.x o superiore
npm -v               # Dovrebbe mostrare 9.x o superiore
mysql --version      # Dovrebbe mostrare 8.0.x
git --version        # Dovrebbe mostrare 2.x
```

### Setup Completo del Progetto

#### 1. Clone del Repository

```bash
# Clona il repository
git clone https://github.com/MarcoDima02/Immobiliaris-Watson.git
cd Immobiliaris-Watson
```

#### 2. Setup Database

```bash
# Avvia MySQL (se non già in esecuzione)
# Windows: Avvia il servizio MySQL dal pannello servizi
# Linux/Mac: sudo systemctl start mysql

# Accedi a MySQL
mysql -u root -p

# Crea il database e importa lo schema
CREATE DATABASE residea;
USE residea;
SOURCE database/init/01-init-schema.sql;

# (Opzionale) Popola il database con dati di esempio
SOURCE database/seeds/popolazioneDb.sql;

# Esci da MySQL
EXIT;
```

**Nota:** Se riscontri problemi di connessione, verifica le credenziali in `back-end/residea/src/main/resources/application.properties`.

#### 3. Setup Backend (Spring Boot)

```bash
# Entra nella directory del backend
cd back-end/residea

# Compila il progetto con Maven Wrapper (Windows)
mvnw.cmd clean install

# Compila il progetto con Maven Wrapper (Linux/Mac)
./mvnw clean install

# Avvia il server Spring Boot (Windows)
mvnw.cmd spring-boot:run

# Avvia il server Spring Boot (Linux/Mac)
./mvnw spring-boot:run
```

Il backend sarà disponibile su: **http://localhost:8080**

**Verifica funzionamento:**
```bash
# Testa un endpoint
curl http://localhost:8080/api/utenti
```

#### 4. Setup Frontend (React + TypeScript)

Apri un **nuovo terminale** (mantieni il backend in esecuzione):

```bash
# Dalla root del progetto, entra nella directory frontend
cd frontend

# Installa le dipendenze
npm install

# Avvia il server di sviluppo
npm run dev
```

Il frontend sarà disponibile su: **http://localhost:5173**

**Verifica funzionamento:** Apri il browser e vai su http://localhost:5173

#### 5. Setup MailHog (Servizio Email - Opzionale)

MailHog è utilizzato in sviluppo per testare l'invio email senza inviare email reali.

```bash
# Con Docker
docker run -d -p 1025:1025 -p 8025:8025 --name mailhog mailhog/mailhog

# Oppure con Docker Compose (dalla root del progetto)
docker-compose -f docker-compose.dev.yml up mailhog -d
```

MailHog Web UI disponibile su: **http://localhost:8025**

### Configurazione Ambiente

#### Variabili d'Ambiente Backend

Il file `back-end/residea/src/main/resources/application.properties` contiene le configurazioni:

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/residea
spring.datasource.username=root
spring.datasource.password=your_password_here

# Server
server.port=8080

# Email (MailHog)
spring.mail.host=localhost
spring.mail.port=1025

# Upload Immagini
immagini.upload-dir=uploads/immagini
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=50MB
```

**Importante:** Modifica `spring.datasource.password` con la password del tuo database MySQL.

#### Variabili d'Ambiente Frontend

Il frontend utilizza Vite. Verifica `frontend/vite.config.ts` per la configurazione del proxy API.

### Struttura Comandi Rapidi

```bash
# Backend
cd back-end/residea
mvnw.cmd spring-boot:run          # Windows
./mvnw spring-boot:run             # Linux/Mac

# Frontend
cd frontend
npm run dev                        # Development server
npm run build                      # Build produzione
npm run preview                    # Preview build produzione

# Database
mysql -u root -p residea < database/init/01-init-schema.sql
mysql -u root -p residea < database/seeds/popolazioneDb.sql

# Docker (MailHog)
docker-compose -f docker-compose.dev.yml up mailhog -d
docker-compose -f docker-compose.dev.yml down
```

### Troubleshooting

#### Problema: Backend non si avvia

**Errore:** `Cannot connect to database`
- Verifica che MySQL sia in esecuzione
- Controlla username/password in `application.properties`
- Assicurati che il database `residea` esista

**Errore:** `Port 8080 already in use`
- Cambia porta in `application.properties`: `server.port=8081`

#### Problema: Frontend non si connette al backend

**Errore:** `Network Error` o `CORS error`
- Verifica che il backend sia in esecuzione su localhost:8080
- Controlla la configurazione proxy in `vite.config.ts`

#### Problema: Immagini non si caricano

- Assicurati che la directory `back-end/residea/uploads/immagini` esista
- Verifica i permessi di scrittura sulla directory
- Controlla la configurazione in `application.properties`

### Ripristino Stato Iniziale del Progetto

Per ripristinare il database allo stato iniziale:

```bash
# Accedi a MySQL
mysql -u root -p

# Elimina e ricrea il database
DROP DATABASE IF EXISTS residea;
CREATE DATABASE residea;
USE residea;

# Importa schema iniziale
SOURCE database/init/01-init-schema.sql;

# Popola con dati di esempio
SOURCE database/seeds/popolazioneDb.sql;

EXIT;
```

### Accesso alle Funzionalità

Una volta avviato il progetto, puoi accedere a:

- **Frontend:** http://localhost:5173
- **Backend API:** http://localhost:8080/api
- **MailHog UI:** http://localhost:8025 (se avviato)
- **Database:** MySQL su localhost:3306

**Utenti di test** (disponibili dopo il seed del database):

| Ruolo | Email | Password |
|-------|-------|----------|
| Amministratore | anna.verdi@example.com | admin123 |
| Agente | sofia.costa@example.com | agente123 |
| Utente | utente@example.com | |

**Nota:** Le password sono hash bcrypt nel database. Verifica il file `database/seeds/popolazioneDb.sql` per i dettagli.

---

## Documentazione

### API Documentation

- Swagger UI: `http://localhost:8080/swagger-ui.html` (Coming soon)
- API Endpoints: Vedi `/docs/api-reference.md` (Coming soon)

### Guides

- Setup Guide: `/docs/setup-guide.md` (Coming soon)
- Contributing: `/docs/CONTRIBUTING.md` (Coming soon)
- Style Guide: `/docs/style-guide.md` (Coming soon)
- SEO Checklist: `/docs/seo-checklist.md` (Coming soon)

---

## Sicurezza e Privacy

- Conformità **GDPR** per gestione dati personali
- Autenticazione e autorizzazione sicure
- Crittografia dati sensibili
- Backup automatici database
- Audit log per operazioni critiche
- SSL/TLS obbligatorio in produzione

---

## Contribuire al Progetto

Il progetto è gestito tramite GitHub Projects con metodologia Agile.

### Strategia di Branching

#### Branch Permanenti

Il progetto mantiene due branch principali che non vengono mai eliminate:

**main**

- Rappresenta il codice in produzione
- Solo codice stabile, testato e rilasciabile
- Protetta da push diretti (solo merge via Pull Request)
- Deploy automatico in ambiente di produzione

**develop**

- Branch di integrazione per lo sviluppo
- Raccoglie tutte le feature completate prima del rilascio
- Ambiente di staging/test
- Base da cui partono tutte le branch temporanee

#### Branch Temporanee (create dalle Issue)

Tutte le branch di lavoro sono temporanee e vengono create automaticamente dalle GitHub Issues:

**Branch da Issue** (es: `23-form-onboarding`, `45-dashboard-admin`)

- Create automaticamente quando si inizia a lavorare su una issue
- Nominate con il numero dell'issue seguito da una descrizione breve
- Si mergiano su `develop` quando completate
- Vengono eliminate dopo il merge completato

**hotfix/** (es: `hotfix/critical-login-error`)

- Solo per bug critici trovati in produzione
- Partono da `main` (non da develop!)
- Si mergiano sia su `main` che su `develop`
- Vengono eliminate dopo il merge

### Flusso di Lavoro Completo

```
main (produzione) ──────────────────────────────────►
  ↑                                                  ↑
  │                                          (10) merge release
  │
develop (staging) ─────────────────────────────────►
  ↑                    ↑
  │ (8) merge      (8) merge
  │
23-form-onboarding    45-dashboard-admin
(issue #23)          (issue #45)
```

### Workflow Passo-Passo

1. **Creazione Issue**: Aprire una issue su GitHub con descrizione dettagliata del task
2. **Assegnazione**: Assegnare la issue ai membri del team responsabili
3. **Creazione Branch**: Creare branch dall'issue (GitHub suggerisce automaticamente il nome)
   ```bash
   # Esempio: dalla issue #23 si crea la branch "23-form-onboarding"
   git checkout develop
   git pull origin develop
   git checkout -b 23-form-onboarding
   ```
4. **Sviluppo**: Lavorare sulla funzionalità con commit frequenti
5. **Commit**: Scrivere commit che referenziano la issue
   ```bash
   git commit -m "feat: implementa validazione form onboarding #23"
   git commit -m "fix: corregge validazione email #23"
   ```
6. **Push**: Pushare la branch sul repository
   ```bash
   git push origin 23-form-onboarding
   ```
7. **Pull Request**: Aprire PR verso `develop` linkando la issue
   - Usare il template con checklist
   - Aggiungere screenshot se necessario
   - Richiedere review ai compagni di team
8. **Code Review**: Almeno un membro del team deve approvare
9. **Merge su develop**: Dopo l'approvazione, merge su `develop`
10. **Testing su Staging**: Verificare che tutto funzioni nell'ambiente di test
11. **Merge su main**: Quando `develop` è stabile, merge su `main` per il rilascio
12. **Chiusura automatica**: La issue viene chiusa automaticamente

### Convenzioni di Naming

**Commit Messages** (Conventional Commits):

- `feat:` nuova funzionalità
- `fix:` correzione bug
- `docs:` modifiche documentazione
- `style:` formattazione codice
- `refactor:` refactoring senza cambiare funzionalità
- `test:` aggiunta/modifica test
- `chore:` task di manutenzione

**Branch**:

- Da issue: `[numero-issue]-[descrizione-breve]` (es: `23-form-onboarding`)
- Hotfix: `hotfix/[descrizione-bug]` (es: `hotfix/critical-login-error`)

**Pull Request**:

- Titolo chiaro e descrittivo
- Template con checklist compilata
- Screenshot/GIF per modifiche UI
- Link alla issue correlata

---

## Licenza

[Da definire con l'azienda cliente]

---

## Note di Sviluppo

### Tecnologie da Approfondire

- Spring Security per autenticazione
- React Query per state management (se si usa React)
- MySQL ottimizzazioni e indexing
- Redis per caching (se necessario)
- Docker per containerizzazione
- CI/CD con GitHub Actions

### Integrazioni Future Possibili

- Google Maps API per geolocalizzazione
- Stripe/PayPal per pagamenti online
- Twilio per notifiche SMS
- SendGrid per email transazionali
- Zapier per automazioni avanzate

---

**Ultimo aggiornamento:** Dicembre 2025
**Versione:** 0.1.0 (Alpha)
**Status:** 🚀 In Development

---
