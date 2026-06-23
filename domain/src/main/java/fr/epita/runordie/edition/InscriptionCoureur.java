package fr.epita.runordie.edition;

import fr.epita.runordie.utilisateur.Utilisateur;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class InscriptionCoureur {
    private final UUID uuid;
    private final Utilisateur utilisateur;

    public InscriptionCoureur(UUID uuid, Utilisateur utilisateur) {
        if (uuid == null || utilisateur == null) {
            throw new IllegalArgumentException("Aucun champ d'une InscriptionCoureur ne peut être nul.");
        }

        this.uuid = uuid;
        this.utilisateur = utilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InscriptionCoureur that)) {
            return false;
        }
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}