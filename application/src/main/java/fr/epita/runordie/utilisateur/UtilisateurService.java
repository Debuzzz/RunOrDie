package fr.epita.runordie.utilisateur;

import fr.epita.runordie.service.MotDePasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final MotDePasseService motDePasseService;

    @Autowired
    public UtilisateurService(UtilisateurRepository utilisateurRepository, MotDePasseService motDePasseService) {
        this.utilisateurRepository = utilisateurRepository;
        this.motDePasseService = motDePasseService;
    }

    @Transactional
    public Utilisateur creerUtilisateur(CreerUtilisateurCommande commande) {
        Email email = new Email(commande.email());

        if (this.utilisateurRepository.trouverParEmail(email) != null) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà.");
        }

        MotDePasseHache motDePasseHache = this.motDePasseService.hacher(commande.motDePasseEnClair());
        Utilisateur utilisateur = new Utilisateur(UUID.randomUUID(), email, motDePasseHache, Role.UTILISATEUR);

        return this.utilisateurRepository.sauvegarder(utilisateur);
    }
}
