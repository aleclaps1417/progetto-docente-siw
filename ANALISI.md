# Analisi Sperimentale: Strategie di Accesso ai Dati

## 1\. Caso d'Uso Analizzato

È stato preso in esame il caso d'uso **"Visualizzazione del dettaglio di un torneo con le squadre partecipanti"**, perché coinvolge una relazione `@ManyToMany` tra le entità `Torneo` e `Squadra`, che è il candidato tipico per il problema **N+1 query**.

### Setup del test

- **Database popolato con**: 1 Torneo (id=1000, "Coppa Estiva") con **15 squadre associate**  
- **Tipo relazione**: `@ManyToMany` LAZY (default per le collezioni)  
- **DBMS**: PostgreSQL  
- **Metodologia**: ogni test è stato eseguito **isolatamente**, riavviando l'applicazione, per garantire condizioni di partenza identiche

## 2\. Strategie Confrontate

### Strategia A — Fetch standard LAZY

Recupero del torneo tramite il metodo standard `findById()` di Spring Data JPA. La collezione `squadre` viene caricata pigramente quando vi si accede.

Torneo torneo \= torneoRepository.findById(id).get();

torneo.getSquadre().size();  // ← qui scatta il caricamento LAZY

### Strategia B — JPQL con JOIN FETCH

Query JPQL personalizzata che forza il caricamento eager della collezione nella stessa query del torneo:

@Query("SELECT t FROM Torneo t LEFT JOIN FETCH t.squadre WHERE t.id \= :id")

Optional\<Torneo\> findByIdWithSquadre(@Param("id") Long id);

### Strategia C — @EntityGraph

Approccio dichiarativo che istruisce Hibernate sulle relazioni da caricare eagerly, senza modificare la query JPQL:

@EntityGraph(attributePaths \= {"squadre"})

@Query("SELECT t FROM Torneo t WHERE t.id \= :id")

Optional\<Torneo\> findByIdWithEntityGraph(@Param("id") Long id);

## 3\. Output console dei test

ecco il risultato finale: \========================================================  
🚀 TEST 1: STRATEGIA LAZY (DEFAULT)  
\========================================================  
▶ FIND BY ID STANDARD (LAZY)  
\==\> Inizio findById...  
\[2m2026-06-28T16:22:39.882+02:00\[0;39m \[32mDEBUG\[0;39m \[35m12128\[0;39m \[2m--- \[progetto-docente\] \[           main\] \[0;39m\[36morg.hibernate.SQL                       \[0;39m \[2m:\[0;39m select t1\_0.id,t1\_0.anno,t1\_0.descrizione,t1\_0.nome from torneo t1\_0 where t1\_0.id=?  
Hibernate: select t1\_0.id,t1\_0.anno,t1\_0.descrizione,t1\_0.nome from torneo t1\_0 where t1\_0.id=?  
\==\> findById finito in 137 ms  
Nome Torneo: Coppa Estiva  
\==\> Inizio accesso a getSquadre()...  
\[2m2026-06-28T16:22:39.971+02:00\[0;39m \[32mDEBUG\[0;39m \[35m12128\[0;39m \[2m--- \[progetto-docente\] \[           main\] \[0;39m\[36morg.hibernate.SQL                       \[0;39m \[2m:\[0;39m select s1\_0.tornei\_id,s1\_1.id,s1\_1.anno\_fondazione,s1\_1.citta,s1\_1.nome from torneo\_squadre s1\_0 join squadra s1\_1 on s1\_1.id=s1\_0.squadre\_id where s1\_0.tornei\_id=?  
Hibernate: select s1\_0.tornei\_id,s1\_1.id,s1\_1.anno\_fondazione,s1\_1.citta,s1\_1.nome from torneo\_squadre s1\_0 join squadra s1\_1 on s1\_1.id=s1\_0.squadre\_id where s1\_0.tornei\_id=?  
\==\> getSquadre() finito in 19 ms  
Squadre trovate: 15  
⏱ Tempo TOTALE LAZY: 156 ms  
\========================================================  
🚀 TEST 2: STRATEGIA JOIN FETCH  
\========================================================  
▶ FIND BY ID CON JOIN FETCH  
\==\> Inizio findByIdWithSquadre...  
\[2m2026-06-28T16:23:58.901+02:00\[0;39m \[32mDEBUG\[0;39m \[35m11768\[0;39m \[2m--- \[progetto-docente\] \[           main\] \[0;39m\[36morg.hibernate.SQL                       \[0;39m \[2m:\[0;39m select t1\_0.id,t1\_0.anno,t1\_0.descrizione,t1\_0.nome,s1\_0.tornei\_id,s1\_1.id,s1\_1.anno\_fondazione,s1\_1.citta,s1\_1.nome from torneo t1\_0 left join torneo\_squadre s1\_0 on t1\_0.id=s1\_0.tornei\_id left join squadra s1\_1 on s1\_1.id=s1\_0.squadre\_id where t1\_0.id=?  
Hibernate: select t1\_0.id,t1\_0.anno,t1\_0.descrizione,t1\_0.nome,s1\_0.tornei\_id,s1\_1.id,s1\_1.anno\_fondazione,s1\_1.citta,s1\_1.nome from torneo t1\_0 left join torneo\_squadre s1\_0 on t1\_0.id=s1\_0.tornei\_id left join squadra s1\_1 on s1\_1.id=s1\_0.squadre\_id where t1\_0.id=?  
\==\> findByIdWithSquadre finito in 206 ms  
Nome Torneo: Coppa Estiva  
\==\> Inizio accesso a getSquadre()...  
\==\> getSquadre() finito in 0 ms (dovrebbe essere 0, è già in memoria)  
Squadre trovate: 15  
⏱ Tempo TOTALE JOIN FETCH: 207 ms  
\========================================================  
\========================================================  
🚀 TEST 3: STRATEGIA @ENTITYGRAPH  
\========================================================  
▶ FIND BY ID CON @ENTITYGRAPH  
\==\> Inizio findByIdWithEntityGraph...  
\[2m2026-06-28T16:24:53.986+02:00\[0;39m \[32mDEBUG\[0;39m \[35m3364\[0;39m \[2m--- \[progetto-docente\] \[           main\] \[0;39m\[36morg.hibernate.SQL                       \[0;39m \[2m:\[0;39m select t1\_0.id,t1\_0.anno,t1\_0.descrizione,t1\_0.nome,s1\_0.tornei\_id,s1\_1.id,s1\_1.anno\_fondazione,s1\_1.citta,s1\_1.nome from torneo t1\_0 left join torneo\_squadre s1\_0 on t1\_0.id=s1\_0.tornei\_id left join squadra s1\_1 on s1\_1.id=s1\_0.squadre\_id where t1\_0.id=?  
Hibernate: select t1\_0.id,t1\_0.anno,t1\_0.descrizione,t1\_0.nome,s1\_0.tornei\_id,s1\_1.id,s1\_1.anno\_fondazione,s1\_1.citta,s1\_1.nome from torneo t1\_0 left join torneo\_squadre s1\_0 on t1\_0.id=s1\_0.tornei\_id left join squadra s1\_1 on s1\_1.id=s1\_0.squadre\_id where t1\_0.id=?  
\==\> findByIdWithEntityGraph finito in 254 ms  
Nome Torneo: Coppa Estiva  
\==\> Inizio accesso a getSquadre()...  
\==\> getSquadre() finito in 0 ms (dovrebbe essere 0, è già in memoria)  
Squadre trovate: 15  
⏱ Tempo TOTALE @ENTITYGRAPH: 254 ms  
\======================================================== 

### 4.1 Tempi misurati

I tempi sono stati raccolti eseguendo ogni test in un'applicazione separata (con riavvio dell'app tra un test e l'altro), per evitare l'influenza della cache di Hibernate tra le strategie.

| Strategia | Tempo TOTALE | Query SQL eseguite |
| :---- | :---- | :---- |
| **LAZY** | **156 ms** | **2 query** |
| **JOIN FETCH** | **207 ms** | **1 query** |
| **@EntityGraph** | **254 ms** | **1 query** |

### 4.2 Numero di query SQL

- **LAZY**: 2 query  
  - Una per recuperare il torneo: `SELECT * FROM torneo WHERE id = ?`  
  - Una scatenata dall'accesso a `getSquadre()`: `SELECT * FROM torneo_squadre JOIN squadra ...`  
- **JOIN FETCH**: 1 query (con JOIN su 3 tabelle: `torneo`, `torneo_squadre`, `squadra`)  
- **@EntityGraph**: 1 query (identica a JOIN FETCH)

### 4.3 Osservazione importante: JOIN FETCH e @EntityGraph sono equivalenti

Come si evince dai log SQL, **JOIN FETCH e @EntityGraph generano esattamente la stessa query SQL**:  
Le due strategie sono **equivalenti a livello di SQL**; differiscono solo nella sintassi di dichiarazione in Java.

## 5\. Discussione dei risultati

### 5.1 Perché LAZY appare più veloce in termini di millisecondi

Un risultato apparentemente controintuitivo è che, nonostante esegua più query, LAZY ottiene il tempo totale più basso. Questo è dovuto a tre fattori che si combinano con un dataset di piccole dimensioni:

1. **Query più semplici da pianificare**: il LAZY esegue due query brevi su tabelle singole (`torneo` e poi `torneo_squadre JOIN squadra`). Il database può pianificare ed eseguire ciascuna query in modo molto efficiente.  
     
2. **JOIN FETCH e @EntityGraph fanno una query più complessa**: una `LEFT JOIN` su tre tabelle che restituisce 15 righe "denormalizzate", che Hibernate deve poi "ricostruire" in 1 oggetto `Torneo` \+ lista di 15 oggetti `Squadra`. Questo lavoro di deduplicazione/marshalling ha un costo.  
     
3. **Warmup di Hibernate inevitabile**: la prima query in qualsiasi strategia paga il costo di inizializzazione del framework (caricamento metadata, pool di connessioni, parsing del piano). Questo costo si manifesta sempre sulla query "principale" di ogni test.

### 5.2 Quando LAZY mostra invece i suoi limiti

Il problema N+1 e i benefici di JOIN FETCH si manifestano in modo evidente quando:

- **Si caricano molte entità**: ad esempio una lista di 100 tornei, ognuno con le proprie squadre. Con LAZY si farebbero 1 \+ 100 \= 101 query, con JOIN FETCH 1 sola.  
- **La latenza di rete è significativa**: ogni round-trip costa, sommare query separate aumenta linearmente l'attesa.  
- **Le entità sono in cache di applicazione**: la prima query non paga più il warmup, e i benefici di "una sola query" emergono.

Nel mio esperimento, con un singolo torneo e 15 squadre, queste condizioni non sono presenti, ed è per questo che i risultati appaiono "invertiti" rispetto alle aspettative tipiche dei testi teorici.

### 5.3 Hibernate moderno e la mitigazione del N+1

È importante notare che le versioni recenti di Hibernate **mitigano automaticamente** il caso peggiore del problema N+1. Invece di fare 1 query per il torneo e N query separate per ogni singola squadra (totale N+1), Hibernate aggrega il caricamento delle squadre della relazione in un'unica query in batch. Il risultato finale sono quindi **2 query** (1 per il torneo, 1 in batch per le squadre), non N+1.

Questo spiega perché LAZY in pratica non è più "catastrofico" come nei testi storici, anche se rimane meno efficiente di una singola query JOIN.

## 6\. Conclusioni e Scelta Implementativa

I risultati confermano che:

1. **JOIN FETCH e @EntityGraph sono equivalenti** in termini di SQL prodotta e prestazioni  
2. **Con dataset piccoli**, la differenza di tempi tra strategie è minima e talvolta invertita  
3. **Il vero vantaggio delle ottimizzazioni di fetch** emerge con dataset grandi o richieste concorrenti, dove il numero ridotto di query si traduce in minore carico sul database

Nel mio progetto ho applicato la strategia `LEFT JOIN FETCH` ai casi d'uso che richiedono il caricamento del torneo con le sue relazioni o delle partite con le squadre:

La strategia generale adottata è:

- **LAZY a livello globale** sulle entità (default), per non caricare collezioni quando non servono  
- **JOIN FETCH localmente** nei repository, solo quando il caso d'uso richiede l'oggetto completo

Questo approccio bilancia consumo di memoria e numero di query: carico solo quello che serve, quando serve, ma in un'unica query quando le relazioni sono indispensabili al caso d'uso.  
