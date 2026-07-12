package fr.epita.runordie.exposition.controller;

import fr.epita.runordie.edition.Edition;
import fr.epita.runordie.edition.EditionService;
import fr.epita.runordie.edition.InscrireCoureurCommande;
import fr.epita.runordie.exposition.dto.InscriptionCoureurRequest;
import fr.epita.runordie.utilisateur.Email;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/coureurs")
public class CoureurController {

    private final EditionService editionService;

    public CoureurController(EditionService editionService) {
        this.editionService = editionService;
    }

    @GetMapping("/editions/disponibles")
    public List<Edition> editionsDisponibles(Principal principal) {
        Email email = new Email(principal.getName());
        return editionService.editionsDisponibles(email);
    }

    @GetMapping("/editions/inscrites")
    public List<Edition> editionsInscrites(Principal principal) {
        Email email = new Email(principal.getName());
        return editionService.editionsInscrites(email);
    }

    @PostMapping("/inscriptions")
    @ResponseStatus(HttpStatus.CREATED)
    public void inscrire(@RequestBody InscriptionCoureurRequest request, Principal principal) {
        Email email = new Email(principal.getName());
        InscrireCoureurCommande commande =
                new InscrireCoureurCommande(email, request.editionId());
        editionService.inscrireCoureur(commande);
    }
}