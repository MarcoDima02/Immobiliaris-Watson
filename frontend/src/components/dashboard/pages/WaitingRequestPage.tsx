import { Button } from "@/components/ui/button";
import RequestDiv from "../components/RequestDiv";

function WaitingRequestPage() {

    let requests = [
        { nome: "Mario Rossi", data: "12/07/25", locali: 2, bagni: 1, mq: 85, stato: "In attesa di valutazione", agente: "Luca Verdi" },
        { nome: "Luigi Bianchi", data: "14/07/25", locali: 3, bagni: 2, mq: 120, stato: "Completato" },
        { nome: "Anna Verdi", data: "15/07/25", locali: 1, bagni: 1, mq: 60, stato: "In attesa di valutazione" },
        { nome: "Giovanni Neri", data: "16/07/25", locali: 4, bagni: 3, mq: 200, stato: "Completato" },
        { nome: "Francesca Gialli", data: "18/07/25", locali: 2, bagni: 1, mq: 75, stato: "In attesa di valutazione" }
    ];

    return (
        <>
            <Button>Indietro</Button>
            <h2 className="text-2xl font-bold mt-5">Richieste prese in carico:</h2>

            <div className="flex gap-4 flex-wrap py-4">
                {requests.map((request, index) => (

                    <RequestDiv index={index} request={request} />
                ))}
            </div>
        </>
    );
}

export default WaitingRequestPage;