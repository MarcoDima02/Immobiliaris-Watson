-- password per gli accessi nella sezione login per ogni utente: agente123

-- ============================================
-- Script di inizializzazione database MySQL
-- Eseguito automaticamente dal container MySQL
-- ============================================

-- Il database e l'utente sono già creati dalle variabili ambiente
-- USE residea_db; -- già selezionato di default

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
-- TRUNCATE TABLE Utente;
-- SET FOREIGN_KEY_CHECKS = 1;
-- ============================================


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
    -- Proprietari (con vari stati di verifica e consenso)
    (1, 'Mario', 'Rossi', '3331234567', 'mario.rossi@example.com', NULL, 'proprietario', TRUE, TRUE),
    (2, 'Luca', 'Bianchi', '3332345678', 'luca.bianchi@example.com', '$2b$12$4D0qFUtKe/AAq3YFojSSMOvbp8CB5P3VvN8YADAgTHNeSv/bCkGTy', 'proprietario', TRUE, TRUE),
    (5, 'Giulia', 'Ferrari', '3334567890', 'giulia.ferrari@example.com', NULL, 'proprietario', TRUE, TRUE),
    (6, 'Marco', 'Colombo', '3335678901', 'marco.colombo@example.com', NULL, 'proprietario', TRUE, TRUE),
    (7, 'Elena', 'Conti', '3336789012', 'elena.conti@example.com', NULL, 'proprietario', TRUE, TRUE),
    (8, 'Paolo', 'Romano', '3337890123', 'paolo.romano@example.com', NULL, 'proprietario', TRUE, TRUE),
    (9, 'Francesca', 'Galli', '3338901234', 'francesca.galli@example.com', NULL, 'proprietario', TRUE, TRUE),
    (10, 'Roberto', 'Martini', '3339012345', 'roberto.martini@example.com', NULL, 'proprietario', TRUE, TRUE),
    (11, 'Chiara', 'Ricci', '3330123456', 'chiara.ricci@example.com', NULL, 'proprietario', TRUE, TRUE),
    (12, 'Alessandro', 'Moretti', '3331234560', 'alessandro.moretti@example.com', NULL, 'proprietario', TRUE, TRUE),
    (15, 'Simone', 'Barbieri', '3341111111', 'simone.barbieri@example.com', NULL, 'proprietario', TRUE, TRUE),
    (16, 'Laura', 'Fontana', '3342222222', 'laura.fontana@example.com', NULL, 'proprietario', TRUE, TRUE),
    (17, 'Andrea', 'Mariani', '3343333333', 'andrea.mariani@example.com', NULL, 'proprietario', FALSE, TRUE),
    (18, 'Claudia', 'Russo', '3344444444', 'claudia.russo@example.com', NULL, 'proprietario', TRUE, FALSE),
    (19, 'Matteo', 'De Luca', '3345555555', 'matteo.deluca@example.com', NULL, 'proprietario', FALSE, FALSE),
    (20, 'Sara', 'Fabbri', '3346666666', 'sara.fabbri@example.com', '$2b$12$4D0qFUtKe/AAq3YFojSSMOvbp8CB5P3VvN8YADAgTHNeSv/bCkGTy', 'proprietario', TRUE, TRUE),
    (21, 'Giorgio', 'Santoro', '3347777777', 'giorgio.santoro@example.com', NULL, 'proprietario', TRUE, TRUE),
    (22, 'Alessia', 'Pellegrini', '3348888888', 'alessia.pellegrini@example.com', NULL, 'proprietario', TRUE, TRUE),
    (23, 'Davide', 'Serra', '3349999999', 'davide.serra@example.com', NULL, 'proprietario', TRUE, TRUE),
    (24, 'Federica', 'Bassi', '3340000000', 'federica.bassi@example.com', NULL, 'proprietario', TRUE, TRUE),
    (25, 'Riccardo', 'Monti', '3341234567', 'riccardo.monti@example.com', NULL, 'proprietario', TRUE, TRUE),
    
    -- Agenti
    (4, 'Sofia','Costa','3337778888','sofia.costa@example.com','$2b$12$4D0qFUtKe/AAq3YFojSSMOvbp8CB5P3VvN8YADAgTHNeSv/bCkGTy','agente',TRUE,TRUE),
    (13, 'Davide', 'Esposito', '3332345670', 'davide.esposito@example.com', '$2b$12$4D0qFUtKe/AAq3YFojSSMOvbp8CB5P3VvN8YADAgTHNeSv/bCkGTy', 'agente', TRUE, TRUE),
    (14, 'Valentina', 'Greco', '3333456701', 'valentina.greco@example.com', '$2b$12$4D0qFUtKe/AAq3YFojSSMOvbp8CB5P3VvN8YADAgTHNeSv/bCkGTy', 'agente', TRUE, TRUE),
    (26, 'Marco', 'Ferri', '3351234567', 'marco.ferri@example.com', '$2b$12$4D0qFUtKe/AAq3YFojSSMOvbp8CB5P3VvN8YADAgTHNeSv/bCkGTy', 'agente', TRUE, TRUE),
    (27, 'Giulia', 'Mancini', '3352345678', 'giulia.mancini@example.com', '$2b$12$4D0qFUtKe/AAq3YFojSSMOvbp8CB5P3VvN8YADAgTHNeSv/bCkGTy', 'agente', TRUE, TRUE),
    (28, 'Roberto', 'Vitale', '3353456789', 'roberto.vitale@example.com', '$2b$12$4D0qFUtKe/AAq3YFojSSMOvbp8CB5P3VvN8YADAgTHNeSv/bCkGTy', 'agente', TRUE, TRUE),
    
    -- Amministratori
    (3, 'Anna', 'Verdi', '3333456789', 'anna.verdi@example.com', '$2b$12$4D0qFUtKe/AAq3YFojSSMOvbp8CB5P3VvN8YADAgTHNeSv/bCkGTy', 'amministratore', TRUE, TRUE),
    (29, 'Carlo', 'Rossi', '3354567890', 'carlo.rossi@example.com', '$2b$12$4D0qFUtKe/AAq3YFojSSMOvbp8CB5P3VvN8YADAgTHNeSv/bCkGTy', 'amministratore', TRUE, TRUE);
-- ========================
-- SAMPLE: Immobili di esempio (40 immobili diversificati)
-- ========================
INSERT IGNORE INTO Immobile (idImmobile, idProprietario, tipologia, indirizzo, citta, provincia, cap, latitudine, longitudine, stato)
VALUES
    -- Torino - Centro (zona prestigio)
    (1, 1, 'Appartamento', 'Via Garibaldi 10', 'Torino', 'TO', '10121', 45.070300, 7.686900, 'Disponibile'),
    (2, 1, 'Appartamento', 'Corso Francia 200', 'Torino', 'TO', '10135', 45.057500, 7.639700, 'Disponibile'),
    (4, 5, 'Monolocale', 'Piazza Vittorio 1', 'Torino', 'TO', '10123', 45.062000, 7.680600, 'Disponibile'),
    (5, 5, 'Appartamento', 'Via Nizza 45', 'Torino', 'TO', '10126', 45.055000, 7.673000, 'Disponibile'),
    (6, 6, 'Appartamento', 'Corso Vittorio Emanuele 80', 'Torino', 'TO', '10128', 45.058000, 7.671000, 'Disponibile'),
    (7, 6, 'Villa', 'Via Roma 8', 'Torino', 'TO', '10122', 45.0650, 7.6820, 'Disponibile'),
    (8, 7, 'Monolocale', 'Corso Stati Uniti 15', 'Torino', 'TO', '10128', 45.0575, 7.6695, 'Disponibile'),
    (9, 7, 'Appartamento', 'Via San Secondo 22', 'Torino', 'TO', '10128', 45.0560, 7.6720, 'Disponibile'),
    (10, 8, 'Appartamento', 'Via Sacchi 30', 'Torino', 'TO', '10128', 45.0630, 7.6750, 'Venduto'),
    (30, 15, 'Appartamento', 'Piazza Castello 5', 'Torino', 'TO', '10122', 45.0708, 7.6852, 'Disponibile'),
    (31, 16, 'Villa', 'Corso Moncalieri 100', 'Torino', 'TO', '10133', 45.0480, 7.7050, 'Disponibile'),
    
    -- Torino - Altre zone
    (11, 9, 'Casa indipendente', 'Via Po 125', 'Torino', 'TO', '10124', 45.0665, 7.6945, 'Disponibile'),
    (12, 9, 'Appartamento', 'Corso Giulio Cesare 88', 'Torino', 'TO', '10152', 45.0885, 7.6800, 'Disponibile'),
    (13, 10, 'Appartamento', 'Via Cibrario 50', 'Torino', 'TO', '10144', 45.0825, 7.6540, 'Disponibile'),
    (14, 10, 'Villa', 'Strada del Cartman 12', 'Torino', 'TO', '10131', 45.0450, 7.7100, 'Venduto'),
    (15, 11, 'Monolocale', 'Corso Regina Margherita 200', 'Torino', 'TO', '10152', 45.0890, 7.6790, 'Disponibile'),
    (32, 17, 'Appartamento', 'Via Madama Cristina 80', 'Torino', 'TO', '10126', 45.0540, 7.6780, 'Disponibile'),
    (33, 18, 'Casa indipendente', 'Strada Stupinigi 25', 'Torino', 'TO', '10141', 45.0295, 7.6130, 'Disponibile'),
    (34, 19, 'Monolocale', 'Via Barbaroux 12', 'Torino', 'TO', '10122', 45.0690, 7.6830, 'Disponibile'),
    (35, 20, 'Appartamento', 'Corso Casale 200', 'Torino', 'TO', '10132', 45.0880, 7.7050, 'Disponibile'),
    (36, 21, 'Villa', 'Via Tasso 45', 'Torino', 'TO', '10123', 45.0610, 7.6840, 'Venduto'),
    (37, 22, 'Appartamento', 'Corso Sebastopoli 150', 'Torino', 'TO', '10136', 45.0380, 7.6510, 'Disponibile'),
    (38, 23, 'Appartamento', 'Via Giolitti 30', 'Torino', 'TO', '10123', 45.0635, 7.6765, 'Disponibile'),
    
    -- Novara
    (3, 2, 'Villa', 'Via delle Rose 5', 'Novara', 'NO', '28100', 45.448000, 8.621000, 'Disponibile'),
    (16, 11, 'Appartamento', 'Corso Cavour 18', 'Novara', 'NO', '28100', 45.4460, 8.6210, 'Disponibile'),
    (17, 12, 'Casa indipendente', 'Via Biglieri 5', 'Novara', 'NO', '28100', 45.4490, 8.6240, 'Disponibile'),
    (39, 24, 'Monolocale', 'Via Fratelli Rosselli 20', 'Novara', 'NO', '28100', 45.4470, 8.6190, 'Disponibile'),
    (40, 25, 'Appartamento', 'Corso Italia 45', 'Novara', 'NO', '28100', 45.4455, 8.6225, 'Disponibile'),
    
    -- Asti
    (18, 12, 'Appartamento', 'Via San Francesco 30', 'Asti', 'AT', '14100', 44.9010, 8.2065, 'Disponibile'),
    (41, 15, 'Villa', 'Corso Dante 88', 'Asti', 'AT', '14100', 44.9005, 8.2080, 'Disponibile'),
    (42, 16, 'Monolocale', 'Piazza Alfieri 3', 'Asti', 'AT', '14100', 44.9000, 8.2070, 'Disponibile'),
    
    -- Cuneo
    (19, 2, 'Villa', 'Corso Alba 22', 'Cuneo', 'CN', '12100', 44.3840, 7.5420, 'Disponibile'),
    (43, 17, 'Appartamento', 'Via Roma 50', 'Cuneo', 'CN', '12100', 44.3835, 7.5415, 'Disponibile'),
    
    -- Alessandria
    (20, 5, 'Appartamento', 'Via Dante 14', 'Alessandria', 'AL', '15121', 44.9130, 8.6150, 'Disponibile'),
    (44, 18, 'Casa indipendente', 'Corso Crimea 70', 'Alessandria', 'AL', '15121', 44.9125, 8.6160, 'Disponibile'),
    
    -- Biella
    (45, 19, 'Appartamento', 'Via Italia 25', 'Biella', 'BI', '13900', 45.5630, 8.0580, 'Disponibile'),
    (46, 20, 'Villa', 'Corso Risorgimento 15', 'Biella', 'BI', '13900', 45.5625, 8.0575, 'Disponibile'),
    
    -- Verbania
    (47, 21, 'Villa', 'Lungolago Cadorna 10', 'Verbania', 'VB', '28922', 45.9200, 8.5510, 'Disponibile'),
    (48, 22, 'Appartamento', 'Via San Vittore 22', 'Verbania', 'VB', '28922', 45.9195, 8.5505, 'Disponibile'),
    
    -- Vercelli
    (49, 23, 'Appartamento', 'Corso Libertà 80', 'Vercelli', 'VC', '13100', 45.3205, 8.4180, 'Disponibile'),
    (50, 24, 'Casa indipendente', 'Via Garibaldi 45', 'Vercelli', 'VC', '13100', 45.3210, 8.4185, 'Disponibile');

-- ========================
-- SAMPLE: Dettagli immobili (40 immobili)
-- ========================
INSERT IGNORE INTO DettagliImmobile (idImmobile, nStanze, nBagni, nPiano, nPianiImmobile, balconeTerrazzo, giardino, garage, ascensore, cantina, tipoRiscaldamento, annoCostruzione, condizioneImmobile, classeEnergetica, esposizione, prezzo)
VALUES
    -- Immobili Torino centro
    (1, 3, 1, 2, 5, TRUE, FALSE, FALSE, TRUE, FALSE, 'Autonomo', 1998, 'Ristrutturato', 'B', 'Sud-Est', 250000.00),
    (2, 2, 1, 4, 6, TRUE, FALSE, TRUE, TRUE, TRUE, 'Autonomo', 2005, 'Parzialmente ristrutturato', 'C', 'Nord', 180000.00),
    (4, 1, 1, 3, 5, FALSE, FALSE, FALSE, TRUE, FALSE, 'Condominiale', 2010, 'Nuovo', 'A', 'Est', 95000.00),
    (5, 3, 2, 1, 4, TRUE, FALSE, FALSE, TRUE, TRUE, 'Autonomo', 2000, 'Parzialmente ristrutturato', 'C', 'Ovest', 220000.00),
    (6, 4, 2, 3, 6, TRUE, FALSE, TRUE, TRUE, FALSE, 'Autonomo', 1985, 'Ristrutturato', 'B', 'Sud-Est', 320000.00),
    (7, 6, 4, 0, 3, TRUE, TRUE, TRUE, FALSE, TRUE, 'Pompe di calore', 2015, 'Nuovo', 'A+', 'Sud', 750000.00),
    (8, 1, 1, 2, 5, TRUE, FALSE, FALSE, TRUE, FALSE, 'Condominiale', 2018, 'Nuovo', 'A', 'Nord', 125000.00),
    (9, 3, 2, 5, 7, TRUE, FALSE, FALSE, TRUE, TRUE, 'Autonomo', 1992, 'Parzialmente ristrutturato', 'C', 'Est', 265000.00),
    (10, 4, 2, 2, 5, TRUE, FALSE, TRUE, TRUE, FALSE, 'Autonomo', 1997, 'Ristrutturato', 'B', 'Ovest', 310000.00),
    (30, 5, 3, 1, 4, TRUE, FALSE, TRUE, TRUE, TRUE, 'Autonomo', 1920, 'Ristrutturato', 'A', 'Sud', 680000.00),
    (31, 8, 5, 0, 3, TRUE, TRUE, TRUE, FALSE, TRUE, 'Pompe di calore', 2019, 'Nuovo', 'A+', 'Sud-Ovest', 1250000.00),
    
    -- Immobili Torino altre zone
    (11, 5, 3, 0, 2, FALSE, TRUE, TRUE, FALSE, FALSE, 'Autonomo', 1970, 'Non ristrutturato', 'F', 'Sud', 380000.00),
    (12, 2, 1, 1, 4, TRUE, FALSE, FALSE, FALSE, FALSE, 'Condominiale', 2012, 'Nuovo', 'B', 'Nord-Est', 165000.00),
    (13, 3, 1, 6, 8, TRUE, FALSE, FALSE, TRUE, TRUE, 'Autonomo', 1988, 'Parzialmente ristrutturato', 'D', 'Sud', 175000.00),
    (14, 7, 4, 0, 2, TRUE, TRUE, TRUE, FALSE, TRUE, 'Pavimento', 2008, 'Ristrutturato', 'A', 'Sud-Ovest', 920000.00),
    (15, 1, 1, 4, 6, FALSE, FALSE, FALSE, TRUE, FALSE, 'Condominiale', 2016, 'Nuovo', 'A', 'Est', 115000.00),
    (32, 4, 2, 0, 4, TRUE, FALSE, TRUE, FALSE, FALSE, 'Autonomo', 1995, 'Parzialmente ristrutturato', 'C', 'Ovest', 290000.00),
    (33, 6, 3, 0, 2, FALSE, TRUE, TRUE, FALSE, TRUE, 'Pompe di calore', 1985, 'Non ristrutturato', 'E', 'Sud', 420000.00),
    (34, 1, 1, 5, 6, TRUE, FALSE, FALSE, TRUE, FALSE, 'Condominiale', 2020, 'Nuovo', 'A+', 'Est', 145000.00),
    (35, 3, 2, 3, 5, TRUE, FALSE, FALSE, TRUE, TRUE, 'Autonomo', 2003, 'Parzialmente ristrutturato', 'C', 'Nord', 280000.00),
    (36, 7, 4, 0, 2, TRUE, TRUE, TRUE, FALSE, TRUE, 'Pavimento', 2010, 'Ristrutturato', 'A', 'Sud', 890000.00),
    (37, 2, 1, 2, 5, TRUE, FALSE, FALSE, TRUE, FALSE, 'Condominiale', 2015, 'Nuovo', 'B', 'Ovest', 195000.00),
    (38, 4, 2, 4, 6, TRUE, FALSE, TRUE, TRUE, TRUE, 'Autonomo', 2000, 'Ristrutturato', 'B', 'Sud-Est', 380000.00),
    
    -- Immobili Novara
    (3, 5, 3, 0, 2, TRUE, TRUE, TRUE, FALSE, TRUE, 'Pavimento', 1995, 'Ristrutturato', 'A', 'Sud', 890000.00),
    (16, 3, 2, 2, 5, TRUE, FALSE, FALSE, TRUE, TRUE, 'Autonomo', 2001, 'Parzialmente ristrutturato', 'C', 'Ovest', 185000.00),
    (17, 4, 3, 0, 2, FALSE, TRUE, TRUE, FALSE, FALSE, 'Autonomo', 1975, 'Non ristrutturato', 'E', 'Sud', 290000.00),
    (39, 1, 1, 3, 5, FALSE, FALSE, FALSE, TRUE, FALSE, 'Condominiale', 2017, 'Nuovo', 'A', 'Nord', 98000.00),
    (40, 3, 2, 1, 4, TRUE, FALSE, FALSE, FALSE, TRUE, 'Autonomo', 2005, 'Parzialmente ristrutturato', 'C', 'Est', 170000.00),
    
    -- Immobili Asti
    (18, 2, 1, 3, 5, TRUE, FALSE, FALSE, TRUE, FALSE, 'Condominiale', 2010, 'Nuovo', 'B', 'Nord', 140000.00),
    (41, 6, 3, 0, 2, TRUE, TRUE, TRUE, FALSE, TRUE, 'Pavimento', 2005, 'Ristrutturato', 'B', 'Sud-Est', 550000.00),
    (42, 1, 1, 2, 4, TRUE, FALSE, FALSE, FALSE, FALSE, 'Condominiale', 2018, 'Nuovo', 'A', 'Ovest', 85000.00),
    
    -- Immobili Cuneo
    (19, 6, 3, 0, 2, TRUE, TRUE, TRUE, FALSE, TRUE, 'Pompe di calore', 2012, 'Ristrutturato', 'A', 'Sud-Est', 680000.00),
    (43, 3, 2, 1, 5, TRUE, FALSE, FALSE, TRUE, FALSE, 'Autonomo', 2008, 'Parzialmente ristrutturato', 'C', 'Nord', 160000.00),
    
    -- Immobili Alessandria
    (20, 3, 1, 1, 4, TRUE, FALSE, FALSE, FALSE, FALSE, 'Autonomo', 2005, 'Parzialmente ristrutturato', 'C', 'Ovest', 160000.00),
    (44, 5, 3, 0, 2, FALSE, TRUE, TRUE, FALSE, TRUE, 'Autonomo', 1980, 'Non ristrutturato', 'F', 'Sud', 320000.00),
    
    -- Immobili Biella
    (45, 2, 1, 4, 6, TRUE, FALSE, FALSE, TRUE, FALSE, 'Condominiale', 2013, 'Nuovo', 'B', 'Est', 135000.00),
    (46, 6, 4, 0, 2, TRUE, TRUE, TRUE, FALSE, TRUE, 'Pompe di calore', 2016, 'Nuovo', 'A+', 'Sud', 720000.00),
    
    -- Immobili Verbania
    (47, 7, 4, 0, 2, TRUE, TRUE, TRUE, FALSE, TRUE, 'Pavimento', 2018, 'Nuovo', 'A+', 'Sud', 1150000.00),
    (48, 3, 2, 2, 4, TRUE, FALSE, FALSE, TRUE, TRUE, 'Autonomo', 2010, 'Ristrutturato', 'A', 'Ovest', 320000.00),
    
    -- Immobili Vercelli
    (49, 2, 1, 3, 5, TRUE, FALSE, FALSE, TRUE, FALSE, 'Condominiale', 2011, 'Nuovo', 'B', 'Nord', 125000.00),
    (50, 4, 2, 0, 2, FALSE, TRUE, TRUE, FALSE, FALSE, 'Autonomo', 1978, 'Non ristrutturato', 'E', 'Sud', 240000.00);

-- ========================
-- SAMPLE: Superfici (40 immobili)
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
    (17, 150, 0, 250, 30, 0),
    (18, 68, 7, 0, 0, 0),
    (19, 310, 35, 700, 45, 22),
    (20, 92, 8, 0, 0, 0),
    (30, 180, 15, 0, 20, 18),
    (31, 420, 50, 1200, 60, 35),
    (32, 125, 12, 0, 22, 0),
    (33, 200, 0, 350, 40, 15),
    (34, 30, 4, 0, 0, 0),
    (35, 110, 10, 0, 0, 12),
    (36, 380, 45, 900, 55, 28),
    (37, 75, 8, 0, 0, 0),
    (38, 140, 14, 0, 25, 16),
    (39, 26, 0, 0, 0, 0),
    (40, 95, 9, 0, 0, 10),
    (41, 260, 30, 500, 42, 20),
    (42, 29, 5, 0, 0, 0),
    (43, 105, 10, 0, 0, 0),
    (44, 190, 0, 280, 35, 0),
    (45, 72, 8, 0, 0, 0),
    (46, 320, 38, 650, 48, 24),
    (47, 450, 55, 1500, 70, 40),
    (48, 115, 12, 0, 0, 14),
    (49, 65, 6, 0, 0, 0),
    (50, 160, 0, 220, 32, 0);
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
    (10, 16, 92500, 2.00, 185000, 170000, 200000, 0.85),
    (11, 30, 340000, 2.00, 680000, 620000, 740000, 0.90),
    (12, 31, 625000, 2.00, 1250000, 1150000, 1350000, 0.88),
    (13, 32, 145000, 2.00, 290000, 265000, 315000, 0.83),
    (14, 35, 140000, 2.00, 280000, 255000, 305000, 0.85),
    (15, 38, 190000, 2.00, 380000, 350000, 410000, 0.87),
    (16, 41, 275000, 2.00, 550000, 510000, 590000, 0.86),
    (17, 43, 80000, 2.00, 160000, 145000, 175000, 0.80),
    (18, 46, 360000, 2.00, 720000, 660000, 780000, 0.89),
    (19, 47, 575000, 2.00, 1150000, 1050000, 1250000, 0.91),
    (20, 48, 160000, 2.00, 320000, 290000, 350000, 0.84);
-- ========================
INSERT IGNORE INTO Immagine (idImmagine, idImmobile, url, nomeFile, descrizione, copertina, ordinamento, dimensioneKb)
VALUES
    (1, 1, 'https://example.com/images/immobile1-1.jpg', 'immobile1-1.jpg', 'Soggiorno luminoso', TRUE, 1, 250),
    (2, 1, 'https://example.com/images/immobile1-2.jpg', 'immobile1-2.jpg', 'Cucina arredata', FALSE, 2, 180),
    (3, 2, 'https://example.com/images/immobile2-1.jpg', 'immobile2-1.jpg', 'Vista esterna', TRUE, 1, 320),
    (4, 3, 'https://example.com/images/immobile3-1.jpg', 'immobile3-1.jpg', 'Giardino e piscina', TRUE, 1, 980),
    (5, 4, 'https://example.com/images/immobile4-1.jpg', 'immobile4-1.jpg', 'Monolocale centrale', TRUE, 1, 120),
    (6, 5, 'https://example.com/images/immobile5-1.jpg', 'immobile5-1.jpg', 'Camera da letto matrimoniale', TRUE, 1, 210),
    (7, 6, 'https://example.com/images/immobile6-1.jpg', 'immobile6-1.jpg', 'Salone doppio', TRUE, 1, 340),
    (8, 7, 'https://example.com/images/immobile7-1.jpg', 'immobile7-1.jpg', 'Villa con piscina', TRUE, 1, 1200),
    (9, 7, 'https://example.com/images/immobile7-2.jpg', 'immobile7-2.jpg', 'Zona notte', FALSE, 2, 890),
    (10, 8, 'https://example.com/images/immobile8-1.jpg', 'immobile8-1.jpg', 'Open space moderno', TRUE, 1, 150),
    (11, 9, 'https://example.com/images/immobile9-1.jpg', 'immobile9-1.jpg', 'Vista panoramica', TRUE, 1, 280),
    (12, 10, 'https://example.com/images/immobile10-1.jpg', 'immobile10-1.jpg', 'Ingresso elegante', TRUE, 1, 310),
    (13, 11, 'https://example.com/images/immobile11-1.jpg', 'immobile11-1.jpg', 'Casa indipendente', TRUE, 1, 560),
    (14, 12, 'https://example.com/images/immobile12-1.jpg', 'immobile12-1.jpg', 'Bilocale luminoso', TRUE, 1, 190),
    (15, 13, 'https://example.com/images/immobile13-1.jpg', 'immobile13-1.jpg', 'Vista dall\'alto', TRUE, 1, 220),
    (16, 14, 'https://example.com/images/immobile14-1.jpg', 'immobile14-1.jpg', 'Villa di lusso', TRUE, 1, 1500),
    (17, 15, 'https://example.com/images/immobile15-1.jpg', 'immobile15-1.jpg', 'Monolocale studenti', TRUE, 1, 95),
    (18, 30, 'https://example.com/images/immobile30-1.jpg', 'immobile30-1.jpg', 'Attico in centro', TRUE, 1, 780),
    (19, 31, 'https://example.com/images/immobile31-1.jpg', 'immobile31-1.jpg', 'Villa panoramica collina', TRUE, 1, 1450),
    (20, 47, 'https://example.com/images/immobile47-1.jpg', 'immobile47-1.jpg', 'Villa lago Maggiore', TRUE, 1, 1680);

-- ========================
-- SAMPLE: Richieste di valutazione (vari stati - 30 richieste)
-- ========================
INSERT IGNORE INTO Richiesta (idRichiesta, idUtente, idImmobile, dataRichiesta, dataAppuntamento, stato, noteUtente, motivoAnnullamento)
VALUES
    -- Richieste IN ATTESA (non ancora assegnate) - 8 richieste
    (1, 1, 1, '2025-11-15 10:30:00', '2025-12-05 15:00:00', 'In attesa', 'Vorrei una valutazione per vendita', NULL),
    (2, 5, 4, '2025-11-20 14:00:00', '2025-12-08 10:00:00', 'In attesa', 'Urgente, possibile vendita rapida', NULL),
    (3, 10, 13, '2025-11-25 09:15:00', '2025-12-10 11:00:00', 'In attesa', 'Prima valutazione, no fretta', NULL),
    (4, 12, 20, '2025-11-28 16:45:00', '2025-12-12 14:30:00', 'In attesa', 'Interessato a capire valore mercato', NULL),
    (25, 15, 32, '2025-11-29 11:00:00', '2025-12-13 10:00:00', 'In attesa', 'Appartamento zona San Salvario', NULL),
    (26, 17, 37, '2025-11-30 09:30:00', '2025-12-14 15:30:00', 'In attesa', 'Necessaria valutazione rapida', NULL),
    (27, 19, 42, '2025-12-01 14:15:00', '2025-12-15 11:30:00', 'In attesa', 'Monolocale da valutare', NULL),
    (28, 21, 45, '2025-12-01 16:00:00', NULL, 'In attesa', 'Ancora da fissare appuntamento', NULL),
    
    -- Richieste IN ELABORAZIONE (prese in carico da agenti) - 12 richieste
    (5, 2, 3, '2025-10-10 11:00:00', '2025-10-25 16:00:00', 'In elaborazione', 'Villa da valutare per vendita', NULL),
    (6, 5, 5, '2025-11-01 10:00:00', '2025-11-18 15:30:00', 'In elaborazione', 'Appartamento zona Nizza', NULL),
    (7, 6, 6, '2025-11-05 14:30:00', '2025-11-20 10:00:00', 'In elaborazione', 'Crocetta, 4 locali', NULL),
    (8, 7, 8, '2025-11-10 09:00:00', '2025-11-25 11:00:00', 'In elaborazione', 'Monolocale studenti', NULL),
    (9, 7, 9, '2025-11-12 15:00:00', '2025-11-27 14:00:00', 'In elaborazione', 'Appartamento Crocetta', NULL),
    (10, 11, 16, '2025-11-08 10:30:00', '2025-11-22 16:00:00', 'In elaborazione', 'Novara centro', NULL),
    (29, 16, 30, '2025-10-15 10:00:00', '2025-11-02 14:00:00', 'In elaborazione', 'Attico Piazza Castello', NULL),
    (30, 18, 31, '2025-10-20 11:30:00', '2025-11-05 10:30:00', 'In elaborazione', 'Villa prestigiosa collina', NULL),
    (31, 20, 35, '2025-11-10 14:00:00', '2025-11-28 16:00:00', 'In elaborazione', 'Trilocale Corso Casale', NULL),
    (32, 22, 38, '2025-11-15 09:30:00', '2025-12-03 11:00:00', 'In elaborazione', 'Quadrilocale centro', NULL),
    (33, 15, 41, '2025-10-25 15:00:00', '2025-11-10 10:00:00', 'In elaborazione', 'Villa Asti', NULL),
    (34, 17, 43, '2025-11-18 10:00:00', '2025-12-05 14:30:00', 'In elaborazione', 'Trilocale Cuneo', NULL),
    
    -- Richieste COMPLETATA (valutazioni concluse) - 6 richieste
    (11, 9, 11, '2025-09-15 10:00:00', '2025-09-30 15:00:00', 'Completata', 'Casa indipendente zona Po', NULL),
    (12, 10, 14, '2025-09-20 11:00:00', '2025-10-05 10:00:00', 'Completata', 'Villa collina', NULL),
    (35, 23, 46, '2025-08-10 09:00:00', '2025-08-25 14:00:00', 'Completata', 'Villa Biella', NULL),
    (36, 24, 47, '2025-08-15 10:30:00', '2025-09-01 11:00:00', 'Completata', 'Villa lago Maggiore', NULL),
    (37, 25, 48, '2025-09-05 14:00:00', '2025-09-20 15:30:00', 'Completata', 'Appartamento Verbania', NULL),
    (38, 6, 36, '2025-09-10 11:00:00', '2025-09-25 10:00:00', 'Completata', 'Villa Torino centro', NULL),
    
    -- Richieste ANNULLATA (vari motivi) - 4 richieste
    (13, 8, 10, '2025-10-01 14:00:00', '2025-10-15 11:00:00', 'Annullata', 'Appartamento Crocetta', 'Cliente ha ritirato richiesta'),
    (39, 11, 15, '2025-10-05 09:00:00', '2025-10-20 14:00:00', 'Annullata', 'Monolocale Aurora', 'Immobile già venduto privatamente'),
    (40, 12, 18, '2025-10-10 15:30:00', '2025-10-25 10:30:00', 'Annullata', 'Bilocale Asti', 'Cliente non più interessato'),
    (41, 19, 40, '2025-10-12 11:00:00', NULL, 'Annullata', 'Trilocale Novara', 'Appuntamento mai fissato - timeout');

-- ========================
-- SAMPLE: Contratti (collegati a richieste in elaborazione e completate - 22 contratti)
-- ========================
INSERT IGNORE INTO Contratti (idContratto, idImmobile, idAgente, tipoContratto, dataContratto, dataScadenzaContratto, pathContrattoPDF)
VALUES
    -- Contratti per richieste IN_ELABORAZIONE (agente Sofia Costa - id 4)
    (1, 3, 4, 'VENDITA', '2025-10-10', '2026-10-10', '/uploads/contratti/contratto_1.pdf'),
    (2, 5, 4, 'VENDITA', '2025-11-01', '2026-11-01', '/uploads/contratti/contratto_2.pdf'),
    (3, 6, 4, 'VENDITA', '2025-11-05', '2026-11-05', '/uploads/contratti/contratto_3.pdf'),
    (4, 8, 4, 'VENDITA', '2025-11-10', '2026-11-10', '/uploads/contratti/contratto_4.pdf'),
    (5, 9, 4, 'VENDITA', '2025-11-12', '2026-11-12', '/uploads/contratti/contratto_5.pdf'),
    (15, 30, 4, 'VENDITA', '2025-10-15', '2026-10-15', '/uploads/contratti/contratto_15.pdf'),
    (16, 31, 4, 'VENDITA', '2025-10-20', '2026-10-20', '/uploads/contratti/contratto_16.pdf'),
    
    -- Contratti per richieste IN_ELABORAZIONE (agente Davide Esposito - id 13)
    (6, 16, 13, 'VENDITA', '2025-11-08', '2026-11-08', '/uploads/contratti/contratto_6.pdf'),
    (17, 35, 13, 'AFFITTO', '2025-11-10', '2027-11-10', '/uploads/contratti/contratto_17.pdf'),
    (18, 38, 13, 'VENDITA', '2025-11-15', '2026-11-15', '/uploads/contratti/contratto_18.pdf'),
    
    -- Contratti per richieste IN_ELABORAZIONE (agente Valentina Greco - id 14)
    (19, 41, 14, 'VENDITA', '2025-10-25', '2026-10-25', '/uploads/contratti/contratto_19.pdf'),
    (20, 43, 14, 'AFFITTO', '2025-11-18', '2027-11-18', '/uploads/contratti/contratto_20.pdf'),
    
    -- Contratti per richieste COMPLETATA (agente Sofia Costa - id 4)
    (7, 11, 4, 'VENDITA', '2025-09-15', '2026-09-15', '/uploads/contratti/contratto_7.pdf'),
    (8, 14, 4, 'VENDITA', '2025-09-20', '2026-09-20', '/uploads/contratti/contratto_8.pdf'),
    (13, 46, 4, 'VENDITA', '2025-08-10', '2026-08-10', '/uploads/contratti/contratto_13.pdf'),
    (14, 36, 4, 'VENDITA', '2025-09-10', '2026-09-10', '/uploads/contratti/contratto_14.pdf'),
    
    -- Contratti per richieste COMPLETATA (agente Marco Ferri - id 26)
    (21, 47, 26, 'VENDITA', '2025-08-15', '2026-08-15', '/uploads/contratti/contratto_21.pdf'),
    (22, 48, 26, 'AFFITTO', '2025-09-05', '2027-09-05', '/uploads/contratti/contratto_22.pdf'),
    
    -- Contratti COMODATO ed ESCLUSIVO per diversità
    (9, 32, 27, 'COMODATO', '2025-10-01', '2027-10-01', '/uploads/contratti/contratto_9.pdf'),
    (10, 33, 27, 'ESCLUSIVO', '2025-10-05', '2026-04-05', '/uploads/contratti/contratto_10.pdf'),
    (11, 34, 28, 'VENDITA', '2025-11-01', '2026-11-01', '/uploads/contratti/contratto_11.pdf'),
    (12, 37, 28, 'altro', '2025-11-05', '2026-05-05', '/uploads/contratti/contratto_12.pdf');


-- ========================
-- SAMPLE: Vendite (per contratti completati - 10 vendite)
-- ========================
INSERT IGNORE INTO Vendite (idVendita, idContratto, idImmobile, idUtente, commissionePercentuale)
VALUES
    (1, 7, 11, 9, 3.00),
    (2, 8, 14, 10, 3.50),
    (3, 13, 46, 23, 3.20),
    (4, 14, 36, 6, 3.80),
    (5, 21, 47, 21, 4.00),
    (6, 1, 3, 2, 3.00),
    (7, 2, 5, 5, 3.50),
    (8, 3, 6, 6, 3.20),
    (9, 15, 30, 16, 4.20),
    (10, 16, 31, 18, 4.50);

-- ========================
-- SAMPLE: Leads (potenziali clienti - 15 leads)
-- ========================
INSERT IGNORE INTO Leads (idLead, idUtente, nome_completo, email, telefono, citta, fonte, convertitoInRichiesta, idRichiesta, assegnatoA, note, createdAt)
VALUES
    -- Leads convertiti in richieste
    (1, 1, 'Mario Rossi', 'mario.rossi@example.com', '3331234567', 'Torino', 'Cliente Esistente', TRUE, 1, 4, 'Cliente convertito a richiesta', '2025-11-10 11:00:00'),
    (2, 5, 'Giulia Ferrari', 'giulia.ferrari@example.com', '3334567890', 'Torino', 'Referral', TRUE, 2, 4, 'Convertito dopo contatto telefonico', '2025-11-18 09:00:00'),
    (3, 10, 'Roberto Martini', 'roberto.martini@example.com', '3339012345', 'Torino', 'Sito Web', TRUE, 3, 13, 'Lead da form sito web', '2025-11-22 14:30:00'),
    
    -- Leads non ancora convertiti - assegnati
    (4, NULL, 'Giulia Neri', 'giulia.neri@example.com', '3451234567', 'Torino', 'Sito Web', FALSE, NULL, 4, 'Interessata a bilocale zona Crocetta', '2025-11-20 10:00:00'),
    (5, NULL, 'Marco Leone', 'marco.leone@example.com', '3462345678', 'Novara', 'Landing Page', FALSE, NULL, 13, 'Cerca trilocale con giardino', '2025-11-22 14:30:00'),
    (6, NULL, 'Stefania Piras', 'stefania.piras@example.com', '3473456789', 'Torino', 'Referral', FALSE, NULL, 4, 'Referral da cliente Mario Rossi', '2025-11-24 09:15:00'),
    (7, NULL, 'Andrea Fabbri', 'andrea.fabbri@example.com', '3484567890', 'Alessandria', 'Google Ads', FALSE, NULL, 14, 'Primo contatto, cerca info mercato', '2025-11-26 16:20:00'),
    (8, NULL, 'Lucia Belli', 'lucia.belli@example.com', '3495678901', 'Asti', 'Facebook Ads', FALSE, NULL, 14, 'Interessata a villa budget 500k', '2025-11-27 10:45:00'),
    (9, NULL, 'Tommaso Farina', 'tommaso.farina@example.com', '3401234567', 'Torino', 'Instagram', FALSE, NULL, 26, 'Giovane coppia cerca prima casa', '2025-11-28 15:00:00'),
    (10, NULL, 'Elisa Marino', 'elisa.marino@example.com', '3412345678', 'Cuneo', 'Sito Web', FALSE, NULL, 27, 'Cerca monolocale per investimento', '2025-11-29 11:30:00'),
    
    -- Leads non assegnati (in attesa)
    (11, NULL, 'Francesco Volpe', 'francesco.volpe@example.com', '3423456789', 'Verbania', 'Sito Web', FALSE, NULL, NULL, 'Interessato a immobili vista lago', '2025-11-30 09:00:00'),
    (12, NULL, 'Valentina Sanna', 'valentina.sanna@example.com', '3434567890', 'Biella', 'Google Ads', FALSE, NULL, NULL, 'Cerca appartamento per trasferimento lavoro', '2025-12-01 14:00:00'),
    (13, NULL, 'Simone Pala', 'simone.pala@example.com', '3445678901', 'Vercelli', 'Landing Page', FALSE, NULL, NULL, 'Primo acquisto casa, necessita consulenza', '2025-12-01 16:30:00'),
    (14, NULL, 'Chiara De Santis', 'chiara.desantis@example.com', '3456789012', 'Torino', 'Referral', FALSE, NULL, NULL, 'Referral da agente esterno', '2025-12-02 10:00:00'),
    (15, NULL, 'Mattia Caruso', 'mattia.caruso@example.com', '3467890123', 'Novara', 'Email Marketing', FALSE, NULL, NULL, 'Risposta a campagna email novembre', '2025-12-02 11:45:00');

-- ========================
-- SAMPLE: EmailLog (storico invii email - 12 logs)
-- ========================
INSERT IGNORE INTO EmailLog (idEmail, destinatario, subject, template, variablesJson, status, attempts, providerResponse, createdAt, updatedAt)
VALUES
    (1, 'mario.rossi@example.com', 'Conferma richiesta valutazione', 'richiesta_conferma', '{"nome":"Mario","cognome":"Rossi","idRichiesta":1}', 'SENT', 1, 'Message accepted', '2025-11-15 10:35:00', '2025-11-15 10:35:30'),
    (2, 'giulia.ferrari@example.com', 'Conferma richiesta valutazione', 'richiesta_conferma', '{"nome":"Giulia","cognome":"Ferrari","idRichiesta":2}', 'SENT', 1, 'Message accepted', '2025-11-20 14:05:00', '2025-11-20 14:05:25'),
    (3, 'sofia.costa@example.com', 'Nuova richiesta assegnata', 'agente_assegnazione', '{"nomeAgente":"Sofia","idRichiesta":5}', 'SENT', 1, 'Message accepted', '2025-10-10 11:10:00', '2025-10-10 11:10:15'),
    (4, 'luca.bianchi@example.com', 'Valutazione completata', 'valutazione_completata', '{"nome":"Luca","valutazione":890000}', 'SENT', 1, 'Message accepted', '2025-10-25 17:00:00', '2025-10-25 17:00:20'),
    (5, 'francesca.galli@example.com', 'Conferma appuntamento', 'appuntamento_conferma', '{"nome":"Francesca","data":"2025-09-30","ora":"15:00"}', 'SENT', 1, 'Message accepted', '2025-09-20 10:00:00', '2025-09-20 10:00:18'),
    (6, 'roberto.martini@example.com', 'Promemoria appuntamento', 'appuntamento_reminder', '{"nome":"Roberto","data":"2025-10-05","ora":"10:00"}', 'SENT', 1, 'Message accepted', '2025-10-04 09:00:00', '2025-10-04 09:00:22'),
    (7, 'paolo.romano@example.com', 'Richiesta annullata', 'richiesta_annullamento', '{"nome":"Paolo","motivo":"Cliente ha ritirato richiesta"}', 'SENT', 1, 'Message accepted', '2025-10-15 11:30:00', '2025-10-15 11:30:28'),
    (8, 'giulia.neri@example.com', 'Benvenuto - Nuovo Lead', 'lead_benvenuto', '{"nome":"Giulia","fonte":"Sito Web"}', 'SENT', 1, 'Message accepted', '2025-11-20 10:05:00', '2025-11-20 10:05:19'),
    (9, 'andrea.fabbri@example.com', 'Newsletter mensile', 'newsletter_mensile', '{"mese":"Novembre","anno":2025}', 'FAILED', 2, 'Invalid email address', '2025-11-26 18:00:00', '2025-11-26 18:05:45'),
    (10, 'davide.esposito@example.com', 'Report mensile vendite', 'report_agente', '{"nomeAgente":"Davide","mese":"Ottobre","vendite":3}', 'SENT', 1, 'Message accepted', '2025-11-01 08:00:00', '2025-11-01 08:00:35'),
    (11, 'anna.verdi@example.com', 'Report amministratore', 'report_admin', '{"totaleRichieste":30,"totaleContratti":22}', 'PENDING', 0, NULL, '2025-12-02 07:00:00', '2025-12-02 07:00:00'),
    (12, 'marco.leone@example.com', 'Follow-up Lead', 'lead_followup', '{"nome":"Marco","giorni":5}', 'SENT', 1, 'Message accepted', '2025-11-27 10:00:00', '2025-11-27 10:00:24');
    
-- Log di completamento
SELECT 'Database inizializzato con successo!' AS Status;