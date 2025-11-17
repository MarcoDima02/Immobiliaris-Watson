/**
 * Components
 */
import MultiStepForm from '@/components/MultiStepForm';
import StepAddress from '@/components/form/StepAddress';
import StepProperty from '@/components/form/StepProperty';
import StepOptional from '@/components/form/StepOptional';
import StepUserType from '@/components/form/StepUserType';

interface EvaluationProps {
  id: string;
}

const Evaluation = ({ id }: EvaluationProps) => {
  const steps = [StepAddress, StepProperty, StepOptional, StepUserType];

  return (
    <section
      id={id}
      className="px-4 bg-card py-24"
    >
      <h2 className="title mx-auto text-center text-primary!">
        Valuta il tuo immobile
      </h2>
      <MultiStepForm steps={steps} />
    </section>
  );
};

export default Evaluation;
