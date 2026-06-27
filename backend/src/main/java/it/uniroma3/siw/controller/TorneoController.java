package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.repository.PartitaRepository;
import it.uniroma3.siw.service.TorneoService;

@Controller
public class TorneoController {
	@Autowired
	private TorneoService torneoService;
	@Autowired
	private PartitaRepository partitaRepository;
	
	@GetMapping("/tornei")
	public String showTornei(Model model) {
		model.addAttribute("tornei",this.torneoService.findAllTornei());
		return "tornei";
	}
	
	@GetMapping("/torneo/{id}")
	public String showDettaglioTorneo(@PathVariable("id")Long id,Model model) {
		Torneo torneo=this.torneoService.findTorneoConSquadre(id);
		List<Partita>partite=this.partitaRepository.findCalendarioByTorneoId(id);
		model.addAttribute("classifica",this.torneoService.calcolaClassifica(id));
		model.addAttribute("torneo",torneo);
		model.addAttribute("partite",partite);
		
		
		return "torneo";
	}
	

}
