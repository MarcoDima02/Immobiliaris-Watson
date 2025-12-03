# Modifiche Frontend per Dashboard Agenti e Form Valutazione

## Data: 3 Dicembre 2025
## Autore: Backend Team

---

## 📋 Panoramica

Questo documento descrive le modifiche necessarie al frontend per integrare le nuove funzionalità implementate nel backend:

1. **Dashboard Agenti**: Visualizzazione richieste IN_ATTESA e gestite
2. **Form Valutazione**: Campo obbligatorio per consenso GDPR
3. **Email Automatiche**: Integrazione eventi backend

---

## 🔧 Modifiche Richieste

### 1. Form Valutazione - Consenso Privacy GDPR

**File**: `frontend/src/components/form/StepUser.tsx`

**Problema**: Il backend ora richiede il campo `accettazioneTrattamentoDati` obbligatorio.

**Soluzione**: Aggiungere un checkbox per il consenso al trattamento dei dati personali.

**Codice da aggiungere** (prima del pulsante "Invia richiesta"):

```tsx
{/* Campo Consenso Privacy - OBBLIGATORIO */}
<Controller
  name="accettazioneTrattamentoDati"
  control={control}
  render={({ field, fieldState }) => (
    <Field data-invalid={fieldState.invalid}>
      <div className="flex items-start gap-3 mt-4">
        <input
          type="checkbox"
          id="privacy-consent"
          checked={field.value || false}
          onChange={(e) => field.onChange(e.target.checked)}
          className="mt-1"
        />
        <label htmlFor="privacy-consent" className="text-sm">
          Acconsento al trattamento dei miei dati personali ai sensi del 
          Regolamento UE 2016/679 (GDPR) per le finalità di valutazione 
          dell'immobile e per essere contattato da un agente immobiliare.
        </label>
      </div>
      {fieldState.error && (
        <FieldError>{fieldState.error.message}</FieldError>
      )}
    </Field>
  )}
/>
```

**Schema Validation** (`frontend/src/hooks/schemas/valuationSchema.ts`):

Aggiungere al schema `ownerSchema`:

```typescript
accettazioneTrattamentoDati: z.boolean().refine((val) => val === true, {
  message: 'Devi accettare il trattamento dei dati personali',
})
```

**Interface** (`frontend/src/providers/FormProvider.tsx`):

Aggiungere al tipo `FormPayload`:

```typescript
accettazioneTrattamentoDati?: boolean;
```

**Default Value** in `StepUser.tsx`:

Nel `useForm`, aggiungere:

```typescript
defaultValues: {
  // ... altri campi
  accettazioneTrattamentoDati: false,
}
```

---

### 2. Dashboard Agenti - Fix Chiavi React

**File**: `frontend/src/pages/AgentMyRequests.tsx`

**Problema**: Warning React "Encountered two children with the same key".

**Causa**: Le richieste IN_ATTESA hanno `idContratto: null`, quindi `Number(null)` = `0` crea chiavi duplicate.

**Soluzione**: Usare `idRichiesta` come chiave invece di `idContratto`.

**Modifica da applicare** (linea ~58):

```tsx
// PRIMA (ERRATO):
filtered.map((request: any) => (
  <AgentRequestDiv
    key={Number(request.idContratto)}  // ❌ Crea duplicati
    request={request}
  />
))

// DOPO (CORRETTO):
filtered.map((request: any) => (
  <AgentRequestDiv
    key={request.idRichiesta}  // ✅ Chiave unica
    request={request}
  />
))
```

---

### 3. Dettagli Richiesta - Rendering Null-Safe

**File**: `frontend/src/pages/AgentRequestDetails.tsx`

**Problema**: Possibile crash se `valoreMedio`, `valoreMin`, `valoreMax` sono null.

**Soluzione**: Aggiungere controlli null prima del rendering.

**Modifica da applicare** (sezione valutazione, linee ~208-237):

```tsx
{/* Valore medio */}
<p className="text-primary text-4xl font-extrabold mt-2">
  {request.valoreMedio 
    ? `${request.valoreMedio.toLocaleString()} €`
    : 'In attesa di valutazione'}
</p>

{/* Range valutazione */}
{request.valoreMin && request.valoreMax ? (
  <div className="flex flex-col gap-1">
    <div className="flex items-center gap-2">
      <span className="text-sm font-medium text-zinc-600">Min:</span>
      <span className="text-lg font-semibold text-zinc-700">
        {request.valoreMin.toLocaleString()} €
      </span>
    </div>
    <div className="flex items-center gap-2">
      <span className="text-sm font-medium text-zinc-600">Max:</span>
      <span className="text-lg font-semibold text-zinc-700">
        {request.valoreMax.toLocaleString()} €
      </span>
    </div>
  </div>
) : (
  <div className="text-center py-4 text-zinc-500 italic">
    La valutazione sarà disponibile a breve
  </div>
)}
```

**Fix campo nome stanze/bagni** (linee ~131, 137):

```tsx
// PRIMA (case-sensitive error):
<span className="text-lg">{request.nstanze || 'N/A'}</span>  // ❌ Lowercase
<span className="text-lg">{request.nbagni || 'N/A'}</span>   // ❌ Lowercase

// DOPO (corretto):
<span className="text-lg">{request.nStanze || 'N/A'}</span>  // ✅ CamelCase
<span className="text-lg">{request.nBagni || 'N/A'}</span>   // ✅ CamelCase
```

---

## 📊 Struttura Dati Backend

### DTO `AgenteRichiestaDTO`

Il backend restituisce oggetti con questa struttura:

```typescript
interface AgenteRichiestaDTO {
  // Contratto (null per richieste IN_ATTESA)
  idContratto: number | null;
  tipoContratto: string | null;
  dataContratto: string | null;
  dataScadenzaContratto: string | null;
  pathContrattoPDF: string | null;
  
  // Richiesta (sempre presente)
  idRichiesta: number;
  statoRichiesta: 'IN_ATTESA' | 'IN_ELABORAZIONE' | 'COMPLETATA' | 'ANNULLATA';
  dataRichiesta: string;
  dataAppuntamento: string | null;
  noteUtente: string | null;
  motivoAnnullamento: string | null;
  
  // Immobile
  idImmobile: number;
  tipologia: string;
  indirizzo: string;
  citta: string;
  provincia: string;
  cap: string;
  stato: string;
  
  // Dettagli
  nStanze: number;      // ⚠️ CamelCase, non lowercase
  nBagni: number;       // ⚠️ CamelCase, non lowercase
  nPiano: number | null;
  nPianiImmobile: number | null;
  ascensore: boolean;
  garage: boolean;
  balconeTerrazzo: boolean;
  giardino: boolean;
  cantina: boolean;
  annoCostruzione: number;
  condizioneImmobile: string;
  tipoRiscaldamento: string;
  classeEnergetica: string;
  
  // Superfici
  superficieMq: number;  // ⚠️ Non 'superficie'
  superficieBalconeTerrazzo: number | null;
  superficieGiardino: number | null;
  superficieGarage: number | null;
  superficieCantina: number | null;
  
  // Utente (proprietario)
  idUtente: number;
  nomeUtente: string;        // ⚠️ Non 'nomeProprietario'
  cognomeUtente: string;     // ⚠️ Non 'cognomeProprietario'
  emailUtente: string;
  telefonoUtente: string;
  
  // Valutazione (può essere null)
  idValutazione: number | null;
  valoreBase: number | null;
  fattoreAggiustamento: number | null;
  valoreMedio: number | null;     // ⚠️ Può essere null
  valoreMin: number | null;       // ⚠️ Può essere null
  valoreMax: number | null;       // ⚠️ Può essere null
  confidence: number | null;
}
```

### Endpoint Dashboard

**GET** `/api/agente/dashboard/{idAgente}`

Restituisce array di `AgenteRichiestaDTO` con:
1. **Richieste IN_ATTESA** (senza contratto, disponibili a tutti gli agenti)
2. **Richieste dell'agente** (IN_ELABORAZIONE/COMPLETATA/ANNULLATA con contratto)

**Nota importante**: Nessuna richiesta viene duplicata. Ogni `idRichiesta` appare una sola volta.

---

## 🔄 Flusso Email Automatiche

Il backend invia automaticamente email in questi casi:

### 1. Agente prende in carico richiesta

**Trigger**: `POST /api/agente/{idAgente}/richieste/{idRichiesta}/prendi-in-carico`

**Email inviata a**: Proprietario dell'immobile

**Contenuto**:
- Notifica che un agente ha preso in carico la richiesta
- Dati agente (nome, cognome, email, telefono)
- Dettagli immobile (indirizzo, città)
- Logo aziendale embedded
- Styling conforme al sito

### 2. Agente allega contratto PDF

**Trigger**: `PATCH /api/contratti/{id}/allega-pdf`

**Email inviata a**: Cliente (utente che ha fatto la richiesta)

**Contenuto**:
- PDF contratto in allegato
- Conferma upload contratto
- Istruzioni successive
- Logo aziendale embedded
- Styling conforme al sito

**Nota**: Queste email sono gestite automaticamente dal backend tramite Spring Events. Il frontend deve solo chiamare gli endpoint corretti.

---

## ✅ Checklist Implementazione

- [ ] Aggiungere checkbox privacy in `StepUser.tsx`
- [ ] Aggiungere validazione `accettazioneTrattamentoDati` nello schema Zod
- [ ] Aggiungere campo al tipo `FormPayload`
- [ ] Cambiare chiave React da `idContratto` a `idRichiesta` in `AgentMyRequests.tsx`
- [ ] Aggiungere controlli null per valutazione in `AgentRequestDetails.tsx`
- [ ] Correggere `nstanze` → `nStanze` e `nbagni` → `nBagni`
- [ ] Testare submit form con checkbox privacy unchecked (deve fallire)
- [ ] Testare submit form con checkbox privacy checked (deve funzionare)
- [ ] Verificare nessun warning "duplicate keys" in console React
- [ ] Testare visualizzazione dettagli richiesta senza valutazione

---

## 🐛 Problemi Noti Risolti

### Backend

1. ✅ **Typo `valutatzioneRepo`**: Corretto in `valutazioneRepo`
2. ✅ **Duplicati dashboard**: Richieste IN_ATTESA filtrate correttamente
3. ✅ **Valutazione mancante**: Aggiunto caricamento dati valutazione per richieste IN_ATTESA
4. ✅ **Null pointer su immobile**: Corretta indentazione in `mapContrattoToAcquisizioneDTO`

### Frontend (da applicare)

1. ⚠️ **Chiavi React duplicate**: Usare `idRichiesta` invece di `idContratto`
2. ⚠️ **Campo privacy mancante**: Aggiungere checkbox consenso GDPR
3. ⚠️ **Case sensitive**: Correggere `nstanze`/`nbagni` → `nStanze`/`nBagni`
4. ⚠️ **Null safety**: Aggiungere controlli per `valoreMedio`, `valoreMin`, `valoreMax`

---

## 📞 Supporto

Per domande o chiarimenti su queste modifiche, contattare il team backend.

**Endpoints di test**:
- Dashboard: `GET http://localhost:8080/api/agente/dashboard/4`
- Submit form: `POST http://localhost:8080/api/valutazioni/valuta`
- Prendi in carico: `POST http://localhost:8080/api/agente/4/richieste/1/prendi-in-carico`

**Email test**: Verificare MailHog su `http://localhost:8025` per vedere le email inviate.

---

## 📝 Note Finali

- Tutte le modifiche backend sono già committate e in produzione
- Le modifiche frontend sono **obbligatorie** per il corretto funzionamento
- La checkbox privacy è richiesta per legge (GDPR) - non opzionale
- Gli `idRichiesta` sono sempre univoci - usarli come chiavi React è safe

