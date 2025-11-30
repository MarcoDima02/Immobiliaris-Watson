/**
 * Types
 */
import type { AgenteRichiestaDTO } from '@/types';

export async function fetchAgentDashboardApi(idAgente: number) {
  const res = await fetch(
    `${import.meta.env.VITE_API_URL}/agente/dashboard/${idAgente}`,
    {
      method: 'GET',
      credentials: 'include',
    }
  );

  if (!res.ok) {
    throw new Error(`Errore nella richiesta: ${res.status}`);
  }

  const data = (await res.json()) as AgenteRichiestaDTO[];

  return data;
}
