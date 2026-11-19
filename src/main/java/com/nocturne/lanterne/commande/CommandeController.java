package com.nocturne.lanterne.commande;

import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CommandeController {

    public record NouvelleCommande(String client, BigDecimal montant) {}

    private final CommandeRepository commandes;

    public CommandeController(CommandeRepository commandes) {
        this.commandes = commandes;
    }

    @PostMapping("/api/commandes")
    @ResponseStatus(HttpStatus.CREATED)
    public Commande creer(@RequestBody NouvelleCommande nouvelle) {
        return commandes.save(new Commande(nouvelle.client(), nouvelle.montant()));
    }

    @GetMapping("/api/commandes/{id}")
    public Commande lire(@PathVariable Long id) {
        return commandes.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
