package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Credentials;

public interface CredentialsRepository extends CrudRepository<Credentials, Long>{
	public Optional<Credentials>findByUsername(String usernam);
	
	public boolean existsByUsername(String username); //Per verificare se l'username è gia registrato
	

}
