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
    if (!dashboard && user?.ruolo === 'AGENTE') {
      loadDashboard();
    }
  }, [dashboard, user]);

  if (dashboard === null) return <p>Caricamento dashboard...</p>;
  if (dashboard.length === 0) return <p>Nessun contratto trovato</p>;

  return (
    <div>
      <h1 className="text-xl font-bold mb-4">Dashboard Agente </h1>

      {dashboard.map((d) => (
        <div
          key={d.idContratto}
          className="border p-4 rounded mb-2"
        >
          <h2>Contratto {d.contrattoNumero}</h2>
          <p>
            <strong>Immobile:</strong> {d.immobile?.indirizzo}
          </p>
          <p>
            <strong>Richiesta:</strong> {d.richiesta?.stato}
          </p>
          <p>
            <strong>Proprietario:</strong> {d.proprietario?.nome}{' '}
            {d.proprietario?.cognome}
          </p>
        </div>
      ))}
    </div>
  );
};

export default AgentDashboard;
