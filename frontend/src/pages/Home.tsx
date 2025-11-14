/**
 * Pages
 */
import Hero from '@/pages/Hero';
import OurServices from '@/pages/OurServices';
import Evaluation from '@/pages/Evaluation';
import Agents from '@/pages/Agents';
import About from '@/pages/About';
import More from '@/pages/More';

const Home = () => {
  return (
    <>
      <Hero id="home" />

      <OurServices id="come-funziona" />

      <Evaluation id="valutazione" />

      <Agents id="agenti" />

      <About id='chi-siamo' />

      <More id='inizia-gratis'/>
    </>
  );
};

export default Home;
