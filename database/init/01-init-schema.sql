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
VALUES  (1, 'Torino', 'TO', 'Piemonte', '001272'),
  (2, 'Alessandria', 'AL', 'Piemonte', '006001'),
  (3, 'Asti', 'AT', 'Piemonte', '005002'),
  (4, 'Biella', 'BI', 'Piemonte', '096004'),
  (5, 'Cuneo', 'CN', 'Piemonte', '004006'),
  (6, 'Novara', 'NO', 'Piemonte', '003007'),
  (7, 'Verbania', 'VB', 'Piemonte', '103008'),
  (8, 'Vercelli', 'VC', 'Piemonte', '002009')

-- Inserimento prezzi per CAP di Torino
INSERT IGNORE INTO PrezzoPerCAP (cap, idCitta, prezzoMq, fonte, validFrom, qualityScore) 
VALUES 
    -- Torino (idCitta = 1)
  ('10121', 1, 3300.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.92, CURRENT_TIMESTAMP),
  ('10122', 1, 3200.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.90, CURRENT_TIMESTAMP),
  ('10123', 1, 3100.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.89, CURRENT_TIMESTAMP),
  ('10124', 1, 3000.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.88, CURRENT_TIMESTAMP),
  ('10125', 1, 2900.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.86, CURRENT_TIMESTAMP),
  ('10126', 1, 2800.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.85, CURRENT_TIMESTAMP),
  ('10128', 1, 2700.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.82, CURRENT_TIMESTAMP),
  ('10129', 1, 2600.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.80, CURRENT_TIMESTAMP),
  ('10131', 1, 2400.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.78, CURRENT_TIMESTAMP),
  ('10135', 1, 2200.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.75, CURRENT_TIMESTAMP),
  ('10138', 1, 2100.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.72, CURRENT_TIMESTAMP),
  ('10139', 1, 2000.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.70, CURRENT_TIMESTAMP),
  ('10141', 1, 1900.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.68, CURRENT_TIMESTAMP),
  ('10142', 1, 1900.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.68, CURRENT_TIMESTAMP),
  ('10143', 1, 1800.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.66, CURRENT_TIMESTAMP),
  ('10144', 1, 1750.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.65, CURRENT_TIMESTAMP),
  ('10145', 1, 1700.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.64, CURRENT_TIMESTAMP),
  ('10149', 1, 1650.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.62, CURRENT_TIMESTAMP),
  ('10152', 1, 1600.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.60, CURRENT_TIMESTAMP),
  ('10153', 1, 1550.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.58, CURRENT_TIMESTAMP),
  ('10154', 1, 1500.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.57, CURRENT_TIMESTAMP),
  ('10155', 1, 1450.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.56, CURRENT_TIMESTAMP),
  ('10156', 1, 1400.00, 'seed_torino_agg_v1', '2025-11-01', NULL, 0.55, CURRENT_TIMESTAMP),
  ('15121', 2, 1600.00, 'seed_piemonte_agg_v1', '2025-11-01', NULL, 0.70, CURRENT_TIMESTAMP), -- Alessandria
  ('14100', 3, 1400.00, 'seed_piemonte_agg_v1', '2025-11-01', NULL, 0.68, CURRENT_TIMESTAMP), -- Asti
  ('13900', 4, 1350.00, 'seed_piemonte_agg_v1', '2025-11-01', NULL, 0.66, CURRENT_TIMESTAMP), -- Biella
  ('12100', 5, 1450.00, 'seed_piemonte_agg_v1', '2025-11-01', NULL, 0.65, CURRENT_TIMESTAMP), -- Cuneo
  ('28100', 6, 1550.00, 'seed_piemonte_agg_v1', '2025-11-01', NULL, 0.67, CURRENT_TIMESTAMP), -- Novara
  ('28922', 7, 2300.00, 'seed_piemonte_agg_v1', '2025-11-01', NULL, 0.75, CURRENT_TIMESTAMP), -- Verbania
  ('13100', 8, 1250.00, 'seed_piemonte_agg_v1', '2025-11-01', NULL, 0.63, CURRENT_TIMESTAMP) -- Vercelli
-- Utente di test
INSERT IGNORE INTO Utente (idUtente, nome, cognome, telefono, email, ruolo, verifica_email, consenso_privacy)
VALUES (1, 'Mario', 'Rossi', '3331234567', 'mario.rossi@example.com', 'proprietario', TRUE, TRUE);

-- Log di completamento
SELECT 'Database inizializzato con successo!' AS Status;
