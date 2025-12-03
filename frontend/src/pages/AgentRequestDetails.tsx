/**
 * Node modules
 */
import { useEffect, useRef, useState } from 'react';
import { useLocation } from 'react-router';
import mapboxgl from 'mapbox-gl';

/**
 * Components
 */
import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from '@/components/ui/dialog';
import { Input } from '@/components/ui/input';
import { toast } from 'sonner';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';

/**
 * Fetch functions
 */
import { aggiornaStatoRichiesta, uploadContrattoPDF } from '@/api';

/**
 * Store
 */
import { useAuthStore } from '@/store/auth.store';

mapboxgl.accessToken = mapboxgl.accessToken =
  import.meta.env.VITE_MAPBOX_ACCESS_TOKEN ?? 'MAPBOX_TOKEN';

function AgentRequestDetails() {
  const location = useLocation();

  // Prende quello che esiste: prima request, altrimenti item
  const request = location.state?.request || location.state?.item || null;

  // Se esiste anche item separato, lo prendo
  const item = location.state?.item || location.state?.request || null;

  const [loadingStato, setLoadingStato] = useState(false);
  const [showPdf, setShowPdf] = useState(false);
  const [showModal, setShowModal] = useState(false);

  // Stato iniziale: se c’è request prendo il suo stato, altrimenti prendo da item
  const [nuovoStato, setNuovoStato] = useState(
    location.state?.request?.statoRichiesta ??
    location.state?.item?.statoRichiesta ??
    null
  );

  // Se NON ho né request né item, proteggo la pagina
  if (!request && !item) {
    return <p>Errore: nessun dato ricevuto</p>;
  }


  console.log(item);  const [statoDialogOpen, setStatoDialogOpen] = useState(false);
  const [showImages, setShowImages] = useState(false);
  const [immagini, setImmagini] = useState<any[]>([]);
  const [loadingImages, setLoadingImages] = useState(false);
  const [currentRequest, setCurrentRequest] = useState(request);

  const mapContainerRef = useRef<HTMLDivElement | null>(null);
  

  // const handleModificaStato = async () => {
  //   const nuovoStato = prompt(
  //     'Nuovo stato (IN_ELABORAZIONE, COMPLETATA, ANNULLATA)'
  //   );
  //   if (!nuovoStato) return;

  //   try {
  //     await aggiornaStatoRichiesta(request.idRichiesta, nuovoStato);
  //     alert('Stato aggiornato!');
  //     window.location.reload();
  //   } catch (err) {
  //     console.error(err);
  //     alert("Errore nell'aggiornamento dello stato");
  //   }
  // };

  const handleApriModificaStato = () => {
    setNuovoStato(request?.statoRichiesta || '');
    setStatoDialogOpen(true);
  };
  const handleChiudiModificaStato = () => setStatoDialogOpen(false);

  const handleConfermaStato = async () => {
    if (!nuovoStato) return toast.error('Inserisci uno stato valido!');

    setLoadingStato(true);
    try {
      await aggiornaStatoRichiesta(request.idRichiesta, nuovoStato);
      toast.success('Stato aggiornato con successo!');
      setCurrentRequest({
      ...currentRequest,
      statoRichiesta: nuovoStato,
    });
    } catch (err) {
      console.error(err);
      toast.error('Errore aggiornamento stato');
    } finally {
      setLoadingStato(false);
      setStatoDialogOpen(false);
    }
  };

  // const handleUploadContratto = async () => {
  //   if (!request.idContratto)
  //     return alert('La richiesta non ha ancora un contratto!');

  //   const input = document.createElement('input');
  //   input.type = 'file';
  //   input.accept = 'application/pdf';

  //   input.onchange = async () => {
  //     if (!input.files?.length) return;
  //     const file = input.files[0];

  //     try {
  //       const percorsoFile = await uploadContrattoPDF(file);
  //       alert('Contratto caricato!');

  //       request.pathContrattoPDF = percorsoFile;
  //     } catch (err) {
  //       console.error(err);
  //       alert('Errore durante upload contratto');
  //     }
  //   };

  //   input.click();
  // };
  const handleUploadContratto = async () => {
    if (!currentRequest.idContratto)
      return toast.error('La richiesta non ha ancora un contratto!');

    const input = document.createElement('input');
    input.type = 'file';
    input.accept = 'application/pdf';

    input.onchange = async () => {
      if (!input.files?.length) return;
      const file = input.files[0];

      try {
        const percorsoFile = await uploadContrattoPDF(file);
        toast.success('Contratto caricato!');
        currentRequest.pathContrattoPDF = percorsoFile;
      } catch (err) {
        console.error(err);
        toast.error('Errore durante upload contratto');
      }
    };

    input.click();
  };

  // const handleVisualizzaContratti = () => {
  //   if (!request.pathContrattoPDF) return alert('Nessun contratto disponibile');
  //   setShowPdf(true);
  // };
  const handleVisualizzaContratti = () => {
    if (!currentRequest.pathContrattoPDF)
      return toast.error('Nessun contratto disponibile');
    setShowPdf(true);
  };

  // const handleUploadImages = async () => {
  //   const input = document.createElement('input');
  //   input.type = 'file';
  //   input.accept = 'image/*';
  //   input.multiple = true;

  //   input.onchange = async () => {
  //     const files = input.files;
  //     if (!files?.length) return;

  //     const formData = new FormData();
  //     for (let f of files) formData.append('files', f);

  //     await fetch(
  //       `http://localhost:8080/api/immobili/${request.idImmobile}/immagini`,
  //       {
  //         method: 'POST',
  //         body: formData,
  //       }
  //     );

  //     alert('Immagini caricate');
  //   };

  //   input.click();
  // };

  // const handleUploadImages = async () => {
  //   const input = document.createElement('input');
  //   input.type = 'file';
  //   input.accept = 'image/*';
  //   input.multiple = true;

  //   input.onchange = async () => {
  //     const files = input.files;
  //     if (!files?.length) return;

  //     const formData = new FormData();
  //     for (let f of files) formData.append('files', f);

  //     try {
  //       await fetch(
  //         `http://localhost:8080/api/immobili/${request.idImmobile}/immagini`,
  //         { method: 'POST', body: formData }
  //       );
  //       toast.success('Immagini caricate!');
  //     } catch (err) {
  //       console.error(err);
  //       toast.error('Errore caricamento immagini');
  //     }
  //   };

  //   input.click();
  // };

  const handleUploadImages = async () => {
    const input = document.createElement('input');
    input.type = 'file';
    input.accept = 'image/*';
    input.multiple = true;

    input.onchange = async () => {
      const files = input.files;
      if (!files?.length) return;

      const formData = new FormData();
      for (let f of files) formData.append('files', f);

      try {
        await fetch(
          `http://localhost:8080/api/immobili/${currentRequest.idImmobile}/immagini`,
          { method: 'POST', body: formData }
        );
        toast.success('Immagine caricate!');

        // Aggiorna lista immagini dopo upload
        fetchImmagini();
      } catch (err) {
        console.error(err);
        toast.error('Errore caricamento immagini');
      }
    };

    input.click();
  };

  const fetchImmagini = async () => {
    if (!currentRequest?.idImmobile) return;

    setLoadingImages(true);
    try {
      const res = await fetch(
        `http://localhost:8080/api/immagini/immobile/${currentRequest.idImmobile}`
      );
      if (!res.ok) throw new Error('Errore caricamento immagini');
      const data = await res.json();
      setImmagini(data);
    } catch (err) {
      console.error(err);
      toast.error('Errore caricamento immagini');
    } finally {
      setLoadingImages(false);
    }
  };

  // const handleViewImages = () => {
  //   window.location.href = `/immobile/${request.idImmobile}/immagini`;
  // };

  useEffect(() => {
    if (!currentRequest) return;

    const address = `${currentRequest.indirizzo}, ${currentRequest.citta}, ${currentRequest.provincia}`;

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

  const confermaModificaStato = async () => {
    setLoadingStato(true);

    try {
      await aggiornaStatoRichiesta(request.idRichiesta, nuovoStato);
      setShowModal(false);
    } catch (err) {
      console.error(err);
      alert("Errore nell'aggiornamento dello stato");
    } finally {
      setLoadingStato(false);
    }
  };


  if (!currentRequest) return <p>Nessuna richiesta trovata</p>;

  return (
    <>

      {showModal && (
        <div className="fixed inset-0 bg-black/40 backdrop-blur-sm flex justify-center items-center z-50 animate-fadeIn">
          <div className="bg-white rounded-xl shadow-xl p-6 w-96 relative animate-scaleIn">

            {/* X chiusura */}
            <button
              onClick={() => setShowModal(false)}
              className="absolute top-3 right-3 text-zinc-500 hover:text-zinc-700 transition"
            >
              ✕
            </button>

            <h2 className="text-xl font-bold text-black mb-4">
              Modifica stato richiesta
            </h2>

            {/* Select stato */}
            <label className="text-sm font-medium text-zinc-700">
              Seleziona nuovo stato:
            </label>
            <select
              value={nuovoStato}
              onChange={(e) => setNuovoStato(e.target.value)}
              className="w-full mt-2 p-2 border border-zinc-300 rounded-lg focus:ring-2 focus:ring-primary/50 outline-none"
            >
              <option value="IN_ELABORAZIONE">In elaborazione</option>
              <option value="COMPLETATA">Completata</option>
              <option value="ANNULLATA">Annullata</option>
            </select>

            {/* Bottoni */}
            <div className="flex justify-end gap-3 mt-6">
              <Button variant="outline" onClick={() => setShowModal(false)}>
                Annulla
              </Button>
              <Button onClick={confermaModificaStato}>
                Conferma
              </Button>
            </div>
          </div>
        </div>
      )}


      <Button onClick={() => history.back()}>Indietro</Button>

      {/* --- Dialog Modifica Stato --- */}
      <Dialog
        open={statoDialogOpen}
        onOpenChange={setStatoDialogOpen}
      >
        <DialogContent className="sm:max-w-[425px] bg-card">
          <DialogHeader>
            <DialogTitle>Modifica stato richiesta</DialogTitle>
          </DialogHeader>
          <div className="grid gap-4 py-4">
            <Select
              value={nuovoStato}
              onValueChange={setNuovoStato}
            >
              <SelectTrigger>
                <SelectValue placeholder="Seleziona stato" />
              </SelectTrigger>
              <SelectContent className='bg-card'>
                <SelectItem value="IN_ELABORAZIONE">IN_ELABORAZIONE</SelectItem>
                <SelectItem value="COMPLETATA">COMPLETATA</SelectItem>
                <SelectItem value="ANNULLATA">ANNULLATA</SelectItem>
              </SelectContent>
            </Select>
          </div>
          <DialogFooter>
            <Button
              variant="outline"
              onClick={handleChiudiModificaStato}
            >
              Annulla
            </Button>
            <Button
              onClick={handleConfermaStato}
              disabled={loadingStato}
            >
              {loadingStato ? 'Caricamento...' : 'Conferma'}
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>

      {/* --- Dialog PDF --- */}
      <Dialog
        open={showPdf}
        onOpenChange={setShowPdf}
      >
        <DialogTitle>Contratto</DialogTitle>
        <DialogContent
          className="w-4/5 h-4/5 max-w-5xl max-h-[90vh]"
          closeButtonClassName="text-white bg-primary p-1 cursor-pointer opacity-100 hover:bg-white hover:text-primary"
        >
          <div className="relative w-full h-full">
            {currentRequest.pathContrattoPDF && (
              <iframe
                src={`http://localhost:8080/api/contratti/pdf/${currentRequest.pathContrattoPDF.split('/').pop()}`}
                className="w-full h-full"
              />
            )}
          </div>
        </DialogContent>
      </Dialog>

      <Dialog
        open={showImages}
        onOpenChange={setShowImages}
      >
        <DialogTitle>Immagini</DialogTitle>
        <DialogContent
          className="w-4/5 h-4/5 max-w-5xl max-h-[90vh]"
          closeButtonClassName="text-white bg-primary p-1 cursor-pointer opacity-100 hover:bg-white hover:text-primary"
        >
          <DialogHeader>
            <DialogTitle>Immagini Immobile</DialogTitle>
          </DialogHeader>
          <div className="grid grid-cols-3 gap-4 overflow-auto h-full mt-4">
            {loadingImages ? (
              <p>Caricamento immagini...</p>
            ) : immagini.length ? (
              immagini.map((img) => (
                <img
                  key={img.idImmagine}
                  src={`http://localhost:8080/api/immagini/immobile/${currentRequest.idImmobile}/${img.filename}`}
                  alt="immobile"
                  className="w-full h-auto object-cover rounded"
                />
              ))
            ) : (
              <p className="col-span-3 text-center text-zinc-500">
                Nessuna immagine disponibile
              </p>
            )}
          </div>
        </DialogContent>
      </Dialog>

      <div className="flex flex-col lg:flex-row flex-wrap pb-5">
        {/* INFO CLIENTE + IMMOBILE */}
        <div className="bg-white p-4 rounded-xl shadow-md text-black mt-4 w-full lg:w-[60%]">
          <p className="font-bold text-xl mb-4">Dati del cliente</p>

          <div className="flex flex-wrap">
            {/* Nome utente */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Nome e cognome:</p>
              <p className="text-zinc-600">
                {currentRequest.nomeUtente} {currentRequest.cognomeUtente}
              </p>
            </div>

            {/* Numero */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Numero di cellulare:</p>
              <p className="text-zinc-600">{currentRequest.telefonoUtente}</p>
            </div>

            {/* Email */}
            <div className="w-full py-2">
              <p className="font-bold">Email:</p>
              <p className="text-zinc-600">{currentRequest.emailUtente}</p>
            </div>

            {/* IMMOBILE */}
            <div className="w-full pt-6 pb-2">
              <p className="font-bold text-xl">Dati dell'immobile</p>
            </div>

            {/* Indirizzo */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Indirizzo:</p>
              <p className="text-zinc-600">
                {currentRequest.indirizzo}, {currentRequest.citta}
              </p>
            </div>

            {/* Tipologia */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Tipo di immobile:</p>
              <p className="text-zinc-600">{currentRequest.tipologia}</p>
            </div>

            {/* CAP */}
            <div className="w-1/2 py-2">
              <p className="font-bold">CAP:</p>
              <p className="text-zinc-600">{currentRequest.cap}</p>
            </div>

            {/* Provincia */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Provincia:</p>
              <p className="text-zinc-600">{currentRequest.provincia}</p>
            </div>

            {/* MQ */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Metri quadri:</p>
              <p className="text-zinc-600">{currentRequest.superficieMq} mq</p>
            </div>

            {/* Locali */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Numero di locali:</p>
              <p className="text-zinc-600">{currentRequest.nstanze}</p>
            </div>

            {/* Bagni */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Numero di bagni:</p>
              <p className="text-zinc-600">{currentRequest.nbagni}</p>
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
              <p className="text-zinc-600">{currentRequest.garage ? 'Sì' : 'No'}</p>
            </div>

            {/* Classe energetica */}
            <div className="w-1/2 py-2">
              <p className="font-bold">Classe energetica:</p>
              <p className="text-zinc-600">{currentRequest.classeEnergetica}</p>
            </div>
          </div>
        </div>

        {/* COLONNA DI DESTRA */}
        <div className="w-full lg:w-[40%] h-full mt-4">
          <div className="flex flex-wrap justify-start items-center lg:px-4 px-0 gap-4 h-full w-full">
            {/* Bottone modifica stato */}
            <Button
              className="w-auto min-w-50"
              onClick={() => setShowModal(true)}
            >
              {loadingStato ? 'Caricamento...' : 'Modifica stato'}
            </Button>


            {/* Stato attuale */}
            <div className="flex flex-col">
              <p className="text-black font-bold">Stato attuale:</p>
              <p className="text-primary font-medium">
                {currentRequest.statoRichiesta.replace(/_/g, ' ')}
              </p>
            </div>

            {/* Contratti */}
            <div className="flex flex-wrap gap-4">
              <Button
                className="w-auto min-w-50"
                onClick={handleUploadContratto}
              >
                Allega contratto
              </Button>
              <Button
                variant={'outline'}
                className="w-auto min-w-50"
                onClick={handleVisualizzaContratti}
              >
                Visualizza contratti
              </Button>
              {/* {showPdf && (
                <div className="fixed inset-0 bg-black/50 flex justify-center items-center z-50">
                  <div className="bg-white w-4/5 h-4/5 p-4 relative">
                    <button
                      className="absolute top-2 right-2 text-red-500"
                      onClick={() => setShowPdf(false)}
                    >
                      Chiudi
                    </button>
                    <iframe
                      src={`http://localhost:8080/api/contratti/pdf/${currentRequest.pathContrattoPDF.split('/').pop()}`}
                      className="w-full h-full"
                    />
                  </div>
                </div>
              )} */}
            </div>

            {/* MAPPA */}
            <div className="flex flex-col bg-white rounded-xl shadow-md w-full h-full p-4">
              <p className="font-bold text-black">Posizione dell'immobile</p>
              <div
                ref={mapContainerRef}
                className="w-full h-80 rounded-xl mt-5"
                style={{ minHeight: '400px' }}
              ></div>
            </div>
          </div>
        </div>

        {/* STIMA IMMOBILE */}

        <div className="w-full lg:w-3/5">
          <div className="bg-white rounded-2xl shadow-lg mt-5 p-6 flex flex-col gap-6">
            {/* Titolo */}
            <h2 className="font-bold text-black text-xl md:text-2xl tracking-tight">
              Stima dell'immobile
            </h2>
            {/* Valore effettivo (PRINCIPALE) */}
            <div className="text-center py-4">
              <p className="text-zinc-600 font-medium">Valore effettivo stimato</p>
              <p className="text-primary text-4xl md:text-5xl lg:text-6xl font-extrabold mt-2">
                {currentRequest.valoreMedio.toLocaleString()} €
              </p>
            </div>

            {/* Separatore */}
            <div className="w-full h-px bg-zinc-200"></div>

            {/* Range valori (SECONDARIO) */}
            <div className="flex flex-col gap-1">
              <div className="flex justify-between items-center text-black font-bold text-lg md:text-xl">
                <span className="truncate">
                  {currentRequest.valoreMin.toLocaleString()} €
                </span>
                <span className="mx-3 text-zinc-300 text-xl">•</span>
                <span className="truncate text-right">
                  {currentRequest.valoreMax.toLocaleString()} €
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
              <Button
                className="h-1/2"
                onClick={handleUploadImages}
              >
                Aggiungi immagini
              </Button>
              <Button
                className="h-1/2"
                variant={'outline'}
                onClick={() => {
                  fetchImmagini();
                  setShowImages(true);
                }}
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
