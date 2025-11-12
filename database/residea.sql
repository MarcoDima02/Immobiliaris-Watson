-- Passaggi creazione database e utente autorizzato
-- CREATE DATABASE residea_db;
-- Use residea_db;
-- CREATE USER 'residea_user'@'localhost' IDENTIFIED BY 'ResideaP@ss';
-- GRANT ALL PRIVILEGES ON residea_db.* TO 'residea_user'@'localhost';
-- FLUSH PRIVILEGES;

-- ============================================
-- Comandi reset tabelle in caso sia necessario
-- SET FOREIGN_KEY_CHECKS = 0;
-- TRUNCATE TABLE Superfici;
-- TRUNCATE TABLE DettagliImmobile;
-- TRUNCATE TABLE ValutazioneImmobile;
-- TRUNCATE TABLE Immobile;
-- SET FOREIGN_KEY_CHECKS = 1;
-- ============================================


-- ========================
-- TABELLA: Utente
-- ========================
CREATE TABLE Utente (
    idUtente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(20) NOT NULL,
    cognome VARCHAR(20) NOT NULL,
    telefono VARCHAR(10) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    passwordHash VARCHAR(255) NULL,
    ruolo ENUM('proprietario', 'agente', 'amministratore') DEFAULT 'proprietario',
    verifica_email BOOLEAN DEFAULT FALSE,
    consenso_privacy BOOLEAN DEFAULT FALSE
);

-- ========================
-- TABELLA: Immobile 
-- ========================
CREATE TABLE Immobile (
    idImmobile INT AUTO_INCREMENT PRIMARY KEY,
    idProprietario INT NULL,
    tipologia ENUM('Appartamento', 'Villa', 'Casa indipendente', 'Monolocale'),
    indirizzo VARCHAR(200) NOT NULL,
    citta VARCHAR(100) NOT NULL,
    provincia VARCHAR(3) NOT NULL,
    cap VARCHAR(5) NOT NULL,
    latitudine DECIMAL(10, 8),
    longitudine DECIMAL(11, 8),
    stato ENUM('Disponibile', 'Venduto'),
    FOREIGN KEY (idProprietario) REFERENCES Utente(idUtente) ON DELETE CASCADE
);

-- ========================
-- TABELLA: DettagliImmobile
-- ========================
CREATE TABLE DettagliImmobile (
    idImmobile INT PRIMARY KEY,
    nStanze INT(2) NOT NULL,
    nBagni INT(2) NOT NULL,
    nPiano INT(2) NOT NULL,
    nPianiImmobile INT(2) NOT NULL,
    balconeTerrazzo BOOLEAN NOT NULL DEFAULT FALSE,
    giardino BOOLEAN NOT NULL DEFAULT FALSE,
    garage BOOLEAN NOT NULL DEFAULT FALSE,
    ascensore BOOLEAN NOT NULL DEFAULT FALSE,
    cantina BOOLEAN NOT NULL DEFAULT FALSE,
    tipoRiscaldamento ENUM('No','Autonomo','Condominiale','Pompe di calore','Pavimento') DEFAULT 'No' NOT NULL,
    annoCostruzione YEAR NOT NULL,
    condizioneImmobile ENUM('Nuovo', 'Ristrutturato','Parzialmente ristrutturato','Non ristrutturato') NOT NULL,
    classeEnergetica ENUM('A+', 'A', 'B', 'C', 'D', 'E', 'F', 'G') NOT NULL,
    prezzo DECIMAL(10, 2),
    FOREIGN KEY (idImmobile) REFERENCES Immobile(idImmobile) ON DELETE CASCADE
);


-- ========================
-- TABELLA: Citta - CRUD Completa
-- ========================
CREATE TABLE Citta (
    idCitta INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    provincia VARCHAR(3) NOT NULL,
    regione VARCHAR(20) DEFAULT 'Piemonte',
    codiceIstat VARCHAR(10)
);

-- ========================
-- TABELLA: PrezzoPerCAP - CRUD Completa
-- ========================
CREATE TABLE PrezzoPerCAP (
    cap VARCHAR(10) PRIMARY KEY,
    idCitta INT DEFAULT NULL,
    prezzoMq DECIMAL(10, 2) NOT NULL,
    fonte VARCHAR(150) DEFAULT NULL,
    validFrom DATE DEFAULT NULL,
    validTo DATE DEFAULT NULL,
    qualityScore DECIMAL(3,2) DEFAULT 0.0,
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (idCitta) REFERENCES Citta(idCitta) ON DELETE SET NULL
);

-- ========================
-- TABELLA: Immagine - CRUD Completa
-- ========================
CREATE TABLE Immagine (
    idImmagine INT AUTO_INCREMENT PRIMARY KEY,
    idImmobile INT NOT NULL,
    url VARCHAR(255),
    nomeFile VARCHAR(150),
    descrizione TEXT,
    copertina BOOLEAN DEFAULT FALSE,
    ordinamento INT DEFAULT 0,
    dimensioneKb INT,
    FOREIGN KEY (idImmobile) REFERENCES Immobile(idImmobile) ON DELETE CASCADE
);

-- ========================
-- TABELLA: Prenotazione
-- ========================
CREATE TABLE Richiesta (
    idRichiesta INT AUTO_INCREMENT PRIMARY KEY,
    idUtente INT NOT NULL,
    idImmobile INT NOT NULL,
    dataRichiesta DATETIME NOT NULL,
    dataAppuntamento DATETIME,
    stato ENUM('In attesa', 'In elaborazione', 'Completata', 'Annullata') DEFAULT 'In attesa',
    noteUtente TEXT,
    motivoAnnullamento TEXT,
    FOREIGN KEY (idUtente) REFERENCES Utente(idUtente) ON DELETE CASCADE,
    FOREIGN KEY (idImmobile) REFERENCES Immobile(idImmobile) ON DELETE CASCADE
);

-- ========================
-- TABELLA: Superfici
-- ========================
CREATE TABLE Superfici (
    idImmobile INT PRIMARY KEY,
    superficieMq INT(4),
    superficieBalconeTerrazzo INT(4),
    superficieGiardino INT(4),
    superficieGarage INT(4),
    superficieCantina INT(4),
    FOREIGN KEY (idImmobile) REFERENCES Immobile(idImmobile) ON DELETE CASCADE
);

-- ========================
-- TABELLA: ValutazioneImmobile
-- ========================
CREATE TABLE ValutazioneImmobile (
    idValutazione INT AUTO_INCREMENT PRIMARY KEY,
    idImmobile INT NOT NULL,
    valoreBase INT(9),
    fattoreAggiustamento DECIMAL(5, 2),
    valoreMedio INT(9),
    valoreMin INT(9),
    valoreMax INT(9),
    confidence DECIMAL(3, 2),
    FOREIGN KEY (idImmobile) REFERENCES Immobile(idImmobile) ON DELETE CASCADE
);

-- ========================
-- TABELLA: Contratti
-- ========================
CREATE TABLE Contratti (
    idContratto INT AUTO_INCREMENT PRIMARY KEY,
    idImmobile INT NOT NULL,
    tipoContratto ENUM ('Esclusivo', 'altro'),
    dataContratto DATE,
    dataScadenzaContratto DATE,
    pathContrattoPDF VARCHAR(255),
    FOREIGN KEY (idImmobile) REFERENCES Immobile(idImmobile) ON DELETE CASCADE
);

-- ========================
-- TABELLA: Leads - contatti generati, da revisionare
-- ========================
CREATE TABLE Leads (
    idLead INT AUTO_INCREMENT PRIMARY KEY,
    idUtente INT,
    nome VARCHAR(100),
    email VARCHAR(150),
    telefono VARCHAR(20),
    citta VARCHAR(100),
    fonte VARCHAR(100),
    campagna VARCHAR(100),
    utm_source VARCHAR(100),
    utm_medium VARCHAR(100),
    utm_campaign VARCHAR(100),
    convertitoInRichiesta BOOLEAN DEFAULT FALSE,
    idRichiesta INT,
    assegnatoA INT,
    note TEXT,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (idUtente) REFERENCES Utente(idUtente) ON DELETE SET NULL,
    FOREIGN KEY (idRichiesta) REFERENCES Richiesta(idRichiesta)
    ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (assegnatoA) REFERENCES Utente(idUtente)
    ON DELETE SET NULL ON UPDATE CASCADE
);


-- ========================
-- TABELLA: Vendite
-- ========================
CREATE TABLE Vendite (
    idVendita INT AUTO_INCREMENT PRIMARY KEY,
    idContratto INT NOT NULL,
    idImmobile INT NOT NULL,
    idUtente INT NOT NULL,
    commissionePercentuale DECIMAL(5, 2),
    FOREIGN KEY (idContratto) REFERENCES Contratti(idContratto) ON DELETE CASCADE,
    FOREIGN KEY (idImmobile) REFERENCES Immobile(idImmobile) ON DELETE CASCADE,
    FOREIGN KEY (idUtente) REFERENCES Utente(idUtente) ON DELETE CASCADE
);

