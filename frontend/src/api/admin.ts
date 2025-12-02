/**
 * Types
 */
import type {
  UtenteDto,
  ImmobileDto,
  ContrattoDto,
  RichiestaDto,
  VenditaDto,
  ImmagineDto,
} from '@/types';

const API_URL = import.meta.env.VITE_API_URL;

export interface AdminUtentiFilters {
  nome?: string;
  cognome?: string;
  email?: string;
  ruolo?: 'PROPRIETARIO' | 'AGENTE' | 'AMMINISTRATORE';
  telefono?: string;
}

export interface AdminImmobiliFilters {
  citta?: string;
  provincia?: string;
  tipologia?: ImmobileDto['tipologia'];
  stato?: ImmobileDto['stato'];
  proprietario?: number;
}

export interface AdminContrattiFilters {
  tipo?: ContrattoDto['tipoContratto'];
  immobile?: number;
  agente?: number;
}

export interface AdminRichiesteFilters {
  stato?: RichiestaDto['stato'];
  utente?: number;
  immobile?: number;
}

export interface AdminRichiesteDettagliFilters {
  stato?: RichiestaDto['stato'];
  utente?: number;
  immobile?: number;
}

export interface AdminVenditeFilters {
  contratto?: number;
  immobile?: number;
  utente?: number;
}

export interface AdminImmaginiFilters {
  immobile?: number;
  copertina?: boolean;
}

function buildQuery(filters?: Record<string, any>): string {
  if (!filters) return '';

  const params = new URLSearchParams();

  Object.entries(filters).forEach(([key, value]) => {
    if (value !== undefined && value !== '' && value !== null) {
      params.append(key, String(value));
    }
  });

  const query = params.toString();
  return query.length > 0 ? `?${query}` : '';
}

// --- ENDPOINTS ---

export async function fetchAdminUtenti(
  filters?: AdminUtentiFilters
): Promise<UtenteDto[]> {
  const response = await fetch(
    `${API_URL}/admin/dashboard/utenti${buildQuery(filters)}`,
    {
      credentials: 'include',
    }
  );

  if (!response.ok) throw new Error('Errore durante il fetch degli utenti');
  return response.json();
}

export async function fetchAdminImmobili(
  filters?: AdminImmobiliFilters
): Promise<ImmobileDto[]> {
  const response = await fetch(
    `${API_URL}/admin/dashboard/immobili${buildQuery(filters)}`,
    {
      credentials: 'include',
    }
  );

  if (!response.ok) throw new Error('Errore durante il fetch degli immobili');
  return response.json();
}

export async function fetchAdminContratti(
  filters?: AdminContrattiFilters
): Promise<ContrattoDto[]> {
  const response = await fetch(
    `${API_URL}/admin/dashboard/contratti${buildQuery(filters)}`,
    {
      credentials: 'include',
    }
  );

  if (!response.ok) throw new Error('Errore durante il fetch degli contratti');
  return response.json();
}

export async function fetchAdminRichieste(
  filters?: AdminRichiesteFilters
): Promise<RichiestaDto[]> {
  const response = await fetch(
    `${API_URL}/admin/dashboard/richieste${buildQuery(filters)}`,
    {
      credentials: 'include',
    }
  );

  if (!response.ok) throw new Error('Errore durante il fetch degli richieste');
  return response.json();
}

export async function fetchAdminRichiesteDettagli(
  filters?: AdminRichiesteFilters
): Promise<RichiestaDto[]> {
  const response = await fetch(
    `${API_URL}/admin/dashboard/richieste/dettagli${buildQuery(filters)}`,
    {
      credentials: 'include',
    }
  );

  if (!response.ok) throw new Error('Errore durante il fetch degli richieste');
  return response.json();
}

export async function fetchAdminVendite(
  filters?: AdminVenditeFilters
): Promise<VenditaDto[]> {
  const response = await fetch(
    `${API_URL}/admin/dashboard/vendite${buildQuery(filters)}`,
    {
      credentials: 'include',
    }
  );

  if (!response.ok) throw new Error('Errore durante il fetch degli vendite');
  return response.json();
}

export async function fetchAdminImmagini(
  filters?: AdminImmaginiFilters
): Promise<ImmagineDto[]> {
  const response = await fetch(
    `${API_URL}/admin/dashboard/immagini${buildQuery(filters)}`,
    {
      credentials: 'include',
    }
  );

  if (!response.ok) throw new Error('Errore durante il fetch degli immagini');
  return response.json();
}