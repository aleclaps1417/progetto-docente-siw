package it.uniroma3.siw.model;

import java.util.List;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

@Entity
public class Squadra {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	private Integer annoFondazione;
	@Column(nullable=false)
	@NotBlank
	private String citta;
	
	//GETTER e SETTER
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}
	public Integer getAnnoFondazione() {return annoFondazione;}
	public void setAnnoFondazione(Integer annoFondazione) {this.annoFondazione = annoFondazione;}
	public String getCitta() {return citta;}
	public void setCitta(String citta) {this.citta = citta;}
	public List<Giocatore> getGiocatori() {return giocatori;}
	public void setGiocatori(List<Giocatore> giocatori) {this.giocatori = giocatori;}
	public List<Torneo> getTornei() {return tornei;}
	public void setTornei(List<Torneo> tornei) {this.tornei = tornei;}
	public List<Partita> getPartiteInCasa() {return partiteInCasa;}
    public void setPartiteInCasa(List<Partita> partiteInCasa) {this.partiteInCasa = partiteInCasa;}
    public List<Partita> getPartiteInTrasferta() {return partiteInTrasferta;}

    public void setPartiteInTrasferta(List<Partita> partiteInTrasferta) {
        this.partiteInTrasferta = partiteInTrasferta;
    }
	
	@Override
	public int hashCode() {
		return Objects.hash(annoFondazione, id, nome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Squadra other = (Squadra) obj;
		return Objects.equals(annoFondazione, other.annoFondazione) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome);
	}
	public Squadra() {}
	public Squadra(Long id, String nome, Integer annoFondazione, String citta) {
		this.id = id;
		this.nome = nome;
		this.annoFondazione = annoFondazione;
		this.citta = citta;
	}
	
	/*------------------------------------------------------------RELAZIONI--------------------------------------------------------------------------------------------------------------------------*/
	@OneToMany(mappedBy="squadra",cascade = CascadeType.ALL,orphanRemoval=true)//orphanRemoval significa che se rimuovi un giocatore dalla lista,hibernate lo cancella automaticamente anche dal database
	private List<Giocatore>giocatori;
	
	
	@ManyToMany(mappedBy="squadre")
	private List<Torneo>tornei;
	
	// Le partite in cui questa squadra gioca in casa
    @OneToMany(mappedBy = "squadraCasa")
    private List<Partita> partiteInCasa;

    // Le partite in cui questa squadra gioca in trasferta
    @OneToMany(mappedBy = "squadraTrasferta")
    private List<Partita> partiteInTrasferta;
	/*------------------------------------------------------------FINE RELAZIONI--------------------------------------------------------------------------------------------------------------------------*/

}
