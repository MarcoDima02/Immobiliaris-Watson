# 🐳 Immobiliaris Watson - Ambiente Docker

Setup Docker completo per testing e sviluppo dell'applicazione Immobiliaris Watson (Residea).

## 📋 Prerequisiti

- **Docker Desktop** (Windows/Mac) o **Docker Engine** (Linux)
- **Docker Compose** v2.0+
- Almeno **4GB RAM** disponibili per Docker
- Porte libere: **80** (frontend), **8080** (backend), **3306** (MySQL)

Verifica installazione:
```powershell
docker --version
docker compose version
```

## 🚀 Quick Start

### 1. Configurazione iniziale

Copia il file `.env.example` in `.env`:

```powershell
Copy-Item .env.example .env
```

Modifica `.env` se necessario (porte, password, ecc.).

### 2. Avvio completo

Avvia tutti i servizi (MySQL + Backend + Frontend):

```powershell
docker compose up -d
```

**Primo avvio:** ci vogliono 5-10 minuti per:
- Download immagini base (MySQL 8, Node 20, Maven 3, JRE 21)
- Build backend (dipendenze Maven + compilazione Java)
- Build frontend (dipendenze npm + build Vite)

### 3. Verifica stato

```powershell
docker compose ps
```

Output atteso:
```
NAME                 STATUS         PORTS
residea-mysql       Up (healthy)    0.0.0.0:3306->3306/tcp
residea-backend     Up (healthy)    0.0.0.0:8080->8080/tcp
residea-frontend    Up (healthy)    0.0.0.0:80->80/tcp
```

### 4. Accesso applicazione

- **Frontend:** http://localhost
- **Backend API:** http://localhost:8080/api
- **Health check backend:** http://localhost:8080/actuator/health
- **MySQL:** `localhost:3306` (user: `residea_user`, pass: `ResideaP@ss`)

## 📂 Struttura Docker

```
.
├── docker-compose.yml           # Orchestrazione servizi
├── .env.example                 # Template variabili ambiente
├── back-end/residea/
│   ├── Dockerfile               # Build Spring Boot (multi-stage)
│   └── .dockerignore
├── frontend/
│   ├── Dockerfile               # Build React + Nginx (multi-stage)
│   ├── nginx.conf               # Configurazione Nginx per SPA
│   └── .dockerignore
└── database/
    └── init/
        └── 01-init-schema.sql   # Inizializzazione automatica DB
```

## 🔧 Comandi Utili

### Gestione servizi

```powershell
# Avvia tutti i servizi
docker compose up -d

# Ferma tutti i servizi (mantiene volumi)
docker compose down

# Ferma e rimuovi volumi (ATTENZIONE: perde dati DB!)
docker compose down -v

# Riavvia singolo servizio
docker compose restart backend

# Ferma singolo servizio
docker compose stop frontend
```

### Logs e debugging

```powershell
# Visualizza log di tutti i servizi
docker compose logs -f

# Log di un singolo servizio
docker compose logs -f backend

# Ultimi 100 righe di log
docker compose logs --tail=100 mysql

# Log senza follow (stampa ed esce)
docker compose logs backend
```

### Rebuild dopo modifiche codice

```powershell
# Rebuild solo backend (dopo modifiche Java)
docker compose build backend
docker compose up -d backend

# Rebuild solo frontend (dopo modifiche React)
docker compose build frontend
docker compose up -d frontend

# Rebuild completo tutto
docker compose build
docker compose up -d
```

### Accesso shell nei container

```powershell
# Shell backend (Alpine Linux)
docker compose exec backend sh

# Shell MySQL
docker compose exec mysql bash

# MySQL client
docker compose exec mysql mysql -u residea_user -p
# Password: ResideaP@ss
```

### Pulizia spazio disco

```powershell
# Rimuovi immagini non utilizzate
docker image prune -a

# Rimuovi tutto (container, network, volumi, cache)
docker system prune -a --volumes
```

## 🗄️ Database

### Accesso diretto MySQL

```powershell
# Da host Windows (se hai MySQL client installato)
mysql -h 127.0.0.1 -P 3306 -u residea_user -p
# Password: ResideaP@ss

# Da container Docker
docker compose exec mysql mysql -u residea_user -p residea_db
```

### Backup database

```powershell
# Backup completo
docker compose exec mysql mysqldump -u residea_user -pResideaP@ss residea_db > backup.sql

# Restore da backup
Get-Content backup.sql | docker compose exec -T mysql mysql -u residea_user -pResideaP@ss residea_db
```

### Reset database

```powershell
# Ferma servizi, rimuovi volume, riavvia
docker compose down
docker volume rm immobiliaris-watson_mysql_data
docker compose up -d
```

## 🐛 Troubleshooting

### Backend non si avvia

```powershell
# Verifica log
docker compose logs backend

# Verifica connessione MySQL
docker compose exec backend sh
wget -O- http://localhost:8080/actuator/health
```

**Errori comuni:**
- `Connection refused` → MySQL non è pronto, aspetta 30s
- `Access denied` → Verifica credenziali in `.env`
- `Port 8080 already in use` → Cambia `BACKEND_PORT` in `.env`

### Frontend non carica

```powershell
# Verifica log Nginx
docker compose logs frontend

# Verifica health
curl http://localhost/health
```

**Errori comuni:**
- `502 Bad Gateway` → Backend non risponde, verifica backend prima
- `404 su route` → Nginx non configurato per SPA, verifica `nginx.conf`

### MySQL non si avvia

```powershell
# Verifica log
docker compose logs mysql

# Verifica volume
docker volume inspect immobiliaris-watson_mysql_data
```

**Errori comuni:**
- `port 3306 already in use` → Ferma MySQL locale o cambia porta in `.env`
- Volume corrotto → Rimuovi volume e ricrea: `docker volume rm immobiliaris-watson_mysql_data`

### Health check fallisce

```powershell
# Verifica manualmente health check
docker compose exec backend wget --spider http://localhost:8080/actuator/health
docker compose exec frontend wget --spider http://localhost/health
docker compose exec mysql mysqladmin ping -h localhost -u root -prootpassword
```

### Ricostruzione completa (clean slate)

```powershell
# Ferma tutto
docker compose down -v

# Rimuovi immagini costruite
docker rmi residea-backend residea-frontend

# Pulisci cache build
docker builder prune -a

# Ricostruisci e avvia
docker compose build --no-cache
docker compose up -d
```

## 🔐 Sicurezza

**IMPORTANTE per produzione:**

1. Cambia tutte le password in `.env`:
   ```
   MYSQL_ROOT_PASSWORD=<password-sicura>
   DB_PASS=<password-sicura>
   ```

2. Non committare `.env` in Git (già in `.gitignore`)

3. Usa Docker secrets invece di variabili ambiente

4. Limita esposizione porte (rimuovi `ports:` da `docker-compose.yml`)

## 📊 Monitoraggio risorse

```powershell
# Utilizzo risorse in tempo reale
docker stats

# Spazio occupato
docker system df
```

## 🌐 Configurazione porte personalizzate

Modifica `.env`:

```env
MYSQL_PORT=3307        # se hai già MySQL su 3306
BACKEND_PORT=9090      # se hai altro su 8080
FRONTEND_PORT=8000     # se hai altro su 80
```

Poi riavvia:
```powershell
docker compose down
docker compose up -d
```

## 🔄 Workflow sviluppo

### Modifiche backend (hot reload con devtools)

1. Modifica codice Java locale
2. Spring Boot DevTools rileva modifiche automaticamente
3. Backend si riavvia automaticamente nel container

**Oppure rebuild manuale:**
```powershell
docker compose build backend
docker compose up -d backend
```

### Modifiche frontend

1. Modifica codice React locale
2. Rebuild immagine:
   ```powershell
   docker compose build frontend
   docker compose up -d frontend
   ```

### Modifiche database schema

1. Modifica `database/init/01-init-schema.sql`
2. Reset database:
   ```powershell
   docker compose down
   docker volume rm immobiliaris-watson_mysql_data
   docker compose up -d
   ```

## 📝 Note

- **Volumi persistenti:** I dati MySQL sono salvati nel volume `mysql_data` e sopravvivono ai restart
- **Network isolata:** Tutti i servizi comunicano tramite rete Docker `residea-network`
- **Multi-stage build:** Dockerfile ottimizzati per immagini leggere (solo runtime, no build tools)
- **Health checks:** Docker monitora automaticamente la salute dei servizi

## 🆘 Supporto

Per problemi non risolti:

1. Controlla log: `docker compose logs -f`
2. Verifica health: `docker compose ps`
3. Rebuild pulito: vedi sezione "Ricostruzione completa"
4. Controlla issue su GitHub repository

---

**Buon testing! 🚀**
