package it.uniroma3.siw.model.exceptions;

public class DuplicateEmailException extends RuntimeException{
	
	public DuplicateEmailException(String email) {
		super("L'email "+ email+ "e' gia' presente nel sistema");
	}

}
