import React, { useState, useEffect } from 'react';
import '../styles/AgentDashboard.css';

interface AgenteRichiestaDTO {
  // Contratto
  idContratto: number;
  tipoContratto: string;
  dataContratto: string;
  dataScadenzaContratto: string;
  pathContrattoPDF: string;

  // Immobile
  idImmobile: number;
  tipologia: string;
  indirizzo: string;
  citta: string;
  provincia: string;
  cap: string;
  stato: string;

  // Dettagli Immobile
  nStanze: number;
  nBagni: number;
  nPiano: number;
  nPianiImmobile: number;
  balconeTerrazzo: boolean;
  giardino: boolean;
  garage: boolean;
  ascensore: boolean;
  cantina: boolean;
  tipoRiscaldamento: string;
  annoCostruzione: number;
  condizioneImmobile: string;
  classeEnergetica: string;

  // Superfici
  superficieMq: number;
  superficieBalconeTerrazzo: number;
  superficieGiardino: number;
  superficieGarage: number;
  superficieCantina: number;

  // Richiesta
  idRichiesta: number;
  dataRichiesta: string;
  dataAppuntamento: string;
  statoRichiesta: string;
  noteUtente: string;
  motivoAnnullamento: string;

  // Utente (richiedente)
  idUtente: number;
  nomeUtente: string;
  cognomeUtente: string;
  telefonoUtente: string;
  emailUtente: string;

  // Valutazione Immobile
  idValutazione: number;
  valoreBase: number;
  fattoreAggiustamento: number;
  valoreMedio: number;
  valoreMin: number;
  valoreMax: number;
  confidence: number;
}

export default function AgentDashboard() {
  const [data, setData] = useState<AgenteRichiestaDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [expandedCard, setExpandedCard] = useState<number | null>(null);

  const idAgente = 4; // TODO: Get from auth context

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        setLoading(true);
        const response = await fetch(
          `http://localhost:8080/api/agente/dashboard/${idAgente}`,
          {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json',
            },
          }
        );

        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }

        const result = await response.json();
        setData(result);
        setError(null);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Errore sconosciuto');
        console.error('Errore nel fetching dashboard:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchDashboardData();
  }, []);

  const getStatusBadgeColor = (status: string) => {
    if (!status) return 'badge-neutral';
    const statusLower = status.toLowerCase();
    if (statusLower === 'attesa') return 'badge-warning';
    if (statusLower === 'elaborazione') return 'badge-info';
    if (statusLower === 'completata') return 'badge-success';
    if (statusLower === 'annullata') return 'badge-danger';
    return 'badge-neutral';
  };

  const getEnergyClassColor = (energyClass: string) => {
    if (!energyClass) return 'energy-unknown';
    const classLower = energyClass.toLowerCase();
    if (classLower === 'a') return 'energy-a';
    if (classLower === 'b') return 'energy-b';
    if (classLower === 'c') return 'energy-c';
    if (classLower === 'd') return 'energy-d';
    if (classLower === 'e') return 'energy-e';
    if (classLower === 'f') return 'energy-f';
    if (classLower === 'g') return 'energy-g';
    return 'energy-unknown';
  };

  const formatCurrency = (value: number | null) => {
    if (value === null || value === undefined) return 'N/A';
    return new Intl.NumberFormat('it-IT', {
      style: 'currency',
      currency: 'EUR',
    }).format(value);
  };

  const formatDate = (dateString: string | null) => {
    if (!dateString) return 'N/A';
    return new Intl.DateTimeFormat('it-IT').format(new Date(dateString));
  };

  const totalValue = data.reduce((sum, item) => sum + (item.valoreMedio || 0), 0);
  const totalContracts = data.length;
  const pendingRequests = data.filter(
    (item) => item.statoRichiesta && item.statoRichiesta.toLowerCase() === 'attesa'
  ).length;

  if (loading) {
    return (
      <div className="dashboard-container">
        <div className="loading">
          <div className="spinner"></div>
          <p>Caricamento dashboard...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="dashboard-container">
      <div className="dashboard-header">
        <h1>📊 Dashboard Agente</h1>
        <p className="subtitle">Panoramica dei tuoi contratti e immobili</p>
      </div>

      {error && (
        <div className="alert alert-error">
          <span>❌ Errore nel fetching dashboard: {error}</span>
          <button
            onClick={() => window.location.reload()}
            className="btn-retry"
          >
            Riprova
          </button>
        </div>
      )}

      {!error && (
        <>
          <div className="stats-grid">
            <div className="stat-card">
              <div className="stat-value">{totalContracts}</div>
              <div className="stat-label">Contratti Totali</div>
              <div className="stat-icon">📋</div>
            </div>

            <div className="stat-card">
              <div className="stat-value">{formatCurrency(totalValue)}</div>
              <div className="stat-label">Valore Totale</div>
              <div className="stat-icon">💰</div>
            </div>

            <div className="stat-card">
              <div className="stat-value">{pendingRequests}</div>
              <div className="stat-label">Richieste in Attesa</div>
              <div className="stat-icon">⏳</div>
            </div>
          </div>

          {data.length === 0 ? (
            <div className="empty-state">
              <p>Nessun contratto disponibile</p>
            </div>
          ) : (
            <div className="contracts-grid">
              {data.map((item, index) => (
                <div
                  key={index}
                  className={`contract-card ${
                    expandedCard === index ? 'expanded' : ''
                  }`}
                >
                  <div
                    className="card-header"
                    onClick={() =>
                      setExpandedCard(expandedCard === index ? null : index)
                    }
                  >
                    <div className="header-content">
                      <h3>
                        🏠 {item.tipologia} - {item.citta}
                      </h3>
                      <p className="address">{item.indirizzo}</p>
                      <div className="badges">
                        {item.tipoContratto && (
                          <span className="badge badge-contract">
                            {item.tipoContratto}
                          </span>
                        )}
                        {item.statoRichiesta && (
                          <span
                            className={`badge ${getStatusBadgeColor(
                              item.statoRichiesta
                            )}`}
                          >
                            {item.statoRichiesta}
                          </span>
                        )}
                        {item.classeEnergetica && (
                          <span
                            className={`badge energy-badge ${getEnergyClassColor(
                              item.classeEnergetica
                            )}`}
                          >
                            Classe {item.classeEnergetica}
                          </span>
                        )}
                      </div>
                    </div>
                    <div className="toggle-icon">
                      {expandedCard === index ? '▼' : '▶'}
                    </div>
                  </div>

                  {expandedCard === index && (
                    <div className="card-content">
                      {/* Immobile Info */}
                      <div className="section">
                        <h4>📍 Informazioni Immobile</h4>
                        <div className="info-grid">
                          <div>
                            <strong>Tipologia:</strong> {item.tipologia}
                          </div>
                          <div>
                            <strong>Indirizzo:</strong> {item.indirizzo},{' '}
                            {item.cap} {item.citta} ({item.provincia})
                          </div>
                          <div>
                            <strong>Stato:</strong> {item.stato}
                          </div>
                          <div>
                            <strong>Superficie:</strong> {item.superficieMq}{' '}
                            m²
                          </div>
                        </div>
                      </div>

                      {/* Dettagli Immobile */}
                      <div className="section">
                        <h4>🏢 Dettagli Immobile</h4>
                        <div className="info-grid">
                          <div>
                            <strong>Stanze:</strong> {item.nStanze}
                          </div>
                          <div>
                            <strong>Bagni:</strong> {item.nBagni}
                          </div>
                          <div>
                            <strong>Piano:</strong>{' '}
                            {item.nPiano || 'N/A'}
                          </div>
                          <div>
                            <strong>Piani Immobile:</strong>{' '}
                            {item.nPianiImmobile || 'N/A'}
                          </div>
                          <div>
                            <strong>Anno Costruzione:</strong>{' '}
                            {item.annoCostruzione}
                          </div>
                          <div>
                            <strong>Condizione:</strong>{' '}
                            {item.condizioneImmobile}
                          </div>
                          <div>
                            <strong>Riscaldamento:</strong>{' '}
                            {item.tipoRiscaldamento}
                          </div>
                        </div>

                        <div className="features-grid">
                          {item.balconeTerrazzo && (
                            <span className="feature-tag">🌳 Balcone/Terrazzo</span>
                          )}
                          {item.giardino && (
                            <span className="feature-tag">🌿 Giardino</span>
                          )}
                          {item.garage && (
                            <span className="feature-tag">🚗 Garage</span>
                          )}
                          {item.ascensore && (
                            <span className="feature-tag">🛗 Ascensore</span>
                          )}
                          {item.cantina && (
                            <span className="feature-tag">🏠 Cantina</span>
                          )}
                        </div>
                      </div>

                      {/* Superfici */}
                      <div className="section">
                        <h4>📐 Superfici</h4>
                        <div className="info-grid">
                          <div>
                            <strong>Principale:</strong> {item.superficieMq}{' '}
                            m²
                          </div>
                          {item.superficieBalconeTerrazzo > 0 && (
                            <div>
                              <strong>Balcone/Terrazzo:</strong>{' '}
                              {item.superficieBalconeTerrazzo} m²
                            </div>
                          )}
                          {item.superficieGiardino > 0 && (
                            <div>
                              <strong>Giardino:</strong>{' '}
                              {item.superficieGiardino} m²
                            </div>
                          )}
                          {item.superficieGarage > 0 && (
                            <div>
                              <strong>Garage:</strong> {item.superficieGarage}{' '}
                              m²
                            </div>
                          )}
                          {item.superficieCantina > 0 && (
                            <div>
                              <strong>Cantina:</strong> {item.superficieCantina}{' '}
                              m²
                            </div>
                          )}
                        </div>
                      </div>

                      {/* Valutazione */}
                      {item.idValutazione && (
                        <div className="section">
                          <h4>💎 Valutazione Immobile</h4>
                          <div className="valuation-grid">
                            <div className="valuation-item">
                              <span className="label">Valore Base:</span>
                              <span className="value">
                                {formatCurrency(item.valoreBase)}
                              </span>
                            </div>
                            <div className="valuation-item">
                              <span className="label">Valore Medio:</span>
                              <span className="value highlight">
                                {formatCurrency(item.valoreMedio)}
                              </span>
                            </div>
                            <div className="valuation-item">
                              <span className="label">Valore Min:</span>
                              <span className="value">
                                {formatCurrency(item.valoreMin)}
                              </span>
                            </div>
                            <div className="valuation-item">
                              <span className="label">Valore Max:</span>
                              <span className="value">
                                {formatCurrency(item.valoreMax)}
                              </span>
                            </div>
                            <div className="valuation-item">
                              <span className="label">Fattore Aggiustamento:</span>
                              <span className="value">
                                {item.fattoreAggiustamento?.toFixed(2)}x
                              </span>
                            </div>
                            <div className="valuation-item">
                              <span className="label">Confidence:</span>
                              <span className="value">
                                {(item.confidence * 100)?.toFixed(0)}%
                              </span>
                            </div>
                          </div>
                        </div>
                      )}

                      {/* Contratto */}
                      <div className="section">
                        <h4>📄 Informazioni Contratto</h4>
                        <div className="info-grid">
                          <div>
                            <strong>Tipo:</strong> {item.tipoContratto}
                          </div>
                          <div>
                            <strong>Data Contratto:</strong>{' '}
                            {formatDate(item.dataContratto)}
                          </div>
                          <div>
                            <strong>Data Scadenza:</strong>{' '}
                            {formatDate(item.dataScadenzaContratto)}
                          </div>
                          {item.pathContrattoPDF && (
                            <div>
                              <strong>PDF:</strong>{' '}
                              <a
                                href={item.pathContrattoPDF}
                                target="_blank"
                                rel="noopener noreferrer"
                                className="link"
                              >
                                📎 Visualizza
                              </a>
                            </div>
                          )}
                        </div>
                      </div>

                      {/* Richiesta */}
                      {item.idRichiesta && (
                        <div className="section">
                          <h4>💬 Dati Richiesta</h4>
                          <div className="info-grid">
                            <div>
                              <strong>Data Richiesta:</strong>{' '}
                              {formatDate(item.dataRichiesta)}
                            </div>
                            <div>
                              <strong>Data Appuntamento:</strong>{' '}
                              {formatDate(item.dataAppuntamento)}
                            </div>
                            <div>
                              <strong>Stato:</strong> {item.statoRichiesta}
                            </div>
                            {item.noteUtente && (
                              <div>
                                <strong>Note:</strong> {item.noteUtente}
                              </div>
                            )}
                            {item.motivoAnnullamento && (
                              <div>
                                <strong>Motivo Annullamento:</strong>{' '}
                                {item.motivoAnnullamento}
                              </div>
                            )}
                          </div>
                        </div>
                      )}

                      {/* Richiedente */}
                      {item.idUtente && (
                        <div className="section">
                          <h4>👤 Dati Richiedente</h4>
                          <div className="info-grid">
                            <div>
                              <strong>Nome:</strong> {item.nomeUtente}{' '}
                              {item.cognomeUtente}
                            </div>
                            <div>
                              <strong>Telefono:</strong>{' '}
                              <a href={`tel:${item.telefonoUtente}`}>
                                {item.telefonoUtente}
                              </a>
                            </div>
                            <div>
                              <strong>Email:</strong>{' '}
                              <a href={`mailto:${item.emailUtente}`}>
                                {item.emailUtente}
                              </a>
                            </div>
                          </div>
                        </div>
                      )}
                    </div>
                  )}
                </div>
              ))}
            </div>
          )}
        </>
      )}
    </div>
  );
}
