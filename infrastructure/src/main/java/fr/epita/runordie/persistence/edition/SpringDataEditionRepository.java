package fr.epita.runordie.persistence.edition;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataEditionRepository extends JpaRepository<EditionEntity, UUID> {}
