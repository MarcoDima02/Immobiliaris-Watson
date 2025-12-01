/**
 * Node modules
 */
import { Link } from 'react-router';

interface AgentRequestProps {
  children: React.ReactNode;
  num: number;
  type?: 'completato' | 'archiviato' | 'annullato';
  requests?: any[];
}

const colorByType = {
  default: 'text-primary',
  completato: 'text-secondary',
  archiviato: 'text-neutral-400',
  annullato: 'text-red-500',
};

function AgentRequest({ children, num, type, requests }: AgentRequestProps) {
  const color = colorByType[type ?? 'default'];

  const content = (
    <div className="bg-white rounded-2xl shadow-md flex flex-col pb-3 pt-6 px-6 gap-5 w-64 opacity-100">
      <p className={`font-extrabold ${color} text-6xl`}>{num}</p>
      <p className="text-neutral-400">{children}</p>
    </div>
  );

  if (num === 0) {
    return <div className="cursor-not-allowed opacity-40">{content}</div>;
  }

  return (
    <Link
      to="/backoffice/agent/myRequests"
      state={{ requests }}
      className="hover:scale-[1.02] transition-all"
    >
      {content}
    </Link>
  );
}

export default AgentRequest;
