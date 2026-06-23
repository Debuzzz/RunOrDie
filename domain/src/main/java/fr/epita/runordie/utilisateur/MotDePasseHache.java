package fr.epita.runordie.utilisateur;

public record MotDePasseHache(String value) {

    public MotDePasseHache {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Le mot de passe haché ne peut pas être vide.");
        }
    }
}
