/**
 * Components
 */
import AgentRequest from './AgentRequest';

/**
 * Types
 */
import type { AgenteRichiestaDTO } from '@/types';

// stato: 'IN_ATTESA' | 'IN_ELABORAZIONE' | 'COMPLETATA' | 'ANNULLATA' | null;

function AgentRequestContainer({
  requests,
}: {
  requests: AgenteRichiestaDTO[];
}) {
  const preseInCarico = requests.filter(
    (request) => request.statoRichiesta === 'IN_ELABORAZIONE'
  );
  const completate = requests.filter(
    (request) => request.statoRichiesta === 'COMPLETATA'
  );
  const inAttesa = requests.filter(
    (request) => request.statoRichiesta === 'IN_ATTESA'
  );

  const annullate = requests.filter(
    (request) => request.statoRichiesta === 'ANNULLATA'
  );

  return (
    <div className="flex gap-5 py-4 flex-wrap">
      <AgentRequest
        num={preseInCarico.length}
        requests={preseInCarico}
        type='default'
      >
        In elaborazione
      </AgentRequest>

      <AgentRequest
        num={completate.length}
        type="completato"
        requests={completate}
      >
        Completate con successo
      </AgentRequest>

      <AgentRequest
        num={inAttesa.length}
        type="in_attesa"
        requests={inAttesa}
      >
        In attesa
      </AgentRequest>

      <AgentRequest
        num={annullate.length}
        type="annullato"
        requests={annullate}
      >
        Annullate
      </AgentRequest>
    </div>
  );
}

export default AgentRequestContainer;
