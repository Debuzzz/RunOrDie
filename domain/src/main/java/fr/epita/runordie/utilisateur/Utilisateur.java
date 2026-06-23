package fr.epita.runordie.utilisateur;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class Utilisateur {

    private final UUID uuid;
    private final Email email;
    private final MotDePasseHache motDePasse;
    private final Role role;

    public Utilisateur(UUID uuid, Email email, MotDePasseHache motDePasse, Role role) {
        if (uuid == null || email == null || motDePasse == null || role == null) {
            throw new IllegalArgumentException("Aucun champ d'un Utilisateur ne peut être nul.");
        }
        this.uuid = uuid;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public boolean estOrganisateur() {
        return role == Role.ORGANISATEUR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Utilisateur that)) {
            return false;
        }
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
