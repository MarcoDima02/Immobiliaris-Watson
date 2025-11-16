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

/**
 * Types
 */
import { ownerSchema } from '@/hooks/schemas/valuationSchema';

type UserTypeValues = z.infer<typeof ownerSchema>;

const URL = 'http://localhost:8080/api/valutazioni/form';

const StepUserType = ({
  onNext,
  onBack,
}: {
  onNext: () => void;
  onBack: () => void;
}) => {
  const { data, setData } = useFormContext();

  const { control, handleSubmit } = useForm<UserTypeValues>({
    resolver: zodResolver(ownerSchema),
    defaultValues: {
      contactEmail: data.contactEmail ?? '',
      contactPhone: data.contactPhone ?? '',
    },
    shouldUnregister: false,
  });

  const onSubmit = async (values: UserTypeValues) => {
    const allData = { ...data, ...values };
    console.log('Tutti i dati del form:', allData);
    setData(allData);

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

      console.log('Risposta dal backend:', result);
    } catch (error) {
      console.log('Error: ', error);
    }

    onNext();
  };
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
              name="contactEmail"
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
              name="contactPhone"
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
