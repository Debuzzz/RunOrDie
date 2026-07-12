package fr.epita.runordie.exposition.controller;

import fr.epita.runordie.edition.EditionService;
import fr.epita.runordie.edition.Histogramme;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/editions")
public class HistogrammeController {

    private final EditionService editionService;

    public HistogrammeController(EditionService editionService) {
        this.editionService = editionService;
    }

    @GetMapping("/{id}/zombies/histogramme")
    public List<Histogramme> histogramme(@PathVariable UUID id) {
        return editionService.histogrammeZombies(id);
    }
}