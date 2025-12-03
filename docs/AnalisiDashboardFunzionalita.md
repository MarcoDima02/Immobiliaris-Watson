# 📊 ANALISI COMPLETA FUNZIONALITÀ DASHBOARD

**Data**: 3 Dicembre 2025  
**Branch**: dashboard-admin  
**Stato**: Documentazione funzionalità esistenti e mancanti

---

## 🏢 DASHBOARD AMMINISTRATORE

### 📍 Endpoint Base
`/backoffice/admin`

### Sezioni Disponibili

#### 1. Dashboard Home (`/backoffice/admin/dashboard`)

**Visualizzazione**:
- ✅ Card navigazione verso sezioni principali
- ✅ Link rapidi: Richieste, Utenti, Contratti

**CRUD**: Nessuna (solo navigazione)  
**Filtri**: Nessuno

---

#### 2. Gestione Utenti (`/backoffice/admin/utenti`)

**Backend Endpoint**: `GET /api/admin/dashboard/utenti`

**Filtri Disponibili** (Query Parameters - BACKEND IMPLEMENTATO):
```
?nome={nome}           - Filtra per nome (case-insensitive, contains)
?cognome={cognome}     - Filtra per cognome (case-insensitive, contains)
?email={email}         - Filtra per email (case-insensitive, contains)
?telefono={telefono}   - Filtra per telefono (contains)
?ruolo={ruolo}         - Filtra per ruolo (PROPRIETARIO, AGENTE, AMMINISTRATORE)
```

**CRUD Disponibili** (✅ Backend implementato):
- ✅ **CREATE**: `POST /api/utenti` - Crea nuovo utente
- ✅ **READ**: `GET /api/utenti` - Lista tutti
- ✅ **READ**: `GET /api/utenti/{id}` - Dettaglio singolo
- ✅ **UPDATE**: `PUT /api/utenti/{id}` - Aggiorna utente completo
- ✅ **UPDATE**: `PUT /api/utenti/{id}/ruolo` - Cambia solo ruolo
- ❌ **DELETE**: Non implementato (politica aziendale - no eliminazione dati)

**Frontend Stato**:
- ✅ Visualizzazione: Card con nome, cognome, ruolo, telefono, email
- ❌ **Form creazione**: NON implementato (backend pronto)
- ❌ **Form modifica**: NON implementato (bottone "Modifica" presente ma non funzionante)
- ❌ **Filtri UI**: NON implementati (backend pronto, manca solo UI)

**API Service Frontend**: ✅ Implementato (`frontend/src/api/utenti.ts`)
```typescript
- getAllUtenti()
- getUtenteById(id)
- creaUtente(utente)
- aggiornaUtente(id, utente)
- cambiaRuoloUtente(id, ruolo)
```

---

#### 3. Gestione Richieste (`/backoffice/admin/richieste`)

**Backend Endpoint**: `GET /api/admin/dashboard/richieste/dettagli`

**Filtri Disponibili**:
- ❌ **Nessun filtro** attualmente implementato nel backend

**Filtri da Implementare** (💡 Suggeriti):
```
?stato={stato}           - IN_ATTESA, IN_ELABORAZIONE, COMPLETATA, ANNULLATA
?citta={citta}           - Filtra per città
?dataInizio={data}       - Data inizio periodo
?dataFine={data}         - Data fine periodo
?tipologia={tipologia}   - Tipo immobile
```

**CRUD Disponibili** (Backend generico richieste):
- ✅ **CREATE**: `POST /api/richieste` - Crea richiesta
- ✅ **READ**: `GET /api/richieste` - Lista tutte
- ✅ **READ**: `GET /api/richieste/{id}` - Dettaglio singola
- ✅ **READ**: `GET /api/richieste/utente/{idUtente}` - Per utente
- ✅ **READ**: `GET /api/richieste/immobile/{idImmobile}` - Per immobile
- ✅ **READ**: `GET /api/richieste/stato/{stato}` - Per stato
- ✅ **UPDATE**: `PUT /api/richieste/{id}` - Aggiorna richiesta
- ✅ **DELETE**: `DELETE /api/richieste/{id}` - Elimina richiesta

**Frontend Stato**:
- ✅ Visualizzazione: Card con cliente, data, immobile, stato
- ✅ Dettaglio: Link "Visualizza dettagli" → `/backoffice/admin/richiesta`
- ❌ **Form creazione**: NON implementato
- ❌ **Form modifica**: NON implementato
- ❌ **Filtri UI**: NON implementati
- ❌ **Azioni su richieste**: NON implementate (assegna agente, cambia stato, ecc.)

---

#### 4. Gestione Contratti (`/backoffice/admin/contratti`)

**Backend Endpoint**: `GET /api/admin/dashboard/contratti`

**Filtri Disponibili** (Query Parameters - BACKEND IMPLEMENTATO):
```
?tipoContratto={tipo}    - VENDITA, AFFITTO, ACQUISIZIONE
?agente={idAgente}       - Filtra per agente
?immobile={idImmobile}   - Filtra per immobile
```

**CRUD Disponibili** (Backend generico contratti):
- ✅ **CREATE**: `POST /api/contratti` - Crea contratto
- ✅ **READ**: `GET /api/contratti` - Lista tutti
- ✅ **READ**: `GET /api/contratti/{id}` - Dettaglio singolo
- ✅ **READ**: `GET /api/contratti/agente/{idAgente}` - Per agente
- ✅ **READ**: `GET /api/contratti/immobile/{idImmobile}` - Per immobile
- ✅ **READ**: `GET /api/contratti/tipo/{tipo}` - Per tipo
- ✅ **UPDATE**: `PUT /api/contratti/{id}` - Aggiorna contratto
- ✅ **DELETE**: `DELETE /api/contratti/{id}` - Elimina contratto

**Frontend Stato**:
- ❌ **Visualizzazione**: NON implementata (dati caricati nello store ma non renderizzati)
- ❌ **Form creazione**: NON implementato
- ❌ **Form modifica**: NON implementato
- ❌ **Filtri UI**: NON implementati

---

#### 5. Altre Sezioni Backend Disponibili (NON esposte in frontend)

**Immobili**: `GET /api/admin/dashboard/immobili`
```
Filtri: ?citta, ?provincia, ?tipologia, ?stato, ?proprietario
```

**Vendite**: `GET /api/admin/dashboard/vendite`
```
Filtri: ?utente={idUtente}, ?immobile={idImmobile}, ?contratto={idContratto}
```

**Immagini**: `GET /api/admin/dashboard/immagini`
```
Filtri: ?immobile={idImmobile}, ?copertina={true/false}
```

---

## 🏠 DASHBOARD AGENTE

### 📍 Endpoint Base
`/backoffice/agent`

### Sezioni Disponibili

#### 1. Dashboard Home (`/backoffice/agent/dashboard`)

**Backend Endpoint**: `GET /api/agente/dashboard/{idAgente}`

**DTO Utilizzato**: `RichiestaDettagliImmobileDto` (DTO UNIFICATO admin/agente)

**Visualizzazione**:
- ✅ Richieste **IN_ATTESA** (pool comune - visibili a tutti gli agenti)
- ✅ Richieste **IN_ELABORAZIONE** (prese in carico dall'agente)
- ✅ Richieste **COMPLETATE**
- ✅ Richieste **ANNULLATE**
- ✅ Tabella "Ultime attività" (ultimi 10 movimenti ordinati per data)

**Filtri Frontend** (Client-side):
- ✅ Filtro automatico per stato (4 sezioni visive separate)
- ❌ Nessun filtro aggiuntivo UI (città, data, tipo immobile, range prezzo)

**Azioni Disponibili**:
- ✅ **"Prendi in Carico"**: Pulsante per richieste IN_ATTESA
  - Endpoint: `POST /api/agente/{idAgente}/richieste/{idRichiesta}/prendi-in-carico`
  - Comportamento: Crea contratto, cambia stato a IN_ELABORAZIONE, invia email automatica
  - UI: Modale conferma, loading state, reload automatico dopo successo

**CRUD**:
- ✅ **READ**: Visualizzazione richieste aggregate
- ✅ **UPDATE**: Prendi in carico (crea contratto automaticamente)
- ❌ **CREATE**: Non disponibile (le richieste arrivano dal form pubblico utenti)
- ❌ **DELETE**: Non disponibile

**Componenti**:
- `AgentRequestContainer.tsx` - Container principale con 4 sezioni
- `AgentRequestDiv.tsx` - Card singola richiesta con badge stato
- `AgentRequest.tsx` - Filtro rapido per categoria

---

#### 2. Le Mie Richieste (`/backoffice/agent/myRequests/:filter`)

**Filtri URL Disponibili** (Route parameters):
```
/tutte              - Tutte le richieste dell'agente
/in_elaborazione    - Solo IN_ELABORAZIONE
/completate         - Solo COMPLETATE
/in_attesa          - Solo IN_ATTESA (pool comune)
/annullate          - Solo ANNULLATE
```

**Backend**: Stesso endpoint dashboard (`GET /api/agente/dashboard/{idAgente}`)
- Filtro applicato **lato frontend** tramite parametro URL

**Mappatura Filtri** (frontend/src/pages/AgentMyRequests.tsx):
```typescript
const mapFilterToStatuses = {
  tutte: null,
  in_elaborazione: ['IN_ELABORAZIONE'],
  completate: ['COMPLETATA'],
  in_attesa: ['IN_ATTESA'],
  annullate: ['ANNULLATA'],
};
```

**Visualizzazione**:
- ✅ Card con dettagli richiesta/immobile
- ✅ Badge stato colorato (verde=completata, rosso=annullata, giallo=elaborazione, grigio=attesa)
- ✅ Link "Visualizza dettagli" → `/backoffice/agent/requestDetails`

**CRUD**:
- ✅ **READ**: Visualizzazione filtrata per stato
- ❌ Nessuna altra azione disponibile da questa vista

---

#### 3. Dettagli Richiesta (`/backoffice/agent/requestDetails`)

**Visualizzazione Completa**:
- ✅ Dettagli immobile (tipologia, indirizzo, città, CAP)
- ✅ Valutazione immobile (valoreBase, valoreMedio, valoreMin, valoreMax, confidence)
- ✅ Dati cliente (nome, cognome, email, telefono)
- ✅ Superfici (mq totali, balcone, giardino, garage, cantina)
- ✅ Caratteristiche (stanze, bagni, piano, ascensore, riscaldamento, classe energetica)
- ✅ Contratto (se presente: tipo, data, scadenza)

**Azioni**:
- ❌ Nessuna azione implementata (solo visualizzazione read-only)

**Azioni da Implementare** (💡 Suggerite):
- Cambia stato richiesta (dropdown: IN_ELABORAZIONE → COMPLETATA / ANNULLATA)
- Aggiungi note/commenti privati agente
- Carica documenti allegati (contratto PDF, foto, documenti identità)
- Programma appuntamento (data/ora, note)
- Invia email al cliente
- Genera report PDF richiesta

---

## 🔄 DTO UNIFICATO (Modifiche Recenti)

### RichiestaDettagliImmobileDto

**Stato**: ✅ Unificato per Admin e Agente

**Campi Totali**: 47 campi

**Naming Standardizzato**:
- `statoRichiesta` - Stato della richiesta (IN_ATTESA, IN_ELABORAZIONE, COMPLETATA, ANNULLATA)
- `statoImmobile` - Stato dell'immobile (DISPONIBILE, IN_TRATTATIVA, VENDUTO, AFFITTATO)

**Sezioni**:
1. **CONTRATTO** (5 campi): idContratto, tipoContratto, dataContratto, dataScadenzaContratto, pathContrattoPDF
2. **RICHIESTA** (6 campi): idRichiesta, statoRichiesta, dataRichiesta, dataAppuntamento, noteUtente, motivoAnnullamento
3. **IMMOBILE** (7 campi): idImmobile, tipologia, indirizzo, citta, provincia, cap, statoImmobile
4. **DETTAGLI IMMOBILE** (13 campi): nStanze, nBagni, nPiano, nPianiImmobile, balconeTerrazzo, giardino, garage, ascensore, cantina, annoCostruzione, condizioneImmobile, tipoRiscaldamento, classeEnergetica
5. **SUPERFICI** (5 campi): superficieMq, superficieBalconeTerrazzo, superficieGiardino, superficieGarage, superficieCantina
6. **UTENTE** (5 campi): idUtente, nomeUtente, cognomeUtente, emailUtente, telefonoUtente
7. **VALUTAZIONE** (7 campi): idValutazione, valoreBase, fattoreAggiustamento, valoreMedio, valoreMin, valoreMax, confidence
8. **ADMIN-ONLY** (4 campi): latitudine, longitudine, esposizione, prezzo (null per agenti)

**Backward Compatibility**:
- `getStato()` / `setStato()` delegano a `statoRichiesta`
- `AgenteRichiestaDTO` deprecato, ora type alias di `RichiestaDettagliImmobileDto`

**Frontend**: ✅ Aggiornato in `frontend/src/types/index.ts`

---

## 🔍 RIEPILOGO FUNZIONALITÀ MANCANTI

### ADMIN Dashboard - Priorità ALTA ⚠️

#### 1. Form Gestione Utenti
**Stato**: Backend ✅ | Frontend ❌

**Necessario**:
- Dialog/Modal creazione utente
- Form campi: nome, cognome, email, telefono, ruolo, password
- Validazione client-side (Zod schema)
- Dialog/Modal modifica utente
- Pre-compilazione form con dati esistenti
- Gestione errori e feedback utente

**Componenti da creare**:
```
frontend/src/components/adminDashboard/
  ├── UserCreateDialog.tsx
  ├── UserEditDialog.tsx
  └── UserForm.tsx (shared form component)
```

---

#### 2. UI Filtri Utenti
**Stato**: Backend ✅ | Frontend ❌

**Necessario**:
- Input text per nome/cognome/email/telefono
- Select per ruolo (PROPRIETARIO, AGENTE, AMMINISTRATORE)
- Bottone "Applica Filtri" / "Reset"
- Indicatore filtri attivi (badge count)

**Componenti da creare**:
```
frontend/src/components/adminDashboard/
  └── UserFilters.tsx
```

---

#### 3. Dashboard Contratti Completa
**Stato**: Backend ✅ | Frontend ❌ (completamente mancante)

**Necessario**:
- Visualizzazione tabella/card contratti
- Filtri: tipo contratto, agente, immobile
- Form creazione contratto
- Form modifica contratto
- Link a immobile/agente associati
- Badge tipo contratto (VENDITA=verde, AFFITTO=blu, ACQUISIZIONE=arancione)

**Componenti da creare**:
```
frontend/src/components/adminDashboard/
  ├── ContractList.tsx
  ├── ContractCard.tsx
  ├── ContractCreateDialog.tsx
  ├── ContractEditDialog.tsx
  └── ContractFilters.tsx
```

---

### ADMIN Dashboard - Priorità MEDIA

#### 4. Filtri Richieste (Backend + Frontend)
**Stato**: Backend ❌ | Frontend ❌

**Backend da implementare**:
```java
@GetMapping("/richieste/dettagli")
public ResponseEntity<List<RichiestaDettagliImmobileDto>> getRichiesteDettagli(
    @RequestParam(required = false) String stato,
    @RequestParam(required = false) String citta,
    @RequestParam(required = false) LocalDateTime dataInizio,
    @RequestParam(required = false) LocalDateTime dataFine,
    @RequestParam(required = false) String tipologia
)
```

**Frontend da implementare**:
- UI filtri richieste
- Date picker range (dataInizio - dataFine)
- Select multiplo stati
- Input città con autocomplete

---

#### 5. Azioni su Richieste
**Stato**: Backend parziale | Frontend ❌

**Necessario**:
- Assegna agente a richiesta (dropdown agenti disponibili)
- Cambia stato richiesta manualmente
- Aggiungi note amministratore
- Visualizza storico modifiche

---

#### 6. Dashboard Immobili
**Stato**: Backend ✅ | Frontend ❌ (non esposta)

**Necessario**:
- Visualizzazione griglia/lista immobili
- Filtri: città, provincia, tipologia, stato, proprietario
- Card immobile con immagine copertina
- Form creazione immobile completo
- Form modifica immobile
- Upload immagini multiple
- Gestione stato immobile (DISPONIBILE → IN_TRATTATIVA → VENDUTO)

---

### AGENTE Dashboard - Priorità MEDIA

#### 7. Filtri Avanzati Richieste
**Stato**: ❌ NON implementato

**Necessario**:
- Filtro per città (dropdown con città disponibili)
- Range date (date picker)
- Tipo immobile (select: APPARTAMENTO, VILLA, ecc.)
- Range prezzo valutazione
- Ordinamento (più recenti, prezzo crescente/decrescente)

**Implementazione**:
- Lato frontend (filtro su dati già caricati)
- Oppure backend (nuovi query parameters)

---

#### 8. Cambio Stato Richiesta
**Stato**: Backend parziale | Frontend ❌

**Necessario**:
- Dropdown/Select per cambiare stato
- Transizioni permesse:
  - IN_ELABORAZIONE → COMPLETATA
  - IN_ELABORAZIONE → ANNULLATA
- Dialog conferma con campo motivo annullamento
- Endpoint: `PUT /api/agente/{idAgente}/richieste/{idRichiesta}/stato`

---

#### 9. Note/Commenti su Richieste
**Stato**: ❌ NON implementato

**Necessario**:
- Tabella `nota_richiesta` (DB)
- Entity `NotaRichiesta` (Backend)
- Endpoint CRUD note
- UI timeline commenti
- Editor Markdown per note
- Timestamp e autore nota

---

### AGENTE Dashboard - Priorità BASSA

#### 10. Upload Documenti Contratto
**Stato**: ❌ NON implementato

**Necessario**:
- Upload PDF contratto firmato
- Upload documenti identità cliente
- Upload foto immobile aggiuntive
- Lista documenti allegati
- Download/preview documenti
- Storage backend (filesystem o S3)

---

#### 11. Statistiche/Grafici Dashboard
**Stato**: ❌ NON implementato

**Necessario**:
- Grafico richieste per mese
- Tasso conversione (prese in carico → completate)
- Valore medio valutazioni
- Tempo medio chiusura richiesta
- Classifica agenti più performanti (admin)
- Chart library: Recharts o Chart.js

---

#### 12. Export Dati
**Stato**: ❌ NON implementato

**Necessario**:
- Export CSV richieste
- Export PDF report richiesta singola
- Export Excel dashboard completa
- Filtri esportazione (periodo, stato)
- Backend: libreria Apache POI (Excel) o iText (PDF)

---

## ✨ PIANO DI IMPLEMENTAZIONE CONSIGLIATO

### Fase 1 - CRUD Utenti Admin (Priorità ALTA) ⭐⭐⭐
**Tempo stimato**: 4-6 ore

**Tasks**:
1. ✅ Backend CRUD utenti (FATTO)
2. ✅ API Service frontend (FATTO)
3. ❌ UserForm component (shared form)
4. ❌ UserCreateDialog component
5. ❌ UserEditDialog component
6. ❌ UserFilters component
7. ❌ Integrazione in AdminInfoContainer
8. ❌ Validazione Zod schema
9. ❌ Testing CRUD completo

**Output**:
- Admin può creare nuovi utenti
- Admin può modificare utenti esistenti
- Admin può filtrare utenti per nome/cognome/email/ruolo
- Admin può cambiare ruolo utente

---

### Fase 2 - Dashboard Contratti Admin (Priorità ALTA) ⭐⭐⭐
**Tempo stimato**: 6-8 ore

**Tasks**:
1. ContractList component (tabella/griglia)
2. ContractCard component (visualizzazione singola)
3. ContractCreateDialog component
4. ContractEditDialog component
5. ContractFilters component
6. Integrazione in route `/backoffice/admin/contratti`
7. API Service contratti
8. Validazione form
9. Testing CRUD completo

**Output**:
- Admin visualizza lista contratti con filtri
- Admin crea nuovi contratti
- Admin modifica contratti esistenti
- Admin filtra per tipo/agente/immobile

---

### Fase 3 - Filtri Richieste (Priorità MEDIA) ⭐⭐
**Tempo stimato**: 4-5 ore

**Tasks**:
1. Backend: aggiungere query parameters a `/api/admin/dashboard/richieste/dettagli`
2. RequestFilters component (frontend)
3. Date picker range
4. Select multiplo stati
5. Autocomplete città
6. Integrazione in AdminDashboard richieste
7. Testing filtri

**Output**:
- Admin filtra richieste per stato/città/periodo/tipologia
- Filtri persistono in URL (query params)
- Reset filtri

---

### Fase 4 - Azioni Agente su Richieste (Priorità MEDIA) ⭐⭐
**Tempo stimato**: 5-6 ore

**Tasks**:
1. Backend: endpoint cambio stato richiesta
2. StatusChangeDialog component
3. Dropdown transizioni stato
4. Campo motivo annullamento (obbligatorio)
5. Validazione transizioni
6. Email notifica cambio stato
7. Aggiornamento UI real-time

**Output**:
- Agente cambia stato richiesta (IN_ELABORAZIONE → COMPLETATA/ANNULLATA)
- Sistema registra motivo annullamento
- Cliente riceve email notifica

---

### Fase 5 - Note/Commenti (Priorità MEDIA) ⭐
**Tempo stimato**: 6-8 ore

**Tasks**:
1. Database: tabella `nota_richiesta`
2. Backend: Entity + Repository + Service + Controller
3. NotesTimeline component (frontend)
4. NoteEditor component (Markdown)
5. Note CRUD API
6. Visualizzazione autore e timestamp
7. Permessi (admin vede tutte, agente solo proprie)

**Output**:
- Agente/Admin aggiunge note private su richieste
- Timeline cronologica note
- Editor Markdown per formattazione

---

### Fase 6 - Dashboard Immobili Admin (Priorità BASSA)
**Tempo stimato**: 8-10 ore

**Tasks**:
1. PropertyList component
2. PropertyCard component
3. PropertyCreateDialog (form complesso)
4. PropertyEditDialog
5. PropertyFilters
6. ImageUpload component (multiple)
7. Gestione stato immobile
8. Integrazione mappa (Google Maps / Leaflet)

**Output**:
- Admin gestisce catalogo immobili completo
- Upload multiplo immagini
- Visualizzazione su mappa
- Filtri avanzati

---

### Fase 7 - Statistiche e Grafici (Priorità BASSA)
**Tempo stimato**: 6-8 ore

**Tasks**:
1. Backend: endpoint statistiche aggregate
2. Dashboard statistiche component
3. Chart components (Recharts)
4. KPI cards (totali, medie, trend)
5. Filtri periodo temporale

**Output**:
- Dashboard con grafici prestazioni
- KPI chiave (conversioni, tempi medi)
- Trend mensili/annuali

---

### Fase 8 - Export Dati (Priorità BASSA)
**Tempo stimato**: 4-6 ore

**Tasks**:
1. Backend: endpoint export CSV/PDF
2. ExportButton component
3. Dialog selezione formato
4. Filtri export
5. Download file generato

**Output**:
- Export CSV richieste
- Export PDF report singola richiesta
- Export Excel dashboard

---

## 📝 NOTE TECNICHE

### Backend Endpoints Disponibili (Non documentati sopra)

**DettagliImmobile**:
```
GET /api/dettagli-immobile
GET /api/dettagli-immobile/{id}
GET /api/dettagli-immobile/prezzo/{prezzo}
GET /api/dettagli-immobile/classeEnergetica/{classe}
GET /api/dettagli-immobile/condizione/{condizione}
GET /api/dettagli-immobile/tipoRiscaldamento/{tipo}
POST /api/dettagli-immobile
PUT /api/dettagli-immobile/{id}
DELETE /api/dettagli-immobile/{id}
```

**Immagini**:
```
GET /api/immagini
GET /api/immagini/{id}
GET /api/immagini/immobile/{idImmobile}
POST /api/immagini
PUT /api/immagini/{id}
DELETE /api/immagini/{id}
```

**Vendite**:
```
GET /api/vendite
GET /api/vendite/{id}
GET /api/vendite/utente/{idUtente}
GET /api/vendite/immobile/{idImmobile}
POST /api/vendite
PUT /api/vendite/{id}
DELETE /api/vendite/{id}
```

**Superficie**:
```
GET /api/superficie
GET /api/superficie/{idImmobile}
POST /api/superficie
PUT /api/superficie/{id}
DELETE /api/superficie/{id}
```

**PrezzoPerCap**:
```
GET /api/prezzo-per-cap
GET /api/prezzo-per-cap/{cap}
GET /api/prezzo-per-cap/citta/{idCitta}
GET /api/prezzo-per-cap/validFromAfter/{date}
GET /api/prezzo-per-cap/validToBefore/{date}
POST /api/prezzo-per-cap
PUT /api/prezzo-per-cap/{cap}
DELETE /api/prezzo-per-cap/{cap}
```

**Lead**:
```
GET /api/leads
GET /api/leads/{id}
GET /api/leads/email/{email}
GET /api/leads/citta/{citta}
GET /api/leads/utente/{idUtente}
POST /api/leads
PUT /api/leads/{id}
DELETE /api/leads/{id}
```

---

## 🔐 Autenticazione e Permessi

**Stato Attuale**:
- ✅ Login implementato: `POST /api/utenti/login`
- ✅ Sessione HTTP con attributi: `userId`, `userRuolo`, `userEmail`
- ⚠️ Controlli autorizzazione commentati (TODO: riattivare)

**Da Implementare**:
- Middleware autorizzazione per ruoli
- Token JWT invece di sessioni HTTP
- Refresh token
- Logout endpoint
- Password reset flow
- Email verification

---

## 🗄️ Database Schema (Rilevante)

**Tabelle Principali**:
- `utente` - Utenti sistema (PROPRIETARIO, AGENTE, AMMINISTRATORE)
- `immobile` - Catalogo immobili
- `dettagli_immobile` - Caratteristiche dettagliate
- `superficie` - Metrature
- `richiesta` - Richieste valutazione
- `contratto` - Contratti gestione
- `valutazione_immobile` - Valutazioni automatiche
- `vendita` - Transazioni vendite
- `immagine` - Immagini immobili
- `lead` - Lead marketing

**Tabelle da Creare**:
- `nota_richiesta` - Note/commenti privati
- `documento_richiesta` - Allegati documenti
- `appuntamento` - Calendario appuntamenti
- `notifica` - Sistema notifiche

---

## 📚 Librerie e Tecnologie

**Backend**:
- Spring Boot 3.x
- JPA/Hibernate
- MySQL
- JavaMailSender (email)
- Thymeleaf (template email)

**Frontend**:
- React 18 + TypeScript
- React Router
- Zustand (state management)
- Zod (validazione)
- Shadcn/UI (componenti)
- Tailwind CSS

**Da Aggiungere** (Suggerite):
- Recharts (grafici)
- React Hook Form (form complessi)
- date-fns (manipolazione date)
- React Dropzone (upload file)
- React Markdown (editor note)

---

## 🎯 Metriche di Successo

**KPI Dashboard Admin**:
- Tempo medio risposta richieste: < 24h
- Tasso conversione richieste: > 60%
- Utenti attivi mensili
- Immobili catalogati
- Contratti attivi

**KPI Dashboard Agente**:
- Richieste prese in carico / mese
- Tasso completamento: > 80%
- Tempo medio chiusura richiesta: < 7 giorni
- Valore medio valutazioni gestite

---

## 📅 Roadmap Temporale Stimata

**Settimana 1**: CRUD Utenti Admin + Filtri  
**Settimana 2**: Dashboard Contratti Admin  
**Settimana 3**: Filtri Richieste + Azioni Agente  
**Settimana 4**: Note/Commenti Sistema  
**Settimana 5-6**: Dashboard Immobili Completa  
**Settimana 7**: Statistiche e Grafici  
**Settimana 8**: Export Dati + Polishing

**Totale stimato**: 2 mesi di sviluppo (1 developer full-time)

---

## 🐛 Bug Noti e Limitazioni

1. **Controlli autorizzazione commentati**: Tutti gli endpoint admin accessibili senza verifica ruolo
2. **Password in chiaro**: Non viene hashata durante creazione/modifica utente
3. **Nessuna validazione email**: Email duplicate permesse
4. **Filtri case-sensitive**: Alcuni filtri backend usano `contains` ma case-sensitive
5. **Paginazione assente**: Liste complete caricate (problema performance con molti record)
6. **Upload path hardcoded**: Path upload file non configurabile
7. **Email template statiche**: Nessuna personalizzazione template

---

## 🔄 Workflow Completo Richiesta (Come Dovrebbe Funzionare)

1. **Utente pubblico**: Compila form valutazione → Crea Richiesta (IN_ATTESA) + Valutazione
2. **Sistema**: Calcola valutazione automatica basata su caratteristiche
3. **Pool agenti**: Tutti gli agenti vedono richieste IN_ATTESA
4. **Agente**: Click "Prendi in Carico" → Crea Contratto, stato → IN_ELABORAZIONE, email cliente
5. **Agente**: Lavora richiesta, aggiunge note, carica documenti
6. **Agente**: Programma appuntamento con cliente
7. **Agente**: Dopo visita → cambia stato COMPLETATA/ANNULLATA
8. **Sistema**: Invia email conferma/annullamento
9. **Admin**: Monitora tutto, assegna manualmente se necessario

---

**Fine Documento**

_Per riprendere: partire da Fase 1 - CRUD Utenti Admin_
