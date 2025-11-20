import AgentHome from "@/components/dashboard/AgentHome";
import AgentNavbar from "@/components/dashboard/components/agentNavbar";



export default function Dashboard() {
    return (
        <>

            <div className="flex-row">
                <div>
                    <AgentNavbar />
                </div>
                <div className="ms-16 md:ms-64 p-4">
                    <AgentHome />
                </div>
            </div>



        </>


    );
}