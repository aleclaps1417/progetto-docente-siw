package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Entity
public class Torneo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	@NotBlank
	private String nome;
	@Column(nullable = false)
	@NotNull
	@Min(value = 1900, message = "L'anno non può essere precedente al 1900")
	@Max(value = 2100, message = "L'anno non può essere superiore al 2100")
	private Integer anno;
	private String descrizione;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getAnno() {
		return anno;
	}
	public void setAnno(Integer anno) {
		this.anno = anno;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public List<Squadra> getSquadre() {
		return squadre;
	}
	public void setSquadre(List<Squadra> squadre) {
		this.squadre = squadre;
	}
	public List<Partita> getPartite() { return partite; }
    public void setPartite(List<Partita> partite) { this.partite = partite; }

	@Override
	public int hashCode() {
		return Objects.hash(anno, id, nome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Torneo other = (Torneo) obj;
		return Objects.equals(anno, other.anno) && Objects.equals(id, other.id) && Objects.equals(nome, other.nome);
	}
	public Torneo() {}
	public Torneo(Long id, String nome, Integer anno, String descrizione, List<Squadra> squadre) {
		super();
		this.id = id;
		this.nome = nome;
		this.anno = anno;
		this.descrizione = descrizione;
		this.squadre = squadre;
	}
	
	
	
	
	/*------------------------------------------------------------RELAZIONI-----------------------------------------------------------------------------------------------------------------------*/
	
	@OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Partita> partite;

   
    

	
	@ManyToMany
	private List<Squadra>squadre;
	/*------------------------------------------------------------FINE RELAZIONI----------------------------------------------------------------------------------------------*/
	

}
