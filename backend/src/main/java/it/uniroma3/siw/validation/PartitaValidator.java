package it.uniroma3.siw.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.PartitaRepository;
import org.springframework.validation.Validator;

@Component
public class PartitaValidator implements Validator{
	@Autowired
	private PartitaRepository partitaRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		//Dico a Spring che questo validatore seve solo per la classe Squadra
		return Squadra.class.equals(clazz);
	}
	@Override
	public void validate(Object target,Errors errors) {
		Partita partita=(Partita)target;
		
		//Controllo che le squadre siano state selezionate e non siano nulle
		if(partita.getSquadraCasa()!=null && partita.getSquadraTrasferta()!=null ) {
			//Se l'Id della squadra di casa è uguale a quello della squadra in trasferta
			if(partita.getSquadraCasa().getId().equals(partita.getSquadraTrasferta().getId())) {
				//Blocco tutto e attacco l'errore al campo "squadraTrasferta"
				errors.rejectValue("squadraTrasferta", "Duplicate.partita.squadre");
			}
				
		}
	}
	

}
