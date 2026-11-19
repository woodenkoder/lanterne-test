package com.nocturne.lanterne.commande;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/** Le parcours d'achat : créer une commande, puis la payer. */
@RestController
public class CommandeController {

    public record NouvelleCommande(String client, BigDecimal montant) {}

    public record Paiement(String carte) {}

    private final Banque banque;
    private final AtomicLong sequence = new AtomicLong();
    private final Map<Long, Commande> commandes = new ConcurrentHashMap<>();

    public CommandeController(Banque banque) {
        this.banque = banque;
    }

    @PostMapping("/api/commandes")
    @ResponseStatus(HttpStatus.CREATED)
    public Commande creer(@RequestBody NouvelleCommande nouvelle) {
        long id = sequence.incrementAndGet();
        Commande commande = new Commande(id, nouvelle.client(), nouvelle.montant(), "EN_ATTENTE_DE_PAIEMENT", null);
        commandes.put(id, commande);
        return commande;
    }

    @GetMapping("/api/commandes/{id}")
    public Commande lire(@PathVariable Long id) {
        Commande commande = commandes.get(id);
        if (commande == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return commande;
    }

    /** Payer : PAYEE si la banque accepte ; sinon la commande reste en attente, avec « Paiement refusé ». */
    @PostMapping("/api/commandes/{id}/paiement")
    public Commande payer(@PathVariable Long id, @RequestBody Paiement paiement) {
        Commande commande = lire(id);
        Commande apres = banque.payer(paiement.carte(), commande.montant())
                ? new Commande(id, commande.client(), commande.montant(), "PAYEE", "Merci pour ta commande")
                : new Commande(id, commande.client(), commande.montant(), "EN_ATTENTE_DE_PAIEMENT", "Paiement refusé");
        commandes.put(id, apres);
        return apres;
    }
}
