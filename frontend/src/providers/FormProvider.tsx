/**
 * Node modules
 */
import { useState, type ReactNode } from 'react';
import FormContext from '@/context/FormContext';

export type FormPayload = {
  // mandatory
  propertyType: string;
  area?: number;
  address: string;
  province: string;
  city: string;
  cap: string;
  rooms?: number;
  bathrooms?: number;
  requestedPurpose: 'estimate' | 'officialDocument';

  // optional
  floor?: number;
  totalFloors?: number;
  elevator?: boolean;
  garage?: boolean;
  garageArea?: number;
  balconyTerraceArea?: number;
  garden?: boolean;
  gardenArea?: number; 
  basement?: boolean;
  basementArea?: number;
  heatingType?: string;
  energyClass?: string; 
  yearBuilt?: number;
  condition?: string;
  exposure?: string;

  // geolocation from mapbox
  latitude?: number;
  longitude?: number;

  // contacts
  contactEmail?: string;
  contactPhone?: string;
};

const defaultPayload: FormPayload = {
  propertyType: '',
  area: undefined,
  address: '',
  province: '',
  city: '',
  cap: '',
  rooms: undefined,
  bathrooms: undefined,
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
