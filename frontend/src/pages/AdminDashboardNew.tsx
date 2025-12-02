/**
 * Node modules
 */
import { useEffect } from 'react';

/**
 * Store
 */
import { useAuthStore } from '@/store/auth.store';
import AgentRequestContainer from '@/components/agentDashboard/AgentRequestContainer';

const AdminDashboardNew = () => {
  const user = useAuthStore((s) => s.user);

  const {
    adminUtenti,
    adminImmobili,
    adminContratti,
    adminRichieste,
    adminVendite,
    adminImmagini,

    loadAdminUtenti,
    loadAdminImmobili,
    loadAdminContratti,
    loadAdminRichieste,
    loadAdminVendite,
    loadAdminImmagini,
  } = useAuthStore();


  useEffect(() => {
    if (user?.ruolo !== "AMMINISTRATORE") return;

    loadAdminUtenti();
    loadAdminImmobili();
    loadAdminContratti();
    loadAdminRichieste();
    loadAdminVendite();
    loadAdminImmagini();


  }, [user]);

  if (adminUtenti === null) return <p>Caricamento dashboard...</p>;
  if (adminUtenti.length === 0) return <p>Nessun contratto trovato</p>;


  return (
    <>
      <div>
        <h2 className="text-2xl font-bold">Bentornat* {user?.nome}</h2>
        <h3 className="font-medium">Ecco le tue richieste:</h3>
        {/* <AgentRequestContainer requests={dashboard} /> */}
      </div>
    </>
  );
};

export default AdminDashboardNew;
