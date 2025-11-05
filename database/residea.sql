-- ========================
-- TABELLA: Utente
-- ========================
CREATE TABLE Utente (
    idUtente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cognome VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    ruolo ENUM('proprietario', 'utente') DEFAULT 'utente',
    verifica_email BOOLEAN DEFAULT FALSE,
    consenso_privacy BOOLEAN DEFAULT FALSE
);

-- ========================
-- TABELLA: Immobile
-- ========================
CREATE TABLE Immobile (
    idImmobile INT AUTO_INCREMENT PRIMARY KEY,
    idProprietario INT NOT NULL,
    tipologia ENUM('Appartamento', 'Villa', 'Terratetto', 'Monolocale', 'Bilocale', 'Trilocale', 'Quadrilocale', 'Attico'),
    indirizzo VARCHAR(255),
    citta VARCHAR(100),
    provincia VARCHAR(100),
    cap INT(10),
    latitudine DECIMAL(10, 8),
    longitudine DECIMAL(11, 8),
    stato VARCHAR(50),
    FOREIGN KEY (idProprietario) REFERENCES Utente(idUtente) ON DELETE CASCADE
);

-- ========================
-- TABELLA: DettagliImmobile
-- ========================
CREATE TABLE DettagliImmobile (
    idImmobile INT PRIMARY KEY,
    nLocali INT,
    nCamere INT,
    nBagni INT,
    nPiano INT,
    balconeTerrazzo BOOLEAN,
    giardino BOOLEAN,
    garage BOOLEAN,
    ascensore BOOLEAN,
    cantina BOOLEAN,
    tipoRiscaldamento ENUM('No','Autonomo','Condominiale','Pompe di calore','Pavimento') DEFAULT 'No',
    annoCostruzione YEAR,
    esposizioneSolare BOOLEAN,
    condizioneImmobile ENUM('Nuovo', 'Ristrutturato','Parzialmente ristrutturato','Non ristrutturato'),
    classeEnergetica VARCHAR(50),
    prezzo DECIMAL(15, 2),
    FOREIGN KEY (idImmobile) REFERENCES Immobile(idImmobile) ON DELETE CASCADE
);

-- ========================
-- TABELLA: Immagine
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
CREATE TABLE Prenotazione (
    idPrenotazione INT AUTO_INCREMENT PRIMARY KEY,
    idUtente INT NOT NULL,
    idImmobile INT NOT NULL,
    dataPrenotazione DATETIME NOT NULL,
    dataAppuntamento DATETIME,
    stato VARCHAR(50),
    noteUtente TEXT,
    motivoAnnullamento TEXT,
    FOREIGN KEY (idUtente) REFERENCES Utente(idUtente) ON DELETE CASCADE,
    FOREIGN KEY (idImmobile) REFERENCES Immobile(idImmobile) ON DELETE CASCADE
);

-- ========================
-- TABELLA: Leads
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
    FOREIGN KEY (idUtente) REFERENCES Utente(idUtente) ON DELETE SET NULL
);

-- ========================
-- TABELLA: Superfici
-- ========================
CREATE TABLE Superfici (
    idImmobile INT PRIMARY KEY,
    superficieMq DECIMAL(10, 2),
    superficieBalconeTerrazzo DECIMAL(10, 2),
    superficieGiardino DECIMAL(10, 2),
    superficieGarage DECIMAL(10, 2),
    superficieCantina DECIMAL(10, 2),
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

-- ========================
-- TABELLA: Amministrazione
-- ========================
CREATE TABLE Amministrazione (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    cognome VARCHAR(100),
    telefono VARCHAR(20),
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    ruolo ENUM('agente', 'amministratore') DEFAULT 'agente'
);
