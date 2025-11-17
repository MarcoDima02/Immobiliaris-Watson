/**
 * Node modules
 */
import { useForm, Controller } from 'react-hook-form';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';

/**
 * Hooks
 */
import { useFormContext } from '@/hooks/useFormContext ';

/**
 * Components
 */
import {
  Card,
  CardHeader,
  CardTitle,
  CardContent,
  CardFooter,
} from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Field, FieldLabel, FieldGroup } from '@/components/ui/field';
import { Input } from '@/components/ui/input';
import Loader from '@/components/Loader';

/**
 * Types
 */
import { ownerSchema } from '@/hooks/schemas/valuationSchema';
import { useState } from 'react';

/**
 * Icons
 */
import { Award } from 'lucide-react';

type UserTypeValues = z.infer<typeof ownerSchema>;

const URL = `${import.meta.env.VITE_API_URL}/valutazioni/form`;

const StepUserType = ({
  onBack,
}: {
  onNext: () => void;
  onBack: () => void;
}) => {
  const { data, setData } = useFormContext();

  const [loading, setLoading] = useState(false);
  const [valuation, setValuation] = useState<any | null>(null);

  const { control, handleSubmit } = useForm<UserTypeValues>({
    resolver: zodResolver(ownerSchema),
    defaultValues: {
      emailUtente: data.emailUtente ?? '',
      telefonoUtente: data.telefonoUtente ?? '',
    },
    shouldUnregister: false,
  });

  const onSubmit = async (values: UserTypeValues) => {
    const allData = { ...data, ...values };
    console.log('Tutti i dati del form:', allData);
    setData(allData);
    setLoading(true);

    try {
      const res = await fetch(URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(allData),
      });

      if (!res.ok) {
        const errorMessage = await res.text();
        throw new Error(errorMessage || 'Errore sconosciuto');
      }

      const result = await res.json();
      setValuation(result);

      console.log('Risposta dal backend:', result);
    } catch (error) {
      console.log('Error: ', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="flex flex-col items-center justify-center py-20 gap-4">
        <Loader />
        <p className="text-muted-foreground text-lg">
          Stiamo calcolando la valutazione del tuo immobile...
        </p>
      </div>
    );
  }

  if (valuation) {
    const { valoreFinale, valoreMin, valoreMax } = valuation;

    return (
      <Card className="max-w-2xl mx-auto">
        <CardHeader>
          <CardTitle className='flex gap-3 justify-center'><Award /> Valutazione completata</CardTitle>
        </CardHeader>

        <CardContent className="space-y-6">
          <div className="p-5 bg-primary/10 rounded-lg border text-center">
            <p className="text-sm text-primary">Valore stimato</p>
            <p className="text-3xl font-bold text-primary">
              € {valoreFinale.toLocaleString('it-IT')}
            </p>

            <p className="text-sm mt-2">
              Range: <strong>€ {valoreMin.toLocaleString('it-IT')}</strong> –{' '}
              <strong>€ {valoreMax.toLocaleString('it-IT')}</strong>
            </p>
          </div>
        </CardContent>

        <CardFooter className="flex justify-between">
          <Button
            variant="outline"
            onClick={onBack}
          >
            Torna indietro
          </Button>
          <Button>Scarica report PDF</Button>
        </CardFooter>
      </Card>
    );
  }

  return (
    <Card className="max-w-xl mx-auto">
      <CardHeader>
        <CardTitle>
          Lasci i suoi riferimenti e un nostro consulente la contatterà al più
          presto.
        </CardTitle>
      </CardHeader>

      <CardContent>
        <form
          id="user-form"
          onSubmit={handleSubmit(onSubmit)}
        >
          <FieldGroup>
            <Controller
              name="emailUtente"
              control={control}
              render={({ field, fieldState }) => (
                <Field data-invalid={fieldState.invalid}>
                  <FieldLabel>Email</FieldLabel>
                  <Input
                    type="email"
                    placeholder="Es. esempio@email.com"
                    {...field}
                  />
                  {fieldState.error && (
                    <p className="text-red-500 text-sm">
                      {fieldState.error.message}
                    </p>
                  )}
                </Field>
              )}
            />

            <Controller
              name="telefonoUtente"
              control={control}
              render={({ field, fieldState }) => (
                <Field data-invalid={fieldState.invalid}>
                  <FieldLabel>Telefono</FieldLabel>
                  <Input
                    type="tel"
                    placeholder="Es. +39 345 678 9012"
                    {...field}
                    pattern="^(\+39\s?)?(\d{6,12})$"
                    onInvalid={(e) => {
                      e.currentTarget.setCustomValidity(
                        'Inserisci un numero di telefono valido'
                      );
                    }}
                    onInput={(e) => {
                      // resetta l’errore quando l’utente digita
                      e.currentTarget.setCustomValidity('');
                    }}
                  />
                  {fieldState.error && (
                    <p className="text-red-500 text-sm">
                      {fieldState.error.message}
                    </p>
                  )}
                </Field>
              )}
            />
          </FieldGroup>
        </form>
      </CardContent>
      <CardFooter className="flex justify-between">
        <Button
          variant="outline"
          onClick={onBack}
        >
          Indietro
        </Button>
        <Button
          type="submit"
          form="user-form"
          className="hover:bg-card"
        >
          Avanti
        </Button>
      </CardFooter>
    </Card>
  );
};

export default StepUserType;
