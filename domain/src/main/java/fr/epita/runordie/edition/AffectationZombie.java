package fr.epita.runordie.edition;

import fr.epita.runordie.utilisateur.Utilisateur;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class AffectationZombie {
    private final UUID uuid;
    private final Utilisateur utilisateur;
    private final CreneauHoraire creneauHoraire;

    public AffectationZombie(UUID uuid, Utilisateur utilisateur, CreneauHoraire creneauHoraire) {
        if (uuid == null || utilisateur == null || creneauHoraire == null) {
            throw new IllegalArgumentException("Aucun champ d'une AffectationZombie ne peut être nul.");
        }

        if (creneauHoraire.getDuree().toHours() < 1) {
            throw new IllegalArgumentException("Un zombie s'affecte sur des plages de 1h minimum.");
        }

        this.uuid = uuid;
        this.utilisateur = utilisateur;
        this.creneauHoraire = creneauHoraire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AffectationZombie that)) {
            return false;
        }
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}