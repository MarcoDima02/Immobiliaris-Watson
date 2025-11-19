export const links = [
  {
    href: '#home',
    label: 'Home',
  },
  {
    href: '#come-funziona',
    label: 'Come funziona',
  },
  {
    href: '#valutazione',
    label: 'Valuta ora',
  },
  {
    href: '#agenti',
    label: 'Agenti',
  },
  {
    href: '#chi-siamo',
    label: 'Chi siamo',
  },
  {
    href: '#inizia-gratis',
    label: 'Inizia gratis',
  },
];

export const capToProvinceMap: Record<string, string> = {
  // Piemonte
  "10100": "TO",
  "10098": "TO",
  "10121": "TO",
  "10125": "TO",
  "10043": "TO",
  "12084": "CN",
  "12010": "CN",
  "13100": "VC",
  "28100": "NO",
  "15121": "AL",

  // Lombardia
  '20100': 'MI',
  '20121': 'MI',
  '20122': 'MI',
  '20123': 'MI',
  '20133': 'MI',
  '25121': 'BS',
  '23100': 'SO',
  '24020': 'BG',
  '20090': 'MI',
  '21013': 'VA',

  // Lazio
  '00100': 'RM',
  '00118': 'RM',
  '00184': 'RM',
  '00019': 'RM',
  '00144': 'RM',
  '00040': 'RM',
  '00054': 'RM',

  // Campania
  '80100': 'NA',
  '80035': 'NA',
  '80077': 'NA',
  '81020': 'CE',
  '83040': 'AV',
  '84081': 'SA',
  '80059': 'NA',
  '80026': 'NA',

  // Veneto
  '30100': 'VE',
  '35121': 'PD',
  '37121': 'VR',
  '36100': 'VI',
  '36040': 'VI',
  '37057': 'VR',
  '35010': 'PD',

  // Toscana
  '50100': 'FI',
  '56121': 'PI',
  '52100': 'AR',
  '53100': 'SI',
  '55011': 'LU',
  '56019': 'PI',
  '51100': 'PT',
  '54033': 'MS',
  '52027': 'AR',

  // Puglia
  '70121': 'BA',
  '73050': 'LE',
  '73100': 'LE',
  '74023': 'TA',
  '71016': 'FG',
  '72015': 'BR',
  '73010': 'LE',
  '74015': 'TA',

  // Sicilia
  '90100': 'PA',
  '95100': 'CT',
  '98121': 'ME',
  '91016': 'TP',
  '90010': 'PA',
  '93012': 'CL',
  '97015': 'RG',
  '95010': 'CT',
  '91011': 'TP',

  // Emilia-Romagna
  '40121': 'BO',
  '41121': 'MO',
  '43121': 'PR',
  '47121': 'FC',
  '44022': 'FE',
  '48018': 'RA',
  '42015': 'RE',
  '43044': 'PR',
  '42024': 'RE',

  // Sardegna
  '09100': 'CA',
  '07026': 'SS',
  '08020': 'NU',
  '09010': 'VS',
  '07040': 'OT',
  '08028': 'NU',

  // Liguria
  '16121': 'GE',
  '18100': 'IM',
  '17031': 'SV',
  '16035': 'GE',
  '18038': 'IM',

  // Trentino-Alto Adige
  '38100': 'TN',
  '39012': 'BZ',
  '38066': 'TN',
  '39040': 'BZ',

  // Friuli-Venezia Giulia
  '34121': 'TS',
  '33010': 'UD',
  '33045': 'PN',
  '34070': 'GO',

  // Marche
  '60121': 'AN',
  '63100': 'AP',
  '60035': 'AN',
  '62010': 'MC',
  '61032': 'PU',

  // Abruzzo
  '65121': 'PE',
  '67100': 'AQ',
  '66010': 'CH',
  '64026': 'TE',
  '67039': 'AQ',

  // Umbria
  '06121': 'PG',
  '05100': 'TR',
  '06010': 'PG',
  '05035': 'TR',

  // Molise
  '86100': 'CB',
  '86039': 'IS',
  '86020': 'CB',

  // Basilicata
  '85100': 'PZ',
  '85050': 'PZ',
  '75100': 'MT',

  // Valle d’Aosta
  '11100': 'AO',
  '11020': 'AO',
};


export const cityToProvinceMap: Record<string, string> = {
  // Piemonte
  "Torino": "TO",
  "Alessandria": "AL",
  "Novara": "NO",
  "Vercelli": "VC",
  "Cuneo": "CN",
  // Lombardia
  "Milano": "MI",
  "Brescia": "BS",
  "Bergamo": "BG",
  "Como": "CO",
  // Lazio
  "Roma": "RM",
  "Frosinone": "FR",
  "Latina": "LT",
  "Rieti": "RI",
  "Viterbo": "VT",
  // Campania
  "Napoli": "NA",
  "Caserta": "CE",
  "Salerno": "SA",
  "Avellino": "AV",
  "Benevento": "BN",
  // Toscana
  "Firenze": "FI",
  "Pisa": "PI",
  "Siena": "SI",
  "Arezzo": "AR",
  "Prato": "PO",
  "Lucca": "LU",
  "Pistoia": "PT",
  // Puglia
  "Bari": "BA",
  "Lecce": "LE",
  "Taranto": "TA",
  "Brindisi": "BR",
  "Foggia": "FG",
  // Sicilia
  "Palermo": "PA",
  "Catania": "CT",
  "Messina": "ME",
  "Trapani": "TP",
  "Agrigento": "AG",
  "Siracusa": "SR",
  // Emilia-Romagna
  "Bologna": "BO",
  "Modena": "MO",
  "Parma": "PR",
  "Ferrara": "FE",
  "Ravenna": "RA",
  "Forlì": "FC",
  "Rimini": "RN",
};
