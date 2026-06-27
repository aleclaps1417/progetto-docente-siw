package it.uniroma3.siw.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


import it.uniroma3.siw.model.exceptions.RisorsaNonTrovataException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleUnexpectedException(Exception e, Model model) {
        
        model.addAttribute("errorMessage", "Si è verificato un errore interno ai nostri server. Riprova più tardi.");
        
        
        return "error/500"; 
    }
	
	@ExceptionHandler(RisorsaNonTrovataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // Restituiamo il codice 404 corretto
    public String handleGiocatoreNotFound(RisorsaNonTrovataException e, Model model) {
        
        // Passiamo alla vista il messaggio che abbiamo scritto nella classe dell'eccezione
        model.addAttribute("errorMessage", e.getMessage());
        
        // Reindirizziamo alla pagina 404 che hai creato in templates/error/
        return "error/404"; 
    }
    
	
}
