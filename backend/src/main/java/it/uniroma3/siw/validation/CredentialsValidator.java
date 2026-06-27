package it.uniroma3.siw.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import it.uniroma3.siw.model.Credentials;
import org.springframework.validation.Validator;
@Component
public class CredentialsValidator implements Validator{
	
	@Override
	public boolean supports(Class<?>clazz) {
		return Credentials.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target,Errors errors) {
		Credentials credentials=(Credentials)target;
		
		if(credentials.getPassword()!=null && credentials.getConfirmPassword()!=null
				&& !credentials.getPassword().equals(credentials.getConfirmPassword())){
			//Registriamo l'errore sul campo 'confirmPassword'
			errors.rejectValue("confirmPassword", "password.mismatch");
		}
	}

}
