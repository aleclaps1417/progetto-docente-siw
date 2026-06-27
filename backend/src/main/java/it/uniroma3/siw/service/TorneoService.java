package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.dto.RigaClassifica;
import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.PartitaStatus;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.model.exceptions.RisorsaNonTrovataException;
import it.uniroma3.siw.repository.PartitaRepository;
import it.uniroma3.siw.repository.TorneoRepository;


@Service
public class TorneoService {
	@Autowired
	TorneoRepository torneoRepository;
	@Autowired
	PartitaRepository partitaRepository;
	
	//CASO D'USO:Visualizzazione dell'elenco dei tornei
	@Transactional(readOnly = true)
	public List<Torneo> findAllTornei(){
		return torneoRepository.findAllByOrderByAnno();
	}
	//CASO D'USO:Visualizzazione del dettaglio di un torneo
	@Transactional(readOnly=true)
	public Torneo findTorneo(Long id) {
		return torneoRepository.findById(id).orElseThrow(() -> new RisorsaNonTrovataException("il Torneo", id));
	}
	//CASO D'USO:Visualizzazione delle squadre partecipanti al torneo
	@Transactional(readOnly=true)
	public Torneo findTorneoConSquadre(Long id) {
		return torneoRepository.findByIdWithSquadre(id).orElseThrow(() -> new RisorsaNonTrovataException("il Torneo", id));
	}
	//CASO D'USO:Visualizzazione della classifica del torneo
	@Transactional(readOnly=true)
	public List<RigaClassifica>calcolaClassifica(Long torneoId){
		Torneo torneo=torneoRepository.findByIdWithSquadre(torneoId).orElse(null);
		if(torneo==null) {
			return new ArrayList<>();
		}
		List<Partita> partiteGiocate=partitaRepository.findPartiteGiocateByTorneoId(torneoId,PartitaStatus.PLAYED);
		System.out.println("Partite giocate trovate dal DB: " + partiteGiocate.size());
		//Preparo la mappa: per ogni ID squadra, associo una riga a 0 punti
        Map<Long, RigaClassifica> mappaClassifica = new HashMap<>();
        for(Squadra squadra:torneo.getSquadre()) {
        	RigaClassifica riga=new RigaClassifica(squadra);
        	riga.setSquadra(squadra);
        	mappaClassifica.put(squadra.getId(), riga);
        }
        for (Partita p : partiteGiocate) {
            RigaClassifica rigaCasa = mappaClassifica.get(p.getSquadraCasa().getId());
            RigaClassifica rigaTrasferta = mappaClassifica.get(p.getSquadraTrasferta().getId());
            if(rigaCasa==null || rigaTrasferta==null) {
            	System.out.println("Partita ID"+p.getId()+" ingorata perchè una delle squadre è stata rimossa");
            	continue;
            }
            if (p.getGoalsHome() > p.getGoalsAway()) {
                rigaCasa.aggiungiVittoria();
                rigaTrasferta.aggiungiSconfitta();
            } else if (p.getGoalsHome() < p.getGoalsAway()) {
                rigaTrasferta.aggiungiVittoria();
                rigaCasa.aggiungiSconfitta();
            } else {
                rigaCasa.aggiungiPareggio();
                rigaTrasferta.aggiungiPareggio();
            }
        }
        List<RigaClassifica> classificaFinale = new ArrayList<>(mappaClassifica.values());
        Collections.sort(classificaFinale);

        return classificaFinale;
	}
	@Transactional
	public Torneo save(Torneo torneo) {
		return this.torneoRepository.save(torneo);
	}

}
