package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.exceptions.DuplicateUsernameException;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsService {
	@Autowired
	private CredentialsRepository credentialsRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public Credentials saveCredentials(Credentials credentials) {
		
		//REGOLA DI BUSINESS:se l'username esiste già , lancia l'eccezione
		if(credentialsRepository.existsByUsername(credentials.getUsername())) {
			throw new DuplicateUsernameException(credentials.getUsername());
		}
		credentials.setRole(Credentials.DEFAULT_ROLE);
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		return credentialsRepository.save(credentials);
	}
	
	public Credentials findByUsername(String username) {
		return credentialsRepository.findByUsername(username).orElse(null);
	}

}
