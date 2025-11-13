/**
 * Node modules
 */
import { useState, useEffect } from 'react';

/**
 * Hooks
 */
import { useScrollSpy } from '@/hooks/useScrollSpy';

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
import { Menu, X } from 'lucide-react';

const HEADER_OFFSET = 72;

const Header = () => {
  const [isOpen, setIsOpen] = useState(false);

  const sectionIds = links.map((link) => link.href.slice(1));
  const activeId = useScrollSpy(sectionIds, HEADER_OFFSET);

  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth >= 768) setIsOpen(false);
    };
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  const handleLinkClick = (
    event: React.MouseEvent<HTMLAnchorElement>,
    href: string
  ) => {
    const isHashLink = href.startsWith('#');
    if (!isHashLink) return;

    event.preventDefault();
    setIsOpen(false);

    const targetId = href.slice(1);
    const target = document.getElementById(targetId);

    if (target) {
      const y = target.getBoundingClientRect().top + window.scrollY;
      window.history.pushState(null, '', href);
      window.scrollTo({ top: y, behavior: 'smooth' });
    }
  };

  return (
    <header className="fixed top-0 left-0 w-full bg-background/50 z-50 transition-all duration-150 backdrop-blur-lg">
      <nav className="relative container px-5 md:px-12 md:mx-auto max-w-3xl lg:max-w-[1400px] flex items-center justify-between h-[72px]">
        {/* Logo */}
        <div className="font-semibold text-lg text-primary flex items-center">
          Logo
        </div>

        {/* Hamburger (mobile) */}
        <button
          onClick={() => setIsOpen((prev) => !prev)}
          className="md:hidden p-2 text-primary/80 hover:text-primary transition-colors cursor-pointer"
          aria-label="Apri menu"
        >
          {isOpen ? <X size={26} /> : <Menu size={26} />}
        </button>

        {/* Navigation */}
        <ul
          className={cn(
            'text-center flex flex-col md:flex-row gap-6 md:gap-8 fixed md:static top-16 md:top-0 left-0 md:left-auto w-full md:w-auto shadow-md md:shadow-none py-6 md:py-0 items-center md:items-center justify-center md:justify-end transform -translate-y-full md:translate-y-0 opacity-0 md:opacity-100 pointer-events-none md:pointer-events-auto z-40',
            isOpen
              ? 'translate-y-0 opacity-100 pointer-events-auto transition-all duration-300 ease-in-out backdrop-blur-xl bg-background/95'
              : 'transition-none'
          )}
        >
         {links.map(link => {
            const id = link.href.slice(1);
            const isActive = id === activeId;
            return (
              <li key={link.label}>
                <a
                  href={link.href}
                  onClick={e => handleLinkClick(e, link.href)}
                  className={cn('relative font-semibold transition-colors duration-200',
                    isActive ? 'text-primary after:content-[""] after:absolute after:-bottom-1 after:left-0 after:w-full after:h-0.5 after:bg-primary font-bold' : 'text-primary/80 hover:text-primary'
                  )}
                >
                  {link.label}
                </a>
              </li>
            );
          })}
        </ul>
      </nav>
    </header>
  );
};

export default Header;
