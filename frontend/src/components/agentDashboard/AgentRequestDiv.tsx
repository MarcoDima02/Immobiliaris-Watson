import { Button } from "@/components/ui/button";

function AgentRequestDiv({ request, key }: { request: any; key: number }) {

    return (
        <div key={key} className="bg-white p-4 rounded-xl shadow-md text-black">
            <div className="grid flex-col flex-wrap grid-cols-2 gap-15">
                <div>
                    <p className="font-bold">Nome del cliente:</p>
                    <p className="text-zinc-600">{request.nomeUtente} {request.cognomeUtente}</p>
                </div>
                <div>
                    <p className="font-bold">Data:</p>
                    <p className="text-zinc-600">{(request.dataRichiesta).slice(0,10)} alle {(request.dataRichiesta).slice(11,16)}</p>
                </div>
            </div>

            <div className="my-4 flex flex-col text-zinc-600">
                <p className="font-bold text-black">Dettagli dell'immobile:</p>
                <p>{request.nstanze} stanze</p>
                <p>{request.nbagni} bagni</p>
                <p>{request.superficieMq} metri quadri</p>
            </div>

            <p className="mt-4 font-bold">Stato: <span className="text-primary">{request.statoRichiesta}</span></p>
            <Button className="mt-4 w-full">Visualizza i dettagli</Button>

            {!request.agente && (
                <Button variant={"outline"} className="mt-2 w-full">Prendi in carico</Button>
            )}
            {request.agente && (
                <p className="mt-4 text-zinc-600 font-bold text-center">Presa in carico da <span className="text-black">{request.agente}</span></p>
            )}


        </div>
    );
}
export default AgentRequestDiv;