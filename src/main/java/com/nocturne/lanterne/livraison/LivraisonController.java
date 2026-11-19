package com.nocturne.lanterne.livraison;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LivraisonController {

    private final LivraisonService service;

    public LivraisonController(LivraisonService service) {
        this.service = service;
    }

    @GetMapping("/api/livraison/delai")
    public String delai(@RequestParam String codePostal) {
        return service.delai(codePostal);
    }
}
