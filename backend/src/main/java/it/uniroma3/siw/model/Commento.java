package it.uniroma3.siw.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity
public class Commento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(length=500)
	@Size(max=500)
	private String testo;
	
	private LocalDateTime dataCreazione;
	
	//-------------------------RELAZIONI---------------------------
	//Molti commenti possono essere scritti da un singolo Utente 
	@ManyToOne
	private User autore;
	
	//Molti commenti possono essere associati a una singola Partita
	@ManyToOne
	private Partita partita;
	//-------------------------RELAZIONI---------------------------
	
	public Commento() {
		this.dataCreazione=LocalDateTime.now();
	}
	
	public Commento(String testo,User autore,Partita partita) {
		this();
		this.testo=testo;
		this.autore=autore;
		this.partita=partita;
	}

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	public String getTesto() {return testo;}
	public void setTesto(String testo) {this.testo = testo;}
	public LocalDateTime getDataCreazione() {return dataCreazione;}
	public void setDataCreazione(LocalDateTime dataCreazione) {this.dataCreazione = dataCreazione;}
	public User getAutore() {return autore;}
	public void setAutore(User autore) {this.autore = autore;}
	public Partita getPartita() {return partita;}
	public void setPartita(Partita partita) {this.partita = partita;}
	
	
}
