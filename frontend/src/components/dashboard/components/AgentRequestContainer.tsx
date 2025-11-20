function AgentRequestContainer() {
    return (
        <div className = "flex gap-5 py-4 flex-wrap" >
                <div className="bg-white rounded-2xl shadow-md flex flex-col pb-3 pt-6 px-6 gap-5 w-64">
                    <p className="font-extrabold text-primary text-6xl">2</p>
                    <p className="text-neutral-400">Prese in carico</p>
                </div>
                <div className="bg-white rounded-2xl shadow-md flex flex-col pb-3 pt-6 px-6 gap-5 w-64">
                    <p className="font-extrabold text-secondary text-6xl">28</p>
                    <p className="text-neutral-400">Completate con successo</p>
                </div>
                <div className="bg-white rounded-2xl shadow-md flex flex-col pb-3 pt-6 px-6 gap-5 w-64">
                    <p className="font-extrabold text-neutral-400 text-6xl">14</p>
                    <p className="text-neutral-400">Archiviate</p>
                </div>
            </div >
    );
}

export default AgentRequestContainer;