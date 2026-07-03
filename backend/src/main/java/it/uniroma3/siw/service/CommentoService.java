package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.exceptions.RisorsaNonTrovataException;
import it.uniroma3.siw.repository.CommentoRepository;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional(readOnly = true)
	public Commento findByIdEVerificaAutore(Long id, User utenteLoggato) {
	    Commento commento = this.findById(id);
	    
	    if (commento == null) {
	        throw new IllegalArgumentException("Commento non trovato");
	    }
	    
	    if (!commento.getAutore().getId().equals(utenteLoggato.getId())) {
	        throw new AccessDeniedException("Non puoi modificare commenti di altri utenti");
	    }
	    
	    return commento;
	}

	@Transactional
	public Commento aggiornaCommento(Long commentoId, String nuovoTesto, User utenteLoggato) {
	    Commento commento = this.findById(commentoId);

	    if (commento == null) {
	        throw new IllegalArgumentException("Commento non trovato");
	    }
	    
	    if (!commento.getAutore().getId().equals(utenteLoggato.getId())) {
	        throw new AccessDeniedException("Non puoi modificare commenti di altri utenti");
	    }
	    
	    commento.setTesto(nuovoTesto);
	    return commentoRepository.save(commento);
	}
	

}
