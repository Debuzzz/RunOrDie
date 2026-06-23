package fr.epita.runordie.infrastructureservice;

import fr.epita.runordie.edition.Edition;

public interface NotificationService {
    void notifier(Edition edition);
}
