-- ============================================
-- Script di inizializzazione database MySQL
-- Eseguito automaticamente dal container MySQL
-- ============================================

-- Il database e l'utente sono già creati dalle variabili ambiente
-- USE residea_db; -- già selezionato di default

-- ========================
-- TABELLA: Utente
-- ========================
CREATE TABLE IF NOT EXISTS Utente (
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
CREATE TABLE IF NOT EXISTS Immobile (
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
CREATE TABLE IF NOT EXISTS DettagliImmobile (
    idImmobile INT PRIMARY KEY,
    nStanze INT(2) NOT NULL,
    nBagni INT(2) NOT NULL,
    nPiano INT(2) NOT NULL,
    nPianiImmobile INT(2) NULL,
    balconeTerrazzo BOOLEAN NOT NULL DEFAULT FALSE,
    giardino BOOLEAN NOT NULL DEFAULT FALSE,
    garage BOOLEAN NOT NULL DEFAULT FALSE,
    ascensore BOOLEAN NOT NULL DEFAULT FALSE,
    cantina BOOLEAN NOT NULL DEFAULT FALSE,
    tipoRiscaldamento ENUM('No','Autonomo','Condominiale','Pompe di calore','Pavimento') DEFAULT 'No' NOT NULL,
    annoCostruzione YEAR NOT NULL,
    condizioneImmobile ENUM('Nuovo', 'Ristrutturato','Parzialmente ristrutturato','Non ristrutturato') NOT NULL,
    classeEnergetica ENUM('A+', 'A', 'B', 'C', 'D', 'E', 'F', 'G') DEFAULT 'G' NULL,
    prezzo DECIMAL(10, 2),
    FOREIGN KEY (idImmobile) REFERENCES Immobile(idImmobile) ON DELETE CASCADE
);

-- ========================
-- TABELLA: Citta
-- ========================
CREATE TABLE IF NOT EXISTS Citta (
    idCitta INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    provincia VARCHAR(3) NOT NULL,
    regione VARCHAR(20) DEFAULT 'Piemonte',
    codiceIstat VARCHAR(10)
);

-- ========================
-- TABELLA: PrezzoPerCAP
-- ========================
CREATE TABLE IF NOT EXISTS PrezzoPerCAP (
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
-- TABELLA: Immagine
-- ========================
CREATE TABLE IF NOT EXISTS Immagine (
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
-- TABELLA: Richiesta
-- ========================
CREATE TABLE IF NOT EXISTS Richiesta (
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
CREATE TABLE IF NOT EXISTS Superfici (
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
CREATE TABLE IF NOT EXISTS ValutazioneImmobile (
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
CREATE TABLE IF NOT EXISTS Contratti (
    idContratto INT AUTO_INCREMENT PRIMARY KEY,
    idImmobile INT NOT NULL,
    tipoContratto ENUM ('Esclusivo', 'altro'),
    dataContratto DATE,
    dataScadenzaContratto DATE,
    pathContrattoPDF VARCHAR(255),
    FOREIGN KEY (idImmobile) REFERENCES Immobile(idImmobile) ON DELETE CASCADE
);

-- ========================
-- TABELLA: Leads
-- ========================
CREATE TABLE IF NOT EXISTS Leads (
    idLead INT AUTO_INCREMENT PRIMARY KEY,
    idUtente INT,
    nome VARCHAR(100),
    email VARCHAR(150),
    telefono VARCHAR(20),
    citta VARCHAR(100),
    fonte VARCHAR(100),
    convertitoInRichiesta BOOLEAN DEFAULT FALSE,
    idRichiesta INT,
    assegnatoA INT,
    note TEXT,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (idUtente) REFERENCES Utente(idUtente) ON DELETE SET NULL,
    FOREIGN KEY (idRichiesta) REFERENCES Richiesta(idRichiesta) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (assegnatoA) REFERENCES Utente(idUtente) ON DELETE SET NULL ON UPDATE CASCADE
);

-- ========================
-- TABELLA: Vendite
-- ========================
CREATE TABLE IF NOT EXISTS Vendite (
    idVendita INT AUTO_INCREMENT PRIMARY KEY,
    idContratto INT NOT NULL,
    idImmobile INT NOT NULL,
    idUtente INT NOT NULL,
    commissionePercentuale DECIMAL(5, 2),
    FOREIGN KEY (idContratto) REFERENCES Contratti(idContratto) ON DELETE CASCADE,
    FOREIGN KEY (idImmobile) REFERENCES Immobile(idImmobile) ON DELETE CASCADE,
    FOREIGN KEY (idUtente) REFERENCES Utente(idUtente) ON DELETE CASCADE
);

-- ============================================
-- Dati di esempio per testing
-- ============================================

-- Inserimento città Torino
INSERT IGNORE INTO Citta (idCitta, nome, provincia, regione, codiceIstat) 
VALUES (1, 'Torino', 'TO', 'Piemonte', '001272');

-- Inserimento prezzi per CAP di Torino
INSERT IGNORE INTO PrezzoPerCAP (cap, idCitta, prezzoMq, fonte, validFrom, qualityScore) 
VALUES 
    ('10121', 1, 3200.00, 'Immobiliare.it 2024', '2024-01-01', 0.85),
    ('10122', 1, 2900.00, 'Immobiliare.it 2024', '2024-01-01', 0.82),
    ('10123', 1, 2800.00, 'Immobiliare.it 2024', '2024-01-01', 0.80),
    ('10124', 1, 2700.00, 'Immobiliare.it 2024', '2024-01-01', 0.78),
    ('10125', 1, 2650.00, 'Immobiliare.it 2024', '2024-01-01', 0.75);

-- Utente di test
INSERT IGNORE INTO Utente (idUtente, nome, cognome, telefono, email, ruolo, verifica_email, consenso_privacy)
VALUES (1, 'Mario', 'Rossi', '3331234567', 'mario.rossi@example.com', 'proprietario', TRUE, TRUE);

-- Log di completamento
SELECT 'Database inizializzato con successo!' AS Status;
