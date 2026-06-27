package it.uniroma3.siw.dto; // Adatta il pacchetto

import it.uniroma3.siw.model.Torneo;

public class TorneoDTO {
    private Long id;
    private String nome;
    private Integer anno;
    private String descrizione;
    
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Integer getAnno() { return anno; }
    public void setAnno(Integer anno) { this.anno = anno; }
	public String getDescrizione() {return descrizione;}
	public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
    
	public static TorneoDTO fromEntity(Torneo t) {
	    TorneoDTO dto = new TorneoDTO();
	    dto.setId(t.getId());
	    dto.setNome(t.getNome());
	    dto.setAnno(t.getAnno());
	    dto.setDescrizione(t.getDescrizione());
	    return dto;
	}
}