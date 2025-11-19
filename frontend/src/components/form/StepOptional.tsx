/**
 * Node modules
 */
import { useState } from 'react';
import { z } from 'zod';
/**
 * Context
 */
import { useFormContext } from '@/hooks/useFormContext';
import { useEffect } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';

import {
  Card,
  CardHeader,
  CardTitle,
  CardContent,
  CardFooter,
} from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from '@/components/ui/select';
import { Switch } from '@/components/ui/switch';

import {
  Field,
  FieldLabel,
  FieldGroup,
  FieldError,
} from '@/components/ui/field';

/**
 * Types
 */
import { propertySchema } from '@/hooks/schemas/valuationSchema';

type PropertyValues = z.infer<typeof propertySchema>;

export default function StepOptional({
  onNext,
  onBack,
}: {
  onNext: () => void;
  onBack: () => void;
}) {
  const { data, setData } = useFormContext();
  //   const [selectedType, setSelectedType] = useState<string | undefined>(
  //     data.propertyType || undefined
  //   );

  const { control, handleSubmit, setValue, watch } = useForm<PropertyValues>({
    resolver: zodResolver(propertySchema),
    defaultValues: {
      // required by schema ➜ MUST BE HERE
      tipologia: data.tipologia ?? '',
      finalitaRichiesta: data.finalitaRichiesta ?? 'estimate',
      annoCostruzione: data.annoCostruzione ?? undefined,

      // numeric fields
      superficie: data.superficie ?? undefined,
      nStanze: data.nStanze ?? undefined,
      nBagni: data.nBagni ?? undefined,

      // optional
      condizione: data.condizione ?? 'new',
      tipoRiscaldamento: data.tipoRiscaldamento ?? '',
      superficieBalconeTerrazzo: data.superficieBalconeTerrazzo ?? undefined,
      superficieGiardino: data.superficieGiardino ?? undefined,
      superficieGarage: data.superficieGarage ?? undefined,
      superficieCantina: data.superficieCantina ?? undefined,
      piano: data.piano ?? undefined,
      pianiTotali: data.pianiTotali ?? undefined,
      esposizione: data.esposizione ?? '',
    },
    shouldUnregister: false,
  });

  const safeSetValue = (
    key: keyof PropertyValues,
    value: unknown,
    setValue: (f: keyof PropertyValues, v: any) => void
  ) => {
    if (
      value === undefined ||
      typeof value === 'string' ||
      typeof value === 'number' ||
      typeof value === 'boolean'
    ) {
      setValue(key, value);
    }
  };

  useEffect(() => {
    Object.entries(data).forEach(([key, value]) =>
      safeSetValue(key as keyof PropertyValues, value, setValue)
    );
  }, [data, setValue]);

  const onSubmit = (values: PropertyValues) => {
    setData(values);
    onNext();
    window.scrollBy({ top: -600, left: 0, behavior: 'smooth' });
  };

  const garageValue = watch('garage');
  const gardenValue = watch('giardino');
  const basementValue = watch('cantina');

  return (
    <Card className="max-w-xl mx-auto">
      <CardHeader>
        <CardTitle>Dettagli aggiuntivi dell'immobile</CardTitle>
        <h3 className="text-yellow-700">
          I seguenti campi sono facoltativi, ma più dettagli inserisci
          sull’immobile, più precisa sarà la stima del prezzo.
        </h3>
      </CardHeader>

      <CardContent>
        <form
          id="details-form"
          onSubmit={handleSubmit(onSubmit)}
        >
          <FieldGroup>
            {/* Piano + Piani totali */}
            <div className="grid grid-cols-2 gap-3">
              <Controller
                name="piano"
                control={control}
                render={({ field, fieldState }) => {
                  const [localValue, setLocalValue] = useState(
                    field.value?.toString() ?? ''
                  );

                  return (
                    <Field>
                      <FieldLabel>Piano</FieldLabel>
                      <Input
                        type="number"
                        placeholder="Es. 2"
                        value={localValue}
                        onChange={(e) => {
                          const val = e.target.value;
                          setLocalValue(val);
                          const num = Number(val);
                          field.onChange(
                            val === ''
                              ? undefined
                              : isNaN(num)
                                ? undefined
                                : num
                          );
                        }}
                      />
                      {fieldState.error && (
                        <FieldError>{fieldState.error.message}</FieldError>
                      )}
                    </Field>
                  );
                }}
              />

              <Controller
                name="pianiTotali"
                control={control}
                render={({ field, fieldState }) => {
                  const [localValue, setLocalValue] = useState(
                    field.value?.toString() ?? ''
                  );

                  return (
                    <Field>
                      <FieldLabel>Piani totali</FieldLabel>
                      <Input
                        type="number"
                        placeholder="Es. 2"
                        value={localValue}
                        onChange={(e) => {
                          const val = e.target.value;
                          setLocalValue(val);
                          const num = Number(val);
                          field.onChange(
                            val === ''
                              ? undefined
                              : isNaN(num)
                                ? undefined
                                : num
                          );
                        }}
                      />
                      {fieldState.error && (
                        <FieldError>{fieldState.error.message}</FieldError>
                      )}
                    </Field>
                  );
                }}
              />
            </div>

            {/* Ascensore */}
            <Controller
              name="ascensore"
              control={control}
              render={({ field }) => (
                <Field className="flex items-center justify-between mt-4 max-w-xs">
                  <FieldLabel>Ascensore</FieldLabel>
                  <div className="shrink-0">
                    <Switch
                      checked={field.value ?? false}
                      onCheckedChange={field.onChange}
                    />
                  </div>
                </Field>
              )}
            />

            {/* Garage */}
            <Controller
              name="garage"
              control={control}
              render={({ field }) => (
                <Field className="flex items-center justify-between mt-4 max-w-xs">
                  <FieldLabel>Garage</FieldLabel>
                  <div className="shrink-0">
                    <Switch
                      checked={field.value ?? false}
                      onCheckedChange={field.onChange}
                    />
                  </div>
                </Field>
              )}
            />

            {garageValue && (
              <Controller
                name="superficieGarage"
                control={control}
                render={({ field, fieldState }) => {
                  const [localValue, setLocalValue] = useState(
                    field.value?.toString() ?? ''
                  );

                  return (
                    <Field>
                      <FieldLabel>Superficie Garage (m²)</FieldLabel>
                      <Input
                        type="number"
                        placeholder="Es. 20"
                        value={localValue}
                        onChange={(e) => {
                          const val = e.target.value;
                          setLocalValue(val);
                          const num = Number(val);
                          field.onChange(
                            val === ''
                              ? undefined
                              : isNaN(num)
                                ? undefined
                                : num
                          );
                        }}
                      />
                      {fieldState.error && (
                        <FieldError>{fieldState.error.message}</FieldError>
                      )}
                    </Field>
                  );
                }}
              />

              // <Controller
              //   name="superficieGarage"
              //   control={control}
              //   render={({ field }) => (
              //     <Field>
              //       <FieldLabel>Superficie Garage (m²)</FieldLabel>
              //       <Input
              //         type="number"
              //         placeholder="Es. 20"
              //         {...field}
              //         value={field.value ?? ''}
              //         onChange={(e) =>
              //           field.onChange(
              //             isNaN(e.target.valueAsNumber)
              //               ? undefined
              //               : e.target.valueAsNumber
              //           )
              //         }
              //       />
              //     </Field>
              //   )}
              // />
            )}

            {/* Balcone/Terrazzo */}

            <Controller
              name="superficieBalconeTerrazzo"
              control={control}
              render={({ field, fieldState }) => {
                const [localValue, setLocalValue] = useState(
                  field.value?.toString() ?? ''
                );

                return (
                  <Field>
                    <FieldLabel>Balcone/Terrazzo (m²)</FieldLabel>
                    <Input
                      type="number"
                      placeholder="Es. 12"
                      value={localValue}
                      onChange={(e) => {
                        const val = e.target.value;
                        setLocalValue(val);
                        const num = Number(val);
                        field.onChange(
                          val === '' ? undefined : isNaN(num) ? undefined : num
                        );
                      }}
                    />
                    {fieldState.error && (
                      <FieldError>{fieldState.error.message}</FieldError>
                    )}
                  </Field>
                );
              }}
            />

            {/* Giardino + Mq */}
            <Controller
              name="giardino"
              control={control}
              render={({ field }) => (
                <Field className="flex items-center justify-between mt-4 max-w-xs">
                  <FieldLabel>Giardino</FieldLabel>
                  <div className="shrink-0">
                    <Switch
                      checked={field.value ?? false}
                      onCheckedChange={field.onChange}
                    />
                  </div>
                </Field>
              )}
            />
            {gardenValue && (
              <Controller
                name="superficieGiardino"
                control={control}
                render={({ field, fieldState }) => {
                  const [localValue, setLocalValue] = useState(
                    field.value?.toString() ?? ''
                  );

                  return (
                    <Field>
                      <FieldLabel>Superficie Giardino (m²)</FieldLabel>
                      <Input
                        type="number"
                        placeholder="Es. 2"
                        value={localValue}
                        onChange={(e) => {
                          const val = e.target.value;
                          setLocalValue(val);
                          const num = Number(val);
                          field.onChange(
                            val === ''
                              ? undefined
                              : isNaN(num)
                                ? undefined
                                : num
                          );
                        }}
                      />
                      {fieldState.error && (
                        <FieldError>{fieldState.error.message}</FieldError>
                      )}
                    </Field>
                  );
                }}
              />
            )}

            {/* Cantina + mq */}
            <Controller
              name="cantina"
              control={control}
              render={({ field }) => (
                <Field className="flex items-center justify-between mt-4 max-w-xs">
                  <FieldLabel>Cantina</FieldLabel>
                  <div className="shrink-0">
                    <Switch
                      checked={field.value ?? false}
                      onCheckedChange={field.onChange}
                    />
                  </div>
                </Field>
              )}
            />

            {basementValue && (
              <Controller
                name="superficieCantina"
                control={control}
                render={({ field, fieldState }) => {
                  const [localValue, setLocalValue] = useState(
                    field.value?.toString() ?? ''
                  );

                  return (
                    <Field>
                      <FieldLabel>Superficie Cantina (m²)</FieldLabel>
                      <Input
                        type="number"
                        placeholder="Es. 2"
                        value={localValue}
                        onChange={(e) => {
                          const val = e.target.value;
                          setLocalValue(val);
                          const num = Number(val);
                          field.onChange(
                            val === ''
                              ? undefined
                              : isNaN(num)
                                ? undefined
                                : num
                          );
                        }}
                      />
                      {fieldState.error && (
                        <FieldError>{fieldState.error.message}</FieldError>
                      )}
                    </Field>
                  );
                }}
              />
            )}

            {/* Riscaldamento */}
            <Controller
              name="tipoRiscaldamento"
              control={control}
              render={({ field }) => (
                <Field>
                  <FieldLabel>Tipo di riscaldamento</FieldLabel>
                  <Select
                    onValueChange={field.onChange}
                    defaultValue={field.value}
                  >
                    <SelectTrigger className="cursor-pointer border-primary! data-placeholder:text-primary">
                      <SelectValue placeholder="Seleziona..." />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="autonomous">Autonomo</SelectItem>
                      <SelectItem value="centralized">Centralizzato</SelectItem>
                      <SelectItem value="heat_pump">Pompa di calore</SelectItem>
                    </SelectContent>
                  </Select>
                </Field>
              )}
            />

            {/* Classe Energetica */}
            <Controller
              name="classeEnergetica"
              control={control}
              render={({ field }) => (
                <Field>
                  <FieldLabel>Classe Energetica</FieldLabel>
                  <Select
                    onValueChange={field.onChange}
                    defaultValue={field.value}
                  >
                    <SelectTrigger className="cursor-pointer border-primary! data-placeholder:text-primary">
                      <SelectValue placeholder="Seleziona..." />
                    </SelectTrigger>
                    <SelectContent>
                      {['A+', 'A', 'B', 'C', 'D', 'E', 'F', 'G'].map((cl) => (
                        <SelectItem
                          key={cl}
                          value={cl}
                        >
                          {cl}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </Field>
              )}
            />

            {/* Esposizione */}
            <Controller
              name="esposizione"
              control={control}
              render={({ field }) => (
                <Field>
                  <FieldLabel>Esposizione</FieldLabel>
                  <Input
                    placeholder="Nord, Sud, Est, Ovest..."
                    {...field}
                  />
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
          type="button"
        >
          Indietro
        </Button>

        <Button
          form="details-form"
          type="submit"
        >
          Avanti
        </Button>
      </CardFooter>
    </Card>
  );
}
