package it.uniroma3.siw.authentication;

import static it.uniroma3.siw.model.Credentials.ADMIN_ROLE;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Autowired
    private DataSource dataSource;

    // 1. COME RECUPERARE GLI UTENTI DAL DATABASE
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
            .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }

    // 2. COME CIFRARE LE PASSWORD 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3. IL VERO E PROPRIO "BUTTAFUORI" (Regole di accesso)
 // 3. IL VERO E PROPRIO "BUTTAFUORI" (Regole di accesso aggiornate per Spring Security 6+)
    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        
            // Regole di autorizzazione
            .authorizeHttpRequests(auth -> auth
                // --- ROTTE PUBBLICHE ---
            	.requestMatchers("/rest/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/", "/index", "/login", "/register", "/css/**", "/images/**", "/favicon.ico", 
                                 "/tornei", "/torneo/**", "/squadra/**","/error","/v3/api-docs","/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                
                // Chiunque può inviare i propri dati per registrarsi o loggarsi
                .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
                
                // --- ROTTE PROTETTE (AREA ADMIN) ---
                .requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
                
                // Tutte le altre richieste
                .anyRequest().authenticated()
            )
            
            // Configurazione Login
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/success", true)
                .failureUrl("/login?error=true")
            )
            
            // Configurazione Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .permitAll()
            );

        return httpSecurity.build();
    }
}