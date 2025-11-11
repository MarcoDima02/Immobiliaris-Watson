/**
 * Components
 */
import MultiStepForm from '@/components/MultiStepForm';
import StepAddress from '@/components/form/StepAddress';
import StepProperty from '@/components/form/StepProperty';
import StepUserType from '@/components/form/StepUserType';
import StepSuccess from '@/components/form/StepSuccess';

const Evaluation = () => {
  const steps = [StepAddress, StepProperty, StepUserType, StepSuccess];

  return (
    <div className='p-4'>
      <MultiStepForm steps={steps} />
    </div>
  );
};

export default Evaluation;
