/**
 * Node modules
 */
import { create } from 'zustand';
import { persist } from 'zustand/middleware';

/**
 * Types
 */
import type { User } from '@/types';
import type { AgenteRichiestaDTO } from '@/types';

/**
 * Fetching functions
 */
import { fetchAgentDashboardApi } from '@/api/agente';

interface AuthState {
  user: User | null;
  isAuthenticated: boolean;
  agentDashboard: AgenteRichiestaDTO[] | null;
  login: (user: User) => void;
  logout: () => void;
  isLoading: boolean;
  setLoading: (value: boolean) => void;
  loadAgentDashboard: () => Promise<void>;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set, get) => ({
      user: null,
      isAuthenticated: false,
      isLoading: false,
      agentDashboard: null,

      login: (user) => set({ user, isAuthenticated: true }),
      logout: () => set({ user: null, isAuthenticated: false }),
      setLoading: (value) => set({ isLoading: value }),

      loadAgentDashboard: async () => {
        const user = get().user;

        if (!user || user.ruolo !== 'AGENTE') return;

        const data = await fetchAgentDashboardApi(user.idUtente);
        console.log('Dashboard fetch result:', data);
        set({ agentDashboard: data })
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
