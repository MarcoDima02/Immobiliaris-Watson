/**
 * Node modules
 */
import { useState, type ReactNode } from 'react';

/**
 * Context
 */
import FormContext from '@/context/FormContext';

export type FormPayload = {
  // mandatory
  propertyType: string;
  area: number | null;
  address: string;
  province: string;
  city: string;
  cap: string;
  rooms: number | null;
  bathrooms: number | null;
  requestedPurpose: 'estimate' | 'officialDocument';

  // optional
  floor?: number | null;
  totalFloors?: number | null;
  elevator?: boolean;
  garage?: boolean;
  garageArea?: number | null;
  balconyTerraceArea?: number | null;
  garden?: boolean;
  gardernArea?: number | null;
  basement?: boolean;
  basementArea?: number | null;
  heatingType?: string | null;
  energyClasee?: string | null;
  yearBuilt?: number | null;
  condition?: string | null;
  exposure?: string | null;

  // geolocation from mapbox
  latitude?: number | null;
  longitude?: number | null;

  // contacts
  contactEmail?: string | null;
  contactPhone?: string | null;
};

const defaultPayload: FormPayload = {
  propertyType: '',
  area: null,
  address: '',
  province: '',
  city: '',
  cap: '',
  rooms: null,
  bathrooms: null,
  requestedPurpose: 'estimate',
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
