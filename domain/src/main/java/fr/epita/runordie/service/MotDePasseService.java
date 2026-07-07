package fr.epita.runordie.service;

import fr.epita.runordie.utilisateur.MotDePasseHache;

public interface MotDePasseService {
    MotDePasseHache hacher(String motDePasse);
}
