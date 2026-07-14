package fr.epita.runordie.exposition.controller;

import fr.epita.runordie.edition.AffecterZombieCommande;
import fr.epita.runordie.edition.EditionService;
import fr.epita.runordie.exposition.dto.AffectationResponse;
import fr.epita.runordie.exposition.dto.AffectationZombieRequest;
import fr.epita.runordie.exposition.dto.CreneauZombieResponse;
import fr.epita.runordie.utilisateur.Email;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/zombies")
public class ZombieController {

    private final EditionService editionService;

    public ZombieController(EditionService editionService) {
        this.editionService = editionService;
    }

    @GetMapping("/creneaux/disponibles")
    public List<CreneauZombieResponse> creneauxDisponibles(Principal principal) {
        Email email = new Email(principal.getName());
        return editionService.creneauxDisponibles(email).stream().map(CreneauZombieResponse::from).toList();
    }

    @GetMapping("/creneaux/affectes")
    public List<CreneauZombieResponse> creneauxAffectes(Principal principal) {
        Email email = new Email(principal.getName());
        return editionService.creneauxAffectes(email).stream().map(CreneauZombieResponse::from).toList();
    }

    @PostMapping("/editions/{editionId}/affectations")
    @ResponseStatus(HttpStatus.CREATED)
    public AffectationResponse affecter(@PathVariable UUID editionId, @RequestBody AffectationZombieRequest request, Principal principal) {
        Email email = new Email(principal.getName());
        AffecterZombieCommande commande = new AffecterZombieCommande(
                email, editionId, request.heureDebut(), request.heureFin());
        return AffectationResponse.from(editionService.affecterZombie(commande));
    }
}