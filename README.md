# Gestione Tornei - Progetto Docente SIW

Sistema informativo per la gestione di tornei di calcio amatoriale, sviluppato come progetto docente per il corso di **Sistemi Informativi su Web (SIW)** - Università degli Studi Roma Tre, A.A. 2025/2026.

**Studente**: Alessandro Claps  

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


## Entità principali
- **User**:dati anagrafici dell'utente (nome, cognome, email)
- **Torneo**:nome, anno, descrizione
- **Squadra**:nome, città, anno di fondazione
- **Partita**:data e ora, luogo, gol casa, gol trasferta, status (SCHEDULED / PLAYED / CANCELLED)
- **Giocatore**:nome, cognome, ruolo, altezza, data di nascita
- **Arbitro**:nome, cognome, codice arbitrale (unique)
- **Credentials**: username, password , ruolo (DEFAULT_ROLE / ADMIN_ROLE)
- **Commento**: testo, data creazione, autore (User), partita associata

## Casi d'uso implementati

### Utenti pubblici
1. **UC1** -Visualizzazione dell'elenco dei tornei
2. **UC2** -Visualizzazione del dettaglio di un torneo
3. **UC3** - Visualizzazione delle squadre partecipanti
4. **UC4** -Visualizzazione del calendario delle partite
5. **UC5** - Visualizzazione del dettaglio di una squadra (con giocatori)
6. **UC6** - Visualizzazione della classifica del torneo

### Utenti registrati
7. **UC7** - Visualizzazione commenti
8. **UC8** - Inserimento e modifica di un proprio commento ad una partita (un utente può modificare solo i propri commenti)

### Amministratore
9. **UC9** - Creazione e modifica di un torneo
10. **UC10** - Inserimento e modifica di una squadra
11. **UC11** - Inserimento e modifica dei giocatori
12. **UC12** - Registrazione di una partita
13. **UC13** - Inserimento del risultato di una partita
14. **UC14** - Eliminazione di squadre e partite