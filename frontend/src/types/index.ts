
export interface AuthState {
  isAuthenticated: boolean;
}

export interface LoaderData {
  auth: AuthState;
}

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

export interface AgenteRichiestaDTO {
  idContratto?: number;
  contrattoNumero?: string;

  immobile?: {
    idImmobile: number;
    indirizzo?: string;
    citta?: string;
    provincia?: string;
    tipologia?: string;
  };

  richiesta?: {
    idRichiesta: number;
    dataRichiesta?: string;
    stato?: string;
  };

  proprietario?: User;

  superfici?: Record<string, number>;
}
