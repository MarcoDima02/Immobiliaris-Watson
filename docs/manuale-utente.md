# Manuale Utente - Immobiliaris Watson

**Versione:** 1.0.0  
**Data:** Dicembre 2024  
**Progetto:** Sistema di Valutazione e Gestione Immobiliare

---

## Indice

1. [Introduzione](#introduzione)
2. [Utenti Generici - Richiesta Valutazione](#utenti-generici---richiesta-valutazione)
3. [Agenti Immobiliari - Dashboard](#agenti-immobiliari---dashboard)
4. [Amministratori - Backoffice](#amministratori---backoffice)
5. [FAQ e Risoluzione Problemi](#faq-e-risoluzione-problemi)

---

## Introduzione

**Immobiliaris Watson** è una piattaforma web per la valutazione automatica di immobili e la gestione delle richieste di valutazione.

### Ruoli Utente

- **Utente Generico**: Può richiedere una valutazione del proprio immobile
- **Agente Immobiliare**: Può gestire le richieste di valutazione assegnate
- **Amministratore**: Ha accesso completo al backoffice per la gestione del sistema

### Accesso al Sistema

- **URL Produzione**: `http://localhost:3000` (o dominio assegnato)
- **URL Backend API**: `http://localhost:8080/api`
- **Swagger Documentation**: `http://localhost:8080/swagger-ui/index.html`

---

## Utenti Generici - Richiesta Valutazione

### 1. Accesso alla Pagina di Valutazione

![Screenshot 1: Homepage]()
*Inserire screenshot della homepage con il pulsante "Richiedi Valutazione"*

**Passi:**
1. Aprire il browser e navigare alla homepage
2. Cliccare sul pulsante **"Richiedi Valutazione"** nel menu principale
3. Verrai reindirizzato al form multi-step

---

### 2. Compilazione Form - Step 1: Indirizzo

![Screenshot 4: Form Step 3 - Indirizzo]()
*Inserire screenshot dello step 3 del form*

**Campi Obbligatori:**
- **Indirizzo**: Via e numero civico
- **Città**: Città dell'immobile
- **Provincia**: Sigla provincia (es. MI, RM, NA)
- **CAP**: Codice avviamento postale

**Campi Opzionali:**
- **Latitudine**: Coordinate GPS (se disponibili)
- **Longitudine**: Coordinate GPS (se disponibili)

**Istruzioni:**
1. Inserire l'indirizzo completo dell'immobile
2. Cliccare su **"Avanti"** per procedere

---

### 3. Compilazione Form - Step 2: Dati Proprietà

![Screenshot 3: Form Step 2 - Dati Proprietà]()
*Inserire screenshot dello step 2 del form*

**Campi Obbligatori:**
- **Tipologia Immobile**: Appartamento, Villa, Ufficio, Garage, Terreno
- **Superficie (mq)**: Metratura dell'immobile
- **Numero Stanze**: Quante stanze ha l'immobile
- **Numero Bagni**: Quanti bagni ha l'immobile
- **Piano**: A che piano si trova (0 = piano terra)
- **Numero Piani dell'Immobile**: Quanti piani ha l'edificio
- **Anno di Costruzione**: Anno in cui è stato costruito

**Campi Opzionali:**
- **Balcone/Terrazzo**: Indica se presente
- **Giardino**: Indica se presente
- **Garage**: Indica se presente
- **Ascensore**: Indica se presente
- **Cantina**: Indica se presente
- **Tipo Riscaldamento**: Autonomo, Centralizzato, etc.
- **Condizione Immobile**: Nuovo, Ottimo, Buono, Da Ristrutturare
- **Classe Energetica**: A+, A, B, C, D, E, F, G
- **Esposizione**: Nord, Sud, Est, Ovest

**Istruzioni:**
1. Compilare tutti i campi obbligatori
2. Opzionalmente compilare i campi aggiuntivi per una valutazione più accurata
3. Cliccare su **"Avanti"** per procedere

---


---

### 4. Compilazione Form - Step 3: Informazioni Opzionali

![Screenshot 5: Form Step 4 - Informazioni Opzionali]()
*Inserire screenshot dello step 4 del form*

**Campi:**
- **Superfici Aggiuntive**: Se hai balcone, giardino, garage o cantina, inserisci le metrature
- **Note Aggiuntive**: Qualsiasi informazione aggiuntiva che vuoi comunicare
- **Prezzo Atteso**: Il prezzo che ti aspetti per l'immobile (opzionale)

**Istruzioni:**
1. Compilare i campi se hai informazioni aggiuntive
2. Cliccare su **"Invia Richiesta"** per completare

---

### 5. Compilazione Form - Step 4: Dati Personali

![Screenshot 2: Form Step 1 - Dati Personali]()
*Inserire screenshot dello step 1 del form*

**Campi Obbligatori:**
- **Nome**: Il tuo nome
- **Cognome**: Il tuo cognome
- **Email**: Indirizzo email valido (riceverai la conferma qui)
- **Telefono**: Numero di telefono per essere contattato

**Istruzioni:**
1. Compilare tutti i campi obbligatori
2. Cliccare su **"Avanti"** per procedere allo step successivo

---

### 6. Visualizzazione Risultato Valutazione

![Screenshot 6: Risultato Valutazione]()
*Inserire screenshot della pagina con il risultato della valutazione*

**Informazioni Mostrate:**
- **Valore Base**: Stima base dell'immobile
- **Valore Medio**: Valore medio stimato
- **Range di Valutazione**: Valore minimo e massimo
- **Confidence Score**: Livello di affidabilità della stima
- **Dettagli Immobile**: Riepilogo delle informazioni inserite

**Cosa Succede Dopo:**
1. Riceverai una **email di conferma** all'indirizzo fornito
2. La tua richiesta sarà assegnata a un **agente immobiliare**
3. L'agente ti contatterà per fissare un appuntamento e una valutazione più dettagliata

---

## Agenti Immobiliari - Dashboard

### 1. Login Agente

![Screenshot 7: Pagina Login]()
*Inserire screenshot della pagina di login*

**Credenziali di Accesso:**
- **Email**: La tua email aziendale
- **Password**: Password fornita dall'amministratore

**Istruzioni:**
1. Cliccare su **"Login"** nel menu
2. Inserire email e password
3. Cliccare su **"Accedi"**

---

### 2. Dashboard Principale Agente

![Screenshot 8: Dashboard Agente]()
*Inserire screenshot della dashboard agente con lista richieste*

**Sezioni della Dashboard:**
- **Lista Richieste**: Tutte le richieste assegnate all'agente
- **Filtri**: Filtra per stato (Da Valutare, In Lavorazione, Completata, Annullata)
- **Ricerca**: Cerca per nome, email o ID richiesta

**Informazioni per Ogni Richiesta:**
- ID Richiesta
- Nome e Cognome del richiedente
- Email e Telefono
- Indirizzo immobile
- Data richiesta
- Stato attuale
- Azioni disponibili (Dettagli, Cambia Stato)

---

### 3. Visualizzazione Dettagli Richiesta

![Screenshot 9: Dettagli Richiesta]()
*Inserire screenshot della pagina dettagli richiesta*

**Istruzioni:**
1. Cliccare su **"Dettagli"** nella lista richieste
2. Visualizzare tutte le informazioni dell'immobile e del richiedente

**Informazioni Disponibili:**
- **Dati Richiedente**: Nome, cognome, email, telefono
- **Dati Immobile**: Tipologia, indirizzo, superficie, stanze, bagni, etc.
- **Valutazione Automatica**: Valore base, medio, min, max, confidence
- **Superfici Aggiuntive**: Balcone, giardino, garage, cantina
- **Note del Richiedente**: Eventuali note aggiuntive
- **Stato Richiesta**: Stato attuale del processo
- **Immagini**: Gallery delle immagini caricate (se presenti)

---

### 4. Gestione Stato Richiesta

![Screenshot 10: Cambio Stato]()
*Inserire screenshot del dialog di cambio stato*

**Stati Disponibili:**
- **Da Valutare**: Richiesta appena ricevuta (stato iniziale)
- **In Lavorazione**: Richiesta presa in carico dall'agente
- **Completata**: Valutazione conclusa
- **Annullata**: Richiesta annullata (con motivazione)

**Istruzioni:**
1. Dalla pagina dettagli, cliccare su **"Cambia Stato"**
2. Selezionare il nuovo stato dal menu a tendina
3. Se annullata, inserire il motivo dell'annullamento
4. Cliccare su **"Conferma"** per salvare

**Workflow Tipico:**
```
Da Valutare → In Lavorazione → Completata
```

---

### 5. Upload Immagini Immobile

![Screenshot 11: Upload Immagini]()
*Inserire screenshot della sezione upload immagini*

**Istruzioni:**
1. Dalla pagina dettagli richiesta, scorrere fino alla sezione **"Immagini"**
2. Cliccare su **"Carica Immagini"**
3. Selezionare una o più immagini dal computer (formati: JPG, PNG, max 5MB)
4. Le immagini verranno caricate e mostrate nella gallery
5. È possibile eliminare immagini cliccando sull'icona cestino

**Formati Supportati:**
- JPG/JPEG
- PNG
- Dimensione massima: 5MB per immagine

---

### 6. Logout Agente

![Screenshot 12: Menu Logout]()
*Inserire screenshot del menu utente*

**Istruzioni:**
1. Cliccare sull'icona utente in alto a destra
2. Cliccare su **"Logout"**
3. Verrai reindirizzato alla homepage

---

## Amministratori - Backoffice

### 1. Login Amministratore

![Screenshot 13: Login Admin]()
*Inserire screenshot della pagina login admin*

**Credenziali:**
- **Email**: Email amministratore
- **Password**: Password amministratore

**Istruzioni:**
1. Navigare a `/login`
2. Inserire credenziali amministratore
3. Verrai reindirizzato al backoffice

---

### 2. Dashboard Amministratore - Overview

![Screenshot 14: Dashboard Admin]()
*Inserire screenshot della dashboard admin principale*

**Sezioni Disponibili:**
1. **Utenti**: Gestione utenti (proprietari, agenti, amministratori)
2. **Immobili**: Visualizzazione immobili
3. **Contratti**: Gestione contratti
4. **Richieste**: Gestione richieste di valutazione
5. **Vendite**: Gestione vendite
6. **Immagini**: Visualizzazione immagini caricate

---

### 3. Gestione Utenti

![Screenshot 15: Lista Utenti]()
*Inserire screenshot della lista utenti con filtri*

**Funzionalità:**
- **Visualizzare tutti gli utenti** con filtri per ruolo, nome, email
- **Creare nuovo utente** (proprietario, agente, amministratore)
- **Modificare utente esistente**
- **Filtrare** per nome, cognome, email, ruolo, telefono

**Istruzioni - Visualizzazione:**
1. Cliccare su **"Utenti"** nel menu laterale
2. Utilizzare i filtri per cercare utenti specifici
3. La tabella mostra: ID, Nome, Cognome, Email, Ruolo, Telefono

---

### 4. Creazione Nuovo Utente

![Screenshot 16: Form Creazione Utente]()
*Inserire screenshot del form di creazione utente*

**Campi Obbligatori:**
- **Nome**: Nome dell'utente
- **Cognome**: Cognome dell'utente
- **Email**: Email univoca (non duplicabile)
- **Telefono**: Numero di telefono
- **Ruolo**: PROPRIETARIO, AGENTE, AMMINISTRATORE
- **Password**: Solo per AGENTE e AMMINISTRATORE (minimo 8 caratteri)

**Istruzioni:**
1. Cliccare su **"Crea Nuovo Utente"**
2. Compilare tutti i campi obbligatori
3. Se ruolo = AGENTE o AMMINISTRATORE, inserire la password
4. Cliccare su **"Salva"**

---

### 5. Modifica Utente

![Screenshot 17: Form Modifica Utente]()
*Inserire screenshot del form di modifica utente*

**Campi Modificabili:**
- Nome, Cognome, Email, Telefono
- Password (opzionale - se non inserita, rimane quella esistente)

**Istruzioni:**
1. Dalla lista utenti, cliccare su **"Modifica"** accanto all'utente
2. Modificare i campi desiderati
3. La password può essere lasciata vuota per mantenerla invariata
4. Cliccare su **"Salva Modifiche"**

---

### 6. Gestione Immobili

![Screenshot 18: Lista Immobili]()
*Inserire screenshot della lista immobili con filtri*

**Filtri Disponibili:**
- Tipologia (Appartamento, Villa, Ufficio, Garage, Terreno)
- Città
- Provincia
- Stato (Disponibile, In Trattativa, Venduto, Ritirato)
- Range di prezzo

**Informazioni Mostrate:**
- ID, Tipologia, Indirizzo, Città, Provincia
- Superficie, Stanze, Bagni
- Prezzo, Stato
- Data inserimento

**Istruzioni:**
1. Cliccare su **"Immobili"** nel menu
2. Utilizzare i filtri per trovare immobili specifici
3. Cliccare su un immobile per vedere i dettagli completi

---

### 7. Gestione Contratti

![Screenshot 19: Lista Contratti]()
*Inserire screenshot della lista contratti*

**Funzionalità:**
- **Visualizzare tutti i contratti**
- **Creare nuovo contratto**
- **Modificare contratto esistente**
- **Download PDF contratto**

**Informazioni Mostrate:**
- ID Contratto
- Tipo (Vendita, Affitto, Incarico)
- Nome e Cognome Agente
- Immobile associato
- Data Contratto
- Data Scadenza
- Link al PDF

---

### 8. Creazione Nuovo Contratto

![Screenshot 20: Form Creazione Contratto]()
*Inserire screenshot del form contratto*

**Campi Obbligatori:**
- **Tipo Contratto**: Vendita, Affitto, Incarico
- **Agente**: Seleziona dall'elenco agenti
- **Immobile**: Seleziona dall'elenco immobili
- **Data Contratto**: Data di stipula
- **Data Scadenza**: Data di scadenza
- **Upload PDF**: File PDF del contratto firmato

**Istruzioni:**
1. Cliccare su **"Crea Nuovo Contratto"**
2. Compilare tutti i campi
3. Caricare il PDF del contratto
4. Cliccare su **"Salva"**

---

### 9. Modifica Contratto

![Screenshot 21: Form Modifica Contratto]()
*Inserire screenshot del form modifica contratto*

**Campi Modificabili:**
- Tipo Contratto
- Data Scadenza
- PDF (upload nuovo file)

**Istruzioni:**
1. Dalla lista contratti, cliccare su **"Modifica"**
2. Modificare i campi desiderati
3. Opzionalmente caricare un nuovo PDF
4. Cliccare su **"Salva Modifiche"**

---

### 10. Gestione Richieste

![Screenshot 22: Lista Richieste Admin]()
*Inserire screenshot della lista richieste vista admin*

**Funzionalità:**
- **Visualizzare tutte le richieste** del sistema
- **Filtrare per stato** (Da Valutare, In Lavorazione, Completata, Annullata)
- **Vedere dettagli completi** richiesta + immobile
- **Modificare stato** richiesta

**Informazioni Mostrate:**
- ID Richiesta
- Utente richiedente
- Indirizzo immobile
- Data richiesta
- Data appuntamento
- Stato
- Note

---

### 11. Dettagli Richiesta Completi

![Screenshot 23: Dettagli Richiesta Admin]()
*Inserire screenshot dei dettagli completi richiesta*

**Informazioni Disponibili:**
- **Dati Richiesta**: ID, data, stato, appuntamento
- **Dati Utente**: Nome, cognome, email, telefono
- **Dati Immobile Completi**: Tutti i campi dell'immobile
- **Dettagli Immobile**: Stanze, bagni, piano, riscaldamento, etc.
- **Superfici**: Superficie totale, balcone, giardino, garage, cantina
- **Valutazione**: Valore base, medio, min, max, confidence, fattore aggiustamento
- **Contratto**: Se presente, dati del contratto associato

**Istruzioni:**
1. Dalla lista richieste, cliccare su **"Dettagli Completi"**
2. Visualizzare tutte le informazioni aggregate

---

### 12. Gestione Vendite

![Screenshot 24: Lista Vendite]()
*Inserire screenshot della lista vendite*

**Funzionalità:**
- **Visualizzare tutte le vendite**
- **Creare nuova vendita**
- **Modificare vendita esistente**

**Informazioni Mostrate:**
- ID Vendita
- Immobile venduto
- Agente responsabile
- Contratto associato
- Prezzo finale
- Data vendita
- Commissioni

---

### 13. Creazione Nuova Vendita

![Screenshot 25: Form Creazione Vendita]()
*Inserire screenshot del form vendita*

**Campi Obbligatori:**
- **Immobile**: Seleziona immobile venduto
- **Agente**: Agente che ha concluso la vendita
- **Contratto**: Contratto di vendita associato
- **Prezzo Finale**: Prezzo di vendita effettivo
- **Data Vendita**: Data di conclusione
- **Commissioni**: Percentuale o importo commissioni

**Istruzioni:**
1. Cliccare su **"Crea Nuova Vendita"**
2. Compilare tutti i campi
3. Cliccare su **"Salva"**

---

### 14. Visualizzazione Immagini

![Screenshot 26: Gallery Immagini]()
*Inserire screenshot della gallery immagini*

**Funzionalità:**
- Visualizzare tutte le immagini caricate nel sistema
- Filtrare per immobile
- Vedere dettagli immagine (dimensione, data upload, formato)

**Istruzioni:**
1. Cliccare su **"Immagini"** nel menu
2. Utilizzare i filtri per trovare immagini specifiche
3. Cliccare su un'immagine per vederla a dimensione intera

---

### 15. Logout Amministratore

![Screenshot 27: Logout Admin]()
*Inserire screenshot del logout*

**Istruzioni:**
1. Cliccare sull'icona utente in alto a destra
2. Cliccare su **"Logout"**
3. Verrai reindirizzato alla homepage

---

## FAQ e Risoluzione Problemi

### Utenti Generici

**Q: Non ricevo l'email di conferma dopo la richiesta di valutazione**
- Controlla la cartella spam/posta indesiderata
- Verifica di aver inserito l'email corretta
- Contatta l'assistenza se il problema persiste

**Q: Ho inserito dati sbagliati nel form, posso modificarli?**
- No, una volta inviata la richiesta non è modificabile
- Contatta l'agente assegnato via email o telefono per comunicare le correzioni

**Q: Quanto tempo ci vuole per essere contattato da un agente?**
- Normalmente entro 24-48 ore lavorative
- Riceverai un'email quando l'agente prende in carico la richiesta

---

### Agenti Immobiliari

**Q: Non vedo alcune richieste nella dashboard**
- Verifica di non avere filtri attivi che nascondono le richieste
- Contatta l'amministratore se le richieste non ti sono state assegnate

**Q: Non riesco a caricare immagini**
- Verifica che le immagini siano in formato JPG o PNG
- Verifica che ogni immagine sia massimo 5MB
- Controlla la connessione internet

**Q: Come faccio a stampare o esportare i dettagli di una richiesta?**
- Usa la funzione "Stampa" del browser (Ctrl+P o Cmd+P)
- Oppure richiedi all'amministratore di implementare export PDF

---

### Amministratori

**Q: Ho creato un agente ma non riesce a fare login**
- Verifica che la password inserita rispetti i requisiti (minimo 8 caratteri)
- Verifica che l'email sia corretta
- Prova a modificare l'utente e reimpostare la password

**Q: Posso eliminare utenti, immobili o contratti?**
- No, attualmente la funzione DELETE non è implementata
- Contatta il team di sviluppo se è necessaria questa funzionalità

**Q: Come esporto un report delle vendite?**
- Attualmente non c'è funzione di export automatico
- Puoi usare la documentazione API Swagger per creare script personalizzati

---

## Supporto Tecnico

Per problemi tecnici o richieste di assistenza:

- **Email**: supporto@immobiliaris-watson.it (esempio)
- **Telefono**: +39 XXX XXXXXXX
- **Documentazione API**: http://localhost:8080/swagger-ui/index.html
- **Repository GitHub**: https://github.com/MarcoDima02/Immobiliaris-Watson

---

**Fine del Manuale Utente**

*Ultimo aggiornamento: Dicembre 2025*
