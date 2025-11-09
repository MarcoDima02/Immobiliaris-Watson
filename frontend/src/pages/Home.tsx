/**
 * Immages
 */
import { heroAvif } from '@/assets';
import { heroWebp } from '@/assets';
import { heroJpg } from '@/assets';

/**
 * Components
 */
import { Button } from '@/components/ui/button';

const Home = () => {
  return (
    <>
      <section className="relative w-full h-[50vh] overflow-hidden">
        <figure className="absolute inset-0 w-full h-full">
          <picture>
            <source
              srcSet={heroAvif}
              type="image/avif"
            />
            <source
              srcSet={heroWebp}
              type="image/webp"
            />
            <img
              src={heroJpg}
              alt="Immagine di un appartamento"
              loading="lazy"
              className="md:max-h-[50vh] w-full h-full object-cover"
            />
          </picture>

          <div className="absolute inset-0 pointer-events-none bg-linear-to-t from-background via-transparent "></div>
          <div className="absolute inset-0 pointer-events-none bg-linear-to-r from-background via-transparent"></div>
        </figure>
      </section>

      <div className="absolute top-[20%] z-10 left-4 sm:left-6 lg:left-8 right-4 sm:right-6 lg:right-8 max-w-max mx-auto p-8 bg-background/90 rounded-xl">
        <h1 className="text-3xl font-semibold mb-3 max-w-[20ch] md:max-w-[35ch]">
          Vuoi vendere casa senza stress?
        </h1>
        <h2 className="font-semibold mb-4 max-w-[30ch] md:max-w-[45ch] md:text-center">
          Con noi, il tuo immobile trova presto il suo nuovo proprietario.
        </h2>
        <div className='flex md:justify-center'>
          <Button className="">Valuta ora</Button>
        </div>
      </div>
    </>
  );
};

export default Home;
