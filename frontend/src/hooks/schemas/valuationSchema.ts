/**
 * Node modules
 */
import { z } from 'zod';

export const addressSchema = z.object({
  citta: z.string().min(1, 'Inserisci la città'),
  provincia: z
    .string()
    .min(2, 'Inserisci la sigla della provincia')
    .max(2, 'Sigla procincia (2 caratteri)'),
  cap: z.string().regex(/^\d{5}$/, 'Inserisci un CAP valido (5 cifre)'),
  indirizzo: z.string().min(3, "Inserisci l' indirizzo"),
  latitude: z.number().optional(),
  longitude: z.number().optional(),
});

export const propertySchema = z.object({
  tipologia: z.string().min(1, 'Seleziona la tipologia del tuo immobile'),
  superficie: z
    .number({ error: 'Inserisci la superficie' })
    .min(0.01, 'La superficie deve essere > 0')
    .max(20000, 'Superficie troppo grande'),
  nStanze: z
    .number({ error: 'Inserisci il numero di stanze' })
    .int()
    .min(0, 'La stanze devono essere > 0'),
  nBagni: z
    .number({ error: 'Inserisci il numero di bagni' })
    .int()
    .min(0, 'I bagni devono essere > 0'),
  finalitaRichiesta: z.enum(['estimate', 'officialDocument']),
  piano: z.number().int().optional(),
  pianiTotali: z.number().int().optional(),
  ascensore: z.boolean().optional(),
  garage: z.boolean().optional(),
  superficieGarage: z.number().optional(),
  balconeTerrazzo: z.boolean().optional(),
  superficieBalconeTerrazzo: z.number().optional(),
  giardino: z.boolean().optional(),
  superficieGiardino: z.number().optional(),
  cantina: z.boolean().optional(),
  superficieCantina: z.number().optional(),
  tipoRiscaldamento: z.string().optional(),
  classeEnergetica: z
    .enum(['A+', 'A', 'B', 'C', 'D', 'E', 'F', 'G'])
    .optional(),
  annoCostruzione: z
    .number({ error: "Inserisci l'anno di costruzione" })
    .int()
    .min(1800, "L'anno deve essere superiore al 1800")
    .max(new Date().getFullYear()),
  condizione: z.string().optional(),
  esposizione: z.enum(['Nord', 'Sud', 'Ovest', 'Est']) .optional()
});

export const ownerSchema = z.object({
  nomeUtente: z.string().min(3, 'Inserisci un nome valido'),
  cognomeUtente: z.string().min(3, 'Inserisci un cognome valido'),
  emailUtente: z.email('Email non valida'),
  telefonoUtente: z
    .string('Inserisci un numero telefonico valido')
    .max(10, 'Il numero deve essere di 10 cifre'),
});
