package fr.epita.runordie.edition;

import java.util.List;
import java.util.UUID;

public interface EditionRepository {
    Edition sauvegarder(Edition edition);
    void supprimer(Edition edition);
    Edition trouverParId(UUID uuid);
    List<Edition> trouverEditionsAVenir();
}