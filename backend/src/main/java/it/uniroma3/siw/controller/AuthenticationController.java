package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.exceptions.DuplicateEmailException;
import it.uniroma3.siw.model.exceptions.DuplicateUsernameException;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validation.CredentialsValidator;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	@Autowired
	CredentialsService credentialsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CredentialsValidator credentialsValidator;
	
	//---REGISTRAZIONE---
	@GetMapping("/register")
	public String showRegistrerForm(Model model) {
		model.addAttribute("user",new User());
		model.addAttribute("credentials",new Credentials());
		return "formRegisterUser";
	}
	
	@PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
                           BindingResult userBindingResult,
                           @Valid @ModelAttribute("credentials") Credentials credentials,
                           BindingResult credentialsBindingResult,
                           Model model) {
		
		//Chiamiamo il nostro validatore custom per confrontare le password
		this.credentialsValidator.validate(credentials, credentialsBindingResult);

        // Se non ci sono errori di validazione, salva tutto
        if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
        	try {
        		 userService.saveUser(user);
                 credentials.setUser(user); // Collega le credenziali all'anagrafica
                 credentialsService.saveCredentials(credentials); // Cifra la password e salva
                 return "registrationSuccessful"; // Rimanda a una pagina di successo
        	}catch(DuplicateUsernameException e){
        		credentialsBindingResult.rejectValue("username", "duplicate.username");
        		return  "formRegisterUser"; 
        	}catch(DuplicateEmailException e) {
        		userBindingResult.rejectValue("email","duplicate.email");
        		return "formRegisterUser";
        	}
           
        }
        return  "formRegisterUser"; // Se ci sono errori, ricarica il form
        
    }
	
	// --- LOGIN ---
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "formLogin";
    }

    // --- DOPO IL LOGIN ---
    // Nella AuthConfiguration abbiamo detto a Spring di mandare gli utenti a "/success" dopo il login
    @GetMapping("/success")
    public String defaultAfterLogin(Model model) {
        return "redirect:/"; // Dopo il login, rimandiamo tutti alla Home Page!
    }
}
