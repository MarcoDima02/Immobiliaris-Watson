import { heroAltAvif, heroAltWebp, heroAltJpg, hero2Jpg } from '@/assets';

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
      className=""
    >

      <div
        className="relative h-[30vh] xl:h-[80vh] w-full flex justify-start items-start flex-col text-start mx-auto"
      >
        {/* Mirrored background image */}
        <div
          className="absolute inset-0 pointer-events-none opacity-30 md:opacity-50"
          style={{
            backgroundImage: `url(${hero2Jpg})`,
            backgroundSize: 'cover',
            backgroundPosition: 'top',
            transform: 'scaleX(-1)',
            transformOrigin: 'center',
            zIndex: 0
          }}
        />

        <div
          className="absolute inset-0 pointer-events-none h-[80vh]"
          style={{
            background: 'linear-gradient(to bottom, rgba(255,255,255,1), rgba(255,255,255,0))',
            zIndex: 10
          }}
        />

        <div className="container z-20 flex flex-col mx-auto justify-center xl:mt-48 p-10 text-start w-fit md:w-full h-full xl:h-fit">
          <h1 className="title lg:mx-0 mt-8 xl:text-6xl! 2xl:text-7xl! font-extrabold">
            Vuoi <span className='text-primary text-shadow-primary text-shadow-lg/10 '>vendere casa</span><br></br>senza stress?
          </h1>
          <h2 className="title-secondary lg:mx-0 xl:text-2xl! mb-6">
            Con noi, il tuo immobile trova presto il suo nuovo proprietario.
          </h2>
          <div className="flex justify-start">
            <Button
              asChild
              size="lg"
              className="text-md lg:text-lg"
            >
              <a href="#valutazione">Valuta ora</a>
            </Button>
          </div>
        </div>
      </div>



    </section>
  );
};

export default Hero;
