package it.uniroma3.siw.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Squadra;

public interface SquadraRepository extends CrudRepository<Squadra, Long>{
	@Query("SELECT s FROM Squadra s LEFT JOIN FETCH s.giocatori WHERE s.id=:id")
	public Optional<Squadra> findByIdWithGiocatori(@Param("id")Long id);

	//Trova tutte le squadre che non fanno parte del torneo specificato
	@Query("SELECT s FROM Squadra s WHERE s.id NOT IN (SELECT sq.id FROM Torneo t JOIN t.squadre sq WHERE t.id=:torneoId)")
	public Iterable<Squadra>findSquadreNonNelTorneo(@Param("torneoId")Long torneoId);
	
	public boolean existsByNome(String nome);
	
	public Squadra findByNome(String nome);
}
