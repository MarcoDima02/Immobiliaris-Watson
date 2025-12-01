/**
 * Node modules
 */
import { useEffect } from 'react';

/**
 * Store
 */
import { useAuthStore } from '@/store/auth.store';
import { Button } from '@/components/ui/button';
import AgentRequestDiv from '@/components/agentDashboard/AgentRequestDiv';

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
  console.log(dashboard);
  return (
    <>
      <Button>Indietro</Button>
      <h2 className="text-2xl font-bold mt-5">Richieste prese in carico:</h2>

      <div className="flex gap-4 flex-wrap py-4">
        {dashboard.map((request) => (
          <>
            <AgentRequestDiv key={Number(request.idContratto)} request={request} />
          </>
        ))}

      </div>
    </>
  );
};

export default AgentDashboard;
