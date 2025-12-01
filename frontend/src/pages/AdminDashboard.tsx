import { useEffect, useState } from "react";
import { useAuthStore } from "@/store/auth.store";

export default function AdminDashboard() {
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

  // Caricamento automatico una volta entrati nella dashboard
  useEffect(() => {
    if (user?.ruolo !== "AMMINISTRATORE") return;

    loadAdminUtenti();
    loadAdminImmobili();
    loadAdminContratti();
    loadAdminRichieste();
    loadAdminVendite();
    loadAdminImmagini();
  }, [user]);

  if (!user) return <div>Non autenticato…</div>;
  if (user.ruolo !== "AMMINISTRATORE") return <div>Accesso negato</div>;

  return (
     <div style={{ padding: "20px" }}>
      <h1 className="mb-6">Benvenut* Admin: {user.nome} {user.cognome}</h1>

      {/* MENU TABS */}
      <div style={{ display: "flex", gap: "10px", marginBottom: "20px" }}>
        {["utenti", "immobili", "contratti", "richieste", "vendite", "immagini"].map((tab) => (
          <button
            key={tab}
            onClick={() => setActiveTab(tab as any)}
            style={{
              padding: "10px",
              borderRadius: "6px",
              border: "1px solid #ccc",
              background: activeTab === tab ? "#333" : "white",
              color: activeTab === tab ? "white" : "black",
              cursor: "pointer",
            }}
          >
            {tab.toUpperCase()}
          </button>
        ))}
      </div>

      {/* SEZIONI */}

      {activeTab === "utenti" && (
        <Section title="Utenti" data={adminUtenti} empty="Nessun utente trovato" />
      )}

      {activeTab === "immobili" && (
        <Section title="Immobili" data={adminImmobili} empty="Nessun immobile trovato" />
      )}

      {activeTab === "contratti" && (
        <Section title="Contratti" data={adminContratti} empty="Nessun contratto trovato" />
      )}

      {activeTab === "richieste" && (
        <Section title="Richieste" data={adminRichieste} empty="Nessuna richiesta trovata" />
      )}

      {activeTab === "vendite" && (
        <Section title="Vendite" data={adminVendite} empty="Nessuna vendita trovata" />
      )}

      {activeTab === "immagini" && (
        <Section title="Immagini" data={adminImmagini} empty="Nessuna immagine trovata" />
      )}

    </div>
  );
}

function Section({ title, data, empty }: { title: string; data: any[] | null; empty: string }) {
  return (
    <div>
      <h2 style={{ marginBottom: "10px" }}>{title}</h2>

      {!data || data.length === 0 ? (
        <p>{empty}</p>
      ) : (
        <table style={{ width: "100%", borderCollapse: "collapse" }}>
          <thead>
            <tr>
              {Object.keys(data[0]).map((key) => (
                <th key={key} style={{ borderBottom: "1px solid #ccc", textAlign: "left", padding: "8px" }}>
                  {key}
                </th>
              ))}
            </tr>
          </thead>

          <tbody>
            {data.map((item, idx) => (
              <tr key={idx}>
                {Object.values(item).map((value: any, i) => (
                  <td key={i} style={{ borderBottom: "1px solid #eee", padding: "8px" }}>
                    {typeof value === "object" ? JSON.stringify(value) : String(value)}
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}