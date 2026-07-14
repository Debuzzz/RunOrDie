package fr.epita.runordie.exposition.controller;

import fr.epita.runordie.edition.CreerEditionCommande;
import fr.epita.runordie.edition.EditionService;
import fr.epita.runordie.exposition.dto.CreerEditionRequest;
import fr.epita.runordie.exposition.dto.EditionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/editions")
public class EditionController {

    private final EditionService editionService;

    public EditionController(EditionService editionService) {
        this.editionService = editionService;
    }

    @GetMapping
    public List<EditionResponse> listerEditions() {
        return editionService.trouverEditionAVenir().stream().map(EditionResponse::from).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EditionResponse creerEdition(@RequestBody CreerEditionRequest request) {
        CreerEditionCommande commande = new CreerEditionCommande(
                request.nom(),
                request.dateDebut(),
                request.dateFin(),
                request.lieu(),
                request.capaciteCoureurs(),
                request.capaciteZombies()
        );
        return EditionResponse.from(editionService.creerEdition(commande));
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