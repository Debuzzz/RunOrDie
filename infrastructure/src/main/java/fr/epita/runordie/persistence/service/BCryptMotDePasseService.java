package fr.epita.runordie.persistence.service;

import fr.epita.runordie.service.MotDePasseService;
import fr.epita.runordie.utilisateur.MotDePasseHache;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BCryptMotDePasseService implements MotDePasseService {

    private final PasswordEncoder encoder;

    public BCryptMotDePasseService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public MotDePasseHache hacher(String motDePasse) {
        return new MotDePasseHache(encoder.encode(motDePasse));
    }
}
