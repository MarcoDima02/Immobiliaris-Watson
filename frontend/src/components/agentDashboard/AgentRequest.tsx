/**
 * Node modules
 */
import { Link } from 'react-router';

interface AgentRequestProps {
  children: React.ReactNode;
  num: number;
  type?: 'completato' | 'in_attesa' | 'annullato' | 'default';
  requests?: any[];
}

const colorByType = {
  default: 'text-primary',
  completato: 'text-secondary',
  in_attesa: 'text-neutral-400',
  annullato: 'text-red-500',
};

const filterByType = {
  default: 'in_elaborazione',
  completato: 'completate',
  in_attesa: 'in_attesa',
  annullato: 'annullate',
};

function AgentRequest({
  children,
  num,
  type = 'default',
  requests,
}: AgentRequestProps) {
  const color = colorByType[type ?? 'default'];
   const filter = filterByType[type];

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
      to={`/backoffice/agent/myRequests/${filter}`}
      state={{ requests }}
      className="hover:scale-[1.02] transition-all"
    >
      {content}
    </Link>
  );
}

export default AgentRequest;
