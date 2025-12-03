import { Link } from "react-router";
import { Button } from "../ui/button";
import { useState } from "react";
import { useAuthStore } from "@/store/auth.store";

export default function AdminInfoContainer({ data, type }: { data: any[] | null, type: string }) {


    console.log(data);
    return (
        <>
            <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-10 px-8">
                {data && (data.map((item) => (
                    <>
                        <div className="bg-white p-4 w-full rounded-xl shadow-xl text-black flex flex-col">


                            {type === "utenti" && (
                                <>
                                    <div className="grid flex-col flex-wrap grid-cols-2 gap-3 mb-5 ">
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
                                    </div>
                                </>
                            )}

                            {type === "richieste" &&

                                (
                                    <div className="flex flex-col gap-3 mb-5 ">
                                        <div className="flex flex-col w-full">
                                            <div className="w-full flex justify-evenly mb-4">
                                                <div className="w-full">
                                                    <p className="font-bold">Cliente:</p>
                                                    <p className="text-zinc-600">
                                                        {item.nomeUtente} {item.cognomeUtente}
                                                    </p>
                                                </div>
                                                <div className="w-full">
                                                    <p className="font-bold">Data:</p>
                                                    <p className="text-zinc-600">
                                                        {item.dataRichiesta?.slice(0, 10).replace(/-/g, "/")} alle{" "}
                                                        {item.dataRichiesta?.slice(11, 16)}
                                                    </p>
                                                </div>
                                            </div>

                                            <div className="flex flex-col my-3 text-zinc-600">
                                                <p className="font-bold text-black mb-2">Immobile:</p>
                                                <p>{item.nstanze} stanze</p>
                                                <p>{item.nbagni} bagni</p>


                                                <p className="mt-3 font-bold w-full">
                                                    Stato:{" "}
                                                    <span className="text-primary">
                                                        {item.stato?.replace(/_/g, " ")}
                                                    </span>
                                                </p>
                                            </div>

                                            <Link to="/backoffice/admin/request" state={{ item }}>
                                                <Button className="mt-4 w-full">Visualizza dettagli</Button>
                                            </Link>
                                        </div>
                                    </div>

                                )}





                            <Button variant={"default"} className="mt-auto w-full">Modifica</Button>
                        </div>

                    </>
                )))
                }
            </div >
        </>
    );
}   