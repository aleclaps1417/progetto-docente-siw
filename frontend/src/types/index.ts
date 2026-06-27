export interface Torneo {
  id: number;
  nome: string;
  anno: number;
  descrizione?: string; // Il "?" indica che questo campo è opzionale
}

export interface Partita {
  id: number;
  dataEOra: string | null;
  luogo: string | null;
  squadraCasa: string;
  squadraTrasferta: string;
  goalsHome: number | null;
  goalsAway: number | null;
  status: string;
}

export interface Giocatore {
  id: number;
  nome: string;
  cognome: string;
  ruolo: string | null;
  altezza: number | null;
  dataDiNascita: string | null;
  squadra: string | null;
  squadraId: number | null;
}