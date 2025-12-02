import { Button } from "../ui/button";

export default function AdminInfoContainer({ data, type }: { data: any[] | null, type: string }) {
    console.log(data);
    return (
        <>
            <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-10 px-8">
                {data && (data.map((item) => (
                    <>
                        <div className="bg-white p-4 w-full rounded-xl shadow-xl text-black flex flex-col">
                            <div className="grid flex-col flex-wrap grid-cols-2 gap-3 mb-5 ">

                                {type === "utenti" && (
                                    <>
                                        <div>
                                            <p className="font-bold">Nome:</p>
                                            <p className="text-zinc-600">{item.nome}</p>
                                        </div>
                                        <div>
                                            <p className="font-bold">Cognome:</p>
                                            <p className="text-zinc-600">{item.cognome}</p>
                                        </div>
                                        <div>
                                            <p className="font-bold">Ruolo:</p>
                                            <p className="text-zinc-600">{item.ruolo}</p>
                                        </div>
                                        <div>
                                            <p className="font-bold">Telefono:</p>
                                            <p className="text-zinc-600">{item.telefono}</p>
                                        </div>
                                        <div>
                                            <p className="font-bold">Email:</p>
                                            <p className="text-zinc-600">{item.email}</p>
                                        </div>
                                    </>
                                )}

                                {type === "richieste" && (
                                    <>
                                        <div>
                                            <p className="font-bold">Indirizzo:</p>
                                            <p className="text-zinc-600">{item.indirizzo}</p>
                                        </div>
                                        <div>
                                            <p className="font-bold">Città:</p>
                                            <p className="text-zinc-600">{item.citta}</p>
                                        </div>
                                        <div>
                                            <p className="font-bold">CAP:</p>
                                            <p className="text-zinc-600">{item.cap}</p>
                                        </div>
  

                                    </>
                                )}


                            </div>
                            <Button variant={"default"} className="mt-auto w-full">Modifica</Button>
                        </div>

                    </>
                )))}
            </div>
        </>
    );
}   