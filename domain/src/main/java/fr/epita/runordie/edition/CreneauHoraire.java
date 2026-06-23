package fr.epita.runordie.edition;

import java.time.Duration;
import java.time.LocalDateTime;

public record CreneauHoraire(LocalDateTime heureDebut, LocalDateTime heureFin) {
    public CreneauHoraire {
        if (heureDebut == null || heureFin == null) {
            throw new IllegalArgumentException("Les dates ne peuvent pas être nulles");
        }

        if (!heureDebut.isBefore(heureFin)) {
            throw new IllegalArgumentException("L'heure de fin doit être après l'heure de début.");
        }

    }

    public Duration getDuree() {
        return Duration.between(heureDebut, heureFin);
    }

    public boolean chevauche(CreneauHoraire autre) {
        return this.heureDebut.isBefore(autre.heureFin) && autre.heureDebut.isBefore(this.heureFin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreneauHoraire that)) {
            return false;
        }
        return heureDebut.equals(that.heureDebut) && heureFin.equals(that.heureFin);
    }

}
