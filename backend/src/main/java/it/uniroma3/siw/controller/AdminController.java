package it.uniroma3.siw.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.repository.ArbitroRepository;
import it.uniroma3.siw.service.GiocatoreService;
import it.uniroma3.siw.service.PartitaService;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.service.TorneoService;
import it.uniroma3.siw.validation.GiocatoreValidator;
import it.uniroma3.siw.validation.PartitaValidator;
import it.uniroma3.siw.validation.SquadraValidator;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin") //Tutte le rotte qui iniziano con /admin
public class AdminController {
	
	
	@Autowired
	private SquadraService squadraService;
	
	@Autowired
	private TorneoService torneoService;
	
	@Autowired
	private GiocatoreService giocatoreService;
	
	@Autowired
	private PartitaService partitaService;
	
	@Autowired
	private SquadraValidator squadraValidator;
	
	@Autowired
	private GiocatoreValidator giocatoreValidator;
	
	@Autowired
	private PartitaValidator partitaValidator;
	@Autowired
	private ArbitroRepository arbitroRepository;
	
	@GetMapping("/dashboard")
	public String getAdminDashboard() {
		return "admin/dashboard";
	}
	
	//Mostra il form vuoto per una nuova squadra
	@GetMapping("/squadra/new")
	public String formNewSquadra(Model model) {
		model.addAttribute("squadra",new Squadra());
		return "admin/formNewSquadra";
	}
	//Mostra il form vuoto per un nuovo torneo
	@GetMapping("/torneo/new")
	public String formNewTorneo(Model model) {
		model.addAttribute("torneo",new Torneo());
		return "admin/formNewTorneo";
	}
	//Mostra il form vuoto per un nuovo giocatore
	@GetMapping("/giocatore/new")
	public String formNewGiocatore(Model model) {
		model.addAttribute("giocatore",new Giocatore());
		//passiamo la lista di tutte le squadre al form
		model.addAttribute("squadre",squadraService.findAllSquadre());
		return "admin/formNewGiocatore";
	}
	//Riceve i dati e salva la squadra
	@PostMapping("/squadra")
	public String newSquadra(@Valid @ModelAttribute("squadra")Squadra squadra,
			BindingResult bindingResult,
			Model model) {
		
		this.squadraValidator.validate(squadra, bindingResult);
		
		if(bindingResult.hasErrors()) {
			return "admin/formNewSquadra";
		}
		
		//Salviamo la squadra nel database tramite il Service
		squadraService.save(squadra);
		
		//Se tutto va bene, facciamo un redirect alla pagina pubblica della squadra appena creata
		return "redirect:/admin/squadre";	
	}
	
	//Riceve i dati e salva il giocatore 
	@PostMapping("/giocatore")
	public String newGiocatore(@Valid @ModelAttribute("giocatore")Giocatore giocatore,
			BindingResult bindingResult,
			Model model) {
		
		this.giocatoreValidator.validate(giocatore, bindingResult);
		if(bindingResult.hasErrors()) {
			model.addAttribute("squadre",squadraService.findAllSquadre());
			return "admin/formNewGiocatore";
		}
		//Salviamo il giocatore nel database tramite il Service
		giocatoreService.save(giocatore);
		
		return "redirect:/admin/giocatori";	
	}
	
	
	// Mostra la lista di tutte le squadre per poterle gestire
    @GetMapping("/squadre")
    public String manageSquadre(Model model) {
        model.addAttribute("squadre", squadraService.findAllSquadre());
        return "admin/gestioneSquadre";
    }
    
    
    //Mostra la lista di tutti i tornei per poterli gestire 
    @GetMapping("/tornei")
    public String manageTornei(Model model) {
    		model.addAttribute("tornei",torneoService.findAllTornei());
    		return "admin/gestioneTornei";
    }
    
    @GetMapping("/giocatori")
    public String manageGiocatori(Model model) {
    		model.addAttribute("giocatori",giocatoreService.findAllGiocatori());
    		return "admin/gestioneGiocatori";
    }
    
    //Mostra il form pre-compilato per la modifica
    @GetMapping("/squadra/edit/{id}")
    public String editSquadraForm(@PathVariable("id")Long id,Model model) {
    	Squadra squadraToEdit=squadraService.findSquadraById(id);
    	
	    	if(squadraToEdit==null) {
	    		return "redirect:/admin/squadre";
	    	}
	    	model.addAttribute("squadra",squadraToEdit);
	    	return "admin/formEditSquadra";
    }
    
    //Riceve i dati modificati e aggiorna il db
    @PostMapping("/squadra/edit/{id}")
    public String updateSquadra(@PathVariable("id")Long id,
    							@Valid @ModelAttribute("squadra")Squadra squadra,
    							BindingResult bindingResult,Model model) {
    		squadraValidator.validate(squadra, bindingResult);
	    	if(bindingResult.hasErrors()) {
	    		return "admin/formEditSquadra";
	    	}
	    	//Impostiamo l'ID sulla squadra per far fare un UPDATE di un record esistente e non un INSERT
	    	squadra.setId(id);
	    	squadraService.save(squadra);
	    	
	    	//Torniamo alla lista per vedere la modifica
	    	return "redirect:/admin/squadre";
    }
    
    @PostMapping("/squadra/delete/{id}")
    public String deleteSquadra(@PathVariable("id")Long id,RedirectAttributes redirectAttributes) {
	    	try {
	    		squadraService.deleteSquadra(id);
	    		//Se va tutto bene , prepariamo un messaggio di successo
	        	redirectAttributes.addFlashAttribute("successMessage","Squadra eliminata con successo !");
	    	}
	    	catch(DataIntegrityViolationException e ) {
	    		//Se il db ci blocca per le chiavi esterne,prepariamo un messaggio d'errore
	    		redirectAttributes.addFlashAttribute("errorMessage","Impossibile eliminare la squadra: "
	    				+ "è attualmente iscritta a un torneo o ha delle partite in calendario.Elimina prima le partite associate");
	    	}
	    	return "redirect:/admin/squadre";
    }
    
    @PostMapping("/torneo")
    public String newTorneo(@Valid @ModelAttribute("torneo")Torneo torneo,
    		BindingResult bindingResult) {
    		if(bindingResult.hasErrors()) {
    			return "admin/formNewTorneo";
    		}
	    	//Salviamo il torneo tramite il service
	    	torneoService.save(torneo);
	    	//Se tutto va bene torniamo alla pagina di gestione dei tornei
	    	return "redirect:/admin/tornei";
    }
    
    
    @GetMapping("/torneo/edit/{id}")
    public String editTorneoForm(@PathVariable("id")Long id,Model model) {
	    	Torneo torneoToEdit=torneoService.findTorneo(id);
	    	
	    	if(torneoToEdit==null) {
	    		return "redirect:/admin/tornei";
	    	}
	    	model.addAttribute("torneo",torneoToEdit);
	    	return "admin/formEditTorneo";
	}
    
    @GetMapping("/giocatore/edit/{id}")
    public String editGiocatoreForm(@PathVariable("id")Long id,Model model) {
	    	Giocatore giocatoreToEdit=giocatoreService.findGiocatoreById(id);
	    	
	    	if(giocatoreToEdit==null) {
	    		return "redirect:/admin/giocatori";
	    	}
	    	model.addAttribute("giocatore",giocatoreToEdit);
	    	model.addAttribute("squadre",squadraService.findAllSquadre());
	    	return "admin/formEditGiocatore";
	}
	    
    
  //Riceve i dati modificati e aggiorna il db
    @PostMapping("/torneo/edit/{id}")
    public String updateTorneo(@PathVariable("id")Long id,
    							@Valid @ModelAttribute("torneo")Torneo torneo,
    							BindingResult bindingResult) {
	    	if(bindingResult.hasErrors()) {
	    		return "admin/formEditTorneo";
	    	}
	    	//Impostiamo l'ID sulla squadra per far fare un UPDATE di un record esistente e non un INSERT
	    	torneo.setId(id);
	    	torneoService.save(torneo);
	    	
	    	//Torniamo alla lista per vedere la modifica
	    	return "redirect:/admin/tornei";
    }
    
  //Riceve i dati modificati e aggiorna il db
    @PostMapping("/giocatore/edit/{id}")
    public String updateGiocatore(@PathVariable("id")Long id,
    							@Valid @ModelAttribute("giocatore")Giocatore giocatore,
    							BindingResult bindingResult,Model model) {
    		this.giocatoreValidator.validate(giocatore, bindingResult);
	    	if(bindingResult.hasErrors()) {
	    		return "admin/formEditGiocatore";
	    	}
	    	//Impostiamo l'ID sulla squadra per far fare un UPDATE di un record esistente e non un INSERT
	    	giocatore.setId(id);
	    	giocatoreService.save(giocatore);
	    	
	    	//Torniamo alla lista per vedere la modifica
	    	return "redirect:/admin/giocatori";
    }
    
    
    
    //Mostra la pagina per aggiungere Nuove squadre al torneo
    @GetMapping("/torneo/{id}/squadre/add")
    public String addSquadreToTorneoForm(@PathVariable("id")Long id,Model model) {
    		Torneo torneo=torneoService.findTorneo(id);
    		if(torneo==null) {
    			return "redirect:/admin/tornei";
    		}
    		//chiediamo al db solo le squadre disponibili
    		Iterable<Squadra>squadreDisponibili=squadraService.findSquadreNonNelTorneo(id);
    		model.addAttribute("torneo",torneo);
    		model.addAttribute("squadreDisponibili",squadreDisponibili);
    		return "admin/addSquadreTorneo";
    }
    
    //Disiscrive una squadra dal torneo
    @PostMapping("/torneo/{t_id}/squadra/{s_id}/remove")
    public String removeSquadraFromTorneo(@PathVariable("t_id")Long torneoId,
    										@PathVariable("s_id")Long squadraId) {
    		Torneo torneo=torneoService.findTorneo(torneoId);
    		Squadra squadra=squadraService.findSquadraById(squadraId);
    		
    		if(torneo!=null && squadra!=null) {
    			//rimuoviamo la squadra dalla lista del torneo
    			torneo.getSquadre().remove(squadra);
    			//salviamo il torneo aggiornato
    			torneoService.save(torneo);
    		}
    		
    		return "redirect:/admin/torneo/edit/"+torneoId;
    }
    
    //Aggiunta di una squadra specifica al torneo
    @PostMapping("/torneo/{t_id}/squadra/{s_id}/add")
    public String addSquadraToTorneo(@PathVariable("t_id")Long torneoId,
    		@PathVariable("s_id")Long SquadraId) {
    		Torneo torneo=torneoService.findTorneo(torneoId);
    		Squadra squadra=squadraService.findSquadraById(SquadraId);
    		
    		if(torneo!=null && squadra!=null) {
    			torneo.getSquadre().add(squadra);
    			torneoService.save(torneo);
    		}
    		return "redirect:/admin/torneo/"+torneoId+"/squadre/add";
    	
    }
    
    //Mostra il form per creare una nuova partita in un torneo specifico
    @GetMapping("/torneo/{t_id}/partita/new")
    public String formNewPartita(@PathVariable("t_id")Long torneoId,Model model) {
    		Torneo torneo=torneoService.findTorneo(torneoId);
    		
    		if(torneo==null) {
    			return "redirect:/admin/tornei";
    		}
    		
    		model.addAttribute("torneo",torneo);
    		model.addAttribute("partita",new Partita());
    		//Passiamo SOLO le squadre iscritte a questo torneo
    		model.addAttribute("squadreTorneo",torneo.getSquadre());
    		
    		//Passimao i possibili stati della partita
    		model.addAttribute("statiPartita",it.uniroma3.siw.model.PartitaStatus.values());
    		model.addAttribute("arbitri",arbitroRepository.findAll());
    		return "admin/formNewPartita";
    }
    
    //Salva la partita associandola al torneo
    @PostMapping("/torneo/{t_id}/partita/new")
    public String newPartita(@PathVariable("t_id")Long torneoId,
    							@Valid @ModelAttribute("partita")Partita partita,
    							BindingResult bindingResult,Model model) {
    		
    		partitaValidator.validate(partita, bindingResult);
    		if(bindingResult.hasErrors()) {
    			Torneo torneo=torneoService.findTorneo(torneoId);
    			model.addAttribute("torneo",torneo);
    			model.addAttribute("squadreTorneo",torneo.getSquadre());
    			model.addAttribute("statiPartita",it.uniroma3.siw.model.PartitaStatus.values());
    			model.addAttribute("arbitri", arbitroRepository.findAll()); 
    			return "admin/formNewPartita";
    		}
    		
    		//Leghiamo la partita al torneo e salviamo
    		Torneo torneo=torneoService.findTorneo(torneoId);
    		partita.setTorneo(torneo);
    		partitaService.save(partita);
    		
    		//Per ora torniamo alla pagina di modifica del torneo
    		return "redirect:/admin/torneo/edit/"+torneoId;
    }
    
    @PostMapping("/partita/delete/{id}")
    public String deletePartita(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        // Recupero la partita prima di eliminarla, altrimenti non saprei più a quale torneo tornare!
        Partita partita = partitaService.findById(id);
        Long idTorneo = partita.getTorneo().getId();
        
        try {
            partitaService.deleteById(id);
            // Se va tutto bene, messaggio verde!
            redirectAttributes.addFlashAttribute("successMessage", "Partita eliminata dal calendario con successo!");
        } catch (DataIntegrityViolationException e) {
            // Se in futuro ci saranno vincoli che bloccano l'eliminazione, messaggio rosso!
            redirectAttributes.addFlashAttribute("errorMessage", "Impossibile eliminare la partita: ci sono dati collegati che lo impediscono.");
        }
        
        // Torno alla pagina del torneo
        return "redirect:/admin/torneo/edit/" + idTorneo;
    }
    
    @GetMapping("/partita/edit/{id}")
    public String formEditPartita(@PathVariable("id")Long id,Model model) {
    	Partita partita=partitaService.findById(id);
    	model.addAttribute("partita",partita);
    	model.addAttribute("statiPartita",it.uniroma3.siw.model.PartitaStatus.values());
    	model.addAttribute("arbitri", arbitroRepository.findAll());
    	return "admin/formEditPartita";
    }
    
    @PostMapping("/partita/edit/{id}")
    public String updateRisultatoPartita(@PathVariable("id") Long id, 
    									 @Valid @ModelAttribute("partita")Partita partitaForm,
    									 BindingResult bindingResult,Model model,
                                         RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
        	//Se c'è un errore(es.gol negativi),ricarichiamo i dati mancanti per mostrare la pagina
        	Partita partitaOriginale=partitaService.findById(id);
        	partitaForm.setSquadraCasa(partitaOriginale.getSquadraCasa());
        	partitaForm.setSquadraTrasferta(partitaOriginale.getSquadraTrasferta());
        	partitaForm.setDataEOra(partitaOriginale.getDataEOra());
        	partitaForm.setTorneo(partitaOriginale.getTorneo());
        	model.addAttribute("statiPartita",it.uniroma3.siw.model.PartitaStatus.values());
        	model.addAttribute("arbitri", arbitroRepository.findAll()); 
        	return "admin/formEditPartita";
        }
        //Se va tutto bene, salviamo
        Partita partitaDaSalvare = partitaService.findById(id);
        partitaDaSalvare.setGoalsHome(partitaForm.getGoalsHome());
        partitaDaSalvare.setGoalsAway(partitaForm.getGoalsAway());
        partitaDaSalvare.setStatus(partitaForm.getStatus());
        partitaDaSalvare.setLuogo(partitaForm.getLuogo());
        partitaDaSalvare.setArbitro(partitaForm.getArbitro()); 
        
        partitaService.save(partitaDaSalvare);
        redirectAttributes.addFlashAttribute("successMessage", "Risultato aggiornato con successo!");
        return "redirect:/admin/torneo/edit/" + partitaDaSalvare.getTorneo().getId();
    }

}
