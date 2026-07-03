package fr.epita.runordie.interfaces;

import fr.epita.runordie.edition.Edition;
import fr.epita.runordie.edition.EditionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
class EditionRepositoryImpl implements EditionRepository {

    @Override
    public Edition sauvegarder(Edition edition) {
        return null;
    }

    @Override
    public void supprimer(Edition edition) {

    }

    @Override
    public Edition trouverParId(UUID uuid) {
        return null;
    }

    @Override
    public List<Edition> trouverEditionsAVenir() {
        return List.of();
    }
}