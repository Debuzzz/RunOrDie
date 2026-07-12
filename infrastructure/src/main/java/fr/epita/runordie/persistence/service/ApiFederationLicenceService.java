package fr.epita.runordie.persistence.service;

import fr.epita.runordie.service.LicenceService;
import fr.epita.runordie.utilisateur.Utilisateur;
import org.springframework.stereotype.Service;

@Service
public class ApiFederationLicenceService implements LicenceService {

    @Override
    public boolean estLicencie(Utilisateur utilisateur) {
        System.out.println("Utilisateur licentié par défaut");
        return true;
    }
}