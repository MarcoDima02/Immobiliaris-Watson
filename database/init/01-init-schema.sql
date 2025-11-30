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
    nPiano INT(2) NULL,
    nPianiImmobile INT(2) NULL,
    balconeTerrazzo BOOLEAN NOT NULL DEFAULT FALSE,
    giardino BOOLEAN NOT NULL DEFAULT FALSE,
    garage BOOLEAN NOT NULL DEFAULT FALSE,
    ascensore BOOLEAN NOT NULL DEFAULT FALSE,
    cantina BOOLEAN NOT NULL DEFAULT FALSE,
    tipoRiscaldamento ENUM('No','Autonomo','Condominiale','Pompe di calore','Pavimento') DEFAULT 'No' NULL,
    annoCostruzione YEAR NOT NULL,
    condizioneImmobile ENUM('Nuovo', 'Ristrutturato','Parzialmente ristrutturato','Non ristrutturato') NULL,
    classeEnergetica ENUM('A+', 'A', 'B', 'C', 'D', 'E', 'F', 'G') DEFAULT 'G' NULL,
    esposizione VARCHAR(50) NULL,
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
    idAgente INT,
    idImmobile INT NOT NULL,
    tipoContratto ENUM ('AFFITTO', 'VENDITA', 'COMODATO', 'ESCLUSIVO', 'altro'),
    dataContratto DATE,
    dataScadenzaContratto DATE,
    pathContrattoPDF VARCHAR(255),
    FOREIGN KEY (idImmobile) REFERENCES Immobile(idImmobile) ON DELETE CASCADE,
    FOREIGN KEY (idAgente) REFERENCES Utente(idUtente) ON DELETE CASCADE

);

-- ========================
-- TABELLA: Leads
-- ========================
CREATE TABLE IF NOT EXISTS Leads (
    idLead INT AUTO_INCREMENT PRIMARY KEY,
    idUtente INT,
    nome_completo VARCHAR(100),
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

-- ========================
-- TABELLA: EmailLog (storico invii mail)
-- ========================
CREATE TABLE IF NOT EXISTS EmailLog (
    idEmail INT AUTO_INCREMENT PRIMARY KEY,
    destinatario VARCHAR(200),
    subject VARCHAR(255),
    template VARCHAR(150),
    variablesJson TEXT,
    status VARCHAR(20) DEFAULT 'PENDING',
    attempts INT DEFAULT 0,
    providerResponse TEXT,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    (8, 'Vercelli', 'VC', 'Piemonte', '002009');

-- Inserimento prezzi per CAP di Torino
INSERT IGNORE INTO PrezzoPerCAP (cap, idCitta, prezzoMq, fonte, validFrom, qualityScore) 
VALUES 
    -- Torino (idCitta = 1)
    -- Centro (Prestigio)
    ('10121', 1, 4800.00, 'seed_torino_agg_v2', '2025-11-01', 0.95), -- Via Roma, Centro Storico
    ('10122', 1, 4200.00, 'seed_torino_agg_v2', '2025-11-01', 0.92), -- Piazza Castello, Quadrilatero
    ('10123', 1, 4600.00, 'seed_torino_agg_v2', '2025-11-01', 0.94), -- Piazza Vittorio, Gran Madre
  
    -- Zone Residenziali Prestigio (Crocetta, Cit Turin, Collina)
    ('10128', 1, 3800.00, 'seed_torino_agg_v2', '2025-11-01', 0.90), -- Crocetta
    ('10129', 1, 3700.00, 'seed_torino_agg_v2', '2025-11-01', 0.90), -- Crocetta
    ('10131', 1, 3900.00, 'seed_torino_agg_v2', '2025-11-01', 0.88), -- Borgo Po, Cavoretto (Collina)
    ('10132', 1, 3800.00, 'seed_torino_agg_v2', '2025-11-01', 0.88), -- Madonna del Pilone (Collina)
    ('10133', 1, 3600.00, 'seed_torino_agg_v2', '2025-11-01', 0.87), -- Cavoretto (Collina)
    ('10138', 1, 3300.00, 'seed_torino_agg_v2', '2025-11-01', 0.89), -- Cit Turin (Liberty)

    -- Zone Semicentrali / Residenziali (San Salvario, Vanchiglia, San Paolo, Santa Rita)
    ('10124', 1, 3000.00, 'seed_torino_agg_v2', '2025-11-01', 0.85), -- Vanchiglia
    ('10125', 1, 2900.00, 'seed_torino_agg_v2', '2025-11-01', 0.84), -- San Salvario
    ('10126', 1, 2800.00, 'seed_torino_agg_v2', '2025-11-01', 0.82), -- San Salvario / Nizza
    ('10134', 1, 1600.00, 'seed_torino_agg_v2', '2025-11-01', 0.75), -- Mirafiori Sud
    ('10135', 1, 1800.00, 'seed_torino_agg_v2', '2025-11-01', 0.78), -- Mirafiori Sud
    ('10136', 1, 2100.00, 'seed_torino_agg_v2', '2025-11-01', 0.80), -- Santa Rita
    ('10137', 1, 2000.00, 'seed_torino_agg_v2', '2025-11-01', 0.79), -- Santa Rita / Mirafiori Nord
    ('10139', 1, 2200.00, 'seed_torino_agg_v2', '2025-11-01', 0.80), -- Cenisia
    ('10141', 1, 2300.00, 'seed_torino_agg_v2', '2025-11-01', 0.81), -- San Paolo
    ('10142', 1, 2000.00, 'seed_torino_agg_v2', '2025-11-01', 0.78), -- Pozzo Strada
    ('10143', 1, 1900.00, 'seed_torino_agg_v2', '2025-11-01', 0.77), -- Parella
    ('10144', 1, 1850.00, 'seed_torino_agg_v2', '2025-11-01', 0.76), -- San Donato
    ('10146', 1, 2000.00, 'seed_torino_agg_v2', '2025-11-01', 0.78), -- Parella / Campidoglio

    -- Periferia / Zone Popolari
    ('10145', 1, 1500.00, 'seed_torino_agg_v2', '2025-11-01', 0.70), -- Barriera di Milano
    ('10147', 1, 1600.00, 'seed_torino_agg_v2', '2025-11-01', 0.72), -- Borgo Vittoria
    ('10148', 1, 1400.00, 'seed_torino_agg_v2', '2025-11-01', 0.68), -- Barriera di Lanzo
    ('10149', 1, 1650.00, 'seed_torino_agg_v2', '2025-11-01', 0.70), -- Lucento
    ('10151', 1, 1300.00, 'seed_torino_agg_v2', '2025-11-01', 0.65), -- Vallette
    ('10152', 1, 1600.00, 'seed_torino_agg_v2', '2025-11-01', 0.70), -- Aurora
    ('10153', 1, 1700.00, 'seed_torino_agg_v2', '2025-11-01', 0.72), -- Regio Parco (in riqualificazione)
    ('10154', 1, 1500.00, 'seed_torino_agg_v2', '2025-11-01', 0.68), -- Barriera di Milano
    ('10155', 1, 1450.00, 'seed_torino_agg_v2', '2025-11-01', 0.67), -- Barriera di Milano
    ('10156', 1, 1400.00, 'seed_torino_agg_v2', '2025-11-01', 0.65), -- Falchera

    -- Altre Città Piemonte
    ('15121', 2, 1600.00, 'seed_piemonte_agg_v1', '2025-11-01', 0.70), -- Alessandria
    ('14100', 3, 1400.00, 'seed_piemonte_agg_v1', '2025-11-01', 0.68), -- Asti
    ('13900', 4, 1350.00, 'seed_piemonte_agg_v1', '2025-11-01', 0.66), -- Biella
    ('12100', 5, 1450.00, 'seed_piemonte_agg_v1', '2025-11-01', 0.65), -- Cuneo
    ('28100', 6, 1550.00, 'seed_piemonte_agg_v1', '2025-11-01', 0.67), -- Novara
    ('28922', 7, 2300.00, 'seed_piemonte_agg_v1', '2025-11-01', 0.75), -- Verbania
    ('13100', 8, 1250.00, 'seed_piemonte_agg_v1', '2025-11-01', 0.63); -- Vercelli
-- Utenti di test (Proprietari, Agenti, Admin)
INSERT IGNORE INTO Utente (idUtente, nome, cognome, telefono, email, passwordHash, ruolo, verifica_email, consenso_privacy)
VALUES 
    -- Proprietari
    (1, 'Mario', 'Rossi', '3331234567', 'mario.rossi@example.com', NULL, 'proprietario', TRUE, TRUE),
    (2, 'Luca', 'Bianchi', '3332345678', 'luca.bianchi@example.com', '$2a$10$z8nV/pxKZg8HVv2u5U7Juu1d8yqV1T1ZsGzV8cQpGQh7yK6d5n6e2', 'proprietario', TRUE, TRUE),
    (5, 'Giulia', 'Ferrari', '3334567890', 'giulia.ferrari@example.com', NULL, 'proprietario', TRUE, TRUE),
    (6, 'Marco', 'Colombo', '3335678901', 'marco.colombo@example.com', NULL, 'proprietario', TRUE, TRUE),
    (7, 'Elena', 'Conti', '3336789012', 'elena.conti@example.com', NULL, 'proprietario', TRUE, TRUE),
    (8, 'Paolo', 'Romano', '3337890123', 'paolo.romano@example.com', NULL, 'proprietario', TRUE, TRUE),
    (9, 'Francesca', 'Galli', '3338901234', 'francesca.galli@example.com', NULL, 'proprietario', TRUE, TRUE),
    (10, 'Roberto', 'Martini', '3339012345', 'roberto.martini@example.com', NULL, 'proprietario', TRUE, TRUE),
    (11, 'Chiara', 'Ricci', '3330123456', 'chiara.ricci@example.com', NULL, 'proprietario', TRUE, TRUE),
    (12, 'Alessandro', 'Moretti', '3331234560', 'alessandro.moretti@example.com', NULL, 'proprietario', TRUE, TRUE),
    
    -- Agenti
    (4, 'Sofia','Costa','3337778888','sofia.costa@example.com','$2b$12$4D0qFUtKe/AAq3YFojSSMOvbp8CB5P3VvN8YADAgTHNeSv/bCkGTy','agente',TRUE,TRUE),
    (13, 'Davide', 'Esposito', '3332345670', 'davide.esposito@example.com', '$2a$10$k3MH0a9qW7jZl4Ey3P1bXe9u6V6pQ2F9xYg6cR8sH4jJ1uE0q9s8a', 'agente', TRUE, TRUE),
    (14, 'Valentina', 'Greco', '3333456701', 'valentina.greco@example.com', '$2a$10$k3MH0a9qW7jZl4Ey3P1bXe9u6V6pQ2F9xYg6cR8sH4jJ1uE0q9s8a', 'agente', TRUE, TRUE),
    
    -- Amministratore
    (3, 'Anna', 'Verdi', '3333456789', 'anna.verdi@example.com', '$2a$10$k3MH0a9qW7jZl4Ey3P1bXe9u6V6pQ2F9xYg6cR8sH4jJ1uE0q9s8a', 'amministratore', TRUE, TRUE);
-- ========================
-- SAMPLE: Immobili di esempio (20 immobili)
-- ========================
INSERT IGNORE INTO Immobile (idImmobile, idProprietario, tipologia, indirizzo, citta, provincia, cap, latitudine, longitudine, stato)
VALUES
    -- Torino - Centro e Crocetta
    (1, 1, 'Appartamento', 'Via Garibaldi 10', 'Torino', 'TO', '10121', 45.070300, 7.686900, 'Disponibile'),
    (2, 1, 'Appartamento', 'Corso Francia 200', 'Torino', 'TO', '10135', 45.057500, 7.639700, 'Disponibile'),
    (3, 2, 'Villa', 'Via delle Rose 5', 'Novara', 'NO', '28100', 45.448000, 8.621000, 'Disponibile'),
    (4, 5, 'Monolocale', 'Piazza Vittorio 1', 'Torino', 'TO', '10123', 45.062000, 7.680600, 'Disponibile'),
    (5, 5, 'Appartamento', 'Via Nizza 45', 'Torino', 'TO', '10126', 45.055000, 7.673000, 'Disponibile'),
    (6, 6, 'Appartamento', 'Corso Vittorio Emanuele 80', 'Torino', 'TO', '10128', 45.058000, 7.671000, 'Disponibile'),
    (7, 6, 'Villa', 'Via Roma 8', 'Torino', 'TO', '10122', 45.0650, 7.6820, 'Disponibile'),
    (8, 7, 'Monolocale', 'Corso Stati Uniti 15', 'Torino', 'TO', '10128', 45.0575, 7.6695, 'Disponibile'),
    (9, 7, 'Appartamento', 'Via San Secondo 22', 'Torino', 'TO', '10128', 45.0560, 7.6720, 'Disponibile'),
    (10, 8, 'Appartamento', 'Via Sacchi 30', 'Torino', 'TO', '10128', 45.0630, 7.6750, 'Disponibile'),
    
    -- Torino - Altre zone
    (11, 9, 'Casa indipendente', 'Via Po 125', 'Torino', 'TO', '10124', 45.0665, 7.6945, 'Disponibile'),
    (12, 9, 'Appartamento', 'Corso Giulio Cesare 88', 'Torino', 'TO', '10152', 45.0885, 7.6800, 'Disponibile'),
    (13, 10, 'Appartamento', 'Via Cibrario 50', 'Torino', 'TO', '10144', 45.0825, 7.6540, 'Disponibile'),
    (14, 10, 'Villa', 'Strada del Cartman 12', 'Torino', 'TO', '10131', 45.0450, 7.7100, 'Disponibile'),
    (15, 11, 'Monolocale', 'Corso Regina Margherita 200', 'Torino', 'TO', '10152', 45.0890, 7.6790, 'Disponibile'),
    
    -- Novara e altre città
    (16, 11, 'Appartamento', 'Corso Cavour 18', 'Novara', 'NO', '28100', 45.4460, 8.6210, 'Disponibile'),
    (17, 12, 'Casa indipendente', 'Via Biglieri 5', 'Novara', 'NO', '28100', 45.4490, 8.6240, 'Disponibile'),
    (18, 12, 'Appartamento', 'Via San Francesco 30', 'Asti', 'AT', '14100', 44.9010, 8.2065, 'Disponibile'),
    (19, 2, 'Villa', 'Corso Alba 22', 'Cuneo', 'CN', '12100', 44.3840, 7.5420, 'Disponibile'),
    (20, 5, 'Appartamento', 'Via Dante 14', 'Alessandria', 'AL', '15121', 44.9130, 8.6150, 'Disponibile');

-- ========================
-- SAMPLE: Dettagli immobili (20 immobili)
-- ========================
INSERT IGNORE INTO DettagliImmobile (idImmobile, nStanze, nBagni, nPiano, nPianiImmobile, balconeTerrazzo, giardino, garage, ascensore, cantina, tipoRiscaldamento, annoCostruzione, condizioneImmobile, classeEnergetica, esposizione, prezzo)
VALUES
    (1, 3, 1, 2, 5, TRUE, FALSE, FALSE, TRUE, FALSE, 'Autonomo', 1998, 'Ristrutturato', 'B', 'Sud-Est', 250000.00),
    (2, 2, 1, 4, 6, TRUE, FALSE, TRUE, TRUE, TRUE, 'Autonomo', 2005, 'Parzialmente ristrutturato', 'C', 'Nord', 180000.00),
    (3, 5, 3, 0, 2, TRUE, TRUE, TRUE, FALSE, TRUE, 'Pavimento', 1995, 'Ristrutturato', 'A', 'Sud', 890000.00),
    (4, 1, 1, 3, 5, FALSE, FALSE, FALSE, TRUE, FALSE, 'Condominiale', 2010, 'Nuovo', 'A', 'Est', 95000.00),
    (5, 3, 2, 1, 4, TRUE, FALSE, FALSE, TRUE, TRUE, 'Autonomo', 2000, 'Parzialmente ristrutturato', 'C', 'Ovest', 220000.00),
    (6, 4, 2, 3, 6, TRUE, FALSE, TRUE, TRUE, FALSE, 'Autonomo', 1985, 'Ristrutturato', 'B', 'Sud-Est', 320000.00),
    (7, 6, 4, 0, 3, TRUE, TRUE, TRUE, FALSE, TRUE, 'Pompe di calore', 2015, 'Nuovo', 'A+', 'Sud', 750000.00),
    (8, 1, 1, 2, 5, TRUE, FALSE, FALSE, TRUE, FALSE, 'Condominiale', 2018, 'Nuovo', 'A', 'Nord', 125000.00),
    (9, 3, 2, 5, 7, TRUE, FALSE, FALSE, TRUE, TRUE, 'Autonomo', 1992, 'Parzialmente ristrutturato', 'C', 'Est', 265000.00),
    (10, 4, 2, 2, 5, TRUE, FALSE, TRUE, TRUE, FALSE, 'Autonomo', 1997, 'Ristrutturato', 'B', 'Ovest', 310000.00),
    (11, 5, 3, 0, 2, FALSE, TRUE, TRUE, FALSE, FALSE, 'Autonomo', 1970, 'Non ristrutturato', 'F', 'Sud', 380000.00),
    (12, 2, 1, 1, 4, TRUE, FALSE, FALSE, FALSE, FALSE, 'Condominiale', 2012, 'Nuovo', 'B', 'Nord-Est', 165000.00),
    (13, 3, 1, 6, 8, TRUE, FALSE, FALSE, TRUE, TRUE, 'Autonomo', 1988, 'Parzialmente ristrutturato', 'D', 'Sud', 175000.00),
    (14, 7, 4, 0, 2, TRUE, TRUE, TRUE, FALSE, TRUE, 'Pavimento', 2008, 'Ristrutturato', 'A', 'Sud-Ovest', 920000.00),
    (15, 1, 1, 4, 6, FALSE, FALSE, FALSE, TRUE, FALSE, 'Condominiale', 2016, 'Nuovo', 'A', 'Est', 115000.00),
    (16, 3, 2, 2, 5, TRUE, FALSE, FALSE, TRUE, TRUE, 'Autonomo', 2001, 'Parzialmente ristrutturato', 'C', 'Ovest', 185000.00),
    (17, 4, 3, 0, 2, FALSE, TRUE, TRUE, FALSE, FALSE, 'Autonomo', 1975, 'Non ristrutturato', 'E', 'Sud', 290000.00),
    (18, 2, 1, 3, 5, TRUE, FALSE, FALSE, TRUE, FALSE, 'Condominiale', 2010, 'Nuovo', 'B', 'Nord', 140000.00),
    (19, 6, 3, 0, 2, TRUE, TRUE, TRUE, FALSE, TRUE, 'Pompe di calore', 2012, 'Ristrutturato', 'A', 'Sud-Est', 680000.00),
    (20, 3, 1, 1, 4, TRUE, FALSE, FALSE, FALSE, FALSE, 'Autonomo', 2005, 'Parzialmente ristrutturato', 'C', 'Ovest', 160000.00);

-- ========================
-- SAMPLE: Superfici (20 immobili)
-- ========================
INSERT IGNORE INTO Superfici (idImmobile, superficieMq, superficieBalconeTerrazzo, superficieGiardino, superficieGarage, superficieCantina)
VALUES
    (1, 85, 5, 0, 0, 0),
    (2, 60, 8, 0, 0, 12),
    (3, 220, 30, 400, 40, 20),
    (4, 28, 0, 0, 0, 0),
    (5, 95, 10, 0, 0, 8),
    (6, 120, 12, 0, 18, 0),
    (7, 280, 25, 600, 45, 30),
    (8, 35, 6, 0, 0, 0),
    (9, 105, 8, 0, 0, 10),
    (10, 115, 15, 0, 20, 0),
    (11, 180, 0, 300, 35, 0),
    (12, 70, 7, 0, 0, 0),
    (13, 90, 5, 0, 0, 12),
    (14, 350, 40, 800, 50, 25),
    (15, 32, 0, 0, 0, 0),
    (16, 100, 10, 0, 0, 15),
-- ========================
-- SAMPLE: Valutazioni (per immobili con richieste)
-- ========================
INSERT IGNORE INTO ValutazioneImmobile (idValutazione, idImmobile, valoreBase, fattoreAggiustamento, valoreMedio, valoreMin, valoreMax, confidence)
VALUES
    (1, 1, 127500, 1.96, 250000, 225000, 275000, 0.85),
    (2, 2, 108000, 1.67, 180000, 160000, 200000, 0.75),
    (3, 3, 450000, 1.98, 890000, 820000, 960000, 0.92),
    (4, 5, 115000, 1.91, 220000, 200000, 240000, 0.88),
    (5, 6, 160000, 2.00, 320000, 290000, 350000, 0.90),
    (6, 8, 65000, 1.92, 125000, 115000, 135000, 0.82),
    (7, 9, 130000, 2.04, 265000, 240000, 290000, 0.87),
    (8, 11, 190000, 2.00, 380000, 350000, 410000, 0.78),
    (9, 13, 87500, 2.00, 175000, 160000, 190000, 0.80),
    (10, 16, 92500, 2.00, 185000, 170000, 200000, 0.85);
-- ========================
INSERT IGNORE INTO Immagine (idImmagine, idImmobile, url, nomeFile, descrizione, copertina, ordinamento, dimensioneKb)
VALUES
    (1, 1, 'https://example.com/images/immobile1-1.jpg', 'immobile1-1.jpg', 'Soggiorno luminoso', TRUE, 1, 250),
    (2, 1, 'https://example.com/images/immobile1-2.jpg', 'immobile1-2.jpg', 'Cucina arredata', FALSE, 2, 180),
    (3, 2, 'https://example.com/images/immobile2-1.jpg', 'immobile2-1.jpg', 'Vista esterna', TRUE, 1, 320),
    (4, 3, 'https://example.com/images/immobile3-1.jpg', 'immobile3-1.jpg', 'Giardino e piscina', TRUE, 1, 980),
    (5, 4, 'https://example.com/images/immobile4-1.jpg', 'immobile4-1.jpg', 'Monolocale centrale', TRUE, 1, 120);

-- ========================
-- SAMPLE: Richieste di valutazione (vari stati)
-- ========================
INSERT IGNORE INTO Richiesta (idRichiesta, idUtente, idImmobile, dataRichiesta, dataAppuntamento, stato, noteUtente, motivoAnnullamento)
VALUES
    -- Richieste IN_ATTESA (non ancora assegnate)
    (1, 1, 1, '2025-11-15 10:30:00', '2025-12-05 15:00:00', 'In attesa', 'Vorrei una valutazione per vendita', NULL),
    (2, 5, 4, '2025-11-20 14:00:00', '2025-12-08 10:00:00', 'In attesa', 'Urgente, possibile vendita rapida', NULL),
    (3, 10, 13, '2025-11-25 09:15:00', '2025-12-10 11:00:00', 'In attesa', 'Prima valutazione, no fretta', NULL),
    (4, 12, 20, '2025-11-28 16:45:00', '2025-12-12 14:30:00', 'In attesa', 'Interessato a capire valore mercato', NULL),
    
    -- Richieste IN_ELABORAZIONE (prese in carico da agenti)
    (5, 2, 3, '2025-10-10 11:00:00', '2025-10-25 16:00:00', 'In elaborazione', 'Villa da valutare per vendita', NULL),
    (6, 5, 5, '2025-11-01 10:00:00', '2025-11-18 15:30:00', 'In elaborazione', 'Appartamento zona Nizza', NULL),
    (7, 6, 6, '2025-11-05 14:30:00', '2025-11-20 10:00:00', 'In elaborazione', 'Crocetta, 4 locali', NULL),
    (8, 7, 8, '2025-11-10 09:00:00', '2025-11-25 11:00:00', 'In elaborazione', 'Monolocale studenti', NULL),
    (9, 7, 9, '2025-11-12 15:00:00', '2025-11-27 14:00:00', 'In elaborazione', 'Appartamento Crocetta', NULL),
    (10, 11, 16, '2025-11-08 10:30:00', '2025-11-22 16:00:00', 'In elaborazione', 'Novara centro', NULL),
    
    -- Richieste COMPLETATA (valutazioni concluse)
    (11, 9, 11, '2025-09-15 10:00:00', '2025-09-30 15:00:00', 'Completata', 'Casa indipendente zona Po', NULL),
    (12, 10, 14, '2025-09-20 11:00:00', '2025-10-05 10:00:00', 'Completata', 'Villa collina', NULL),
    
    -- Richieste ANNULLATA
    (13, 8, 10, '2025-10-01 14:00:00', '2025-10-15 11:00:00', 'Annullata', 'Appartamento Crocetta', 'Cliente ha ritirato richiesta');

-- ========================
-- SAMPLE: Contratti (collegati a richieste in elaborazione e completate)
-- ========================
INSERT IGNORE INTO Contratti (idContratto, idImmobile, idAgente, tipoContratto, dataContratto, dataScadenzaContratto, pathContrattoPDF)
VALUES
    -- Contratti per richieste IN_ELABORAZIONE (agente Sofia Costa - id 4)
    (1, 3, 4, 'VENDITA', '2025-10-10', '2026-10-10', '/uploads/contratti/contratto_1.pdf'),
    (2, 5, 4, 'VENDITA', '2025-11-01', '2026-11-01', '/uploads/contratti/contratto_2.pdf'),
    (3, 6, 4, 'VENDITA', '2025-11-05', '2026-11-05', '/uploads/contratti/contratto_3.pdf'),
    (4, 8, 4, 'VENDITA', '2025-11-10', '2026-11-10', '/uploads/contratti/contratto_4.pdf'),
    (5, 9, 4, 'VENDITA', '2025-11-12', '2026-11-12', '/uploads/contratti/contratto_5.pdf'),
    
    -- Contratti per richieste IN_ELABORAZIONE (agente Davide Esposito - id 13)
    (6, 16, 13, 'VENDITA', '2025-11-08', '2026-11-08', '/uploads/contratti/contratto_6.pdf'),
    
    -- Contratti per richieste COMPLETATA (agente Sofia Costa - id 4)
    (7, 11, 4, 'VENDITA', '2025-09-15', '2026-09-15', '/uploads/contratti/contratto_7.pdf'),
    (8, 14, 4, 'VENDITA', '2025-09-20', '2026-09-20', '/uploads/contratti/contratto_8.pdf');
    (2, 2, 108000, 1.67, 180000, 160000, 200000, 0.75);

-- ========================
-- SAMPLE: Contratti
-- ========================
INSERT IGNORE INTO Contratti (idContratto, idImmobile, idAgente, tipoContratto, dataContratto, dataScadenzaContratto, pathContrattoPDF)
VALUES
    (1, 3, 4, 'Esclusivo', '2025-06-01', '2026-06-01', '/contracts/contratto-3.pdf');

-- ========================
-- SAMPLE: Vendite (per contratti completati)
-- ========================
INSERT IGNORE INTO Vendite (idVendita, idContratto, idImmobile, idUtente, commissionePercentuale)
VALUES
    (1, 7, 11, 9, 3.00),
    (2, 8, 14, 10, 3.50);

-- ========================
-- SAMPLE: Leads (potenziali clienti)
-- ========================
INSERT IGNORE INTO Leads (idLead, idUtente, nome_completo, email, telefono, citta, fonte, convertitoInRichiesta, idRichiesta, assegnatoA, note, createdAt)
VALUES
    (1, NULL, 'Giulia Neri', 'giulia.neri@example.com', '3451234567', 'Torino', 'Sito Web', FALSE, NULL, 4, 'Interessata a bilocale zona Crocetta', '2025-11-20 10:00:00'),
    (2, NULL, 'Marco Leone', 'marco.leone@example.com', '3462345678', 'Novara', 'Landing Page', FALSE, NULL, 13, 'Cerca trilocale con giardino', '2025-11-22 14:30:00'),
    (3, NULL, 'Stefania Piras', 'stefania.piras@example.com', '3473456789', 'Torino', 'Referral', FALSE, NULL, 4, 'Referral da cliente Mario Rossi', '2025-11-24 09:15:00'),
    (4, NULL, 'Andrea Fabbri', 'andrea.fabbri@example.com', '3484567890', 'Alessandria', 'Google Ads', FALSE, NULL, 14, 'Primo contatto, cerca info mercato', '2025-11-26 16:20:00'),
    (5, 1, 'Mario Rossi', 'mario.rossi@example.com', '3331234567', 'Torino', 'Cliente Esistente', TRUE, 1, 4, 'Cliente convertito a richiesta', '2025-11-10 11:00:00');
    
-- Log di completamento
SELECT 'Database inizializzato con successo!' AS Status;


-- Password già hashate nel DB:

-- Agente Sofia Costa (id=4): sofia.costa@example.com - password: password123 (hash bcrypt presente)
-- Admin Anna Verdi (id=3): anna.verdi@example.com - password: admin123 (hash presente)