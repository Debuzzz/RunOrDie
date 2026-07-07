package fr.epita.runordie.persistence.service;

import fr.epita.runordie.edition.Edition;
import fr.epita.runordie.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationService {
    @Override
    public void notifier(Edition edition) {
        //TODO:
        // logique d'envoi réel (JavaMailSender, appel API SendGrid, etc.)
        System.out.println("Notification envoyée pour l'édition : " + edition.getNom());
    }
}
