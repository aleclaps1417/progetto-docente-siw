package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.service.SquadraService;

@Controller
public class SquadraController {
	
	@Autowired
	private SquadraService squadraService;
	
	@GetMapping("/squadra/{id}")
	 public String showDettaglioSquadra(@PathVariable("id")Long id,Model model) {
		Squadra squadra=this.squadraService.findSquadraWithGiocatori(id);
		
		model.addAttribute("squadra",squadra);
		return "squadra";
	}

}
