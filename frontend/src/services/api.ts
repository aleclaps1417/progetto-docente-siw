import axios from 'axios';

// Creiamo un'istanza personalizzata di Axios
export const api = axios.create({
  // Questo è l'indirizzo base del tuo backend Spring Boot
  baseURL: 'http://localhost:8080',
  
  // Qui in futuro potremo aggiungere altre configurazioni globali,
  // come ad esempio degli header o dei timeout
});