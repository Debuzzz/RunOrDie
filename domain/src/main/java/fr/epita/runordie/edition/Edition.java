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
        if (uuid == null || nom == null || creneauHoraire == null || lieu == null
                || inscriptionsCoureurs == null || affectationZombies == null) {
            throw new IllegalArgumentException("Aucun champ d'une Edition ne peut être nul.");
        }
        if (!creneauHoraire.heureDebut().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La date d'une édition doit être dans le futur.");
        }

        this.uuid = uuid;
        this.nom = nom;
        this.creneauHoraire = creneauHoraire;
        this.lieu = lieu;
        this.capaciteCoureurs = capaciteCoureurs;
        this.capaciteZombies = capaciteZombies;
        this.inscriptionsCoureurs = inscriptionsCoureurs;
        this.affectationZombies = affectationZombies;
        this.annulee = false;
    }

    public InscriptionCoureur inscrireCoureur(Utilisateur coureur) {
        validerEditionFuture();
        refuserSiDejaCoureur(coureur);
        refuserSiDejaZombie(coureur);
        validerCapaciteCoureursDisponible();

        InscriptionCoureur inscription = new InscriptionCoureur(UUID.randomUUID(), coureur);
        inscriptionsCoureurs.add(inscription);
        return inscription;
    }

    public AffectationZombie affecterZombie(Utilisateur zombie, CreneauHoraire creneau) {
        validerEditionFuture();
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

    public List<Histogramme> histogrammeZombiesParHeure() {
        List<Histogramme> histogramme = new ArrayList<>();

        LocalDateTime heureActuelle = creneauHoraire.heureDebut();
        while (heureActuelle.isBefore(creneauHoraire.heureFin())) {
            CreneauHoraire tranche = new CreneauHoraire(heureActuelle, heureActuelle.plusHours(1));

            long nombreZombies = affectationZombies.stream()
                    .filter(affectation -> affectation.getCreneauHoraire().chevauche(tranche))
                    .count();

            histogramme.add(new Histogramme(tranche, nombreZombies));
            heureActuelle = heureActuelle.plusHours(1);
        }

        return histogramme;
    }

    private void validerEditionFuture() {
        if (!creneauHoraire.heureDebut().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Impossible de s'inscrire ou de s'affecter à une édition passée.");
        }
    }

    private void refuserSiDejaCoureur(Utilisateur utilisateur) {
        boolean flag = inscriptionsCoureurs.stream()
                .anyMatch(inscription -> inscription.getUtilisateur().equals(utilisateur));
        if (flag) {
            throw new IllegalArgumentException("Cet utilisateur est déjà inscrit comme coureur sur cette édition.");
        }
    }

    private void refuserSiDejaZombie(Utilisateur utilisateur) {
        boolean flag = affectationZombies.stream()
                .anyMatch(affectation -> affectation.getUtilisateur().equals(utilisateur));
        if (flag) {
            throw new IllegalArgumentException("Cet utilisateur est déjà affecté comme zombie sur cette édition.");
        }
    }

    private void refuserSiDejaSurCeCreneau(Utilisateur zombie, CreneauHoraire creneau) {
        boolean flag = affectationZombies.stream()
                .anyMatch(affectation -> affectation.getUtilisateur().equals(zombie)
                        && affectation.getCreneauHoraire().equals(creneau));
        if (flag) {
            throw new IllegalArgumentException("Ce zombie est déjà affecté sur ce créneau.");
        }
    }

    private void validerCapaciteCoureursDisponible() {
        if (inscriptionsCoureurs.size() >= capaciteCoureurs) {
            throw new IllegalArgumentException("La capacité maximale de coureurs est atteinte.");
        }
    }

    private void validerCapaciteZombiesDisponible(CreneauHoraire creneau) {
        long zombiesSurCeCreneau = affectationZombies.stream()
                .filter(affectation -> affectation.getCreneauHoraire().equals(creneau))
                .count();
        if (zombiesSurCeCreneau >= capaciteZombies) {
            throw new IllegalArgumentException("La capacité maximale de zombies est atteinte sur ce créneau.");
        }
    }
}