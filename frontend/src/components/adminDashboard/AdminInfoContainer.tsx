import { Link, useNavigate } from "react-router";
import { Button } from "../ui/button";
import { useState } from "react";

export default function AdminInfoContainer({ data, type }: { data: any[] | null, type: string }) {
    const [showModal, setShowModal] = useState(false);
    const [selectedUser, setSelectedUser] = useState<any>(null);
    const [filterNome, setFilterNome] = useState("");
    const [dettagliImmobile, setDettagliImmobile] = useState("");
    const [filterCognome, setFilterCognome] = useState("");
    const [formData, setFormData] = useState({
        nome: "",
        cognome: "",
        ruolo: "",
        telefono: "",
        email: "",
    });
    async function getDettagliImmobile(id: number) {
        try {
            const response = await fetch(`http://localhost:8080/api/richieste/${id}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                }
            });

            if (!response.ok) {
                throw new Error(`Errore nella fetch: ${response.status}`);
            }

            const data = await response.json();
            setDettagliImmobile(data);
            return data;

        } catch (error) {
            console.error("Errore durante getDettagliImmobile:", error);
            return null;
        }
    }

    const navigate = useNavigate();


    async function handleClick(id: number) {
        const dettagli = await getDettagliImmobile(id);
        console.log(dettagli)
        navigate("/backoffice/admin/richiesta", {
            state: { dettagliImmobile: dettagli }
        });
    }


    console.log(data)

    const openModal = (item: any) => {
        setSelectedUser(item);
        setFormData({
            nome: item.nome,
            cognome: item.cognome,
            ruolo: item.ruolo,
            telefono: item.telefono,
            email: item.email,
        });
        setShowModal(true);
    };

    const handleSave = async () => {
        if (!selectedUser) return;

        try {
            const response = await fetch(`/admin/dashboard/utenti/${selectedUser.id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(formData),
            });

            if (!response.ok) {
                throw new Error("Errore nel salvataggio dell'utente");
            }

            const updatedUser = await response.json();
            console.log("Utente aggiornato:", updatedUser);

            // Aggiorna la lista locali in stato se vuoi subito vedere i cambiamenti
            // Per esempio, se hai uno stato 'data', fai:
            // setData(prev => prev?.map(u => u.id === updatedUser.id ? updatedUser : u));

            setShowModal(false);
        } catch (error) {
            console.error(error);
            alert("Si è verificato un errore durante il salvataggio.");
        }
    };


    // Lista filtrata per utenti o richieste
    const listToRender =
        type === "utenti"
            ? data?.filter(
                (item) =>
                    item.nome.toLowerCase().includes(filterNome.toLowerCase()) &&
                    item.cognome.toLowerCase().includes(filterCognome.toLowerCase())
            )
            : data;

    return (
        <>
            {/* Filtri utenti */}
            {type === "utenti" && (
                <div className="flex gap-3 mb-5 px-8">
                    <input
                        type="text"
                        placeholder="Filtra per nome"
                        value={filterNome}
                        onChange={(e) => setFilterNome(e.target.value)}
                        className="p-2 border border-zinc-300 rounded-lg outline-none focus:ring-2 focus:ring-primary/50"
                    />
                    <input
                        type="text"
                        placeholder="Filtra per cognome"
                        value={filterCognome}
                        onChange={(e) => setFilterCognome(e.target.value)}
                        className="p-2 border border-zinc-300 rounded-lg outline-none focus:ring-2 focus:ring-primary/50"
                    />
                </div>
            )}

            {/* Griglia */}
            <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-10 px-8">
                {listToRender?.map((item) => (
                    <div key={item.id} className="bg-white p-4 w-full rounded-xl shadow-xl text-black flex flex-col mb-5">
                        {type === "utenti" && (
                            <>
                                <div className="grid grid-cols-2 gap-3 mb-5">
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
                                    <div className="col-span-2">
                                        <p className="font-bold">Email:</p>
                                        <p className="text-zinc-600">{item.email}</p>
                                    </div>
                                </div>
                                <Button variant="default" className="mt-auto w-full" onClick={() => openModal(item)}>
                                    Modifica
                                </Button>
                            </>
                        )}

                        {type === "richieste" && (
                            <div className="flex flex-col gap-3 mb-5">
                                <div className="flex flex-col w-full">
                                    <div className="w-full flex justify-evenly mb-4">
                                        <div className="w-full">
                                            <p className="font-bold">Cliente:</p>
                                            <p className="text-zinc-600">{item.nomeUtente} {item.cognomeUtente}</p>
                                        </div>
                                        <div className="w-full">
                                            <p className="font-bold">Data:</p>
                                            <p className="text-zinc-600">
                                                {item.dataRichiesta?.slice(0, 10).replace(/-/g, "/")} alle {item.dataRichiesta?.slice(11, 16)}
                                            </p>
                                        </div>
                                    </div>

                                    <div className="flex flex-col my-3 text-zinc-600">
                                        <p className="font-bold text-black mb-2">Immobile:</p>
                                        <p>{item.nstanze} stanze</p>
                                        <p>{item.nbagni} bagni</p>
                                        <p className="mt-3 font-bold w-full">
                                            Stato: <span className="text-primary">{item.stato?.replace(/_/g, " ")}</span>
                                        </p>
                                    </div>

                                    <Link to="/backoffice/admin/richiesta" state={{ item }}>
                                        <Button className="mt-4 w-full">Visualizza dettagli</Button>
                                    </Link>
                                </div>
                            </div>
                        )}

                        {type === "contratti" && (
                            <div className="flex flex-col gap-3 mb-5">
                                <div className="flex flex-col w-full">
                                    <div className="w-full flex justify-evenly mb-4">
                                        <div className="w-full">
                                            <p className="font-bold">Cliente:</p>
                                            <p className="text-zinc-600">{item.nomeUtente} {item.cognomeUtente}</p>
                                        </div>
                                        <div className="w-full">
                                            <p className="font-bold">Data contratto:</p>
                                            <p className="text-zinc-600">
                                                {item.dataContratto?.slice(0, 10).replace(/-/g, "/")} alle {item.dataContratto?.slice(11, 16)}
                                            </p>
                                        </div>
                                        <div className="w-full">
                                            <p className="font-bold">Data scadenza:</p>
                                            <p className="text-zinc-600">
                                                {item.dataScadenzaContratto?.slice(0, 10).replace(/-/g, "/")} alle {item.dataScadenzaContratto?.slice(11, 16)}
                                            </p>
                                        </div>
                                    </div>

                                    <div className="flex flex-col my-3 text-zinc-600">
                                        <p className="font-bold text-black mb-2">Immobile:</p>
                                        <p>{item.nstanze} stanze</p>
                                        <p>{item.nbagni} bagni</p>
                                        <p className="mt-3 font-bold w-full">
                                            Stato: <span className="text-primary">{item.stato?.replace(/_/g, " ")}</span>
                                        </p>
                                    </div>

                                    <Button className="mt-4 w-full" onClick={() => handleClick(item.idImmobile)}>
                                        Visualizza dettagli immobile
                                    </Button>

                                </div>
                            </div>
                        )}


                    </div>
                ))}
            </div>

            {/* Modale utente */}
            {showModal && selectedUser && (
                <div className="fixed inset-0 bg-black/40 backdrop-blur-sm flex justify-center items-center z-50">
                    <div className="bg-white rounded-xl shadow-xl p-6 w-96 relative">
                        <button
                            onClick={() => setShowModal(false)}
                            className="absolute top-3 right-3 text-zinc-500 hover:text-zinc-700 transition"
                        >
                            ✕
                        </button>
                        <h2 className="text-xl font-bold mb-4">Modifica utente</h2>

                        <div className="flex flex-col gap-3">
                            <input
                                type="text"
                                placeholder="Nome"
                                value={formData.nome}
                                onChange={(e) => setFormData({ ...formData, nome: e.target.value })}
                                className="p-2 border border-zinc-300 rounded-lg w-full outline-none focus:ring-2 focus:ring-primary/50"
                            />
                            <input
                                type="text"
                                placeholder="Cognome"
                                value={formData.cognome}
                                onChange={(e) => setFormData({ ...formData, cognome: e.target.value })}
                                className="p-2 border border-zinc-300 rounded-lg w-full outline-none focus:ring-2 focus:ring-primary/50"
                            />
                            <input
                                type="text"
                                placeholder="Ruolo"
                                value={formData.ruolo}
                                onChange={(e) => setFormData({ ...formData, ruolo: e.target.value })}
                                className="p-2 border border-zinc-300 rounded-lg w-full outline-none focus:ring-2 focus:ring-primary/50"
                            />
                            <input
                                type="text"
                                placeholder="Telefono"
                                value={formData.telefono}
                                onChange={(e) => setFormData({ ...formData, telefono: e.target.value })}
                                className="p-2 border border-zinc-300 rounded-lg w-full outline-none focus:ring-2 focus:ring-primary/50"
                            />
                            <input
                                type="email"
                                placeholder="Email"
                                value={formData.email}
                                onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                                className="p-2 border border-zinc-300 rounded-lg w-full outline-none focus:ring-2 focus:ring-primary/50"
                            />
                        </div>

                        <div className="flex justify-end gap-3 mt-6">
                            <Button variant="outline" onClick={() => setShowModal(false)}>Annulla</Button>
                            <Button onClick={handleSave}>Salva</Button>
                        </div>
                    </div>
                </div>
            )}
        </>
    );
}
