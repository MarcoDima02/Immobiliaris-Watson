/**
 * Immages
 */
import { heroAvif } from '@/assets';
import { heroWebp } from '@/assets';
import { heroJpg, heroSecondJpg, heroThirdJpg } from '@/assets';

/**
 * Components
 */
import { Button } from '@/components/ui/button';

const Home = () => {
  return (
    <>
      <section className="relative w-full h-[60vh] overflow-hidden">
        <div className="row flex flex-row h-full">
          <div className='bg-background w-[20%] h-full inset-0'></div>
          <figure className="w-[80%] h-full relative">
            <picture>
              <source
                // srcSet={heroAvif}
                type="image/avif"
              />
              <source
                // srcSet={heroWebp}
                type="image/webp"
              />
              <img
                src={heroThirdJpg}
                alt="Immagine di un appartamento"
                loading="lazy"
                className="md:max-h-[60vh] w-full h-full object-cover opacity-40 lg:opacity-100 scale-x-[-1]"
              />
            </picture>
            <div className="absolute inset-0 pointer-events-none bg-linear-to-r from-background to-transparent" />
            {/* <div className="absolute inset-0 pointer-events-none bg-linear-to-t from-background via-transparent to-transparent" /> */}
          </figure>
        </div>
      </section>

      <div className="absolute top-[25%] z-10 left-1/2 lg:left-50 transform -translate-x-1/2 lg:translate-x-0 px-4 sm:px-6 w-full max-w-sm sm:max-w-md md:max-w-lg p-8 rounded-xl text-center lg:text-left">
        <h1 className="text-4xl font-bold text-zinc-800 mb-3 max-w-[20ch] md:max-w-[35ch]">
          Vuoi vendere casa senza stress?
        </h1>
        <h2 className="text-lg font-semibold mb-4 max-w-[30ch] md:max-w-[45ch]">
          Con noi, il tuo immobile trova presto il suo nuovo proprietario.
        </h2>
        <div className="flex justify-center lg:justify-start">
          <Button>Valuta ora</Button>
        </div>
      </div>
    </>
  );
};

export default Home;
