/**
 * Node modules
 */
import { useState } from 'react';

export function useMultistepForm(totalSteps: number) {
  const [step, setStep] = useState(0);

  const next = () => setStep((step) => Math.min(totalSteps - 1, step + 1));
  const back = () => setStep((step) => Math.max(0, step - 1));
  const goTo = (index: number) =>
    setStep(() => Math.max(0, Math.min(totalSteps - 1, index)));

  return {
    step,
    next,
    back,
    goTo,
    isFirst: step === 0,
    isLast: step === totalSteps - 1,
  };
}
