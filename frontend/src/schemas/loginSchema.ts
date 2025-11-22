/**
 * Node modules
 */
import { z } from 'zod';

/**
 * Login form schema
 */
export const loginSchema = z.object({
  email: z
    .email("Inserisci un'email valida")
    .nonempty('Inserisci la tua email')
    .max(50, "L'email deve essere minore di 50 caratteri"),
  password: z
    .string()
    .nonempty('Inserisci la tua password')
    .min(8, 'La password deve essere almeno di 8 caratteri'),
});

export type LoginSchemaType = z.infer<typeof loginSchema>