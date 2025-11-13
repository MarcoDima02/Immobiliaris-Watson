/**
 * Componets
 */
import { Card } from '@/components/ui/card';

/**
 * Icons
 */
import { HomeIcon } from 'lucide-react';

/**
 * Helper 
 */
import { cn } from '@/lib/utils';

interface AgentCardProps {
  agentName: string;
  agentDescription: string;
  agentImage: string;
  soldProperties: number;
}

const AgentCard = ({
  agentName,
  agentDescription,
  agentImage,
  soldProperties,
}: AgentCardProps) => {
  return (
 <Card
      className={cn(
        "group relative h-[520px] overflow-hidden rounded-[40px] border-8 border-white shadow-2xl bg-white transition-all duration-500 aspect-9/16 mx-auto"
      )}
    >
      
      <div className="absolute overflow-hidden rounded-4xl">
        <img
          src={agentImage}
          alt={agentName}
          className="w-full  object-cover transition-all duration-700 ease-in-out group-hover:h-full group-hover:scale-105"
        />
      </div>

      
      <div className="absolute inset-0 flex flex-col justify-end p-6 bg-linear-to-t transition-all duration-700 group-hover:from-black/80 group-hover:via-black/30 group-hover:to-transparent">
        <div
          className={cn(
            "transition-all duration-500 group-hover:text-white"
          )}
        >
          <h3 className="text-2xl font-bold mb-1">{agentName}</h3>
          <p className="text-gray-600 text-sm font-medium group-hover:text-gray-200">
            {agentDescription}
          </p>
        </div>

        <div className="flex gap-2 items-center mt-4">
          <HomeIcon className="text-secondary h-5 w-5 group-hover:text-white" />
          <p className="text-secondary font-semibold text-sm group-hover:text-gray-200">
            {soldProperties}+ immobili venduti
          </p>
        </div>
      </div>
    </Card>

  );
};

export default AgentCard;
