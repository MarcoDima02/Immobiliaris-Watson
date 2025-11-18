/**
 * Node modules
 */
import { Link } from 'react-router';

/**
 * Components
 */
import { Separator } from '@/components/ui/separator';

/**
 * Icons
 */

/**
 * Assets
 */
import { logoWhite } from '@/assets';


import {
  FaFacebookF,
  FaInstagram,
  FaLinkedinIn,
  FaMapMarkerAlt,
  FaPhoneAlt,
  FaEnvelope,
} from 'react-icons/fa';


const Footer = () => {
  return (
    <footer className="bg-primary text-white">
      <div className="container mx-auto px-6 md:px-12 py-12">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-10">
          <div>

            <figure className='text-2xl font-bold mb-3'>
              <picture>
                <img src={logoWhite} alt="Logo Residea" className='w-[120px]' />
              </picture>
            </figure>

            <p className="text-sm text-white/80 leading-relaxed">
              La tua agenzia immobiliare di fiducia. Valutazioni rapide,
              trasparenti e professionali.
            </p>
          </div>

          <div>
            <h4 className="text-lg font-semibold mb-3">Navigazione</h4>
            <ul className="space-y-4 text-sm">
              <li>
                <a
                  href="#chi-siamo"
                  className="hover:text-white/80 transition-colors"
                >
                  Chi siamo
                </a>
              </li>
              <li>
                <a
                  href="#agenti"
                  className="hover:text-white/80 transition-colors"
                >
                  I nostri agenti
                </a>
              </li>
              <li>
                <a
                  href="/valutazione"
                  className="hover:text-white/80 transition-colors"
                >
                  Valuta ora
                </a>
              </li>
              <li>
                <Link
                  to="/backoffice"
                  className="hover:text-primary hover:bg-white hover:font-semibold rounded-md transition-colors font-medium border px-3 py-2"
                  viewTransition
                >
                  Area privata
                </Link>
              </li>
            </ul>
          </div>

          <div>
            <h4 className="text-lg font-semibold mb-3">Contatti</h4>
            <ul className="space-y-3 text-sm">
              <li className="flex items-center gap-2">
                <FaMapMarkerAlt className="text-white/80" />
                <span>Via Roma 123, Milano</span>
              </li>
              <li className="flex items-center gap-2">
                <FaPhoneAlt className="text-white/80" />
                <a
                  href="tel:+39021234567"
                  className="hover:text-white/80 transition-colors"
                >
                  +39 02 123 4567
                </a>
              </li>
              <li className="flex items-center gap-2">
                <FaEnvelope className="text-white/80" />
                <a
                  href="mailto:info@agenzia.it"
                  className="hover:text-white/80 transition-colors"
                >
                  info@agenzia.it
                </a>
              </li>
            </ul>
          </div>

          <div>
            <h4 className="text-lg font-semibold mb-3">Seguici</h4>
            <div className="flex space-x-4 text-xl">
              <a
                href="#"
                className="hover:text-white/80 transition-colors"
              >
                <FaFacebookF />
              </a>
              <a
                href="#"
                className="hover:text-white/80 transition-colors"
              >
                <FaInstagram />
              </a>
              <a
                href="#"
                className="hover:text-white/80 transition-colors"
              >
                <FaLinkedinIn />
              </a>
            </div>
          </div>
        </div>

        <Separator className="my-8 bg-white/20" />

        {/* COPYRIGHT */}
        <div className="flex flex-col md:flex-row items-center justify-between text-sm text-white/70">
          <p>
            © {new Date().getFullYear()} Agenzia Immobiliare. Tutti i diritti
            riservati.
          </p>
          <div className="flex gap-4 mt-2 md:mt-0">
            <a
              href="#"
              className="hover:text-white/90 transition-colors"
            >
              Privacy Policy
            </a>
            <a
              href="#"
              className="hover:text-white/90 transition-colors"
            >
              Termini e Condizioni
            </a>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
