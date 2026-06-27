package it.uniroma3.siw.dto;

public class GiocatoreDTO {
    
    private Long id;
    private String nome;
    private String cognome;
    private String ruolo;
    private Integer altezza;
    private String dataDiNascita;   
    private String squadra;         
    private Long squadraId;         
    
    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    
    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }
    
    public Integer getAltezza() { return altezza; }
    public void setAltezza(Integer altezza) { this.altezza = altezza; }
    
    public String getDataDiNascita() { return dataDiNascita; }
    public void setDataDiNascita(String dataDiNascita) { this.dataDiNascita = dataDiNascita; }
    
    public String getSquadra() { return squadra; }
    public void setSquadra(String squadra) { this.squadra = squadra; }
    
    public Long getSquadraId() { return squadraId; }
    public void setSquadraId(Long squadraId) { this.squadraId = squadraId; }
}