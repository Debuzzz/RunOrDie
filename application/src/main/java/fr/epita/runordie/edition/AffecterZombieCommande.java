package fr.epita.runordie.edition;

import fr.epita.runordie.utilisateur.Email;

import java.time.LocalDateTime;
import java.util.UUID;

public record AffecterZombieCommande(
        Email email,
        UUID editionId,
        LocalDateTime heureDebut,
        LocalDateTime heureFin
) {
}