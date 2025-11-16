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
  latitude: z.number().optional(),
  longitude: z.number().optional(),
});

export const propertySchema = z.object({
  propertyType: z.string().min(1, 'Seleziona la tipologia del tuo immobile'),
  area: z
    .number({ error: 'Inserisci la superficie' })
    .min(0.01, 'La superficie deve essere > 0')
    .max(20000, 'Superficie troppo grande'),
  rooms: z.number({ error: 'Inserisci il numero di stanze' }).int().min(0,'La stanze devono essere > 0'),
  bathrooms: z.number({ error: 'Inserisci il numero di bagni' }).int().min(0, 'I bagni devono essere > 0'),
  requestedPurpose: z.enum(['estimate', 'officialDocument']),
  floor: z.number().int().optional(),
  totalFloors: z.number().int().optional(),
  elevator: z.boolean().optional(),
  garage: z.boolean().optional(),
  garageArea: z.number().optional(),
  balconyTerraceArea: z.number().optional(),
  garden: z.boolean().optional(),
  gardenArea: z.number().optional(),
  basement: z.boolean().optional(),
  basementArea: z.number().optional(),
  heatingType: z.string().optional(),
  energyClass: z.enum(['A+', 'A', 'B', 'C', 'D', 'E', 'F', 'G']).optional(),
  yearBuilt: z.number({ error: 'Inserisci l\'anno di costruzione' }).int().min(1800, 'L\'anno deve essere superiore al 1800').max(new Date().getFullYear()),
  condition: z.string().optional(),
  exposure: z.string().optional(),
});

export const ownerSchema = z.object({
  contactEmail: z.email('Email non valida').optional(),
  contactPhone: z.string().max(10, 'Il numero deve essere di 10 cifre').optional(),
});
