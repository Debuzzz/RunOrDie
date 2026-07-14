package fr.epita.runordie.security;

import fr.epita.runordie.utilisateur.Email;
import fr.epita.runordie.utilisateur.Utilisateur;
import fr.epita.runordie.utilisateur.UtilisateurRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@NullMarked
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    public CustomUserDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Email email = new Email(username);
        Utilisateur utilisateur = this.utilisateurRepository.trouverParEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Aucun utilisateur trouvé avec l'email : " + username));

        return User.builder()
                .username(utilisateur.getEmail().email())
                .password(utilisateur.getMotDePasse().value())
                .authorities(new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().name()))
                .build();
    }
}
