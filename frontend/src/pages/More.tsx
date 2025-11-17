/**
 * Components
 */
import { Button } from '@/components/ui/button';

/**
 * Icons
 */
import { Sparkle, ChartColumn, UsersRound } from 'lucide-react';

interface MoreProps {
  id: string;
}

const More = ({ id }: MoreProps) => {
  return (
    <section id={id} className="p-5 md:px-12 bg-card mt-24 py-24 ">
      <div className="px-6 sm:px-8 lg:px-0 mx-auto container max-w-xl lg:max-w-[1400px]">
        <div className="grid gap-8 lg:grid-cols-2 items-center">
          <div className="w-full">
            <h2 className="title text-2xl sm:text-3xl lg:text-4xl leading-snug">
              Vuoi scoprire il vero valore della tua casa?
            </h2>
            <h3 className="font-semibold mt-4 mb-6 text-base sm:text-lg lg:text-xl">
              Ottieni una valutazione professionale dettagliata{' '}
              <b>entro 72 ore</b> e vendi con il supporto dedicato!
            </h3>

            <div className="flex flex-col font-extrabold text-black text-lg mt-8 gap-4 sm:gap-5">
              <div className="flex items-center gap-3 sm:gap-5">
                <Sparkle /> <span>Stima immediata con tecnologia AI</span>
              </div>
              <div className="flex items-center gap-3 sm:gap-5">
                <ChartColumn />{' '}
                <span>Dati reali e aggiornati sul tuo quartiere</span>
              </div>
              <div className="flex items-center gap-3 sm:gap-5">
                <UsersRound />{' '}
                <span>Agente personale disponibile on demand</span>
              </div>
            </div>

            <Button
              size="lg"
              className="mt-8 mb-3 w-full sm:w-auto text-md lg:text-lg"
            >
              Inizia gratuitamente
            </Button>
            <p className="text-sm sm:text-base mt-2 italic">
              Nessun vincolo. Solo opportunità per valorizzare il tuo immobile.
            </p>
          </div>

          <div className="w-full flex lg:justify-center">
            <div className="flex flex-col bg-foreground rounded-2xl text-white p-6 sm:p-8 shadow-primary shadow-2xl w-full max-w-md">
              <h4 className="font-extrabold text-xl sm:text-2xl">Stima live</h4>
              <div className="grid grid-cols-2 gap-x-6 gap-y-2 mt-4 text-sm sm:text-base">
                <p>Città:</p>
                <p>Torino</p>
                <p>Metri quadrati:</p>
                <p>120 m²</p>
                <p>Valore medio stimato:</p>
                <p>€ 250.000</p>
              </div>
              <Button
                size="lg"
                className="mt-6 text-md lg:text-lg bg-background text-primary w-full"
              >
                Ottieni valutazione
              </Button>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default More;
