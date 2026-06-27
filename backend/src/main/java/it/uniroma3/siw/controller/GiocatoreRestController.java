package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.dto.GiocatoreDTO;
import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.service.GiocatoreService;

@RestController
@RequestMapping("/rest/giocatori")
public class GiocatoreRestController {

    @Autowired
    private GiocatoreService giocatoreService;
    
    @GetMapping
    public List<GiocatoreDTO> getGiocatori() {
        List<GiocatoreDTO> giocatoriDTO = new ArrayList<>();
        
        Iterable<Giocatore> giocatori = giocatoreService.findAllGiocatori();
        
        for (Giocatore g : giocatori) {
            GiocatoreDTO dto = new GiocatoreDTO();
            dto.setId(g.getId());
            dto.setNome(g.getNome());
            dto.setCognome(g.getCognome());
            dto.setRuolo(g.getRuolo());
            dto.setAltezza(g.getAltezza());
            dto.setDataDiNascita(g.getDataDiNascita() != null ? g.getDataDiNascita().toString() : null);
            
            // Gestione della squadra (potrebbe essere null)
            if (g.getSquadra() != null) {
                dto.setSquadra(g.getSquadra().getNome());
                dto.setSquadraId(g.getSquadra().getId());
            }
            
            giocatoriDTO.add(dto);
        }
        
        return giocatoriDTO;
    }
}