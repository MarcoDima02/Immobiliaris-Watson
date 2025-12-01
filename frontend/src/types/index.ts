
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
  // Contratto
  idContratto?: number;
  tipoContratto?: string;
  dataContratto?: string;
  dataScadenzaContratto?: string;
  pathContrattoPDF?: string;

  // Immobile
  idImmobile?: number;
  tipologia?: string;
  indirizzo?: string;
  citta?: string;
  provincia?: string;
  cap?: string;
  stato?: string;

  // Dettagli Immobile
  nStanze?: number;
  nBagni?: number;
  nPiano?: number;
  nPianiImmobile?: number;
  balconeTerrazzo?: boolean;
  giardino?: boolean;
  garage?: boolean;
  ascensore?: boolean;
  cantina?: boolean;
  tipoRiscaldamento?: string;
  annoCostruzione?: number;
  condizioneImmobile?: string;
  classeEnergetica?: string;

  // Superfici
  superficieMq?: number;
  superficieBalconeTerrazzo?: number;
  superficieGiardino?: number;
  superficieGarage?: number;
  superficieCantina?: number;

  // Richiesta
  idRichiesta?: number;
  dataRichiesta?: string;
  dataAppuntamento?: string;
  statoRichiesta?: string;
  noteUtente?: string;
  motivoAnnullamento?: string;

  // Utente (cliente che ha fatto la richiesta)
  idUtente?: number;
  nomeUtente?: string;
  cognomeUtente?: string;
  telefonoUtente?: string;
  emailUtente?: string;

  // Valutazione
  idValutazione?: number;
  valoreBase?: number;
  fattoreAggiustamento?: number;
  valoreMedio?: number;
  valoreMin?: number;
  valoreMax?: number;
  confidence?: number;
}
