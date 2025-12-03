/**
 * Node modules
 */
import { useState } from 'react';
import { Link } from 'react-router';

/**
 * Components
 */
import { Button } from '@/components/ui/button';
import { toast } from 'sonner';

/**
 * Store
 */
import { useAuthStore } from '@/store/auth.store';

/**
 * Types
 */
import type { AgenteRichiestaDTO } from '@/types';

interface AgentRequestDivProps {
  request: AgenteRichiestaDTO;
  key: number;
}

function AgentRequestDiv({ request, key }: AgentRequestDivProps) {
  const { user } = useAuthStore();
  const [taken, setTaken] = useState(
    !!request.statoRichiesta && request.statoRichiesta !== 'IN_ATTESA'
  );

  const handleTakeOver = async () => {
    if (!user) return;

    try {
      const response = await fetch(
        `http://localhost:8080/api/agente/${user.idUtente}/richieste/${request.idRichiesta}/prendi-in-carico`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
        }
      );

      if (!response.ok) {
        const text = await response.text();
        throw new Error(text || 'Errore nella presa in carico');
      }

      toast.success('Richiesta presa in carico con successo!');
      setTaken(true);
    } catch (error) {
      console.error(error);
      toast.error('Errore durante la presa in carico');
    }
  };

  return (
    <div
      key={key}
      className="bg-white p-4 rounded-xl shadow-md text-black"
    >
      <div className="grid flex-col flex-wrap grid-cols-2 gap-15">
        <div>
          <p className="font-bold">Nome del cliente:</p>
          <p className="text-zinc-600">
            {request.nomeUtente} {request.cognomeUtente}
          </p>
        </div>
        <div>
          <p className="font-bold">Data:</p>
          <p className="text-zinc-600">
            {request.dataRichiesta?.slice(0, 10).replace(/-/g, '/')} alle{' '}
            {request.dataRichiesta?.slice(11, 16)}
          </p>
        </div>
      </div>

      <div className="my-4 flex flex-col text-zinc-600">
        <p className="font-bold text-black">Dettagli dell'immobile:</p>
        <p>{request.nStanze} stanze</p>
        <p>{request.nBagni} bagni</p>
        <p>{request.superficieMq} metri quadri</p>
      </div>

      <p className="mt-4 font-bold">
        Stato:{' '}
        <span className="text-primary">
          {request.statoRichiesta?.replace(/_/g, ' ')}
        </span>
      </p>
      <Link
        to="/backoffice/agent/request"
        state={{ request }}
      >
        <Button className="mt-4 w-full">Visualizza i dettagli</Button>
      </Link>

        <Button
        variant="outline"
        className="mt-2 w-full"
        onClick={handleTakeOver}
        disabled={taken} 
      >
        {taken ? 'Richiesta presa in carico' : 'Prendi in carico'}
      </Button>
    </div>
  );
}
export default AgentRequestDiv;
