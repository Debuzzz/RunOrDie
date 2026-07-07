package fr.epita.runordie.persistence.utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataUtilisateurRepository extends JpaRepository<UtilisateurEntity, UUID> {
    Optional<UtilisateurEntity> findByEmail(String email);
}
