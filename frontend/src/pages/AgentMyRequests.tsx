/**
 * Node modules
 */
import { useEffect } from 'react';
import { useParams } from 'react-router';

/**
 * Components
 */
import { Button } from '@/components/ui/button';
import AgentRequestDiv from '@/components/agentDashboard/AgentRequestDiv';

/**
 * Store
 */
import { useAuthStore } from '@/store/auth.store';

const mapFilterToStatuses = {
  tutte: null,
  in_elaborazione: ['IN_ELABORAZIONE'],
  completate: ['COMPLETATA'],
  in_attesa: ['IN_ATTESA'],
  annullate: ['ANNULLATA'],
};

const AgentMyRequests = () => {
  const { agentDashboard, loadAgentDashboard } = useAuthStore();
  const { filter = 'tutte' } = useParams();

  useEffect(() => {
    loadAgentDashboard();
  }, []);

  if (agentDashboard === null) return <p>Caricamento dashboard...</p>;
  if (agentDashboard.length === 0) return <p>Nessun contratto trovato</p>;

  const statuses =
    mapFilterToStatuses[filter as keyof typeof mapFilterToStatuses];

  const filtered = statuses
    ? agentDashboard.filter((req: any) => statuses.includes(req.statoRichiesta))
    : agentDashboard;

  console.log(
    filtered.map((req) => ({
      id: req.idContratto,
      stato: req.statoRichiesta,
      min: req.valoreMin,
      max: req.valoreMax,
      medio: req.valoreMedio,
    }))
  );

  return (
    <>
      <Button onClick={() => history.back()}>Indietro</Button>

      <h2 className="text-2xl font-bold mt-5">
        Richieste – {filter?.replace('_', ' ')}
      </h2>

      <div className="flex gap-4 flex-wrap py-4">
        {filtered.length === 0 ? (
          <p>Nessuna richiesta con questo filtro.</p>
        ) : (
          filtered.map((request: any) => (
            <AgentRequestDiv
              key={Number(request.idContratto)}
              request={request}
            />
          ))
        )}
      </div>
    </>
  );
};

export default AgentMyRequests;
