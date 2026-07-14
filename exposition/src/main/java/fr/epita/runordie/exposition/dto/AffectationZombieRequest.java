package fr.epita.runordie.exposition.dto;

import java.time.LocalDateTime;

public record AffectationZombieRequest(
        LocalDateTime heureDebut,
        LocalDateTime heureFin
) {}