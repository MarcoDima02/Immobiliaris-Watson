# Frontend Authentication — Login & Registration (developer notes)

This document explains how the backend login and registration APIs are designed so frontend developers can implement UI safely and correctly.

## Overview
- Backend base API path: `/api` (e.g. `http://localhost:8080/api`)
- Dev frontend (Vite) runs on `http://localhost:5173` and can call backend at `http://localhost:8080/api`.
- Two relevant endpoints:
  - POST `/api/utenti` — create (register) user
  - POST `/api/utenti/login` — authenticate (login) user

> Important: authentication uses BCrypt via the backend PasswordEncoder. Frontend should never attempt to "hash" passwords; send plaintext over TLS in production (or in dev over localhost) and let backend hash/verify.

---

## Register (POST /api/utenti)

Purpose: create a new user. The backend will hash passwords automatically for roles that require credentials (AGENTE, AMMINISTRATORE).

Payload shape (JSON) — required fields for AGENTE / AMMINISTRATORE:

{
  "nome": "Mario",
  "cognome": "Rossi",
  "telefono": "3331234567",
  "email": "mario.rossi@example.com",
  "passwordHash": "<plaintext password here when creating>",
  "ruolo": "AGENTE", // or AMMINISTRATORE or PROPRIETARIO
  "verificaEmail": true,
  "consensoPrivacy": true
}

Notes:
- The backend field is named `passwordHash` but should be provided with the plaintext password on create/update; the service will encode/hash it before saving.
- If `ruolo` is AGENTE or AMMINISTRATORE, a non-empty password is mandatory — backend enforces this.
- For PROPRIETARIO (owner) the password is optional.

Errors:
- Backend returns 400 if JSON payload is invalid or required fields missing.

---

## Login (POST /api/utenti/login)

Purpose: authenticate using email + password.

Payload (JSON):
{
  "email": "user@example.com",
  "password": "cleartext-password"
}

Response (200 OK):
{
  "user": { /* Utente object without passwordHash (server strips it) */ },
  "redirectTo": "/agente/dashboard" // depends on role
}

Notes on behavior:
- Backend uses BCrypt: `PasswordEncoder.matches(raw, storedHash)` to verify password. This is one‑way — passwords are not and cannot be "dehashed".
- `redirectTo` helps the frontend decide where to navigate on success. Example mapping is:
  - AGENTE -> `/agente/dashboard`
  - AMMINISTRATORE -> `/admin/dashboard`
  - PROPRIETARIO (default) -> `/`

Errors:
- 400 Bad Request for malformed JSON or missing fields.
- 401 Unauthorized for invalid credentials.

---

## CORS & Dev notes
- Backend allows CORS from `http://localhost:5173` and a few other local origins. If you run the frontend on a different host/port you may need to add it to `WebConfig.addCorsMappings`.
- In dev you can use `import.meta.env.VITE_API_URL` (optional) if the frontend needs to target a different API base; by default frontend code uses the relative `/api/` path which will work when the frontend is served from the same host or proxied correctly.

## Quick test examples (developer)

- Register a user with curl (plaintext password — backend will hash it):
```bash
curl -X POST http://localhost:8080/api/utenti \
  -H 'Content-Type: application/json' \
  -d '{"nome":"Test","cognome":"Agente","telefono":"3330001111","email":"qa.agente@example.com","passwordHash":"agente123","ruolo":"AGENTE","verificaEmail":true,"consensoPrivacy":true}'
```

- Login example:
```bash
curl -X POST http://localhost:8080/api/utenti/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"qa.agente@example.com","password":"agente123"}'
```

Response will be `200 OK` with JSON containing `user` (without passwordHash) and `redirectTo` telling the frontend where to navigate.

---

## Recommendations for frontend implementers
- Use secure HTTPS in production — do not send credentials over plain HTTP.
- Do not attempt to implement hashing in frontend; let backend manage hashing and validation.
- Validate forms client-side (email + password length) but rely on backend for final validation & error messages.
- Add clear UI feedback for success/failure and handle `401` / `400` responses gracefully.

If you want, I can also provide a small playground example component (hooks + API wrapper) for the team to reuse.
