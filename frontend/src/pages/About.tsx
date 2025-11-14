/**
 * Images
 */
import { about } from '@/assets';

interface AboutProps {
  id: string;
}

const About = ({ id }: AboutProps) => {
  return (
    <section
      id={id}
      className="container max-w-xl lg:max-w-[1400px] mx-auto mt-12"
    >
      <div className="flex flex-col items-center mx-auto text-center pb-8">
        <h2 className="title">Chi siamo</h2>
        <h3 className="title-secondary">
          Scopri la storia e i valori di Immobiliaris
        </h3>
      </div>
      <div className="flex flex-col lg:flex-row lg:items-center lg:justify-center gap-10 mx-auto">
        <div className="w-full px-8 pb-5 lg:pb-0">
          <img
            src={about}
            alt="About us"
            className="w-full h-full object-cover rounded-[40px]"
          />
        </div>
        <div className="flex flex-col justify-center px-8 w-full">
          <p className="text-lg text-justify mb-4 text-black font-medium ">
            <b>Immobiliaris</b> è una giovane realtà nata per rivoluzionare il
            modo in cui si vendono e si valutano gli immobili.
            <br /> <br />
            Uniamo la professionalità dei nostri agenti <b>
              all’efficienza
            </b>{' '}
            delle nuove tecnologie digitali.
          </p>

          <div className="bg-foreground rounded-2xl p-6 mt-6 flex justify-center items-center">
            <p className="text-white leading-relaxed">
              Il nostro obiettivo è rendere la valutazione immobiliare semplice,
              rapida e trasparente, offrendo ai clienti un servizio innovativo
              che combina dati, consulenza e fiducia.
            </p>
          </div>
        </div>
      </div>
    </section>
  );
};

export default About;
