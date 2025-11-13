import { heroAltAvif, heroAltWebp, heroAltJpg } from '@/assets';

/**
 * Components
 */
import { Button } from '@/components/ui/button';

interface HeroProps {
  id: string;
}

const Hero = ({ id }: HeroProps) => {
  return (
    <section
      id={id}
      className="p-5 md:px-12 container max-w-xl lg:max-w-[1400px] grid lg:grid-cols-2 items-center mx-auto h-[35vh] lg:h-[55vh]"
    >
      <div className="z-10 w-full h-full flex flex-col justify-center">
        <h1 className="title mx-auto lg:mx-0 text-center lg:text-start mt-8">
          Vuoi vendere casa senza stress?
        </h1>
        <h2 className="title-secondary mx-auto lg:mx-0 text-center lg:text-start">
          Con noi, il tuo immobile trova presto il suo nuovo proprietario.
        </h2>
        <div className="flex justify-center lg:justify-start">
          <Button
          asChild
            size="lg"
            className="text-md lg:text-lg"
          >
            <a href="#valutazione">Valuta ora</a>
          </Button>
        </div>
      </div>

      <div className="w-full overflow-hidden">
        <figure className="w-full absolute top-0 right-0 h-[50vh] md:h-[42vh] lg:h-[55vh]">
          <picture>
            <source
              srcSet={heroAltAvif}
              type="image/avif"
            />
            <source
              srcSet={heroAltWebp}
              type="image/webp"
            />
            <img
              src={heroAltJpg}
              alt="Immagine di un appartamento"
              loading="lazy"
              className="object-cover w-full h-full"
            />
          </picture>

          <div className="absolute inset-0 pointer-events-none bg-background/75 lg:bg-background/30"></div>
          <div className="absolute inset-0 pointer-events-none bg-linear-to-r from-background lg:from-40%  via-transparent lg:to-10%"></div>
        </figure>
      </div>
    </section>
  );
};

export default Hero;
