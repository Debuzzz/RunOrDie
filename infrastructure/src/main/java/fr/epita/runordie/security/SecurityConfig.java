package fr.epita.runordie.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
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
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/h2-console", "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/utilisateurs").permitAll()
                        .requestMatchers("/api/editions/*/zombies/histogramme").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/editions").hasRole("ORGANISATEUR")
                        .requestMatchers(HttpMethod.GET, "/api/editions").hasRole("ORGANISATEUR")
                        .requestMatchers(HttpMethod.DELETE, "/api/editions/*").hasRole("ORGANISATEUR")
                        .requestMatchers(HttpMethod.POST, "/api/editions/*/annuler").hasRole("ORGANISATEUR")

                        .requestMatchers("/api/coureurs/**").hasRole("UTILISATEUR")
                        .requestMatchers("/api/zombies/**").hasRole("UTILISATEUR")

                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
