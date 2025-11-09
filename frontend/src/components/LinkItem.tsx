/**
 * Node modules
 */
import { NavLink } from 'react-router';

/**
 * Helper
 */
import { cn } from '@/lib/utils';

/**
 * Constants
 */
import { links } from '@/constants';

type LinkItemProps = {
  to?: string;
  href?: string;
  label: string;
};

const LinkItem = ({ to, href, label }: LinkItemProps) => {
  const baseClasses =
    'relative font-semibold text-primary/75 hover:text-primary transition-colors duration-150';

  if (to) {
    return (
      <NavLink
        to={to}
        viewTransition
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
      >
        {label}
      </a>
    );
  }
  return null;
};

export default LinkItem;
