# Coefficienti e formula per il calcolo automatizzato del valore immobiliare

Documento tecnico rapido con i coefficienti suggeriti, la formula principale e esempi. Usatelo come base: i coefficienti sono raccomandazioni iniziali da calibrare con dati reali.

> Inputs considerati: tipo di immobile, area (m²), prezzo medio per m² (da `PrezzoPerCAP` per CAP), numero di stanze, numero di bagni, piano, numero di piani, presenza ascensore, garage, giardino, metratura degli spazi aggiuntivi (se fornita), tipo di riscaldamento, classe energetica, anno di costruzione.

---

## 1) Concetto e formula principale

1. Recupera il prezzo base per m²: `prezzo_mq = lookupPrezzoPerCAP(cap)` (fallback: media globale se non presente).
2. Calcola valore base:

$\text{valore_base} = \text{area} \times \text{prezzo\_mq}$

3. Calcola un fattore di aggiustamento totale come prodotto dei singoli fattori (ogni fattore espresso come 1 + percentuale):

$\text{fattore\_aggiustamento} = \prod_i (1 + c_i)$

Dove ogni $c_i$ è il coefficiente relativo a una caratteristica (garage, ascensore, etc.).

4. Valore medio stimato:

$\text{valore\_medio} = \text{valore\_base} \times \text{fattore\_aggiustamento}$

5. Range (min / max) basato su incertezza di base (`uncertainty`) e su qualità della fonte:

$\text{valore\_min} = \text{valore\_medio} \times (1 - \text{uncertainty})$ $\text{valore\_max} = \text{valore\_medio} \times (1 + \text{uncertainty})$

6. Confidence (punteggio 0..1): calcolato con una euristica a partire da qualità della fonte `PrezzoPerCAP`, completezza dei dati e presenza di immagini.

---

## 2) Coefficienti suggeriti (valori iniziali)

Nota: i coefficienti sono percentuali relative (ad es. +5% → 0.05). Salvare i coefficienti in config esterno (properties / DB) per poterli aggiustare senza deploy.

### A. Tipo di immobile (type_factor)

- apartment (Appartamento): 1.00 (baseline)
- house (Casa indipendente / villetta): 1.05
- villa: 1.15
- monolocale / studio: 0.95
- commercial: 0.95 (richiede regole diverse, valutare revenue-based)
- land (terreno): special handling (non applicare prezzo_mq come per edificio)

Implementazione: `type_factor = valore_fisso` e includere nel `fattore_aggiustamento` moltiplicando `valore_base * type_factor` oppure applicare `type_factor` come primo moltiplicatore.

### B. Area — effetto dimensionale (size band)

I prezzi al m² possono variare con la superficie. Consiglio bands:

- area < 40 m²: small_penalty = +5% (più costoso per m²)
- 40 ≤ area < 80: 0% (baseline)
- 80 ≤ area < 150: -2%
- area ≥ 150: -5%

Implementazione: `size_factor = 1 + size_adj` (es. per 85 m² → size_adj = -0.02 → size_factor = 0.98)

### C. Numero stanze (rooms)

Le stanze che vengono conteggiate nel numero sono:
- camere da letto
- soggiorno / sala da pranzo (1 stanza)
- cucina abitabile, cioè che abbia spazio per un tavolo e sedie (1 stanza)
- studio / ufficio (1 stanza)


Regola semplice: considerare come baseline 2 stanze.

- per ogni stanza aggiuntiva oltre 2: +3% (0.03)
- se stanze < 2: -2% per stanza mancante

Implementazione:
`rooms_factor = 1 + (rooms - 2) * 0.03` clamped ad esempio tra 0.9 e 1.3

### D. Numero bagni (bathrooms)

- baseline 1 bagno
- per bagno aggiuntivo: +2% ciascuno
- per 0 bagni: -5%

`bathrooms_factor = 1 + (bathrooms - 1) * 0.02` (clamp come appropriato)

### E. Piano (floor) e numero piani totali (totalFloors)

Semplice regola per appartamenti:

- piano terra: -3% (0.97)
- primo piano: 0% (baseline)
- piani intermedi (2..4): +1% per piano sopra il 1° se non troppo alto
- piano attico/top floor: +2% (vista) o +4% se vista eccellente

Se l'edificio ha ascensore e piano>1: aggiungere +3% (ascensore_factor)

Esempio implementazione:

```
if (floor == 0) floor_factor = 0.97;
else if (floor == 1) floor_factor = 1.00;
else if (floor >= 2 && floor <= 4) floor_factor = 1.00 + 0.01 * (floor - 1);
else floor_factor = 1.02; // top floors
if (hasElevator && floor > 1) elevator_factor = 1.03; else elevator_factor = 1.0;
```

### F. Garage / posti auto

- garage coperto: +5% (1.05)
- posto auto scoperto: +2% (1.02)

### G. Giardino

- giardino privato: +4% (1.04)
- terrazzo/balcone piccolo: +1–2% (1.01–1.02)

### H. Tipo di riscaldamento

- pompe di calore / sistema moderno: +3% (1.03)
- autonomo (buono): +2% (1.02)
- condominiale: +1% (1.01)
- nessun riscaldamento o impianto obsoleto: -5% (0.95)

### I. Classe energetica (APE)

Suggerimento: step per classe (es. rispetto a G baseline):

- A: +8% (1.08)
- B: +6% (1.06)
- C: +4% (1.04)
- D: +2% (1.02)
- E: 0% (1.00)
- F: -2% (0.98)
- G: -4% (0.96)

(Adattare a mercato locale: in alcune zone la classe energetica pesa di più.)

### J. Anno di costruzione / età

Regola semplice:

- se `yearBuilt` >= currentYear - 10 → +3% (nuovo)
- se 10 < age ≤ 30 → +1% (buono)
- se 30 < age ≤ 50 → 0%
- se age > 50 → -5% (se non ristrutturato)

Se `lastRenovationYear` è recente, applicare +5% aggiuntivo.

### K. Metratura aggiuntiva (giardino, balconi)

Aggiungere il valore proporzionale: se fornite `gardenArea`, `balconyArea` o `cantinaArea`, potete considerare che la superficie commerciale includa queste porzioni con fattori di sconto (es. balcone vale 30% del mq interno):

- effective_area = area + 0.3 * balconyArea + 0.5 * gardenArea + 0.6 * garageArea + 0.4 * cantinaArea (se applicabile)

Usare `effective_area` nella formula per ottenere valore più accurato.

### L. Cantina

- Cantina (cantina presente): coefficiente consigliato: +2% (1.02) se la cantina è considerata un valore aggiuntivo per l'immobile.
- Se è fornita la metratura della cantina (`cantinaArea` o `superficieCantina`), includere la metratura nel calcolo dell'`effective_area` usando un peso ridotto (es. 40%): `effective_area += 0.4 * cantinaArea`.
- Se la cantina è priva di valore commerciale nella zona (es. in alcuni mercati sotterranei), il coefficiente può essere 0.0; tarare in base al mercato locale.

Implementazione tipica:

```
if (hasCantina) cantina_factor = 1.02; else cantina_factor = 1.0;
effective_area = area + 0.3 * balconyArea + 0.5 * gardenArea + 0.6 * garageArea + 0.4 * cantinaArea;
```

Includere `cantina_factor` nel `fattore_aggiustamento` insieme agli altri fattori (garage, elevator, ecc.).

---

## 3) Composizione finale: pseudocode della formula

```text
prezzo_mq = lookupPrezzoPerCAP(cap)
valore_base = area_effettiva * prezzo_mq
fattore_aggiustamento = type_factor * size_factor * rooms_factor * bathrooms_factor * floor_factor * elevator_factor * garage_factor * garden_factor * heating_factor * energy_class_factor * age_factor
valore_medio = valore_base * fattore_aggiustamento
valore_min = valore_medio * (1 - uncertainty)
valore_max = valore_medio * (1 + uncertainty)
```

Dove `area_effettiva` = `area` + pesi su balconi/giardino ecc.

---

## 4) Uncertainty e confidence (policy consigliata)

Quando si usa solo `PrezzoPerCAP` (nessun comparabile), suggerisco:

- default base uncertainty = 0.08 (±8%) con `prezzo_mq` recente e dati completi
- 0.12 se `prezzo_mq` > 12 mesi
- 0.20 se mancano molti campi (energia, anno costruzione, nessun garage/giardino indicato)

Confidence heuristica (0..1):

- baseline CAP-only: 0.30
- +0.10 se `PrezzoPerCAP.quality_score` alta o `valid_from` entro 6 mesi
- +0.05 se `yearBuilt` fornito
- clamp(0.05, 0.95)

Esempio: prezzo recente + anno costruzione → confidence ≈ 0.45

---


## 5) Esempio numerico adattato (i dati che hai fornito)

Input forniti:

- area = 100 m²
- cap = 10073 → prezzo_mq = 1093 €/m² (fornito)
- tipo = apartment (Appartamento)
- stanze = 4
- bagni = 2
- piano = 1 (si estende per un piano)
- ascensore = no
- garage doppio: totale 40 m²
- giardino = no
- classe energetica = sconosciuta
- anno di costruzione = 1970
- balcone + terrazzo = 30 m² (totale)
- riscaldamento = autonomo

Assunzioni e parametri usati (come da documento):

- peso balcone/terrazzo = 0.3 (30% del mq)
- peso garageArea = 0.6 (60% del mq)
- peso cantinaArea = 0.4 (non presente qui)
- fattori da documento: size_factor per 100 m² = -2% (0.98), rooms +3% per stanza oltre 2, bathrooms +2% per bagno aggiuntivo, garage presence +5% (1.05), age >50 anni = -5% (0.95), heating autonomo = +2% (1.02)
- uncertainty scelta: 12% (0.12) — conservativa perché mancano alcuni dati (classe energetica)
- quality_score PrezzoPerCAP non fornito → uso default 0.80

1) Calcolo `effective_area`:

```
effective_area = area + 0.3 * balconyArea + 0.5 * gardenArea + 0.6 * garageArea + 0.4 * cantinaArea
								= 100 + 0.3*30 + 0.5*0 + 0.6*40 + 0.4*0
								= 100 + 9 + 24 = 133 m²
```

2) Valore base:

```
valore_base = effective_area * prezzo_mq = 133 * 1093 = 145.369,00 €
```

3) Coefficienti applicati (fattori):

- type_factor (apartment) = 1.00
- size_factor (100 m² -> banda 80–150) = 0.98
- rooms_factor (4 stanze; baseline 2): (4-2)*0.03 = +0.06 → 1.06
- bathrooms_factor (2 bagni; baseline 1): (2-1)*0.02 = +0.02 → 1.02
- floor_factor (piano 1) = 1.00
- elevator_factor = 1.00 (assenza)
- garage_factor (presenza) = 1.05
- garden_factor = 1.00
- energy_class_factor = 1.00 (sconosciuta)
- age_factor (1970, >50 anni, non ristrutturato) = 0.95
- heating_factor (autonomo) = 1.02
- cantina_factor = 1.00 (assente)

Prodotto dei fattori:

```
fattore_aggiustamento = 1.00 * 0.98 * 1.06 * 1.02 * 1.00 * 1.00 * 1.05 * 1.00 * 1.00 * 0.95 * 1.02
										 ≈ 1.07806458
```

4) Valore medio stimato:

```
valore_medio = valore_base * fattore_aggiustamento
						 ≈ 145.369 * 1.07806458 ≈ 156.717,31 €
```

5) Range (min / max) con `uncertainty = 0.12`:

```
valore_min = valore_medio * (1 - 0.12) ≈ 156.717,31 * 0.88 ≈ 137.911,23 €
valore_max = valore_medio * (1 + 0.12) ≈ 156.717,31 * 1.12 ≈ 175.523,39 €
```

6) Confidence (euristica):

- baseline CAP-only: 0.30
- +0.10 (PrezzoPerCAP disponibile, non fallback)
- +0.05 (anno costruzione fornito)
- nessun bonus per classe energetica (mancante)
- somma = 0.30 + 0.10 + 0.05 = 0.45
- applico quality_score (0.80): confidence = 0.45 * 0.80 = 0.36

Confidence finale ≈ 0.36 (36%)

---

## 6) Risultato finale della valutazione
- Valore medio stimato: circa 156.717,31 €
- Range di valutazione: 137.911,23 € – 175.523,39 €
- Confidence della stima: circa 0.36 (36%)
