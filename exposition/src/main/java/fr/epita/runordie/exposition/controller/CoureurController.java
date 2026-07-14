package fr.epita.runordie.exposition.controller;

import fr.epita.runordie.edition.EditionService;
import fr.epita.runordie.edition.InscrireCoureurCommande;
import fr.epita.runordie.exposition.dto.EditionResponse;
import fr.epita.runordie.exposition.dto.InscriptionResponse;
import fr.epita.runordie.utilisateur.Email;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/coureurs/editions")
public class CoureurController {

    private final EditionService editionService;

    public CoureurController(EditionService editionService) {
        this.editionService = editionService;
    }

    @GetMapping("/disponibles")
    public List<EditionResponse> editionsDisponibles(Principal principal) {
        Email email = new Email(principal.getName());
        return editionService.editionsDisponibles(email).stream().map(EditionResponse::from).toList();
    }

    @GetMapping("/inscrites")
    public List<EditionResponse> editionsInscrites(Principal principal) {
        Email email = new Email(principal.getName());
        return editionService.editionsInscrites(email).stream().map(EditionResponse::from).toList();
    }

    @PostMapping("/{editionId}/inscriptions")
    @ResponseStatus(HttpStatus.CREATED)
    public InscriptionResponse inscrire(@PathVariable UUID editionId, Principal principal) {
        Email email = new Email(principal.getName());
        InscrireCoureurCommande commande =
                new InscrireCoureurCommande(email, editionId);
        return InscriptionResponse.from(editionService.inscrireCoureur(commande));
    }
}