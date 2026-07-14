package fr.epita.runordie.exposition.dto;

import fr.epita.runordie.edition.Edition;

import java.time.LocalDateTime;
import java.util.UUID;

public record EditionResponse(
        UUID id,
        String nom,
        LocalDateTime heureDebut,
        LocalDateTime heureFin,
        String lieu,
        int capaciteCoureurs,
        int capaciteZombies,
        int placesCoureursRestantes,
        boolean annulee
) {
    public static EditionResponse from(Edition edition) {
        return new EditionResponse(
                edition.getUuid(),
                edition.getNom(),
                edition.getCreneauHoraire().heureDebut(),
                edition.getCreneauHoraire().heureFin(),
                edition.getLieu(),
                edition.getCapaciteCoureurs(),
                edition.getCapaciteZombies(),
                edition.placesCoureursRestantes(),
                edition.isAnnulee()
        );
    }
}
