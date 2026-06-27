package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.exceptions.RisorsaNonTrovataException;
import it.uniroma3.siw.repository.PartitaRepository;


@Service
public class PartitaService {
	@Autowired
	private PartitaRepository partitaRepository;
	
	@Transactional(readOnly=true)
	public List<Partita> calendarioPartite(Long torneoId){
		return this.partitaRepository.findCalendarioByTorneoId(torneoId);
	}
	
	public Partita partitaConCommenti(Long partitaId) {
		return this.partitaRepository.findByIdWithCommenti(partitaId);
	}
	
	@Transactional
	public Partita save(Partita partita) {
		return this.partitaRepository.save(partita);
	}
	
	
	
	@Transactional
	public void deleteById(Long id) {
		this.partitaRepository.deleteById(id);
	}
	@Transactional(readOnly=true)
	public Partita findById(Long id) {
		return this.partitaRepository.findById(id).orElseThrow(() -> new RisorsaNonTrovataException("la Partita", id));
	}
	
	
}
