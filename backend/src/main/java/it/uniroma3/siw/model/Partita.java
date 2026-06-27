package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;


@Entity
public class Partita {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")//per evitare che Spring vada in confusione
	private LocalDateTime dataEOra;
	private String luogo;
	@Min(0)
	private Integer goalsHome;
	@Min(0)
	private Integer goalsAway;
	@Enumerated(EnumType.STRING)
	private PartitaStatus status;
	
	//GETTER e SETTER
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	public LocalDateTime getDataEOra() {return dataEOra;}
	public void setDataEOra(LocalDateTime dataEOra) {this.dataEOra = dataEOra;}
	public String getLuogo() {return luogo;}
	public void setLuogo(String luogo) {this.luogo = luogo;}
	
	public PartitaStatus getStatus() {return status;}
	public void setStatus(PartitaStatus status) {this.status = status;}
	public Arbitro getArbitro() {return arbitro;}
	public void setArbitro(Arbitro arbitro) {this.arbitro = arbitro;}
	public Torneo getTorneo() {return torneo;}
	public void setTorneo(Torneo torneo) {this.torneo = torneo;}
	public Squadra getSquadraCasa() {return squadraCasa;}
	public void setSquadraCasa(Squadra squadraCasa) {this.squadraCasa = squadraCasa;}
	public Squadra getSquadraTrasferta() {return squadraTrasferta;}
	public void setSquadraTrasferta(Squadra squadraTrasferta) {this.squadraTrasferta = squadraTrasferta;}
	public void setGoalsHome(Integer goalsHome) {this.goalsHome = goalsHome;}
	public void setGoalsAway(Integer goalsAway) {this.goalsAway = goalsAway;}
	public Integer getGoalsHome() {return goalsHome;}
	public Integer getGoalsAway() {return goalsAway;}
	public List<Commento> getCommentiPartita() {return commentiPartita;}
	public void setCommentiPartita(List<Commento> commentiPartita) {this.commentiPartita = commentiPartita;}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partita other = (Partita) obj;
		return Objects.equals(dataEOra, other.dataEOra) && Objects.equals(id, other.id)
				&& Objects.equals(luogo, other.luogo) && Objects.equals(status, other.status);
	}
	public Partita() {}
	public Partita(Long id, LocalDateTime dataEOra, String luogo, Integer goalsHome, 
            Integer goalsAway, PartitaStatus status, Squadra squadraCasa, Squadra squadraTrasferta) {
		 this.id = id;
		 this.dataEOra = dataEOra;
		 this.luogo = luogo;
		 this.goalsHome = goalsHome;
		 this.goalsAway = goalsAway;
		 this.status = status;
		 this.squadraCasa = squadraCasa;
		 this.squadraTrasferta = squadraTrasferta;
}
	
	/*------------------------------------------------------------RELAZIONI--------------------------------------------------------------------------------------------------------------------------*/
	@ManyToOne
	private Arbitro arbitro;
	
	
	@ManyToOne
	private Torneo torneo;

	@ManyToOne
	private Squadra squadraCasa;

	@ManyToOne
	private Squadra squadraTrasferta;

	//Una partita può avere uno o piu commenti
	@OneToMany(mappedBy = "partita", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commento> commentiPartita;
	/*------------------------------------------------------------FINE RELAZIONI--------------------------------------------------------------------------------------------------------------------------*/

	
	
	
}
