/**
 * Components
 */
import MultiStepForm from '@/components/MultiStepForm';
import StepAddress from '@/components/form/StepAddress';
import StepProperty from '@/components/form/StepProperty';
import StepUserType from '@/components/form/StepUserType';
import StepSuccess from '@/components/form/StepSuccess';

interface EvaluationProps {
  id: string;
}

const Evaluation = ({ id }: EvaluationProps) => {
  const steps = [StepAddress, StepProperty, StepUserType, StepSuccess];

  return (
    <section
      id={id}
      className="px-4 py-8 mt-20 bg-card min-h-screen"
    >
      <h2 className='title mx-auto text-center text-primary!'>Valuta il tuo immobile</h2>
      <MultiStepForm steps={steps} />
    </section>
  );
};

export default Evaluation;
