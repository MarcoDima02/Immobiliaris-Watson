/**
 * Immages
 */
import { homeAvif } from '@/assets';
import { homeWebp } from '@/assets';
import { homeJpg } from '@/assets';

const Home = () => {
  return (
    <section className="absolute top-0 left-0 w-full">
      <figure className="relative inline-block overflow-hidden w-full">
        <picture>
          <source
            srcSet={homeAvif}
            type="image/avif"
          />
          <source
            srcSet={homeWebp}
            type="image/webp"
          />
          <img
            src={homeJpg}
            alt="Immagine di un appartamento"
            loading="lazy"
            className="md:max-h-[50vh] w-full object-cover"
          />
        </picture>

        <div className="absolute inset-0 pointer-events-none bg-linear-to-t from-background via-transparent "></div>
        <div className="absolute inset-0 pointer-events-none bg-linear-to-r from-background via-transparent"></div>
      </figure>
    </section>
  );
};

export default Home;
