package it.epicode.pugnatorisClub.security;


import it.epicode.pugnatorisClub.enums.Ruolo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityChain {

    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);


        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/auth/**").permitAll());

        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/utente").hasAuthority(Ruolo.ADMIN.name()));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/utente/delete/**").hasAuthority(Ruolo.ADMIN.name()));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/utente/**").permitAll());
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET,"/insegnante/**").permitAll());
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/insegnante/**").hasAuthority(Ruolo.ADMIN.name()));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/turno/**").permitAll());
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET,"/corso/**").permitAll());
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/corso/**").hasAuthority(Ruolo.ADMIN.name()));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/prenotazione/**").permitAll());
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET,"/abbonamento/**").permitAll());
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/abbonamento/**").hasAuthority(Ruolo.ADMIN.name()));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/**").denyAll());







        return httpSecurity.build();
    }
}
