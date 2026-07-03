package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import it.uniroma3.siw.service.CommentoService;
import it.uniroma3.siw.service.CredentialsService;
import jakarta.validation.Valid;

@Controller
public class CommentoController {
	
	@Autowired
	private CommentoService commentoService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@GetMapping("/commento/modifica/{id}")
	public String getModificaCommento(@PathVariable("id") Long id, Model model) {
	    // Recuperiamo l'utente loggato PRIMA
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String username = ((UserDetails) principal).getUsername();
	    Credentials credentials = credentialsService.findByUsername(username);
	    
	    // Recuperiamo il commento SOLO se l'utente è l'autore
	    Commento commento = commentoService.findByIdEVerificaAutore(id, credentials.getUser());
	    
	    model.addAttribute("commento", commento);
	    return "modificaCommento";
	}
	
	// 2. Riceve i dati dal form e aggiorna il commento
    @PostMapping("/commento/modifica/{id}")
    public String updateCommento(@PathVariable("id") Long id, 
                                 @Valid @ModelAttribute("commento") Commento commentoDalForm, 
                                 BindingResult bindingResult, 
                                 Model model) {
        
        // Recuperiamo il commento originale per sapere a quale partita appartiene
        Commento commentoOriginale = commentoService.findById(id);

        // Se l'utente ha inserito un testo vuoto o troppo lungo
        if (bindingResult.hasErrors()) {
            return "modificaCommento";
        }

        // Recuperiamo l'utente loggato
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Credentials credentials = credentialsService.findByUsername(username);

        // Chiamiamo il service per aggiornare (qui dentro c'è un ulteriore controllo di sicurezza)
        commentoService.aggiornaCommento(id, commentoDalForm.getTesto(), credentials.getUser());

        // Riportiamo l'utente alla pagina della partita
        return "redirect:/partita/" + commentoOriginale.getPartita().getId();
    }
}
