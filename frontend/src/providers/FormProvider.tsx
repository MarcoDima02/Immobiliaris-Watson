/**
 * Node modules
 */
import { useState, type ReactNode } from 'react';
import FormContext from '@/context/FormContext';

export type FormPayload = {
  // mandatory
  tipologia: string;
  superficie?: number;
  indirizzo: string;
  provincia: string;
  citta: string;
  cap: string;
  nStanze?: number;
  nBagni?: number;
  finalitaRichiesta: 'estimate' | 'officialDocument';

  // optional
  piano?: number;
  pianiTotali?: number;
  ascensore?: boolean;
  garage?: boolean;
  superficieGarage?: number;
  superficieBalconeTerrazzo?: number;
  giardino?: boolean;
  superficieGiardino?: number; 
  cantina?: boolean;
  superficieCantina?: number;
  tipoRiscaldamento?: string;
  classeEnergetica?: string; 
  annoCostruzione?: number;
  condizione?: string;
  esposizione?: string;

  // geolocation from mapbox
  latitude?: number;
  longitude?: number;

  // contacts
  emailUtente?: string;
  telefonoUtente?: string;
};

const defaultPayload: FormPayload = {
  tipologia: '',
  superficie: undefined,
  indirizzo: '',
  provincia: '',
  citta: '',
  cap: '',
  nStanze: undefined,
  nBagni: undefined,
  finalitaRichiesta: 'estimate',
};

const FormProvider = ({ children }: { children: ReactNode }) => {
  const [data, setDataState] = useState<FormPayload>(defaultPayload);

  const setData = (patch: Partial<FormPayload>) => {
    setDataState((prev) => ({ ...prev, ...patch }));
  };

  const reset = () => setDataState(defaultPayload);

  return (
    <FormContext.Provider value={{ data, setData, reset }}>
      {children}
    </FormContext.Provider>
  );
};

export default FormProvider;
