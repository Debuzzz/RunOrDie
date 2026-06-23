package fr.epita.runordie.utilisateur;

public record CreerUtilisateurCommande(
        String email,
        String motDePasseEnClair
) {
}