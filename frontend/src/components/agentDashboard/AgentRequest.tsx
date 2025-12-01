function AgentRequest({ children, num, type }: { children: React.ReactNode, num: number, type: string }) {
    return (
        <>
            {!type && (
                <div className="bg-white rounded-2xl shadow-md flex flex-col pb-3 pt-6 px-6 gap-5 w-64">
                    <p className="font-extrabold text-primary text-6xl"> {num} </p>
                    <p className="text-neutral-400"> {children} </p>
                </div>
            )}

            {type === "completato" && (
                <div className="bg-white rounded-2xl shadow-md flex flex-col pb-3 pt-6 px-6 gap-5 w-64">
                    <p className="font-extrabold text-secondary text-6xl"> {num} </p>
                    <p className="text-neutral-400"> {children} </p>
                </div>
            )}

            {type === "archiviato" && (
                <div className="bg-white rounded-2xl shadow-md flex flex-col pb-3 pt-6 px-6 gap-5 w-64">
                    <p className="font-extrabold text-neutral-400 text-6xl"> {num} </p>
                    <p className="text-neutral-400"> {children} </p>
                </div>
            )}


        </>


    )
}

export default AgentRequest;