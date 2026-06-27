# Gestione Tornei - Progetto Docente SIW

Sistema informativo per la gestione di tornei di calcio amatoriale, sviluppato come progetto docente per il corso di **Sistemi Informativi su Web (SIW)** - Università degli Studi Roma Tre, A.A. 2025/2026.

**Studente**: Alessandro Claps  
**Appello**: Luglio 2026

## Descrizione

L'applicazione consente di gestire uno o più tornei di calcio amatoriale, registrandone le squadre partecipanti, i giocatori, il calendario delle partite, i risultati e la classifica. Il sistema distingue tra utenti generici, utenti registrati (che possono commentare le partite) e amministratori (che gestiscono i tornei e tutte le entità associate).

## Tecnologie

### Backend
- **Spring Boot**
- **Spring Security** 
- **Spring Data JPA / Hibernate**
- **PostgreSQL**
- **Thymeleaf** per il frontend principale

### Frontend React (lista tornei, calendario partite, gestione giocatori)
- **React ** + **TypeScript**
- **Vite** come build tool
- **React Router** per il routing client-side
- **Material UI** per i componenti grafici
- **Axios** per le chiamate HTTP

