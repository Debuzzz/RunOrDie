package fr.epita.runordie.exposition.controller;

import fr.epita.runordie.exposition.dto.CreerUtilisateurRequest;
import fr.epita.runordie.utilisateur.CreerUtilisateurCommande;
import fr.epita.runordie.utilisateur.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void creerUtilisateur(@RequestBody CreerUtilisateurRequest request) {
        CreerUtilisateurCommande commande =
                new CreerUtilisateurCommande(request.email(), request.motDePasse());
        utilisateurService.creerUtilisateur(commande);
    }
}