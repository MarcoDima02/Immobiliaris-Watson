/**
 * Types
 */
import { type LucideIcon } from 'lucide-react';

type FunctionalityProps = {
  icon: LucideIcon;
  desc: React.ReactNode;
};

const Functionality = ({ icon: Icon, desc }: FunctionalityProps) => {
  return (
    <>
      <div>
          <div className="bg-card rounded-full p-3 shadow-xl w-20 h-20 flex justify-center items-center mx-auto mb-4">
            <Icon className="h-10 w-10 text-foreground" />
          </div>
          <div>
              <p className='max-w-[35ch] md:max-w-[25ch]'>{desc}</p>
          </div>
      </div>
    </>
  );
};

export default Functionality;
