# ============================================
# Stage 1: Build con Node.js e Vite
# ============================================
FROM node:20-alpine AS builder

WORKDIR /app

# Copia package.json e lock file per cache delle dipendenze
COPY package*.json ./
RUN npm ci

# Copia il codice sorgente
COPY . .

# Build production
RUN npm run build

# ============================================
# Stage 2: Serve con Nginx
# ============================================
FROM nginx:alpine

# Copia i file buildati
COPY --from=builder /app/dist /usr/share/nginx/html

# Copia configurazione Nginx personalizzata per SPA
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Espone porta 80
EXPOSE 80

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost/health || exit 1

CMD ["nginx", "-g", "daemon off;"]
