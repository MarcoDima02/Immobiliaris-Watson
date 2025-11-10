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
import { Euro, File, UserRound } from 'lucide-react';

const Home = () => {
  return (
    <>
      <section className="relative w-full h-[60vh] overflow-hidden">
        <figure className="relative w-full h-full">
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
              className="
          w-full h-full object-cover scale-x-[-1]
          md:mask-[linear-gradient(to_right,white_0%,transparent_100%)]
          md:[-webkit-mask-image:linear-gradient(to_right,white_0%,transparent_90%)]
          opacity-20 md:opacity-80 lg:object-top
        "
            />
          </picture>
        </figure>

        <div className="absolute top-[25%] z-10 left-1/2 lg:left-50 transform -translate-x-1/2 lg:translate-x-0 px-4 sm:px-6 w-full max-w-sm sm:max-w-md md:max-w-lg p-8 rounded-xl text-center lg:text-left">
          <h1 className="text-5xl font-bold text-zinc mb-3 max-w-[20ch] md:max-w-[35ch]">
            Vuoi vendere casa senza stress?
          </h1>
          <h2 className="text-lg font-semibold mb-4 max-w-[30ch] md:max-w-[45ch]">
            Con noi, il tuo immobile trova presto il suo nuovo proprietario.
          </h2>
          <div className="flex justify-center lg:justify-start">
            <Button>Valuta ora</Button>
          </div>
        </div>
      </section>

      <section className='py-25 container mx-auto'>

        <div className="flex justify-center flex-col items-center gap-10">

          <div className='text-center'>
            <h2 className='text-4xl font-bold text-zinc'>Come funziona</h2>
            <h3 className='text-lg font-semibold text-zinc'>Ricevi in tre step la valutazione del tuo immobile!</h3>
          </div>

          <div className='row flex flex-row flex-wrap justify-center items-start gap-10 lg:gap-0'>

            <div className='flex lg:flex-col w-full lg:w-[33%] justify-center items-center px-10 md:px-25 lg:px-10 xl:px-25 gap-10 '>
              <div>
                <div className='bg-card rounded-full p-3 shadow-xl w-20 h-20 flex justify-center items-center'><File className='h-10 w-10 text-foreground' /></div>
              </div>
              <div className=' text-left text-lg font-medium text-zinc'>
                <p><b>Compila il form</b> con i dati dell’abitazione in pochi minuti</p>
              </div>
            </div>

            <div className='flex lg:flex-col w-full lg:w-[33%] justify-center items-center px-10 md:px-25 lg:px-10 xl:px-25 gap-10 align-center'>
              <div>
                <div className='bg-card rounded-full p-3 shadow-xl w-20 h-20 flex justify-center items-center'><Euro className='h-10 w-10 text-foreground' /></div>
              </div>
              <div className=' text-left text-lg font-medium text-zinc'>
                <p><b>Ricevi una fascia di valutazione</b> in base ai tuoi dati</p>
              </div>
            </div>

            <div className='flex lg:flex-col  w-full lg:w-[33%] justify-center items-center px-10 md:px-25 lg:px-10 xl:px-25 gap-10 '>
              <div>
                <div className='bg-card rounded-full p-3 shadow-xl w-20 h-20 flex justify-center items-center'><UserRound className='h-10 w-10 text-foreground' /></div>
              </div>
              <div className=' text-left text-lg font-medium text-zinc'>
                <p><b>Consulenza</b> e valutazione precisa da parte di uno <b>dei nostri agenti</b></p>
              </div>
            </div>

          </div>

          <div className='mx-auto'><Button>Valuta ora</Button></div>
        </div>

      </section>


    </>
  );
};

export default Home;
