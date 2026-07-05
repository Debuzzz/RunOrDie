package fr.epita.runordie.edition;

import fr.epita.runordie.utilisateur.Utilisateur;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Edition {
    private final UUID uuid;
    private final String nom;
    private final CreneauHoraire creneauHoraire;
    private final String lieu;
    private final int capaciteCoureurs;
    private final int capaciteZombies;
    private final List<InscriptionCoureur> inscriptionsCoureurs;
    private final List<AffectationZombie> affectationZombies;
    private boolean annulee;

    public Edition(UUID uuid, String nom, CreneauHoraire creneauHoraire, String lieu, int capaciteCoureurs, int capaciteZombies, List<InscriptionCoureur> inscriptionsCoureurs, List<AffectationZombie> affectationZombies) {
        this(uuid, nom, creneauHoraire, lieu, capaciteCoureurs, capaciteZombies, inscriptionsCoureurs, affectationZombies, false);

        if (!creneauHoraire.heureDebut().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La date d'une édition doit être dans le futur.");
        }
    }

    private Edition(UUID uuid, String nom, CreneauHoraire creneauHoraire, String lieu, int capaciteCoureurs, int capaciteZombies, List<InscriptionCoureur> inscriptionsCoureurs, List<AffectationZombie> affectationZombies, boolean annulee) {
        if (uuid == null || nom == null || creneauHoraire == null || lieu == null
                || inscriptionsCoureurs == null || affectationZombies == null) {
            throw new IllegalArgumentException("Aucun champ d'une Edition ne peut être nul.");
        }

        if (capaciteCoureurs <= 0 || capaciteZombies <= 0) {
            throw new IllegalArgumentException("La capacité de coureur et/ou de zombies doit être positive.");
        }

        this.uuid = uuid;
        this.nom = nom;
        this.creneauHoraire = creneauHoraire;
        this.lieu = lieu;
        this.capaciteCoureurs = capaciteCoureurs;
        this.capaciteZombies = capaciteZombies;
        this.inscriptionsCoureurs = inscriptionsCoureurs;
        this.affectationZombies = affectationZombies;
        this.annulee = annulee;
    }

    public static Edition reconstituer(UUID uuid, String nom, CreneauHoraire creneauHoraire, String lieu, int capaciteCoureurs, int capaciteZombies, List<InscriptionCoureur> inscriptionsCoureurs, List<AffectationZombie> affectationZombies, boolean annulee) {
        return new Edition(uuid, nom, creneauHoraire, lieu, capaciteCoureurs, capaciteZombies, inscriptionsCoureurs, affectationZombies, annulee);
    }

    public InscriptionCoureur inscrireCoureur(Utilisateur coureur) {
        validerEditionFutureEtNonAnnulee();
        refuserSiDejaCoureur(coureur);
        refuserSiDejaZombie(coureur);
        validerCapaciteCoureursDisponible();

        InscriptionCoureur inscription = new InscriptionCoureur(UUID.randomUUID(), coureur);
        inscriptionsCoureurs.add(inscription);
        return inscription;
    }

    public AffectationZombie affecterZombie(Utilisateur zombie, CreneauHoraire creneau) {
        validerEditionFutureEtNonAnnulee();
        refuserSiDejaCoureur(zombie);
        refuserSiDejaSurCeCreneau(zombie, creneau);
        validerCapaciteZombiesDisponible(creneau);

        AffectationZombie affectation = new AffectationZombie(UUID.randomUUID(), zombie, creneau);
        affectationZombies.add(affectation);
        return affectation;
    }

    public void annuler() {
        this.annulee = true;
    }

    public boolean aDesInscrits() {
        return !inscriptionsCoureurs.isEmpty() || !affectationZombies.isEmpty();
    }

    public int placesCoureursRestantes() {
        return capaciteCoureurs - inscriptionsCoureurs.size();
    }

    public boolean peutSinscrireCommeCoureur(Utilisateur utilisateur) {
        return estFutureEtNonAnnulee()
                && !estInscritCommeCoureur(utilisateur)
                && !estAffecteCommeZombie(utilisateur)
                && placesCoureursRestantes() > 0;
    }

    public List<Histogramme> histogrammeZombiesParHeure() {
        return tranchesHoraires().stream()
                .map(tranche -> new Histogramme(tranche, affectationZombies.stream()
                        .filter(affectation -> affectation.getCreneauHoraire().chevauche(tranche))
                        .count()))
                .toList();
    }

    public List<CreneauHoraire> creneauxAffectesPourZombie(Utilisateur zombie) {
        return affectationZombies.stream()
                .filter(affectation -> affectation.getUtilisateur().equals(zombie))
                .map(AffectationZombie::getCreneauHoraire)
                .toList();
    }

    public List<CreneauHoraire> creneauxDisponiblesPourZombie(Utilisateur zombie) {
        if (!estFutureEtNonAnnulee() || estInscritCommeCoureur(zombie)) {
            return List.of();
        }
        return tranchesHoraires().stream()
                .filter(tranche -> !estDejaAffecteSurCeCreneau(zombie, tranche) && placesZombiesRestantes(tranche) > 0)
                .toList();
    }

    public boolean estInscritCommeCoureur(Utilisateur utilisateur) {
        return inscriptionsCoureurs.stream()
                .anyMatch(inscription -> inscription.getUtilisateur().equals(utilisateur));
    }

    private List<CreneauHoraire> tranchesHoraires() {
        List<CreneauHoraire> tranches = new ArrayList<>();
        LocalDateTime heureActuelle = creneauHoraire.heureDebut();
        while (heureActuelle.isBefore(creneauHoraire.heureFin())) {
            tranches.add(new CreneauHoraire(heureActuelle, heureActuelle.plusHours(1)));
            heureActuelle = heureActuelle.plusHours(1);
        }
        return tranches;
    }

    private boolean estFutureEtNonAnnulee() {
        return creneauHoraire.heureDebut().isAfter(LocalDateTime.now()) && !annulee;
    }

    private void validerEditionFutureEtNonAnnulee() {
        if (!estFutureEtNonAnnulee()) {
            throw new IllegalArgumentException("Impossible d'agir sur une édition passée ou annulée.");
        }
    }

    private void refuserSiDejaCoureur(Utilisateur utilisateur) {
        if (estInscritCommeCoureur(utilisateur)) {
            throw new IllegalArgumentException("Cet utilisateur est déjà inscrit comme coureur sur cette édition.");
        }
    }

    private boolean estAffecteCommeZombie(Utilisateur utilisateur) {
        return affectationZombies.stream()
                .anyMatch(affectation -> affectation.getUtilisateur().equals(utilisateur));
    }

    private void refuserSiDejaZombie(Utilisateur utilisateur) {
        if (estAffecteCommeZombie(utilisateur)) {
            throw new IllegalArgumentException("Cet utilisateur est déjà affecté comme zombie sur cette édition.");
        }
    }

    private boolean estDejaAffecteSurCeCreneau(Utilisateur zombie, CreneauHoraire creneau) {
        return affectationZombies.stream()
                .anyMatch(affectation -> affectation.getUtilisateur().equals(zombie)
                        && affectation.getCreneauHoraire().equals(creneau));
    }

    private void refuserSiDejaSurCeCreneau(Utilisateur zombie, CreneauHoraire creneau) {
        if (estDejaAffecteSurCeCreneau(zombie, creneau)) {
            throw new IllegalArgumentException("Ce zombie est déjà affecté sur ce créneau.");
        }
    }

    private void validerCapaciteCoureursDisponible() {
        if (placesCoureursRestantes() <= 0) {
            throw new IllegalArgumentException("La capacité maximale de coureurs est atteinte.");
        }
    }

    private int placesZombiesRestantes(CreneauHoraire creneau) {
        long zombiesSurCeCreneau = affectationZombies.stream()
                .filter(affectation -> affectation.getCreneauHoraire().equals(creneau))
                .count();
        return capaciteZombies - (int) zombiesSurCeCreneau;
    }

    private void validerCapaciteZombiesDisponible(CreneauHoraire creneau) {
        if (placesZombiesRestantes(creneau) <= 0) {
            throw new IllegalArgumentException("La capacité maximale de zombies est atteinte sur ce créneau.");
        }
    }
}