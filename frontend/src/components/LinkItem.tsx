/**
 * Node modules
 */
import { NavLink, useNavigate, useLocation } from 'react-router';

/**
 * Helper
 */
import { cn } from '@/lib/utils';

/**
 * Types
 */
import type { Dispatch, SetStateAction } from 'react';

type LinkItemProps = {
  to?: string;
  href?: string;
  label: string;
  setIsOpen: Dispatch<SetStateAction<boolean>>;
};

const LinkItem = ({ to, href, label, setIsOpen }: LinkItemProps) => {
  const navigate = useNavigate();
  const location = useLocation();

  const handleAnchorClick = (href: string) => {
    const id = href.replace('#', '');
    setIsOpen(false);

    if (location.pathname === '/') {
      const element = document.getElementById(id);
      if (element) {
        const yOffset = -80; 
        const y =
          element.getBoundingClientRect().top + window.scrollY + yOffset;

        window.scrollTo({ top: y, behavior: 'smooth' });
      }
    } else {
      navigate('/');
      setTimeout(() => {
        const element = document.getElementById(id);
        if (element) {
          const yOffset = -80;
          const y =
            element.getBoundingClientRect().top + window.scrollY + yOffset;

          window.scrollTo({ top: y, behavior: 'smooth' });
        }
      }, 400);
    }
  };

  const baseClasses =
    'relative font-semibold transition-colors duration-150 text-primary/70 hover:text-primary';
  const activeClasses = 'text-primary after:absolute after:bottom-[-4px] after:left-0 after:w-full after:h-[2px] after:bg-primary';

  if (href) {
    
    const isActive = location.hash === href;
    return (
      <button
        onClick={() => handleAnchorClick(href)}
        className={cn(baseClasses, isActive && activeClasses)}
      >
        {label}
      </button>
    );
  }

    return (
    <NavLink
      to={to!}
      onClick={() => setIsOpen(false)}
      className={cn(
        'text-foreground/80 hover:text-primary font-medium transition-colors duration-200'
      )}
    >
      {label}
    </NavLink>
  );
};

export default LinkItem;
