/**
 * Node modules
 */
import { useEffect, useRef } from 'react';
import { useLocation } from 'react-router';
import mapboxgl from 'mapbox-gl';

/**
 * Components
 */
import { Button } from '@/components/ui/button';

mapboxgl.accessToken = mapboxgl.accessToken =
  import.meta.env.VITE_MAPBOX_ACCESS_TOKEN ?? 'MAPBOX_TOKEN';

function AgentRequestDetails() {
  const location = useLocation();
  const { request } = location.state || {};

  const mapContainerRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (!request) return;

    const address = `${request.indirizzo}, ${request.citta}, ${request.provincia}`;

    const fetchCoordinates = async () => {
      try {
        const res = await fetch(
          `https://api.mapbox.com/geocoding/v5/mapbox.places/${encodeURIComponent(
            address
          )}.json?access_token=${mapboxgl.accessToken}`
        );

        const data = await res.json();

        if (!data.features?.length) return;

        const [lng, lat] = data.features[0].center;

        if (!mapContainerRef.current) return;
        const map = new mapboxgl.Map({
          container: mapContainerRef.current,
          style: 'mapbox://styles/mapbox/streets-v11',
          center: [lng, lat],
          zoom: 14,
        });

        new mapboxgl.Marker().setLngLat([lng, lat]).addTo(map);

        return () => map.remove();
      } catch (err) {
        console.error('Errore caricamento mappa:', err);
      }
    };
    fetchCoordinates();
  }, [request]);

  if (!request) return <p>Nessuna richiesta trovata</p>;

  return (
    <>
      <Button onClick={() => history.back()}>Indietro</Button>

      <div className="flex flex-col lg:flex-row flex-wrap pb-5">
        {/* INFO CLIENTE + IMMOBILE */}
        <div className="bg-white p-4 rounded-xl shadow-md text-black mt-4 w-full lg:w-[60%]">
          <p className="font-bold text-xl mb-4">Dati del cliente</p>

          <div className="flex flex-wrap">
            {/* Nome utente */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Nome e cognome:</p>
              <p className="text-zinc-600">
                {request.nomeUtente} {request.cognomeUtente}
              </p>
            </div>

            {/* Numero */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Numero di cellulare:</p>
              <p className="text-zinc-600">{request.telefonoUtente}</p>
            </div>

            {/* Email */}
            <div className="w-full py-2">
              <p className="font-bold">Email:</p>
              <p className="text-zinc-600">{request.emailUtente}</p>
            </div>

            {/* IMMOBILE */}
            <div className="w-full pt-6 pb-2">
              <p className="font-bold text-xl">Dati dell'immobile</p>
            </div>

            {/* Indirizzo */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Indirizzo:</p>
              <p className="text-zinc-600">
                {request.indirizzo}, {request.citta}
              </p>
            </div>

            {/* Tipologia */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Tipo di immobile:</p>
              <p className="text-zinc-600">{request.tipologia}</p>
            </div>

            {/* CAP */}
            <div className="w-1/2 py-2">
              <p className="font-bold">CAP:</p>
              <p className="text-zinc-600">{request.cap}</p>
            </div>

            {/* Provincia */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Provincia:</p>
              <p className="text-zinc-600">{request.provincia}</p>
            </div>

            {/* MQ */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Metri quadri:</p>
              <p className="text-zinc-600">{request.superficieMq} mq</p>
            </div>

            {/* Locali */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Numero di locali:</p>
              <p className="text-zinc-600">{request.nstanze}</p>
            </div>

            {/* Bagni */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Numero di bagni:</p>
              <p className="text-zinc-600">{request.nbagni}</p>
            </div>

            {/* Balcone */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Balcone/Terrazzo:</p>
              <p className="text-zinc-600">
                {request.balconeTerrazzo ? 'Sì' : 'No'}
              </p>
            </div>

            {/* Garage */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Garage:</p>
              <p className="text-zinc-600">{request.garage ? 'Sì' : 'No'}</p>
            </div>

            {/* Classe energetica */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Classe energetica:</p>
              <p className="text-zinc-600">{request.classeEnergetica}</p>
            </div>
          </div>
        </div>

        {/* COLONNA DI DESTRA */}
        <div className="w-full lg:w-[40%] h-full mt-4">
          <div className="flex flex-wrap justify-start items-center lg:px-4 px-0 gap-4 h-full w-full">
            {/* Bottone modifica stato */}
            <Button className="w-auto min-w-50">Modifica stato</Button>

            {/* Stato attuale */}
            <div className="flex flex-col">
              <p className="text-black font-bold">Stato attuale:</p>
              <p className="text-primary font-medium">
                {request.statoRichiesta.replace(/_/g, ' ')}
              </p>
            </div>

            {/* Contratti */}
            <div className="flex flex-wrap gap-4">
              <Button className="w-auto min-w-50">Allega contratto</Button>
              <Button
                variant={'outline'}
                className="w-auto min-w-50"
              >
                Visualizza contratti
              </Button>
            </div>

            {/* MAPPA */}
            <div className="flex flex-col bg-white rounded-xl shadow-md w-full h-full p-4">
              <p className="font-bold text-black">Posizione dell'immobile</p>
              <div
                ref={mapContainerRef}
                className="w-full h-80 rounded-xl mt-5"
                style={{ minHeight: '300px' }}
              ></div>
            </div>
          </div>
        </div>

        {/* STIMA IMMOBILE */}
        {/* <div className="w-full lg:w-[60%]">
          <div className="bg-white rounded-xl h-48 shadow-md mt-5 p-4">
            <p className="font-bold text-black">Stima dell'immobile</p>

            <div className="flex justify-between text-primary text-xl md:text-3xl lg:text-4xl xl:text-5xl px-5 py-3 font-extrabold">
              <p className="w-[45%]">{request.valoreMin.toLocaleString()} €</p>
              <p className="w-[10%] text-center"> - </p>
              <p className="w-[45%] text-end">
                {request.valoreMax.toLocaleString()} €
              </p>
            </div>

            <div className="flex justify-between">
              <p className="w-1/2 text-zinc-400 text-sm">
                <i>Valore minimo</i>
              </p>
              <p className="w-1/2 text-zinc-400 text-sm text-end">
                <i>Valore massimo</i>
              </p>
            </div>
            <div>Valore effettivo</div>
            <p className="">{request.valoreMedio.toLocaleString()} €</p>
          </div>
        </div> */}
        <div className="w-full lg:w-3/5">
          <div className="bg-white rounded-2xl shadow-lg mt-5 p-6 flex flex-col gap-6">
            {/* Titolo */}
            <h2 className="font-bold text-black text-xl md:text-2xl tracking-tight">
              Stima dell'immobile
            </h2>

            {/* Valore effettivo (PRINCIPALE) */}
            <div className="text-center py-4">
              <p className="text-zinc-600 font-medium">
                Valore effettivo stimato
              </p>
              <p className="text-primary text-4xl md:text-5xl lg:text-6xl font-extrabold mt-2">
                {request.valoreMedio.toLocaleString()} €
              </p>
            </div>

            {/* Separatore */}
            <div className="w-full h-px bg-zinc-200"></div>

            {/* Range valori (SECONDARIO) */}
            <div className="flex flex-col gap-1">
              <div className="flex justify-between items-center text-black font-bold text-lg md:text-xl">
                <span className="truncate">
                  {request.valoreMin.toLocaleString()} €
                </span>
                <span className="mx-3 text-zinc-300 text-xl">•</span>
                <span className="truncate text-right">
                  {request.valoreMax.toLocaleString()} €
                </span>
              </div>

              {/* Label min/max */}
              <div className="flex justify-between text-zinc-400 text-xs md:text-sm mt-1">
                <span className="italic">Valore minimo</span>
                <span className="italic">Valore massimo</span>
              </div>
            </div>
          </div>
        </div>

        {/* IMMAGINI */}
        <div className="w-full lg:w-[40%] px-4 wrap">
          <div className="bg-white rounded-xl h-48 shadow-md mt-5 p-4 flex flex-wrap">
            <div className="flex flex-col w-full xl:w-1/2 py-3 gap-3 justify-center h-full">
              <Button className="h-1/2">Aggiungi immagini</Button>
              <Button
                className="h-1/2"
                variant={'outline'}
              >
                Visualizza immagini
              </Button>
            </div>

            <div className="hidden xl:block w-1/2 h-full px-3 py-1">
              <div className="bg-black/20 w-full h-full rounded-2xl flex justify-center items-center">
                <p className="text-4xl font-bold">12+</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default AgentRequestDetails;
