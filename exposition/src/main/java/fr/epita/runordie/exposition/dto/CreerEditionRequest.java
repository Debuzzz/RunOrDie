package fr.epita.runordie.exposition.dto;

import java.time.LocalDateTime;

public record CreerEditionRequest(
        String nom,
        LocalDateTime dateDebut,
        LocalDateTime dateFin,
        String lieu,
        int capaciteCoureurs,
        int capaciteZombies
) {}