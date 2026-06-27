package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.PartitaStatus;

public interface PartitaRepository extends CrudRepository<Partita, Long> {
	//estrae tutte le partite appartenenti a un torneo specifico, ordinate cronologicamente. Utilizza una doppia clausola JOIN FETCH per caricare immediatamente
	//in memoria (EAGER locale) le entità relative alla Squadra di Casa e alla Squadra in Trasferta
	@Query("SELECT p FROM Partita p " +
	           "JOIN FETCH p.squadraCasa " +
	           "JOIN FETCH p.squadraTrasferta " +
	           "WHERE p.torneo.id = :torneoId " +
	           "ORDER BY p.dataEOra ASC")
	    public List<Partita> findCalendarioByTorneoId(@Param("torneoId") Long torneoId);
	
	@Query("SELECT p FROM Partita p " +
	           "JOIN FETCH p.squadraCasa " +
	           "JOIN FETCH p.squadraTrasferta " +
	           "WHERE p.torneo.id = :torneoId AND p.status = :status") 
	    public List<Partita> findPartiteGiocateByTorneoId(@Param("torneoId") Long torneoId, @Param("status") PartitaStatus status);
	
	
	//Ti da la partita e i commenti insieme 
	@Query("SELECT p FROM Partita p LEFT JOIN FETCH p.commentiPartita WHERE p.id = :id")
    public Partita findByIdWithCommenti(@Param("id") Long id);
}
