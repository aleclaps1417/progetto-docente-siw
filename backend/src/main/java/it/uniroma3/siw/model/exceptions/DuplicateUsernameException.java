package it.uniroma3.siw.model.exceptions;

public class DuplicateUsernameException extends RuntimeException {
	
	public DuplicateUsernameException(String username) {
		super("L'username' "+ username+ " è già presente nel sistema");
	}

}
