import AgentNavbar from "@/components/dashboard/components/AgentNavbar";
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
                    <WaitingRequestPage />

                </div>
            </div>



        </>


    );
}