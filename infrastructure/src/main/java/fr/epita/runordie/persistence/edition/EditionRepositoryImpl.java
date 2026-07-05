package fr.epita.runordie.persistence.edition;

import fr.epita.runordie.edition.Edition;
import fr.epita.runordie.edition.EditionRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
class EditionRepositoryImpl implements EditionRepository {

    private final SpringDataEditionRepository jpaRepository;
    private final EditionMapper mapper;

    EditionRepositoryImpl(SpringDataEditionRepository jpaRepository, EditionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Edition sauvegarder(Edition edition) {
        EditionEntity entity = mapper.toEntity(edition);
        EditionEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void supprimer(Edition edition) {
        jpaRepository.deleteById(edition.getUuid());
    }

    @Override
    public Edition trouverParId(UUID uuid) {
        return jpaRepository.findById(uuid)
                .map(mapper::toDomain)
                .orElseThrow(() -> new RuntimeException("uuid not found"));
    }

    @Override
    public List<Edition> trouverEditionsAVenir() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .filter(edition -> edition.getCreneauHoraire().heureDebut().isAfter(LocalDateTime.now())
                        && !edition.isAnnulee())
                .toList();
    }}