/**
 * Components
 */
import Functionality from '@/components/Functionality';
import { Button } from '@/components/ui/button';

/**
 * Icons
 */
import { File, Euro, UserRound } from 'lucide-react';

interface OurServicesProps {
  id: string;
}

const OurServices = ({ id }: OurServicesProps) => {
  return (
    <section
      id={id}
      className="container mx-auto py-24 flex flex-col justify-center items-center text-center"
    >
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
              <strong>Ricevi una fascia di valutazione</strong> in base ai tuoi
              dati
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

      <Button
        size="lg"
        className="text-md lg:text-lg mt-12"
        asChild
      >
        <a href="#valutazione">Valuta ora</a>
      </Button>
    </section>
  );
};

export default OurServices;
