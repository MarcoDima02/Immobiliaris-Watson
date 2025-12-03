// ---- PRENDI IN CARICO ----
export const prendiInCaricoRichiesta = async (idAgente: number, idRichiesta: number) => {
  const response = await fetch(
    `http://localhost:8080/api/agente/${idAgente}/richieste/${idRichiesta}/prendi-in-carico`,
    { method: 'POST', headers: { 'Content-Type': 'application/json' } }
  );

  if (!response.ok) {
    throw new Error(await response.text());
  }

  return response.json();
};

// ---- MODIFICA STATO RICHIESTA ----
export const aggiornaStatoRichiesta = async (id: number, nuovoStato: string) => {
 
  const getRes = await fetch(`http://localhost:8080/api/richieste/${id}`);
  if (!getRes.ok) throw new Error("Errore nel recupero della richiesta");

  const richiestaCompleta = await getRes.json();

  
  const richiestaAggiornata = {
    ...richiestaCompleta,
    stato: nuovoStato, 
  };

  
  const putRes = await fetch(`http://localhost:8080/api/richieste/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(richiestaAggiornata),
  });

  if (!putRes.ok) throw new Error(await putRes.text());

  return putRes.json();
};


// ---- UPLOAD CONTRATTO ----
export const uploadContrattoPDF = async (file: File) => {
  const formData = new FormData();
  formData.append("file", file); 

  const response = await fetch(
    `http://localhost:8080/api/upload/pdf`, 
    {
      method: 'POST', 
      body: formData,
    }
  );

  if (!response.ok) {
    throw new Error(await response.text());
  }

  return response.text(); 
};
