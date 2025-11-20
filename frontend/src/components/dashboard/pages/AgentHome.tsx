import AgentRequestContainer from "../components/AgentRequestContainer";

function AgentHome() {
    return (
        <div>
            <h2 className="text-2xl font-bold">Ciao Sofia!</h2>
            <h3 className="font-medium">Ecco le tue richieste:</h3>
            <AgentRequestContainer />

        </div>
    );
}

export default AgentHome;