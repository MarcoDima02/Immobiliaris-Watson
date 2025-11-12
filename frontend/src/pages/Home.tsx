/**
 * Immages
 */
import { heroAltAvif, heroAltWebp, heroAltJpg, about } from '@/assets';

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
      <div className="p-5 container max-w-3xl lg:max-w-[1400px]  grid lg:grid-cols-2 items-center mx-auto h-[35vh] lg:h-[55vh]">
        <div className="z-10 w-full h-full flex flex-col justify-center">
          <h1 className="title mx-auto lg:mx-0 text-center lg:text-start mt-8">
            Vuoi vendere casa senza stress?
          </h1>
          <h2 className="title-secondary mx-auto lg:mx-0 text-center lg:text-start">
            Con noi, il tuo immobile trova presto il suo nuovo proprietario.
          </h2>
          <div className="flex justify-center lg:justify-start">
            <Button
              size="lg"
              className="text-md lg:text-lg"
            >
              Valuta ora
            </Button>
          </div>
        </div>

        <div className="w-full overflow-hidden">
          <figure className="w-full absolute top-0 right-0 h-[40vh] md:h-[42vh] lg:h-[55vh]">
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
      </div>

      <section className="container mx-auto mt-8 flex flex-col justify-center items-center text-center ">
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

      <section className="container mt-32 mx-auto flex flex-col justify-center items-center">
        <h2 className="title">Chi siamo</h2>
        <h3 className="title-secondary">Perchè scegliere Immobiliaris</h3>
        <div>
          <figure>
            <img
              src={about}
              alt=""
              className="mx-auto"
            />
          </figure>

          <div>
            <p>
              Immobiliaris è una giovane realtà nata per rivoluzionare il modo
              in cui si vendono e si valutano gli immobili.
            </p>

            <p>
              Uniamo la professionalità dei nostri agenti all’efficienza delle
              nuove tecnologie digitali.
            </p>

            <p>
              Il nostro obiettivo è rendere la valutazione immobiliare semplice,
              rapida e trasparente, offrendo ai clienti un servizio innovativo
              che combina dati, consulenza e fiducia.
            </p>
          </div>
        </div>
      </section>
    </>
  );
};

export default Home;
