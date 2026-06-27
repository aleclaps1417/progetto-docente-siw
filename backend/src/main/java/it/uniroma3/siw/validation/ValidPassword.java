package it.uniroma3.siw.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidPasswordValidator.class) // Dice a Spring quale classe userà per i controlli
@Target({ ElementType.FIELD }) // Indica che questa annotazione si usa sopra i campi (le variabili)
@Retention(RetentionPolicy.RUNTIME)

public @interface ValidPassword {  
	// Il messaggio di default se non ne specifichiamo uno in messages.properties
    String message() default "La password non rispetta i requisiti di sicurezza";
    
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
