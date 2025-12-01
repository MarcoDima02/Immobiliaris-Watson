/**
 * Node modules
 */
import { useEffect } from 'react';

/**
 * Store
 */
import { useAuthStore } from '@/store/auth.store';

const AgentDashboard = () => {
  const dashboard = useAuthStore((s) => s.agentDashboard);
  const loadDashboard = useAuthStore((s) => s.loadAgentDashboard);
  const user = useAuthStore((s) => s.user);

  useEffect(() => {
    if (user?.ruolo === 'AGENTE') {
      // Forza sempre il reload dei dati
      loadDashboard();
    }
  }, [user]);

  if (dashboard === null) return <p>Caricamento dashboard...</p>;
  if (dashboard.length === 0) return <p>Nessun contratto trovato</p>;

  return (
    <div className="container mx-auto p-6">
      <h1 className="text-3xl font-bold mb-6">Dashboard Agente</h1>
      <p className="mb-4 text-gray-600">Totale contratti: {dashboard.length}</p>

      <div className="grid gap-6">
        {dashboard.map((d) => (
          <div
            key={d.idContratto}
            className="bg-white border border-gray-200 rounded-lg shadow-md p-6"
          >
            {/* Intestazione Contratto */}
            <div className="border-b pb-4 mb-4">
              <h2 className="text-2xl font-bold text-blue-600">
                Contratto #{d.idContratto} - {d.tipoContratto}
              </h2>
              <p className="text-sm text-gray-500 mt-1">
                Data: {new Date(d.dataContratto || '').toLocaleDateString('it-IT')} - 
                Scadenza: {new Date(d.dataScadenzaContratto || '').toLocaleDateString('it-IT')}
              </p>
              <p className="text-sm text-gray-500">
                Stato Richiesta: <span className={`font-semibold ${
                  d.statoRichiesta === 'COMPLETATA' ? 'text-green-600' :
                  d.statoRichiesta === 'IN_ELABORAZIONE' ? 'text-yellow-600' :
                  d.statoRichiesta === 'ANNULLATA' ? 'text-red-600' : 'text-gray-600'
                }`}>{d.statoRichiesta}</span>
              </p>
            </div>

            <div className="grid md:grid-cols-2 gap-6">
              {/* Colonna Sinistra: Immobile */}
              <div>
                <h3 className="text-lg font-semibold mb-3 text-gray-700">📍 Immobile</h3>
                <div className="space-y-2 text-sm">
                  <p><strong>Tipologia:</strong> {d.tipologia}</p>
                  <p><strong>Indirizzo:</strong> {d.indirizzo}</p>
                  <p><strong>Città:</strong> {d.citta} ({d.provincia}) - CAP {d.cap}</p>
                  <p><strong>Stato:</strong> {d.stato}</p>
                  <p><strong>Anno Costruzione:</strong> {d.annoCostruzione}</p>
                  <p><strong>Condizione:</strong> {d.condizioneImmobile}</p>
                  <p><strong>Classe Energetica:</strong> {d.classeEnergetica}</p>
                  <p><strong>Riscaldamento:</strong> {d.tipoRiscaldamento}</p>
                </div>

                <h4 className="text-md font-semibold mt-4 mb-2 text-gray-700">📐 Dettagli</h4>
                <div className="space-y-1 text-sm">
                  <p><strong>Stanze:</strong> {d.nstanze} | <strong>Bagni:</strong> {d.nbagni}</p>
                  <p><strong>Piano:</strong> {d.npiano} / {d.npianiImmobile}</p>
                  <p>
                    {d.balconeTerrazzo && '✓ Balcone/Terrazzo '}
                    {d.giardino && '✓ Giardino '}
                    {d.garage && '✓ Garage '}
                    {d.ascensore && '✓ Ascensore '}
                    {d.cantina && '✓ Cantina'}
                  </p>
                </div>

                <h4 className="text-md font-semibold mt-4 mb-2 text-gray-700">📏 Superfici</h4>
                <div className="space-y-1 text-sm">
                  <p><strong>Totale:</strong> {d.superficieMq} m²</p>
                  {d.superficieBalconeTerrazzo ? <p>Balcone/Terrazzo: {d.superficieBalconeTerrazzo} m²</p> : null}
                  {d.superficieGiardino ? <p>Giardino: {d.superficieGiardino} m²</p> : null}
                  {d.superficieGarage ? <p>Garage: {d.superficieGarage} m²</p> : null}
                  {d.superficieCantina ? <p>Cantina: {d.superficieCantina} m²</p> : null}
                </div>
              </div>

              {/* Colonna Destra: Cliente e Valutazione */}
              <div>
                <h3 className="text-lg font-semibold mb-3 text-gray-700">👤 Cliente</h3>
                <div className="space-y-2 text-sm">
                  <p><strong>Nome:</strong> {d.nomeUtente} {d.cognomeUtente}</p>
                  <p><strong>Email:</strong> {d.emailUtente}</p>
                  <p><strong>Telefono:</strong> {d.telefonoUtente}</p>
                </div>

                <h3 className="text-lg font-semibold mt-4 mb-3 text-gray-700">📋 Richiesta</h3>
                <div className="space-y-2 text-sm">
                  <p><strong>ID:</strong> #{d.idRichiesta}</p>
                  <p><strong>Data Richiesta:</strong> {new Date(d.dataRichiesta || '').toLocaleString('it-IT')}</p>
                  {d.dataAppuntamento && (
                    <p><strong>Appuntamento:</strong> {new Date(d.dataAppuntamento).toLocaleString('it-IT')}</p>
                  )}
                  {d.noteUtente && (
                    <p><strong>Note:</strong> {d.noteUtente}</p>
                  )}
                  {d.motivoAnnullamento && (
                    <p className="text-red-600"><strong>Motivo Annullamento:</strong> {d.motivoAnnullamento}</p>
                  )}
                </div>

                {d.idValutazione && (
                  <>
                    <h3 className="text-lg font-semibold mt-4 mb-3 text-gray-700">💰 Valutazione</h3>
                    <div className="space-y-2 text-sm bg-green-50 p-3 rounded">
                      <p className="text-xl font-bold text-green-700">
                        €{d.valoreMedio?.toLocaleString('it-IT')}
                      </p>
                      <p><strong>Range:</strong> €{d.valoreMin?.toLocaleString('it-IT')} - €{d.valoreMax?.toLocaleString('it-IT')}</p>
                      <p><strong>Valore Base:</strong> €{d.valoreBase?.toLocaleString('it-IT')}</p>
                      <p><strong>Fattore Aggiustamento:</strong> {d.fattoreAggiustamento?.toFixed(2)}</p>
                      <p><strong>Confidenza:</strong> {((d.confidence || 0) * 100).toFixed(0)}%</p>
                    </div>
                  </>
                )}

                {d.pathContrattoPDF && (
                  <div className="mt-4">
                    <a 
                      href={`http://localhost:8080${d.pathContrattoPDF}`}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="inline-block bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                    >
                      📄 Scarica Contratto PDF
                    </a>
                  </div>
                )}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default AgentDashboard;
