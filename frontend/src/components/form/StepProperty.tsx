/**
 * Node modules
 */
import { useState, useEffect } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import clsx from 'clsx';

/**
 * Context
 */
import { useFormContext } from '@/hooks/useFormContext ';

/**
 * Componets
 */
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
  Field,
  FieldLabel,
  FieldGroup,
  FieldError,
} from '@/components/ui/field';
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from '@/components/ui/select';

/**
 * Icons
 */
import { Building, House, School, Building2 } from 'lucide-react';

/**
 * Types
 */
import { propertySchema } from '@/hooks/schemas/valuationSchema';

const allowedKeys: (keyof PropertyValues)[] = [
  'propertyType',
  'requestedPurpose',
  'yearBuilt',
  'area',
  'rooms',
  'bathrooms',
  'condition',
  'heatingType',
  'balconyTerraceArea',
  'gardenArea',
  'basementArea',
  'floor',
  'totalFloors',
];

type PropertyValues = z.infer<typeof propertySchema>;

const StepProperty = ({
  onNext,
  onBack,
}: {
  onNext: () => void;
  onBack: () => void;
}) => {
  const { data, setData } = useFormContext();
  const [selectedType, setSelectedType] = useState<string | undefined>(
    data.propertyType || undefined
  );

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
    },
    shouldUnregister: false,
    mode: 'onSubmit',
    reValidateMode: 'onSubmit',
  });

  useEffect(() => {
    allowedKeys.forEach((key) => {
      if (data[key] !== undefined) {
        setValue(key, data[key] as PropertyValues[typeof key]);
      }
    });
    if (data.propertyType) {
      setSelectedType(data.propertyType);
    }
  }, [data, setValue]);

  const onSubmit = (values: PropertyValues) => {
    setData(values);
    onNext();
  };

  const propertyTypes = [
    { label: 'Appartamento', value: 'appartamento', icon: <Building /> },
    { label: 'Villa', value: 'villa', icon: <House /> },
    { label: 'Monolocale', value: 'monolocale', icon: <Building2 /> },
    {
      label: 'Casa indipendente',
      value: 'casa_indipendente',
      icon: <School />,
    },
  ];

  return (
    <Card className="max-w-xl mx-auto">
      <CardHeader>
        <CardTitle>Caratteristiche dell'immobile</CardTitle>
      </CardHeader>

      <CardContent>
        <form
          id="property-form"
          onSubmit={handleSubmit(onSubmit)}
        >
          <FieldGroup>
            <div className="grid grid-cols-2 gap-3 mb-4">
              {propertyTypes.map((type) => (
                <Button
                  key={type.value}
                  type="button"
                  onClick={() => {
                    setSelectedType(type.value);
                    setValue('propertyType', type.value);
                  }}
                  className={clsx('p-3 rounded-lg border transition', {
                    'border-primary bg-primary/10 text-primary':
                      selectedType === type.value,
                    'border-border': selectedType !== type.value,
                  })}
                >
                  <span className="text-2xl">{type.icon}</span>
                  <p className="text-sm font-medium mt-1">{type.label}</p>
                </Button>
              ))}
            </div>

            {selectedType && (
              <>
                {/* <Controller
                  name="area"
                  control={control}
                  render={({ field, fieldState }) => (
                    <Field>
                      <FieldLabel>Superficie (m²)</FieldLabel>
                      <Input
                        type="number"
                        step={10}
                        placeholder="Es. 120"
                        {...field}
                        value={field.value ?? ''}
                        onChange={(e) => {
                          const raw = e.target.value;
                          if (raw === '') return field.onChange(undefined);
                          const num = Number(raw);
                          if (!isNaN(num)) field.onChange(num);
                        }}
                      />
                      {fieldState.error && (
                        <FieldError>{fieldState.error.message}</FieldError>
                      )}
                    </Field>
                  )}
                /> */}
                <Controller
                  name="area"
                  control={control}
                  render={({ field, fieldState }) => {
                    const [localValue, setLocalValue] = useState(
                      field.value?.toString() ?? ''
                    );

                    return (
                      <Field>
                        <FieldLabel>Superficie (m²)</FieldLabel>
                        <Input
                          type="number"
                          step={10}
                          placeholder="Es. 120"
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

                <div className="grid grid-cols-2 gap-3">
                  {/* <Controller
                    name="rooms"
                    control={control}
                    render={({ field, fieldState }) => (
                      <Field>
                        <FieldLabel>Numero di locali</FieldLabel>
                        <Input
                          className="w-1/2 min-w-1"
                          type="number"
                          {...field}
                          placeholder="Es. 3"
                          value={field.value ?? ''}
                          onChange={(e) => {
                            const raw = e.target.value;
                            if (raw === '') return field.onChange(undefined);
                            const num = Number(raw);
                            if (!isNaN(num)) field.onChange(num);
                          }}
                        />
                        {fieldState.error && (
                          <FieldError>{fieldState.error.message}</FieldError>
                        )}
                      </Field>
                    )}
                  /> */}
                  <Controller
                    name="rooms"
                    control={control}
                    render={({ field, fieldState }) => {
                      const [localValue, setLocalValue] = useState(
                        field.value?.toString() ?? ''
                      );

                      return (
                        <Field>
                          <FieldLabel>Numero di locali</FieldLabel>
                          <Input
                            className="w-1/2 min-w-1"
                            type="number"
                            placeholder="Es. 3"
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
                  {/* <Controller
                    name="bathrooms"
                    control={control}
                    render={({ field, fieldState }) => (
                      <Field>
                        <FieldLabel>Numero di bagni</FieldLabel>
                        <Input
                          type="number"
                          placeholder="Es. 2"
                          {...field}
                          value={field.value ?? ''}
                          onChange={(e) => {
                            const raw = e.target.value;
                            if (raw === '') return field.onChange(undefined);
                            const num = Number(raw);
                            if (!isNaN(num)) field.onChange(num);
                          }}
                        />
                        {fieldState.error && (
                          <FieldError>{fieldState.error.message}</FieldError>
                        )}
                      </Field>
                    )}
                  /> */}
                  <Controller
                    name="bathrooms"
                    control={control}
                    render={({ field, fieldState }) => {
                      const [localValue, setLocalValue] = useState(
                        field.value?.toString() ?? ''
                      );

                      return (
                        <Field>
                          <FieldLabel>Numero di bagni</FieldLabel>
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
                {/* 
                <Controller
                  name="yearBuilt"
                  control={control}
                  render={({ field, fieldState }) => (
                    <Field>
                      <FieldLabel>Anno di costruzione</FieldLabel>
                      <Input
                        type="number"
                        placeholder="Es. 2015"
                        step={100}
                        {...field}
                        value={field.value ?? ''}
                        onChange={(e) => {
                          const raw = e.target.value;
                          if (raw === '') return field.onChange(undefined);
                          const num = Number(raw);
                          if (!isNaN(num)) field.onChange(num);
                        }}
                      />
                      {fieldState.error && (
                        <FieldError>{fieldState.error.message}</FieldError>
                      )}
                    </Field>
                  )}
                /> */}

                <Controller
                  name="yearBuilt"
                  control={control}
                  render={({ field, fieldState }) => {
                    const [localValue, setLocalValue] = useState(
                      field.value?.toString() ?? ''
                    );

                    return (
                      <Field>
                        <FieldLabel>Anno di costruzione</FieldLabel>
                        <Input
                          type="number"
                          placeholder="Es. 2015"
                          step={100}
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
                  name="condition"
                  control={control}
                  render={({ field }) => (
                    <Field>
                      <FieldLabel>Seleziona lo stato immobile</FieldLabel>

                      <Select
                        value={field.value ?? ''}
                        onValueChange={field.onChange}
                      >
                        <SelectTrigger className="cursor-pointer">
                          <SelectValue placeholder="Seleziona..." />
                        </SelectTrigger>

                        <SelectContent>
                          <SelectItem value="new">Nuovo</SelectItem>
                          <SelectItem value="renovated">
                            Ristrutturato
                          </SelectItem>
                          <SelectItem value="to_renovate">
                            Da ristrutturare
                          </SelectItem>
                        </SelectContent>
                      </Select>
                    </Field>
                  )}
                />
              </>
            )}
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
          form="property-form"
          type="submit"
          className="hover:bg-card"
        >
          Avanti
        </Button>
      </CardFooter>
    </Card>
  );
};

export default StepProperty;
