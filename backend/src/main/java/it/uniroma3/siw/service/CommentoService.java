package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.exceptions.RisorsaNonTrovataException;
import it.uniroma3.siw.repository.CommentoRepository;
import jakarta.transaction.Transactional;

@Service
public class CommentoService {
	
	@Autowired
	private CommentoRepository commentoRepository;
	
	public Commento findById(Long id) {
		return commentoRepository.findById(id).orElseThrow(() -> new RisorsaNonTrovataException("il commento ", id));
	}
	
	@Transactional
	public Commento save(Commento commento) {
		return commentoRepository.save(commento);
	}
	
	@Transactional
	public Commento aggiornaCommento(Long commentoId,String nuovoTesto,User utenteLoggato) {
		Commento commento=this.findById(commentoId);
		
		if(commento==null) {
			throw new IllegalArgumentException("Commento non trovato");
		}
		// CONTROLLO DI SICUREZZA: L'utente che fa la richiesta è davvero l'autore?
        // Confrontiamo gli ID degli utenti 
		if(commento.getAutore().getId().equals(utenteLoggato.getId())){
			commento.setTesto(nuovoTesto);
			return commentoRepository.save(commento);
		}
		else {
			throw new RuntimeException("Operazione negata:puoi modificare solo i tuoi commenti");
		}
	}
	

}
