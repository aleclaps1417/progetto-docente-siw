package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Giocatore;

import it.uniroma3.siw.model.exceptions.RisorsaNonTrovataException;
import it.uniroma3.siw.repository.GiocatoreRepository;


@Service
public class GiocatoreService {
	@Autowired
	private GiocatoreRepository giocatoreRepository;
	
	@Transactional
	public Giocatore save(Giocatore giocatore) {
		return this.giocatoreRepository.save(giocatore);
	}
	
	@Transactional(readOnly=true)
	public Iterable<Giocatore> findAllGiocatori(){
		return this.giocatoreRepository.findAll();
	}
	
	@Transactional(readOnly=true)
	public Giocatore findGiocatoreById(Long id) {
		return this.giocatoreRepository.findById(id)
				.orElseThrow(() -> new RisorsaNonTrovataException("il Giocatore", id));
	}
	

}
