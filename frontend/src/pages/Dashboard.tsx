import AgentNavbar from "@/components/dashboard/components/AgentNavbar";
import AgentHome from "@/components/dashboard/pages/AgentHome";
import RequestDetails from "@/components/dashboard/pages/RequestDetails";
import WaitingRequestPage from "@/components/dashboard/pages/WaitingRequestPage";



export default function Dashboard() {
    return (
        <>

            <div className="flex-row">
                <div>
                    <AgentNavbar />
                </div>
                <div className="ms-13 md:ms-64 p-4 -mt-16">
                    {/* <AgentHome /> */}
                    {/* <WaitingRequestPage /> */}
                    <RequestDetails />

                </div>
            </div>



        </>


    );
}