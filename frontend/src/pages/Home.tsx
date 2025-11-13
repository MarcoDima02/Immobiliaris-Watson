/**
 * Immages
 */
import { heroAltAvif, heroAltWebp, heroAltJpg, agent1, about } from '@/assets';

/**
 * Components
 */
import { Button } from '@/components/ui/button';
import Functionality from '@/components/Functionality';

/**
 * Icons
 */
import { File, Euro, UserRound, Sparkle, ChartColumn, UsersRound } from 'lucide-react';
import AgentCard from '@/components/ui/agentCard';

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

        <div className="grid md:grid-cols-2 xl:grid-cols-3 gap-10 mt-8 text-left">
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

      <section className='container mx-auto'>
        <div className='flex flex-col items-center mx-auto text-center pb-8'>
          <h2 className='title'>I nostri agenti</h2>
          <h3 className='title-secondary'>Esperti del settore al tuo fianco in ogni passo della compravendita.</h3>
        </div>


        <div className="container-fluid grid md:grid-cols-2 xl:grid-cols-4 justify-center gap-10 mx-auto">
          <AgentCard agentName="Anna Abate" agentDescription="Agente immobiliare in zona Torino" agentImage={agent1} soldProperties={150} />
          <AgentCard agentName="Anna Abate" agentDescription="Agente immobiliare in zona Torino" agentImage={agent1} soldProperties={150} />
          <AgentCard agentName="Anna Abate" agentDescription="Agente immobiliare in zona Torino" agentImage={agent1} soldProperties={150} />
          <AgentCard agentName="Anna Abate" agentDescription="Agente immobiliare in zona Torino" agentImage={agent1} soldProperties={150} />
        </div>
      </section>

      <section className='container mx-auto'>
        <div className='flex flex-col items-center mx-auto text-center pb-8'>
          <h2 className='title'>Chi siamo</h2>
          <h3 className='title-secondary'>Scopri la storia e i valori di Immobiliaris</h3>
        </div>
        <div className='flex flex-col lg:flex-row lg:items-center lg:justify-center gap-10 mx-auto'>
          <div className='w-full px-8 pb-5 lg:pb-0'>
            <img src={about} alt="About us" className='w-full h-full object-cover rounded-[40px]' />
          </div>
          <div className='flex flex-col justify-center px-8 w-full'>
            <p className='text-lg text-justify mb-4 text-black font-medium '>
              <b>Immobiliaris</b> è una giovane realtà nata per rivoluzionare il modo in cui si vendono e si valutano gli immobili.
              <br /> <br />
              Uniamo la professionalità dei nostri agenti <b>all’efficienza</b> delle nuove tecnologie digitali.</p>

            <div className='bg-foreground rounded-2xl p-6 mt-6 flex justify-center items-center'>
              <p className='text-white'>Il nostro obiettivo è rendere la valutazione immobiliare semplice, rapida e trasparente, offrendo ai clienti un servizio innovativo che combina dati, consulenza e fiducia.</p>
            </div>
          </div>

        </div>
      </section>

      <section className='w-full bg-card'>
        <div className="container px-8 mx-auto">
          <div className='gap-5 grid justify-center lg:grid-cols-2 items-center mx-auto pb-8'>

            <div className='w-full'>
              <h2 className='title'>Vuoi scoprire il vero
                valore della tua casa?</h2>
              <h3 className='font-semibold mb-4  '>Ottieni una valutazione professionale dettagliata <b>entro 72 ore</b> e vendi con il supporto dedicato! </h3>

              <div className='flex flex-col font-extrabold text-black text-lg mt-10 gap-5'>
                <div className='flex justify-start items-center gap-5'><Sparkle /> <span>Stima immediata con tecnologia AI</span></div>
                <div className='flex justify-start items-center gap-5'><ChartColumn /> <span>Dati reali e aggiornati sul tuo quartiere</span></div>
                <div className='flex justify-start items-center gap-5'><UsersRound /> <span>Agente personale disponibile on demand</span></div>
              </div>

              <Button size="lg"
                className="text-md lg:text-lg mt-10 mb-3">
                Inizia gratuitamente
              </Button>
              <p><i>Nessun vincolo. Solo opportunità per valorizzare il tuo immobile.</i></p>

            </div>

            <div className='mx-auto w-full lg:w-2/3'>
              <div className='flex flex-col bg-foreground rounded-2xl text-white p-6 mt-6 w-full shadow-primary shadow-2xl'>
                <h4 className='font-extrabold text-xl'>Stima live</h4>
                <div className='grid grid-cols-2 gap-x-14 gap-y-4 mt-4 text-sm'>
                  <p>Città: </p>
                  <p>Torino</p>
                  <p>Metri quadrati:</p>
                  <p>120 m²</p>
                  <p>Valore medio stimato:</p>
                  <p>€ 250.000</p>
                </div>
                <Button size="lg"
                  className="text-md lg:text-lg bg-background text-primary mt-6">
                  Ottieni valutazione
                </Button>
              </div>
            </div>

          </div>
        </div>

      </section >
    </>
  );
};

export default Home;
