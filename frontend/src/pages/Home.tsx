/**
 * Pages
 */
import Hero from '@/pages/Hero';
import OurServices from '@/pages/OurServices';
import Evaluation from './Evaluation';
import Agents from './Agents';

/**
 * Immages
 */
import { about } from '@/assets';


const Home = () => {
  return (
    <>

      <Hero id='home'/>

      <OurServices id='come-funziona'/>
      
      <Evaluation  id='valutazione'/>

     <Agents id='agenti'/>


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
