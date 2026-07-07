package fr.epita.runordie.edition;

import fr.epita.runordie.service.LicenceService;
import fr.epita.runordie.service.NotificationService;
import fr.epita.runordie.utilisateur.Email;
import fr.epita.runordie.utilisateur.Utilisateur;
import fr.epita.runordie.utilisateur.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EditionService {

    private final EditionRepository editionRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final NotificationService notificationService;
    private final LicenceService licenceService;

    @Autowired
    public EditionService(EditionRepository editionRepository, UtilisateurRepository utilisateurRepository, NotificationService notificationService, LicenceService licenceService) {
        this.editionRepository = editionRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.notificationService = notificationService;
        this.licenceService = licenceService;
    }

    @Transactional
    public Edition creerEdition(CreerEditionCommande commande) {
        CreneauHoraire creneauHoraire = new CreneauHoraire(commande.dateDebut(), commande.dateFin());

        List<CreneauHoraire> creneauxExistants = editionRepository.trouverEditionsAVenir().stream()
                .map(Edition::getCreneauHoraire)
                .toList();
        if (creneauHoraire.chevauche(creneauxExistants)) {
            throw new IllegalArgumentException("Cette édition chevauche une édition déjà existante.");
        }

        Edition edition = new Edition(UUID.randomUUID(), commande.nom(), creneauHoraire, commande.lieu(),
                commande.capaciteCoureurs(), commande.capaciteZombies(), new ArrayList<>(), new ArrayList<>());

        return editionRepository.sauvegarder(edition);
    }

    @Transactional
    public boolean supprimerEdition(UUID uuid) {
        Edition edition = chargerEdition(uuid);

        if (edition.aDesInscrits()) {
            return false;
        }

        this.editionRepository.supprimer(edition);
        return true;
    }

    @Transactional
    public void annulerEdition(UUID uuid) {
        Edition edition = chargerEdition(uuid);

        edition.annuler();
        editionRepository.sauvegarder(edition);
        notificationService.notifier(edition);
    }

    @Transactional
    public List<Edition> trouverEditionAVenir() {
        return this.editionRepository.trouverEditionsAVenir();
    }

    @Transactional
    public List<Edition> editionsInscrites(Email email) {
        Utilisateur coureur = chargerUtilisateur(email);
        return editionRepository.trouverEditionsAVenir().stream()
                .filter(edition -> edition.estInscritCommeCoureur(coureur))
                .toList();
    }

    @Transactional
    public List<Edition> editionsDisponibles(Email email) {
        Utilisateur coureur = chargerUtilisateur(email);
        boolean licencie = licenceService.estLicencie(coureur);
        return editionRepository.trouverEditionsAVenir().stream()
                .filter(edition -> licencie && edition.peutSinscrireCommeCoureur(coureur))
                .toList();
    }

    @Transactional
    public List<CreneauZombie> creneauxAffectes(Email email) {
        Utilisateur zombie = chargerUtilisateur(email);
        return editionRepository.trouverEditionsAVenir().stream()
                .flatMap(edition -> edition.creneauxAffectesPourZombie(zombie).stream()
                        .map(creneau -> new CreneauZombie(edition, creneau)))
                .toList();
    }

    @Transactional
    public List<CreneauZombie> creneauxDisponibles(Email email) {
        Utilisateur zombie = chargerUtilisateur(email);
        return editionRepository.trouverEditionsAVenir().stream()
                .flatMap(edition -> edition.creneauxDisponiblesPourZombie(zombie).stream()
                        .map(creneau -> new CreneauZombie(edition, creneau)))
                .toList();
    }

    @Transactional
    public AffectationZombie affecterZombie(AffecterZombieCommande commande) {
        Edition edition = chargerEdition(commande.editionId());
        Utilisateur zombie = chargerUtilisateur(commande.email());

        CreneauHoraire creneauHoraire = new CreneauHoraire(commande.heureDebut(), commande.heureFin());
        AffectationZombie affectation = edition.affecterZombie(zombie, creneauHoraire);
        this.editionRepository.sauvegarder(edition);

        return affectation;
    }

    @Transactional
    public InscriptionCoureur inscrireCoureur(InscrireCoureurCommande commande) {
        Edition edition = chargerEdition(commande.editionId());
        Utilisateur coureur = chargerUtilisateur(commande.email());

        if (!this.licenceService.estLicencie(coureur)) {
            throw new IllegalArgumentException("Ce coureur n'est pas licencié.");
        }

        InscriptionCoureur inscription = edition.inscrireCoureur(coureur);
        this.editionRepository.sauvegarder(edition);

        return inscription;
    }

    @Transactional
    public List<Histogramme> histogrammeZombies(UUID editionId) {
        Edition edition = chargerEdition(editionId);
        return edition.histogrammeZombiesParHeure();
    }

    private Edition chargerEdition(UUID id) {
        Edition edition = this.editionRepository.trouverParId(id);
        if (edition == null) {
            throw new IllegalArgumentException("L'édition n'existe pas.");
        }
        return edition;
    }

    private Utilisateur chargerUtilisateur(Email email) {
        Utilisateur utilisateur = this.utilisateurRepository.trouverParEmail(email);
        if (utilisateur == null) {
            throw new IllegalArgumentException("L'utilisateur n'existe pas.");
        }
        return utilisateur;
    }
}
