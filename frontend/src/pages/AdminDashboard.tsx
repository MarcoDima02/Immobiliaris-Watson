import { useEffect, useState } from "react";
import { useAuthStore } from "@/store/auth.store";
import AdminInfoContainer from "@/components/adminDashboard/AdminInfoContainer";
import { Link } from "react-router";

export default function AdminDashboard({ type }: { type: string }) {
  const {
    user,
    adminUtenti,
    adminContratti,

    loadAdminUtenti,
    loadAdminImmobili,
    loadAdminContratti,
    loadAdminRichieste,
    loadAdminVendite,
    loadAdminImmagini,
  } = useAuthStore();



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
      {/* SEZIONI */}

      {type === "dashboard" && (
        <div className="w-full p-6">
          <h1 className="text-3xl font-bold text-black mb-4">
            Ciao {user.nome}!
            <br />
            <span className="text-xl">Esplora la dashboard da amministratore</span>
          </h1>

          <p className="text-zinc-600 mb-6">
            Da qui puoi gestire richieste, utenti e immobili.
          </p>


          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-4">

            {/* Richieste */}
            <Link
              to="/backoffice/admin/richieste"
              className="bg-white shadow-md rounded-xl p-5 hover:shadow-lg hover:-translate-y-1 transition-all"
            >
              <p className="text-xl font-semibold text-black mb-1">Richieste</p>
              <p className="text-zinc-500 text-sm">
                Controlla e gestisci le nuove richieste.
              </p>
            </Link>

            {/* Utenti */}
            <Link
              to="/backoffice/admin/utenti"
              className="bg-white shadow-md rounded-xl p-5 hover:shadow-lg hover:-translate-y-1 transition-all"
            >
              <p className="text-xl font-semibold text-black mb-1">Utenti</p>
              <p className="text-zinc-500 text-sm">
                Gestisci gli utenti registrati.
              </p>
            </Link>

            {/* Immobili */}
            <Link
              to="/backoffice/admin/contratti"
              className="bg-white shadow-md rounded-xl p-5 hover:shadow-lg hover:-translate-y-1 transition-all"
            >
              <p className="text-xl font-semibold text-black mb-1">Contratti</p>
              <p className="text-zinc-500 text-sm">
                Visualizza e aggiorna i contratti.
              </p>
            </Link>

          </div>
        </div>


      )
      }

      {
        type === "utente" && (
          <>
            <h2 className="text-2xl font-bold mb-5"> Gestisci gli utenti</h2>
            <AdminInfoContainer data={adminUtenti} type="utenti" />
          </>
        )
      }

      {
        type === "richiesta" && (
          <>
            <h2 className="text-2xl font-bold mb-5"> Gestisci le richieste</h2>
            <AdminInfoContainer data={adminImmobiliDettagli} type="richieste" />
          </>
        )
      }

      {
        type === "contratto" && (
          <>
            <h2 className="text-2xl font-bold mb-5"> Gestisci i contratti</h2>
            <AdminInfoContainer data={adminContratti} type="contratti" />
          </>
        )
      }


    </div >
  );
}
