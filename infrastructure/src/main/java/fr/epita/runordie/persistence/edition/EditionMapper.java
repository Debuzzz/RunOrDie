package fr.epita.runordie.persistence.edition;

import fr.epita.runordie.edition.*;
import fr.epita.runordie.persistence.utilisateur.UtilisateurMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class EditionMapper {

    private final UtilisateurMapper utilisateurMapper;

    public EditionMapper(UtilisateurMapper utilisateurMapper) {
        this.utilisateurMapper = utilisateurMapper;
    }

    public Edition toDomain(EditionEntity entity) {
        CreneauHoraire creneau = new CreneauHoraire(entity.getHeureDebut(), entity.getHeureFin());

        List<InscriptionCoureur> inscriptions = entity.getInscriptionsCoureurs().stream()
                .map(this::toDomain)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

        List<AffectationZombie> affectations = entity.getAffectationZombies().stream()
                .map(this::toDomain)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

        return Edition.reconstituer(
                entity.getUuid(),
                entity.getNom(),
                creneau,
                entity.getLieu(),
                entity.getCapaciteCoureurs(),
                entity.getCapaciteZombies(),
                inscriptions,
                affectations,
                entity.isAnnulee()
        );
    }

    public EditionEntity toEntity(Edition edition) {
        EditionEntity entity = new EditionEntity();
        entity.setUuid(edition.getUuid());
        entity.setNom(edition.getNom());
        entity.setHeureDebut(edition.getCreneauHoraire().heureDebut());
        entity.setHeureFin(edition.getCreneauHoraire().heureFin());
        entity.setLieu(edition.getLieu());
        entity.setCapaciteCoureurs(edition.getCapaciteCoureurs());
        entity.setCapaciteZombies(edition.getCapaciteZombies());
        entity.setAnnulee(edition.isAnnulee());

        List<InscriptionCoureurEntity> inscriptions = edition.getInscriptionsCoureurs().stream()
                .map(inscription -> toEntity(inscription, entity))
                .toList();
        entity.setInscriptionsCoureurs(inscriptions);

        List<AffectationZombieEntity> affectations = edition.getAffectationZombies().stream()
                .map(affectation -> toEntity(affectation, entity))
                .toList();
        entity.setAffectationZombies(affectations);

        return entity;
    }

    private InscriptionCoureur toDomain(InscriptionCoureurEntity entity) {
        return new InscriptionCoureur(
                entity.getUuid(),
                utilisateurMapper.toDomain(entity.getUtilisateur())
        );
    }

    private InscriptionCoureurEntity toEntity(InscriptionCoureur inscription, EditionEntity edition) {
        InscriptionCoureurEntity entity = new InscriptionCoureurEntity();
        entity.setUuid(inscription.getUuid());
        entity.setEdition(edition);
        entity.setUtilisateur(utilisateurMapper.toEntity(inscription.getUtilisateur()));
        return entity;
    }

    private AffectationZombie toDomain(AffectationZombieEntity entity) {
        CreneauHoraire creneau = new CreneauHoraire(entity.getCreneauDebut(), entity.getCreneauFin());
        return new AffectationZombie(
                entity.getUuid(),
                utilisateurMapper.toDomain(entity.getUtilisateur()),
                creneau
        );
    }

    private AffectationZombieEntity toEntity(AffectationZombie affectation, EditionEntity edition) {
        AffectationZombieEntity entity = new AffectationZombieEntity();
        entity.setUuid(affectation.getUuid());
        entity.setEdition(edition);
        entity.setUtilisateur(utilisateurMapper.toEntity(affectation.getUtilisateur()));
        entity.setCreneauDebut(affectation.getCreneauHoraire().heureDebut());
        entity.setCreneauFin(affectation.getCreneauHoraire().heureFin());
        return entity;
    }
}