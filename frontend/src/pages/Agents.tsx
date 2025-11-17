/**
 * Images
 */
import { agent1, agent2, agent3, agent4 } from '@/assets';

/**
 * Components
 */
import AgentCard from '@/components/AgentCard';

interface AgentsProps {
  id: string;
}

const Agents = ({ id }: AgentsProps) => {
  return (
    <section
      id={id}
      className="py-24 my-12 mx-auto container max-w-xl lg:max-w-[1400px]"
    >
      <div className="flex flex-col items-center mx-auto text-center pb-8">
        <h2 className="title">I nostri agenti</h2>
        <h3 className="title-secondary">
          Esperti del settore al tuo fianco in ogni passo della compravendita.
        </h3>
      </div>

      <div className="grid grid-cols-[repeat(auto-fit,minmax(200px,1fr))] gap-10 p-12">
        <AgentCard
          agentName="Anna Abate"
          agentDescription="Specialista in compravendite residenziali a Torino."
          agentImage={agent1}
          soldProperties={150}
        />
        <AgentCard
          agentName="Marco Bianchi"
          agentDescription="Esperto di immobili di lusso e consulenze d'investimento a Milano."
          agentImage={agent2}
          soldProperties={210}
        />
        <AgentCard
          agentName="Lucia Ferri"
          agentDescription="Professionista nella gestione di locazioni a Roma e dintorni."
          agentImage={agent3}
          soldProperties={175}
        />
        <AgentCard
          agentName="Rita Rinaldi"
          agentDescription="Agente con esperienza in immobili commerciali a Firenze."
          agentImage={agent4}
          soldProperties={120}
        />
      </div>
    </section>
  );
};

export default Agents;
