package fr.epita.runordie.exposition.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AffectationZombieRequest(
        UUID editionId,
        LocalDateTime heureDebut,
        LocalDateTime heureFin
) {}