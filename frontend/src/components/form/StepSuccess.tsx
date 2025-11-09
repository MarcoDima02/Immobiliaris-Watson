/**
 * Node modules
 */
import { Link } from 'react-router';

/**
 * Components
 */
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/card';

/**
 * Icons
 */
import { CheckCircle } from 'lucide-react';
import { Button } from '../ui/button';

const StepSuccess = () => {
  return (
    <div className='flex flex-col'>
      <Card className="max-w-lg mx-auto text-center py-10 mb-8">
        <CardHeader>
          <CardTitle className="text-2xl text-primary flex flex-col items-center">
            <CheckCircle className="w-12 h-12 mb-2 text-green-600" />
            Richiesta inviata!
          </CardTitle>
        </CardHeader>
        <CardContent>
          <p>
            Ti contatteremo al più presto con una{' '}
            <strong>stima indicativa</strong> del tuo immobile. Un nostro agente
            ti seguirà per una valutazione reale e gratuita.
          </p>
        </CardContent>
      </Card>

      <Button asChild className="mx-auto">
        <Link
          to="/"
          
        >
          Torna alla home
        </Link>
      </Button>
    </div>
  );
};

export default StepSuccess;
