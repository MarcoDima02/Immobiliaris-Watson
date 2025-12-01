import { Button } from '@/components/ui/button';
import AgentRequestDiv from '@/components/agentDashboard/AgentRequestDiv';
import { useLocation } from 'react-router';

const AgentMyRequests = () => {
  const location = useLocation();
  const { requests } = location.state || {};

    const safeRequests = Array.isArray(requests) ? requests : [];

  if (requests === null) return <p>Caricamento dashboard...</p>;
  if (safeRequests.length === 0) return <p>Nessun contratto trovato</p>;

  return (
    <>
      <Button onClick={() => history.back()}>Indietro</Button>
      <h2 className="text-2xl font-bold mt-5">Richieste prese in carico:</h2>
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
