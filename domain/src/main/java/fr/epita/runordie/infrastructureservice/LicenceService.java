package fr.epita.runordie.infrastructureservice;

import fr.epita.runordie.utilisateur.Utilisateur;

public interface LicenceService {
    boolean estLicencie(Utilisateur utilisateur);
}
