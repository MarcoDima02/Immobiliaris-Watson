import { Button } from "@/components/ui/button";

function RequestDiv({ request, index }: { request: any; index: number }) {
    return (
        <div key={index} className="bg-white p-4 rounded-xl shadow-md text-black">
            <div className="grid flex-col flex-wrap grid-cols-2 gap-15">
                <div>
                    <p className="font-bold">Nome del cliente:</p>
                    <p className="text-zinc-600">{request.nome}</p>
                </div>
                <div>
                    <p className="font-bold">Data:</p>
                    <p className="text-zinc-600">{request.data}</p>
                </div>
            </div>

            <div className="my-4 flex flex-col text-zinc-600">
                <p className="font-bold text-black">Dettagli dell'immobile:</p>
                <p>{request.locali} locali</p>
                <p>{request.bagni} bagni</p>
                <p>{request.mq} metri quadri</p>
            </div>

            <p className="mt-4 font-bold">Stato: <span className="text-primary">{request.stato}</span></p>
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
export default RequestDiv;