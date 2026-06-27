package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Giocatore;

public interface GiocatoreRepository extends CrudRepository<Giocatore,Long> {

	public boolean existsByNomeAndCognomeAndDataDiNascita(String nome,String cognome,LocalDate localDate);
	
	public Giocatore findByNomeAndCognomeAndDataDiNascita(String nome,String cognome,LocalDate localDate);
}
