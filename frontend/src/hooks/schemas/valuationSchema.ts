/**
 * Node modules
 */
import { z } from 'zod';

export const addressSchema = z.object({
  city: z.string().min(1, 'Inserisci la città'),
  province: z
    .string()
    .min(2, 'Inserisci la sigla della provincia')
    .max(2, 'Sigla procincia (2 caratteri)'),
  cap: z.string().regex(/^\d{5}$/, 'Inserisci un CAP valido (5 cifre)'),
  address: z.string().min(3, "Inserisci l' indirizzo"),
  latitude: z.number().nullable().optional(),
  longitude: z.number().nullable().optional(),
});

export const propertySchema = z.object({
  propertyType: z.string().min(1, 'Seleziona la tipologia del tuo immobile'),
  area: z
    .number({ error: 'Inserisci la superficie' })
    .min(0.01, 'La superficie deve essere > 0')
    .max(20000, 'Superficie troppo grande'),
  rooms: z.number().int().min(0).nullable(),
  bathrooms: z.number().int().min(0).nullable(),
  requestedPurpose: z.enum(['estimate', 'officialDocument']),
  floor: z.number().int().nullable().optional(),
  totalFloors: z.number().int().nullable().optional(),
  elevator: z.boolean().optional(),
  garage: z.boolean().optional(),
  balconyTerraceArea: z.number().nullable().optional(),
  garden: z.boolean().optional(),
  gardenArea: z.number().nullable().optional(),
  basement: z.boolean().optional(),
  basementArea: z.number().nullable().optional(),
  heatingType: z.string().nullable().optional(),
  energyClass: z
    .enum(['A+', 'A', 'B', 'C', 'D', 'E', 'F', 'G'])
    .nullable()
    .optional(),
  yearBuilt: z
    .number()
    .int()
    .min(1800)
    .max(new Date().getFullYear())
    .nullable(),
  condition: z.string().nullable().optional(),
  exposure: z.string().nullable().optional(),
});

export const ownerSchema = z.object({
  contactEmail: z.email().nullable().optional(),
  contactPhone: z.string().nullable().optional(),
});
