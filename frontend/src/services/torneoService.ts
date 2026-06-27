import { api } from './api';
import type { Torneo } from '../types';

export async function getTornei(): Promise<Torneo[]> {
  try {
    const { data } = await api.get<Torneo[]>('/rest/tornei');
    return data;
  } catch (error) {
    console.error("Errore durante il caricamento dei tornei:", error);
    throw new Error("Impossibile caricare i tornei. Verifica che il server sia acceso.");
  }
}