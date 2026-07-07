package fr.epita.runordie.persistence.utilisateur;

import fr.epita.runordie.utilisateur.Email;
import fr.epita.runordie.utilisateur.Utilisateur;
import fr.epita.runordie.utilisateur.UtilisateurRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UtilisateurRepositoryImpl implements UtilisateurRepository {

    private final SpringDataUtilisateurRepository jpaRepository;
    private final UtilisateurMapper mapper;

    public UtilisateurRepositoryImpl(SpringDataUtilisateurRepository jpaRepository,
                                     UtilisateurMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Utilisateur sauvegarder(Utilisateur utilisateur) {
        UtilisateurEntity entity = mapper.toEntity(utilisateur);
        UtilisateurEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Utilisateur trouverParEmail(Email email) {
        return jpaRepository.findByEmail(email.email())
                .map(mapper::toDomain)
                .orElseThrow(() -> new RuntimeException("email not found"));
    }
}