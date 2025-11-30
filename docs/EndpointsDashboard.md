# API Endpoints - Dashboard Agente

Documentazione completa degli endpoint REST per la dashboard dell'agente immobiliare.

## Indice
- [Dashboard Principale](#dashboard-principale)
- [Gestione Richieste](#gestione-richieste)
- [Gestione Immobili](#gestione-immobili)
- [Gestione Acquisizioni](#gestione-acquisizioni)
- [Gestione Stato Richieste](#gestione-stato-richieste)
- [DTO Reference](#dto-reference)

---

## Dashboard Principale

### GET /api/agente/dashboard/{idAgente}

Recupera i dati aggregati completi per la dashboard dell'agente con tutte le richieste gestite.

**Autenticazione:** Richiesta  
**Metodo:** GET  
**URL:** `http://localhost:8080/api/agente/dashboard/{idAgente}`

#### Parametri Path
| Nome | Tipo | Descrizione |
|------|------|-------------|
| idAgente | Integer | ID dell'agente |

#### Response 200 OK
```json
[
  {
    "idContratto": 1,
    "tipoContratto": "VENDITA",
    "dataContratto": "2025-06-01",
    "dataScadenzaContratto": "2026-06-01",
    "pathContrattoPDF": "/uploads/contratti/contratto_1.pdf",
    "idImmobile": 3,
    "tipologia": "VILLA",
    "indirizzo": "Via delle Rose 5",
    "citta": "Novara",
    "provincia": "NO",
    "cap": "28100",
    "stato": "DISPONIBILE",
    "nStanze": 5,
    "nBagni": 3,
    "nPiano": 0,
    "nPianiImmobile": 2,
    "balconeTerrazzo": true,
    "giardino": true,
    "garage": true,
    "ascensore": false,
    "cantina": true,
    "tipoRiscaldamento": "AUTONOMO",
    "annoCostruzione": 1995,
    "condizioneImmobile": "OTTIMO",
    "classeEnergetica": "A",
    "superficieMq": 220.0,
    "superficieBalconeTerrazzo": 15.0,
    "superficieGiardino": 500.0,
    "superficieGarage": 30.0,
    "superficieCantina": 20.0,
    "idRichiesta": 1,
    "dataRichiesta": "2025-05-15",
    "dataAppuntamento": "2025-05-20",
    "statoRichiesta": "IN_ELABORAZIONE",
    "noteUtente": "Interessato a vendere",
    "motivoAnnullamento": null,
    "idUtente": 2,
    "nomeUtente": "Luca",
    "cognomeUtente": "Bianchi",
    "telefonoUtente": "3332345678",
    "emailUtente": "luca.bianchi@example.com",
    "idValutazione": 1,
    "valoreBase": 200000.0,
    "fattoreAggiustamento": 1.15,
    "valoreMedio": 230000,
    "valoreMin": 210000,
    "valoreMax": 250000,
    "confidence": 85.5
  }
]
```

#### Response 500 Internal Server Error
Errore interno del server durante il recupero dei dati.

**Note:**
- Restituisce dati aggregati da 7 tabelle: Contratto, Immobile, DettagliImmobile, Superficie, Richiesta, Utente, ValutazioneImmobile
- Include tutti i contratti gestiti dall'agente con informazioni complete

---

### GET /api/agente/{idAgente}/dashboard/stats

Recupera le statistiche aggregate per la dashboard dell'agente.

**Autenticazione:** Richiesta  
**Metodo:** GET  
**URL:** `http://localhost:8080/api/agente/{idAgente}/dashboard/stats`

#### Parametri Path
| Nome | Tipo | Descrizione |
|------|------|-------------|
| idAgente | Integer | ID dell'agente |

#### Response 200 OK
```json
{
  "richiesteInCarico": 5,
  "richiesteCompletate": 12,
  "richiesteArchiviate": 3
}
```

#### Response 500 Internal Server Error
Errore interno del server durante il recupero delle statistiche.

**Campi Statistiche:**
- `richiesteInCarico`: Numero richieste con stato IN_ELABORAZIONE
- `richiesteCompletate`: Numero richieste con stato COMPLETATA
- `richiesteArchiviate`: Numero richieste con stato ANNULLATA

---

### GET /api/agente/richiesta-dettagli/{idContratto}

Recupera i dettagli completi di una singola richiesta/contratto.

**Autenticazione:** Richiesta  
**Metodo:** GET  
**URL:** `http://localhost:8080/api/agente/richiesta-dettagli/{idContratto}`

#### Parametri Path
| Nome | Tipo | Descrizione |
|------|------|-------------|
| idContratto | Integer | ID del contratto |

#### Response 200 OK
```json
{
  "idContratto": 1,
  "tipoContratto": "VENDITA",
  "dataContratto": "2025-06-01",
  "dataScadenzaContratto": "2026-06-01",
  "pathContrattoPDF": "/uploads/contratti/contratto_1.pdf",
  "idImmobile": 3,
  "tipologia": "VILLA",
  "indirizzo": "Via delle Rose 5",
  "citta": "Novara",
  "provincia": "NO",
  "cap": "28100",
  "stato": "DISPONIBILE",
  "nStanze": 5,
  "nBagni": 3,
  "nPiano": 0,
  "nPianiImmobile": 2,
  "balconeTerrazzo": true,
  "giardino": true,
  "garage": true,
  "ascensore": false,
  "cantina": true,
  "tipoRiscaldamento": "AUTONOMO",
  "annoCostruzione": 1995,
  "condizioneImmobile": "OTTIMO",
  "classeEnergetica": "A",
  "superficieMq": 220.0,
  "superficieBalconeTerrazzo": 15.0,
  "superficieGiardino": 500.0,
  "superficieGarage": 30.0,
  "superficieCantina": 20.0,
  "idRichiesta": 1,
  "dataRichiesta": "2025-05-15",
  "dataAppuntamento": "2025-05-20",
  "statoRichiesta": "IN_ELABORAZIONE",
  "noteUtente": "Interessato a vendere",
  "motivoAnnullamento": null,
  "idUtente": 2,
  "nomeUtente": "Luca",
  "cognomeUtente": "Bianchi",
  "telefonoUtente": "3332345678",
  "emailUtente": "luca.bianchi@example.com",
  "idValutazione": 1,
  "valoreBase": 200000.0,
  "fattoreAggiustamento": 1.15,
  "valoreMedio": 230000,
  "valoreMin": 210000,
  "valoreMax": 250000,
  "confidence": 85.5
}
```

#### Response 404 Not Found
Contratto non trovato con l'ID specificato.

#### Response 500 Internal Server Error
Errore interno del server durante il recupero dei dati.

---

## Gestione Richieste

### GET /api/agente/richieste/attesa

Recupera tutte le richieste in attesa (non ancora prese in carico da nessun agente).

**Autenticazione:** Richiesta  
**Metodo:** GET  
**URL:** `http://localhost:8080/api/agente/richieste/attesa`

#### Response 200 OK
```json
[
  {
    "idRichiesta": 5,
    "dataRichiesta": "2025-11-28",
    "dataAppuntamento": "2025-12-05",
    "stato": "IN_ATTESA",
    "noteUtente": "Prima casa, interessato a valutazione",
    "idImmobile": 4,
    "tipologia": "APPARTAMENTO",
    "indirizzo": "Via Roma 45",
    "citta": "Milano",
    "provincia": "MI",
    "cap": "20100",
    "nStanze": 3,
    "nBagni": 2,
    "superficieMq": 95.0,
    "idUtente": 8,
    "nomeUtente": "Marco",
    "cognomeUtente": "Verdi",
    "emailUtente": "marco.verdi@example.com",
    "telefonoUtente": "3334567890"
  }
]
```

#### Response 500 Internal Server Error
Errore interno del server durante il recupero delle richieste.

**Note:**
- Restituisce solo richieste con stato IN_ATTESA
- Include informazioni base immobile e utente richiedente
- Utilizzato per la schermata "Richieste in Attesa"

---

### GET /api/agente/{idAgente}/richieste/carico

Recupera le richieste prese in carico dall'agente specifico.

**Autenticazione:** Richiesta  
**Metodo:** GET  
**URL:** `http://localhost:8080/api/agente/{idAgente}/richieste/carico`

#### Parametri Path
| Nome | Tipo | Descrizione |
|------|------|-------------|
| idAgente | Integer | ID dell'agente |

#### Response 200 OK
```json
[
  {
    "idRichiesta": 1,
    "dataRichiesta": "2025-05-15",
    "dataAppuntamento": "2025-05-20",
    "stato": "IN_ELABORAZIONE",
    "noteUtente": "Interessato a vendere",
    "idImmobile": 3,
    "tipologia": "VILLA",
    "indirizzo": "Via delle Rose 5",
    "citta": "Novara",
    "provincia": "NO",
    "cap": "28100",
    "nStanze": 5,
    "nBagni": 3,
    "superficieMq": 220.0,
    "idUtente": 2,
    "nomeUtente": "Luca",
    "cognomeUtente": "Bianchi",
    "emailUtente": "luca.bianchi@example.com",
    "telefonoUtente": "3332345678"
  }
]
```

#### Response 500 Internal Server Error
Errore interno del server durante il recupero delle richieste.

**Note:**
- Filtra richieste tramite esistenza di contratto associato all'agente
- Include richieste in stato IN_ELABORAZIONE o COMPLETATA
- Utilizzato per la schermata "Richieste in Carico"

---

### POST /api/agente/{idAgente}/richieste/{idRichiesta}/prendi-in-carico

Assegna una richiesta all'agente creando un contratto.

**Autenticazione:** Richiesta  
**Metodo:** POST  
**URL:** `http://localhost:8080/api/agente/{idAgente}/richieste/{idRichiesta}/prendi-in-carico`

#### Parametri Path
| Nome | Tipo | Descrizione |
|------|------|-------------|
| idAgente | Integer | ID dell'agente |
| idRichiesta | Integer | ID della richiesta da prendere in carico |

#### Response 200 OK
```
Richiesta presa in carico con successo
```

#### Response 500 Internal Server Error
```
Errore: [messaggio errore dettagliato]
```

**Operazioni Eseguite:**
1. Verifica esistenza agente e richiesta
2. Verifica che richiesta sia in stato IN_ATTESA
3. Crea nuovo contratto associando agente e immobile
4. Cambia stato richiesta da IN_ATTESA a IN_ELABORAZIONE
5. Salva contratto e aggiorna richiesta

---

## Gestione Immobili

### GET /api/immobili/dashboard/all

Recupera la lista completa di tutti gli immobili con informazioni aggregate (proprietario, agente, richiesta, contratto, valutazione, superficie).

**Autenticazione:** Richiesta  
**Metodo:** GET  
**URL:** `http://localhost:8080/api/immobili/dashboard/all`

#### Response 200 OK
```json
[
  {
    "idImmobile": 1,
    "tipologia": "APPARTAMENTO",
    "indirizzo": "Via Garibaldi 10",
    "citta": "Torino",
    "provincia": "TO",
    "cap": "10121",
    "stato": "DISPONIBILE",
    "idProprietario": 1,
    "nomeProprietario": "Mario",
    "cognomeProprietario": "Rossi",
    "emailProprietario": "mario.rossi@example.com",
    "telefonoProprietario": "3331234567",
    "idAgente": null,
    "nomeAgente": null,
    "cognomeAgente": null,
    "idRichiesta": 2,
    "statoRichiesta": "IN_ELABORAZIONE",
    "idContratto": null,
    "statoContratto": null,
    "valutazioneStimata": 250000.0,
    "superficieTotale": 85.0,
    "latitudine": 45.0703,
    "longitudine": 7.6869
  }
]
```

#### Response 500 Internal Server Error
Errore interno del server durante il recupero dei dati.

---

### GET /api/immobili/dashboard/{id}

Recupera i dettagli di un singolo immobile con tutte le informazioni aggregate.

**Autenticazione:** Richiesta  
**Metodo:** GET  
**URL:** `http://localhost:8080/api/immobili/dashboard/{id}`

#### Parametri Path
| Nome | Tipo | Descrizione |
|------|------|-------------|
| id | Integer | ID dell'immobile |

#### Response 200 OK
```json
{
  "idImmobile": 3,
  "tipologia": "VILLA",
  "indirizzo": "Via delle Rose 5",
  "citta": "Novara",
  "provincia": "NO",
  "cap": "28100",
  "stato": "DISPONIBILE",
  "idProprietario": 2,
  "nomeProprietario": "Luca",
  "cognomeProprietario": "Bianchi",
  "emailProprietario": "luca.bianchi@example.com",
  "telefonoProprietario": "3332345678",
  "idAgente": 4,
  "nomeAgente": "Sofia",
  "cognomeAgente": "Costa",
  "idRichiesta": 1,
  "statoRichiesta": "IN_ATTESA",
  "idContratto": 1,
  "statoContratto": "ATTIVO",
  "valutazioneStimata": null,
  "superficieTotale": 220.0,
  "latitudine": 45.448,
  "longitudine": 8.621
}
```

#### Response 404 Not Found
Immobile non trovato con l'ID specificato.

#### Response 500 Internal Server Error
Errore interno del server durante il recupero dei dati.

---

## Gestione Acquisizioni

### GET /api/agente/{idAgente}/acquisizioni

Recupera la lista di tutti i contratti (acquisizioni) completati da un agente specifico.

**Autenticazione:** Richiesta  
**Metodo:** GET  
**URL:** `http://localhost:8080/api/agente/{idAgente}/acquisizioni`

#### Parametri Path
| Nome | Tipo | Descrizione |
|------|------|-------------|
| idAgente | Integer | ID dell'agente |

#### Response 200 OK
```json
[
  {
    "idContratto": 1,
    "dataInizio": "2025-06-01",
    "dataFine": "2026-06-01",
    "stato": "COMPLETATA",
    "commissione": null,
    "terminiCondizioni": null,
    "idCliente": 1,
    "nomeCliente": "Mario",
    "cognomeCliente": "Rossi",
    "emailCliente": "mario.rossi@example.com",
    "telefonoCliente": "3331234567",
    "idImmobile": 3,
    "tipologiaImmobile": "VILLA",
    "indirizzoImmobile": "Via delle Rose 5",
    "cittaImmobile": "Novara",
    "provinciaImmobile": "NO",
    "valutazioneFinale": null,
    "superficieTotale": 220.0,
    "nomeProprietario": "Luca",
    "cognomeProprietario": "Bianchi"
  }
]
```

#### Response 500 Internal Server Error
Errore interno del server durante il recupero dei dati.

**Note:**
- Vengono restituiti solo i contratti con `statoContratto = ATTIVO` per l'agente specificato
- I campi `stato`, `commissione` e `terminiCondizioni` sono impostati a valori di default perché l'entità `Contratto` non contiene questi campi nella struttura attuale del database

---

## Gestione Stato Richieste

### PUT /api/agente/{idAgente}/richieste/{idRichiesta}/stato

Aggiorna lo stato di una richiesta di valutazione con validazione delle transizioni di stato consentite.

**Autenticazione:** Richiesta  
**Metodo:** PUT  
**URL:** `http://localhost:8080/api/agente/{idAgente}/richieste/{idRichiesta}/stato?nuovoStato={STATO}`

#### Parametri Path
| Nome | Tipo | Descrizione |
|------|------|-------------|
| idAgente | Integer | ID dell'agente che gestisce la richiesta |
| idRichiesta | Integer | ID della richiesta da aggiornare |

#### Parametri Query
| Nome | Tipo | Descrizione | Valori |
|------|------|-------------|--------|
| nuovoStato | String | Nuovo stato da assegnare | IN_ATTESA, IN_ELABORAZIONE, COMPLETATA, ANNULLATA |

#### Response 200 OK
```
Stato richiesta aggiornato con successo
```

#### Response 400 Bad Request
```
Errore: Transizione non valida da {STATO_CORRENTE} a {NUOVO_STATO}
```

#### Response 404 Not Found
```
Errore: Richiesta non trovata o non in carico a questo agente
```
oppure
```
Errore: Agente non trovato
```
oppure
```
Errore: Contratto non trovato per questa richiesta
```

#### Response 500 Internal Server Error
Errore interno del server durante l'aggiornamento.

### Regole di Transizione Stati

Le transizioni di stato sono validate secondo le seguenti regole business:

| Stato Corrente | Stati Consentiti | Note |
|----------------|------------------|------|
| IN_ATTESA | IN_ELABORAZIONE, ANNULLATA | Prima fase - richiesta appena ricevuta |
| IN_ELABORAZIONE | COMPLETATA, ANNULLATA | Fase intermedia - valutazione in corso |
| COMPLETATA | *(nessuno)* | Stato terminale - non modificabile |
| ANNULLATA | *(nessuno)* | Stato terminale - non modificabile |

**Esempio Flusso Standard:**
```
IN_ATTESA → IN_ELABORAZIONE → COMPLETATA
```

**Annullamento Possibile da Qualsiasi Stato Non Terminale:**
```
IN_ATTESA → ANNULLATA
IN_ELABORAZIONE → ANNULLATA
```

**Transizioni NON Valide (esempi):**
- IN_ELABORAZIONE → IN_ATTESA ❌
- COMPLETATA → IN_ATTESA ❌
- COMPLETATA → IN_ELABORAZIONE ❌
- ANNULLATA → qualsiasi altro stato ❌

---

## DTO Reference

### AgenteRichiestaDTO

DTO principale per la visualizzazione aggregata dei dati dashboard agente.

| Campo | Tipo | Descrizione | Nullable |
|-------|------|-------------|----------|
| **Contratto** ||||
| idContratto | Integer | ID univoco contratto | Sì |
| tipoContratto | String | Tipo contratto (VENDITA, AFFITTO) | Sì |
| dataContratto | String (Date) | Data stipula contratto | Sì |
| dataScadenzaContratto | String (Date) | Data scadenza contratto | Sì |
| pathContrattoPDF | String | Path file PDF contratto | Sì |
| **Immobile** ||||
| idImmobile | Integer | ID univoco immobile | Sì |
| tipologia | String | Tipo immobile (APPARTAMENTO, VILLA, etc) | Sì |
| indirizzo | String | Via e numero civico | Sì |
| citta | String | Città | Sì |
| provincia | String | Sigla provincia | Sì |
| cap | String | Codice postale | Sì |
| stato | String | Stato immobile (DISPONIBILE, etc) | Sì |
| **Dettagli Immobile** ||||
| nStanze | Integer | Numero stanze | Sì |
| nBagni | Integer | Numero bagni | Sì |
| nPiano | Integer | Piano (0 = terra) | Sì |
| nPianiImmobile | Integer | Numero totale piani edificio | Sì |
| balconeTerrazzo | Boolean | Presenza balcone/terrazzo | Sì |
| giardino | Boolean | Presenza giardino | Sì |
| garage | Boolean | Presenza garage | Sì |
| ascensore | Boolean | Presenza ascensore | Sì |
| cantina | Boolean | Presenza cantina | Sì |
| tipoRiscaldamento | String | Tipo riscaldamento (AUTONOMO, CENTRALIZZATO, etc) | Sì |
| annoCostruzione | Integer | Anno costruzione edificio | Sì |
| condizioneImmobile | String | Condizione (NUOVO, OTTIMO, BUONO, DA_RISTRUTTURARE) | Sì |
| classeEnergetica | String | Classe energetica (A, B, C, etc) | Sì |
| **Superfici** ||||
| superficieMq | Double | Superficie totale (mq) | Sì |
| superficieBalconeTerrazzo | Double | Superficie balcone/terrazzo (mq) | Sì |
| superficieGiardino | Double | Superficie giardino (mq) | Sì |
| superficieGarage | Double | Superficie garage (mq) | Sì |
| superficieCantina | Double | Superficie cantina (mq) | Sì |
| **Richiesta** ||||
| idRichiesta | Integer | ID univoco richiesta | Sì |
| dataRichiesta | String (Date) | Data creazione richiesta | Sì |
| dataAppuntamento | String (Date) | Data appuntamento valutazione | Sì |
| statoRichiesta | String | Stato (IN_ATTESA, IN_ELABORAZIONE, COMPLETATA, ANNULLATA) | Sì |
| noteUtente | String | Note inserite dall'utente | Sì |
| motivoAnnullamento | String | Motivo annullamento (se ANNULLATA) | Sì |
| **Utente Richiedente** ||||
| idUtente | Integer | ID utente che ha fatto richiesta | Sì |
| nomeUtente | String | Nome utente | Sì |
| cognomeUtente | String | Cognome utente | Sì |
| telefonoUtente | String | Telefono utente | Sì |
| emailUtente | String | Email utente | Sì |
| **Valutazione Immobile** ||||
| idValutazione | Integer | ID valutazione | Sì |
| valoreBase | Double | Valore base calcolato (EUR) | Sì |
| fattoreAggiustamento | Double | Fattore moltiplicativo aggiustamento | Sì |
| valoreMedio | Integer | Valore medio stimato (EUR) | Sì |
| valoreMin | Integer | Valore minimo range (EUR) | Sì |
| valoreMax | Integer | Valore massimo range (EUR) | Sì |
| confidence | Double | Livello confidenza stima (%) | Sì |

**Aggregazione Dati:**
- **Contratto**: Dati contrattuali e path PDF
- **Immobile**: Informazioni base proprietà
- **DettagliImmobile**: Caratteristiche dettagliate
- **Superficie**: Metrature di tutti gli spazi
- **Richiesta**: Stato workflow valutazione
- **Utente**: Dati contatto richiedente
- **ValutazioneImmobile**: Stima valore e range

---

### DashboardStatsDTO

DTO per le statistiche aggregate della dashboard.

| Campo | Tipo | Descrizione | Nullable |
|-------|------|-------------|----------|
| richiesteInCarico | Integer | Numero richieste in stato IN_ELABORAZIONE | No |
| richiesteCompletate | Integer | Numero richieste in stato COMPLETATA | No |
| richiesteArchiviate | Integer | Numero richieste in stato ANNULLATA | No |

**Calcolo Contatori:**
- `richiesteInCarico`: COUNT richieste con stato = IN_ELABORAZIONE E agente assegnato
- `richiesteCompletate`: COUNT richieste con stato = COMPLETATA E agente assegnato
- `richiesteArchiviate`: COUNT richieste con stato = ANNULLATA E agente assegnato

---

### RichiestaCardDTO

DTO per la visualizzazione card richieste (versione semplificata).

| Campo | Tipo | Descrizione | Nullable |
|-------|------|-------------|----------|
| **Richiesta** ||||
| idRichiesta | Integer | ID univoco richiesta | No |
| dataRichiesta | String (Date) | Data creazione richiesta | No |
| dataAppuntamento | String (Date) | Data appuntamento valutazione | Sì |
| stato | String | Stato richiesta | No |
| noteUtente | String | Note utente | Sì |
| **Immobile Base** ||||
| idImmobile | Integer | ID immobile | No |
| tipologia | String | Tipo immobile | No |
| indirizzo | String | Indirizzo | No |
| citta | String | Città | No |
| provincia | String | Provincia | No |
| cap | String | CAP | No |
| nStanze | Integer | Numero stanze | Sì |
| nBagni | Integer | Numero bagni | Sì |
| superficieMq | Double | Superficie totale (mq) | Sì |
| **Utente Base** ||||
| idUtente | Integer | ID utente richiedente | No |
| nomeUtente | String | Nome utente | No |
| cognomeUtente | String | Cognome utente | No |
| emailUtente | String | Email utente | No |
| telefonoUtente | String | Telefono utente | Sì |

**Utilizzo:**
- Schermata "Richieste in Attesa"
- Schermata "Richieste in Carico"
- Preview card nelle liste

---

### ImmobileListDTO

DTO per la visualizzazione aggregata degli immobili nella dashboard.

| Campo | Tipo | Descrizione | Nullable |
|-------|------|-------------|----------|
| idImmobile | Integer | ID univoco immobile | No |
| tipologia | String | Tipo immobile (APPARTAMENTO, VILLA, etc) | No |
| indirizzo | String | Via e numero civico | No |
| citta | String | Città | No |
| provincia | String | Sigla provincia | No |
| cap | String | Codice postale | No |
| stato | String | Stato immobile (DISPONIBILE, etc) | No |
| idProprietario | Integer | ID proprietario | Sì |
| nomeProprietario | String | Nome proprietario | Sì |
| cognomeProprietario | String | Cognome proprietario | Sì |
| emailProprietario | String | Email proprietario | Sì |
| telefonoProprietario | String | Telefono proprietario | Sì |
| idAgente | Integer | ID agente assegnato | Sì |
| nomeAgente | String | Nome agente | Sì |
| cognomeAgente | String | Cognome agente | Sì |
| idRichiesta | Integer | ID richiesta valutazione | Sì |
| statoRichiesta | String | Stato richiesta (IN_ATTESA, IN_ELABORAZIONE, COMPLETATA, ANNULLATA) | Sì |
| idContratto | Integer | ID contratto | Sì |
| statoContratto | String | Stato contratto (ATTIVO, etc) | Sì |
| valutazioneStimata | Double | Valore stimato immobile (EUR) | Sì |
| superficieTotale | Double | Superficie totale (mq) | Sì |
| latitudine | Double | Coordinata GPS latitudine | No |
| longitudine | Double | Coordinata GPS longitudine | No |

**Aggregazione Dati:**
- **Immobile**: Dati principali (tipologia, indirizzo, coordinate)
- **Proprietario**: Informazioni contatto estratte da relazione `Immobile → Utente`
- **Agente**: Nome/cognome estratto da `Richiesta → Agente`
- **Richiesta**: Stato valutazione corrente
- **Contratto**: Stato contrattuale se presente (query su `Contratto.idImmobile`)
- **Valutazione**: Valore stimato da `ValutazioneImmobile.valoreMedio`
- **Superficie**: Totale mq da `Superficie.superficieMq`

---

### AcquisizioneDTO

DTO per la visualizzazione dei contratti completati (acquisizioni) nella dashboard agente.

| Campo | Tipo | Descrizione | Nullable |
|-------|------|-------------|----------|
| idContratto | Integer | ID univoco contratto | No |
| dataInizio | Date | Data inizio validità contratto | No |
| dataFine | Date | Data scadenza contratto | No |
| stato | String | Stato contratto (default: "COMPLETATA") | No |
| commissione | Double | Commissione agente (default: null) | Sì |
| terminiCondizioni | String | Termini contrattuali (default: null) | Sì |
| idCliente | Integer | ID cliente (acquirente) | No |
| nomeCliente | String | Nome cliente | No |
| cognomeCliente | String | Cognome cliente | No |
| emailCliente | String | Email cliente | No |
| telefonoCliente | String | Telefono cliente | Sì |
| idImmobile | Integer | ID immobile venduto | No |
| tipologiaImmobile | String | Tipo immobile | No |
| indirizzoImmobile | String | Indirizzo completo immobile | No |
| cittaImmobile | String | Città immobile | No |
| provinciaImmobile | String | Provincia immobile | No |
| valutazioneFinale | Double | Valore finale transazione (EUR) | Sì |
| superficieTotale | Double | Superficie totale immobile (mq) | Sì |
| nomeProprietario | String | Nome proprietario venditore | Sì |
| cognomeProprietario | String | Cognome proprietario venditore | Sì |

**Aggregazione Dati:**
- **Contratto**: Date inizio/fine, stato (default "COMPLETATA")
- **Cliente**: Informazioni contatto da `Contratto.idUtente (cliente)`
- **Immobile**: Dati principali da `Contratto.idImmobile`
- **Proprietario**: Nome/cognome da `Immobile.idUtente (proprietario)`
- **Valutazione**: Valore finale da `ValutazioneImmobile.valoreMedio`
- **Superficie**: Totale mq da `Superficie.superficieMq`

**Note:**
- I campi `stato`, `commissione` e `terminiCondizioni` sono valorizzati con default perché l'entità `Contratto` non contiene questi campi nel modello database corrente
- La query filtra solo i contratti con `statoContratto = ATTIVO` associati all'agente tramite `Richiesta`

---

## Esempi di Utilizzo

### Scenario 1: Caricamento Dashboard Completa
```bash
# PowerShell
Invoke-RestMethod -Uri "http://localhost:8080/api/agente/dashboard/4" -Method Get

# cURL
curl -X GET http://localhost:8080/api/agente/dashboard/4
```

**Output:** Array di AgenteRichiestaDTO con tutti i contratti gestiti dall'agente ID 4.

---

### Scenario 2: Visualizzazione Statistiche Dashboard
```bash
# PowerShell
Invoke-RestMethod -Uri "http://localhost:8080/api/agente/4/dashboard/stats" -Method Get

# cURL
curl -X GET http://localhost:8080/api/agente/4/dashboard/stats
```

**Output:** 
```json
{
  "richiesteInCarico": 5,
  "richiesteCompletate": 12,
  "richiesteArchiviate": 3
}
```

---

### Scenario 3: Lista Richieste in Attesa
```bash
# PowerShell
Invoke-RestMethod -Uri "http://localhost:8080/api/agente/richieste/attesa" -Method Get

# cURL
curl -X GET http://localhost:8080/api/agente/richieste/attesa
```

**Output:** Array di RichiestaCardDTO con tutte le richieste non ancora assegnate.

---

### Scenario 4: Presa in Carico Richiesta
```bash
# PowerShell
Invoke-RestMethod -Uri "http://localhost:8080/api/agente/4/richieste/5/prendi-in-carico" -Method Post

# cURL
curl -X POST http://localhost:8080/api/agente/4/richieste/5/prendi-in-carico
```

**Output:** 
```
Richiesta presa in carico con successo
```

**Effetti:**
- Crea nuovo contratto con agente ID 4 e immobile della richiesta 5
- Cambia stato richiesta da IN_ATTESA a IN_ELABORAZIONE

---

### Scenario 5: Visualizzazione Lista Immobili
```bash
# PowerShell
Invoke-RestMethod -Uri "http://localhost:8080/api/immobili/dashboard/all" -Method Get

# cURL
curl -X GET http://localhost:8080/api/immobili/dashboard/all
```

---

### Scenario 6: Dettaglio Singolo Immobile
```bash
# PowerShell
Invoke-RestMethod -Uri "http://localhost:8080/api/immobili/dashboard/3" -Method Get

# cURL
curl -X GET http://localhost:8080/api/immobili/dashboard/3
```

---

### Scenario 7: Visualizzazione Acquisizioni Agente
```bash
# PowerShell
Invoke-RestMethod -Uri "http://localhost:8080/api/agente/4/acquisizioni" -Method Get

# cURL
curl -X GET http://localhost:8080/api/agente/4/acquisizioni
```

### Scenario 8: Workflow Cambio Stato Richiesta
```bash
# PowerShell - Transizione da IN_ATTESA a IN_ELABORAZIONE
Invoke-RestMethod -Uri "http://localhost:8080/api/agente/4/richieste/1/stato?nuovoStato=IN_ELABORAZIONE" -Method Put

# PowerShell - Completamento richiesta
Invoke-RestMethod -Uri "http://localhost:8080/api/agente/4/richieste/1/stato?nuovoStato=COMPLETATA" -Method Put

# PowerShell - Annullamento richiesta
Invoke-RestMethod -Uri "http://localhost:8080/api/agente/4/richieste/1/stato?nuovoStato=ANNULLATA" -Method Put
```

---

### Scenario 9: Dettaglio Richiesta Specifica
```bash
# PowerShell
Invoke-RestMethod -Uri "http://localhost:8080/api/agente/richiesta-dettagli/1" -Method Get

# cURL
curl -X GET http://localhost:8080/api/agente/richiesta-dettagli/1
```

**Output:** AgenteRichiestaDTO completo con tutti i dettagli della richiesta associata al contratto ID 1.

---

## Test Effettuati

### Test Case 1: Caricamento Dashboard Completa ✅
- **Endpoint:** GET /api/agente/dashboard/4
- **Risultato:** Array con tutti i contratti gestiti da Sofia Costa
- **Validazione:** Dati aggregati da 7 tabelle correttamente mappati

### Test Case 2: Statistiche Dashboard ✅
- **Endpoint:** GET /api/agente/4/dashboard/stats
- **Risultato:** Contatori corretti (richiesteInCarico: 1, richiesteCompletate: 0, richiesteArchiviate: 0)
- **Validazione:** Calcolo contatori accurato

### Test Case 3: Richieste in Attesa ✅
- **Endpoint:** GET /api/agente/richieste/attesa
- **Risultato:** Lista richieste con stato IN_ATTESA
- **Validazione:** Filtro stato corretto, dati base presenti

### Test Case 13: Recupero Lista Immobili (Legacy) ✅
- **Endpoint:** POST /api/agente/4/richieste/5/prendi-in-carico
- **Risultato:** 200 OK - Contratto creato, stato cambiato a IN_ELABORAZIONE
- **Validazione:** Transazione database completa

### Test Case 5: Recupero Lista Immobili ✅
- **Endpoint:** GET /api/immobili/dashboard/all
- **Risultato:** 4 immobili restituiti con dati completi
- **Validazione:** Tutti i campi mappati correttamente, dati aggregati da 6 tabelle diverse

### Test Case 6: Recupero Dettaglio Immobile ✅
- **Endpoint:** GET /api/immobili/dashboard/3
- **Risultato:** Dettagli villa Novara con tutte le relazioni
- **Validazione:** Proprietario, agente, richiesta, contratto correttamente aggregati

### Test Case 7: Lista Acquisizioni Agente ✅
- **Endpoint:** GET /api/agente/4/acquisizioni
- **Risultato:** 1 contratto completato per Sofia Costa
- **Validazione:** Dati cliente, immobile, proprietario correttamente mappati

### Test Case 8: Cambio Stato Valido (IN_ATTESA → IN_ELABORAZIONE) ✅
- **Endpoint:** PUT /api/agente/4/richieste/1/stato?nuovoStato=IN_ELABORAZIONE
- **Risultato:** 200 OK - "Stato richiesta aggiornato con successo"
- **Validazione:** Stato aggiornato correttamente nel database

### Test Case 9: Cambio Stato Valido (IN_ELABORAZIONE → COMPLETATA) ✅
- **Endpoint:** PUT /api/agente/4/richieste/1/stato?nuovoStato=COMPLETATA
- **Risultato:** 200 OK - "Stato richiesta aggiornato con successo"
- **Validazione:** Transizione consentita eseguita correttamente

### Test Case 10: Cambio Stato Non Valido (IN_ELABORAZIONE → IN_ATTESA) ✅
- **Endpoint:** PUT /api/agente/4/richieste/1/stato?nuovoStato=IN_ATTESA
- **Risultato:** 400 Bad Request - "Transizione non valida"
- **Validazione:** Validazione business logic funzionante

### Test Case 11: Cambio Stato da Terminale (COMPLETATA → IN_ATTESA) ✅
- **Endpoint:** PUT /api/agente/4/richieste/1/stato?nuovoStato=IN_ATTESA
- **Risultato:** 400 Bad Request - "Transizione non valida da COMPLETATA a IN_ATTESA"
- **Validazione:** Stati terminali non modificabili come da specifica

### Test Case 12: Dettaglio Richiesta Specifica ✅
- **Endpoint:** GET /api/agente/richiesta-dettagli/1
- **Risultato:** AgenteRichiestaDTO completo con 70+ campi
- **Validazione:** Tutte le join tabelle funzionanti

---

## Riepilogo Endpoint Dashboard

| Endpoint | Metodo | Scopo | DTO Response |
|----------|--------|-------|--------------|
| `/api/agente/dashboard/{idAgente}` | GET | Dashboard completa agente | AgenteRichiestaDTO[] |
| `/api/agente/{idAgente}/dashboard/stats` | GET | Statistiche dashboard | DashboardStatsDTO |
| `/api/agente/richiesta-dettagli/{idContratto}` | GET | Dettaglio singola richiesta | AgenteRichiestaDTO |
| `/api/agente/richieste/attesa` | GET | Richieste non assegnate | RichiestaCardDTO[] |
| `/api/agente/{idAgente}/richieste/carico` | GET | Richieste assegnate ad agente | RichiestaCardDTO[] |
| `/api/agente/{idAgente}/richieste/{idRichiesta}/prendi-in-carico` | POST | Assegna richiesta ad agente | String |
| `/api/immobili/dashboard/all` | GET | Lista completa immobili | ImmobileListDTO[] |
| `/api/immobili/dashboard/{id}` | GET | Dettaglio singolo immobile | ImmobileListDTO |
| `/api/agente/{idAgente}/acquisizioni` | GET | Contratti completati | AcquisizioneDTO[] |
| `/api/agente/{idAgente}/richieste/{idRichiesta}/stato` | PUT | Cambio stato richiesta | String |

---

## Test Effettuati (Legacy - mantenuti per riferimento)
```bash
# PowerShell - Tentativo transizione non valida (restituisce 400)
try {
  Invoke-RestMethod -Uri "http://localhost:8080/api/agente/4/richieste/1/stato?nuovoStato=IN_ATTESA" -Method Put
} catch {
  Write-Host "Errore: $($_.Exception.Message)"
}
# Output: Errore del server remoto: (400) Richiesta non valida.
```

---

## Test Effettuati

### Test Case 1: Recupero Lista Immobili ✅
- **Endpoint:** GET /api/immobili/dashboard/all
- **Risultato:** 4 immobili restituiti con dati completi
- **Validazione:** Tutti i campi mappati correttamente, dati aggregati da 6 tabelle diverse

### Test Case 14: Recupero Dettaglio Immobile (Legacy) ✅
- **Endpoint:** GET /api/immobili/dashboard/3
- **Risultato:** Dettagli villa Novara con tutte le relazioni
- **Validazione:** Proprietario, agente, richiesta, contratto correttamente aggregati

### Test Case 15: Lista Acquisizioni Agente (Legacy) ✅
- **Endpoint:** GET /api/agente/4/acquisizioni
- **Risultato:** 1 contratto completato per Sofia Costa
- **Validazione:** Dati cliente, immobile, proprietario correttamente mappati

### Test Case 16: Cambio Stato Valido (IN_ATTESA → IN_ELABORAZIONE) (Legacy) ✅
- **Endpoint:** PUT /api/agente/4/richieste/1/stato?nuovoStato=IN_ELABORAZIONE
- **Risultato:** 200 OK - "Stato richiesta aggiornato con successo"
- **Validazione:** Stato aggiornato correttamente nel database

### Test Case 17: Cambio Stato Valido (IN_ELABORAZIONE → COMPLETATA) (Legacy) ✅
- **Endpoint:** PUT /api/agente/4/richieste/1/stato?nuovoStato=COMPLETATA
- **Risultato:** 200 OK - "Stato richiesta aggiornato con successo"
- **Validazione:** Transizione consentita eseguita correttamente

### Test Case 18: Cambio Stato Non Valido (IN_ELABORAZIONE → IN_ATTESA) (Legacy) ✅
- **Endpoint:** PUT /api/agente/4/richieste/1/stato?nuovoStato=IN_ATTESA
- **Risultato:** 400 Bad Request - "Transizione non valida"
- **Validazione:** Validazione business logic funzionante

### Test Case 19: Cambio Stato da Terminale (COMPLETATA → IN_ATTESA) (Legacy) ✅
- **Endpoint:** PUT /api/agente/4/richieste/1/stato?nuovoStato=IN_ATTESA
- **Risultato:** 400 Bad Request - "Transizione non valida da COMPLETATA a IN_ATTESA"
- **Validazione:** Stati terminali non modificabili come da specifica

---

## Note Implementative

### Entity Structure Limitations
L'entità `Contratto` nel database attuale **non contiene** i seguenti campi:
- `stato` (String)
- `commissione` (Double)
- `terminiCondizioni` (String)

Di conseguenza, nell'AcquisizioneDTO questi campi sono valorizzati con valori di default:
- `stato = "COMPLETATA"` (hardcoded)
- `commissione = null`
- `terminiCondizioni = null`

Se in futuro questi campi verranno aggiunti all'entità `Contratto`, sarà necessario aggiornare il metodo `AgenteService.mapContrattoToAcquisizioneDTO()` per mappare i valori reali.

### Repository Method Corrections
Durante l'implementazione sono state identificate e corrette le seguenti incongruenze:

1. **SuperficiRepo**
   - ❌ `findByImmobile_IdImmobile(Integer)` - metodo non esistente
   - ✅ `findById(Integer)` - metodo corretto da utilizzare

2. **ValutazioneImmobileRepo**
   - ❌ `findByImmobile_IdImmobile(Integer)` - metodo non esistente
   - ✅ `findByImmobile(Immobile)` - metodo corretto da utilizzare

3. **ContrattoRepo**
   - ❌ `findByRichiesta_IdRichiesta(Integer)` - metodo non esistente
   - ✅ `findByIdImmobile_IdImmobile(Integer)` - metodo corretto per query contratti

### Type Conversions
Tutte le conversioni da `BigDecimal` (tipo database) a `Double` (tipo DTO) richiedono l'invocazione esplicita di `.doubleValue()`:

```java
// Corretto
dto.setSuperficieTotale(superficie.getSuperficieMq().doubleValue());

// Errato (causa errore compilazione)
dto.setSuperficieTotale(superficie.getSuperficieMq());
```

---

## Deployment Notes

### Container Startup Issue Risolto
Durante il deployment è stato riscontrato un problema di classloading con Spring Boot DevTools in ambiente Docker:
- **Errore:** `ClassNotFoundException: Contratto`
- **Causa:** Classloader di DevTools in conflitto con repository scanning
- **Soluzione:** Full restart di tutti i container (`docker compose down/up`)

Dopo il restart completo:
- ✅ Repository scanning corretto: **13 JPA repository** trovati
- ✅ HikariPool connesso correttamente a MySQL
- ✅ Tomcat avviato su porta 8080
- ✅ Application context inizializzato con successo
- ✅ Tutti gli endpoint rispondono correttamente

**Tempo di startup totale:** ~54 secondi (processo completo in ambiente di sviluppo con DevTools abilitato)

---

## Riferimenti
- [EndpointsList.md](./EndpointsList.md) - Lista completa endpoint esistenti
- [FormulaValutazione.md](./FormulaValutazione.md) - Logica business valutazione immobili
- [AutenticazioneFrontend.md](./AutenticazioneFrontend.md) - Sistema autenticazione sessioni
