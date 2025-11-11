/**
 * Node modules
 */
import React from 'react';
/**
 * Componets
 */

import { Progress } from '@/components/ui/progress';

/**
 * Hooks
 */
import { useMultistepForm } from '@/hooks/useMultiStepForm';

/**
 * Providers
 */
import FormProvider from '@/providers/FormProvider';

interface StepProps {
  onNext: () => void;
  onBack: () => void;
}

type StepComponent = React.ComponentType<StepProps>;

interface MultiStepFormProps {
  steps: StepComponent[];
}

const MultiStepForm = ({ steps }: MultiStepFormProps) => {
  const { stepIndex, back, next, totalSteps } = useMultistepForm(steps.length);

  const CurrentStep = steps[stepIndex];

  return (
    <FormProvider>
      <div className="max-w-2xl mx-auto">
        <div className="w-full bg-muted h-2 rounded-full mb-6">
          <Progress
            aria-hidden
            style={{
              width: `${((stepIndex + 1) / totalSteps) * 100}%`,
            }}
          />
        </div>
        <CurrentStep
          onNext={next}
          onBack={back}
        />
      </div>
    </FormProvider>
  );
};

export default MultiStepForm;
