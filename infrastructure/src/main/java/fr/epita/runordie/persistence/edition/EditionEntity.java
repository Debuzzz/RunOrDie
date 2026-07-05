package fr.epita.runordie.persistence.edition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "editions")
@Getter
@Setter
public class EditionEntity {
    @Id
    private UUID uuid;
    private String nom;

    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;

    private String lieu;
    private int capaciteCoureurs;
    private int capaciteZombies;
    private boolean annulee;

    @OneToMany(mappedBy = "edition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InscriptionCoureurEntity> inscriptionsCoureurs = new ArrayList<>();

    @OneToMany(mappedBy = "edition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AffectationZombieEntity> affectationZombies = new ArrayList<>();
}
