# Documentazione Frontend: Richieste con Dettagli Immobile

## 📋 Panoramica

Questo documento descrive come consumare l'endpoint backend `GET /api/admin/dashboard/richieste/con-dettagli` che ritorna richieste di valutazione con i dettagli completi dell'immobile associato.

## 🔗 Endpoint Backend

### URL
```
GET http://localhost:8080/api/admin/dashboard/richieste/con-dettagli
```

### Autenticazione
```
Authorization: Bearer <token_jwt>
```

### Parametri Query (Opzionali)
| Parametro | Tipo | Descrizione | Esempio |
|-----------|------|-------------|---------|
| `stato` | String | Filtra per stato richiesta | `IN_ATTESA`, `IN_ELABORAZIONE`, `COMPLETATA`, `ANNULLATA` |
| `utente` | Integer | Filtra per ID utente (richiedente) | `5` |
| `immobile` | Integer | Filtra per ID immobile | `10` |

### Esempi di Richieste

```bash
# Tutte le richieste con dettagli
GET http://localhost:8080/api/admin/dashboard/richieste/con-dettagli

# Solo richieste in elaborazione
GET http://localhost:8080/api/admin/dashboard/richieste/con-dettagli?stato=IN_ELABORAZIONE

# Richieste di uno specifico utente
GET http://localhost:8080/api/admin/dashboard/richieste/con-dettagli?utente=5

# Richieste per uno specifico immobile
GET http://localhost:8080/api/admin/dashboard/richieste/con-dettagli?immobile=10

# Combinazione di filtri
GET http://localhost:8080/api/admin/dashboard/richieste/con-dettagli?stato=COMPLETATA&utente=5
```

## 📊 Struttura Risposta

La risposta è un array di oggetti `RichiestaDettagliImmobileDto`:

```json
[
  {
    "idRichiesta": 1,
    "dataRichiesta": "2025-12-01T10:30:00",
    "dataAppuntamento": "2025-12-05T14:00:00",
    "stato": "IN_ELABORAZIONE",
    "noteUtente": "Preferisco visite nel pomeriggio",
    "motivoAnnullamento": null,
    
    "idUtente": 5,
    "nomeUtente": "Marco",
    "cognomeUtente": "Rossi",
    "emailUtente": "marco.rossi@example.com",
    "telefonoUtente": "+39 320 1234567",
    
    "idImmobile": 10,
    "tipologia": "APPARTAMENTO",
    "indirizzo": "Via Roma 42",
    "citta": "Milano",
    "provincia": "MI",
    "cap": "20100",
    "statoImmobile": "DISPONIBILE",
    "latitudine": 45.4642,
    "longitudine": 9.1900,
    
    "nStanze": 3,
    "nBagni": 2,
    "nPiano": 4,
    "nPianiImmobile": 6,
    "balconeTerrazzo": true,
    "giardino": false,
    "garage": true,
    "ascensore": true,
    "cantina": false,
    "tipoRiscaldamento": "AUTONOMO",
    "annoCostruzione": 2015,
    "condizioneImmobile": "RISTRUTTURATO",
    "classeEnergetica": "B",
    "esposizione": "Sud-Est",
    "prezzo": 450000.00
  }
]
```

## 🏗️ Struttura Dati del DTO

### Sezione RICHIESTA
| Campo | Tipo | Descrizione |
|-------|------|-------------|
| `idRichiesta` | Integer | ID univoco della richiesta |
| `dataRichiesta` | LocalDateTime | Data/ora di creazione della richiesta |
| `dataAppuntamento` | LocalDateTime | Data/ora dell'appuntamento previsto |
| `stato` | String | Stato della richiesta (IN_ATTESA, IN_ELABORAZIONE, COMPLETATA, ANNULLATA) |
| `noteUtente` | String | Note aggiunte dall'utente |
| `motivoAnnullamento` | String | Motivo dell'annullamento (null se non annullata) |

### Sezione UTENTE (Richiedente)
| Campo | Tipo | Descrizione |
|-------|------|-------------|
| `idUtente` | Integer | ID dell'utente che ha fatto la richiesta |
| `nomeUtente` | String | Nome dell'utente |
| `cognomeUtente` | String | Cognome dell'utente |
| `emailUtente` | String | Email dell'utente |
| `telefonoUtente` | String | Telefono dell'utente |

### Sezione IMMOBILE
| Campo | Tipo | Descrizione |
|-------|------|-------------|
| `idImmobile` | Integer | ID dell'immobile |
| `tipologia` | String | Tipologia (APPARTAMENTO, VILLA, CASA_INDIPENDENTE, MONOLOCALE) |
| `indirizzo` | String | Indirizzo completo |
| `citta` | String | Città |
| `provincia` | String | Provincia (2 lettere) |
| `cap` | String | Codice Avviamento Postale |
| `statoImmobile` | String | Stato (DISPONIBILE, VENDUTO) |
| `latitudine` | Double | Coordinate GPS latitudine |
| `longitudine` | Double | Coordinate GPS longitudine |

### Sezione DETTAGLI IMMOBILE
| Campo | Tipo | Descrizione |
|-------|------|-------------|
| `nStanze` | Integer | Numero di stanze |
| `nBagni` | Integer | Numero di bagni |
| `nPiano` | Integer | Piano in cui si trova |
| `nPianiImmobile` | Integer | Numero totale di piani dello stabile |
| `balconeTerrazzo` | Boolean | Presenza di balcone/terrazzo |
| `giardino` | Boolean | Presenza di giardino |
| `garage` | Boolean | Presenza di garage |
| `ascensore` | Boolean | Presenza di ascensore |
| `cantina` | Boolean | Presenza di cantina |
| `tipoRiscaldamento` | String | Tipo (NO, AUTONOMO, CONDOMINIALE, POMPE_DI_CALORE, PAVIMENTO) |
| `annoCostruzione` | Integer | Anno di costruzione |
| `condizioneImmobile` | String | Condizione (NUOVO, RISTRUTTURATO, PARZIALMENTE_RISTRUTTURATO, NON_RISTRUTTURATO) |
| `classeEnergetica` | String | Classe energetica (A+, A, B, C, D, E, F, G) |
| `esposizione` | String | Orientamento (es. "Sud-Est") |
| `prezzo` | Double | Prezzo in euro |

## 💻 Implementazione Frontend (TypeScript/React)

### 1. Definire l'Interfaccia TypeScript

```typescript
interface RichiestaDettagliImmobile {
  // Richiesta
  idRichiesta: number;
  dataRichiesta: string;
  dataAppuntamento: string;
  stato: string;
  noteUtente: string;
  motivoAnnullamento: string | null;

  // Utente
  idUtente: number;
  nomeUtente: string;
  cognomeUtente: string;
  emailUtente: string;
  telefonoUtente: string;

  // Immobile
  idImmobile: number;
  tipologia: string;
  indirizzo: string;
  citta: string;
  provincia: string;
  cap: string;
  statoImmobile: string;
  latitudine: number;
  longitudine: number;

  // Dettagli Immobile
  nStanze: number;
  nBagni: number;
  nPiano: number;
  nPianiImmobile: number;
  balconeTerrazzo: boolean;
  giardino: boolean;
  garage: boolean;
  ascensore: boolean;
  cantina: boolean;
  tipoRiscaldamento: string;
  annoCostruzione: number;
  condizioneImmobile: string;
  classeEnergetica: string;
  esposizione: string;
  prezzo: number;
}
```

### 2. Hook Custom per il Fetch

```typescript
import { useState, useEffect } from 'react';

export function useRichiesteDettagli(
  stato?: string,
  idUtente?: number,
  idImmobile?: number
) {
  const [richieste, setRichieste] = useState<RichiestaDettagliImmobile[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchRichieste = async () => {
      try {
        setLoading(true);
        
        // Costruisci i parametri di query
        const params = new URLSearchParams();
        if (stato) params.append('stato', stato);
        if (idUtente) params.append('utente', idUtente.toString());
        if (idImmobile) params.append('immobile', idImmobile.toString());

        const response = await fetch(
          `http://localhost:8080/api/admin/dashboard/richieste/con-dettagli?${params.toString()}`,
          {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
          }
        );

        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data: RichiestaDettagliImmobile[] = await response.json();
        setRichieste(data);
        setError(null);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Errore sconosciuto');
      } finally {
        setLoading(false);
      }
    };

    fetchRichiesteDettagli();
  }, [stato, idUtente, idImmobile]);

  return { richieste, loading, error };
}
```

### 3. Componente di Visualizzazione

```typescript
import React, { useState } from 'react';
import { useRichiesteDettagli } from './hooks/useRichiesteDettagli';

export function RichiesteDettagliPage() {
  const [filtroStato, setFiltroStato] = useState<string>();
  const { richieste, loading, error } = useRichiesteDettagli(filtroStato);
  const [selectedId, setSelectedId] = useState<number | null>(null);

  const selected = richieste.find(r => r.idRichiesta === selectedId);

  return (
    <div style={{ display: 'grid', gridTemplateColumns: '300px 1fr', gap: '2rem' }}>
      {/* Sidebar: Lista */}
      <aside>
        <h2>Richieste ({richieste.length})</h2>
        
        <div>
          <label>Filtra per stato:</label>
          <select value={filtroStato || ''} onChange={e => setFiltroStato(e.target.value || undefined)}>
            <option value="">Tutti</option>
            <option value="IN_ATTESA">In Attesa</option>
            <option value="IN_ELABORAZIONE">In Elaborazione</option>
            <option value="COMPLETATA">Completata</option>
            <option value="ANNULLATA">Annullata</option>
          </select>
        </div>

        {loading && <p>Caricamento...</p>}
        {error && <p style={{ color: 'red' }}>Errore: {error}</p>}

        <ul style={{ listStyle: 'none', padding: 0 }}>
          {richieste.map(richiesta => (
            <li key={richiesta.idRichiesta}>
              <div
                onClick={() => setSelectedId(richiesta.idRichiesta)}
                style={{
                  padding: '1rem',
                  border: selectedId === richiesta.idRichiesta ? '2px solid blue' : '1px solid #ddd',
                  cursor: 'pointer',
                  borderRadius: '4px'
                }}
              >
                <strong>{richiesta.nomeUtente} {richiesta.cognomeUtente}</strong>
                <p>{richiesta.citta}</p>
                <small>{richiesta.stato}</small>
              </div>
            </li>
          ))}
        </ul>
      </aside>

      {/* Main: Dettagli */}
      <main>
        {selected ? (
          <div>
            <h2>
              {selected.tipologia} - {selected.citta}
              <span style={{ marginLeft: '1rem', fontSize: '0.8em' }}>
                Stato: {selected.stato}
              </span>
            </h2>

            <section>
              <h3>Richiedente</h3>
              <p><strong>Nome:</strong> {selected.nomeUtente} {selected.cognomeUtente}</p>
              <p><strong>Email:</strong> {selected.emailUtente}</p>
              <p><strong>Telefono:</strong> {selected.telefonoUtente}</p>
              <p><strong>Data Richiesta:</strong> {new Date(selected.dataRichiesta).toLocaleDateString('it-IT')}</p>
            </section>

            <section>
              <h3>Dettagli Immobile</h3>
              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
                <div>
                  <p><strong>Indirizzo:</strong> {selected.indirizzo}</p>
                  <p><strong>Città:</strong> {selected.citta} ({selected.provincia})</p>
                  <p><strong>CAP:</strong> {selected.cap}</p>
                  <p><strong>Piano:</strong> {selected.nPiano} / {selected.nPianiImmobile}</p>
                </div>
                <div>
                  <p><strong>Stanze:</strong> {selected.nStanze}</p>
                  <p><strong>Bagni:</strong> {selected.nBagni}</p>
                  <p><strong>Anno:</strong> {selected.annoCostruzione}</p>
                  <p><strong>Classe Energetica:</strong> {selected.classeEnergetica}</p>
                </div>
              </div>
            </section>

            <section>
              <h3>Caratteristiche</h3>
              <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '1rem' }}>
                {selected.balconeTerrazzo && <span>✓ Balcone/Terrazzo</span>}
                {selected.giardino && <span>✓ Giardino</span>}
                {selected.garage && <span>✓ Garage</span>}
                {selected.ascensore && <span>✓ Ascensore</span>}
                {selected.cantina && <span>✓ Cantina</span>}
              </div>
            </section>

            <section>
              <h3>Valutazione</h3>
              <p><strong>Prezzo:</strong> €{selected.prezzo?.toLocaleString('it-IT')}</p>
              <p><strong>Condizione:</strong> {selected.condizioneImmobile}</p>
              <p><strong>Riscaldamento:</strong> {selected.tipoRiscaldamento}</p>
            </section>

            {selected.noteUtente && (
              <section>
                <h3>Note</h3>
                <p>{selected.noteUtente}</p>
              </section>
            )}

            {selected.motivoAnnullamento && (
              <section style={{ background: '#ffebee', padding: '1rem', borderRadius: '4px' }}>
                <h3 style={{ color: '#c62828' }}>Motivo Annullamento</h3>
                <p>{selected.motivoAnnullamento}</p>
              </section>
            )}
          </div>
        ) : (
          <p>Seleziona una richiesta dalla lista</p>
        )}
      </main>
    </div>
  );
}
```

### 4. Utili Helper Functions

```typescript
// Formattazione Data
export function formatDate(dateString: string): string {
  return new Date(dateString).toLocaleDateString('it-IT', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  });
}

// Formattazione Valuta
export function formatPrice(price: number): string {
  return new Intl.NumberFormat('it-IT', {
    style: 'currency',
    currency: 'EUR'
  }).format(price);
}

// Colore per Stato
export function getStatoColor(stato: string): string {
  switch (stato) {
    case 'IN_ATTESA': return '#ff9800';
    case 'IN_ELABORAZIONE': return '#2196f3';
    case 'COMPLETATA': return '#4caf50';
    case 'ANNULLATA': return '#f44336';
    default: return '#999';
  }
}

// Badge per Stato
export function StatoBadge({ stato }: { stato: string }) {
  return (
    <span style={{
      background: getStatoColor(stato),
      color: 'white',
      padding: '4px 12px',
      borderRadius: '20px',
      fontSize: '0.85rem',
      fontWeight: 'bold'
    }}>
      {stato}
    </span>
  );
}
```

## 🔍 Casi d'Uso

### 1. Dashboard Amministratore
Visualizzare tutte le richieste di valutazione con i dettagli degli immobili per gestione centralizzata.

### 2. Filtro per Stato
```typescript
// Solo richieste completate
const { richieste } = useRichiesteDettagli('COMPLETATA');
```

### 3. Filtro per Utente
```typescript
// Solo richieste di uno specifico utente
const { richieste } = useRichiesteDettagli(undefined, 5);
```

### 4. Filtro per Immobile
```typescript
// Tutte le richieste per uno specifico immobile
const { richieste } = useRichiesteDettagli(undefined, undefined, 10);
```

## 🛠️ Testing con cURL

```bash
# Ottieni il token
TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"anna.verdi@example.com","password":"agente123"}' \
  | jq -r '.token')

# Test endpoint
curl -X GET "http://localhost:8080/api/admin/dashboard/richieste/con-dettagli" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  | jq '.' # pretty print
```

## 🧪 Testing con Postman

1. **Login**
   - POST: `http://localhost:8080/api/auth/login`
   - Body: `{"email":"anna.verdi@example.com","password":"agente123"}`
   - Copia il `token` dalla risposta

2. **Richieste Dettagli**
   - GET: `http://localhost:8080/api/admin/dashboard/richieste/con-dettagli`
   - Header: `Authorization: Bearer <token_copiato>`
   - Seleziona "JSON" nella risposta per visualizzare i dati

## ⚠️ Gestione Errori

```typescript
const { richieste, loading, error } = useRichiesteDettagli();

if (error === '401') {
  // Token scaduto, redirect a login
  window.location.href = '/login';
}

if (error === '403') {
  // Non autorizzato, utente non è amministratore
  return <p>Non hai i permessi per accedere a questa pagina</p>;
}

if (error === '500') {
  // Errore server
  return <p>Errore del server. Contatta l'amministratore</p>;
}
```

## 📝 Note

- L'endpoint ritorna sempre un **array**, anche se vuoto
- Tutti i campi sono **nullable** a eccezione di `idRichiesta` e `idImmobile`
- Le **date sono in formato ISO 8601** (es: `2025-12-01T10:30:00`)
- Gli **enum sono convertiti in stringhe** (es: `APPARTAMENTO`, `IN_ELABORAZIONE`)
- Il **JOIN con DettagliImmobile** viene fatto automaticamente dal backend per ogni richiesta

## 🚀 Prossimi Passi

1. Creare una pagina dedicata per la visualizzazione
2. Aggiungere filtri avanzati (data range, prezzo min/max)
3. Implementare esportazione CSV/PDF
4. Aggiungere paginazione se le richieste sono molte
