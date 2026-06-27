import { api } from './api';
import type { Partita } from '../types';

export async function getPartiteByTorneo(torneoId: number): Promise<Partita[]> {
  try {
    const { data } = await api.get<Partita[]>(`/rest/tornei/${torneoId}/partite`);
    return data;
  } catch (error) {
    console.error("Errore durante il caricamento delle partite:", error);
    throw new Error("Impossibile caricare il calendario partite.");
  }
}