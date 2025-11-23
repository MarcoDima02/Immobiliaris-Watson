/**
 * Node modules
 */
import { create } from 'zustand';
import { persist } from 'zustand/middleware';

export type UserRole = 'PROPRIETARIO' | 'AGENTE' | 'AMMINISTRATORE';

export interface User {
  idUtente: number;
  nome: string;
  cognome: string;
  telefono?: string;
  email: string;
  ruolo: UserRole;
  verificaEmail: boolean;
  consensoPrivacy: boolean;
}

interface AuthState {
  user: User | null;
  isAuthenticated: boolean;
  login: (user: User) => void;
  logout: () => void;
  isLoading: boolean;
  setLoading: (value: boolean) => void;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      user: null,
      isAuthenticated: false,
      isLoading: false,

      login: (user) => set({ user, isAuthenticated: true }),
      logout: () => set({ user: null, isAuthenticated: false }),
      setLoading: (value) => set({ isLoading: value }),
    }),
    {
      name: 'auth-storage',
    }
  )
);
