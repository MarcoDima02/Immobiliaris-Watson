import { Button } from "@/components/ui/button";
import { Euro, UserRound, File } from "lucide-react";

const Test = () => {
  return (
    <section className="py-25 container mx-auto">
      <div className="flex justify-center flex-col items-center gap-10">
        <div className="text-center">
          <h2 className="text-4xl font-bold text-zinc">Come funziona</h2>
          <h3 className="text-lg font-semibold text-zinc">
            Ricevi in tre step la valutazione del tuo immobile!
          </h3>
        </div>

        <div className="row flex flex-row flex-wrap justify-center items-start gap-10 lg:gap-0">
          <div className="flex lg:flex-col w-full lg:w-[33%] justify-center items-center px-10 md:px-25 lg:px-10 xl:px-25 gap-10 ">
            <div>
              <div className="bg-card rounded-full p-3 shadow-xl w-20 h-20 flex justify-center items-center">
                <File className="h-10 w-10 text-foreground" />
              </div>
            </div>
            <div className=" text-left text-lg font-medium text-zinc">
              <p>
                <b>Compila il form</b> con i dati dell’abitazione in pochi
                minuti
              </p>
            </div>
          </div>

          <div className="flex lg:flex-col w-full lg:w-[33%] justify-center items-center px-10 md:px-25 lg:px-10 xl:px-25 gap-10 align-center">
            <div>
              <div className="bg-card rounded-full p-3 shadow-xl w-20 h-20 flex justify-center items-center">
                <Euro className="h-10 w-10 text-foreground" />
              </div>
            </div>
            <div className=" text-left text-lg font-medium text-zinc">
              <p>
                <b>Ricevi una fascia di valutazione</b> in base ai tuoi dati
              </p>
            </div>
          </div>

          <div className="flex lg:flex-col  w-full lg:w-[33%] justify-center items-center px-10 md:px-25 lg:px-10 xl:px-25 gap-10 ">
            <div>
              <div className="bg-card rounded-full p-3 shadow-xl w-20 h-20 flex justify-center items-center">
                <UserRound className="h-10 w-10 text-foreground" />
              </div>
            </div>
            <div className=" text-left text-lg font-medium text-zinc">
              <p>
                <b>Consulenza</b> e valutazione precisa da parte di uno{' '}
                <b>dei nostri agenti</b>
              </p>
            </div>
          </div>
        </div>

        <div className="mx-auto">
          <Button>Valuta ora</Button>
        </div>
      </div>
    </section>
  );
};

export default Test;
