package it.uniroma3.siw.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword,String>{
	public boolean isValid(String value,ConstraintValidatorContext context) {
		// Se la password è vuota o nulla, lasciamo che sia @NotBlank a mostrare l'errore
		if(value==null ||value.trim().isEmpty()){
			return true;
		}
		
		if(value.length()<8) {
			return false;
		}
		// L'espressione regolare [^a-zA-Z0-9] significa "qualsiasi cosa che NON sia una lettera o un numero"
		if(!value.matches(".*[^a-zA-Z0-9].*")) {
			return false;
		}
		
		return true;
		
		
		
	}

}
