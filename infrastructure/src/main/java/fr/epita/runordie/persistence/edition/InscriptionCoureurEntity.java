package fr.epita.runordie.persistence.edition;

import fr.epita.runordie.persistence.utilisateur.UtilisateurEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "inscription_coureur")
@Getter
@Setter
public class InscriptionCoureurEntity {
    @Id
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "edition_uuid")
    private EditionEntity edition;

    @ManyToOne
    @JoinColumn(name = "utilisateur_uuid")
    private UtilisateurEntity utilisateur;
}
