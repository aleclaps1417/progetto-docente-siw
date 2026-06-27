package it.uniroma3.siw.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.repository.GiocatoreRepository;

@Component
public class GiocatoreValidator implements Validator{
	@Autowired
	private GiocatoreRepository giocatoreRepository;
	@Override
	public boolean supports(Class<?> clazz) {
		//Dico a Spring che questo validatore serve solo per la classe Giocatore
		return Giocatore.class.equals(clazz);
	}
	@Override
	public void validate(Object target, Errors errors) {
		Giocatore giocatore = (Giocatore) target;
	// 1. Prima di tutto, mi assicuro che i campi non siano nulli o vuoti
			if(giocatore.getNome() != null && !giocatore.getNome().trim().isEmpty() && 
			   giocatore.getCognome() != null && !giocatore.getCognome().trim().isEmpty() && 
			   giocatore.getDataDiNascita() != null) {
				
				// 2. Peschiamo il giocatore dal DB usando i tre campi 
				Giocatore giocatoreEsistente = giocatoreRepository.findByNomeAndCognomeAndDataDiNascita(
				        giocatore.getNome(), 
				        giocatore.getCognome(),
				        giocatore.getDataDiNascita());
				
				// 3. Se abbiamo trovato un giocatore con questi esatti dati...
				if (giocatoreEsistente != null) {
				    // ...lancio l'errore SOLO se sto creando un giocatore nuovo (id == null) 
				    // OPPURE se sto modificando un giocatore ma sto copiando i dati di un ALTRO giocatore (id diversi)
				    if (giocatore.getId() == null || !giocatoreEsistente.getId().equals(giocatore.getId())) {
				        errors.rejectValue("nome", "duplicate", "Esiste già un giocatore con questi dati!");
				    }
				}
			}
	}
}
