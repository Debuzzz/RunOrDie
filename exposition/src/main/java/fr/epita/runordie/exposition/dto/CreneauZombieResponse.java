package fr.epita.runordie.exposition.dto;

import fr.epita.runordie.edition.CreneauZombie;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreneauZombieResponse(
        UUID editionId,
        String editionNom,
        LocalDateTime heureDebut,
        LocalDateTime heureFin
) {
    public static CreneauZombieResponse from(CreneauZombie creneauZombie) {
        return new CreneauZombieResponse(
                creneauZombie.edition().getUuid(),
                creneauZombie.edition().getNom(),
                creneauZombie.creneauHoraire().heureDebut(),
                creneauZombie.creneauHoraire().heureFin()
        );
    }
}
