/**
 * Node modules
 */
import { useState, useEffect } from 'react';
import { Link, useLocation } from 'react-router';

/**
 * Assets
 */
import { logoWhite } from '@/assets';

/**
 * Icons
 */
import { LayoutDashboard, LogOut, PanelRight } from 'lucide-react';
import { FaRegQuestionCircle } from 'react-icons/fa';

/**
 * Store
 */
import { useAuthStore } from '@/store/auth.store';

const menu = [
    {
        label: 'Utenti',
        to: '/backoffice/admin/dashboard',
        icon: <LayoutDashboard className="w-5 h-5" />,
        match: 'start',
    },
    {
        label: 'Richieste',
        to: '/backoffice/admin/richieste',
        icon: <FaRegQuestionCircle className="w-5 h-5" />,
        match: 'exact',
    },
    {
        label: 'Contratti',
        to: '/backoffice/admin/contratti',
        icon: <FaRegQuestionCircle className="w-5 h-5" />,
        match: 'exact',
    },
    {
        label: 'Logout',
        to: '#',
        icon: <LogOut className="w-5 h-5" />,
        match: 'exact',
    },
];

function AdminNavbar() {
    const location = useLocation();
    const pathname = location.pathname;

    const [isOpen, setIsOpen] = useState(true);
    const [isMobile, setIsMobile] = useState(false);

    const { logout } = useAuthStore();

    useEffect(() => {
        const handleResize = () => {
            setIsMobile(window.innerWidth < 1024);
        };

        handleResize();
        window.addEventListener('resize', handleResize);

        return () => window.removeEventListener('resize', handleResize);
    }, []);

    useEffect(() => {
        if (isMobile) {
            setIsOpen(false);
        } else {
            setIsOpen(true);
        }
    }, [pathname, isMobile]);

    return (
        <nav
            className={`
    fixed left-0 top-0 h-full bg-primary flex flex-col shadow-xl 
    transition-all duration-300 z-50
  `}
            style={{ width: isOpen ? '240px' : '72px' }}
            role="navigation"
            aria-label="Barra laterale principale"
        >
            <div
                className={`
    flex items-center border-b border-primary/50 py-4 px-2
    ${isOpen ? 'justify-between' : 'justify-center'}
  `}
            >
                {/* LOGO */}
                <div
                    className={`
    flex items-center overflow-hidden
    ${isOpen
                            ? 'transition-all duration-300 max-w-[140px] opacity-100'
                            : 'max-w-0 opacity-0'
                        }
  `}
                >
                    <img
                        src={logoWhite}
                        alt="Immobiliaris"
                        className="w-32"
                    />
                </div>

                {/* TOGGLE BUTTON */}
                <button
                    onClick={() => setIsOpen(!isOpen)}
                    className="p-2 rounded hover:bg-white/10 transition-all"
                    aria-label={isOpen ? 'Chiudi sidebar' : 'Apri sidebar'}
                >
                    <PanelRight className="w-5 h-5 text-white cursor-pointer" />
                </button>
            </div>

            {/* MENU */}
            <ul className="flex flex-col mt-4 bg-secondary">
                {menu.map((item) => {
                    const isActive =
                        item.to !== '#' &&
                        (item.match === 'exact'
                            ? pathname === item.to
                            : pathname === item.to ||
                            pathname.startsWith(item.to + '/') ||
                            pathname.startsWith(item.to));

                    const baseClass = 'flex items-center px-4 py-3 transition-all';
                    const activeClass = isActive ? 'bg-card' : 'hover:bg-card/50';

                    const MenuContent = (
                        <>
                            <div className="shrink-0 w-5 flex justify-center">
                                {item.icon}
                            </div>

                            <div
                                className={`
    ml-2 overflow-hidden 
    ${isOpen
                                        ? 'transition-all duration-300 max-w-[200px] opacity-100 translate-x-0'
                                        : 'max-w-0 opacity-0 -translate-x-2'
                                    } 
  `}
                            >
                                <span className="whitespace-nowrap block">{item.label}</span>
                            </div>
                        </>
                    );

                    return (
                        <li key={item.label}>
                            {item.to === '#' ? (
                                <div
                                    className={`
                ${baseClass} ${activeClass} 
                w-full flex items-center cursor-pointer
                ${isOpen ? 'gap-3' : 'justify-center'}
              `}
                                    aria-current={isActive ? 'page' : undefined}
                                    role="button"
                                    onClick={logout}
                                >
                                    {MenuContent}
                                </div>
                            ) : (
                                <Link
                                    to={item.to}
                                    className={`
                ${baseClass} ${activeClass} 
                w-full flex items-center 
                ${isOpen ? 'gap-3' : 'justify-center'}
              `}
                                    aria-current={isActive ? 'page' : undefined}
                                >
                                    {MenuContent}
                                </Link>
                            )}
                        </li>
                    );
                })}
            </ul>
        </nav>
    );
}
export default AdminNavbar;
