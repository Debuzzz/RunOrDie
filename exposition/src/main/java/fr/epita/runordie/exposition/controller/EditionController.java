package fr.epita.runordie.exposition.controller;

import fr.epita.runordie.edition.CreerEditionCommande;
import fr.epita.runordie.edition.EditionService;
import fr.epita.runordie.exposition.dto.CreerEditionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/editions")
public class EditionController {

    private final EditionService editionService;

    public EditionController(EditionService editionService) {
        this.editionService = editionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void creerEdition(@RequestBody CreerEditionRequest request) {
        CreerEditionCommande commande = new CreerEditionCommande(
                request.nom(),
                request.dateDebut(),
                request.dateFin(),
                request.lieu(),
                request.capaciteCoureurs(),
                request.capaciteZombies()
        );
        editionService.creerEdition(commande);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerEdition(@PathVariable UUID id) {
        boolean supprimee = editionService.supprimerEdition(id);
        if (!supprimee) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/annuler")
    @ResponseStatus(HttpStatus.OK)
    public void annulerEdition(@PathVariable UUID id) {
        editionService.annulerEdition(id);
    }
}