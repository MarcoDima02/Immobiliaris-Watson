/**
 * Node modules
 */
import { useEffect } from 'react';

/**
 * Store
 */
import { useAuthStore } from '@/store/auth.store';
import AgentRequestContainer from '@/components/agentDashboard/AgentRequestContainer';

/**
 * Components
 */
import {
  Table,
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
} from "@/components/ui/table";

import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";

/**
 * Types
 */
import type { AgenteRichiestaDTO } from '@/types';

const getActivities = (dashboard: AgenteRichiestaDTO[]) => {
  return dashboard
    .map((r) => {
      let activity = "Richiesta creata";
      let activityDate = r.dataRichiesta;

      if (r.dataAppuntamento) {
        activity = "Appuntamento fissato";
        activityDate = r.dataAppuntamento;
      }

      if (r.statoRichiesta === "COMPLETATA") {
        activity = "Richiesta completata";
        activityDate = r.dataRichiesta; // puoi sostituire se hai una data completamento
      }

      if (r.statoRichiesta === "ANNULLATA") {
        activity = "Richiesta annullata";
        activityDate = r.dataRichiesta;
      }

      return {
        id: r.idRichiesta,
        cliente: `${r.nomeUtente} ${r.cognomeUtente}`,
        immobile: `${r.tipologia} – ${r.citta}`,
        stato: r.statoRichiesta,
        activity,
        date: activityDate,
      };
    })
    .sort(
      (a, b) =>
        new Date(b.date || "").getTime() -
        new Date(a.date || "").getTime()
    )
    .slice(0, 10);
};


const AgentDashboard = () => {
  const dashboard = useAuthStore((s) => s.agentDashboard);
  const loadDashboard = useAuthStore((s) => s.loadAgentDashboard);
  const user = useAuthStore((s) => s.user);

  useEffect(() => {
    if (user?.ruolo === 'AGENTE') {
      // Forza sempre il reload dei dati
      loadDashboard();
    }
  }, [user]);

  if (dashboard === null) return <p>Caricamento dashboard...</p>;
  if (dashboard.length === 0) return <p>Nessun contratto trovato</p>;



  return (
    <>
      <div>
        <h2 className="text-2xl font-bold">Bentornat* {user?.nome}</h2>
        <h3 className="font-medium">Ecco le tue richieste:</h3>
        <AgentRequestContainer requests={dashboard} />
      </div>

         {/* ==== ULTIME ATTIVITÀ ==== */}
      <h3 className="text-xl font-semibold mt-4 mb-2">Ultime attività</h3>

        <Card className="mt-6">
        <CardHeader>
          <CardTitle>Ultime attività</CardTitle>
        </CardHeader>

        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Data</TableHead>
                <TableHead>Attività</TableHead>
                <TableHead>Cliente</TableHead>
                <TableHead>Immobile</TableHead>
                <TableHead>Stato</TableHead>
              </TableRow>
            </TableHeader>

            <TableBody>
              {getActivities(dashboard).map((a) => (
                <TableRow key={a.id}>
                  <TableCell>
                    {a.date
                      ? new Date(a.date).toLocaleDateString()
                      : "-"}
                  </TableCell>

                  <TableCell>
                    <Badge
                      variant="outline"
                      className="capitalize"
                    >
                      {a.activity}
                    </Badge>
                  </TableCell>

                  <TableCell>{a.cliente}</TableCell>

                  <TableCell>{a.immobile}</TableCell>

                  <TableCell>
                    <Badge
                      className={
                        a.stato === "COMPLETATA"
                          ? "bg-emerald-500"
                          : a.stato === "ANNULLATA"
                          ? "bg-red-500"
                          : a.stato === "IN_ELABORAZIONE"
                          ? "bg-yellow-500"
                          : "bg-gray-500"
                      }
                    >
                      {a.stato}
                    </Badge>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </>
  );
};

export default AgentDashboard;
