/**
 * Node modules
 */
import { useEffect } from 'react';

/**
 * Store
 */
import { useAuthStore } from '@/store/auth.store';
import AgentRequestContainer from '@/components/agentDashboard/AgentRequestContainer';

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
    <>
      <div>
        <h2 className="text-2xl font-bold">Bentornat* {user?.nome}</h2>
        <h3 className="font-medium">Ecco le tue richieste:</h3>
        <AgentRequestContainer requests={dashboard} />
      </div>
    </>
  );
};

export default AgentDashboard;
