import { hero2Avif, hero2Webp, hero2Jpg } from '@/assets';
import { Button } from '@/components/ui/button';

interface HeroProps {
  id: string;
}

const Hero = ({ id }: HeroProps) => {
  return (
    <section id={id} className="relative w-full h-[60vh] lg:h-[80vh]  xl:h-[85vh] overflow-hidden">
      
      {/* Background image con supporto responsive e fallback */}
      <picture className="absolute inset-0 w-full h-full -scale-x-100">
        <source srcSet={hero2Avif} type="image/avif" />
        <source srcSet={hero2Webp} type="image/webp" />
        <img
          src={hero2Jpg}
          alt="Immagine di un appartamento"
          className="w-full h-full object-cover object-top opacity-30 md:opacity-50 xl:opacity-100"
          loading="lazy"
        />
      </picture>

      {/* Overlay leggero per contrasto testo */}
      <div className="absolute inset-0 bg-black/35 z-10 backdrop-blur-sm xl:backdrop-blur-none"></div>

      {/* Contenuto hero */}
      <div className="relative z-20 container mx-auto flex flex-col justify-center items-center xl:items-start xl:justify-start xl:mt-20 h-full px-6 md:px-8 xl:px-20 text-start">
        <h1 className="text-3xl md:text-4xl xl:text-6xl 2xl:text-7xl font-extrabold leading-tight text-white drop-shadow-lg">
          Vuoi{' '}
          <span className="text-primary text-shadow-primary">
            vendere casa
          </span>
          <br />
          senza stress?
        </h1>
        <p className="mt-4 md:mt-6 text-md md:text-lg xl:text-2xl text-white/90 max-w-xl drop-shadow-md">
          Con noi, il tuo immobile trova presto il suo nuovo proprietario.
        </p>
        <div className="mt-6 md:mt-8">
          <Button asChild size="lg" className="text-md lg:text-lg">
            <a href="#valutazione">Valuta ora</a>
          </Button>
        </div>
      </div>
    </section>
  );
};

export default Hero;
