package it.uniroma3.siw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	    
	    @Value("${analisi.sperimentale.attiva:false}")
	    private boolean analisAttiva;
	    
	    public static void main(String[] args) {
	        SpringApplication.run(ProgettoDocenteApplication.class, args);
	    }
	    @Override
	    @Transactional
	    public void run(String... args) throws Exception {
	    	
	    		if (!analisAttiva) {
	            return;
	    		}
	    	    System.out.println("\n========================================================");
	    	    System.out.println("🚀 TEST 1: STRATEGIA LAZY (DEFAULT)");
	    	    System.out.println("========================================================\n");

	    	    Long idTorneo = 1000L;

	    	    System.out.println("▶ FIND BY ID STANDARD (LAZY)");
	    	    
	    	    long t0 = System.currentTimeMillis();
	    	    System.out.println("==> Inizio findById...");
	    	    
	    	    Torneo torneoLazy = torneoRepository.findById(idTorneo).get();
	    	    
	    	    long t1 = System.currentTimeMillis();
	    	    System.out.println("==> findById finito in " + (t1 - t0) + " ms");
	    	    System.out.println("Nome Torneo: " + torneoLazy.getNome());
	    	    
	    	    System.out.println("==> Inizio accesso a getSquadre()...");
	    	    long t2 = System.currentTimeMillis();
	    	    int numeroSquadre = torneoLazy.getSquadre().size();
	    	    long t3 = System.currentTimeMillis();
	    	    System.out.println("==> getSquadre() finito in " + (t3 - t2) + " ms");
	    	    
	    	    System.out.println("Squadre trovate: " + numeroSquadre);
	    	    System.out.println("⏱ Tempo TOTALE LAZY: " + (t3 - t0) + " ms\n");

	    	    System.out.println("========================================================\n");
	    	
//	    	
//	    	    System.out.println("\n========================================================");
//	    	    System.out.println("🚀 TEST 2: STRATEGIA JOIN FETCH");
//	    	    System.out.println("========================================================\n");
//
//	    	    Long idTorneo = 1000L;
//
//	    	    System.out.println("▶ FIND BY ID CON JOIN FETCH");
//	    	    
//	    	    long t0 = System.currentTimeMillis();
//	    	    System.out.println("==> Inizio findByIdWithSquadre...");
//	    	    
//	    	    Torneo torneoFetch = torneoRepository.findByIdWithSquadre(idTorneo).get();
//	    	    
//	    	    long t1 = System.currentTimeMillis();
//	    	    System.out.println("==> findByIdWithSquadre finito in " + (t1 - t0) + " ms");
//	    	    System.out.println("Nome Torneo: " + torneoFetch.getNome());
//	    	    
//	    	    System.out.println("==> Inizio accesso a getSquadre()...");
//	    	    long t2 = System.currentTimeMillis();
//	    	    int numeroSquadre = torneoFetch.getSquadre().size();
//	    	    long t3 = System.currentTimeMillis();
//	    	    System.out.println("==> getSquadre() finito in " + (t3 - t2) + " ms (dovrebbe essere 0, è già in memoria)");
//	    	    
//	    	    System.out.println("Squadre trovate: " + numeroSquadre);
//	    	    System.out.println("⏱ Tempo TOTALE JOIN FETCH: " + (t3 - t0) + " ms\n");
//
//	    	    System.out.println("========================================================\n");
//	    	
//	    	
//	    	System.out.println("\n========================================================");
//	        System.out.println("🚀 TEST 3: STRATEGIA @ENTITYGRAPH");
//	        System.out.println("========================================================\n");
//
//	        Long idTorneo = 1000L;
//
//	        System.out.println("▶ FIND BY ID CON @ENTITYGRAPH");
//	        
//	        long t0 = System.currentTimeMillis();
//	        System.out.println("==> Inizio findByIdWithEntityGraph...");
//	        
//	        Torneo torneoGraph = torneoRepository.findByIdWithEntityGraph(idTorneo).get();
//	        
//	        long t1 = System.currentTimeMillis();
//	        System.out.println("==> findByIdWithEntityGraph finito in " + (t1 - t0) + " ms");
//	        System.out.println("Nome Torneo: " + torneoGraph.getNome());
//	        
//	        System.out.println("==> Inizio accesso a getSquadre()...");
//	        long t2 = System.currentTimeMillis();
//	        int numeroSquadre = torneoGraph.getSquadre().size();
//	        long t3 = System.currentTimeMillis();
//	        System.out.println("==> getSquadre() finito in " + (t3 - t2) + " ms (dovrebbe essere 0, è già in memoria)");
//	        
//	        System.out.println("Squadre trovate: " + numeroSquadre);
//	        System.out.println("⏱ Tempo TOTALE @ENTITYGRAPH: " + (t3 - t0) + " ms\n");
//
//	        System.out.println("========================================================\n");
	    }
	    
}
