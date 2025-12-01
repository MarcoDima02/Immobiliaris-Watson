/**
 * Node modules
 */
import { create } from 'zustand';
import { persist } from 'zustand/middleware';

/**
 * Types
 */
import type {
  AgenteRichiestaDTO,
  User,
  UtenteDto,
  ImmobileDto,
  ContrattoDto,
  RichiestaDto,
  VenditaDto,
  ImmagineDto,
} from '@/types';

/**
 * Fetching functions
 */
import { fetchAgentDashboardApi } from '@/api/agente';
import {
  fetchAdminUtenti,
  fetchAdminContratti,
  fetchAdminImmobili,
  fetchAdminRichieste,
  fetchAdminVendite,
  fetchAdminImmagini,
  type AdminUtentiFilters,
  type AdminContrattiFilters,
  type AdminImmobiliFilters,
  type AdminImmaginiFilters,
  type AdminRichiesteFilters,
  type AdminVenditeFilters,
} from '@/api/admin';

interface AuthState {
  user: User | null;
  isAuthenticated: boolean;
  isLoading: boolean;

  // Agent data
  agentDashboard: AgenteRichiestaDTO[] | null;

  // Admin data
  adminUtenti: UtenteDto[] | null;
  adminImmobili: ImmobileDto[] | null;
  adminContratti: ContrattoDto[] | null;
  adminRichieste: RichiestaDto[] | null;
  adminVendite: VenditaDto[] | null;
  adminImmagini: ImmagineDto[] | null;

  // Actions
  login: (user: User) => void;
  logout: () => void;
  setLoading: (value: boolean) => void;

  loadAgentDashboard: () => Promise<void>;

  loadAdminUtenti: (filters?: AdminUtentiFilters) => Promise<void>;
  loadAdminImmobili: (filters?: AdminImmobiliFilters) => Promise<void>;
  loadAdminContratti: (filters?: AdminContrattiFilters) => Promise<void>;
  loadAdminRichieste: (filters?: AdminRichiesteFilters) => Promise<void>;
  loadAdminVendite: (filters?: AdminVenditeFilters) => Promise<void>;
  loadAdminImmagini: (filters?: AdminImmaginiFilters) => Promise<void>;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set, get) => ({
      user: null,
      isAuthenticated: false,
      isLoading: false,

      agentDashboard: null,

      adminUtenti: null,
      adminImmobili: null,
      adminContratti: null,
      adminRichieste: null,
      adminVendite: null,
      adminImmagini: null,

      login: (user) => set({ user, isAuthenticated: true }),
      logout: () =>
        set({
          user: null,
          isAuthenticated: false,
          adminUtenti: null,
          adminImmobili: null,
          adminContratti: null,
          adminRichieste: null,
          adminVendite: null,
          adminImmagini: null,
        }),
      setLoading: (value) => set({ isLoading: value }),

        // --- LOADERS Agent ---
      loadAgentDashboard: async () => {
        const user = get().user;

        if (!user || user.ruolo !== 'AGENTE') return;

        const data = await fetchAgentDashboardApi(user.idUtente);
        console.log('Dashboard fetch result:', data);
        set({ agentDashboard: data });
      },

      // --- LOADERS ADMIN ---
      loadAdminUtenti: async (filters) => {
        if (get().user?.ruolo !== 'AMMINISTRATORE') return;
        const data = await fetchAdminUtenti(filters);
        set({ adminUtenti: data })
      },

      loadAdminImmobili: async (filters) => {
        if (get().user?.ruolo !== 'AMMINISTRATORE') return;
        const data = await fetchAdminImmobili(filters);
        set({ adminImmobili: data })
      },

      loadAdminContratti: async (filters) => {
        if (get().user?.ruolo !== 'AMMINISTRATORE') return;
        const data = await fetchAdminContratti(filters);
        set({ adminContratti: data })
      },

      loadAdminRichieste: async (filters) => {
        if (get().user?.ruolo !== 'AMMINISTRATORE') return;
        const data = await fetchAdminRichieste(filters);
        set({ adminRichieste: data })
      },

      loadAdminVendite: async (filters) => {
        if (get().user?.ruolo !== 'AMMINISTRATORE') return;
        const data = await fetchAdminVendite(filters);
        set({ adminVendite: data })
      },

      loadAdminImmagini: async (filters) => {
        if (get().user?.ruolo !== 'AMMINISTRATORE') return;
        const data = await fetchAdminImmagini(filters);
        set({ adminImmagini: data })
      }
    }),
    {
      name: 'auth-storage',
      partialize: (state) => ({
        user: state.user,
        isAuthenticated: state.isAuthenticated,
        // Escludi agentDashboard dal localStorage - sempre fresh
      }),
    }
  )
);
