package fr.epita.runordie.exposition.controller;

import fr.epita.runordie.edition.AffecterZombieCommande;
import fr.epita.runordie.edition.CreneauZombie;
import fr.epita.runordie.edition.EditionService;
import fr.epita.runordie.exposition.dto.AffectationZombieRequest;
import fr.epita.runordie.utilisateur.Email;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/zombies")
public class ZombieController {

    private final EditionService editionService;

    public ZombieController(EditionService editionService) {
        this.editionService = editionService;
    }

    @GetMapping("/creneaux/disponibles")
    public List<CreneauZombie> creneauxDisponibles(Principal principal) {
        Email email = new Email(principal.getName());
        return editionService.creneauxDisponibles(email);
    }

    @GetMapping("/creneaux/affectes")
    public List<CreneauZombie> creneauxAffectes(Principal principal) {
        Email email = new Email(principal.getName());
        return editionService.creneauxAffectes(email);
    }

    @PostMapping("/affectations")
    @ResponseStatus(HttpStatus.CREATED)
    public void affecter(@RequestBody AffectationZombieRequest request, Principal principal) {
        Email email = new Email(principal.getName());
        AffecterZombieCommande commande = new AffecterZombieCommande(
                email, request.editionId(), request.heureDebut(), request.heureFin());
        editionService.affecterZombie(commande);
    }
}