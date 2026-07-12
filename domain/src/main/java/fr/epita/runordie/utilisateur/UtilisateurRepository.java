package fr.epita.runordie.utilisateur;

import java.util.Optional;

public interface UtilisateurRepository {
    void sauvegarder(Utilisateur utilisateur);
    Optional<Utilisateur> trouverParEmail(Email email);
}
