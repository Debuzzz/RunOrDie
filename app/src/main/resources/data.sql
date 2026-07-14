DELETE
FROM affectation_zombie;
DELETE
FROM inscription_coureur;
DELETE
FROM editions;
DELETE
FROM utilisateur;

INSERT INTO utilisateur (uuid, email, mot_de_passe, role)
VALUES ('11111111-1111-1111-1111-111111111111', 'mastermind@epita.fr',
        '$2b$10$hMozHoB6aHuuThXvZtub0O6iOrfUefd5dqwDBq3t2IgGZDzMaynSa', 'ORGANISATEUR'),
       ('22222222-2222-2222-2222-222222222222', 'forrest@epita.fr',
        '$2b$10$CP2tIb9Y6d83ZngOLigOhOiCid2U3C8TuCPBRHofBDLMzbcuKXAs2', 'UTILISATEUR');

INSERT INTO utilisateur (uuid, email, mot_de_passe, role)
VALUES ('33333333-3333-3333-3333-333333333333', 'alice@epita.fr',
        '$2b$10$CP2tIb9Y6d83ZngOLigOhOiCid2U3C8TuCPBRHofBDLMzbcuKXAs2', 'UTILISATEUR'),
       ('44444444-4444-4444-4444-444444444444', 'bob@epita.fr',
        '$2b$10$CP2tIb9Y6d83ZngOLigOhOiCid2U3C8TuCPBRHofBDLMzbcuKXAs2', 'UTILISATEUR');

INSERT INTO editions (uuid, nom, heure_debut, heure_fin, lieu, capacite_coureurs, capacite_zombies, annulee)
VALUES ('55555555-5555-5555-5555-555555555555', 'Run or Die — Édition Automne', '2026-10-10 20:00:00',
        '2026-10-11 06:00:00', 'Forêt de Fontainebleau', 50, 20, false),
       ('66666666-6666-6666-6666-666666666666', 'Run or Die — Édition Hiver', '2026-12-05 20:00:00',
        '2026-12-06 06:00:00', 'Bois de Vincennes', 30, 15, false);

INSERT INTO affectation_zombie (uuid, edition_uuid, utilisateur_uuid, creneau_debut, creneau_fin)
VALUES ('77777777-7777-7777-7777-777777777777', '55555555-5555-5555-5555-555555555555',
        '22222222-2222-2222-2222-222222222222', '2026-10-10 21:00:00', '2026-10-10 22:00:00'),
       ('88888888-8888-8888-8888-888888888888', '55555555-5555-5555-5555-555555555555',
        '33333333-3333-3333-3333-333333333333', '2026-10-10 22:00:00', '2026-10-10 23:00:00');

INSERT INTO inscription_coureur (uuid, edition_uuid, utilisateur_uuid)
VALUES ('99999999-9999-9999-9999-999999999999', '66666666-6666-6666-6666-666666666666',
        '44444444-4444-4444-4444-444444444444');