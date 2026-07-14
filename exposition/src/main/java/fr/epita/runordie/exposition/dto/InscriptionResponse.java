package fr.epita.runordie.exposition.dto;

import fr.epita.runordie.edition.InscriptionCoureur;

import java.util.UUID;

public record InscriptionResponse(UUID id) {
    public static InscriptionResponse from(InscriptionCoureur inscription) {
        return new InscriptionResponse(inscription.getUuid());
    }
}