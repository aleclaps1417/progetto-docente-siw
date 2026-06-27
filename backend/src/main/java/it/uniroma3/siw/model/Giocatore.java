package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

@Entity
public class Giocatore {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(nullable=false)
	private String nome;
	@NotBlank
	@Column(nullable=false)
	private String cognome;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past
	private LocalDate dataDiNascita;
	private String ruolo;
	private Integer altezza;
	public Long getId() {
		return id;
	}
	
	//Getter e Setter
	public void setId(Long id) {this.id = id;}
	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}
	public String getCognome() {return cognome;}
	public void setCognome(String cognome) {this.cognome = cognome;}
	public LocalDate getDataDiNascita() {return dataDiNascita;}
	public void setDataDiNascita(LocalDate dataDiNascita) {this.dataDiNascita = dataDiNascita;}
	public String getRuolo() {return ruolo;}
	public void setRuolo(String ruolo) {this.ruolo = ruolo;}
	public Integer getAltezza() {return altezza;}
	public void setAltezza(Integer altezza) {this.altezza = altezza;}
	public Squadra getSquadra() {return squadra;}
	public void setSquadra(Squadra squadra) {this.squadra = squadra;}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(cognome, dataDiNascita, id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giocatore other = (Giocatore) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(dataDiNascita, other.dataDiNascita)
				&& Objects.equals(id, other.id);
	}
	public Giocatore() {}
	
	public Giocatore(Long id, String nome, String cognome, LocalDate dataDiNascita, String ruolo, Integer altezza) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.dataDiNascita = dataDiNascita;
		this.ruolo = ruolo;
		this.altezza = altezza;
	}
	

	

/*------------------------------------------------------------RELAZIONI--------------------------------------------------------------------------------------------------------------------------*/
@ManyToOne
private Squadra squadra;
	
/*------------------------------------------------------------RELAZIONI--------------------------------------------------------------------------------------------------------------------------*/
}
