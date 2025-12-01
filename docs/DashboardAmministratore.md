# Dashboard Amministratore - Guida Implementazione Frontend

Documentazione completa per l'implementazione della dashboard amministratore nel frontend React.

## Indice

- [Panoramica](#panoramica)
- [Architettura](#architettura)
- [Endpoint API Disponibili](#endpoint-api-disponibili)
- [Struttura Dati (DTO)](#struttura-dati-dto)
- [Implementazione Frontend](#implementazione-frontend)
- [Autenticazione e Protezione](#autenticazione-e-protezione)
- [Funzionalità da Implementare](#funzionalità-da-implementare)

---

## Panoramica

La dashboard amministratore consente la gestione completa del sistema immobiliare con accesso a:

- **Utenti**: Visualizzazione, creazione, modifica e filtri (nome, cognome, email, ruolo, telefono)
- **Immobili**: Gestione immobili con filtri avanzati (città, provincia, tipologia, stato, proprietario)
- **Contratti**: Visualizzazione e gestione contratti con filtri (tipo, immobile, agente)
- **Richieste**: Monitoraggio richieste di valutazione con filtri (stato, utente, immobile)
- **Vendite**: Gestione vendite completate con filtri (contratto, immobile, utente)
- **Immagini**: Gestione immagini immobili con filtri (immobile, copertina)

---

## Architettura

### URL Base API

```
http://localhost:8080/api/admin/dashboard
```

### Autenticazione

- **Ruolo richiesto**: `AMMINISTRATORE`
- **Sessione HTTP**: Verificata tramite `HttpSession` (attualmente disabilitata per testing)
- **Protezione route**: Da implementare con `<ProtectedRoute roles={["AMMINISTRATORE"]}>`

### Pattern di Risposta

Tutti gli endpoint GET restituiscono:

- **200 OK**: Array di DTO (oggetti semplificati)
- **403 Forbidden**: Se l'utente non è amministratore (quando riattivata)
- **500 Internal Server Error**: Errore server

---

## Endpoint API Disponibili

### 1. Gestione Utenti

#### GET /api/admin/dashboard/utenti

Recupera lista utenti con filtri opzionali.

**Query Parameters:**

| Nome     | Tipo   | Descrizione                       | Esempio                 |
| -------- | ------ | --------------------------------- | ----------------------- |
| nome     | String | Cerca per nome (case-insensitive) | `?nome=sofia`         |
| cognome  | String | Cerca per cognome                 | `?cognome=costa`      |
| email    | String | Cerca per email                   | `?email=@example.com` |
| ruolo    | Enum   | Filtra per ruolo                  | `?ruolo=AGENTE`       |
| telefono | String | Cerca per telefono                | `?telefono=333`       |

**Valori Ruolo:**

- `PROPRIETARIO`
- `AGENTE`
- `AMMINISTRATORE`

**Response 200 OK:**

```json
[
  {
    "idUtente": 4,
    "nome": "Sofia",
    "cognome": "Costa",
    "email": "sofia.costa@example.com",
    "telefono": "3338901234",
    "ruolo": "AGENTE",
    "verificaEmail": true,
    "consensoPrivacy": true
  }
]
```

**Esempio chiamata:**

```typescript
const response = await fetch(
  `${API_URL}/admin/dashboard/utenti?ruolo=AGENTE`,
  { credentials: 'include' }
);
const utenti = await response.json();
```

---

### 2. Gestione Immobili

#### GET /api/admin/dashboard/immobili

Recupera lista immobili con filtri opzionali.

**Query Parameters:**

| Nome         | Tipo    | Descrizione                       | Esempio                     |
| ------------ | ------- | --------------------------------- | --------------------------- |
| citta        | String  | Filtra per città                 | `?citta=Torino`           |
| provincia    | String  | Filtra per provincia (es: TO, NO) | `?provincia=TO`           |
| tipologia    | Enum    | Filtra per tipologia              | `?tipologia=APPARTAMENTO` |
| stato        | Enum    | Filtra per stato                  | `?stato=DISPONIBILE`      |
| proprietario | Integer | ID del proprietario               | `?proprietario=5`         |

**Valori Tipologia:**

- `APPARTAMENTO`
- `VILLA`
- `ATTICO`
- `LOFT`
- `BILOCALE`
- `TRILOCALE`
- `QUADRILOCALE`

**Valori Stato:**

- `DISPONIBILE`
- `IN_TRATTATIVA`
- `VENDUTO`
- `RITIRATO`

**Response 200 OK:**

```json
[
  {
    "idImmobile": 1,
    "idProprietario": 5,
    "tipologia": "APPARTAMENTO",
    "indirizzo": "Via Roma 10",
    "citta": "Torino",
    "provincia": "TO",
    "cap": "10100",
    "latitudine": 45.0703,
    "longitudine": 7.6869,
    "stato": "DISPONIBILE"
  }
]
```

---

### 3. Gestione Contratti

#### GET /api/admin/dashboard/contratti

Recupera lista contratti con filtri opzionali.

**Query Parameters:**

| Nome     | Tipo    | Descrizione    | Esempio           |
| -------- | ------- | -------------- | ----------------- |
| tipo     | Enum    | Tipo contratto | `?tipo=VENDITA` |
| immobile | Integer | ID immobile    | `?immobile=1`   |
| agente   | Integer | ID agente      | `?agente=4`     |

**Valori Tipo:**

- `VENDITA`
- `AFFITTO`
- `ACQUISIZIONE`

**Response 200 OK:**

```json
[
  {
    "idContratto": 1,
    "idImmobile": 3,
    "idAgente": 4,
    "tipoContratto": "VENDITA",
    "dataContratto": "2025-06-01",
    "dataScadenzaContratto": "2026-06-01",
    "pathContrattoPDF": "/uploads/contratti/contratto_1.pdf"
  }
]
```

#### POST /api/admin/dashboard/contratti

Crea nuovo contratto.

**Request Body:**

```json
{
  "idImmobile": { "idImmobile": 3 },
  "agente": { "idUtente": 4 },
  "tipoContratto": "VENDITA",
  "dataContratto": "2025-06-01",
  "dataScadenzaContratto": "2026-06-01"
}
```

#### PUT /api/admin/dashboard/contratti/

Aggiorna contratto esistente.

---

### 4. Gestione Richieste

#### GET /api/admin/dashboard/richieste

Recupera lista richieste con filtri opzionali.

**Query Parameters:**

| Nome     | Tipo    | Descrizione           | Esempio                    |
| -------- | ------- | --------------------- | -------------------------- |
| stato    | Enum    | Stato richiesta       | `?stato=IN_ELABORAZIONE` |
| utente   | Integer | ID utente richiedente | `?utente=2`              |
| immobile | Integer | ID immobile           | `?immobile=3`            |

**Valori Stato:**

- `IN_ATTESA`
- `IN_ELABORAZIONE`
- `COMPLETATA`
- `ANNULLATA`

**Response 200 OK:**

```json
[
  {
    "idRichiesta": 1,
    "idUtente": 2,
    "idImmobile": 3,
    "dataRichiesta": "2025-05-15",
    "dataAppuntamento": "2025-05-20",
    "stato": "IN_ELABORAZIONE",
    "noteUtente": "Interessato a vendere",
    "motivoAnnullamento": null
  }
]
```

#### POST /api/admin/dashboard/richieste

Crea nuova richiesta.

**Request Body:**

```json
{
  "utente": { "idUtente": 2 },
  "immobile": { "idImmobile": 3 },
  "dataRichiesta": "2025-05-15",
  "dataAppuntamento": "2025-05-20",
  "stato": "IN_ATTESA",
  "noteUtente": "Richiesta valutazione"
}
```

#### PUT /api/admin/dashboard/richieste/

Aggiorna richiesta esistente.

---

### 5. Gestione Vendite

#### GET /api/admin/dashboard/vendite

Recupera lista vendite con filtri opzionali.

**Query Parameters:**

| Nome      | Tipo    | Descrizione   | Esempio          |
| --------- | ------- | ------------- | ---------------- |
| contratto | Integer | ID contratto  | `?contratto=1` |
| immobile  | Integer | ID immobile   | `?immobile=3`  |
| utente    | Integer | ID acquirente | `?utente=8`    |

**Response 200 OK:**

```json
[
  {
    "idVendita": 1,
    "idContratto": 1,
    "idImmobile": 3,
    "idUtente": 8,
    "commissionePercentuale": 3.5
  }
]
```

#### POST /api/admin/dashboard/vendite

Crea nuova vendita.

#### PUT /api/admin/dashboard/vendite/

Aggiorna vendita esistente.

---

### 6. Gestione Immagini

#### GET /api/admin/dashboard/immagini

Recupera lista immagini con filtri opzionali.

**Query Parameters:**

| Nome      | Tipo    | Descrizione                | Esempio             |
| --------- | ------- | -------------------------- | ------------------- |
| immobile  | Integer | ID immobile                | `?immobile=3`     |
| copertina | Boolean | Solo immagini di copertina | `?copertina=true` |

**Response 200 OK:**

```json
[
  {
    "idImmagine": 1,
    "idImmobile": 3,
    "url": "/uploads/immobili/3/foto1.jpg",
    "nomeFile": "foto1.jpg",
    "descrizione": "Vista frontale",
    "copertina": true,
    "ordinamento": 1,
    "dimensioneKb": 850
  }
]
```

---

## Struttura Dati (DTO)

### TypeScript Types

```typescript
// types/admin.ts

export interface UtenteDto {
  idUtente: number;
  nome: string;
  cognome: string;
  email: string;
  telefono: string;
  ruolo: 'PROPRIETARIO' | 'AGENTE' | 'AMMINISTRATORE';
  verificaEmail: boolean;
  consensoPrivacy: boolean;
}

export interface ImmobileDto {
  idImmobile: number;
  idProprietario: number | null;
  tipologia: 'APPARTAMENTO' | 'VILLA' | 'ATTICO' | 'LOFT' | 'BILOCALE' | 'TRILOCALE' | 'QUADRILOCALE' | null;
  indirizzo: string;
  citta: string;
  provincia: string;
  cap: string;
  latitudine: number | null;
  longitudine: number | null;
  stato: 'DISPONIBILE' | 'IN_TRATTATIVA' | 'VENDUTO' | 'RITIRATO' | null;
}

export interface ContrattoDto {
  idContratto: number;
  idImmobile: number | null;
  idAgente: number | null;
  tipoContratto: 'VENDITA' | 'AFFITTO' | 'ACQUISIZIONE' | null;
  dataContratto: string; // ISO date string
  dataScadenzaContratto: string | null;
  pathContrattoPDF: string | null;
}

export interface RichiestaDto {
  idRichiesta: number;
  idUtente: number | null;
  idImmobile: number | null;
  dataRichiesta: string; // ISO date string
  dataAppuntamento: string | null;
  stato: 'IN_ATTESA' | 'IN_ELABORAZIONE' | 'COMPLETATA' | 'ANNULLATA' | null;
  noteUtente: string | null;
  motivoAnnullamento: string | null;
}

export interface VenditaDto {
  idVendita: number;
  idContratto: number | null;
  idImmobile: number | null;
  idUtente: number | null;
  commissionePercentuale: number | null;
}

export interface ImmagineDto {
  idImmagine: number;
  idImmobile: number | null;
  url: string;
  nomeFile: string;
  descrizione: string | null;
  copertina: boolean;
  ordinamento: number | null;
  dimensioneKb: number | null;
}
```

---

## Implementazione Frontend

### 1. Store Zustand (auth.store.ts)

Aggiungere stato per l'amministratore:

```typescript
interface AuthState {
  user: User | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  agentDashboard: AgenteRichiestaDTO[] | null;
  
  // Nuovo: Admin dashboard data
  adminUtenti: UtenteDto[] | null;
  adminImmobili: ImmobileDto[] | null;
  adminContratti: ContrattoDto[] | null;
  adminRichieste: RichiestaDto[] | null;
  adminVendite: VenditaDto[] | null;
  
  // Actions
  login: (user: User) => void;
  logout: () => void;
  setLoading: (value: boolean) => void;
  loadAgentDashboard: () => Promise<void>;
  
  // Nuovo: Admin actions
  loadAdminUtenti: (filters?: AdminUtentiFilters) => Promise<void>;
  loadAdminImmobili: (filters?: AdminImmobiliFilters) => Promise<void>;
  loadAdminContratti: (filters?: AdminContrattiFilters) => Promise<void>;
  loadAdminRichieste: (filters?: AdminRichiesteFilters) => Promise<void>;
  loadAdminVendite: (filters?: AdminVenditeFilters) => Promise<void>;
}

// Implementazione
loadAdminUtenti: async (filters) => {
  const user = get().user;
  if (!user || user.ruolo !== 'AMMINISTRATORE') return;
  
  const params = new URLSearchParams();
  if (filters?.nome) params.append('nome', filters.nome);
  if (filters?.cognome) params.append('cognome', filters.cognome);
  if (filters?.email) params.append('email', filters.email);
  if (filters?.ruolo) params.append('ruolo', filters.ruolo);
  if (filters?.telefono) params.append('telefono', filters.telefono);
  
  const response = await fetch(
    `${import.meta.env.VITE_API_URL}/admin/dashboard/utenti?${params}`,
    { credentials: 'include' }
  );
  
  if (response.ok) {
    const data = await response.json();
    set({ adminUtenti: data });
  }
},
```

### 2. Partialize per localStorage

Escludere i dati admin dalla persistenza (sempre freschi dal server):

```typescript
persist(
  (set, get) => ({ /* ... */ }),
  {
    name: 'auth-storage',
    partialize: (state) => ({
      user: state.user,
      isAuthenticated: state.isAuthenticated,
      // Escludi dashboard data (admin e agente)
    })
  }
)
```

### 3. API Functions (api/admin.ts)

```typescript
// api/admin.ts

const API_URL = import.meta.env.VITE_API_URL;

export interface AdminUtentiFilters {
  nome?: string;
  cognome?: string;
  email?: string;
  ruolo?: 'PROPRIETARIO' | 'AGENTE' | 'AMMINISTRATORE';
  telefono?: string;
}

export async function fetchAdminUtenti(filters?: AdminUtentiFilters): Promise<UtenteDto[]> {
  const params = new URLSearchParams();
  if (filters?.nome) params.append('nome', filters.nome);
  if (filters?.cognome) params.append('cognome', filters.cognome);
  if (filters?.email) params.append('email', filters.email);
  if (filters?.ruolo) params.append('ruolo', filters.ruolo);
  if (filters?.telefono) params.append('telefono', filters.telefono);
  
  const response = await fetch(
    `${API_URL}/admin/dashboard/utenti?${params}`,
    { credentials: 'include' }
  );
  
  if (!response.ok) {
    throw new Error(`HTTP ${response.status}: ${response.statusText}`);
  }
  
  return response.json();
}

// Ripetere per ogni risorsa: fetchAdminImmobili, fetchAdminContratti, ecc.
```

### 4. Componente Dashboard (pages/AdminDashboard.tsx)

#### Layout Base

```tsx
import { useEffect, useState } from 'react';
import { useAuthStore } from '@/store/auth.store';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';

const AdminDashboard = () => {
  const user = useAuthStore((s) => s.user);
  const [activeTab, setActiveTab] = useState('utenti');
  
  useEffect(() => {
    if (user?.ruolo !== 'AMMINISTRATORE') {
      // Redirect o errore
      return;
    }
  }, [user]);
  
  return (
    <div className="container mx-auto p-6">
      <h1 className="text-3xl font-bold mb-6">Dashboard Amministratore</h1>
    
      <Tabs value={activeTab} onValueChange={setActiveTab}>
        <TabsList className="grid grid-cols-6 w-full">
          <TabsTrigger value="utenti">👥 Utenti</TabsTrigger>
          <TabsTrigger value="immobili">🏠 Immobili</TabsTrigger>
          <TabsTrigger value="contratti">📄 Contratti</TabsTrigger>
          <TabsTrigger value="richieste">📋 Richieste</TabsTrigger>
          <TabsTrigger value="vendite">💰 Vendite</TabsTrigger>
          <TabsTrigger value="immagini">🖼️ Immagini</TabsTrigger>
        </TabsList>
      
        <TabsContent value="utenti">
          <UtentiTab />
        </TabsContent>
      
        <TabsContent value="immobili">
          <ImmobiliTab />
        </TabsContent>
      
        {/* Altri tab */}
      </Tabs>
    </div>
  );
};

export default AdminDashboard;
```

#### Tab Utenti (esempio completo)

```tsx
// components/admin/UtentiTab.tsx

import { useEffect, useState } from 'react';
import { useAuthStore } from '@/store/auth.store';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import type { UtenteDto } from '@/types/admin';

const UtentiTab = () => {
  const adminUtenti = useAuthStore((s) => s.adminUtenti);
  const loadAdminUtenti = useAuthStore((s) => s.loadAdminUtenti);
  
  const [filters, setFilters] = useState({
    nome: '',
    cognome: '',
    email: '',
    ruolo: '',
    telefono: ''
  });
  
  useEffect(() => {
    loadAdminUtenti();
  }, []);
  
  const handleSearch = () => {
    loadAdminUtenti(filters);
  };
  
  const handleReset = () => {
    setFilters({ nome: '', cognome: '', email: '', ruolo: '', telefono: '' });
    loadAdminUtenti();
  };
  
  if (!adminUtenti) return <p>Caricamento utenti...</p>;
  
  return (
    <div className="space-y-6">
      {/* Filtri */}
      <Card>
        <CardHeader>
          <CardTitle>Filtri di Ricerca</CardTitle>
          <CardDescription>Cerca utenti per nome, email, ruolo, ecc.</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <Input
              placeholder="Nome"
              value={filters.nome}
              onChange={(e) => setFilters({ ...filters, nome: e.target.value })}
            />
            <Input
              placeholder="Cognome"
              value={filters.cognome}
              onChange={(e) => setFilters({ ...filters, cognome: e.target.value })}
            />
            <Input
              placeholder="Email"
              value={filters.email}
              onChange={(e) => setFilters({ ...filters, email: e.target.value })}
            />
            <Input
              placeholder="Telefono"
              value={filters.telefono}
              onChange={(e) => setFilters({ ...filters, telefono: e.target.value })}
            />
            <Select value={filters.ruolo} onValueChange={(v) => setFilters({ ...filters, ruolo: v })}>
              <SelectTrigger>
                <SelectValue placeholder="Seleziona ruolo" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="">Tutti</SelectItem>
                <SelectItem value="PROPRIETARIO">Proprietario</SelectItem>
                <SelectItem value="AGENTE">Agente</SelectItem>
                <SelectItem value="AMMINISTRATORE">Amministratore</SelectItem>
              </SelectContent>
            </Select>
            <div className="flex gap-2">
              <Button onClick={handleSearch} className="flex-1">🔍 Cerca</Button>
              <Button onClick={handleReset} variant="outline">🔄 Reset</Button>
            </div>
          </div>
        </CardContent>
      </Card>
    
      {/* Statistiche */}
      <Card>
        <CardHeader>
          <CardTitle>Statistiche Utenti</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="grid grid-cols-4 gap-4">
            <div className="text-center">
              <p className="text-2xl font-bold">{adminUtenti.length}</p>
              <p className="text-sm text-gray-600">Totale</p>
            </div>
            <div className="text-center">
              <p className="text-2xl font-bold text-blue-600">
                {adminUtenti.filter(u => u.ruolo === 'AGENTE').length}
              </p>
              <p className="text-sm text-gray-600">Agenti</p>
            </div>
            <div className="text-center">
              <p className="text-2xl font-bold text-green-600">
                {adminUtenti.filter(u => u.ruolo === 'PROPRIETARIO').length}
              </p>
              <p className="text-sm text-gray-600">Proprietari</p>
            </div>
            <div className="text-center">
              <p className="text-2xl font-bold text-purple-600">
                {adminUtenti.filter(u => u.ruolo === 'AMMINISTRATORE').length}
              </p>
              <p className="text-sm text-gray-600">Amministratori</p>
            </div>
          </div>
        </CardContent>
      </Card>
    
      {/* Tabella Utenti */}
      <Card>
        <CardHeader>
          <CardTitle>Elenco Utenti ({adminUtenti.length})</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>ID</TableHead>
                <TableHead>Nome</TableHead>
                <TableHead>Cognome</TableHead>
                <TableHead>Email</TableHead>
                <TableHead>Telefono</TableHead>
                <TableHead>Ruolo</TableHead>
                <TableHead>Verificato</TableHead>
                <TableHead>Azioni</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {adminUtenti.map((utente) => (
                <TableRow key={utente.idUtente}>
                  <TableCell>{utente.idUtente}</TableCell>
                  <TableCell>{utente.nome}</TableCell>
                  <TableCell>{utente.cognome}</TableCell>
                  <TableCell>{utente.email}</TableCell>
                  <TableCell>{utente.telefono}</TableCell>
                  <TableCell>
                    <span className={`px-2 py-1 rounded text-xs font-semibold ${
                      utente.ruolo === 'AMMINISTRATORE' ? 'bg-purple-100 text-purple-800' :
                      utente.ruolo === 'AGENTE' ? 'bg-blue-100 text-blue-800' :
                      'bg-green-100 text-green-800'
                    }`}>
                      {utente.ruolo}
                    </span>
                  </TableCell>
                  <TableCell>
                    {utente.verificaEmail ? '✅' : '❌'}
                  </TableCell>
                  <TableCell>
                    <Button size="sm" variant="outline">📝 Modifica</Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </div>
  );
};

export default UtentiTab;
```

---

## Autenticazione e Protezione

### Protected Route

```tsx
// routes/index.tsx

function AdminProtected() {
  return (
    <ProtectedRoute roles={["AMMINISTRATORE"]}>
      <AdminDashboard />
    </ProtectedRoute>
  );
}

export const router = createBrowserRouter([
  {
    path: "/",
    element: <RootLayout />,
    children: [
      // ... altre routes
      {
        path: "/admin",
        element: <AdminProtected />
      }
    ]
  }
]);
```

### Controllo Sessione Backend

Attualmente disabilitato per testing. Per riattivare, rimuovere i commenti in `AmministratoreDashboard.java`:

```java
@GetMapping("/utenti")
public ResponseEntity<List<UtenteDto>> getUtenti(HttpSession session, ...) {
    // Rimuovi i commenti:
    if (!isAmministratore(session)) {
        return ResponseEntity.status(403).build();
    }
  
    // ... resto del codice
}
```

---

## Funzionalità da Implementare

### Priorità Alta

1. **Tab Utenti** ✅

   - [ ] Tabella con filtri (nome, cognome, email, ruolo, telefono)
   - [ ] Statistiche (totale, agenti, proprietari, admin)
   - [ ] Modifica utente (modal)
   - [ ] Creazione utente (modal)
   - [ ] Reset password
2. **Tab Immobili** 🔶

   - [ ] Tabella con filtri (città, provincia, tipologia, stato, proprietario)
   - [ ] Statistiche (totale per stato, per tipologia)
   - [ ] Vista dettaglio immobile con immagini
   - [ ] Modifica stato immobile
   - [ ] Assegnazione agente
3. **Tab Contratti** 🔶

   - [ ] Tabella con filtri (tipo, immobile, agente)
   - [ ] Statistiche (per tipo, scadenze prossime)
   - [ ] Download PDF contratto
   - [ ] Creazione contratto (modal)
   - [ ] Modifica contratto

### Priorità Media

4. **Tab Richieste** 📋

   - [ ] Tabella con filtri (stato, utente, immobile)
   - [ ] Statistiche (per stato)
   - [ ] Cambio stato richiesta
   - [ ] Assegnazione agente
   - [ ] Vista timeline richiesta
5. **Tab Vendite** 💰

   - [ ] Tabella con filtri (contratto, immobile, utente)
   - [ ] Statistiche (totale vendite, commissioni)
   - [ ] Grafici mensili
   - [ ] Export Excel
6. **Tab Immagini** 🖼️

   - [ ] Griglia immagini con filtri (immobile, copertina)
   - [ ] Upload nuove immagini
   - [ ] Riordinamento immagini
   - [ ] Impostazione copertina
   - [ ] Eliminazione immagini

### Funzionalità Avanzate

7. **Dashboard Overview** 📊

   - [ ] Pagina iniziale con KPI
   - [ ] Grafici attività recenti
   - [ ] Notifiche/Alert (es: contratti in scadenza)
   - [ ] Lista ultimi utenti registrati
   - [ ] Lista ultime richieste
8. **Export & Report** 📈

   - [ ] Export CSV/Excel per ogni tabella
   - [ ] Report PDF personalizzati
   - [ ] Statistiche avanzate con grafici
9. **Audit Log** 📝

   - [ ] Log modifiche (chi, quando, cosa)
   - [ ] Filtri cronologici
   - [ ] Rollback modifiche

---

## Best Practices

### Performance

- **Paginazione**: Implementare pagination per tabelle grandi (>100 record)
- **Debounce**: Usare debounce su filtri di ricerca (500ms)
- **Lazy Loading**: Caricare immagini solo quando visibili
- **Cache**: Mantenere dati in Zustand per evitare fetch ripetuti

### UX/UI

- **Loading States**: Mostrare skeleton/spinner durante fetch
- **Error Handling**: Toast notifications per errori
- **Conferme**: Modal di conferma per azioni distruttive
- **Feedback**: Success toast dopo creazione/modifica
- **Validazione**: Client-side validation su form

### Codice

- **Componenti Riutilizzabili**: Creare componenti generici per filtri, tabelle, modal
- **Types**: Definire tutti i DTO TypeScript in `types/admin.ts`
- **API Layer**: Centralizzare chiamate API in `api/admin.ts`
- **Error Boundaries**: Wrap componenti critici con error boundaries

---

## Testing

### Credenziali Test

```
Email: anna.verdi@example.com
Password: admin123
Ruolo: AMMINISTRATORE
```

### Dati di Test

Nel database locale sono presenti:

- **14 utenti** (3 agenti, 10 proprietari, 1 admin)
- **20 immobili** (Torino, Novara, Asti, Cuneo, Alessandria)
- **8 contratti** (6 vendita, 2 acquisizione)
- **13 richieste** (4 IN_ATTESA, 6 IN_ELABORAZIONE, 2 COMPLETATA, 1 ANNULLATA)
- **10 valutazioni**

### Test Cases

1. Login come amministratore ✓
2. Accesso a `/admin` ✓
3. Tab Utenti: filtro per ruolo "AGENTE" → deve mostrare 3 utenti
4. Tab Immobili: filtro per città "Torino" → deve mostrare N immobili
5. Tab Contratti: filtro per tipo "VENDITA" → deve mostrare 6 contratti
6. Tab Richieste: filtro per stato "IN_ELABORAZIONE" → deve mostrare 6 richieste

---

## Riferimenti

- **Backend Controller**: `back-end/residea/src/main/java/com/residea/residea/controller/AmministratoreDashboard.java`
- **Template Thymeleaf**: `back-end/residea/src/main/resources/templates/dashboard-*.html`
- **Dashboard Agente**: `docs/EndpointsDashboard.md`
- **Database Schema**: `database/init/01-init-schema.sql`

---

