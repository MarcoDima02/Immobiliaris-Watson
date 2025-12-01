/**
 * Node modules
 */
import { useLocation } from 'react-router';

/**
 * Components
 */
import { Button } from '@/components/ui/button';
import AgentRequestDiv from '@/components/agentDashboard/AgentRequestDiv';

/**
 * Store
 */
import { useAuthStore } from '@/store/auth.store';

const AgentMyRequests = () => {
  const location = useLocation();
  const { requests: stateRequests } = location.state || {};


  const allRequests = useAuthStore((s) => s.agentDashboard) || [];
  const requests = stateRequests ?? allRequests;

  if (requests === null) return <p>Caricamento dashboard...</p>;
  if (requests.length === 0) return <p>Nessun contratto trovato</p>;

  return (
    <>
      <Button onClick={() => history.back()}>Indietro</Button>
      <h2 className="text-2xl font-bold mt-5">Richieste:</h2>
      <div className="flex gap-4 flex-wrap py-4">
        {requests.map((request: any) => (
          <>
            <AgentRequestDiv
              key={Number(request.idContratto)}
              request={request}
            />
          </>
        ))}
      </div>
    </>
  );
};

export default AgentMyRequests;
