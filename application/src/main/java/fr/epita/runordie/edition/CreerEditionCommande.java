package fr.epita.runordie.edition;

import java.time.LocalDateTime;

public record CreerEditionCommande(
        String nom,
        LocalDateTime dateDebut,
        LocalDateTime dateFin,
        String lieu,
        int capaciteCoureurs,
        int capaciteZombies
) {
}
