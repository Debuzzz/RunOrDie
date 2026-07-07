package fr.epita.runordie.persistence.service;

import fr.epita.runordie.service.MotDePasseService;
import fr.epita.runordie.utilisateur.MotDePasseHache;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BCryptMotDePasseService implements MotDePasseService {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public MotDePasseHache hacher(String motDePasse) {
        return new MotDePasseHache(encoder.encode(motDePasse));
    }
}
