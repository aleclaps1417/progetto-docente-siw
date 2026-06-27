package it.uniroma3.siw.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.dto.PartitaDTO;
import it.uniroma3.siw.dto.TorneoDTO;
import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.service.PartitaService;
import it.uniroma3.siw.service.TorneoService;

@RestController 
@RequestMapping("/rest/tornei") // Questo è l'URL che chiamerà React
public class TorneoRestController {
	@Autowired
    private TorneoService torneoService;
	@Autowired
    private PartitaService partitaService;
	@GetMapping
    public List<TorneoDTO> getTornei() {
        List<TorneoDTO> torneiDTO = new ArrayList<>();
        
        for(Torneo t:torneoService.findAllTornei()) {
        	torneiDTO.add(TorneoDTO.fromEntity(t));
        }
        return torneiDTO;
    }
	
	
	  @GetMapping("/{id}/partite")
	    public List<PartitaDTO> getPartiteByTorneo(@PathVariable Long id) {
	        List<PartitaDTO> partiteDTO = new ArrayList<>();
	        
	        List<Partita> partite = partitaService.calendarioPartite(id);
	        
	        for (Partita p : partite) {
	            PartitaDTO dto = new PartitaDTO();
	            dto.setId(p.getId());
	            dto.setDataEOra(p.getDataEOra() != null ? p.getDataEOra().toString() : null);
	            dto.setLuogo(p.getLuogo());
	            dto.setSquadraCasa(p.getSquadraCasa() != null ? p.getSquadraCasa().getNome() : "");
	            dto.setSquadraTrasferta(p.getSquadraTrasferta() != null ? p.getSquadraTrasferta().getNome() : "");
	            dto.setGoalsHome(p.getGoalsHome());
	            dto.setGoalsAway(p.getGoalsAway());
	            dto.setStatus(p.getStatus() != null ? p.getStatus().name() : null);
	            
	            partiteDTO.add(dto);
	        }
	        
	        return partiteDTO;
	    }

}
