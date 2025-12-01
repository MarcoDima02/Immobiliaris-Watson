/**
 * Node modules
 */
import { Link, useLocation } from 'react-router';

/**
 * Assets
 */
import { logoWhite } from '@/assets';

/**
 * Icons
 */
import { Home, LayoutDashboard, Settings } from 'lucide-react';
import { FaMoneyBillWave, FaRegQuestionCircle } from 'react-icons/fa';

const menu = [
  {
    label: 'Dashboard',
    to: '/backoffice/agent/dashboard',
    icon: <LayoutDashboard className="w-5 h-5" />,
    match: 'start',
  },
  {
    label: 'Richieste',
    to: '/backoffice/agent/myRequests',
    icon: <FaRegQuestionCircle className="w-5 h-5" />,
    match: 'exact',
  },
  {
    label: 'Immobili',
    to: '#',
    icon: <Home className="w-5 h-5" />,
    match: 'exact',
  },
  {
    label: 'Acquisizioni',
    to: '#',
    icon: <FaMoneyBillWave className="w-5 h-5" />,
    match: 'exact',
  },
  {
    label: 'Impostazioni',
    to: '#',
    icon: <Settings className="w-5 h-5" />,
    match: 'exact',
  },
];

function AgentNavbar() {
  const location = useLocation();
  const pathname = location.pathname;
  return (
    <nav
      className="fixed left-0 top-0 h-full w-14 md:w-60 bg-primary flex flex-col shadow-xl"
      role="navigation"
      aria-label="Barra laterale principale"
    >
      {/* LOGO */}
      <div className="flex items-center justify-center border-b border-primary/50 py-4">
        <img
          src={logoWhite}
          alt="Immobiliaris"
          className="hidden md:block w-32"
        />
      </div>

      {/* MENU */}
      <ul className="flex flex-col mt-4 bg-secondary">
        {menu.map((item) => {
          const isActive =
            item.to !== '#' &&
            (item.match === 'exact'
              ? pathname === item.to
              : // 'start'
                pathname === item.to ||
                pathname.startsWith(item.to + '/') ||
                pathname.startsWith(item.to));

          const baseClass =
            'flex items-center gap-3 px-4 py-3 transition-all ';

          const activeClass = isActive ? 'bg-card' : 'hover:bg-card/50';

          return (
            <li key={item.label}>
              {item.to === '#' ? (
                <div
                  className={`${baseClass} ${activeClass}`}
                  aria-current={isActive ? 'page' : undefined}
                  role="button"
                >
                  {item.icon}
                  <span className="hidden md:block">{item.label}</span>
                </div>
              ) : (
                <Link
                  to={item.to}
                  className={`${baseClass} ${activeClass}`}
                  aria-current={isActive ? 'page' : undefined}
                >
                  {item.icon}
                  <span className="hidden md:block">{item.label}</span>
                </Link>
              )}
            </li>
          );
        })}
      </ul>
    </nav>
  );
}
export default AgentNavbar;
