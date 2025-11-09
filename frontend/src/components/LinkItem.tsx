/**
 * Node modules
 */
import { NavLink } from 'react-router';

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
  const baseClasses =
    'relative font-semibold text-primary/80 hover:text-primary transition-colors duration-150';

  if (to) {
    return (
      <NavLink
        to={to}
        viewTransition
        onClick={() => setIsOpen(false)}
        className={({ isActive }) =>
          cn(
            baseClasses,
            isActive &&
              'after:absolute after:left-0 after:-bottom-2 after:h-1 after:w-full after:bg-primary after:rounded-lg'
          )
        }
      >
        {label}
      </NavLink>
    );
  }

  if (href) {
    return (
      <a
        href={href}
        className={baseClasses}
        onClick={() => setIsOpen(false)}
      >
        {label}
      </a>
    );
  }
  return null;
};

export default LinkItem;
