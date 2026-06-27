package it.uniroma3.siw.model;

import java.util.Objects;

import it.uniroma3.siw.validation.ValidPassword;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Credentials {
	public static final String DEFAULT_ROLE="DEFAULT";
	public static final String ADMIN_ROLE="ADMIN";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false,unique=true)
	@NotBlank
	@Size(min = 4, max = 20)
	@Pattern(regexp = "^[a-zA-Z0-9_]+$")
	private String username;
	
	@Column(nullable = false)
	@NotBlank
	@ValidPassword
    private String password;

    @Column(nullable = false)
    private String role;
    
    @Transient//Non diventerà una colonna nel db
    private String confirmPassword;

	@OneToOne(cascade = CascadeType.ALL)
    private User user;
    
    
    public Credentials() {}

    public Credentials(Long id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
 // --- GETTER E SETTER ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getConfirmPassword() {return confirmPassword;}
	public void setConfirmPassword(String confirmPassword) {this.confirmPassword = confirmPassword;}
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    // --- EQUALS & HASHCODE ---
    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Credentials other = (Credentials) obj; // Aggiornato al nuovo nome
        return Objects.equals(id, other.id) && Objects.equals(username, other.username);
    }
}
