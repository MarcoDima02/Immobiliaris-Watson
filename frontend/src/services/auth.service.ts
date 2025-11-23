/**
 * Types
 */
import type { User } from '@/store/auth.store';

const API_URL = import.meta.env.VITE_API_URL;

export async function loginRequest(
  email: string,
  password: string
): Promise<User> {
  const response = await fetch(`${API_URL}/utenti/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password }),
  });

  if (!response.ok) {
    const msg = await response.text();
    throw new Error(msg || 'Errore durante il login');
  }

  return await response.json();
}
