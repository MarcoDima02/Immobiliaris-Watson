import { useEffect, useState } from "react";
import { useAuthStore } from "@/store/auth.store";
import AdminInfoContainer from "@/components/adminDashboard/AdminInfoContainer";

export default function AdminDashboard({ type }: { type: string }) {
  const {
    user,
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


  const [activeTab, setActiveTab] = useState<"utenti" | "immobili" | "contratti" | "richieste" | "vendite" | "immagini">("utenti");

  const [adminImmobiliDettagli, setAdminImmobiliDettagli] = useState(null);
  // Caricamento automatico una volta entrati nella dashboard
  useEffect(() => {
    if (user?.ruolo !== "AMMINISTRATORE") return;

    loadAdminUtenti();
    loadAdminImmobili();
    loadAdminContratti();
    loadAdminRichieste();
    loadAdminVendite();
    loadAdminImmagini();

    async function fetchAdminRichiesteDettagli() {
      try {
        const response = await fetch(
          `${import.meta.env.VITE_API_URL}/admin/dashboard/richieste/dettagli`,
          {
            credentials: 'include',
          }
        );


        setAdminImmobiliDettagli(await response.json())

      } catch (error) {
        console.error('Errore nella fetch:', error);
      }
    }

    fetchAdminRichiesteDettagli();

  }, [user]);

  if (!user) return <div>Non autenticato…</div>;
  if (user.ruolo !== "AMMINISTRATORE") return <div>Accesso negato</div>;



  return (
    <div style={{ padding: "20px" }}>
      <h1 className="mb-6">Benvenut* Admin: {user.nome} {user.cognome}</h1>

      {/* SEZIONI */}

      {type === "utente" && (
        <AdminInfoContainer data={adminUtenti} type="utenti" />
      )}

      {type === "richiesta" && (
        <AdminInfoContainer data={adminImmobiliDettagli} type="richieste" />
      )}

      {type === "contratto" && (
        <AdminInfoContainer data={adminContratti} type="contratti" />
      )}


    </div>
  );
}
