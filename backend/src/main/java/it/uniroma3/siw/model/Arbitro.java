package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Arbitro {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable=false)
	private String nome;
	@Column(nullable=false)
	private String cognome;
	@Column(unique=true,nullable=false)
	private String codiceArbitrale;
	//GETTER e SETTER
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}
	public String getCognome() {return cognome;}
	public void setCognome(String cognome) {this.cognome = cognome;}
	public List<Partita> getPartite() {return partite;}
	public void setPartite(List<Partita> partite) {this.partite = partite;}
	public String getCodiceArbitrale() {return codiceArbitrale;}
	public void setCodiceArbitrale(String codiceArbitrale) {this.codiceArbitrale = codiceArbitrale;}
	

	@Override
	public int hashCode() {
		return Objects.hash(cognome, id, nome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arbitro other = (Arbitro) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome);
	}
	public Arbitro() {}
	public Arbitro(Long id, String nome, String cognome) {
		
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
	}
	
	/*------------------------------------------------------------RELAZIONI--------------------------------------------------------------------------------------------------------------------------*/
	@OneToMany(mappedBy="arbitro")
	private List<Partita> partite;
	
	
	

/*----------------------------------------------------------FINE RELAZIONI--------------------------------------------------------------------------------------------------------------------------*/
}