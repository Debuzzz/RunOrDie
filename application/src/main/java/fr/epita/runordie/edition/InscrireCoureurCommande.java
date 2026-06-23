package fr.epita.runordie.edition;

import fr.epita.runordie.utilisateur.Email;

import java.util.UUID;

public record InscrireCoureurCommande(
        Email email,
        UUID editionId
) {
}
