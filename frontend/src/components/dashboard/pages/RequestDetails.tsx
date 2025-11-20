import { Button } from "@/components/ui/button";

function RequestDetails() {
    return (
        <>
            <Button>Indietro</Button>
            <div className="flex flex-col lg:flex-row flex-wrap">
                <div className="bg-white p-4 rounded-xl shadow-md text-black mt-4 w-full lg:w-[60%]">
                    <p>Dati del cliente:</p>
                    <div className="flex flex-wrap">
                        <div className="w-1/2 py-2">
                            <p className="font-bold">Nome e cognome:</p>
                            <p className="text-zinc-600">Michele D'avena</p>
                        </div>
                        <div className="w-1/2 py-2">
                            <p className="font-bold">Numero di cellulare:</p>
                            <p className="text-zinc-600">3456782345</p>
                        </div>
                        <div className="w-full py-2">
                            <p className="font-bold">Email:</p>
                            <p className="text-zinc-600">michele.davena@example.com</p>
                        </div>
                        <div className="w-full pt-6 pb-2">
                            <p className="font-bold">Dati dell'immobile:</p>
                        </div>
                        <div className="w-1/2 py-2">
                            <p className="font-bold">Indirizzo:</p>
                            <p className="text-zinc-600">Via Roma 123</p>
                        </div>
                        <div className="w-1/2 py-2">
                            <p className="font-bold">Tipo di immobile:</p>
                            <p className="text-zinc-600">Appartamento</p>
                        </div>
                        <div className="w-1/2 py-2">
                            <p className="font-bold">CAP:</p>
                            <p className="text-zinc-600">12345</p>
                        </div>
                        <div className="w-1/2 py-2">
                            <p className="font-bold">Provincia:</p>
                            <p className="text-zinc-600">TO</p>
                        </div>
                        <div className="w-1/2 py-2">
                            <p className="font-bold">Metri quadri:</p>
                            <p className="text-zinc-600">85 mq</p>
                        </div>
                        <div className="w-1/2 py-2">
                            <p className="font-bold">Numero di locali:</p>
                            <p className="text-zinc-600">3</p>
                        </div>
                        <div className="w-1/2 py-2">
                            <p className="font-bold">Numero di bagni:</p>
                            <p className="text-zinc-600">2</p>
                        </div>
                        <div className="w-1/2 py-2">
                            <p className="font-bold">Balcone:</p>
                            <p className="text-zinc-600">Sì</p>
                        </div>
                        <div className="w-1/2 py-2">
                            <p className="font-bold">Garage:</p>
                            <p className="text-zinc-600">No</p>
                        </div>
                        <div className="w-1/2 py-2">
                            <p className="font-bold">Classe energetica:</p>
                            <p className="text-zinc-600">B</p>
                        </div>
                    </div>
                </div>
                <div className="w-full lg:w-[40%] border mt-4">
                    <div className="flex flex-wrap justify-start items-center p-4 gap-4 border w-full">
                        <Button className="w-auto min-w-50">Modifica stato</Button>
                        <div className="flex flex-col">
                            <p className="text-black font-bold">Stato attuale:</p>
                            <p className="text-primary font-medium">In attesa di valutazione</p>
                        </div>
                        <div className="flex flex-wrap gap-4">
                            <Button className="w-auto min-w-50">Allega contratto</Button>
                            <Button variant={"outline"} className="w-auto min-w-50">Visualizza contratti</Button>
                        </div>
                        <div className="flex flex-col bg-white rounded-xl shadow-md w-full h-101 p-4">
                            <p className="font-bold text-black">Posizione dell'immobile</p>
                            <div className="w-full h-full bg-green-200 mt-5"></div>
                        </div>
                    </div>
                </div>
                <div className="w-full lg:w-[60%]">
                    <div className="bg-white rounded-xl h-48 shadow-md mt-5 p-4">
                        <p className="font-bold text-black">Stima dell'immobile</p>
                        <div className="flex justify-between text-primary text-4xl px-5 py-3 font-extrabold">
                            <p className="w-[45%]">115.000 €</p>
                            <p className="w-[10%] text-center"> - </p>
                            <p className="w-[45%] text-end">165.000 €</p>
                        </div>
                        <div className="flex justify-between">
                            <p className="w-1/2 text-zinc-400 text-sm"><i>Valore minimo</i></p>
                            <p className="w-1/2 text-zinc-400 text-sm text-end"><i>Valore massimo</i></p>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default RequestDetails;