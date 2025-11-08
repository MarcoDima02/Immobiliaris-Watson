/**
 * Node modules
 */
import { useEffect, useState, useRef } from 'react';
import mapboxgl from 'mapbox-gl';
import debounce from 'lodash.debounce';
import type z from 'zod';
import { useForm, Controller } from 'react-hook-form';

/**
 * Resolvers
 */
import { zodResolver } from '@hookform/resolvers/zod';

/**
 * Schemas
 */
import { addressSchema } from '@/hooks/schemas/valuationSchema';

/**
 * Context
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
import {
  Field,
  FieldError,
  FieldGroup,
  FieldLabel,
} from '@/components/ui/field';
import { Input } from '@/components/ui/input';
// import { Toaster } from '.@/components/ui/sonner';
import { Button } from '@/components/ui/button';

mapboxgl.accessToken = mapboxgl.accessToken =
  import.meta.env.VITE_MAPBOX_ACCESS_TOKEN ?? 'MAPBOX_TOKEN';

type FormValues = z.infer<typeof addressSchema>;

const StepAddress = ({ onNext }: { onNext: () => void }) => {
  const { data, setData } = useFormContext();
  const { control, handleSubmit, setValue, watch } = useForm<FormValues>({
    resolver: zodResolver(addressSchema),
    defaultValues: {
      address: data.address ?? '',
      city: data.city ?? '',
      province: data.province ?? '',
      cap: data.cap ?? '',
    },
  });

  const address = watch('address');
  const [suggestions, setSuggestions] = useState<any[]>([]);
  const mapContainer = useRef<HTMLDivElement>(null);
  const mapRef = useRef<mapboxgl.Map | null>(null);
  const markerRef = useRef<mapboxgl.Marker | null>(null);

  useEffect(() => {
    if (!mapContainer.current) return;
    mapRef.current = new mapboxgl.Map({
      container: mapContainer.current,
      style: 'mapbox://styles/mapbox/streets-v11',
      center: [7.6869, 45.0703],
      zoom: 11,
    });
    return () => mapRef.current?.remove();
  }, []);

  const setMarker = (lng: number, lat: number) => {
    if (!mapRef.current) return;
    if (!markerRef.current) {
      markerRef.current = new mapboxgl.Marker()
        .setLngLat([lng, lat])
        .addTo(mapRef.current);
    } else {
      markerRef.current.setLngLat([lng, lat]);
    }

    mapRef.current.flyTo({ center: [lng, lat], zoom: 15 });
    setData({ longitude: lng, latitude: lat });
  };

  const geocode = debounce(async (query: string) => {
    if (!query) {
      setSuggestions([]);
      return;
    }
    const token = mapboxgl.accessToken;
    const url = `https://api.mapbox.com/geocoding/v5/mapbox.places/${encodeURIComponent(
      query
    )}.json?autocomplete=true&country=it&limit=5&access_token=${token}`;
    try {
      const res = await fetch(url);
      const json = await res.json();
      if (json?.features) setSuggestions(json.features);
    } catch (err) {
      console.error('Errore geocoding:', err);
    }
  }, 400);

  useEffect(() => {
    geocode(address);
  }, [address]);

  const onSelectSuggestion = (feature: any) => {
    const selectedAddress = feature.place_name;
    const [longitude, latitude] = feature.center;

    setValue('address', selectedAddress, { shouldValidate: true });

    let city = '';
    let province = '';
    let cap = '';

    feature.context?.forEach((ctx: any) => {
      const id = ctx.id ?? '';
      if (id.startsWith('place.')) city = ctx.text;
      if (id.startsWith('district.')) province = ctx.text;
      if (id.startsWith('postcode.')) cap = ctx.text;
    });

    setValue('city', city);
    setValue('province', province);
    setValue('cap', cap);

    setData({
      address: selectedAddress,
      city,
      province,
      cap,
    });

    setMarker(longitude, latitude);
    setSuggestions([]);
  };

  const onSubmit = (formValues: FormValues) => {
    setData(formValues);
    onNext();
  };

  return (
    <Card className="w-full sm:maxw-md">
      <CardHeader>
        <CardTitle>Dove si trova l'immobile?</CardTitle>
      </CardHeader>

      <CardContent>
        <form
          id="address-form"
          onSubmit={handleSubmit(onSubmit)}
        >
          <FieldGroup>
            <Controller
              name="address"
              control={control}
              render={({ field, fieldState }) => (
                <Field data-invalid={fieldState.invalid}>
                  <FieldLabel>Indirizzo</FieldLabel>

                  <Input
                    {...field}
                    placeholder="Es. Via Roma 10, Milano"
                    aria-invalid={fieldState.invalid}
                    autoComplete="off"
                  />

                  {fieldState.invalid && (
                    <FieldError errors={[fieldState.error]} />
                  )}

                  {suggestions && suggestions.length > 0 && (
                    <ul className="bg-white border rounded mt-2 max-h-48 overflow-auto shadow-sm">
                      {suggestions.map((suggestion) => (
                        <li
                          key={suggestion.id}
                          className="p-2 hover:bg-gray-100 cursor-pointer text-sm"
                          onClick={() => onSelectSuggestion(suggestion)}
                        >
                          {suggestion.place_name}
                        </li>
                      ))}
                    </ul>
                  )}
                </Field>
              )}
            ></Controller>

            <Controller
              name="city"
              control={control}
              render={({ field, fieldState }) => (
                <Field data-invalid={fieldState.invalid}>
                  <FieldLabel>Città</FieldLabel>

                  <Input
                    {...field}
                    aria-invalid={fieldState.invalid}
                  />

                  {fieldState.invalid && (
                    <FieldError errors={[fieldState.error]} />
                  )}
                </Field>
              )}
            ></Controller>

            <Controller
              name="province"
              control={control}
              render={({ field, fieldState }) => (
                <Field data-invalid={fieldState.invalid}>
                  <FieldLabel>Provincia (sigla)</FieldLabel>

                  <Input
                    {...field}
                    maxLength={2}
                    aria-invalid={fieldState.invalid}
                  />

                  {fieldState.invalid && (
                    <FieldError errors={[fieldState.error]} />
                  )}
                </Field>
              )}
            ></Controller>

            <Controller
              name="cap"
              control={control}
              render={({ field, fieldState }) => (
                <Field>
                  <FieldLabel>Cap</FieldLabel>

                  <Input
                    {...field}
                    aria-invalid={fieldState.invalid}
                  />

                  {fieldState.invalid && (
                    <FieldError errors={[fieldState.error]} />
                  )}
                </Field>
              )}
            ></Controller>

            <Field>
              <FieldLabel>Posizione sulla mappa</FieldLabel>

              <div
                ref={mapContainer}
                className="w-full h-64 rounded mt-2"
              ></div>
            </Field>
          </FieldGroup>
        </form>
      </CardContent>

      <CardFooter>
        <Field orientation="horizontal">
          <Button
            type="button"
            variant="outline"
          >
            Indietro
          </Button>
          <Button
            type="submit"
            form="address-form"
          >
            Avanti
          </Button>
        </Field>
      </CardFooter>
    </Card>
  );
};

export default StepAddress;
