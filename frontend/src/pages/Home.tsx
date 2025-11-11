/**
 * Immages
 */
import { heroAltAvif, heroAltWebp, heroAltJpg, agent1 } from '@/assets';

/**
 * Components
 */
import { Button } from '@/components/ui/button';
import Functionality from '@/components/Functionality';

/**
 * Icons
 */
import { File, Euro, UserRound, HomeIcon } from 'lucide-react';

const Home = () => {
  return (
    <>
      <div className=" relative w-full h-[50vh] overflow-hidden">
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

          {/* <div className="absolute inset-0 pointer-events-none bg-linear-to-t from-background via-transparent "></div> */}
          <div className="absolute inset-0 pointer-events-none bg-linear-to-r from-background via-transparent"></div>
        </figure>
      </div>

      <div className="absolute top-[20%] z-10  px-6 sm:px-8 lg:px-12 w-full max-w-3xl xl:max-w[1700px] left-1/2 -translate-x-1/2  bg-linear-to-b ">
        <h1 className="title">Vuoi vendere casa senza stress?</h1>
        <h2 className="title-secondary">
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

      <section className="container-fluid mt-8 flex flex-col justify-center items-center text-center">
        <h2 className="title">Come funziona</h2>
        <h3 className="title-secondary">
          Ricevi in tre step la valutazione del tuo immobile!
        </h3>

        <div className="grid md:grid-cols-2 xl:grid-cols-3 gap-6 mt-8">
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
                <strong>Consulenza</strong> e valutazione precisa da parte di uno{' '}
                <strong>dei nostri agenti</strong>
              </>
            }
          />
        </div>
      </section>

      <section className='w-full'>
        <div className='flex flex-col items-center mx-auto text-center pb-8'>
          <h2 className='title'>I nostri agenti</h2>
          <h3 className='title-secondary'>Esperti del settore al tuo fianco in ogni passo della compravendita.</h3>
        </div>
        <div className="container-fluid flex flex-nowrap justify-center gap-10 mx-auto">


          <div className='bg-white w-[25%] rounded-[50px]  flex flex-col border-16 border-white shadow-2xl'>
            <div><img src={agent1} alt="" className='h-full w-full rounded-[30px]' /></div>
            <div className='px-4'>
              <h3 className='text-xl font-bold mt-5 text-black'>Cristina Rossi</h3>
              <h4 className='text-light-grey font-medium text-md'>Agente immobiliare in zona Torino</h4>
              <div className='flex gap-2 items-center justify-center mt-18 '>
                <HomeIcon className='text-secondary h-5' />
                <p className='text-secondary font-semibold text-center text-sm'> 200+ immobili venduti</p>
              </div>
            </div>
          </div>

          <div className='bg-white w-[25%] rounded-[50px]  flex flex-col border-16 border-white shadow-2xl'>
            <div><img src={agent1} alt="" className='h-full w-full rounded-[30px]' /></div>
            <div className='px-4'>
              <h3 className='text-xl font-bold mt-5 text-black'>Cristina Rossi</h3>
              <h4 className='text-light-grey font-medium text-md'>Agente immobiliare in zona Torino</h4>
              <div className='flex gap-2 items-center justify-center mt-18 '>
                <HomeIcon className='text-secondary h-5' />
                <p className='text-secondary font-semibold text-center text-sm'> 200+ immobili venduti</p>
              </div>
            </div>
          </div>

          <div className='bg-white w-[25%] rounded-[50px]  flex flex-col border-16 border-white shadow-2xl'>
            <div><img src={agent1} alt="" className='h-full w-full rounded-[30px]' /></div>
            <div className='px-4'>
              <h3 className='text-xl font-bold mt-5 text-black'>Cristina Rossi</h3>
              <h4 className='text-light-grey font-medium text-md'>Agente immobiliare in zona Torino</h4>
              <div className='flex gap-2 items-center justify-center mt-18 '>
                <HomeIcon className='text-secondary h-5' />
                <p className='text-secondary font-semibold text-center text-sm'> 200+ immobili venduti</p>
              </div>
            </div>
          </div>

          <div className='bg-white w-[25%] rounded-[50px]  flex flex-col border-16 border-white shadow-2xl'>
            <div><img src={agent1} alt="" className='h-full w-full rounded-[30px]' /></div>
            <div className='px-4'>
              <h3 className='text-xl font-bold mt-5 text-black'>Cristina Rossi</h3>
              <h4 className='text-light-grey font-medium text-md'>Agente immobiliare in zona Torino</h4>
              <div className='flex gap-2 items-center justify-center mt-18 '>
                <HomeIcon className='text-secondary h-5' />
                <p className='text-secondary font-semibold text-center text-sm'> 200+ immobili venduti</p>
              </div>
            </div>
          </div>

        </div>
      </section>
    </>
  );
};

export default Home;
