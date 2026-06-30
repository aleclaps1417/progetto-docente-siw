-- ==========================================
-- 1. TORNEO (ID: 1000)
-- ==========================================
INSERT INTO torneo (id, nome, anno, descrizione) VALUES (1000, 'Coppa Estiva', 2026, 'Il torneo più atteso dell''anno');

-- ==========================================
-- 2. SQUADRE (ID: 2000 e 2001)
-- ==========================================
INSERT INTO squadra (id, nome, citta, anno_fondazione) VALUES (2000, 'Roma Sparita', 'Roma', 1927);
INSERT INTO squadra (id, nome, citta, anno_fondazione) VALUES (2001, 'Milano Centrale', 'Milano', 1908);
INSERT INTO squadra (id, nome, citta, anno_fondazione) VALUES (2002, 'Juventus Storica', 'Torino', 1897);
INSERT INTO squadra (id, nome, citta, anno_fondazione) VALUES (2003, 'Napoli Antica', 'Napoli', 1926);
INSERT INTO squadra (id, nome, citta, anno_fondazione) VALUES (2004, 'Fiorentina Classica', 'Firenze', 1926);
INSERT INTO squadra (id, nome, citta, anno_fondazione) VALUES (2005, 'Lazio Vintage', 'Roma', 1900);
INSERT INTO squadra (id, nome, citta, anno_fondazione) VALUES (2006, 'Genoa Originale', 'Genova', 1893);
INSERT INTO squadra (id, nome, citta, anno_fondazione) VALUES (2007, 'Bologna Storica', 'Bologna', 1909);
INSERT INTO squadra (id, nome, citta, anno_fondazione) VALUES (2008, 'Torino Granata', 'Torino', 1906);
INSERT INTO squadra (id, nome, citta, anno_fondazione) VALUES (2009, 'Sampdoria Blucerchiata', 'Genova', 1946);
INSERT INTO squadra (id, nome, citta, anno_fondazione) VALUES (2010, 'Atalanta Nerazzurra', 'Bergamo', 1907);
INSERT INTO squadra (id, nome, citta, anno_fondazione) VALUES (2011, 'Verona Gialloblù', 'Verona', 1903);
INSERT INTO squadra (id, nome, citta, anno_fondazione) VALUES (2012, 'Palermo Rosanero', 'Palermo', 1900);
INSERT INTO squadra (id, nome, citta, anno_fondazione) VALUES (2013, 'Cagliari Sarda', 'Cagliari', 1920);
INSERT INTO squadra (id, nome, citta, anno_fondazione) VALUES (2014, 'Parma Crociata', 'Parma', 1913);

-- =========================================
-- 3. ISCRIZIONE SQUADRE AL TORNEO 

-- ==========================================
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES (1000, 2000);
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES (1000, 2001);
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES (1000, 2002);
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES (1000, 2003);
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES (1000, 2004);
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES (1000, 2005);
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES (1000, 2006);
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES (1000, 2007);
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES (1000, 2008);
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES (1000, 2009);
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES (1000, 2010);
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES (1000, 2011);
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES (1000, 2012);
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES (1000, 2013);
INSERT INTO torneo_squadre (tornei_id, squadre_id) VALUES (1000, 2014);

-- ==========================================
-- 4. GIOCATORI (ID da 3000 in poi)
-- ==========================================

-- Giocatori della Squadra 2000 (Roma Sparita)
INSERT INTO giocatore (id, nome, cognome, ruolo, squadra_id) VALUES (3001, 'Francesco', 'Totti', 'Attaccante', 2000);
INSERT INTO giocatore (id, nome, cognome, ruolo, squadra_id) VALUES (3002, 'Daniele', 'De Rossi', 'Centrocampista', 2000);
INSERT INTO giocatore (id, nome, cognome, ruolo, squadra_id) VALUES (3003, 'Alisson', 'Becker', 'Portiere', 2000);
INSERT INTO giocatore (id, nome, cognome, ruolo, squadra_id) VALUES (3004, 'Aldair', 'Nascimento', 'Difensore', 2000);

-- Giocatori della Squadra 2001 (Milano Centrale)
INSERT INTO giocatore (id, nome, cognome, ruolo, squadra_id) VALUES (3005, 'Javier', 'Zanetti', 'Difensore', 2001);
INSERT INTO giocatore (id, nome, cognome, ruolo, squadra_id) VALUES (3006, 'Diego', 'Milito', 'Attaccante', 2001);
INSERT INTO giocatore (id, nome, cognome, ruolo, squadra_id) VALUES (3007, 'Wesley', 'Sneijder', 'Centrocampista', 2001);
INSERT INTO giocatore (id, nome, cognome, ruolo, squadra_id) VALUES (3008, 'Julio', 'Cesar', 'Portiere', 2001);

-- ==========================================
-- 5. ARBITRI (ID da 5000 in poi)
-- ==========================================
INSERT INTO arbitro (id, nome, cognome, codice_arbitrale) VALUES (5001, 'Pierluigi', 'Collina', 'AIA-001');
INSERT INTO arbitro (id, nome, cognome, codice_arbitrale) VALUES (5002, 'Daniele', 'Orsato', 'AIA-002');
INSERT INTO arbitro (id, nome, cognome, codice_arbitrale) VALUES (5003, 'Gianluca', 'Rocchi', 'AIA-003');

-- ==========================================
-- 6. PARTITE (ID da 4000 in poi)
-- ==========================================

-- Partita 1: Roma Sparita vs Milano Centrale (Già giocata, vince la Roma 2-1)
INSERT INTO partita (id, dataeora, goals_home, goals_away, status, squadra_casa_id, squadra_trasferta_id, torneo_id,arbitro_id)VALUES (4001, '2026-06-10 20:45:00', 2, 1, 'PLAYED', 2000, 2001, 1000,5001);

-- Partita 2: Ritorno Milano Centrale vs Roma Sparita (Da giocare)
INSERT INTO partita (id, dataeora, goals_home, goals_away, status, squadra_casa_id, squadra_trasferta_id, torneo_id,arbitro_id)VALUES (4002, '2026-06-17 20:45:00', NULL, NULL, 'SCHEDULED', 2001, 2000, 1000,5002);



-- ==========================================
-- 7. UTENTI E CREDENZIALI
-- ==========================================

INSERT INTO users(id, name, surname, email) VALUES(nextval('users_seq'), 'Alessandro', 'Claps', 'admin@gestionetornei.it');
INSERT INTO credentials(id, username, password, role, user_id)VALUES(nextval('credentials_seq'),'alessandro','$2a$10$3HK4W5wK9ZUl4mxV/ikvxOQGv1rlpIzbbrt3.pcZzNHnuilDweseO','ADMIN',currval('users_seq'));
--Settiamo di default un amministratore 
--INSERT INTO credentials(id,username,password,role)values(nextval('credentials_seq'),'alessandro','$2a$10$3HK4W5wK9ZUl4mxV/ikvxOQGv1rlpIzbbrt3.pcZzNHnuilDweseO','ADMIN');