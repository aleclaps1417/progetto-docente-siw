# Amateur Football Tournament Management System

A full-stack web application developed for the Web Information Systems (SIW) course at Università degli Studi Roma Tre (A.Y. 2025/2026). 

## Description
This system manages amateur football tournaments, handling participating teams, players, match schedules, results, and current standings. It implements Role-Based Access Control (RBAC) with distinct privileges for public visitors, registered users, and system administrators. 

## Tech Stack

### Backend
* **Java / Spring Boot:** Core backend framework.
* **Spring Security:** Authentication and authorization.
* **Spring Data JPA / Hibernate:** ORM and database interactions.
* **PostgreSQL:** Relational database management.
* **Thymeleaf:** Server-side rendering for the main frontend application.

### Frontend (React Application)
* **React + TypeScript:** Client-side architecture for tournament listings, match schedules, and player management.
* **Vite:** Build tool and development server.
* **React Router:** Client-side routing.
* **Material UI (MUI):** UI component library.
* **Axios:** HTTP client for REST API communication.

## Core Entities
* **User:** Personal details (name, surname, email) and credentials.
* **Tournament:** Name, year, description.
* **Team:** Name, city, founding year.
* **Match:** Date/time, location, home/away goals, status (SCHEDULED / PLAYED / CANCELLED).
* **Player:** Name, surname, role, height, date of birth.
* **Referee:** Name, surname, unique referee ID.
* **Role/Credentials:** Username, password, role level (DEFAULT_ROLE / ADMIN_ROLE).
* **Comment:** Text, creation timestamp, author (User), associated match.

## Implemented Use Cases

### Public Users
* Browse the list of active and past tournaments.
* View tournament details, participating teams, and current standings.
* Access match schedules and results.
* View specific team details and player rosters.

### Registered Users
* Read match commentary.
* Publish, edit, and manage their own comments on specific matches.

### Administrators
* **Tournaments:** Create and update tournament details.
* **Teams & Players:** Manage team registrations, rosters, and player profiles (Insert/Update/Delete).
* **Matches:** Schedule matches, assign referees, and record final scores.
