-- Seed data per tabella PrezzoPerCAP (Torino) - dati di esempio
-- ATTENZIONE: questi valori sono dati di test/seed e vanno verificati e sostituiti con dati ufficiali/aggiornati
-- Inserire con: mysql -u user -p database_name < prezzo_per_cap_torino.sql

-- Seed per tabella Citta (usata come riferimento per PrezzoPerCAP)
-- NOTA: idCitta è autoincrement; assegniamo qui valori fissi per il seed
INSERT INTO Citta (idCitta, nome, provincia, regione, codice_istat)
VALUES
  (1, 'Torino', 'TO', 'Piemonte', '001272'),
  (2, 'Alessandria', 'AL', 'Piemonte', '006001'),
  (3, 'Asti', 'AT', 'Piemonte', '005002'),
  (4, 'Biella', 'BI', 'Piemonte', '096004'),
  (5, 'Cuneo', 'CN', 'Piemonte', '004006'),
  (6, 'Novara', 'NO', 'Piemonte', '003007'),
  (7, 'Verbania', 'VB', 'Piemonte', '103008'),
  (8, 'Vercelli', 'VC', 'Piemonte', '002009')
ON DUPLICATE KEY UPDATE
  nome = VALUES(nome),
  provincia = VALUES(provincia),
  regione = VALUES(regione),
  codice_istat = VALUES(codice_istat);

-- Seed data per tabella PrezzoPerCAP (collegata a Citta via idCitta)
INSERT INTO PrezzoPerCAP (cap, idCitta, prezzo_mq, fonte, valid_from, valid_to, quality_score, updated_at)
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
ON DUPLICATE KEY UPDATE
  prezzo_mq = VALUES(prezzo_mq),
  quality_score = VALUES(quality_score),
  updated_at = CURRENT_TIMESTAMP,
  fonte = VALUES(fonte),
  valid_from = VALUES(valid_from),
  idCitta = VALUES(idCitta);

-- Fine seed
