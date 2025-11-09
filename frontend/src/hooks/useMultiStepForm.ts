/**
 * Node modules
 */
import { useState } from 'react';

export function useMultistepForm(totalSteps: number) {
  const [stepIndex, setStep] = useState(0);

  const next = () => setStep((step) => Math.min(totalSteps - 1, step + 1));
  const back = () => setStep((step) => Math.max(0, step - 1));
  const goTo = (index: number) =>
    setStep(() => Math.max(0, Math.min(totalSteps - 1, index)));

  return {
    stepIndex,
    next,
    back,
    goTo,
    isFirst: stepIndex === 0,
    isLast: stepIndex === totalSteps - 1,
    totalSteps,
  };
}
