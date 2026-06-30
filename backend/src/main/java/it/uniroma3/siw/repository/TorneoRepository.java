package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Torneo;

public interface TorneoRepository extends CrudRepository<Torneo, Long>{
	// Recupera tutti i tornei ordinati dal più recente al più vecchio
    public List<Torneo>findAllByOrderByAnno();
    
    @Query("SELECT t FROM Torneo t LEFT JOIN FETCH t.squadre WHERE t.id = :id")
    public Optional<Torneo> findByIdWithSquadre(@Param("id") Long id);

    @EntityGraph(attributePaths = {"squadre"})
    @Query("SELECT t FROM Torneo t WHERE t.id = :id")
    Optional<Torneo> findByIdWithEntityGraph(@Param("id") Long id);
}
