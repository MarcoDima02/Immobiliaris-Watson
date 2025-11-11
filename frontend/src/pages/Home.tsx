/**
 * Immages
 */
import { heroAltAvif, heroAltWebp, heroAltJpg } from '@/assets';

/**
 * Components
 */
import { Button } from '@/components/ui/button';
import Functionality from '@/components/Functionality';

/**
 * Icons
 */
import { File, Euro, UserRound } from 'lucide-react';

const Home = () => {
  return (
    <>
    <div className='container grid grid-cols-2 items-center mx-auto h-[50vh] '>
       <div className="z-10 w-full max-w-3xl xl:max-w[1700px] bg-linear-to-b ">
        <h1 className="title mx-auto lg:mx-0">Vuoi vendere casa senza stress?</h1>
        <h2 className="title-secondary mx-auto lg:mx-0">
          Con noi, il tuo immobile trova presto il suo nuovo proprietario.
        </h2>
        <div className="flex">
          <Button
            size="lg"
            className="text-md lg:text-lg"
          >
            Valuta ora
          </Button>
        </div>
      </div>

      <div className="relative w-full overflow-hidden">
        <figure className="w-full h-full">
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
              className=""
            />
          </picture>

          {/* <div className="absolute inset-0 pointer-events-none bg-linear-to-t from-background via-transparent "></div> */}
          <div className="absolute inset-0 pointer-events-none bg-linear-to-r from-background from-0% via-transparent"></div>
        </figure>
      </div>

     

      </div>

      

      <section className="container-fluid mt-8 flex flex-col justify-center items-center text-center ">
        <h2 className="title">Come funziona</h2>
        <h3 className="title-secondary">
          Ricevi in tre step la valutazione del tuo immobile!
        </h3>

        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6 mt-8">
          <Functionality
            icon={File}
            desc={
              <>
                <strong>Compila il form</strong> con i dati dell’abitazione in
                pochi minuti
              </>
            }
          />
          <Functionality
            icon={Euro}
            desc={
              <>
                <strong>Ricevi una fascia di valutazione</strong> in base ai
                tuoi dati
              </>
            }
          />
          <Functionality
            icon={UserRound}
            desc={
              <>
                <strong>Consulenza</strong> e valutazione precisa da parte di
                uno <strong>dei nostri agenti</strong>
              </>
            }
          />
        </div>
      </section>

      <section className='container-fluid mt-32'>
            <h2 className="title">Chi siamo</h2>
            <h3 className='title-secondary'>Perchè scegliere Immobiliaris</h3>
      </section>
    </>
  );
};

export default Home;
