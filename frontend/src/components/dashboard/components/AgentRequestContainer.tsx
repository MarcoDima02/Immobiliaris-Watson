import AgentRequest from "./AgentRequest";

function AgentRequestContainer() {
    return (
        <div className="flex gap-5 py-4 flex-wrap" >
            <AgentRequest num={5} type=""> Prese in carico </AgentRequest>
            <AgentRequest num={2} type="completato"> Completate con successo </AgentRequest>
            <AgentRequest num={1} type="archiviato"> Archiviate </AgentRequest>
        </div >
    );
}

export default AgentRequestContainer;