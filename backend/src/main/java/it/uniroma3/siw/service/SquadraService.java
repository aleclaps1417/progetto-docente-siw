package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.exceptions.RisorsaNonTrovataException;
import it.uniroma3.siw.repository.SquadraRepository;

@Service
public class SquadraService {
	
	@Autowired
	private SquadraRepository squadraRepository;
	
	//CASO D'USO: Dettaglio Squadra con Giocatori
	@Transactional(readOnly=true)
	public Squadra findSquadraWithGiocatori(Long id) {
		return this.squadraRepository.findByIdWithGiocatori(id).orElseThrow(() -> new RisorsaNonTrovataException("la Squadra", id));
	}
	
	//In caso volessi non servissero i giocatori 
	@Transactional(readOnly=true)
	public Squadra findSquadraById(Long id) {
		return this.squadraRepository.findById(id).orElseThrow(() -> new RisorsaNonTrovataException("la Squadra", id));
	}
	
	//Per avere tutte le squadre
	@Transactional(readOnly = true)
    public Iterable<Squadra> findAllSquadre() {
        return this.squadraRepository.findAll();
    }
	@Transactional
    public Squadra save(Squadra squadra) {
        return this.squadraRepository.save(squadra);
    }
	
	@Transactional
	public void deleteSquadra(Long id) {
		this.squadraRepository.deleteById(id);
	}
	
	@Transactional(readOnly=true)
	public Iterable<Squadra> findSquadreNonNelTorneo(Long toneoId){
		return this.squadraRepository.findSquadreNonNelTorneo(toneoId);
	}

}
