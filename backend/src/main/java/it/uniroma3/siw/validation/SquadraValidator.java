package it.uniroma3.siw.validation;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.SquadraRepository;
import org.springframework.validation.Validator;

@Component
public class SquadraValidator implements Validator {
	@Autowired
	private SquadraRepository squadraRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		//Dico a Spring che questo validatore seve solo per la classe Squadra
		return Squadra.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target,Errors errors) {
		Squadra squadra=(Squadra)target;
		
		//Se il nome non è vuota,controllo nel DB se esiste già
		if(squadra.getNome()!=null && !squadra.getNome().trim().isEmpty()) {
			Squadra squadraEsistente=squadraRepository.findByNome(squadra.getNome());
			
			if(squadraEsistente !=null) {
				if(squadra.getId()==null || !squadraEsistente.getId().equals(squadra.getId())) {
					errors.rejectValue("nome", "duplicate","Esiste gia' una squadra con questo nome!");
				}
			}
		}
		
		if(squadra.getAnnoFondazione()!=null) {
			int annoCorrente=Year.now().getValue();//Prende l'anno attuale in automatico
			
			if(squadra.getAnnoFondazione()>annoCorrente) {
				errors.rejectValue("annoFondazione", "Past.squadra.annoFondazione");
			}
			
		}
		
		
	}
}
