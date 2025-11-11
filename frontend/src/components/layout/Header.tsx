/**
 * Node modules
 */
import { useState, useEffect } from 'react';

/**
 * Components
 */
import LinkItem from '@/components/LinkItem';

/**
 * Helper
 */
import { cn } from '@/lib/utils';

/**
 * Constants
 */
import { links } from '@/constants';

/**
 * Icons
 */
import { Menu } from 'lucide-react';

const Header = () => {
  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth >= 768) setIsOpen(false);
    };
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);
  return (
    <header className="fixed top-0 left-0 w-full bg-background/50 z-50 transition-all duration-150 backdrop-blur-lg">
      <nav className="relative container-fluid flex items-center justify-between h-[72px]">
        {/* Logo */}
        <div className="font-semibold text-lg text-primary">Logo</div>

        {/* Hamburger (solo mobile) */}
        <div className="md:hidden">
          <Menu
            strokeWidth={3}
            className="cursor-pointer text-primary/80 hover:text-primary transition-colors duration-150"
            onClick={() => setIsOpen((prev) => !prev)}
          />
        </div>

        {/* Navigation */}
        <ul
          className={cn(
            'text-center flex flex-col md:flex-row gap-6 md:gap-8 fixed md:static top-16 md:top-0 left-0 md:left-auto w-full md:w-auto shadow-md md:shadow-none py-6 md:py-0 items-center md:items-center justify-center md:justify-end transform -translate-y-full md:translate-y-0 opacity-0 md:opacity-100 pointer-events-none md:pointer-events-auto z-40',
            isOpen
              ? 'translate-y-0 opacity-100 pointer-events-auto transition-all duration-300 ease-in-out backdrop-blur-xl bg-background/95'
              : 'transition-none'
          )}
        >
          {links.map((link) => (
            <li
              key={link.label}
              className="not-[last-of-type]:mt-4"
            >
              <LinkItem
                setIsOpen={setIsOpen}
                {...link}
              />
            </li>
          ))}
        </ul>
      </nav>
    </header>
  );
};

export default Header;
