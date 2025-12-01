import { Link } from "react-router";
import AgentRequest from "./AgentRequest";

function AgentRequestContainer({ requests }: { requests: any }) {

    return (
        <div className="flex gap-5 py-4 flex-wrap" >
            <Link state={{requests}} to={"/backoffice/agent/myRequests"}>
                <AgentRequest num={5} type=""> Prese in carico </AgentRequest>
            </Link>
            <AgentRequest num={2} type="completato"> Completate con successo </AgentRequest>
            <AgentRequest num={1} type="archiviato"> Archiviate </AgentRequest>
        </div >
    );
}

export default AgentRequestContainer;