# 📋 Catalogo Completo API Backend - Residea

**Ultimo aggiornamento:** 3 Dicembre 2025  
**Branch:** main

---

## 📑 Indice

1. [Autenticazione & Utenti](#1-autenticazione--utenti)
2. [Dashboard Agente](#2-dashboard-agente)
3. [Dashboard Amministratore](#3-dashboard-amministratore)
4. [Immobili & Dettagli](#4-immobili--dettagli)
5. [Immagini](#5-immagini)
6. [Contratti](#6-contratti)
7. [Richieste Valutazione](#7-richieste-valutazione)
8. [Valutazioni](#8-valutazioni)
9. [Vendite](#9-vendite)
10. [Lead](#10-lead)
11. [Città & Prezzi](#11-città--prezzi)
12. [Upload File](#12-upload-file)
13. [View (Thymeleaf)](#13-view-thymeleaf)

---

## 1. Autenticazione & Utenti

**Controller:** `UtenteRestController.java`  
**Base Path:** `/api/utenti`

| Metodo | Endpoint | Descrizione | Request Body | Response | Note |
|--------|----------|-------------|--------------|----------|------|
| GET | `/api/utenti` | Lista tutti gli utenti | - | `List<Utente>` | Nasconde passwordHash |
| GET | `/api/utenti/{id}` | Dettagli utente per ID | - | `Utente` | Nasconde passwordHash |
| GET | `/api/utenti/telefono/{telefono}` | Cerca utente per telefono | - | `Utente` | - |
| POST | `/api/utenti` | Crea nuovo utente | `Utente` | `Utente` | Ruolo normalizzato |
| POST | `/api/utenti/login` | Login utente | `{email, password}` | `Utente` | Usa sessione HTTP |
| PUT | `/api/utenti/{id}` | Aggiorna utente completo | `Utente` | `Utente` | - |
| PUT | `/api/utenti/{id}/ruolo` | Cambia solo ruolo | `{ruolo}` | `Utente` | PROPRIETARIO/AGENTE/AMMINISTRATORE |

**Email Correlate:**
- Nessuna email diretta (email inviate da altri eventi)

---

## 2. Dashboard Agente

**Controller:** `AgenteController.java`  
**Base Path:** `/api/agente`

### 2.1 Dashboard & Statistiche

| Metodo | Endpoint | Descrizione | Parametri | Response | DTO |
|--------|----------|-------------|-----------|----------|-----|
| GET | `/api/agente/dashboard/{idAgente}` | Dati aggregati dashboard agente | `idAgente` | `List<RichiestaDettagliImmobileDto>` | DTO UNIFICATO |
| GET | `/api/agente/contratti/{idAgente}` | Contratti gestiti dall'agente | `idAgente` | `List<RichiestaDettagliImmobileDto>` | DTO UNIFICATO |
| GET | `/api/agente/{idAgente}/dashboard/stats` | Statistiche contatori | `idAgente` | `DashboardStatsDTO` | Contatori richieste |
| GET | `/api/agente/{idAgente}/acquisizioni` | Acquisizioni (contratti completati) | `idAgente` | `List<AcquisizioneDTO>` | - |

### 2.2 Gestione Richieste

| Metodo | Endpoint | Descrizione | Parametri | Response | Email |
|--------|----------|-------------|-----------|----------|-------|
| GET | `/api/agente/richieste/attesa` | Richieste IN_ATTESA (non assegnate) | - | `List<RichiestaCardDTO>` | - |
| GET | `/api/agente/{idAgente}/richieste/carico` | Richieste prese in carico | `idAgente` | `List<RichiestaCardDTO>` | - |
| GET | `/api/agente/richieste-prese-in-carico/{idAgente}` | Richieste prese in carico (dettagliate) | `idAgente` | `List<RichiestaDettagliImmobileDto>` | - |
| GET | `/api/agente/richiesta-dettagli/{idContratto}` | Dettagli singola richiesta | `idContratto` | `RichiestaDettagliImmobileDto` | - |
| POST | `/api/agente/{idAgente}/richieste/{idRichiesta}/prendi-in-carico` | Prendi in carico richiesta | `idAgente`, `idRichiesta` | `String` | ✅ **Email presa in carico** |
| PUT | `/api/agente/{idAgente}/richieste/{idRichiesta}/stato` | Cambia stato richiesta | `idAgente`, `idRichiesta`, `?nuovoStato` | `String` | - |

**Email Inviate:**
- **Email Presa in Carico:** Quando agente prende in carico richiesta
  - Template: `emails/richiesta-presa-in-carico.html`
  - Destinatario: Proprietario immobile
  - Evento: `RichiestaPresaInCaricoEvent`

---

## 3. Dashboard Amministratore

**Controller:** `AmministratoreDashboard.java`  
**Base Path:** `/api/admin/dashboard`

### 3.1 Gestione Utenti

| Metodo | Endpoint | Descrizione | Query Params | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/admin/dashboard/utenti` | Lista utenti con filtri | `?ruolo`, `?nome`, `?cognome`, `?email` | `List<UtenteDto>` |

### 3.2 Gestione Immobili

| Metodo | Endpoint | Descrizione | Query Params | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/admin/dashboard/immobili` | Lista immobili con filtri | `?tipologia`, `?citta`, `?provincia`, `?prezzoMin`, `?prezzoMax` | `List<ImmobileDto>` |
| GET | `/api/admin/dashboard/immagini` | Lista immagini con filtri | `?idImmobile`, `?copertina` | `List<ImmagineDto>` |

### 3.3 Gestione Contratti

| Metodo | Endpoint | Descrizione | Query Params | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/admin/dashboard/contratti` | Lista contratti con filtri | `?tipo`, `?agenteId`, `?immobileId`, `?scaduto` | `List<ContrattoDto>` |
| POST | `/api/admin/dashboard/contratti` | Crea contratto | - | `Contratto` |
| PUT | `/api/admin/dashboard/contratti/{id}` | Aggiorna contratto | - | `Contratto` |

### 3.4 Gestione Richieste

| Metodo | Endpoint | Descrizione | Query Params | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/admin/dashboard/richieste` | Lista richieste con filtri | `?stato`, `?utenteId`, `?dataInizio`, `?dataFine` | `List<RichiestaDto>` |
| GET | `/api/admin/dashboard/richieste/dettagli` | Dettagli aggregati richieste | `?stato`, `?agenteId` | `List<RichiestaDettagliDto>` |
| POST | `/api/admin/dashboard/richieste` | Crea richiesta | - | `Richiesta` |
| PUT | `/api/admin/dashboard/richieste/{id}` | Aggiorna richiesta | - | `Richiesta` |

### 3.5 Gestione Vendite

| Metodo | Endpoint | Descrizione | Query Params | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/admin/dashboard/vendite` | Lista vendite con filtri | `?dataInizio`, `?dataFine`, `?agenteId`, `?immobileId` | `List<VenditaDto>` |
| POST | `/api/admin/dashboard/vendite` | Crea vendita | - | `Vendita` |
| PUT | `/api/admin/dashboard/vendite/{id}` | Aggiorna vendita | - | `Vendita` |

---

## 4. Immobili & Dettagli

### 4.1 Immobili

**Controller:** `ImmobileController.java`  
**Base Path:** `/api/immobili`

| Metodo | Endpoint | Descrizione | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/immobili` | Lista tutti immobili | - | `List<Immobile>` |
| GET | `/api/immobili/{id}` | Immobile per ID | - | `Immobile` |
| GET | `/api/immobili/proprietario/{idUtente}` | Immobili di un proprietario | - | `List<Immobile>` |
| GET | `/api/immobili/tipologia/{tipologia}` | Immobili per tipologia | - | `List<Immobile>` |
| GET | `/api/immobili/dashboard/all` | Tutti immobili con dettagli completi | - | `List<ImmobileListDTO>` |
| GET | `/api/immobili/dashboard/{id}` | Dettagli completi immobile | - | `ImmobileListDTO` |
| POST | `/api/immobili` | Crea immobile | `Immobile` | `Immobile` |
| POST | `/api/immobili/{idImmobile}/immagini` | **Upload immagini immobile** | `MultipartFile[]` | `List<Immagine>` |
| PUT | `/api/immobili` | Aggiorna immobile | `Immobile` | `Immobile` |

**Tipologie:** APPARTAMENTO, VILLA, TERRENO, UFFICIO, NEGOZIO, MAGAZZINO, GARAGE, ATTICO, LOFT, RUSTICO

### 4.2 Dettagli Immobile

**Controller:** `DettagliImmobileController.java`  
**Base Path:** `/api/dettagli`

| Metodo | Endpoint | Descrizione | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/dettagli` | Lista tutti dettagli | - | `List<DettagliImmobile>` |
| GET | `/api/dettagli/{id}` | Dettagli per ID | - | `DettagliImmobile` |
| GET | `/api/dettagli/prezzo/{prezzo}` | Dettagli per prezzo | - | `List<DettagliImmobile>` |
| GET | `/api/dettagli/classeEnergetica/{classe}` | Per classe energetica | - | `List<DettagliImmobile>` |
| GET | `/api/dettagli/condizione/{condizione}` | Per condizione | - | `List<DettagliImmobile>` |
| GET | `/api/dettagli/tipoRiscaldamento/{tipo}` | Per riscaldamento | - | `List<DettagliImmobile>` |
| GET | `/api/dettagli/garage/{garage}` | Con/senza garage | - | `List<DettagliImmobile>` |
| GET | `/api/dettagli/giardino/{giardino}` | Con/senza giardino | - | `List<DettagliImmobile>` |
| POST | `/api/dettagli` | Crea dettagli | `DettagliImmobile` | `DettagliImmobile` |
| PUT | `/api/dettagli/{id}` | Aggiorna dettagli | `DettagliImmobile` | `DettagliImmobile` |
| PATCH | `/api/dettagli/{id}/prezzo` | Aggiorna solo prezzo | `{prezzo}` | `DettagliImmobile` |
| DELETE | `/api/dettagli/{id}` | Elimina dettagli | - | `void` |

### 4.3 Superfici

**Controller:** `SuperficieController.java`  
**Base Path:** `/api/superficie`

| Metodo | Endpoint | Descrizione | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/superficie` | Lista tutte superfici | - | `List<Superficie>` |
| GET | `/api/superficie/{idImmobile}` | Superficie per immobile | - | `Superficie` |
| POST | `/api/superficie` | Crea superficie | `Superficie` | `Superficie` |
| PUT | `/api/superficie/{idImmobile}` | Aggiorna superficie | `Superficie` | `Superficie` |
| DELETE | `/api/superficie/{idImmobile}` | Elimina superficie | - | `void` |

---

## 5. Immagini

**Controller:** `ImmagineController.java`  
**Base Path:** `/api/immagini`

| Metodo | Endpoint | Descrizione | Request Body | Response | Note |
|--------|----------|-------------|--------------|----------|------|
| GET | `/api/immagini` | Lista tutte immagini (metadati) | - | `List<Immagine>` | Solo metadati DB |
| GET | `/api/immagini/{id}` | Immagine per ID | - | `Immagine` | Metadati |
| GET | `/api/immagini/immobile/{idImmobile}` | Immagini di un immobile | - | `List<Immagine>` | Metadati |
| POST | `/api/immagini` | Crea record immagine | `Immagine` | `Immagine` | Solo metadati |
| PUT | `/api/immagini/{id}` | Aggiorna immagine | `Immagine` | `Immagine` | Solo metadati |
| DELETE | `/api/immagini/{id}` | Elimina immagine | - | `void` | - |

**File Fisici:**
- Upload: `POST /api/immobili/{idImmobile}/immagini` (ImmobileController)
- Visualizzazione: `GET /uploads/immagini/{filename}` (StaticResourceConfig)

---

## 6. Contratti

### 6.1 CRUD Contratti

**Controller:** `ContrattoController.java`  
**Base Path:** `/api/contratti`

| Metodo | Endpoint | Descrizione | Request Body | Response | Email |
|--------|----------|-------------|--------------|----------|-------|
| GET | `/api/contratti` | Lista tutti contratti | - | `List<Contratto>` | - |
| GET | `/api/contratti/{id}` | Contratto per ID | - | `Contratto` | - |
| GET | `/api/contratti/tipo/{tipo}` | Contratti per tipo | - | `List<Contratto>` | - |
| GET | `/api/contratti/immobile/{idImmobile}` | Contratti di un immobile | - | `List<Contratto>` | - |
| GET | `/api/contratti/scaduti/{data}` | Contratti scaduti | - | `List<Contratto>` | - |
| GET | `/api/contratti/in-scadenza/{data}` | Contratti in scadenza | - | `List<Contratto>` | - |
| POST | `/api/contratti` | Crea contratto | `Contratto` | `Contratto` | - |
| PUT | `/api/contratti/{id}` | Aggiorna contratto | `Contratto` | `Contratto` | - |
| PATCH | `/api/contratti/{id}/allega-pdf` | **Allega PDF contratto** | `{pathPDF}` | `Contratto` | ✅ **Email con PDF allegato** |
| DELETE | `/api/contratti/{id}` | Elimina contratto | - | `void` | - |

**Tipi Contratto:** ESCLUSIVO, NON_ESCLUSIVO, VENDITA, AFFITTO

### 6.2 Visualizzazione PDF

**Controller:** `ContrattiController.java`  
**Base Path:** `/api/contratti-pdf`

| Metodo | Endpoint | Descrizione | Response |
|--------|----------|-------------|----------|
| GET | `/api/contratti-pdf/pdf/{filename}` | Serve file PDF contratto | `byte[]` (PDF) |

**Email Inviate:**
- **Email Contratto Allegato:** Quando viene caricato il PDF del contratto
  - Template: `emails/contratto-allegato.html`
  - Destinatario: Proprietario immobile
  - Allegato: PDF del contratto
  - Evento: `ContrattoAllegatoEvent`

---

## 7. Richieste Valutazione

**Controller:** `RichiestaController.java`  
**Base Path:** `/api/richieste`

| Metodo | Endpoint | Descrizione | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/richieste` | Lista tutte richieste | - | `List<Richiesta>` |
| GET | `/api/richieste/{id}` | Richiesta per ID | - | `Richiesta` |
| GET | `/api/richieste/utente/{idUtente}` | Richieste di un utente | - | `List<Richiesta>` |
| GET | `/api/richieste/immobile/{idImmobile}` | Richieste per immobile | - | `List<Richiesta>` |
| GET | `/api/richieste/stato/{stato}` | Richieste per stato | - | `List<Richiesta>` |
| POST | `/api/richieste` | Crea richiesta | `Richiesta` | `Richiesta` |
| PUT | `/api/richieste/{id}` | Aggiorna richiesta | `Richiesta` | `Richiesta` |
| DELETE | `/api/richieste/{id}` | Elimina richiesta | - | `void` |

**Stati Richiesta:** IN_ATTESA, IN_ELABORAZIONE, COMPLETATA, ANNULLATA

---

## 8. Valutazioni

### 8.1 Form Valutazione (Pubblico)

**Controller:** `ValutazioneFormController.java`  
**Base Path:** `/api/valutazioni/form`

| Metodo | Endpoint | Descrizione | Request Body | Response | Email |
|--------|----------|-------------|--------------|----------|-------|
| POST | `/api/valutazioni/form` | Crea valutazione da form pubblico | `FormValutazioneRequest` | `ValutazioneResultResponse` | ✅ **Email valutazione pronta** |

**Email Inviate:**
- **Email Valutazione Creata:** Quando utente compila form valutazione
  - Template: `emails/valutazione-created.html`
  - Destinatario: Utente che ha richiesto valutazione
  - Contenuto: Link valutazione, valore min/max
  - Evento: `ValutazioneCreatedEvent`

### 8.2 Calcolo Valutazioni

**Controller:** `ValutazioneCalcController.java`  
**Base Path:** `/api/valutazione`

| Metodo | Endpoint | Descrizione | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| POST | `/api/valutazione/calculate` | Calcola valutazione (solo calcolo) | `ValutazioneRequest` | `ValutazioneResponse` |
| POST | `/api/valutazione/{idImmobile}/calculate-and-save` | Calcola e salva valutazione | `ValutazioneRequest` | `ValutazioneResponse` |
| GET | `/api/valutazione/{idImmobile}/calcolo` | Recupera valutazione salvata | - | `ValutazioneResponse` |

---

## 9. Vendite

**Controller:** `VenditaController.java`  
**Base Path:** `/api/vendite`

| Metodo | Endpoint | Descrizione | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/vendite` | Lista tutte vendite | - | `List<Vendita>` |
| GET | `/api/vendite/{id}` | Vendita per ID | - | `Vendita` |
| GET | `/api/vendite/utente/{idUtente}` | Vendite di un utente | - | `List<Vendita>` |
| GET | `/api/vendite/immobile/{idImmobile}` | Vendite di un immobile | - | `List<Vendita>` |
| POST | `/api/vendite` | Crea vendita | `Vendita` | `Vendita` |
| PUT | `/api/vendite/{id}` | Aggiorna vendita | `Vendita` | `Vendita` |
| DELETE | `/api/vendite/{id}` | Elimina vendita | - | `void` |

---

## 10. Lead

**Controller:** `LeadController.java`  
**Base Path:** `/api/leads`

| Metodo | Endpoint | Descrizione | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/leads` | Lista tutti lead | - | `List<Lead>` |
| GET | `/api/leads/{id}` | Lead per ID | - | `Lead` |
| GET | `/api/leads/email/{email}` | Lead per email | - | `Lead` |
| GET | `/api/leads/citta/{citta}` | Lead per città | - | `List<Lead>` |
| GET | `/api/leads/utente/{idUtente}` | Lead di un utente | - | `List<Lead>` |
| POST | `/api/leads` | Crea lead | `Lead` | `Lead` |
| PUT | `/api/leads/{id}` | Aggiorna lead | `Lead` | `Lead` |
| DELETE | `/api/leads/{id}` | Elimina lead | - | `void` |

---

## 11. Città & Prezzi

### 11.1 Città

**Controller:** `CittaRestController.java`  
**Base Path:** `/api/citta`

| Metodo | Endpoint | Descrizione | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/citta` | Lista tutte città | - | `List<Citta>` |
| GET | `/api/citta/{id}` | Città per ID | - | `Citta` |
| GET | `/api/citta/nome/{nome}` | Città per nome | - | `Citta` |
| GET | `/api/citta/provincia/{provincia}` | Città per provincia | - | `List<Citta>` |
| GET | `/api/citta/regione/{regione}` | Città per regione | - | `List<Citta>` |
| GET | `/api/citta/codiceIstat/{codice}` | Città per codice ISTAT | - | `Citta` |
| POST | `/api/citta` | Crea città | `Citta` | `Citta` |
| PUT | `/api/citta/{id}` | Aggiorna città | `Citta` | `Citta` |
| DELETE | `/api/citta/{id}` | Elimina città | - | `void` |

### 11.2 Prezzi per CAP

**Controller:** `PrezzoPerCapController.java`  
**Base Path:** `/api/prezzi-cap`

| Metodo | Endpoint | Descrizione | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/prezzi-cap` | Lista tutti prezzi | - | `List<PrezzoPerCap>` |
| GET | `/api/prezzi-cap/{cap}` | Prezzo per CAP | - | `PrezzoPerCap` |
| GET | `/api/prezzi-cap/citta/{idCitta}` | Prezzi per città | - | `List<PrezzoPerCap>` |
| GET | `/api/prezzi-cap/validFromAfter/{date}` | Prezzi validi dopo data | - | `List<PrezzoPerCap>` |
| GET | `/api/prezzi-cap/validToBefore/{date}` | Prezzi validi prima data | - | `List<PrezzoPerCap>` |
| POST | `/api/prezzi-cap` | Crea prezzo CAP | `PrezzoPerCap` | `PrezzoPerCap` |
| PUT | `/api/prezzi-cap/{cap}` | Aggiorna prezzo CAP | `PrezzoPerCap` | `PrezzoPerCap` |
| DELETE | `/api/prezzi-cap/{cap}` | Elimina prezzo CAP | - | `void` |

---

## 12. Upload File

**Controller:** `UploadPdfController.java`  
**Base Path:** `/api/upload`

| Metodo | Endpoint | Descrizione | Request Body | Response | Note |
|--------|----------|-------------|--------------|----------|------|
| POST | `/api/upload/pdf` | Upload file PDF | `MultipartFile` | `String` (path) | Solo PDF, max 10MB |

**Directory Upload:**
- Contratti: `uploads/contratti/`
- Immagini: `uploads/immagini/`

**Visualizzazione File:**
- `/uploads/contratti/{filename}` - Serve PDF contratti (StaticResourceConfig)
- `/uploads/immagini/{filename}` - Serve immagini immobili (StaticResourceConfig)

---

## 13. View (Thymeleaf)

**Controller:** `ViewController.java`  
**Base Path:** `/`

| Metodo | Endpoint | Template | Descrizione |
|--------|----------|----------|-------------|
| GET | `/utenti` | `utenti.html` | Pagina gestione utenti |
| GET | `/immobili` | `immobili.html` | Pagina gestione immobili |
| GET | `/leads` | `lead.html` | Pagina gestione lead |
| GET | `/contratti` | `contratti.html` | Pagina gestione contratti |
| GET | `/amministratore` | `AmministratoreDashboard.html` | Dashboard amministratore |
| GET | `/dashboard-utenti` | `dashboard-utenti.html` | Dashboard utenti |
| GET | `/dashboard-immobili` | `dashboard-immobili.html` | Dashboard immobili |
| GET | `/dashboard-contratti` | `dashboard-contratti.html` | Dashboard contratti |
| GET | `/dashboard-leads` | `dashboard-leads.html` | Dashboard lead |
| GET | `/dashboard-richieste` | `dashboard-richieste.html` | Dashboard richieste |
| GET | `/dashboard-vendite` | `dashboard-vendite.html` | Dashboard vendite |

---

## 📧 Sistema Email - Riepilogo

### Eventi e Template

| Evento | Quando | Template | Destinatario | Allegati |
|--------|--------|----------|--------------|----------|
| `ValutazioneCreatedEvent` | Form valutazione compilato | `emails/valutazione-created.html` | Utente richiedente | - |
| `RichiestaPresaInCaricoEvent` | Agente prende in carico richiesta | `emails/richiesta-presa-in-carico.html` | Proprietario immobile | - |
| `ContrattoAllegatoEvent` | PDF contratto caricato | `emails/contratto-allegato.html` | Proprietario immobile | ✅ PDF contratto |

### Listener

- `ValutazioneEmailListener.java`
- `RichiestaPresaInCaricoEmailListener.java`
- `ContrattoAllegatoEmailListener.java`

### Configurazione Email

**EmailService:** `EmailService.java`
- SMTP: MailHog (dev) - localhost:1025
- Template Engine: Thymeleaf
- Metodi:
  - `sendHtmlEmail()` - Email senza allegati
  - `sendHtmlEmailWithAttachment()` - Email con PDF

---

## 🔧 Configurazione

### Application Properties

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3307/residea
spring.datasource.username=root
spring.datasource.password=password

# Upload
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=50MB
contratti.upload-dir=uploads/contratti
immagini.upload-dir=uploads/immagini

# Email
spring.mail.host=localhost
spring.mail.port=1025
spring.mail.username=
spring.mail.password=
```

---

## 📊 DTO Principali

| DTO | Scopo | Campi Principali |
|-----|-------|------------------|
| `RichiestaDettagliImmobileDto` | **DTO UNIFICATO** dashboard agente/admin | 47 campi: contratto, richiesta, immobile, dettagli, superfici, utente, valutazione, admin |
| `RichiestaCardDTO` | Card richiesta (lista) | idRichiesta, stato, indirizzo, tipologia, dataRichiesta, nomeUtente |
| `DashboardStatsDTO` | Statistiche dashboard | richiesteInCarico, richiesteCompletate, richiesteArchiviate |
| `AcquisizioneDTO` | Contratti completati | idContratto, dataContratto, tipoContratto, immobile, proprietario |
| `ImmobileListDTO` | Immobile con dettagli completi | immobile + dettagli + superficie + proprietario + agente + richiesta + contratto + valutazione |

---

## 🎯 Stato Implementazione

### ✅ Completo e Funzionante
- Autenticazione & Login
- Dashboard Agente (completa)
- Gestione Immobili (CRUD + upload immagini)
- Sistema Email (3 template)
- Upload PDF contratti
- Valutazioni (form + calcolo)
- Contratti (CRUD + allegato PDF)

### 🔄 Parziale (Backend OK, Frontend Mancante)
- Dashboard Amministratore (dati OK, UI mancante)
- CRUD Utenti (API OK, form UI mancante)
- Filtri avanzati (endpoint OK, UI mancante)
- Dashboard Contratti (endpoint OK, UI mancante)

### ❌ Da Implementare
- Sistema Note/Commenti
- Export dati (CSV/Excel)
- Notifiche in-app
- Sistema permessi granulare
- Audit log

---

## 🚀 Prossimi Passi Consigliati

1. **Completare UI Dashboard Admin** (priorità ALTA)
   - Form CRUD Utenti
   - Tabella Contratti con filtri
   - Dashboard statistiche vendite

2. **Implementare Filtri UI** (priorità MEDIA)
   - Filtri richieste (stato, data, agente)
   - Filtri immobili (tipologia, prezzo, città)
   - Filtri contratti (tipo, scadenza)

3. **Sistema Note** (priorità MEDIA)
   - Aggiungere note a richieste
   - Storico azioni

4. **Export Dati** (priorità BASSA)
   - Export CSV richieste
   - Export PDF contratti multipli

---

## 📝 Note Tecniche

- **Pattern:** REST API + DTO pattern
- **Validazione:** Spring Validation (`@Valid`, `@NotNull`, etc.)
- **Errori:** ResponseEntity con HTTP status appropriati
- **Logging:** SLF4J + Logback
- **Sicurezza:** Sessioni HTTP (no JWT implementato)
- **CORS:** Configurato in `WebConfig.java`

---

**Documento creato da:** GitHub Copilot  
**Per:** Progetto Residea - Immobiliaris Watson
