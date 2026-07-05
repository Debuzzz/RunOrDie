package fr.epita.runordie.persistence.utilisateur;

import fr.epita.runordie.utilisateur.Email;
import fr.epita.runordie.utilisateur.MotDePasseHache;
import fr.epita.runordie.utilisateur.Utilisateur;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurMapper {

    public Utilisateur toDomain(UtilisateurEntity entity) {
        return new Utilisateur(
                entity.getUuid(),
                new Email(entity.getEmail()),
                new MotDePasseHache(entity.getMotDePasse()),
                entity.getRole()
        );
    }

    public UtilisateurEntity toEntity(Utilisateur utilisateur) {
        UtilisateurEntity entity = new UtilisateurEntity();
        entity.setUuid(utilisateur.getUuid());
        entity.setEmail(utilisateur.getEmail().email());
        entity.setMotDePasse(utilisateur.getMotDePasse().value());
        entity.setRole(utilisateur.getRole());
        return entity;
    }
}