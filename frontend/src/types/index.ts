
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

// src/types/admin.ts

export interface UtenteDto {
  idUtente: number;
  nome: string;
  cognome: string;
  email: string;
  telefono: string;
  ruolo: 'PROPRIETARIO' | 'AGENTE' | 'AMMINISTRATORE';
  verificaEmail: boolean;
  consensoPrivacy: boolean;
}

export interface ImmobileDto {
  idImmobile: number;
  idProprietario: number | null;
  tipologia: 'APPARTAMENTO' | 'VILLA' | 'ATTICO' | 'LOFT' | 'BILOCALE' | 'TRILOCALE' | 'QUADRILOCALE' | null;
  indirizzo: string;
  citta: string;
  provincia: string;
  cap: string;
  latitudine: number | null;
  longitudine: number | null;
  stato: 'DISPONIBILE' | 'IN_TRATTATIVA' | 'VENDUTO' | 'RITIRATO' | null;
}

export interface ContrattoDto {
  idContratto: number;
  idImmobile: number | null;
  idAgente: number | null;
  tipoContratto: 'VENDITA' | 'AFFITTO' | 'ACQUISIZIONE' | null;
  dataContratto: string;
  dataScadenzaContratto: string | null;
  pathContrattoPDF: string | null;
}

export interface RichiestaDto {
  idRichiesta: number;
  idUtente: number | null;
  idImmobile: number | null;
  dataRichiesta: string;
  dataAppuntamento: string | null;
  stato: 'IN_ATTESA' | 'IN_ELABORAZIONE' | 'COMPLETATA' | 'ANNULLATA' | null;
  noteUtente: string | null;
  motivoAnnullamento: string | null;
}

export interface VenditaDto {
  idVendita: number;
  idContratto: number | null;
  idImmobile: number | null;
  idUtente: number | null;
  commissionePercentuale: number | null;
}

export interface ImmagineDto {
  idImmagine: number;
  idImmobile: number | null;
  url: string;
  nomeFile: string;
  descrizione: string | null;
  copertina: boolean;
  ordinamento: number | null;
  dimensioneKb: number | null;
}

