package com.nocturne.lanterne.commande;

import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandeController {

    public record NouvelleCommande(String client, BigDecimal montant) {}

    private final CommandeService service;

    public CommandeController(CommandeService service) {
        this.service = service;
    }

    @PostMapping("/api/commandes")
    @ResponseStatus(HttpStatus.CREATED)
    public Commande creer(@RequestBody NouvelleCommande nouvelle) {
        return service.creer(nouvelle.client(), nouvelle.montant());
    }

    @PostMapping("/api/commandes/{id}/validation")
    public Commande valider(@PathVariable Long id) {
        return service.valider(id);
    }
}
