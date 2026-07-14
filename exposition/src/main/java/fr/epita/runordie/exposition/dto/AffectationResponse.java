package fr.epita.runordie.exposition.dto;

import fr.epita.runordie.edition.AffectationZombie;

import java.time.LocalDateTime;
import java.util.UUID;

public record AffectationResponse(UUID id, LocalDateTime heureDebut, LocalDateTime heureFin) {
    public static AffectationResponse from(AffectationZombie affectation) {
        return new AffectationResponse(
                affectation.getUuid(),
                affectation.getCreneauHoraire().heureDebut(),
                affectation.getCreneauHoraire().heureFin()
        );
    }
}