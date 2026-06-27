package it.uniroma3.siw.model.exceptions;

public class RisorsaNonTrovataException extends RuntimeException {

	public RisorsaNonTrovataException(String tipoRisorsa,Long id) {
		super ("Impossibile trovare "+ tipoRisorsa + ".L'ID "+ id + " non esiste nel database");
	}
}
