import { api } from './api';
import type { Giocatore } from '../types';

export async function getGiocatori(): Promise<Giocatore[]> {
  try {
    const { data } = await api.get<Giocatore[]>('/rest/giocatori');
    return data;
  } catch (error) {
    console.error("Errore durante il caricamento dei giocatori:", error);
    throw new Error("Impossibile caricare l'elenco dei giocatori.");
  }
}