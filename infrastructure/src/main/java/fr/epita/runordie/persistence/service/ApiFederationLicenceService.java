package fr.epita.runordie.persistence.service;

import fr.epita.runordie.service.LicenceService;
import fr.epita.runordie.utilisateur.Utilisateur;
import org.springframework.stereotype.Service;

@Service
public class ApiFederationLicenceService implements LicenceService {

    @Override
    public boolean estLicencie(Utilisateur utilisateur) {
        //TODO:
        // appel réel à une API externe de fédération sportive
        // RestTemplate, WebClient, etc.
        return true; // stub temporaire
    }
}