package it.uniroma3.siw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.CommandLineRunner;
	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.transaction.annotation.Transactional;

	import it.uniroma3.siw.model.Torneo;
	import it.uniroma3.siw.repository.TorneoRepository;

	@SpringBootApplication
	public class ProgettoDocenteApplication implements CommandLineRunner {

	    @Autowired
	    private TorneoRepository torneoRepository;

	    public static void main(String[] args) {
	        SpringApplication.run(ProgettoDocenteApplication.class, args);
	    }

	    @Override
	    @Transactional
	    public void run(String... args) throws Exception {
	        System.out.println("\n\n========================================================");
	        System.out.println("🚀 INIZIO ANALISI SPERIMENTALE: STRATEGIE DI FETCH");
	        System.out.println("========================================================\n");

	        Long idTorneo = 1000L;

	        // ---------------------------------------------------------
	        // TEST 1: STRATEGIA STANDARD (LAZY) - Problema N+1
	        // ---------------------------------------------------------
	        System.out.println("▶ TEST 1: FIND BY ID STANDARD (LAZY)");
	        long startTimeLazy = System.currentTimeMillis();
	        
	        // Prima query: carica il torneo
	        Torneo torneoLazy = torneoRepository.findById(idTorneo).get();
	        System.out.println("Nome Torneo: " + torneoLazy.getNome());
	        
	        // Qui scattano le N query extra per le squadre!
	        int numeroSquadreLazy = torneoLazy.getSquadre().size(); 
	        
	        long endTimeLazy = System.currentTimeMillis();
	        System.out.println("Squadre trovate: " + numeroSquadreLazy);
	        System.out.println("⏱ Tempo Test LAZY: " + (endTimeLazy - startTimeLazy) + " ms\n");


	        // ---------------------------------------------------------
	        // TEST 2: STRATEGIA OTTIMIZZATA (JOIN FETCH)
	        // ---------------------------------------------------------
	        System.out.println("▶ TEST 2: FIND BY ID CON JOIN FETCH (EAGER LOCALE)");
	        long startTimeFetch = System.currentTimeMillis();
	        
	        // Unica query larga: carica torneo e squadre insieme
	        Torneo torneoFetch = torneoRepository.findByIdWithSquadre(idTorneo).get();
	        System.out.println("Nome Torneo: " + torneoFetch.getNome());
	        
	        // I dati sono già in RAM, P6Spy non stamperà nulla qui!
	        int numeroSquadreFetch = torneoFetch.getSquadre().size();
	        
	        long endTimeFetch = System.currentTimeMillis();
	        System.out.println("Squadre trovate: " + numeroSquadreFetch);
	        System.out.println("⏱ Tempo Test JOIN FETCH: " + (endTimeFetch - startTimeFetch) + " ms\n");

	        System.out.println("========================================================\n\n");
	    }
	
	    
}
