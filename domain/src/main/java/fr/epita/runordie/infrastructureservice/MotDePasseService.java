package fr.epita.runordie.infrastructureservice;

import fr.epita.runordie.utilisateur.MotDePasseHache;

public interface MotDePasseService {
    MotDePasseHache hacher(String motDePasse);
}
