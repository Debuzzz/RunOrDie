package fr.epita.runordie.utilisateur;

import java.util.UUID;

public interface UtilisateurRepository {
    Utilisateur sauvegarder(Utilisateur utilisateur);
    Utilisateur trouverParEmail(Email email);
}
