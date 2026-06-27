package it.uniroma3.siw.dto;

public class PartitaDTO {
    
    private Long id;
    private String dataEOra;        
    private String luogo;
    private String squadraCasa;     
    private String squadraTrasferta;
    private Integer goalsHome;     
    private Integer goalsAway;      
    private String status;          
    
    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getDataEOra() { return dataEOra; }
    public void setDataEOra(String dataEOra) { this.dataEOra = dataEOra; }
    
    public String getLuogo() { return luogo; }
    public void setLuogo(String luogo) { this.luogo = luogo; }
    
    public String getSquadraCasa() { return squadraCasa; }
    public void setSquadraCasa(String squadraCasa) { this.squadraCasa = squadraCasa; }
    
    public String getSquadraTrasferta() { return squadraTrasferta; }
    public void setSquadraTrasferta(String squadraTrasferta) { this.squadraTrasferta = squadraTrasferta; }
    
    public Integer getGoalsHome() { return goalsHome; }
    public void setGoalsHome(Integer goalsHome) { this.goalsHome = goalsHome; }
    
    public Integer getGoalsAway() { return goalsAway; }
    public void setGoalsAway(Integer goalsAway) { this.goalsAway = goalsAway; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}