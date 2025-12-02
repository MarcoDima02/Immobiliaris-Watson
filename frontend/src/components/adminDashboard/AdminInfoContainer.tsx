import { Button } from "../ui/button";

export default function AdminInfoContainer({ data, type }: { data: any[] | null, type: string }) {
    console.log(data);
    return (
        <>
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-10 px-8">
                {data && (data.map((item) => (
                    <>
                        <div className="bg-white p-4 w-full rounded-xl shadow-xl text-black flex flex-col">
                            <div className="grid flex-col flex-wrap grid-cols-2 gap-3 mb-5 ">

                                {type === "utente" && (
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
                                            <p className="font-bold">Telefono:</p>
                                            <p className="text-zinc-600">{item.telefono}</p>
                                        </div>
                                        <div>
                                            <p className="font-bold">Email:</p>
                                            <p className="text-zinc-600">{item.email}</p>
                                        </div>
                                        <div>
                                            <p className="font-bold">Ruolo:</p>
                                            <p className="text-zinc-600">{item.ruolo}</p>
                                        </div>
                                    </>
                                )}

                                {type === "utente" && (
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
                                            <p className="font-bold">Telefono:</p>
                                            <p className="text-zinc-600">{item.telefono}</p>
                                        </div>
                                        <div>
                                            <p className="font-bold">Email:</p>
                                            <p className="text-zinc-600">{item.email}</p>
                                        </div>
                                        <div>
                                            <p className="font-bold">Ruolo:</p>
                                            <p className="text-zinc-600">{item.ruolo}</p>
                                        </div>
                                    </>
                                )}

                                {
                                    Object.keys(item).map((key) => (

                                        <div>
                                            <p className="font-bold">{key}:</p>
                                            <p className="text-zinc-600">{item[key] ? item[key].toString() : "N/A"}</p>
                                        </div>
                                    ))
                                }
                            </div>
                            <Button variant={"default"} className="mt-auto w-full">Modifica</Button>
                        </div>

                    </>
                )))}
            </div>
        </>
    );
}   