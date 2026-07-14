package fr.epita.runordie.persistence.utilisateur;

import fr.epita.runordie.utilisateur.Email;
import fr.epita.runordie.utilisateur.Utilisateur;
import fr.epita.runordie.utilisateur.UtilisateurRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    public void sauvegarder(Utilisateur utilisateur) {
        UtilisateurEntity entity = mapper.toEntity(utilisateur);
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Utilisateur> trouverParEmail(Email email) {
        return jpaRepository.findByEmail(email.email())
                .map(mapper::toDomain);
    }
}