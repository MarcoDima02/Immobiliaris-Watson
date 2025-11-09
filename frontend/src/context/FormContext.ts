/**
 * Node modules
 */
import { createContext } from 'react';

/**
 * Types
 */
import type { FormPayload } from '@/providers/FormProvider';

type ContextType = {
  data: FormPayload;
  setData: (patch: Partial<FormPayload>) => void;
  reset: () => void;
};

const FormContext = createContext<ContextType | undefined>(undefined);

export default FormContext;
