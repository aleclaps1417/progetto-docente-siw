package it.uniroma3.siw.dto;

import it.uniroma3.siw.model.Squadra;

public class RigaClassifica implements Comparable<RigaClassifica>{
	private Squadra squadra;
    private int punti;
    private int partiteGiocate;

    public RigaClassifica(Squadra squadra) {
        this.squadra = squadra;
        this.punti = 0;
        this.partiteGiocate = 0;
    }
    
    public void aggiungiVittoria() { this.punti += 3; this.partiteGiocate++; }
    public void aggiungiPareggio() { this.punti += 1; this.partiteGiocate++; }
    public void aggiungiSconfitta() { this.partiteGiocate++; }

    

    public Squadra getSquadra() {
		return squadra;
	}

	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
	}

	public int getPunti() {
		return punti;
	}

	public void setPunti(int punti) {
		this.punti = punti;
	}

	public int getPartiteGiocate() {
		return partiteGiocate;
	}

	public void setPartiteGiocate(int partiteGiocate) {
		this.partiteGiocate = partiteGiocate;
	}

	// Serve per riordinare la classifica dal punteggio più alto al più basso
    @Override
    public int compareTo(RigaClassifica altra) {
        return Integer.compare(altra.getPunti(), this.punti); 
    }
}
