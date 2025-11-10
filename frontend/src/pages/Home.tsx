/**
 * Immages
 */
import { heroAltAvif, heroAltWebp, heroAltJpg } from '@/assets';

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
              className="w-full h-full object-cover scale-x-[-1]
          md:mask-[linear-gradient(to_right,white_0%,transparent_100%)]
          md:[-webkit-mask-image:linear-gradient(to_right,white_0%,transparent_90%)]
          opacity-20 md:opacity-80 lg:object-top"
            />
          </picture>

          <div className="absolute inset-0 pointer-events-none bg-linear-to-t from-background via-transparent "></div>
          <div className="absolute inset-0 pointer-events-none bg-linear-to-r from-background via-transparent"></div>
        </figure>
      </section>

      <div className="absolute top-[20%] z-10 left-0 sm:left-16 md:left-[90px] lg:left-[120px]  xl:left-[325px] p-8 rounded-xl bg-linear-to-b ">
        <h1 className="text-black text-3xl font-semibold mb-3 max-w-[20ch] lg:max-w-[35ch]">
          Vuoi vendere casa senza stress?
        </h1>
        <h2 className="font-semibold mb-4 max-w-[30ch] md:max-w-[45ch]">
          Con noi, il tuo immobile trova presto il suo nuovo proprietario.
        </h2>
        <div className="flex">
          <Button className="">Valuta ora</Button>
        </div>
      </div>
    </>
  );
};

export default Home;
