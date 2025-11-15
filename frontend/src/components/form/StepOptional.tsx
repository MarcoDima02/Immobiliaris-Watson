import { z } from 'zod';
/**
 * Context
 */
import { useFormContext } from '@/hooks/useFormContext ';
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
  const { data, setData, } = useFormContext();
  //   const [selectedType, setSelectedType] = useState<string | undefined>(
  //     data.propertyType || undefined
  //   );

  const { control, handleSubmit, setValue } = useForm<PropertyValues>({
    resolver: zodResolver(propertySchema),
    defaultValues: {
      // required by schema ➜ MUST BE HERE
      propertyType: data.propertyType ?? '',
      requestedPurpose: data.requestedPurpose ?? 'estimate',
      yearBuilt: data.yearBuilt ?? undefined,

      // numeric fields
      area: data.area ?? undefined,
      rooms: data.rooms ?? undefined,
      bathrooms: data.bathrooms ?? undefined,

      // optional
      condition: data.condition ?? 'new',
      heatingType: data.heatingType ?? '',
      balconyTerraceArea: data.balconyTerraceArea ?? undefined,
      gardenArea: data.gardenArea ?? undefined,
      basementArea: data.basementArea ?? undefined,
      floor: data.floor ?? undefined,
      totalFloors: data.totalFloors ?? undefined,
      exposure: data.exposure ?? '',
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
      typeof value === "string" ||
      typeof value === "number" ||
      typeof value === "boolean"
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
    window.scrollBy({ top: -400, left: 0, behavior: 'smooth' });
  };
  return (
    <Card className="max-w-xl mx-auto">
      <CardHeader>
        <CardTitle>Dettagli aggiuntivi dell'immobile</CardTitle>
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
                name="floor"
                control={control}
                render={({ field }) => (
                  <Field>
                    <FieldLabel>Piano</FieldLabel>
                    <Input
                      type="number"
                      placeholder="Es. 2"
                      {...field}
                      value={field.value ?? ''}
                      onChange={(e) =>
                        field.onChange(
                          isNaN(e.target.valueAsNumber)
                            ? undefined
                            : e.target.valueAsNumber
                        )
                      }
                    />
                  </Field>
                )}
              />

              <Controller
                name="totalFloors"
                control={control}
                render={({ field }) => (
                  <Field>
                    <FieldLabel>Piani totali</FieldLabel>
                    <Input
                      type="number"
                      placeholder="Es. 5"
                      {...field}
                      value={field.value ?? ''}
                      onChange={(e) =>
                        field.onChange(
                          isNaN(e.target.valueAsNumber)
                            ? undefined
                            : e.target.valueAsNumber
                        )
                      }
                    />
                  </Field>
                )}
              />
            </div>

            {/* Ascensore */}
            <Controller
              name="elevator"
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

            {/* Balcone/Terrazzo */}
            <Controller
              name="balconyTerraceArea"
              control={control}
              render={({ field }) => (
                <Field>
                  <FieldLabel>Balcone/Terrazzo (m²)</FieldLabel>
                  <Input
                    type="number"
                    placeholder="Es. 12"
                    {...field}
                    value={field.value ?? ''}
                    onChange={(e) =>
                      field.onChange(
                        isNaN(e.target.valueAsNumber)
                          ? undefined
                          : e.target.valueAsNumber
                      )
                    }
                  />
                </Field>
              )}
            />

            {/* Giardino + Mq */}
            <Controller
              name="garden"
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

            {control._formValues.garden && (
              <Controller
                name="gardenArea"
                control={control}
                render={({ field }) => (
                  <Field>
                    <FieldLabel>Superficie Giardino (m²)</FieldLabel>
                    <Input
                      type="number"
                      placeholder="Es. 50"
                      {...field}
                      value={field.value ?? ''}
                      onChange={(e) =>
                        field.onChange(
                          isNaN(e.target.valueAsNumber)
                            ? undefined
                            : e.target.valueAsNumber
                        )
                      }
                    />
                  </Field>
                )}
              />
            )}

            {/* Cantina + mq */}
            <Controller
              name="basement"
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

            {control._formValues.basement && (
              <Controller
                name="basementArea"
                control={control}
                render={({ field }) => (
                  <Field>
                    <FieldLabel>Superficie Cantina (m²)</FieldLabel>
                    <Input
                      type="number"
                      placeholder="Es. 8"
                      {...field}
                      value={field.value ?? ''}
                      onChange={(e) =>
                        field.onChange(
                          isNaN(e.target.valueAsNumber)
                            ? undefined
                            : e.target.valueAsNumber
                        )
                      }
                    />
                  </Field>
                )}
              />
            )}

            {/* Riscaldamento */}
            {/* <Controller
              name="heatingType"
              control={control}
              render={({ field }) => (
                <Field>
                  <FieldLabel>Tipo di riscaldamento</FieldLabel>
                  <Select
                    onValueChange={field.onChange}
                    defaultValue={field.value}
                  >
                    <SelectTrigger>
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
            /> */}

            <Controller
              name="heatingType"
              control={control}
              render={({ field }) => (
                <Field className="">
                  <FieldLabel>Tipo di riscaldamento</FieldLabel>
                  <Select
                    {...field}
                    onValueChange={field.onChange}
                  >
                    <SelectTrigger className="cursor-pointer border-primary! data-placeholder:text-primary">
                      <SelectValue
                        placeholder="Seleziona..."
                         className=""
                      />
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
              name="energyClass"
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
              name="exposure"
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
