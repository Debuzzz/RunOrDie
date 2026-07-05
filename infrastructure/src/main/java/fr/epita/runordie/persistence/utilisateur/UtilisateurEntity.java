package fr.epita.runordie.persistence.utilisateur;

import fr.epita.runordie.utilisateur.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "utilisateur")
@Getter
@Setter
public class UtilisateurEntity {
    @Id
    private UUID uuid;

    @Column(unique = true)
    private String email;

    private String motDePasse;

    @Enumerated(EnumType.STRING)
    private Role role;
}
