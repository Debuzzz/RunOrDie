package fr.epita.runordie.exposition.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/h2-console", "/h2-console/**").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/utilisateurs").permitAll()
                        .requestMatchers("/api/editions/*/zombies/histogramme").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs").permitAll()

                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/editions").hasRole("ORGANISATEUR")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/api/editions/*").hasRole("ORGANISATEUR")
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/editions/*/annuler").hasRole("ORGANISATEUR")

                        .requestMatchers("/api/coureurs/**").hasRole("UTILISATEUR")
                        .requestMatchers("/api/zombies/**").hasRole("UTILISATEUR")

                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}