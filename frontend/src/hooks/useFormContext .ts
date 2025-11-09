/**
 * Node modules
 */
import { useContext } from 'react';

/**
 * Context
 */
import FormContext from '@/context/FormContext';

export const useFormContext = () => {
  const ctx = useContext(FormContext);
  if (!ctx) throw new Error('useFormContext must be used within FormProvider');
  return ctx;
};
