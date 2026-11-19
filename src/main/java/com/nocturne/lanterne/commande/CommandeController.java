package com.nocturne.lanterne.commande;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** L'API des commandes. Un controller fin : il reçoit, délègue au service, répond. */
@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    private final CommandeService service;

    public CommandeController(CommandeService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommandeCreee creer(@RequestBody NouvelleCommande commande) {
        return service.creer(commande);
    }
}
