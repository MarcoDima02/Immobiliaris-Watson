/**
 * Pages
 */
import OurServices from './OurServices';

/**
 * Immages
 */
import { heroAltAvif, heroAltWebp, heroAltJpg, about } from '@/assets';

/**
 * Components
 */
import { Button } from '@/components/ui/button';

const Home = () => {
  return (
    <>
      <section
        id="home"
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
      </section>

      <OurServices id='come-funziona'/>
      
      <section id="valutazione" className="min-h-[50vh]">ciao</section>

      <section id="agenti" className="min-h-[50vh]">
        ciao
      </section>


      <section
        id="chi-siamo"
        className="container mt-32 mx-auto flex flex-col justify-center items-center"
      >
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
