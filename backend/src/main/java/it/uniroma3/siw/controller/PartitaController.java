package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.service.CommentoService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.PartitaService;
import jakarta.validation.Valid;

@Controller
public class PartitaController {
	
	@Autowired
	private PartitaService partitaService;
	
	@Autowired
	private CommentoService commentoService;
	
	@Autowired 
	private CredentialsService credentialsService;
	
	
	@GetMapping("/partita/{id}")
	public String getPartita(@PathVariable("id") Long id,Model model) {
		Partita partita=partitaService.partitaConCommenti(id);
		model.addAttribute("partita",partita);
		model.addAttribute("nuovoCommento",new Commento());
		// RECUPERO L'ID DELL'UTENTE LOGGATO (se c'è)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            Credentials credentials = credentialsService.findByUsername(userDetails.getUsername());
            // Passo l'ID dell'utente al Model
            model.addAttribute("loggedUserId", credentials.getUser().getId());
        }
		return "partita";
	}
	
	@PostMapping("/partita/{partitaId}/commento")
	public String addCommento(@PathVariable("partitaId")Long partitaId,
								@Valid @ModelAttribute("nuovoCommento")Commento commento,
								BindingResult bindingresult,
								Model model) {
		Partita partita=partitaService.partitaConCommenti(partitaId);
		if(bindingresult.hasErrors()) {
			model.addAttribute("partita",partita);
			return "partita";	//Se ci sono errori,ricarica la pagina
		}
		
		//Recupero utente loggato
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=((UserDetails)principal).getUsername();
		Credentials credentials=credentialsService.findByUsername(username);
		
		commento.setAutore(credentials.getUser());
		commento.setPartita(partita);
		//Salviamo tramite il service che ha il @Transactionale
		commentoService.save(commento);
		
		return "redirect:/partita/"+partitaId;
	}
}
