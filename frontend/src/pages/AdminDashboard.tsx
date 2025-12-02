import { useEffect, useState } from "react";
import { useAuthStore } from "@/store/auth.store";
import AdminInfoContainer from "@/components/adminDashboard/AdminInfoContainer";

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
        <AdminInfoContainer data={adminUtenti} />
      )}

      {activeTab === "immobili" && (
        <AdminInfoContainer data={adminImmobili} />
      )}

      {activeTab === "contratti" && (
        <AdminInfoContainer data={adminContratti} />
      )}

      {activeTab === "richieste" && (
        <AdminInfoContainer data={adminRichieste} />
      )}

      {activeTab === "vendite" && (
        <AdminInfoContainer data={adminVendite} />
      )}

      {activeTab === "immagini" && (
        <AdminInfoContainer data={adminImmagini} />
      )}

    </div>
  );
}
